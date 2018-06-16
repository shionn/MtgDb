package tcg.price.mkm;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	private static final String ENCODING = "UTF-8";
	private static final List<String> IGNORED_EDITION = Arrays.asList("pPRE");

	public List<CardPrice> price(Card card) {
		CardPrice paper = retrieve(card, CardPriceSource.mkm);
		CardPrice foil = retrieve(card, CardPriceSource.mkmFoil);
		if (!isIgnored(card)) {
			try {
				Iterator<String> urls = buildUrl(card).iterator();
				boolean found = crawl(paper, foil, urls.next());
				while (!found && urls.hasNext()) {
					found = crawl(paper, foil, urls.next());
				}
			} catch (IOException e) {
				LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);
			}
		}
		return Arrays.asList(paper, foil);
	}

	private boolean crawl(CardPrice paper, CardPrice foil, String url) {
		boolean found = false;
		try {
			paper.setLink(url);
			Document document = Jsoup.connect(url).get();
			Element e = document.select("table.availTable tr.row_Even.row_2 td.outerRight").first();
			if (e != null) {
				paper.setPrice(
						new BigDecimal(e.text().replaceAll("[^,0-9]", "").replace(',', '.')));
				paper.setPriceDate(new Date());
				found = true;
			}
			e = document.select("table.availTable tr.row_Even.row_4 td.outerRight").first();
			if (e != null) {
				foil.setPrice(new BigDecimal(e.text().replaceAll("[^,0-9]", "").replace(',', '.')));
				foil.setPriceDate(new Date());
				found = true;
			}
		} catch (IOException e) {
			LoggerFactory.getLogger(MkmCrawler.class).error("Can't crawl price : ", e);

		}
		return found;
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
			String url = "https://www.cardmarket.com/en/Magic/Products/Singles/"
					+ URLEncoder.encode(edition, ENCODING) + "/"
					+ URLEncoder.encode(card.getName(), ENCODING);
			if (card.getLinkCard() != null) {
				url += URLEncoder.encode(" // " + card.getLinkCard().getName(), ENCODING);
			}
			//https://www.cardmarket.com/en/Magic/Products/Singles/Rivals+of+Ixalan/Journey+to+Eternity+%2F%2F+Atzal%2C+Cave+of+Eternity
			//https://www.cardmarket.com/en/Magic/Products/Singles/Rivals+of+Ixalan/Journey+to+Eternity+%2F%2F+Atzal%2C+Cave+of+Eternity
			urls.add(url);
		}
		return urls;
	}

}
