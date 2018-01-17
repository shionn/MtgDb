package tcg.price.goldfish;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import tcg.price.goldfish.MtgGoldFishCrawler;

public class MtgGoldFishCrawlerTest {

	@Test
	public void testPrice() throws Exception {
		assertThat(new MtgGoldFishCrawler().price().getPrice()).isPositive();
	}

}
