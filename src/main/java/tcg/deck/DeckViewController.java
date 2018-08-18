package tcg.deck;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.DeckDao;
import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.Deck;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckView;
import tcg.db.dbo.GuildColor;
import tcg.price.PriceDeamon;
import tcg.security.User;

@Controller
public class DeckViewController {

	private static final List<GuildColor> GUILDS = Arrays.asList(GuildColor.azorius,
			GuildColor.dimir, GuildColor.rakdos, GuildColor.gruul, GuildColor.selesnya,
			GuildColor.orzhov, GuildColor.golgari, GuildColor.simic, GuildColor.izzet,
			GuildColor.boros);

	private static final List<GuildColor> COLORS = Arrays.asList(GuildColor.white, GuildColor.blue,
			GuildColor.black, GuildColor.red, GuildColor.green);

	@Autowired
	private SqlSession session;

	@Autowired
	private CardFormater formater;

	@Autowired
	private User user;

	@Autowired
	private PriceDeamon price;

	@RequestMapping(value = "/d/table/{id}", method = RequestMethod.GET)
	public ModelAndView table(@PathVariable("id") int id) {
		Deck deck = deck(id);
		checkAndUpdatView(deck, DeckView.table);
		return new ModelAndView("deck/table").addObject("deck", deck);
	}

	@RequestMapping(value = "/d/flat/{id}", method = RequestMethod.GET)
	public ModelAndView flat(@PathVariable("id") int id) {
		Deck deck = session.getMapper(DeckDao.class).readDeck(id);
		return new ModelAndView("deck/flat").addObject("deck", deck);
	}

	@RequestMapping(value = "/d/cube/{id}", method = RequestMethod.GET)
	public ModelAndView cube(@PathVariable("id") int id) {
		Deck deck = deck(id);
		checkAndUpdatView(deck, DeckView.cube);
		return new ModelAndView("deck/cube") //
				.addObject("deck", deck) //
				.addObject("colors", COLORS) //
				.addObject("guilds", GUILDS);
	}

	@RequestMapping(value = "/d/price/{id}", method = RequestMethod.GET)
	public ModelAndView price(@PathVariable("id") int id) {
		Deck deck = session.getMapper(DeckDao.class).readDeck(id);
		return new ModelAndView("deck/price").addObject("deck", deck);
	}

	@RequestMapping(value = "/d/price/{deck}/refresh/{card}", method = RequestMethod.GET)
	public String refreshPrice(@PathVariable("deck") int deck,
			@PathVariable("card") String card) throws InterruptedException {
		price.request(card);
		price.waitFor(card);
		return "redirect:/d/price/" + deck;
	}

	@RequestMapping(value = "/d/history/{id}", method = RequestMethod.GET)
	public ModelAndView history(@PathVariable("id") int id) {
		Deck deck = session.getMapper(DeckDao.class).readDeck(id);
		return new ModelAndView("deck/history").addObject("deck", deck);
	}

	@RequestMapping(value = "/d/stat/{id}", method = RequestMethod.GET)
	public ModelAndView stat(@PathVariable("id") int id) {
		Deck deck = session.getMapper(DeckDao.class).readDeck(id);
		return new ModelAndView("deck/stat").addObject("deck", deck);
	}

	private Deck deck(int id) {
		Deck deck = session.getMapper(DeckDao.class).readDeck(id);
		deck.getCards().stream().forEach(this::format);
		return deck;
	}

	private void format(DeckEntry e) {
		e.getCard().setManaCost(formater.manaCost(e.getCard()));
	}

	private void checkAndUpdatView(Deck deck, DeckView view) {
		DeckEditDao dao = session.getMapper(DeckEditDao.class);
		if (deck.getView() != view && dao.checkAllow(user.getUser(), deck.getId())) {
			dao.updateView(deck.getId(), view);
			session.commit();
		}
	}

}
