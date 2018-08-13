package tcg.price.mkm;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import tcg.db.dbo.Card;
import tcg.db.dbo.Edition;

public class MkmCrawlerTest {

	@Test
	public void testPrice() throws IOException {
		Edition edition = new Edition();
		edition.setMkmName("Battle for Zendikar");
		Card card = new Card();
		card.setEdition(edition);
		card.setName("Ob Nixilis Reignited");
		assertThat(new MkmCrawler().price(card).get(0).getPrice()).isPositive();
	}

	@Test
	public void testDoublePrice() throws IOException {
		Card card = doubleFace("Rivals of Ixalan", "Journey to Eternity", "Atzal, Cave of Eternity");
		assertThat(new MkmCrawler().price(card).get(0).getPrice()).as("double // url").isPositive();
		card = doubleFace("Ixalan", "Search for Azcanta", "Azcanta, the Sunken Ruin");
		assertThat(new MkmCrawler().price(card).get(0).getPrice()).as("Simple / url").isPositive();
	}

	private Card doubleFace(String edition, String front, String back) {
		Card otherFace = new Card();
		otherFace.setName(back);
		Edition ed = new Edition();
		ed.setMkmName(edition);
		Card card = new Card();
		card.setEdition(ed);
		card.setName(front);
		card.setLinkCard(otherFace);
		return card;
	}

}
