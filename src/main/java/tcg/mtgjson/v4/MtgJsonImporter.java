package tcg.mtgjson.v4;

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

import tcg.db.dao.MtgJsonV4ImporterDao;
import tcg.mtgjson.v3.EditionImporter;
import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonSet;

@Component
public class MtgJsonImporter {
	// private static final int INTERVAL = 5 * 60 * 1000;
	private static final int INTERVAL = 5;
	private Logger logger = LoggerFactory.getLogger(EditionImporter.class);

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;

	private Deque<String> codes = new LinkedList<>();

	@Scheduled(fixedRate = INTERVAL)
	void doImport() {
		if (codes.isEmpty()) {
			Arrays.stream(client.setList()).map(MtgJsonSet::getCode).forEach(code -> codes.add(code));
			logger.info("Found <" + codes.size() + "> to scan");
		} else {
			MtgJsonSet set = client.set(codes.pop());
			logger.info("Start import set <" + set.getCode() + ">");
			try (SqlSession session = factory.openSession()) {
				MtgJsonV4ImporterDao dao = session.getMapper(MtgJsonV4ImporterDao.class);
				dao.updateEdition(set);
				for (MtgJsonCard card : set.getCards()) {
					dao.updateCard(card, set);
				}

				session.commit();
			}
		}
	}

}
