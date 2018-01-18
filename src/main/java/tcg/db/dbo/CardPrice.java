package tcg.db.dbo;

import java.math.BigDecimal;
import java.util.Date;

public class CardPrice {
	private String id;
	private BigDecimal price;
	private CardPriceSource source;
	private Date priceDate;
	private Date updateDate;
	private String link;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public CardPriceSource getSource() {
		return source;
	}

	public void setSource(CardPriceSource source) {
		this.source = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
