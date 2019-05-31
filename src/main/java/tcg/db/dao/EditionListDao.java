package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Edition;

public interface EditionListDao {

	@Select("SELECT * FROM edition ORDER BY release_date DESC")
	List<Edition> listEditions();

}
