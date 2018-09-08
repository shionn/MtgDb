package tcg.price.mkm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import tcg.db.dbo.Card;
import tcg.db.dbo.CardLayout;
import tcg.db.dbo.CardPrice;
import tcg.db.dbo.Edition;

public class MkmCrawlerTest {

	private MkmCrawler crawler = new MkmCrawler();

	@Test
	public void testPrice() throws Exception {
		Edition edition = new Edition();
		edition.setMkmName("Battle for Zendikar");
		Card card = new Card();
		card.setEdition(edition);
		card.setName("Ob Nixilis Reignited");
		assertThat(crawler.price(card).get(0).getPrice()).isPositive();
	}

	@Test
	public void testDoublePrice() throws Exception {
		Card card = doubleFace("Rivals of Ixalan", "Journey to Eternity", "Atzal, Cave of Eternity");
		assertThat(crawler.price(card).get(0).getPrice()).as("double // url").isPositive();
		card = doubleFace("Ixalan", "Search for Azcanta", "Azcanta, the Sunken Ruin");
		assertThat(crawler.price(card).get(0).getPrice()).as("Simple / url").isPositive();
	}

	@Test
	public void testPriceDivers() throws Exception {
		Card card = card("Commander", "Serra Angel");
		assertThat(crawler.price(card).get(0).getPrice()).isPositive();
		card = card("Kaladesh", "Torrential Gearhulk");
		List<CardPrice> price = crawler.price(card);
		assertThat(price.get(0).getPrice()).isPositive();
		price.stream().forEach(p -> System.out.println(p.getPrice()));
	}

	private Card card(String editionName, String cardName) {
		Edition edition = new Edition();
		edition.setName(editionName);
		Card card = new Card();
		card.setEdition(edition);
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
