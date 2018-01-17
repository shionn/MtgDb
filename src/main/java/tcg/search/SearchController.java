package tcg.search;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tcg.db.dao.CardSearchDao;
import tcg.db.dbo.Card;

@Controller
public class SearchController {

	@Autowired
	private SqlSession session;

	// @RequestMapping(value = "/s", method = RequestMethod.GET)
	// public String testSearch(@Param("name") String name) {
	// return "redirect:/c/" +
	// session.getMapper(CardSearchDao.class).findFirstId('%' + name + '%');
	// }

	@ResponseBody
	@RequestMapping(value = "/s", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Card> quickSearch(@Param("name") String name) {
		return session.getMapper(CardSearchDao.class).quick('%' + name + '%');
	}
}
