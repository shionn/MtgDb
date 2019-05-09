package tcg.db.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.User;

public interface AuthenticationDao {

	@Select("SELECT  email, password, admin " //
			+ "FROM user " //
			+ "WHERE email = #{email}")
	User readUser(@Param("email") String email);

}
