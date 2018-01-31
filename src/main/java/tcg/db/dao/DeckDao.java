package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Deck;

public interface DeckDao {

	@Select("SELECT * FROM deck WHERE user = #{user}")
	List<Deck> readAll(String user);

}
