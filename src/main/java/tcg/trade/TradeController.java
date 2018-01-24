package tcg.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.TradeDao;
import tcg.db.dbo.CardPrice;

@Controller
@SessionScope
public class TradeController {

	@Autowired
	private SqlSession session;

	private List<Trad> cards1 = new ArrayList<>();
	private List<Trad> cards2 = new ArrayList<>();

	@RequestMapping(value = "/t", method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("trade").addObject("cards1", cards1).addObject("cards2", cards2)
				.addObject("total1", total(cards1)).addObject("total2", total(cards2));
	}

	private TradTotal total(List<Trad> cards) {
		return new TradTotal(sum(cards, Trad::getMkmPrice), sum(cards, Trad::getMtgGoldFishPrice),
				sum(cards, Trad::getMtgGoldFishTxPrice));
	}

	private BigDecimal sum(List<Trad> cards, Function<Trad, CardPrice> mapper) {
		return cards.stream().map(mapper).filter(Objects::nonNull)
				.map(CardPrice::getPrice).filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@RequestMapping(value = "/t/add/{id}", method = RequestMethod.GET)
	public String add(@PathVariable("id") String id) {
		cards1.add(new Trad(session.getMapper(TradeDao.class).readCard(id), false));
		return "redirect:/c/" + id;
	}

	@RequestMapping(value = "/t/add-f/{id}", method = RequestMethod.GET)
	public String addFoiled(@PathVariable("id") String id) {
		cards1.add(new Trad(session.getMapper(TradeDao.class).readCard(id), true));
		return "redirect:/c/" + id;
	}

	@RequestMapping(value = "/t/rm/{id}", method = RequestMethod.GET)
	public String rm(@PathVariable("id") String id) {
		cards1.removeAll(cards1.stream().filter(t -> t.getCard().getId().equals(id))
				.collect(Collectors.toList()));
		cards2.removeAll(cards2.stream().filter(t -> t.getCard().getId().equals(id))
				.collect(Collectors.toList()));
		return "redirect:/t";
	}

	@RequestMapping(value = "/t/right/{id}", method = RequestMethod.GET)
	public String right(@PathVariable("id") String id) {
		List<Trad> cards = cards1.stream().filter(c -> c.getCard().getId().equals(id))
				.collect(Collectors.toList());
		cards1.removeAll(cards);
		cards2.addAll(cards);
		return "redirect:/t";
	}

	@RequestMapping(value = "/t/left/{id}", method = RequestMethod.GET)
	public String left(@PathVariable("id") String id) {
		List<Trad> cards = cards2.stream().filter(c -> c.getCard().getId().equals(id)).collect(Collectors.toList());
		cards2.removeAll(cards);
		cards1.addAll(cards);
		return "redirect:/t";
	}

}
