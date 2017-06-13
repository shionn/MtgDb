package tcg.mtgjson.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class AllSetsArrayTest {

	private static final String SOURCE = "http://mtgjson.com/json/AllSetsArray-x.json";

	@Test
	public void testRead() {
		Set[] sets = new RestTemplate().getForEntity(SOURCE, Set[].class).getBody();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD");
		List<String> types = Arrays.stream(sets).map(s -> s.getType()).distinct().collect(Collectors.toList());
		assertThat(types).contains("core", "expansion", "reprint", "box", "un", "from the vault", "premium deck",
				"duel deck", "starter", "commander", "planechase", "archenemy", "promo", "vanguard", "masters",
				"conspiracy", "masterpiece");
	}

}
