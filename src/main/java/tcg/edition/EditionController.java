package tcg.edition;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.EditionListDao;
import tcg.db.dbo.EditionType;

@Controller
public class EditionController {
	@Autowired
	private SqlSession session;

	@RequestMapping("/e")
	public ModelAndView editions() {
		EditionListDao dao = session.getMapper(EditionListDao.class);
		return new ModelAndView("editions") //
				.addObject("core", dao.readBlock(EditionType.core)) //
				.addObject("expansion", dao.readBlock(EditionType.expansion)) //
				.addObject("fun", Arrays.asList(dao.readGroup(EditionType.archenemy),
						dao.readGroup(EditionType.commander), dao.readGroup(EditionType.funny),
						dao.readGroup(EditionType.planechase), dao.readGroup(EditionType.vanguard))) //
				.addObject("collections", Arrays.asList(dao.readGroup(EditionType.fromthevault),
						dao.readGroup(EditionType.premiumdeck),
						dao.readGroup(EditionType.spellbook), dao.readGroup(EditionType.masters),
						dao.readGroup(EditionType.box))) //
				.addObject("editions", dao.listEditions());
	}

}
