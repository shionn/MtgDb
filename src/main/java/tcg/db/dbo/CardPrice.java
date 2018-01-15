package tcg.db.dbo;

import java.math.BigDecimal;
import java.util.Date;

public class CardPrice {
	private BigDecimal price;
	private CardPriceSource source;
	private Date date;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CardPriceSource getSource() {
		return source;
	}

	public void setSource(CardPriceSource source) {
		this.source = source;
	}
}
