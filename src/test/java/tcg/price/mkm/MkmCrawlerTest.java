package tcg.price.mkm;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import tcg.price.mkm.MkmCrawler;

public class MkmCrawlerTest {

	@Test
	public void testPrice() throws IOException {
		assertThat(new MkmCrawler().price().getPrice()).isPositive();
	}
}
