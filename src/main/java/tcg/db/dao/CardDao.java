package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrinting;

public interface CardDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	@Results({ @Result(column = "card", property = "printings", many = @Many(select = "readPrintings")) })
	Card read(String id);

	@Select("SELECT c.id, e.code AS edition " //
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.card = #{card} " //
			+ "ORDER BY e.release_date ASC")
	List<CardPrinting> readPrintings(String card);

}
