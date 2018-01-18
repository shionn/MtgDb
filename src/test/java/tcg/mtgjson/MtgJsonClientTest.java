package tcg.mtgjson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import tcg.mtgjson.api.Set;

public class MtgJsonClientTest {

	@Test
	public void testSet() throws Exception {
		Set set = new MtgJsonClient().set("UNH");
		assertThat(set.getCode()).isEqualTo("UNH");

		List<String> rarities = set.getCards().stream().map(c -> c.getRarity()).distinct()
				.collect(Collectors.toList());
		assertThat(rarities).containsOnly("Common", "Uncommon", "Rare", "Basic Land");

		assertThat(new MtgJsonClient().set("MPS").getMagicCardsInfoCode().length()).isEqualByComparingTo(6);
		// assertThat(new
		// MtgJsonClient().set("UGL").getCards().stream().map(Card::getManaCost).filter(Objects::nonNull)
		// .map(String::length).collect(Collectors.maxBy(Integer::max)).get()).isEqualTo(45);

	}

	@Test
	public void testSetList() throws Exception {
		Set[] sets = new MtgJsonClient().setList();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD");
	}

}
