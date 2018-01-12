package tcg.mtgjson;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tcg.db.dao.ImporterDao;
import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Set;

@Component
public class EditionImporter {

	private static final int INTERVAL = 10 * 60 * 1000;
	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;

	private Deque<String> codes = new LinkedList<>();

	@Scheduled(fixedRate = INTERVAL)
	void doImport() {
		if (codes.isEmpty()) {
			Arrays.stream(client.setList()).map(Set::getCode).forEach(code -> codes.add(code));
		} else {
			Set set = client.set(codes.pop());
			try (SqlSession session = factory.openSession()) {
				ImporterDao dao = session.getMapper(ImporterDao.class);
				dao.edition(set);
				for (Card card : set.getCards()) {
					dao.card(card);
					dao.declinaison(card, set);
				}
				session.commit();
			}
		}
	}

}
