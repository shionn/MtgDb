package tcg.search;

import java.util.ArrayList;
import java.util.Arrays;
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

import tcg.card.formater.CardFormater;
import tcg.db.dao.CardSearchDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.Edition;

@Controller
@SessionScope
public class AdvancedSearchController {

	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");

	private static final Pattern POWER_AND_TOUGHNESS_PATTERN = Pattern.compile("^[0-9*]+/[0-9*]+$");

	private static final Pattern CMC_PATTERN = Pattern.compile("^\\d+$");

	private static final List<String> COLORS = Arrays.asList("White", "Blue", "Black", "Red", "Green");

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

	@Autowired
	@Qualifier("Edition")
	private List<Edition> allEditions;

	private List<Filter> filters = new ArrayList<>();

	@Autowired
	private SqlSession session;

	@Autowired
	private CardFormater formater;

	@RequestMapping(path = "/as", method = RequestMethod.GET)
	public ModelAndView view() {
		List<Card> cards = new ArrayList<>();
		if (!filters.isEmpty()) {
			cards = session.getMapper(CardSearchDao.class).search(filters);
			cards.stream().forEach(c -> c.setManaCost(formater.manaCost(c)));
		}
		return new ModelAndView("advanced-search").addObject("filters", filters).addObject("cards", cards);
	}

	@RequestMapping(path = "/as/{type}/{value:.*}", method = RequestMethod.GET)
	public String addFilter(@PathVariable("type") FilterType type, @PathVariable("value") String value) {
		Filter filter = buildFilter(type, value);
		if (legal(filter)) {
			if (!filters.remove(filter)) {
				this.filters.add(filter);
			}
			if (filters.size() > 100) {
				filters.clear();
				throw new IllegalArgumentException("Max 100 Filters");
			}
			return "redirect:/as";
		}
		throw new IllegalArgumentException(value);
	}

	private Filter buildFilter(FilterType type, String value) {
		if (type == FilterType.Edition) {
			return new Filter(allEditions.stream().filter(e -> StringUtils.equalsIgnoreCase(e.getCode(), value))
					.findFirst().orElseThrow(() -> {
						throw new IllegalArgumentException(value);
					}));
		}
		return new Filter(type, value);
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
		case Name:
		case Text:
			return NAME_PATTERN.matcher(filter.getValue()).find();
		case Color:
			return COLORS.contains(filter.getValue());
		case Type:
			return allTypes.contains(filter.getValue());
		case SubType:
			return allSubTypes.contains(filter.getValue());
		case SuperType:
			return allSuperTypes.contains(filter.getValue());
		case KeyWord:
			return allKeyWord.contains(filter.getValue());
		case Edition:
			return filter.getDisplay() != null;
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
		filters.addAll(COLORS.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.Color, t)).collect(Collectors.toList()));
		filters.addAll(allSuperTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.SuperType, t)).collect(Collectors.toList()));
		filters.addAll(allTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.Type, t)).collect(Collectors.toList()));
		filters.addAll(allSubTypes.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.SubType, t)).collect(Collectors.toList()));
		filters.addAll(allKeyWord.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, filter))
				.map(t -> new Filter(FilterType.KeyWord, t)).collect(Collectors.toList()));
		filters.addAll(allEditions.stream().filter(e -> StringUtils.startsWithIgnoreCase(e.getName(), filter))
				.map(e -> new Filter(e)).collect(Collectors.toList()));
		if (NAME_PATTERN.matcher(filter).find()) {
			filters.add(new Filter(FilterType.Name, filter));
			filters.add(new Filter(FilterType.Text, filter));
		}
		return new ModelAndView("advanced-search-autocomplete").addObject("filters", filters);
	}

}
