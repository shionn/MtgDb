package tcg.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SignInDao {

	@Select("SELECT count(email) FROM user WHERE email = #{email} ")
	boolean exist(String email);

	@Insert("INSERT INTO user (email, password, activated) VALUES ( #{email}, #{pass}, false )")
	int register(@Param("email") String email, @Param("pass") String password);

	@Update("UPDATE user SET activated = true WHERE email = #{email}")
	int activate(String email);

}
