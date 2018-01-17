package tcg.price.goldfish;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;

public class MtgGoldFishCrawler {

	public CardPrice price() throws IOException {
		CardPrice price = new CardPrice();
		price.setSource(CardPriceSource.MtgGoldFishPaper);
		price.setDate(new Date());
		String link = "https://www.mtggoldfish.com/price/Conflux/Path to Exile#paper".replace(' ',
				'+');
		Document document = Jsoup.connect(link).get();
		Element e = document.select("div.price-box.paper .price-box-price").first();
		if (e != null) {
			price.setPrice(new BigDecimal(e.text()));
		}
		return price;
	}

}
