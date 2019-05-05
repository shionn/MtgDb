package tcg.mtgjson.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tcg.card.formater.CardFormater;
import tcg.db.dao.MtgJsonV1ImporterDao;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.GameLegality;
import tcg.db.dbo.Legality;
import tcg.mtgjson.v1.api.Card;
import tcg.mtgjson.v1.api.Language;
import tcg.mtgjson.v1.api.Ruling;
import tcg.mtgjson.v1.api.Set;

@Component
public class EditionImporter {

	private static final int INTERVAL = 5 * 60 * 1000;
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
			Arrays.stream(client.setList()).map(Set::getCode).forEach(code -> codes.add(code));
			logger.info("Found <" + codes.size() + "> to scan");
		} else {
			Set set = client.set(codes.pop());
			logger.info("Start import set <" + set.getCode() + ">");
			try (SqlSession session = factory.openSession()) {
				MtgJsonV1ImporterDao dao = session.getMapper(MtgJsonV1ImporterDao.class);
				dao.edition(set);
				for (Card card : set.getCards()) {
					dao.card(card, set);
					card.getForeignNames().stream()
							.filter(name -> name.getLanguage() == Language.fr)
							.forEach(name -> dao.cardName(name, card));
					dao.deleteTypes(card.getId());
					if (card.getSupertypes() != null) {
						for (String value : card.getSupertypes()) {
							dao.type(card, CardTypeClass.SuperType, value);
						}
					}
					if (card.getTypes() != null) {
						for (String value : card.getTypes()) {
							dao.type(card, CardTypeClass.Type, value);
						}
					}
					if (card.getSubtypes() != null) {
						for (String value : card.getSubtypes()) {
							dao.type(card, CardTypeClass.SubType, value);
						}
					}
					dao.deleteRules(card.getId());
					if (card.getRulings() != null) {
						for (Ruling rule : card.getRulings()) {
							dao.rule(card, rule);
						}
					}
					dao.deleteLegality(card.getId());
					if (card.getLegalities() != null && !card.getLegalities().isEmpty()) {
						for (Legality legality : card.getLegalities()) {
							dao.legality(card, legality);
						}
					} else {
						defaultLegalities().stream().forEach(l -> dao.legality(card, l));
					}
					dao.deleteAssistance(card.getId());
					assistances(card.getName()).stream()
							.forEach(a -> dao.assistance(card.getId(), "en", a));
					card.getForeignNames().stream()
							.filter(name -> name.getLanguage() == Language.fr)
							.map(name -> name.getName())
							.flatMap(name -> assistances(name).stream())
							.forEach(assistance -> dao.assistance(card.getId(), "fr", assistance));
				}
				List<Card> doubleFaceds = set.getCards().stream()
						.filter(c -> CardLayout.links().contains(c.getLayout()))
						.collect(Collectors.toList());
				for (Card card : doubleFaceds) {
					try {
						String backName = card.getNames().stream()
								.filter(n -> !n.equals(card.getName())).findFirst().get();
						Card linkCard = doubleFaceds.stream()
								.filter(c -> c.getName().equals(backName)).findFirst().get();
						dao.updateLinkCard(card, linkCard);
					} catch (NoSuchElementException e) {
						logger.error("No Link card for : " + card.getName());
					}
				}
				session.commit();
			}
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

	private List<Legality> defaultLegalities() {
		return Arrays.asList(legality("Commander"), legality("Legacy"), legality("Vintage"),
				legality("Modern"), legality("Standard"));
	}

	private Legality legality(String format) {
		Legality legality = new Legality();
		legality.setFormat(format);
		legality.setLegality(GameLegality.Legal);
		return legality;
	}

}
