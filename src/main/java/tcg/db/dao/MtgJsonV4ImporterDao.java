package tcg.db.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonSet;

public interface MtgJsonV4ImporterDao {

	@Insert("INSERT INTO edition (code, name, release_date, mkm_name, mkm_id, online_only, keyrune_code) "
			+ "VALUES (#{code}, #{name}, #{releaseDate}, #{mcmName}, #{mcmId}, #{onlineOnly}, #{keyruneCode}) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "name = #{name}, release_date = #{releaseDate}, mkm_name = #{mcmName}, "
			+ "mkm_id = #{mcmId}, online_only = #{onlineOnly}, keyrune_code = #{keyruneCode}, "
			+ "update_date = NOW() ")
	int updateEdition(MtgJsonSet set);

	@Insert("INSERT INTO card (id, card, edition, number, "
			+ "name, text, flavor, original_text, artist, type, original_type, " //
			+ "mana_cost, cmc, colors, color_identity, " //
			+ "layout, rarity, reserved, " //
			+ "power, toughness, loyalty) " //
			+ "VALUES (#{c.uuid}, #{c.nameId}, #{s.code}, #{c.number}, "
			+ "#{c.name}, #{c.text}, #{c.flavorText}, #{c.originalText}, #{c.artist}, #{c.type}, #{c.originalType}, "
			+ "#{c.manaCost}, #{c.convertedManaCost}, #{c.colorsId}, #{c.colorIdentityId}, "
			+ "#{c.layout}, #{c.rarity}, #{c.reserved}, " //
			+ "#{c.power}, #{c.toughness}, #{c.loyalty}) " //
			+ "ON DUPLICATE KEY UPDATE " //
			+ "card = #{c.nameId}, edition = #{s.code}, number = #{c.number}, "
			+ "name = #{c.name}, text = #{c.text}, flavor = #{c.flavorText}, original_text = #{c.originalText}, artist = #{c.artist}, type = #{c.type}, original_type = #{c.originalType}, "
			+ "mana_cost = #{c.manaCost}, cmc = #{c.convertedManaCost}, colors = #{c.colorsId}, color_identity = #{c.colorIdentityId}, "
			+ "layout = #{c.layout}, rarity = #{c.rarity}, source = #{c.source}, reserved = #{c.reserved}, "
			+ "power = #{c.power}, toughness = #{c.toughness}, loyalty = #{c.loyalty}")
	int updateCard(@Param("c") MtgJsonCard card, @Param("s") MtgJsonSet set);

}
