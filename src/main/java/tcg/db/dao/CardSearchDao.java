package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardLang;

public interface CardSearchDao {

	@Select("SELECT id FROM card WHERE name LIKE #{name} ORDER BY name, edition, card LIMIT 1")
	String findFirstId(String name);

	@Select("SELECT c.id, c.name, c.card " //
			+ "FROM card AS c " //
			+ "LEFT JOIN card_lang AS l ON c.id = l.id AND l.lang = 'fr' " //
			+ "WHERE c.name LIKE #{name} "//
			+ "OR l.name LIKE #{name} " //
			+ "GROUP BY c.card " //
			+ "ORDER BY c.name")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "langs", many = @Many(select = "readLangs")) })
	List<Card> quick(String name);

	@Select("SELECT lang, name " //
			+ "FROM card_lang " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY lang ASC")
	List<CardLang> readLangs(String id);

}
