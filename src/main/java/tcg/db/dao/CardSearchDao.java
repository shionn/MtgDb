package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Card;

public interface CardSearchDao {

	@Select("SELECT id FROM card WHERE name LIKE #{name} ORDER BY name, edition, card LIMIT 1")
	String findFirstId(String name);

	@Select("SELECT id, name, card FROM card WHERE name LIKE #{name} GROUP BY card ORDER BY name")
	List<Card> quick(String name);

}
