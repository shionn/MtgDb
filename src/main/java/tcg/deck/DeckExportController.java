package tcg.deck;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.DeckDao;
import tcg.db.dbo.Deck;
import tcg.db.dbo.DeckEntry;

@Controller
public class DeckExportController {
	@Autowired
	private SqlSession session;

	@RequestMapping(value = "/d/export/{id}", method = RequestMethod.GET)
	public ModelAndView open(@PathVariable("id") int id) {
		return new ModelAndView("deck/export-modal")//
				.addObject("formats", DeckExportFormat.values())//
				.addObject("deck", session.getMapper(DeckDao.class).readOne(id));//
	}

	@RequestMapping(value = "/d/export/{id}", method = RequestMethod.POST)
	@ResponseBody
	public HttpEntity<String> export(@PathVariable("id") int id,
			@RequestParam("format") DeckExportFormat format) {
		Deck deck = session.getMapper(DeckDao.class).read(id);
		StringBuilder export;
		switch (format) {
		case simple:
			export = new StringBuilder();
			for (DeckEntry e : deck.getMains()) {
				export.append(e.getQty()).append('\t').append(e.getCard().getName()).append('\n');
			}
			export.append('\n');
			for (DeckEntry e : deck.getSides()) {
				export.append(e.getQty()).append('\t').append(e.getCard().getName()).append('\n');
			}
			break;

		default:
			throw new IllegalArgumentException("format <" + format + ">");
		}

		HttpHeaders header = new HttpHeaders();
		header.set(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=export-"
						+ deck.getName() + '-'
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
		return new HttpEntity<String>(export.toString(), header);
	}

}
