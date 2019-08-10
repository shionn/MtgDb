package tcg.price.goldfish;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tcg.db.dbo.Card;
import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardPriceSource;
import tcg.security.MailSender;

@Component
public class MtgGoldFishCrawler {
	private Logger logger = LoggerFactory.getLogger(MtgGoldFishCrawler.class);

	private static final List<String> IGNORED_EDITION = Arrays.asList("WC00");

	@Autowired
	private MailSender mailSender;

	private tcg.security.User user;

	public List<CardPrice> priceForNotFoil(Card card) {
		List<CardPrice> prices = new ArrayList<CardPrice>();
		if (card.getFoil() != Foil.onlyfoil) {
			prices.addAll(crawl(card, CardPriceSource.MtgGoldFishPaper,
					CardPriceSource.MtgGoldFishTx, buildUrl(card)));
		}
		return prices;
	}

	public List<CardPrice> priceForFoil(Card card) {
		List<CardPrice> prices = new ArrayList<CardPrice>();
		if (card.getFoil() != Foil.nofoil) {
			prices.addAll(crawl(card, CardPriceSource.MtgGoldFishFoilPaper,
					CardPriceSource.MtgGoldFishFoilTx, buildFoilUrl(card)));
		}
		return prices;
	}

	private List<CardPrice> crawl(Card card, CardPriceSource paperType, CardPriceSource onlineType,
			List<String> links) {
		Iterator<String> ite = links.iterator();
		CardPrice paper = retrieve(card, paperType);
		CardPrice online = retrieve(card, onlineType);
		boolean found = false;
		while (ite.hasNext() && !found) {
			String link = ite.next();
			try {
				crawl(card, paper, online, link);
				found = true;
			} catch (IOException e) {
				logger.warn("Can't crawl price : ", e);
			}
		}
		if (!found && user.isAdmin()) {
			paper.setError("IOException");
			online.setError("IOException");
			mailSender.sendNoPriceFound(card.getName(), card.getEdition().getName(),
					card.getEdition().getCode(), paperType);

		}
		return Arrays.asList(paper, online);
	}

	private void crawl(Card card, CardPrice paper, CardPrice online, String link)
			throws IOException {
		paper.setLink(link);
		online.setLink(link);
		if (!isIgnored(card)) {
			Document document = Jsoup.connect(link).get();
			Element e = document.select("div.price-box.paper .price-box-price").first();
			if (e != null) {
				try {
					paper.setPrice(new BigDecimal(e.text().replaceAll(",", "")));
					paper.setPriceDate(new Date());
				} catch (NumberFormatException ex) {
					paper.setError("NumberFormatException");
					logger.warn("can't parse " + e.text(), ex);
				}
			} else {
				paper.setError("NoElement");
				logger.warn("paper price not found");
			}
			e = document.select("div.price-box.online .price-box-price").first();
			if (e != null) {
				try {
					online.setPrice(new BigDecimal(e.text().replaceAll(",", "")));
					online.setPriceDate(new Date());
				} catch (NumberFormatException ex) {
					online.setError("NumberFormatException");
					logger.warn("can't parse " + e.text(), ex);
				}
			} else {
				online.setError("NumberFormatException");
				logger.warn("online price not found");
			}
		}
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

	private List<String> buildUrl(Card card) {
		return formatEdition(card).stream().map(e -> {
			String url = "https://www.mtggoldfish.com/price/" + e + "/" + formatName(card)
					+ "#paper";
			return url.replace(' ', '+').replaceAll("\\+\\+", "+");
		}).collect(Collectors.toList());
	}
	// https://www.mtggoldfish.com/price/Magic+Origins/Jace+Vryns+Prodigy#paper
	// https://www.mtggoldfish.com/price/Magic+Origins/Jace+Vryns+Prodigy+Jace+Telepath+Unbound#paper

	private List<String> buildFoilUrl(Card card) {
		return formatEdition(card).stream().map(e -> {
			String url = "https://www.mtggoldfish.com/price/" + e + ":Foil/" + formatName(card)
					+ "#paper";
			return url.replace(' ', '+').replaceAll("\\+\\+", "+");
		}).collect(Collectors.toList());
	}

	private List<String> formatEdition(Card card) {
		String editionName = card.getEdition().getGoldfishName();
		if (editionName == null) {
			editionName = card.getEdition().getName();
		}
		List<String> formateds = new ArrayList<>();
		for (String name : StringUtils.split(editionName, '|')) {
			formateds.add(name.replaceAll("[:',]", "").replaceAll("\\.", "%252E"));
		}
		return formateds;
	}

	private String formatName(Card card) {
		String name = formatName(card.getName());
		if (card.getLayout() == CardLayout.split) {
			name += ' ' + formatName(card.getLinkCard().getName());
		}
		return name;
	}

	private String formatName(String name) {
		return name.replaceAll("[\".,]", " ").replaceAll("'", "");
	}

	@Autowired
	public void setUser(tcg.security.User user) {
		this.user = user;
	}

}
