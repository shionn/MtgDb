package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface CardKeywordDao {

	@Select("SELECT keyword FROM card_keyword ORDER BY keyword")
	List<String> list();

}
