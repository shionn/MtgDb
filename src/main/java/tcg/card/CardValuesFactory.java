package tcg.card;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import tcg.db.dao.CardKeywordDao;
import tcg.db.dao.CardTypeDao;
import tcg.db.dao.EditionDao;
import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.Edition;

@Component
public class CardValuesFactory {

	@Autowired
	private SqlSessionFactory factory;

	@Bean(name = "Type")
	@ApplicationScope
	public List<String> types() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardTypeDao.class).list(CardTypeClass.Type);
		}
	}

	@Bean(name = "SubType")
	@ApplicationScope
	public List<String> subTypes() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardTypeDao.class).list(CardTypeClass.SubType);
		}
	}

	@Bean(name = "SuperType")
	@ApplicationScope
	public List<String> superTypes() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardTypeDao.class).list(CardTypeClass.SuperType);
		}
	}

	@Bean(name = "KeyWord")
	@ApplicationScope
	public List<String> keyWords() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardKeywordDao.class).list();
		}
	}

	@Bean(name = "Editions")
	@ApplicationScope
	public List<Edition> editions() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(EditionDao.class).list();
		}
	}

}
