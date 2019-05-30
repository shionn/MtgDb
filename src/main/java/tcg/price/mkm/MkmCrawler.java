package tcg.price.mkm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import tcg.db.dbo.Card;
import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;
import tcg.security.MailSender;

@Component
@ApplicationScope
public class MkmCrawler {

	private static final String ENCODING = "UTF-8";
	private static final List<String> IGNORED_EDITION = Arrays.asList("pPRE");

	private MailSender mailSender;

	public List<CardPrice> priceForNotFoil(Card card) {
		List<CardPrice> prices = new ArrayList<>();
		if (!isIgnored(card)) {
			try {
				Iterator<String> urls = buildUrl(card).iterator();
				CardPrice price = crawlPaper(card, urls.next());
				while (price == null && urls.hasNext()) {
					price = crawlPaper(card, urls.next());
				}
				if (price != null) {
					prices.add(price);
				} else {
					mailSender.sendNoPriceFound(card.getName(), card.getEdition().getName(),
							card.getEdition().getCode(), CardPriceSource.mkm);
				}
			} catch (IOException e) {
				LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
			}
		}
		return prices;
	}

	public List<CardPrice> priceForFoil(Card card) {
		List<CardPrice> prices = new ArrayList<>();
		if (!isIgnored(card)) {
			try {
				Iterator<String> urls = buildUrl(card).iterator();
				CardPrice price = crawlFoil(card, urls.next());
				while (price == null && urls.hasNext()) {
					price = crawlFoil(card, urls.next());
				}
				if (price != null) {
					prices.add(price);
				} else {
					mailSender.sendNoPriceFound(card.getName(), card.getEdition().getName(),
							card.getEdition().getCode(), CardPriceSource.mkmFoil);
				}
			} catch (IOException e) {
				LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
			}
		}
		return prices;
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
			urls.add(buildUrlMode1(card, edition));
			urls.add(buildUrlMode2(card, edition));
		}
		// https://www.cardmarket.com/en/Magic/Products/Singles/Future-Sight/Haze-of-Rage
		return urls;
	}

	private String buildUrlMode1(Card card, String edition) throws UnsupportedEncodingException {
		String url = "https://www.cardmarket.com/en/Magic/Products/Singles/"
				+ URLEncoder.encode(replaceIllegalChar(edition), ENCODING) + "/"
				+ URLEncoder.encode(buildNameUrlMode1(card), ENCODING);
		if (card.getLayout().isMkmConcatName()) {
			url += URLEncoder.encode("-" + buildNameUrlMode1(card.getLinkCard()), ENCODING);
		}
		return url;
	}

	private String buildUrlMode2(Card card, String edition) throws UnsupportedEncodingException {
		String url = "https://www.cardmarket.com/en/Magic/Products/Singles/"
				+ URLEncoder.encode(replaceIllegalChar(edition), ENCODING) + "/"
				+ URLEncoder.encode(buildNameUrlMode2(card), ENCODING);
		if (card.getLayout().isMkmConcatName()) {
			url += URLEncoder.encode("-" + buildNameUrlMode1(card.getLinkCard()), ENCODING);
		}
		return url;
	}

	private String replaceIllegalChar(String edition) {
		return edition.replaceAll("'", "").replaceAll("[^a-zA-Z0-9-]", "-").replaceAll("--", "-");
	}

	private String buildNameUrlMode1(Card card) {
		return card.getName().replaceAll("'", "").replaceAll("[^a-zA-Z-]", "-").replaceAll("--",
				"-");
	}

	private String buildNameUrlMode2(Card card) {
		return card.getName().replaceAll("[^a-zA-Z-]", "-").replaceAll("--", "-");
	}

	@Autowired
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}
