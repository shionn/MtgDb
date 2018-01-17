package tcg.price.goldfish;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import tcg.db.dbo.Card;
import tcg.db.dbo.Edition;

public class MtgGoldFishCrawlerTest {

	@Test
	public void testPrice() throws Exception {
		Edition edition = new Edition();
		edition.setName("Conflux");
		Card card = new Card();
		card.setEdition(edition);
		card.setName("Path to Exile");
		assertThat(new MtgGoldFishCrawler().price(card).get(0).getPrice()).isPositive();
	}

}
