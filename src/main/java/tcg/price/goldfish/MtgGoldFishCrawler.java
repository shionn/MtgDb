package tcg.price.goldfish;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;
import tcg.db.dbo.Edition.Foil;

@Component
public class MtgGoldFishCrawler {
	private Logger logger = LoggerFactory.getLogger(MtgGoldFishCrawler.class);

	private static final List<String> IGNORED_EDITION = Arrays.asList("CEI");

	public List<CardPrice> price(Card card) {
		List<CardPrice> prices = new ArrayList<CardPrice>();
		prices.addAll(crawl(card, CardPriceSource.MtgGoldFishPaper, CardPriceSource.MtgGoldFishTx, buildUrl(card)));
		if (card.getEdition().getFoil() != Foil.nofoil) {
			prices.addAll(crawl(card, CardPriceSource.MtgGoldFishFoilPaper, CardPriceSource.MtgGoldFishFoilTx,
					buildFoilUrl(card)));
		}
		return prices;
	}

	private List<CardPrice> crawl(Card card, CardPriceSource paperType, CardPriceSource onlineType, String link) {
		CardPrice paper = retrieve(card, paperType);
		CardPrice online = retrieve(card, onlineType);
		try {
			paper.setLink(link);
			online.setLink(link);

			if (!isIgnored(card)) {
				Document document = Jsoup.connect(link).get();
				Element e = document.select("div.price-box.paper .price-box-price").first();
				if (e != null)
					try {
						paper.setPrice(new BigDecimal(e.text().replaceAll(",", "")));
						paper.setPriceDate(new Date());
					} catch (NumberFormatException ex) {
						logger.warn("can't parse " + e.text(), ex);
					}
				e = document.select("div.price-box.online .price-box-price").first();
				if (e != null)
					try {
						online.setPrice(new BigDecimal(e.text().replaceAll(",", "")));
						online.setPriceDate(new Date());
					} catch (NumberFormatException ex) {
						logger.warn("can't parse " + e.text(), ex);
					}
			}
		} catch (IOException e) {
			logger.error("Can't crawl price : ", e);
		}
		return Arrays.asList(paper, online);
	}

	private boolean isIgnored(Card card) {
		return IGNORED_EDITION.contains(card.getEdition().getCode());
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

	private String buildUrl(Card card) {
		String url = "https://www.mtggoldfish.com/price/" + formatEdition(card) + "/" + formatName(card) + "#paper";
		return url.replace(' ', '+').replaceAll("\\+\\+", "+");
	}

	private String buildFoilUrl(Card card) {
		String url = "https://www.mtggoldfish.com/price/" + formatEdition(card) + ":Foil/" + formatName(card)
				+ "#paper";
		return url.replace(' ', '+').replaceAll("\\+\\+", "+");
	}

	private String formatEdition(Card card) {
		String editionName = card.getEdition().getGoldfishName();
		if (editionName == null) {
			editionName = card.getEdition().getName();
		}
		return editionName.replaceAll("[:.',]", "");
	}

	private String formatName(Card card) {
		return card.getName().replaceAll("[\".,]", " ").replaceAll("'", "");
	}

}
