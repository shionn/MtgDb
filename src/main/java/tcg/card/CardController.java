package tcg.card;

import java.util.Calendar;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.CardDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.CardRule;
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
		ModelAndView view = new ModelAndView("card");
		if (isOldPrice(card)) {
			priceUpdater.request(card);
			view.addObject("priceupdate", true);
		}
		return view.addObject("card", card).addObject("user", user);
	}

	private boolean isOldPrice(Card card) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date toOld = calendar.getTime();
		return card.getPrices().stream().map(CardPrice::getUpdateDate).filter(toOld::after).map(d -> true).findFirst()
				.orElse(card.getPrices().isEmpty());
	}

	@RequestMapping(value = "/p/{id}", method = RequestMethod.GET)
	private ModelAndView price(@PathVariable("id") String id) {
		return new ModelAndView("card-prices").addObject("prices", priceUpdater.get(id));
	}

}
