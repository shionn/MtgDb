package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Deck;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckType;

public interface DeckDao {

	@Select("SELECT * FROM deck WHERE user = #{user} ORDER BY updated DESC")
	List<Deck> readAll(String user);

	@Insert("INSERT INTO deck(name, user, type, created, updated) "
			+ "VALUES(#{name}, #{user}, #{type}, NOW(), NOW())")
	int create(@Param("name") String name, @Param("type") DeckType type, @Param("user") String user);

	@Select("SELECT * FROM deck WHERE id = #{id}")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "id", property = "cards", many = @Many(select = "readEntries"))
	})
	Deck readDeck(int id);

	@Select("SELECT * FROM deck WHERE id = #{id}")
	Deck readDeckBase(int id);

	@Select("SELECT * FROM deck_entry WHERE deck = #{id}")
	@Results({
			@Result(column = "card", property = "card", one = @One(select = "tcg.db.dao.CardDao.read")) })
	List<DeckEntry> readEntries(int id);

	@Select("SELECT * FROM deck_entry WHERE deck = #{deck} AND card = #{card}")
	List<DeckEntry> readCardEntries(@Param("deck") int deck, @Param("card") String card);

	@Select("SELECT * FROM deck_entry WHERE deck = #{deck} AND card = #{card.id} "
			+ "AND category = #{category} AND foil = #{foil}")
	@Results({
			@Result(column = "card", property = "card", one = @One(select = "tcg.db.dao.CardDao.read")) })
	DeckEntry readEntry(DeckEntry entry);

}
