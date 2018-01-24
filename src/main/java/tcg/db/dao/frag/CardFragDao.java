package tcg.db.dao.frag;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.CardLang;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.Edition;

public interface CardFragDao {
	@Select("SELECT lang, name " //
			+ "FROM card_lang " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY lang ASC")
	List<CardLang> readLangs(String id);

	@Select("SELECT * " //
			+ "FROM card_price " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY source ASC")
	List<CardPrice> readPrices(String id);

	@Select("SELECT * FROM edition WHERE code = #{code}")
	Edition readEdition(String code);

}
