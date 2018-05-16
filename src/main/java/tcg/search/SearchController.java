package tcg.search;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcg.card.formater.CardFormater;
import tcg.db.dao.CardSearchDao;

@Controller
public class SearchController {

	@Autowired
	private SqlSession session;
	@Autowired
	private CardFormater formater;

	@RequestMapping(value = "/s", method = RequestMethod.GET)
	public ModelAndView quickSearch(@Param("name") String name) {
		return new ModelAndView("quicksearch").addObject("cards",
				session.getMapper(CardSearchDao.class).quick(formater.normalize(name)));
	}
}
