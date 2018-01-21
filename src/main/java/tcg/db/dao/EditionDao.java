package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Edition;

public interface EditionDao {

	@Select("SELECT * FROM edition ORDER BY name")
	List<Edition> list();

}
