package tcg.deck;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.DeckDao;
import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckEntryCategory;
import tcg.security.User;

@Controller
public class DeckEntryEditController {

	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/d/add/{deck}/{qty}/{card}/{category}/{foil}", method = RequestMethod.GET)
	public String add(
			@PathVariable("deck") int deck,
			@PathVariable("qty") int qty,
			@PathVariable("card") String card,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil,
			@RequestHeader("referer") String referer) {
		update(entry(deck, card, qty, category, foil));
		if (referer.contains("/c/" + card)) {
			return "redirect:/c/" + card;
		}
		return "redirect:/d/" + deck;
	}

	@RequestMapping(value = "/d/rm/{deck}/{qty}/{card}/{category}/{foil}", method = RequestMethod.GET)
	public String rm(
			@PathVariable("deck") int deck,
			@PathVariable("qty") int qty,
			@PathVariable("card") String id,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil) {
		update(entry(deck, id, -qty, category, foil));
		return "redirect:/d/" + deck;
	}

	@RequestMapping(value = "/d/mv/{deck}/{qty}/{card}/{source}/{foil}/{dest}", method = RequestMethod.GET)
	public String mv(
			@PathVariable("deck") int deck,
			@PathVariable("qty") int qty,
			@PathVariable("card") String id,
			@PathVariable("source") DeckEntryCategory sourceCat,
			@PathVariable("foil") boolean foil,
			@PathVariable("dest") DeckEntryCategory destCat) {
		update(entry(deck, id, -qty, sourceCat, foil));
		update(entry(deck, id, qty, destCat, foil));
		return "redirect:/d/" + deck;
	}

	@RequestMapping(value = "/d/alter/{deck}/{card}/{category}/{foil}", method = RequestMethod.GET)
	public ModelAndView openAlterModal(
			@PathVariable("deck") int deck,
			@PathVariable("card") String card,
			@PathVariable("category") DeckEntryCategory category,
			@PathVariable("foil") boolean foil) {
		DeckEntry entry = session.getMapper(DeckDao.class)
				.readEntry(entry(deck, card, 0, category, foil));
		return new ModelAndView("deck/alter-modal").addObject("entry", entry);
	}

	@RequestMapping(value = "/d/printing/{deck}/{qty}/{source-card}/{dest-card}/{category}/{foil}", method = RequestMethod.GET)
	public String changeEdition(
			@PathVariable("deck") int deck, //
			@PathVariable("qty") int qty, //
			@PathVariable("source-card") String sourceCard, //
			@PathVariable("dest-card") String destCard, //
			@PathVariable("category") DeckEntryCategory category, //
			@PathVariable("foil") boolean foil) {
		update(entry(deck, sourceCard, -qty, category, foil));
		update(entry(deck, destCard, qty, category, foil));
		return "redirect:/d/" + deck;
	}

	@RequestMapping(value = "/d/tag/{deck}/{card}/{category}/{foil}/{tag}", method = RequestMethod.GET)
	public String tag(@PathVariable("deck") int deck, //
			@PathVariable("card") String card, //
			@PathVariable("category") DeckEntryCategory category, //
			@PathVariable("foil") boolean foil, //
			@PathVariable("tag") String tag) {
		DeckEntry entry = session.getMapper(DeckDao.class)
				.readEntry(entry(deck, card, 0, category, foil));
		entry.setQty(0);
		if (StringUtils.isBlank(entry.getTag())) {
			entry.setTag(tag);
		} else if (entry.getTags().contains(tag)) {
			Set<String> tags = new TreeSet<>(entry.getTags());
			tags.remove(tag);
			entry.setTag(StringUtils.join(tags, ';'));
		} else {
			Set<String> tags = new TreeSet<>(entry.getTags());
			tags.add(tag);
			entry.setTag(StringUtils.join(tags, ';'));
		}
		update(entry);
		return "redirect:/d/" + deck;
	}

	@RequestMapping(value = "/d/tag/{deck}/{card}/{category}/{foil}", method = RequestMethod.GET)
	public String addTag(@PathVariable("deck") int deck, //
			@PathVariable("card") String card, //
			@PathVariable("category") DeckEntryCategory category, //
			@PathVariable("foil") boolean foil, //
			@RequestParam("tag") String tag) {
		return tag(deck, card, category, foil, tag);
	}

	private void update(DeckEntry delta) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (dao.checkAllow(user.getUser(), delta.getDeck())) {
			dao.updateEntry(delta);
			dao.updateDeck(delta.getDeck());
			dao.deleteZero(delta.getDeck());
			// dao.addHistory()
			session.commit();
		}
	}

	private DeckEntry entry(int deck, String cardId, int qty, DeckEntryCategory category,
			boolean foil) {
		Card card = new Card();
		card.setId(cardId);
		DeckEntry entry = new DeckEntry();
		entry.setCard(card);
		entry.setCategory(category);
		entry.setDeck(deck);
		entry.setFoil(foil);
		entry.setQty(qty);
		return entry;
	}

}
