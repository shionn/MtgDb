package tcg.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dao.frag.CardFragDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPrinting;
import tcg.db.dbo.Edition;

public interface CardDao extends CardFragDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "prices", many = @Many(select = "readPrices")),
			@Result(column = "id", property = "langs", many = @Many(select = "readLangs")),
			@Result(column = "card", property = "printings", many = @Many(select = "readPrintings")),
			@Result(column = "edition", property = "edition", one = @One(select = "readEdition")) })
	Card read(String id);

	@Select("SELECT c.id, e.code, e.name " //
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.card = #{card} " //
			+ "ORDER BY e.release_date ASC")
	@Results({ @Result(column = "code", property = "edition.code"),
			@Result(column = "name", property = "edition.name") })
	List<CardPrinting> readPrintings(String card);

	@Select("SELECT * " //
			+ "FROM card_price " //
			+ "WHERE id = #{id} " //
			+ "ORDER BY source ASC")
	List<CardPrice> readPrices(String id);

	@Select("SELECT * FROM edition WHERE code = #{code}")
	Edition readEdition(String code);

	@Select("SELECT CONCAT(e.mci_code, '/', IFNULL(c.mci_number,c.number) ) "//
			+ "FROM card AS c " //
			+ "LEFT JOIN edition AS e ON c.edition = e.code " //
			+ "WHERE c.id = #{id} ")
	String readImg(String id);

	@Insert("INSERT INTO card_price (id, source, price, update_date, price_date, link ) "
			+ "VALUES (#{id}, #{source}, #{price}, #{updateDate}, #{priceDate}, #{link} ) "
			+ "ON DUPLICATE KEY UPDATE source = #{source}, update_date = #{updateDate}, price_date = #{priceDate}, "
			+ "link = #{link} ")
	int price(CardPrice price);

}
