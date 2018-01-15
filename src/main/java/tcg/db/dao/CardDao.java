package tcg.db.dao;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Card;

public interface CardDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	Card read(String id);

}
