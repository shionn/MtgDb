package tcg.price.mkm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;

@Component
public class MkmCrawler {

	public CardPrice price(Card card) {
		CardPrice result = retrieve(card, CardPriceSource.mkm);
		try {
			String url = buildUrl(card);
			result.setLink(url);
			Document document = Jsoup.connect(url).get();
			Element e = document.select("table.availTable tr.row_Even.row_2 td.outerRight").first();
			if (e != null) {
				result.setPrice(new BigDecimal(e.text().replaceAll("[^,0-9]", "").replace(',', '.')));
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
		}
		return result;
	}

	private CardPrice retrieve(Card card, CardPriceSource source) {
		CardPrice price = card.getPrice(source);
		if (price == null) {
			price = new CardPrice();
			price.setSource(source);
			price.setId(card.getId());
		}
		price.setDate(new Date());
		return price;
	}

	private String buildUrl(Card card) {
		String edition = card.getEdition().getMkmName();
		if (edition == null) {
			edition = card.getEdition().getName();
		}
		String url = "https://www.cardmarket.com/en/Magic/Products/Singles/" + edition + "/" + card.getName();
		return url.replace(' ', '+');
	}

}
