package tcg.mtgjson;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class EditionImporter {

	@Autowired
	private SqlSessionFactory factory;

	@Scheduled(fixedRate = 10 * 60 * 1000)
	void doImport() {
		try (SqlSession session = factory.openSession()) {
		}
	}

}
