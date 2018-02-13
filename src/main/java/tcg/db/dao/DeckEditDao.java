package tcg.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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
	int addEntry(DeckEntry entry);

	@Update("UPDATE deck SET updated = NOW() WHERE id = #{deck}")
	int updateDeck(int deck);

	@Select("SELECT qty FROM deck_entry " //
			+ "WHERE deck = #{deck} AND card = #{card.id} " //
			+ "AND foil = #{foil} AND category = #{category} union SELECT 0")
	int count(DeckEntry entry);

	@Delete("DELETE FROM deck_entry WHERE deck = #{deck} AND qty = 0")
	int deleteZero(int currentDeck);

}
