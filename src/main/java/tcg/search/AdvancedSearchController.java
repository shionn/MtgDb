package tcg.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.CardSearchDao;
import tcg.db.dbo.Card;

@Controller
@SessionScope
public class AdvancedSearchController {

	private static final Pattern POWER_AND_TOUGHNESS_PATTERN = Pattern.compile("^[0-9*]+/[0-9*]+$");

	private static final Pattern CMC_PATTERN = Pattern.compile("^\\d+$");

	@Autowired
	@Qualifier("Type")
	private List<String> allTypes;

	@Autowired
	@Qualifier("SubType")
	private List<String> allSubTypes;

	@Autowired
	@Qualifier("SuperType")
	private List<String> allSuperTypes;

	@Autowired
	@Qualifier("KeyWord")
	private List<String> allKeyWord;

	private List<Filter> filters = new ArrayList<>();

	@Autowired
	private SqlSession session;

	@RequestMapping(path = "/as", method = RequestMethod.GET)
	public ModelAndView view() {
		List<Card> cards = new ArrayList<>();
		if (!filters.isEmpty()) {
			cards = session.getMapper(CardSearchDao.class).search(filters);
		}
		return new ModelAndView("advanced-search").addObject("filters", filters).addObject("cards", cards);
	}

	@RequestMapping(path = "/as/{type}/{value:.*}", method = RequestMethod.GET)
	public String addFilter(@PathVariable("type") FilterType type, @PathVariable("value") String value) {
		Filter filter = new Filter(type, value);
		if (legal(filter)) {
			if (!filters.remove(filter)) {
				this.filters.add(filter);
			}
			return "redirect:/as";
		}
		throw new IllegalArgumentException(value);
	}

	/**
	 * Grosse finte
	 */
	@RequestMapping(path = "/as/PowerAndToughness/{p}/{t}", method = RequestMethod.GET)
	public String addFilter(@PathVariable("p") String p, @PathVariable("t") String t) {
		return addFilter(FilterType.PowerAndToughness, p + '/' + t);
	}
	private boolean legal(Filter filter) {
		switch (filter.getType()) {
		case ConvertedManaCost:
			return CMC_PATTERN.matcher(filter.getValue()).find();
		case PowerAndToughness:
			return POWER_AND_TOUGHNESS_PATTERN.matcher(filter.getValue()).find();
		case Type:
			return allTypes.contains(filter.getValue());
		case SubType:
			return allSubTypes.contains(filter.getValue());
		case SuperType:
			return allSuperTypes.contains(filter.getValue());
		case KeyWord:
			return allKeyWord.contains(filter.getValue());
		default:
			return false;
		}
	}

	@RequestMapping(path = "/as/filter", method = RequestMethod.GET)
	public ModelAndView filters(@Param("filter") String filter) {
		List<Filter> filters = new ArrayList<>();
		if (CMC_PATTERN.matcher(filter).find()) {
			filters.add(new Filter(FilterType.ConvertedManaCost, filter));
		}
		if (POWER_AND_TOUGHNESS_PATTERN.matcher(filter).find()) {
			filters.add(new Filter(FilterType.PowerAndToughness, filter));
		}
		filters.addAll(allSuperTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.SuperType, t)).collect(Collectors.toList()));
		filters.addAll(allTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.Type, t)).collect(Collectors.toList()));
		filters.addAll(allSubTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.SubType, t)).collect(Collectors.toList()));
		filters.addAll(allKeyWord.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.KeyWord, t)).collect(Collectors.toList()));
		return new ModelAndView("advanced-search-autocomplete").addObject("filters", filters);
	}

}
