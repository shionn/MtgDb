package tcg.edition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.EditionListDao;
import tcg.db.dbo.EditionGroup;
import tcg.db.dbo.EditionType;

@Controller
public class EditionController {
	@Autowired
	private SqlSession session;

	@RequestMapping("/e")
	public ModelAndView editions() {
		EditionListDao dao = session.getMapper(EditionListDao.class);
		List<List<EditionGroup>> groups = new ArrayList<>();
		groups.add(dao.readBlock(EditionType.core));
		groups.add(dao.readBlock(EditionType.expansion));
		groups.add(Arrays.asList(dao.readGroup(EditionType.archenemy),
				dao.readGroup(EditionType.commander), dao.readGroup(EditionType.funny),
				dao.readGroup(EditionType.planechase), dao.readGroup(EditionType.vanguard)));
		groups.add(Arrays.asList(dao.readGroup(EditionType.fromthevault), //
				dao.readGroup(EditionType.premiumdeck), //
				dao.readGroup(EditionType.spellbook), //
				dao.readGroup(EditionType.masters), //
				dao.readGroup(EditionType.dueldeck), //
				dao.readGroup(EditionType.box)));
		return new ModelAndView("editions") //
				.addObject("groups", groups) //
				.addObject("editions", dao.listEditions());
	}

}
