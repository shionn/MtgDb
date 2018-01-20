package tcg.price.mkm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

	public List<CardPrice> price(Card card) {
		CardPrice paper = retrieve(card, CardPriceSource.mkm);
		CardPrice foil = retrieve(card, CardPriceSource.mkmFoil);
		try {
			String url = buildUrl(card);
			paper.setLink(url);
			if (!isIgnored(card)) {
				Document document = Jsoup.connect(url).get();
				Element e = document.select("table.availTable tr.row_Even.row_2 td.outerRight").first();
				if (e != null) {
					paper.setPrice(
							new BigDecimal(e.text().replaceAll("[^,0-9]", "").replace(',', '.')));
					paper.setPriceDate(new Date());
				}
				e = document.select("table.availTable tr.row_Even.row_4 td.outerRight").first();
				if (e != null) {
					foil.setPrice(
							new BigDecimal(e.text().replaceAll("[^,0-9]", "").replace(',', '.')));
					foil.setPriceDate(new Date());
				}
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
		}
		return Arrays.asList(paper, foil);
	}

	private boolean isIgnored(Card card) {
		return card.getEdition().isOnlineOnly();
	}

	private CardPrice retrieve(Card card, CardPriceSource source) {
		CardPrice price = card.getPrice(source);
		if (price == null) {
			price = new CardPrice();
			price.setSource(source);
			price.setId(card.getId());
		}
		price.setUpdateDate(new Date());
		return price;
	}

	private String buildUrl(Card card) throws UnsupportedEncodingException {

		String edition = card.getEdition().getMkmName();
		if (edition == null) {
			edition = card.getEdition().getName();
		}
		return "https://www.cardmarket.com/en/Magic/Products/Singles/"
				+ URLEncoder.encode(edition,"UTF-8") + "/"
				+ URLEncoder.encode(card.getName(), "UTF-8");
	}

}
