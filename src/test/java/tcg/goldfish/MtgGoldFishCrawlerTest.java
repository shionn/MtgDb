package tcg.goldfish;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MtgGoldFishCrawlerTest {

	@Test
	public void testPrice() throws Exception {
		assertThat(new MtgGoldFishCrawler().price().getPrice()).isPositive();
	}

}
