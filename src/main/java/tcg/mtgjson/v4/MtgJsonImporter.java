package tcg.mtgjson.v4;

import java.util.ArrayList;
import java.util.Arrays;
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
import tcg.mtgjson.v3.EditionImporter;
import tcg.mtgjson.v3.api.Language;
import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonLegalities;
import tcg.mtgjson.v4.api.MtgJsonRuling;
import tcg.mtgjson.v4.api.MtgJsonSet;

@Component
public class MtgJsonImporter {
	// private static final int INTERVAL = 5 * 60 * 1000;
	private static final int INTERVAL = 5;
	private Logger logger = LoggerFactory.getLogger(EditionImporter.class);

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;
	@Autowired
	private CardFormater formater;

	private Deque<String> codes = new LinkedList<>();

	@Scheduled(fixedRate = INTERVAL)
	void doImport() {
		if (codes.isEmpty()) {
			Arrays.stream(client.setList()).map(MtgJsonSet::getCode)
					.forEach(code -> codes.add(code));
			logger.info("Found <" + codes.size() + "> to scan");
		} else {
			MtgJsonSet set = client.set(codes.pop());
			logger.info("Start import set <" + set.getCode() + ">");
			try (SqlSession session = factory.openSession()) {
				MtgJsonV4ImporterDao dao = session.getMapper(MtgJsonV4ImporterDao.class);
				dao.updateEdition(set);
				for (MtgJsonCard card : set.getCards()) {
					dao.updateCard(card, set);
					updateCardName(dao, card);
					updateTypes(card, dao);
					updateRules(card, dao);
					updateLegality(card, dao);
					updateAssistance(card, dao);
					removeOldCard(set, card, dao);
				}
				session.commit();
			}
		}
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

}
