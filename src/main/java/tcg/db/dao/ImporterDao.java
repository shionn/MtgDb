package tcg.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.ForeignName;
import tcg.mtgjson.api.Set;

public interface ImporterDao {

	/**
	 * mkm_name n'est pas mis à jour volontairement pour permmetre l'edition
	 * manuellement
	 */
	@Insert("INSERT INTO edition (code, mci_code, name, release_date, mkm_name, mkm_id, online_only) "
			+ "VALUES (#{code}, #{magicCardsInfoCode}, #{name}, #{releaseDate}, #{mkm_name}, #{mkm_id}, #{onlineOnly}) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "mci_code = #{magicCardsInfoCode}, release_date = #{releaseDate}, "
			+ "name = #{name}, mkm_id = #{mkm_id}, online_only = #{onlineOnly} ")
	int edition(Set set);

	@Insert("INSERT INTO card (id, card, edition, number, mci_number, name, text, flavor, type, mana_cost, " //
			+ "cmc, multiverse_id) "
			+ "VALUES (#{c.id}, #{c.nameId}, #{s.code}, #{c.number}, #{c.mciNumber}, #{c.name}, #{c.text}, "
			+ "#{c.flavor}, #{c.type}, #{c.manaCost}, #{c.cmc}, #{c.multiverseid}) "
			+ "ON DUPLICATE KEY UPDATE " //
			+ "card = #{c.nameId}, edition = #{s.code}, number = #{c.number}, mci_number = #{c.mciNumber}, "
			+ "name = #{c.name}, text = #{c.text}, flavor = #{c.flavor}, type = #{c.type}, mana_cost = #{c.manaCost}, "
			+ "cmc = #{c.cmc}, multiverse_id = #{c.multiverseid}")
	int card(@Param("c") Card card, @Param("s") Set set);

	@Insert("INSERT INTO card_lang (id, lang, multiverse_id, name) " //
			+ "VALUES (#{c.id}, #{n.language}, #{n.multiverseid}, #{n.name}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "lang = #{n.language}, multiverse_id = #{n.multiverseid}, name = #{n.name}")
	int cardName(@Param("n") ForeignName name, @Param("c") Card card);

	@Insert("INSERT INTO declinaison (card, edition, rarity) "
			+ "VALUES ( "
			+ "(SELECT id FROM card WHERE ref = #{card.imageName}), "
			+ "(SELECT id FROM edition WHERE code = #{set.code}), "
			+ "rarity = #{card.rarity} ) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "rarity = #{card.rarity} ")
	void declinaison(@Param("card")Card card, @Param("set") Set set);

}
