package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPrinting;
import tcg.db.dbo.Edition;

public interface CardDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "prices", many = @Many(select = "readPrices")),
			@Result(column = "card", property = "printings", many = @Many(select = "readPrintings")),
			@Result(column = "edition", property = "edition", one = @One(select = "readEdition")) })
	Card read(String id);

	@Select("SELECT c.id, e.code AS edition " //
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.card = #{card} " //
			+ "ORDER BY e.release_date ASC")
	List<CardPrinting> readPrintings(String card);

	@Select("SELECT id, source, price, date " //
			+ "FROM card_price " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY source ASC")
	List<CardPrice> readPrices(String id);

	@Select("SELECT * FROM edition WHERE code = #{code}")
	Edition readEdition(String code);

	@Select("SELECT CONCAT(e.magic_cards_info_code, '/', c.number) "//
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.id = #{id} ")
	String readImg(String id);

	@Insert("INSERT INTO card_price (id, source, price, date ) values (#{id}, #{source}, #{price}, #{date} ) "
			+ "ON DUPLICATE KEY UPDATE source = #{source}, date = #{date}")
	int price(CardPrice price);

}
