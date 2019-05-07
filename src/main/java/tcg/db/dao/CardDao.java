package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dao.frag.CardFragDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrinting;
import tcg.db.dbo.CardRule;
import tcg.db.dbo.Legality;

public interface CardDao extends CardFragDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "prices", many = @Many(select = "readPrices")),
			@Result(column = "id", property = "langs", many = @Many(select = "readLangs")),
			@Result(column = "id", property = "rules", many = @Many(select = "readRules")),
			@Result(column = "id", property = "legalities", many = @Many(select = "readLegalities")),
			@Result(column = "link_card", property = "linkCardId"),
			@Result(column = "link_card", property = "linkCard", one = @One(select = "read")),
			@Result(column = "card", property = "card"),
			@Result(column = "card", property = "printings", many = @Many(select = "readPrintings")),
			@Result(column = "edition", property = "edition", one = @One(select = "readEdition")) })
	Card read(String id);

	@Select("SELECT c.id, e.code, e.name, e.keyrune_code, e.type " //
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.card = #{card} " //
			+ "ORDER BY e.release_date ASC")
	@Results({ @Result(column = "code", property = "edition.code"),
			@Result(column = "name", property = "edition.name"),
			@Result(column = "type", property = "edition.type"),
			@Result(column = "keyrune_code", property = "edition.keyruneCode") })
	List<CardPrinting> readPrintings(String card);

	@Select("SELECT * " //
			+ "FROM card_rule " //
			+ "WHERE id = #{id}")
	List<CardRule> readRules(String id);

	@Select("SELECT * " //
			+ "FROM card_legality " //
			+ "WHERE id = #{id}")
	List<Legality> readLegalities(String id);

	@Select("SELECT edition, number, side, scryfall_id FROM card WHERE id = #{id} ")
	@Results({ @Result(column = "edition", property = "edition.code") })
	Card readImgData(@Param("id") String id);

}
