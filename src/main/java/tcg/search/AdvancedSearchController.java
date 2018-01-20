package tcg.search;

import java.util.ArrayList;
import java.util.List;
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

@Controller
@SessionScope
public class AdvancedSearchController {

	@Autowired
	@Qualifier("Type")
	private List<String> allTypes;

	@Autowired
	@Qualifier("SubType")
	private List<String> allSubTypes;

	@Autowired
	@Qualifier("SuperType")
	private List<String> allSuperTypes;

	private List<Filter> filters = new ArrayList<>();

	@Autowired
	private SqlSession session;

	@RequestMapping(path = "/as", method = RequestMethod.GET)
	public ModelAndView view() {
		List<String> types = filters.stream().filter(f -> f.getType() == FilterType.Type)
				.map(Filter::getValue).collect(Collectors.toList());
		// session.getMapper(CardSearchDao.class).search(types);
		return new ModelAndView("advanced-search").addObject("filters", filters);
	}

	@RequestMapping(path = "/as/{type}/{value}", method = RequestMethod.GET)
	public String addFilter(@PathVariable("type") FilterType type,
			@PathVariable("value") String value) {
		this.filters.add(new Filter(type, value));
		return "redirect:/as";
	}

	@RequestMapping(path = "/as/filter", method = RequestMethod.GET)
	public ModelAndView filters(@Param("filter") String filter) {
		List<Filter> filters = new ArrayList<>();
		filters.addAll(allTypes.stream().filter(t -> StringUtils.indexOfIgnoreCase(t, filter) != -1)
				.map(t -> new Filter(FilterType.Type, t)).collect(Collectors.toList()));
		filters.addAll(
				allSubTypes.stream().filter(t -> StringUtils.indexOfIgnoreCase(t, filter) != -1)
				.map(t -> new Filter(FilterType.SubType, t)).collect(Collectors.toList()));
		filters.addAll(allSuperTypes.stream()
				.filter(t -> StringUtils.indexOfIgnoreCase(t, filter) != -1)
				.map(t -> new Filter(FilterType.SuperType, t)).collect(Collectors.toList()));
		return new ModelAndView("advanced-search-autocomplete").addObject("filters", filters);
	}

}
