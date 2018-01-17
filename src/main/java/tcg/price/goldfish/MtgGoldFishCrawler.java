package tcg.price.goldfish;

import java.io.IOException;
import java.math.BigDecimal;
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
public class MtgGoldFishCrawler {

	public List<CardPrice> price(Card card) {
		CardPrice paper = retrieve(card, CardPriceSource.MtgGoldFishPaper);
		CardPrice online = retrieve(card, CardPriceSource.MtgGoldFishTx);
		try {
			String link = buildUrl(card);

			Document document = Jsoup.connect(link).get();
			Element e = document.select("div.price-box.paper .price-box-price").first();
			if (e != null) {
				paper.setPrice(new BigDecimal(e.text()));
			}
			e = document.select("div.price-box.online .price-box-price").first();
			if (e != null) {
				online.setPrice(new BigDecimal(e.text()));
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MtgGoldFishCrawler.class).error("Can't crawl price : ", e);
		}
		return Arrays.asList(paper, online);
	}

	private CardPrice retrieve(Card card, CardPriceSource source) {
		CardPrice paper = card.getPrice(source);
		if (paper == null) {
			paper = new CardPrice();
			paper.setSource(source);
			paper.setDate(new Date());
			paper.setId(card.getId());
		}
		return paper;
	}

	private String buildUrl(Card card) {
		String url = "https://www.mtggoldfish.com/price/" + (card.getEdition().getName().replaceAll(":", "")) + "/"
				+ formatName(card) + "#paper";
		return url.replace(' ', '+');
	}

	private String formatName(Card card) {
		return card.getName().replaceAll("[\".]", " ");
	}

}
