package tcg.price.mkm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;

public class MkmCrawler {

	public CardPrice price(Card card) throws IOException {
		Document document = Jsoup
				.connect(
						"https://www.cardmarket.com/en/Magic/Products/Singles/Battle+for+Zendikar/Ob+Nixilis+Reignited")
				.get();

		CardPrice result = new CardPrice();
		result.setPrice(new BigDecimal(
				document.select("table.availTable tr.row_Even.row_2 td.outerRight").first().text()
						.replaceAll("[^,0-9]", "").replace(',', '.')));
		result.setSource(CardPriceSource.mkm);
		result.setDate(new Date());
		return result;
	}

}
