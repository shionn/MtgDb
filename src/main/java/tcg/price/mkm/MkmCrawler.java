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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import tcg.db.dbo.Card;
import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;

@Component
@ApplicationScope
public class MkmCrawler {

	private static final String ENCODING = "UTF-8";
	private static final List<String> IGNORED_EDITION = Arrays.asList("pPRE");

	public List<CardPrice> price(Card card) throws InterruptedException, ExecutionException {
		List<CardPrice> prices = new ArrayList<>();
		if (!isIgnored(card)) {
			try {
				Iterator<String> urls = buildUrl(card).iterator();
				prices = crawl(card, urls.next());
				while (prices.isEmpty() && urls.hasNext()) {
					prices = crawl(card, urls.next());
				}
			} catch (IOException e) {
				LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
			}
		}
		return prices;
	}

	private ExecutorService executors = Executors.newFixedThreadPool(2);

	private List<CardPrice> crawl(Card card, String url)
			throws InterruptedException, ExecutionException {
		List<CardPrice> prices = new ArrayList<>();
		for (Future<CardPrice> futur : executors.invokeAll(buildCallable(card, url))) {
			prices.add(futur.get());
		}
		return prices.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

	private List<Callable<CardPrice>> buildCallable(Card card, String url) {
		List<Callable<CardPrice>> callable = new ArrayList<>();
		if (card.getFoil() != Foil.onlyfoil) {
			callable.add(new Callable<CardPrice>() {
				@Override
				public CardPrice call() throws Exception {
					return crawlPaper(card, url);
				}
			});
		}
		if (card.getFoil() != Foil.nofoil) {
			callable.add(new Callable<CardPrice>() {
				@Override
				public CardPrice call() throws Exception {
					return crawlFoil(card, url);
				}
			});
		}
		return callable;
	}

	private CardPrice crawlPaper(Card card, String url) {
		CardPrice price = retrieve(card, CardPriceSource.mkm);
		boolean found = false;
		try {
			price.setLink(url);
			Response execute = Jsoup.connect(url).cookie("cookies_consent", "accepted")
					.method(Method.GET).execute();
			Document document = execute.parse();
			if (!document.select(".article-table .text-warning").isEmpty()
					|| card.getFoil() == Foil.onlyfoil) {
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
			Document document = Jsoup.connect(url) //
					.method(Method.POST) //
					.data("amount", "0") //
					.data("apply", "Filter") //
					.data("extra[isAltered]", "0") //
					.data("extra[isFoil]", "Y") //
					.data("extra[isPlayset]", "0") //
					.data("extra[isSigned]", "0") //
					.data("minCondition", "7") //
					.execute().parse();
			price.setLink(url);

			Element e = document.select("div.info-list-container dl dd").last();
			if (e != null) {
				price.setPrice(new BigDecimal(e.select("span").first().text()
						.replaceAll("[^,0-9]", "").replace(',', '.')));
				price.setPriceDate(new Date());
				found = true;
			} else {
				price.setPrice(null);
				price.setPriceDate(new Date());
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
					+ URLEncoder.encode(replaceIllegalChar(edition), ENCODING) + "/"
					+ URLEncoder.encode(name(card), ENCODING);
			if (card.getLayout().isMkmConcatName()) {
				urls.add(base + URLEncoder.encode("-" + name(card.getLinkCard()), ENCODING));
			} else {
				urls.add(base);
			}
		}
		// https://www.cardmarket.com/en/Magic/Products/Singles/Rivals+of+Ixalan/Journey-to-Eternity-Atzal-Cave-of-Eternity
		// https://www.cardmarket.com/en/Magic/Products/Singles/Battle+for+Zendikar/Ob-Nixilis-Reignited
		// https://www.cardmarket.com/en/Magic/Products/Singles/Alliances/Force-of-Will
		// https://www.cardmarket.com/en/Magic/Products/Singles/Magic-Origins-Promos/Jace-Vryns-Prodigy
		// https://www.cardmarket.com/en/Magic/Products/Singles/Magic-Origins/Jace-Vryns-Prodigy-Jayemdae-Tome
		// https://www.cardmarket.com/en/Magic/Products/Singles/Urzas-Legacy/Mother-of-Runes
		return urls;
	}

	private String replaceIllegalChar(String edition) {
		return edition.replace(':', '-').replace(' ', '-').replaceAll("'", "").replaceAll("--",
				"-");
	}

	private String name(Card card) {
		return card.getName().replaceAll("'", "").replaceAll("[^a-zA-Z-]", "-").replaceAll("--",
				"-");
	}

}
