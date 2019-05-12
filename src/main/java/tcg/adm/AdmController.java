package tcg.adm;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.AdmEditionDao;
import tcg.mtgjson.v4.MtgJsonImporter;
import tcg.security.User;

@Controller
public class AdmController {

	@Autowired
	private User user;

	@Autowired
	private SqlSession session;

	@Autowired
	private MtgJsonImporter importer;

	@RequestMapping(value = "/adm", method = RequestMethod.GET)
	public ModelAndView admin() {
		if (!user.isAdmin())
			return new ModelAndView("home");
		AdmEditionDao dao = session.getMapper(AdmEditionDao.class);
		return new ModelAndView("adm/adm").addObject("editiondeletable", dao.listDeletable())
				.addObject("editionreplacable", dao.listReplacable())
				.addObject("editions", dao.listAll());
	}

	@RequestMapping(value = "/adm/edition/drop", method = RequestMethod.POST)
	public String dropEdition(@RequestParam("deleted") String deleted) {
		if (!user.isAdmin())
			return "redirect:/home";
		AdmEditionDao dao = session.getMapper(AdmEditionDao.class);
		dao.updateDeckEntry(deleted);
		session.commit();
		dao.deleteCardAssistance(deleted);
		dao.deleteCardLang(deleted);
		dao.deleteCardLegality(deleted);
		dao.deleteCardPrice(deleted);
		dao.deleteCardRule(deleted);
		dao.deleteCardType(deleted);
		dao.deleteCard(deleted);
		dao.deleteEdition(deleted);
		session.commit();
		return "redirect:/adm";
	}

	@RequestMapping(value = "/adm/edition/update", method = RequestMethod.POST)
	public String updateEdition(@RequestParam("edition") String edition) {
		if (!user.isAdmin())
			return "redirect:/home";
		importer.forceUpdate(edition);
		return "redirect:/adm";
	}

}
