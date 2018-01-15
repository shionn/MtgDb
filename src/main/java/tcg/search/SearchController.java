package tcg.search;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tcg.db.dao.CardSearchDao;

@Controller
public class SearchController {

	@Autowired
	private SqlSession session;

	@RequestMapping(value = "/s", method = RequestMethod.GET)
	public String testSearch(@Param("name") String name) {
		return "redirect:/c/" + session.getMapper(CardSearchDao.class).findFirstId('%' + name + '%');
	}

}
