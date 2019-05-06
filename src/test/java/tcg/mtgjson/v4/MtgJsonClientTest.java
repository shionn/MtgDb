package tcg.mtgjson.v4;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import tcg.db.dbo.EditionType;
import tcg.mtgjson.v4.api.MtgJsonCard;
import tcg.mtgjson.v4.api.MtgJsonSet;

public class MtgJsonClientTest {

	@Test
	public void testSet() throws Exception {
		MtgJsonSet set = new MtgJsonClient().set("UNH");
		assertThat(set.getCode()).isEqualTo("UNH");

		assertThat(new MtgJsonClient().set("MPS").getKeyruneCode().length())
				.isEqualByComparingTo(3);
		assertThat(new MtgJsonClient().set("PFRF").getType()).isEqualTo(EditionType.promo);
		assertThat(new MtgJsonClient().set("HTR").getCards().stream().map(MtgJsonCard::getLoyalty)
				.filter(Objects::nonNull).distinct().collect(Collectors.toList())).containsOnly("3",
						"1d4+1");
		new MtgJsonClient().set("WC99").getCards().stream().map(MtgJsonCard::getNumber)
				.forEach(n -> assertThat(n.length()).as(n).isLessThanOrEqualTo(8));
	}

	@Test
	public void testSetList() throws Exception {
		MtgJsonSet[] sets = new MtgJsonClient().setList();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD").contains("PFRF");
	}

}
