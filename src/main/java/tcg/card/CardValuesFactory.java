package tcg.card;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import tcg.db.dao.CardValueDomainDao;
import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.Edition;

@Component
public class CardValuesFactory {

	@Autowired
	private SqlSessionFactory factory;

	@Bean(name = "AllTypes")
	@SessionScope
	public List<String> types() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).types(CardTypeClass.Type);
		}
	}

	@Bean(name = "AllSubTypes")
	@SessionScope
	public List<String> subTypes() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).types(CardTypeClass.SubType);
		}
	}

	@Bean(name = "AllSuperTypes")
	@SessionScope
	public List<String> superTypes() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).types(CardTypeClass.SuperType);
		}
	}

	@Bean(name = "AllKeyWords")
	@SessionScope
	public List<String> keyWords() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).keywords();
		}
	}

	@Bean(name = "AllEditions")
	@SessionScope
	public List<Edition> editions() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).editions();
		}
	}

	@Bean(name = "AllFormats")
	@SessionScope
	public List<String> formats() {
		try (SqlSession session = factory.openSession()) {
			return session.getMapper(CardValueDomainDao.class).formats();
		}
	}

}
