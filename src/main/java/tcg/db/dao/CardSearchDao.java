package tcg.db.dao;

import org.apache.ibatis.annotations.Select;

public interface CardSearchDao {

	@Select("SELECT id FROM card WHERE name LIKE #{name} ORDER BY name, edition, card LIMIT 1")
	String findFirstId(String name);

}
