package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tcg.db.dbo.DeckEntry;

public interface DeckEditDao {

	@Select("SELECT count(*) FROM deck WHERE user = #{user} AND id = #{deck}")
	boolean checkAllow(@Param("user") String user, @Param("deck") int deck);

	@Insert("INSERT INTO deck_entry (deck, card, qty, foil, category) " //
			+ "VALUES (#{deck}, #{card.id}, #{qty}, #{foil}, #{category}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "qty = qty + #{qty} "//
	)
	int updateEntry(DeckEntry entry);

	@Delete("DELETE FROM deck_entry " //
			+ "WHERE deck = #{deck} AND card = #{card.id} " //
			+ "AND foil = #{foil} AND category = #{category}")
	void deleteEntry(DeckEntry entry);

	@Delete("DELETE FROM deck_entry WHERE deck = #{deck} AND qty = 0")
	int deleteZero(int deck);

	@Update("UPDATE deck SET updated = NOW() WHERE id = #{deck}")
	int updateDeck(int deck);

	@Select("SELECT IFNULL(sum(qty),0) FROM deck_entry " //
			+ "WHERE deck = #{deck} AND card = #{card.id} " //
			+ "AND foil = #{foil} AND category = #{category}")
	int count(DeckEntry entry);

	@Select("SELECT * FROM deck_entry WHERE deck = #{deck} AND card = #{card}")
	@Results({
			@Result(column = "card", property = "card", one = @One(select = "tcg.db.dao.CardDao.read")) })
	List<DeckEntry> readEntries(@Param("deck") int deck, @Param("card") String card);


}
