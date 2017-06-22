package tcg.mtgjson;

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

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;

	@Scheduled(fixedRate = 10 * 60 * 1000)
	void doImport() {
		try (SqlSession session = factory.openSession()) {
			Set[] sets = client.allSet();
			for (Set set : sets) {
				importSets(session, set);
				session.commit();
			}
		}
	}

	private void importSets(SqlSession session, Set set) {
		ImporterDao dao = session.getMapper(ImporterDao.class);
		dao.edition(set);
		for (Card card : set.getCards()) {
			dao.card(card);
			dao.declinaison(card, set);
		}
	}

}
