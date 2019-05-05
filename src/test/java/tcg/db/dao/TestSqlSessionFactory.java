package tcg.db.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestSqlSessionFactory {

	private org.apache.ibatis.session.SqlSessionFactory factory = build();

	private org.apache.ibatis.session.SqlSessionFactory build() {
		try (InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("mybatis.xml")) {
			return new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public SqlSession openSession() {
		return factory.openSession();
	}

}
