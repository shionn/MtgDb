package tcg.price.goldfish;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import tcg.db.dbo.Card;
import tcg.db.dbo.Edition;

public class MtgGoldFishCrawlerTest {

	@Test
	public void testPrice() throws Exception {
		assertThat(
				new MtgGoldFishCrawler().priceForNotFoil(card("Conflux", "Path to Exile")).get(0)
						.getPrice())
						.isPositive();
		assertThat(
				new MtgGoldFishCrawler().priceForNotFoil(card("Alliances", "Force of Will")).get(0)
						.getPrice())
						.isPositive();
	}

	private Card card(String edition, String card) {
		Edition e = new Edition();
		e.setName(edition);
		Card c = new Card();
		c.setEdition(e);
		c.setName(card);
		return c;
	}

}
