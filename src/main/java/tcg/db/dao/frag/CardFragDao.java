package tcg.db.dao.frag;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.CardLang;

public interface CardFragDao {
	@Select("SELECT lang, name " //
			+ "FROM card_lang " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY lang ASC")
	List<CardLang> readLangs(String id);

}
