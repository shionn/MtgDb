package tcg.deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tcg.db.dao.CardSearchDao;
import tcg.db.dao.DeckDao;
import tcg.db.dao.DeckEditDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.DeckEntry;
import tcg.db.dbo.DeckEntryCategory;
import tcg.security.User;

@Controller
public class DeckImportController {
	private static final DeckExportFormat[] FORMATS = new DeckExportFormat[] {
			DeckExportFormat.simple };
	@Autowired
	private SqlSession session;
	@Autowired
	private User user;

	@RequestMapping(value = "/d/import/{id}", method = RequestMethod.GET)
	public ModelAndView impoort(@PathVariable("id") int id) {
		return new ModelAndView("deck/import-modal")//
				.addObject("formats", FORMATS)//
				.addObject("deck", session.getMapper(DeckDao.class).readDeckBase(id));//
	}

	@RequestMapping(value = "/d/import/{id}", method = RequestMethod.POST)
	public ModelAndView impoort(@PathVariable("id") int deck,
			@RequestParam("file") MultipartFile file,
			@RequestParam("format") DeckExportFormat format) throws IOException {
		if (!session.getMapper(DeckEditDao.class).checkAllow(user.getUser(), deck)) {
			return new ModelAndView("redirect:/not-allowed");
		}
		List<String> errors;
		try (InputStreamReader isr = new InputStreamReader(file.getInputStream());
				BufferedReader r = new BufferedReader(isr)) {
			switch (format) {
			case simple:
				errors = importSimple(deck, r);
				break;
			default:
				throw new IllegalArgumentException("format <" + format + ">");
			}
		}
		if (errors.isEmpty()) {
			return new ModelAndView("redirect:/d/" + deck);
		} else {
			return new ModelAndView("deck/import-error").addObject("errors", errors);
		}
	}

	private List<String> importSimple(int deck, BufferedReader r) throws IOException {
		List<String> errors = new ArrayList<>();
		String line = r.readLine();
		DeckEntryCategory category = DeckEntryCategory.main;
		while (line != null) {
			try {
				if (StringUtils.isBlank(line)) {
					category = DeckEntryCategory.side;
				} else if (line.startsWith("[")) {
					category = DeckEntryCategory.valueOf(line.replaceAll("[\\[\\]]", ""));
				} else {
					DeckEntry entry = readEntry(deck, category, line);
					session.getMapper(DeckEditDao.class).updateEntry(entry);
				}
			} catch (RuntimeException e) {
				errors.add(line);
			}
			line = r.readLine();
		}
		session.getMapper(DeckEditDao.class).updateDeck(deck);
		session.commit();
		return errors;
	}

	private DeckEntry readEntry(int deck, DeckEntryCategory category, String line) {
		DeckEntry entry = new DeckEntry();
		entry.setDeck(deck);
		entry.setCategory(category);
		entry.setQty(extractQty(line));
		entry.setCard(retrieveCard(extractName(line)));
		return entry;
	}


	private int extractQty(String line) {
		if (line.matches("^[0-9]+[ \t].*")) {
			return Integer.parseInt(split(line)[0]);
		}
		return 1;
	}

	private String extractName(String line) {
		String name = line;
		if (line.matches("^[0-9]+[ \t].*")) {
			name = split(line)[1];
		}
		return name;
	}

	private String[] split(String line) {
		String[] split;
		if (line.indexOf('\t') != -1) {
			split = StringUtils.split(line, '\t');
		} else {
			split = StringUtils.split(line, " ", 2);
		}
		return split;
	}

	private Card retrieveCard(String name) {
		return session.getMapper(CardSearchDao.class).findFirstName(name);
	}

}
