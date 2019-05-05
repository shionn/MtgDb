package tcg.mtgjson.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import tcg.mtgjson.v2.api.MtgJsonSet;
import tcg.mtgjson.v2.api.SetType;

public class MtgJsonClientTest {

	@Test
	public void testSet() throws Exception {
		MtgJsonSet set = new MtgJsonClient().set("UNH");
		assertThat(set.getCode()).isEqualTo("UNH");

		assertThat(new MtgJsonClient().set("MPS").getKeyruneCode().length())
				.isEqualByComparingTo(3);
		assertThat(new MtgJsonClient().set("PFRF").getType()).isEqualTo(SetType.promo);
		// assertThat(new MtgJsonClient().set("pFNM").getCards().stream().map(Card::getSource)
		// .filter(Objects::nonNull).distinct().map(s -> s.length())
		// .collect(Collectors.toList())).containsOnly(24, 129);

	}

	@Test
	public void testSetList() throws Exception {
		MtgJsonSet[] sets = new MtgJsonClient().setList();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD").contains("PFRF");
	}

}
