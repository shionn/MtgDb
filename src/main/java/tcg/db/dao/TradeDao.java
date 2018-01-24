package tcg.db.dao;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import tcg.db.dao.frag.CardFragDao;
import tcg.db.dbo.Card;

public interface TradeDao extends CardFragDao {

	@Select("SELECT * FROM card WHERE id = #{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "prices", many = @Many(select = "readPrices")),
			@Result(column = "edition", property = "edition", one = @One(select = "readEdition")) })
	Card readCard(String id);

}
