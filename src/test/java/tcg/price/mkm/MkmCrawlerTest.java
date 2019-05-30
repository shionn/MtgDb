package tcg.price.mkm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tcg.db.dbo.Card;
import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.Edition;
import tcg.security.MailSender;

public class MkmCrawlerTest {

	private MkmCrawler crawler = new MkmCrawler();

	@Before
	public void setup() {
		crawler.setMailSender(mock(MailSender.class));
	}

	@Test
	public void testPrice() throws Exception {
		Edition edition = new Edition();
		edition.setMkmName("Battle for Zendikar");
		Card card = new Card();
		card.setEdition(edition);
		card.setLayout(CardLayout.normal);
		card.setName("Ob Nixilis Reignited");
		List<CardPrice> prices = crawler.priceForNotFoil(card);
		prices.addAll(crawler.priceForFoil(card));
		BigDecimal paper = prices.get(0).getPrice();
		assertThat(paper).isPositive();
		BigDecimal foil = prices.get(1).getPrice();
		assertThat(foil).isPositive().isGreaterThan(paper);
	}

	@Test
	public void testDoublePrice() throws Exception {
		Card card = doubleFace("Rivals of Ixalan", "Journey to Eternity",
				"Atzal, Cave of Eternity");
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).as("double // url")
				.isPositive();
		card = doubleFace("Ixalan", "Search for Azcanta", "Azcanta, the Sunken Ruin");
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).as("double / url").isPositive();
		card = doubleFace("Magic Origins", "Jace, Vryn's Prodigy", "Jace, Telepath Unbound");
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).as("double / url").isPositive();
		card = doubleFace("Shadows over Innistrad", "Arlinn Kord", "Arlinn, Embraced by the Moon");
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).as("double / url").isPositive();
	}

	@Test
	public void testPriceDivers() throws Exception {
		Card card = card("Commander", "Serra Angel", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		card = card("Kaladesh", "Torrential Gearhulk", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		card = card("Alliances", "Force of Will", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		// dans le cas thalia le 's est remplacé par -s alors que pour jace le ' est supprimé
		card = card("Shadows over Innistrad", "Thalia's Lieutenant", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		card = card("Urza's Legacy", "Mother of Runes", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		card = card("Duel Decks: Elves vs. Goblins", "Akki Coalflinger", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
		card = card("Commander 2017", "Earthquake", Foil.nofoil);
		assertThat(crawler.priceForNotFoil(card).get(0).getPrice()).isPositive();
	}

	@Test
	public void testPriceFromMail() throws Exception {
		testAndAssert("Shadowmoor", "Cinderbones");
		testAndAssert("Future Sight", "Haze of Rage");
	}

	@Test
	public void testPriceDiversFoil() throws Exception {
		Card card = card("Judge Rewards Promos", "Argothian Enchantress", Foil.onlyfoil);
		assertThat(crawler.priceForFoil(card).get(0).getPrice()).isPositive();
	}

	private void testAndAssert(String edition, String card) {
		List<CardPrice> prices = crawler.priceForNotFoil(card(edition, card, Foil.nofoil));
		assertThat(prices).isNotEmpty();
		assertThat(prices.get(0).getPrice()).isPositive();
	}

	private Card card(String editionName, String cardName, Foil foil) {
		Edition edition = new Edition();
		edition.setName(editionName);
		Card card = new Card();
		card.setFoil(foil);
		card.setEdition(edition);
		card.setLayout(CardLayout.normal);
		card.setName(cardName);
		return card;
	}

	private Card doubleFace(String edition, String front, String back) {
		Card otherFace = new Card();
		otherFace.setName(back);
		Edition ed = new Edition();
		ed.setMkmName(edition);
		Card card = new Card();
		card.setLayout(CardLayout.doublefaced);
		card.setEdition(ed);
		card.setName(front);
		card.setLinkCard(otherFace);
		return card;
	}

}
