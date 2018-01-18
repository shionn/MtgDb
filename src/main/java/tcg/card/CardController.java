package tcg.card;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.CardDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.price.goldfish.MtgGoldFishCrawler;
import tcg.price.mkm.MkmCrawler;

@Controller
public class CardController {

	@Autowired
	private SqlSession session;

	@Autowired
	private CardFormater formater;

	@RequestMapping(value = "/c/{id}", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable("id") String id) {
		Card card = session.getMapper(CardDao.class).read(id);
		card.setText(formater.text(card));
		card.setManaCost(formater.manaCost(card));
		if (isOldPrice(card)) {
			updatePrices(card);
		}
		return new ModelAndView("card").addObject("card", card);
	}

	@Autowired
	private MtgGoldFishCrawler fishCrawler;
	@Autowired
	private MkmCrawler mkmCrawler;

	@Async
	private void updatePrices(Card card) {
		List<CardPrice> prices = new ArrayList<>();
		prices.add(mkmCrawler.price(card));
		prices.addAll(fishCrawler.price(card));
		prices.stream().forEach(session.getMapper(CardDao.class)::price);
		card.setPrices(prices);
		session.commit();
	}

	private boolean isOldPrice(Card card) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date toOld = calendar.getTime();
		return card.getPrices().stream().map(CardPrice::getUpdateDate).filter(toOld::after).map(d -> true).findFirst()
				.orElse(card.getPrices().isEmpty());
	}

}
