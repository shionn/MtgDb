package tcg.db.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class SqlSessionFactory implements org.apache.ibatis.session.SqlSessionFactory {
	private org.apache.ibatis.session.SqlSessionFactory factory = build();

	private org.apache.ibatis.session.SqlSessionFactory build() {
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis.xml")) {
			return new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Bean
	@RequestScope
	public SqlSession openSession() {
		return new SqlSession(factory.openSession());
	}

	@Override
	public SqlSession openSession(boolean autoCommit) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(Connection connection) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(TransactionIsolationLevel level) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(ExecutorType execType) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public SqlSession openSession(ExecutorType execType, Connection connection) {
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override
	public Configuration getConfiguration() {
		return factory.getConfiguration();
	}

}
