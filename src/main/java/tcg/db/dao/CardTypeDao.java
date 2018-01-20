package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.CardTypeClass;

public interface CardTypeDao {

	@Select("SELECT DISTINCT(value) FROM card_type WHERE type = #{clazz} ORDER BY value")
	List<String> list(CardTypeClass clazz);

}
