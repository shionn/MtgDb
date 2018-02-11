package tcg.deck;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckEntryCategory;
import tcg.security.User;

@Controller
public class DeckCardAddController {

	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/d/add/{card}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange addMain(@PathVariable("card") String id) {
		return new DeckChange(add(id, 1, DeckEntryCategory.main));
	}

	@RequestMapping(value = "/d/add-p/{card}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange addMainPlayset(@PathVariable("card") String id) {
		return new DeckChange(add(id, 4, DeckEntryCategory.main));
	}

	@RequestMapping(value = "/d/add-s/{card}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange addSide(@PathVariable("card") String id) {
		return new DeckChange(add(id, 1, DeckEntryCategory.side));
	}

	private int add(String cardId, int qty, DeckEntryCategory category) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		Card card = new Card();
		card.setId(cardId);
		DeckEntry entry = new DeckEntry();
		entry.setCard(card);
		entry.setCategory(category);
		entry.setDeck(user.getCurrentDeck());
		entry.setFoil(false);
		entry.setQty(qty);
		if (dao.checkAllow(user.getUser(), user.getCurrentDeck())) {
			dao.addEntry(entry);
			dao.updateDeck(user.getCurrentDeck());
			// dao.addHistory()
			session.commit();
		}
		return dao.count(entry);
	}

}
