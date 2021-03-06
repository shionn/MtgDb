package tcg.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckType;
import tcg.db.dbo.DeckView;

public interface DeckEditDao {

	@Select("SELECT count(*) FROM deck WHERE user = #{user} AND id = #{deck}")
	boolean checkAllow(@Param("user") String user, @Param("deck") int deck);

	@Insert("INSERT INTO deck_entry (deck, card, qty, foil, category, tag) " //
			+ "VALUES (#{deck}, #{card.id}, #{qty}, #{foil}, #{category}, #{tag}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "qty = qty + #{qty}, tag = #{tag} "//
	)
	int updateEntry(DeckEntry entry);

	@Delete("DELETE FROM deck_entry WHERE deck = #{deck} AND qty = 0")
	int deleteZero(int deck);

	@Update("UPDATE deck SET updated = NOW() WHERE id = #{deck}")
	int updateDeck(int deck);

	@Update("UPDATE deck SET view = #{view} WHERE id = #{deck}")
	int updateView(@Param("deck") int deck, @Param("view") DeckView view);

	@Update("UPDATE deck SET updated = NOW(), name = #{name}, type = #{type} "
			+ "WHERE id = #{deck}")
	void udateDeckBase(@Param("deck") int deck, @Param("name") String name,
			@Param("type") DeckType type);


}
