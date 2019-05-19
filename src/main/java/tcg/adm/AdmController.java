package tcg.adm;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.AdmCardDao;
import tcg.db.dao.AdmEditionDao;
import tcg.mtgjson.v4.MtgJsonImporter;

@Controller
public class AdmController {

	@Autowired
	private SqlSession session;

	@Autowired
	private MtgJsonImporter importer;

	@RequestMapping(value = "/adm", method = RequestMethod.GET)
	public ModelAndView admin() {
		AdmEditionDao dao = session.getMapper(AdmEditionDao.class);
		return new ModelAndView("adm/adm").addObject("editiondeletable", dao.listDeletable())
				.addObject("editionreplacable", dao.listReplacable())
				.addObject("editions", dao.listAll());
	}

	@RequestMapping(value = "/adm/edition/drop", method = RequestMethod.POST)
	public String dropEdition(@RequestParam("deleted") String deleted) {
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
		dao.removeLink(deleted);
		dao.deleteEdition(deleted);
		session.commit();
		return "redirect:/adm";
	}

	@RequestMapping(value = "/adm/card/{id}/drop", method = RequestMethod.GET)
	public String drop(@PathVariable("id") String id) {
		AdmCardDao dao = session.getMapper(AdmCardDao.class);
		dao.deleteAssistance(id);
		dao.deleteLang(id);
		dao.deleteLegality(id);
		dao.deletePrice(id);
		dao.deleteRule(id);
		dao.deleteType(id);
		dao.updateDeckEntry(id);
		dao.delete(id);
		session.commit();
		return "redirect:/adm";
	}

	@RequestMapping(value = "/adm/edition/update", method = RequestMethod.POST)
	public String updateEdition(@RequestParam("edition") String edition) {
		importer.forceUpdate(edition);
		return "redirect:/adm";
	}

}
