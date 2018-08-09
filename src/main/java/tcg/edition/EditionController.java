package tcg.edition;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.EditionListDao;

@Controller
public class EditionController {
	@Autowired
	private SqlSession session;

	@RequestMapping("/e")
	public ModelAndView editions() {
		return new ModelAndView("editions").addObject("editions",
				session.getMapper(EditionListDao.class).listEditions());
	}

}
