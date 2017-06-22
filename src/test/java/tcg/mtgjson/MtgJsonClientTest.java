package tcg.mtgjson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Set;

public class MtgJsonClientTest {

	@Test
	public void testRead() {
		Set[] sets = new MtgJsonClient().allSet();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD");
		List<String> types = Arrays.stream(sets).map(s -> s.getType()).distinct().collect(Collectors.toList());
		assertThat(types).contains("core", "expansion", "reprint", "box", "un", "from the vault", "premium deck",
				"duel deck", "starter", "commander", "planechase", "archenemy", "promo", "vanguard", "masters",
				"conspiracy", "masterpiece");

		List<Card> cards = Arrays.stream(sets).map(s -> s.getCards()).flatMap(Collection::stream)
				.collect(Collectors.toList());
		System.out.println(cards);
		assertThat(cards.stream().map(c -> c.getRarity()).distinct().collect(Collectors.toList()))
				.containsOnly("Common", "Uncommon", "Rare", "Mythic Rare", "Special", "Basic Land");

	}

}
