package tcg.db.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.Legality;
import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.ForeignName;
import tcg.mtgjson.api.Ruling;
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

	@Insert("INSERT INTO card (id, card, edition, number, mci_number, multiverse_id, "
			+ "name, text, flavor, original_text, artist, type, original_type, " //
			+ "mana_cost, cmc, colors, color_identity, " //
			+ "layout, rarity, source, reserved, " //
			+ "power, toughness, loyalty) " //
			+ "VALUES (#{c.id}, #{c.nameId}, #{s.code}, #{c.number}, #{c.mciNumber}, #{c.multiverseid}, "
			+ "#{c.name}, #{c.text}, #{c.flavor}, #{c.originalText}, #{c.artist}, #{c.type}, #{c.originalType}, "
			+ "#{c.manaCost}, #{c.cmc}, #{c.colorsId}, #{c.colorIdentityId}, "
			+ "#{c.layout}, #{c.rarity}, #{c.source}, #{c.reserved}, " //
			+ "#{c.power}, #{c.toughness}, #{c.loyalty}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "card = #{c.nameId}, edition = #{s.code}, number = #{c.number}, mci_number = #{c.mciNumber}, multiverse_id = #{c.multiverseid}, "
			+ "name = #{c.name}, text = #{c.text}, flavor = #{c.flavor}, original_text = #{c.originalText}, artist = #{c.artist}, type = #{c.type}, original_type = #{c.originalType}, "
			+ "mana_cost = #{c.manaCost}, cmc = #{c.cmc}, colors = #{c.colorsId}, color_identity = #{c.colorIdentityId}, "
			+ "layout = #{c.layout}, rarity = #{c.rarity}, source = #{c.source}, reserved = #{c.reserved}, "
			+ "power = #{c.power}, toughness = #{c.toughness}, loyalty = #{c.loyalty}")
	int card(@Param("c") Card card, @Param("s") Set set);

	@Insert("INSERT INTO card_lang (id, lang, multiverse_id, name) " //
			+ "VALUES (#{c.id}, #{n.language}, #{n.multiverseid}, #{n.name}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "lang = #{n.language}, multiverse_id = #{n.multiverseid}, name = #{n.name}")
	int cardName(@Param("n") ForeignName name, @Param("c") Card card);

	@Delete("DELETE FROM card_type WHERE id = #{id}")
	int deleteTypes(String id);

	@Insert("INSERT INTO card_type (id, type, value) "//
			+ "VALUES (#{c.id}, #{t}, #{v}) ")
	int type(@Param("c") Card card, @Param("t") CardTypeClass type, @Param("v") String value);

	@Delete("DELETE FROM card_rule WHERE id = #{id}")
	int deleteRules(String id);

	@Insert("INSERT INTO card_rule (id, created, rule) "//
			+ "VALUES (#{c.id}, #{r.date}, #{r.text}) ")
	int rule(@Param("c") Card card, @Param("r") Ruling rule);

	@Update("UPDATE card SET link_card = #{l.id} WHERE id = #{c.id}")
	int updateLinkCard(@Param("c") Card card, @Param("l") Card linkCard);

	@Delete("DELETE FROM card_legality WHERE id = #{id}")
	void deleteLegality(String id);

	@Insert("INSERT INTO card_legality (id, format, legality) "//
			+ "VALUES (#{c.id}, #{l.format}, #{l.legality}) ")
	void legality(@Param("c") Card card, @Param("l") Legality legality);

}
