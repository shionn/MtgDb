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

	private static final List<String> USE_MKM_NAME = Arrays.asList("CMD");
	private static final List<String> IGNORED_EDITION = Arrays.asList("CEI");

	public List<CardPrice> price(Card card) {
		CardPrice paper = retrieve(card, CardPriceSource.MtgGoldFishPaper);
		CardPrice online = retrieve(card, CardPriceSource.MtgGoldFishTx);
		try {
			String link = buildUrl(card);
			paper.setLink(link);
			online.setLink(link);

			if (!isIgnored(card)) {
				Document document = Jsoup.connect(link).get();
				Element e = document.select("div.price-box.paper .price-box-price").first();
				if (e != null) {
					paper.setPrice(new BigDecimal(e.text()));
					paper.setPriceDate(new Date());
				}
				e = document.select("div.price-box.online .price-box-price").first();
				if (e != null) {
					online.setPrice(new BigDecimal(e.text()));
					online.setPriceDate(new Date());
				}
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MtgGoldFishCrawler.class).error("Can't crawl price : ", e);
		}
		CardPrice paperFoil = retrieve(card, CardPriceSource.MtgGoldFishFoilPaper);
		CardPrice onlineFoil = retrieve(card, CardPriceSource.MtgGoldFishFoilTx);
		try {
			String link = buildFoilUrl(card);
			paperFoil.setLink(link);
			onlineFoil.setLink(link);

			if (!isIgnored(card)) {
				Document document = Jsoup.connect(link).get();
				Element e = document.select("div.price-box.paper .price-box-price").first();
				if (e != null) {
					paperFoil.setPrice(new BigDecimal(e.text()));
					paperFoil.setPriceDate(new Date());
				}
				e = document.select("div.price-box.online .price-box-price").first();
				if (e != null) {
					onlineFoil.setPrice(new BigDecimal(e.text()));
					paperFoil.setPriceDate(new Date());
				}
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MtgGoldFishCrawler.class).error("Can't crawl price : ", e);
		}
		return Arrays.asList(paper, online, paperFoil, onlineFoil);
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
		return url.replace(' ', '+');
	}

	private String buildFoilUrl(Card card) {
		String url = "https://www.mtggoldfish.com/price/" + formatEdition(card) + ":Foil/" + formatName(card)
				+ "#paper";
		return url.replace(' ', '+');
	}

	private String formatEdition(Card card) {
		String editionName = card.getEdition().getName();
		if (USE_MKM_NAME.contains(card.getEdition().getCode())) {
			editionName = card.getEdition().getMkmName();
		}
		return editionName.replaceAll("[:.']", "");
	}

	private String formatName(Card card) {
		return card.getName().replaceAll("[\".]", " ");
	}

}
