package tcg.deck;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
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
		return new DeckChange(update(entry(id, 1, DeckEntryCategory.main, false)));
	}


	@RequestMapping(value = "/d/add-p/{card}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange addMainPlayset(@PathVariable("card") String id) {
		return new DeckChange(update(entry(id, 4, DeckEntryCategory.main, false)));
	}

	@RequestMapping(value = "/d/add-s/{card}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange addSide(@PathVariable("card") String id) {
		return new DeckChange(update(entry(id, 1, DeckEntryCategory.side, false)));
	}

	@RequestMapping(value = "/d/add/{qty}/{card}/{category}/{foil}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange add(
			@PathVariable("qty") int qty,
			@PathVariable("card") String id,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil) {
		return new DeckChange(update(entry(id, qty, category, foil)));
	}

	@RequestMapping(value = "/d/rm/{qty}/{card}/{category}/{foil}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange rm(
			@PathVariable("qty") int qty,
			@PathVariable("card") String id,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil) {
		return new DeckChange(update(entry(id, -qty, category, foil)));
	}

	@RequestMapping(value = "/d/rm/all/{card}/{category}/{foil}", method = RequestMethod.GET)
	@ResponseBody
	public DeckChange rmAll(
			@PathVariable("card") String id,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil) {
		return new DeckChange(rm(entry(id, 0, category, foil)));
	}

	@RequestMapping(value = "/d/mv/{qty}/{card}/{source}/{foil}/{dest}", method = RequestMethod.GET)
	public ModelAndView mv(
			@PathVariable("qty") int qty,
			@PathVariable("card") String id,
			@PathVariable("source") DeckEntryCategory sourceCat,
			@PathVariable("foil") boolean foil,
			@PathVariable("dest") DeckEntryCategory destCat) {
		update(entry(id, -qty, sourceCat, foil));
		update(entry(id, qty, destCat, foil));
		List<DeckEntry> entries = session.getMapper(DeckEditDao.class).readEntries(user.getCurrentDeck(), id);
		entries.stream().forEach(this::format);
		return new ModelAndView("deck/table-ajax").addObject("items", entries);
	}

	@Autowired
	private CardFormater formater;

	private void format(DeckEntry e) {
		e.getCard().setManaCost(formater.manaCost(e.getCard()));
	}

	private int update(DeckEntry delta) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), user.getCurrentDeck())) {
			dao.updateEntry(delta);
			dao.updateDeck(user.getCurrentDeck());
			dao.deleteZero(user.getCurrentDeck());
			// dao.addHistory()
			session.commit();
		}
		return dao.count(delta);
	}

	private int rm(DeckEntry entry) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), user.getCurrentDeck())) {
			dao.deleteEntry(entry);
			dao.updateDeck(user.getCurrentDeck());
			// dao.addHistory()
			session.commit();
		}
		return dao.count(entry);
	}

	private DeckEntry entry(String cardId, int qty, DeckEntryCategory category, boolean foil) {
		Card card = new Card();
		card.setId(cardId);
		DeckEntry entry = new DeckEntry();
		entry.setCard(card);
		entry.setCategory(category);
		entry.setDeck(user.getCurrentDeck());
		entry.setFoil(foil);
		entry.setQty(qty);
		return entry;
	}

}
