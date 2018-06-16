package tcg.db.dao;

import org.apache.ibatis.annotations.Insert;

import tcg.db.dbo.CardPrice;

public interface CardPriceDao {
	@Insert("INSERT INTO card_price (id, source, price, update_date, price_date, link, error ) "
			+ "VALUES (#{id}, #{source}, #{price}, #{updateDate}, #{priceDate}, #{link}, #{error} ) "
			+ "ON DUPLICATE KEY UPDATE source = #{source}, update_date = #{updateDate}, price_date = #{priceDate}, "
			+ "link = #{link}, error = #{error}, price = #{price} ")
	int price(CardPrice price);

}
