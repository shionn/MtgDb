package tcg.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Set;

public interface ImporterDao {

	@Insert("INSERT INTO edition (code, release_date, name) "
			+ "VALUES (#{code}, #{releaseDate}, #{imageName}) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "release_date = #{releaseDate}, name = #{imageName}")
	int edition(Set set);

	@Insert("INSERT INTO card (ref) "
			+ "VALUES (#{name}) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "ref = #{name}")
	int card(Card card);

	@Insert("INSERT INTO declinaison (card, edition, rarity) "
			+ "VALUES ( "
			+ "(SELECT id FROM card WHERE ref = #{card.imageName}), "
			+ "(SELECT id FROM edition WHERE code = #{set.code}), "
			+ "rarity = #{card.rarity} ) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "rarity = #{card.rarity} ")
	void declinaison(@Param("card")Card card, @Param("set") Set set);

}
