package tcg.db.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import tcg.db.dbo.Card;

public class TestCardDaoTest {


	@Test
	public void testIds() {
		try (SqlSession session = new TestSqlSessionFactory().openSession()) {
			TestCardDao dao = session.getMapper(TestCardDao.class);
			for (Card card : dao.cards()) {
				assertThat(card.getId())
						.isEqualTo(DigestUtils
								.sha256Hex(card.getEdition() + card.getName()));
			}
		}
	}

}
