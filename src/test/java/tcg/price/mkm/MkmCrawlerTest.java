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
	public void testRixPrice() throws IOException {
		Card otherFace = new Card();
		otherFace.setName("Atzal, Cave of Eternity");
		Edition edition = new Edition();
		edition.setMkmName("Rivals of Ixalan");
		Card card = new Card();
		card.setEdition(edition);
		card.setName("Journey to Eternity");
		card.setLinkCard(otherFace);
		assertThat(new MkmCrawler().price(card).get(0).getPrice()).isPositive();
	}

}
