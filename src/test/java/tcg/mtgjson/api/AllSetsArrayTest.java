package tcg.mtgjson.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import tcg.mtgjson.api.Set.Type;

public class AllSetsArrayTest {

	@Test
	public void testRead() {
		Set[] sets = new RestTemplate().getForEntity("http://mtgjson.com/json/AllSetsArray-x.json", Set[].class)
				.getBody();
		List<String> codes = Arrays.stream(sets).map(s -> s.getCode()).collect(Collectors.toList());
		assertThat(codes).contains("LEA").contains("KLD").contains("ISD");
		List<Type> types = Arrays.stream(sets).map(s -> s.getType()).distinct().collect(Collectors.toList());
		assertThat(types).contains(Set.Type.values());
	}


}
