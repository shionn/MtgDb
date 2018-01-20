package tcg.mtgjson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import tcg.mtgjson.api.Card;
import tcg.mtgjson.api.Set;

public class MtgJsonClientTest {

	@Test
	public void testSet() throws Exception {
		Set set = new MtgJsonClient().set("UNH");
		assertThat(set.getCode()).isEqualTo("UNH");

		assertThat(new MtgJsonClient().set("MPS").getMagicCardsInfoCode().length()).isEqualByComparingTo(6);
		assertThat(new MtgJsonClient().set("FRF_UGIN").getCode().length()).isEqualByComparingTo(8);
		assertThat(new MtgJsonClient().set("pFNM").getCards().stream().map(Card::getSource)
				.filter(Objects::nonNull).distinct().map(s -> s.length()).collect(Collectors.toList()))
						.containsOnly(24, 129);

	}

	@Test
	public void testSetList() throws Exception {
		Set[] sets = new MtgJsonClient().setList();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD");
	}

}
