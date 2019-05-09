package tcg.mtgjson.v4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tcg.card.formater.CardFormater;
import tcg.db.dao.MtgJsonV4ImporterDao;
import tcg.db.dbo.CardTypeClass;
import tcg.mtgjson.v4.api.Language;
import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonLegalities;
import tcg.mtgjson.v4.api.MtgJsonRuling;
import tcg.mtgjson.v4.api.MtgJsonSet;

@Component
public class MtgJsonImporter {
	private static final int INTERVAL = 5 * 60 * 1000;
	// private static final int INTERVAL = 5 * 1000;
	private Logger logger = LoggerFactory.getLogger(MtgJsonImporter.class);

	private SqlSessionFactory factory;
	private MtgJsonClient client;
	private CardFormater formater;

	private Deque<String> codes = new LinkedList<>(Arrays.asList());

	@Scheduled(fixedRate = INTERVAL)
	void doImport() {
		if (codes.isEmpty()) {
			List<MtgJsonSet> sets = Arrays.asList(client.setList());
			Collections.shuffle(sets);
			codes.addAll(sets.stream().map(MtgJsonSet::getCode).collect(Collectors.toList()));
			logger.info("Found <" + codes.size() + "> to scan");
		} else {
			String code = codes.pop();
			importEdition(code);
		}
	}

	void importEdition(String code) {
		MtgJsonSet set = new MtgJsonFix().fix(client.set(code));
		logger.info("Start import set <" + set.getCode() + ">");
		long start = System.currentTimeMillis();
		try (SqlSession session = factory.openSession()) {
			MtgJsonV4ImporterDao dao = session.getMapper(MtgJsonV4ImporterDao.class);
			dao.updateEdition(set);
			for (MtgJsonCard card : set.getCards()) {
				if (dao.isTooOld(card) || true) {
					dao.updateCard(card, set);
					updateCardName(dao, card);
					updateTypes(card, dao);
					updateRules(card, dao);
					updateLegality(card, dao);
					updateAssistance(card, dao);
					removeOldCard(set, card, dao);
					session.commit();
				}
			}
			updateLinks(set, dao);
			session.commit();
		}
		logger.info("End import set <" + set.getCode() + "> in <"
				+ ((System.currentTimeMillis() - start) / 1000) + ">");
	}

	private void updateLinks(MtgJsonSet set, MtgJsonV4ImporterDao dao) {
		set.getCards().stream().filter(c -> c.getLayout().isLink()).forEach(card -> {
			String otherName = card.getNames().stream().filter(name -> !name.equals(card.getName()))
					.findFirst().orElseGet(null);
			MtgJsonCard otherCard = set.getCards().stream()
					.filter(o -> o.getName().equals(otherName)).findFirst().orElseGet(null);
			dao.updateLinkCard(card.getUuid(), otherCard.getUuid());
		});
	}

	private void updateCardName(MtgJsonV4ImporterDao dao, MtgJsonCard card) {
		card.getForeignData().stream().filter(data -> "French".equals(data.getLanguage()))
				.forEach(data -> dao.updateCardLang(card.getUuid(), data.getName(), Language.fr));
	}

	private void removeOldCard(MtgJsonSet set, MtgJsonCard card, MtgJsonV4ImporterDao dao) {
		dao.readOldIds(card, set).stream().filter(n -> n.indexOf('-') == -1).forEach(oldId -> {
			dao.updateDeckOldCardRef(oldId, card.getUuid());
			dao.deleteTypes(oldId);
			dao.deleteRules(oldId);
			dao.deleteLegality(oldId);
			dao.deleteAssistance(oldId);
			dao.deleteCardPrice(oldId);
			dao.deleteCardLang(oldId);
			dao.unlinkCard(oldId);
			dao.deleteCard(oldId);
		});
	}

	private void updateAssistance(MtgJsonCard card, MtgJsonV4ImporterDao dao) {
		dao.deleteAssistance(card.getUuid());
		assistances(card.getName()).stream()
				.forEach(a -> dao.insertAssistance(card.getUuid(), "en", a));
		card.getForeignData().stream().filter(data -> "French".equals(data.getLanguage()))
				.map(data -> data.getName()).flatMap(name -> assistances(name).stream())
				.forEach(assistance -> dao.insertAssistance(card.getUuid(), "fr", assistance));
	}

	private void updateTypes(MtgJsonCard card, MtgJsonV4ImporterDao dao) {
		dao.deleteTypes(card.getUuid());
		if (card.getSupertypes() != null) {
			for (String value : card.getSupertypes()) {
				dao.insertType(card, CardTypeClass.SuperType, value);
			}
		}
		if (card.getTypes() != null) {
			for (String value : card.getTypes()) {
				dao.insertType(card, CardTypeClass.Type, value);
			}
		}
		if (card.getSubtypes() != null) {
			for (String value : card.getSubtypes()) {
				dao.insertType(card, CardTypeClass.SubType, value);
			}
		}
	}

	private void updateRules(MtgJsonCard card, MtgJsonV4ImporterDao dao) {
		dao.deleteRules(card.getUuid());
		if (card.getRulings() != null) {
			for (MtgJsonRuling rule : card.getRulings()) {
				dao.insertRule(card, rule);
			}
		}
	}

	private void updateLegality(MtgJsonCard card, MtgJsonV4ImporterDao dao) {
		dao.deleteLegality(card.getUuid());
		MtgJsonLegalities legalities = card.getLegalities();
		if (legalities.getCommander() != null) {
			dao.insertLegality(card.getUuid(), "Commander", legalities.getCommander());
		}
		if (legalities.getDuel() != null) {
			dao.insertLegality(card.getUuid(), "Duel Commander", legalities.getDuel());
		}
		if (legalities.getLegacy() != null) {
			dao.insertLegality(card.getUuid(), "Legacy", legalities.getLegacy());
		}
		if (legalities.getModern() != null) {
			dao.insertLegality(card.getUuid(), "Modern", legalities.getModern());
		}
		if (legalities.getStandard() != null) {
			dao.insertLegality(card.getUuid(), "Standard", legalities.getStandard());
		}
		if (legalities.getVintage() != null) {
			dao.insertLegality(card.getUuid(), "Vintage", legalities.getVintage());
		}
	}

	List<String> assistances(String name) {
		String normalized = formater.normalize(name);
		List<String> assistances = new ArrayList<>();
		for (int l = 3; l <= normalized.length(); l++) {
			for (int i = 0; i <= normalized.length() - l; i++) {
				assistances.add(normalized.substring(i, i + l));
			}
		}
		return assistances.stream().distinct().collect(Collectors.toList());
	}

	@Autowired
	public void setFactory(SqlSessionFactory factory) {
		this.factory = factory;
	}

	@Autowired
	public void setClient(MtgJsonClient client) {
		this.client = client;
	}

	@Autowired
	public void setFormater(CardFormater formater) {
		this.formater = formater;
	}

}
