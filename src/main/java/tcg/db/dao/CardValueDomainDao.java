package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.Edition;

public interface CardValueDomainDao {

	@Select("SELECT DISTINCT(value) FROM card_type WHERE type = #{clazz} ORDER BY value")
	List<String> types(CardTypeClass clazz);

	@Select("SELECT keyword FROM card_keyword ORDER BY keyword")
	List<String> keywords();

	@Select("SELECT * FROM edition ORDER BY name")
	List<Edition> editions();

	@Select("SELECT distinct(format) FROM card_legality ORDER BY format")
	List<String> formats();

}
