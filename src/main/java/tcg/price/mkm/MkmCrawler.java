package tcg.price.mkm;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;
import tcg.db.dbo.Edition.Foil;

@Component
public class MkmCrawler {

	private static final String ENCODING = "UTF-8";
	private static final List<String> IGNORED_EDITION = Arrays.asList("pPRE");

	public List<CardPrice> price(Card card) {
		List<CardPrice> prices = new ArrayList<>();
		if (!isIgnored(card)) {
			try {
				Iterator<String> urls = buildUrl(card).iterator();
				prices = crawl(card, urls.next());
				while (!prices.isEmpty() && urls.hasNext()) {
					prices = crawl(card, urls.next());
				}
			} catch (IOException e) {
				LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
			}
		}
		return prices;
	}

	private List<CardPrice> crawl(Card card, String url) {
		return Arrays.asList(crawlPaper(card, url), crawlFoil(card, url + "?foilMode=1")).stream()
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	private CardPrice crawlPaper(Card card, String url) {
		CardPrice price = retrieve(card, CardPriceSource.mkm);
		boolean found = false;
		try {
			price.setLink(url);
			Document document = Jsoup.connect(url).get();
			if (!document.select(".article-table .text-warning").isEmpty()
					|| card.getEdition().getFoil() == Foil.onlyfoil) {
				price.setPrice(null);
				price.setPriceDate(new Date());
			} else {
				Element e = document.select("div.info-list-container dl dd").last();
				if (e != null) {
					price.setPrice(new BigDecimal(e.select("span").first().text()
							.replaceAll("[^,0-9]", "").replace(',', '.')));
					price.setPriceDate(new Date());
					found = true;
				}
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
		}
		if (found) {
			return price;
		}
		return null;
	}

	private CardPrice crawlFoil(Card card, String url) {
		CardPrice price = retrieve(card, CardPriceSource.mkmFoil);
		boolean found = false;
		try {
			Document document = Jsoup.connect(url).get();
			price.setLink(url);

			if (!document.select(".article-table .text-warning").isEmpty()
					|| card.getEdition().getFoil() == Foil.nofoil) {
				/**
				 * on est dans un cas le foil n'existe probablement pas. Si un enregistrement en
				 * base existe on le passe à null. c'est degueux.
				 */
				price.setPrice(null);
				price.setPriceDate(new Date());
				found = true;
			} else {
				Element e = document.select("div.info-list-container dl dd").last();
				if (e != null) {
					price.setPrice(new BigDecimal(e.select("span").first().text()
							.replaceAll("[^,0-9]", "").replace(',', '.')));
					price.setPriceDate(new Date());
					found = true;
				}
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
		}
		if (found) {
			return price;
		}
		return null;
	}

	private boolean isIgnored(Card card) {
		return card.getEdition().isOnlineOnly()
				|| IGNORED_EDITION.contains(card.getEdition().getCode());
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

	private List<String> buildUrl(Card card) throws IOException {
		String editions = card.getEdition().getMkmName();
		if (editions == null) {
			editions = card.getEdition().getName();
		}
		List<String> urls = new ArrayList<>();
		for (String edition : StringUtils.split(editions, '|')) {
			String base = "https://www.cardmarket.com/en/Magic/Products/Singles/"
					+ URLEncoder.encode(edition, ENCODING) + "/"
					+ URLEncoder.encode(name(card), ENCODING);
			if (card.getLinkCard() != null) {
				urls.add(base + URLEncoder.encode("-" + name(card.getLinkCard()), ENCODING));
			} else {
				urls.add(base);
			}
		}
		// https://www.cardmarket.com/en/Magic/Products/Singles/Rivals+of+Ixalan/Journey-to-Eternity-Atzal-Cave-of-Eternity
		// https://www.cardmarket.com/en/Magic/Products/Singles/Battle+for+Zendikar/Ob-Nixilis-Reignited
		return urls;
	}

	private String name(Card card) {
		return card.getName().replaceAll("[^a-zA-Z-]", "-").replaceAll("--", "-");
	}

}
