package tcg.mtgjson;

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

import tcg.db.dao.ImporterDao;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardTypeClass;
import tcg.db.dbo.Legality;
import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Language;
import tcg.mtgjson.api.Ruling;
import tcg.mtgjson.api.Set;

@Component
public class EditionImporter {

	private static final int INTERVAL = 5 * 60
			* 1000;
	private Logger logger = LoggerFactory.getLogger(EditionImporter.class);

	@Autowired
	private SqlSessionFactory factory;
	@Autowired
	private MtgJsonClient client;

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
				ImporterDao dao = session.getMapper(ImporterDao.class);
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
					if (card.getLegalities() != null) {
						for (Legality legality : card.getLegalities()) {
							dao.legality(card, legality);
						}
					}
				}
				List<Card> doubleFaceds = set.getCards().stream()
						.filter(c -> c.getLayout() == CardLayout.doublefaced)
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

}
