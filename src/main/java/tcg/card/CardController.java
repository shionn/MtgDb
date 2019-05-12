package tcg.card;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.AdmCardDao;
import tcg.db.dao.CardDao;
import tcg.db.dao.DeckDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardRule;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckEntryCategory;
import tcg.price.PriceDeamon;
import tcg.security.User;

@Controller
public class CardController {

	@Autowired
	private SqlSession session;

	@Autowired
	private CardFormater formater;

	@Autowired
	private PriceDeamon priceUpdater;

	@Autowired
	private User user;

	@RequestMapping(value = "/c/{id}", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable("id") String id) {
		Card card = session.getMapper(CardDao.class).read(id);
		card.setText(formater.text(card.getText()));
		card.setOriginalText(formater.text(card.getOriginalText()));
		for (CardRule rule : card.getRules()) {
			rule.setRule(formater.text(rule.getRule()));
		}
		card.setManaCost(formater.manaCost(card));
		card.setFlavor(formater.flavor(card));
		if (card.getLinkCard() != null) {
			card.getLinkCard().setText(card.getLinkCard().getText());
		}
		ModelAndView view = new ModelAndView("card");
		if (isOldPrice(card)) {
			priceUpdater.request(card);
			view.addObject("priceupdate", true);
		}
		if (user.getCurrentDeck() != 0) {
			List<DeckEntry> entries = session.getMapper(DeckDao.class)
					.readCardEntries(user.getCurrentDeck(), id);
			view.addObject("deckMainQty",
					entries.stream().filter(e -> e.getCategory() == DeckEntryCategory.main)
							.map(e -> e.getQty()).reduce(0, (a, b) -> a + b));
			view.addObject("deckSideQty",
					entries.stream().filter(e -> e.getCategory() == DeckEntryCategory.side)
							.map(e -> e.getQty()).reduce(0, (a, b) -> a + b));
		}
		card.setPrices(filterPrices(card.getPrices()));
		return view.addObject("card", card);
	}

	@RequestMapping(value = "/c/{id}/drop", method = RequestMethod.GET)
	public String drop(@PathVariable("id") String id) {
		if (user.isAdmin()) {
			AdmCardDao dao = session.getMapper(AdmCardDao.class);
			dao.deleteAssistance(id);
			dao.deleteLang(id);
			dao.deleteLegality(id);
			dao.deletePrice(id);
			dao.deleteRule(id);
			dao.deleteType(id);
			dao.updateDeckEntry(id);
			dao.delete(id);
			session.commit();
		}
		return "redirect:/";
	}

	private boolean isOldPrice(Card card) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date toOld = calendar.getTime();
		return card.getPrices().stream().map(CardPrice::getUpdateDate).filter(toOld::after)
				.map(d -> true).findFirst().orElse(card.getPrices().isEmpty());
	}

	@RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
	private ModelAndView price(@PathVariable("id") String id) {
		List<CardPrice> prices = priceUpdater.get(id);
		ModelAndView model = new ModelAndView("card-prices");
		if (prices == null || prices.isEmpty()) {
			model.setStatus(HttpStatus.NOT_FOUND);
		} else {
			prices = filterPrices(prices);
		}
		model.addObject("prices", prices);
		return model;
	}

	private List<CardPrice> filterPrices(List<CardPrice> prices) {
		return prices.stream().filter(p -> p.getPrice() != null).collect(Collectors.toList());
	}

}
