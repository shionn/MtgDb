package tcg.mtgjson;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tcg.db.dao.ImporterDao;
import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Language;
import tcg.mtgjson.api.Set;

@Component
public class EditionImporter {

	private static final int INTERVAL = 10
			* 60
			* 1000;
	private Logger logger = LoggerFactory.getLogger(EditionImporter.class);

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;

	private Deque<String> codes = new LinkedList<>();

	@Scheduled(fixedRate = INTERVAL)
	void doImport() {
		if (codes.isEmpty()) {
			Arrays.stream(client.setList()).map(Set::getCode)
					// .filter(c -> new Random().nextFloat() < .2)
					.forEach(code -> codes.add(code));
			logger.info("Found <" + codes.size() + "> to scan");
		} else {
			Set set = client.set(codes.pop());
			logger.info("Start import set <" + set.getCode() + ">");
			try (SqlSession session = factory.openSession()) {
				ImporterDao dao = session.getMapper(ImporterDao.class);
				dao.edition(set);
				for (Card card : set.getCards()) {
					dao.card(card, set);
					card.getForeignNames().stream()
							.filter(name -> name.getLanguage() == Language.fr)
							.forEach(name -> dao.cardName(name, card));
				}
				session.commit();
			}
		}
	}

}
