package tcg.mtgjson;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import tcg.mtgjson.api.Set;

@Component
public class MtgJsonClient {
	private static final String SOURCE = "http://mtgjson.com/json/AllSetsArray-x.json";

	Set[] allSet() {
		return new RestTemplate().getForEntity(SOURCE, Set[].class).getBody();
	}
}
