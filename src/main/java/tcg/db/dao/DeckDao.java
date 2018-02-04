package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Deck;
import tcg.db.dbo.DeckType;

public interface DeckDao {

	@Select("SELECT * FROM deck WHERE user = #{user}")
	List<Deck> readAll(String user);

	@Insert("INSERT INTO deck(name, user, type) VALUES(#{name}, #{user}, #{type})")
	int create(@Param("name") String name, @Param("type") DeckType type, @Param("user") String user);

}
