package tcg.card;

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

	@Async
	private void updatePrices(Card card) {

	}

	private boolean isOldPrice(Card card) {
		return card.getPrices().isEmpty();
	}

}
