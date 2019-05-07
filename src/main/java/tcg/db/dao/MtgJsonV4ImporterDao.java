package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.GameLegality;
import tcg.mtgjson.v4.api.Language;
import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonRuling;
import tcg.mtgjson.v4.api.MtgJsonSet;

public interface MtgJsonV4ImporterDao {

	@Insert("INSERT INTO edition (code, name, block, parent_code, release_date, mkm_name, mkm_id, online_only, "
			+ "keyrune_code, type) "
			+ "VALUES (#{code}, #{name}, #{block}, #{parentCode}, #{releaseDate}, #{mcmName}, #{mcmId}, #{onlineOnly}, "
			+ "#{keyruneCode}, #{type} ) " //
			+ "ON DUPLICATE KEY UPDATE "
			+ "name = #{name}, block = #{block}, parent_code = #{parentCode}, release_date = #{releaseDate}, mkm_name = #{mcmName}, "
			+ "mkm_id = #{mcmId}, online_only = #{onlineOnly}, keyrune_code = #{keyruneCode}, "
			+ "type = #{type}, " //
			+ "update_date = NOW() ")
	int updateEdition(MtgJsonSet set);

	@Insert("INSERT INTO card (id, card, scryfall_id, edition, foil, number, side,  "
			+ "name, text, flavor, original_text, artist, type, original_type, " //
			+ "mana_cost, cmc, colors, color_identity, " //
			+ "layout, rarity, reserved, " //
			+ "power, toughness, loyalty, update_date) " //
			+ "VALUES (#{c.uuid}, #{c.nameId}, #{c.scryfallId}, #{s.code}, #{c.foil}, #{c.number}, #{c.side}, "
			+ "#{c.name}, #{c.text}, #{c.flavorText}, #{c.originalText}, #{c.artist}, #{c.type}, #{c.originalType}, "
			+ "#{c.manaCost}, #{c.convertedManaCost}, #{c.colorsId}, #{c.colorIdentityId}, "
			+ "#{c.layout}, #{c.rarity}, #{c.reserved}, " //
			+ "#{c.power}, #{c.toughness}, #{c.loyalty}, NOW()) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "card = #{c.nameId}, scryfall_id = #{c.scryfallId}, " //
			+ "edition = #{s.code}, foil = #{c.foil}, number = #{c.number}, side = #{c.side}, "
			+ "name = #{c.name}, text = #{c.text}, flavor = #{c.flavorText}, original_text = #{c.originalText}, artist = #{c.artist}, type = #{c.type}, original_type = #{c.originalType}, "
			+ "mana_cost = #{c.manaCost}, cmc = #{c.convertedManaCost}, colors = #{c.colorsId}, color_identity = #{c.colorIdentityId}, "
			+ "layout = #{c.layout}, rarity = #{c.rarity}, reserved = #{c.reserved}, "
			+ "power = #{c.power}, toughness = #{c.toughness}, loyalty = #{c.loyalty}, update_date = NOW() ")
	int updateCard(@Param("c") MtgJsonCard card, @Param("s") MtgJsonSet set);

	@Insert("INSERT INTO card_lang (id, lang, name) " //
			+ "VALUES (#{id}, #{lg}, #{name}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "lang = #{lg}, name = #{name}")
	int updateCardLang(@Param("id") String uuid, @Param("name") String name,
			@Param("lg") Language lg);

	@Delete("DELETE FROM card_type WHERE id = #{id}")
	int deleteTypes(String id);

	@Insert("INSERT INTO card_type (id, type, value) "//
			+ "VALUES (#{c.uuid}, #{t}, #{v}) ")
	int insertType(@Param("c") MtgJsonCard card, @Param("t") CardTypeClass type,
			@Param("v") String value);

	@Delete("DELETE FROM card_rule WHERE id = #{id}")
	int deleteRules(String id);

	@Insert("INSERT INTO card_rule (id, created, rule) "//
			+ "VALUES (#{c.uuid}, #{r.date}, #{r.text}) ")
	int insertRule(@Param("c") MtgJsonCard card, @Param("r") MtgJsonRuling rule);

	@Delete("DELETE FROM card_legality WHERE id = #{id}")
	int deleteLegality(String id);

	@Insert("INSERT INTO card_legality (id, format, legality) "//
			+ "VALUES (#{id}, #{f}, #{l}) ")
	int insertLegality(@Param("id") String uuid, @Param("f") String format,
			@Param("l") GameLegality legality);

	@Delete("DELETE FROM card_assistance WHERE id = #{id}")
	int deleteAssistance(String id);

	@Insert("INSERT INTO card_assistance (id, lang, name) "//
			+ "VALUES (#{c}, #{l}, #{a}) ")
	int insertAssistance(@Param("c") String id, @Param("l") String lang,
			@Param("a") String assistance);

	@Insert("UPDATE card SET link_card = #{id2} WHERE id = #{id1}")
	int updateLinkCard(@Param("id1") String uuid1, @Param("id2") String uuid2);

	// en java on filtre sur ceux qui contienne pas de -
	@Select("SELECT id FROM card WHERE name = #{c.name} AND edition = #{s.code}")
	List<String> readOldIds(@Param("c") MtgJsonCard card, @Param("s") MtgJsonSet set);

	@Update("UPDATE deck_entry SET card = #{new} WHERE card = #{old}")
	int updateDeckOldCardRef(@Param("old") String oldId, @Param("new") String uuid);

	@Delete("DELETE FROM card_price WHERE id = #{id}")
	int deleteCardPrice(String oldId);

	@Delete("DELETE FROM card_lang WHERE id = #{id}")
	int deleteCardLang(String oldId);

	@Update("UPDATE card SET link_card = NULL WHERE link_card = #{id}")
	int unlinkCard(String oldId);

	@Delete("DELETE FROM card WHERE id = #{id}")
	int deleteCard(String oldId);


}
