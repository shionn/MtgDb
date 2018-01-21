package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import tcg.db.dao.frag.CardFragDao;
import tcg.db.dbo.Card;
import tcg.search.Filter;

public interface CardSearchDao extends CardFragDao {

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

	@SelectProvider(type = AdvancedSearchQueryAdapter.class, method = "search")
	List<Card> search(@Param("types") List<Filter> filters);

}
