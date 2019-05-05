package tcg.mtgjson.v1;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import tcg.mtgjson.v1.api.Set;

@Component
public class MtgJsonClient {
	private static final String ONE_SET = "https://mtgjson.com/json/{code}-x.json";
	private static final String SET_LIST = "https://mtgjson.com/json/SetList.json";

	Set[] setList() {
		return new RestTemplate().getForEntity(SET_LIST, Set[].class).getBody();
	}

	Set set(String code) {
		return new RestTemplate().getForEntity(ONE_SET, Set.class, code).getBody();
	}

}
