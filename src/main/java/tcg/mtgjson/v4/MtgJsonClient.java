package tcg.mtgjson.v4;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import tcg.mtgjson.v4.api.MtgJsonSet;

@Component
public class MtgJsonClient {
	private static final String ONE_SET = "https://mtgjson.com/json/{code}.json";
	private static final String SET_LIST = "https://mtgjson.com/json/SetList.json";

	MtgJsonSet[] setList() {
		HttpEntity<MtgJsonSet[]> entity = new HttpEntity<MtgJsonSet[]>(header());
		return new RestTemplate().exchange(SET_LIST, HttpMethod.GET, entity, MtgJsonSet[].class).getBody();
	}

	MtgJsonSet set(String code) {
		HttpEntity<MtgJsonSet> entity = new HttpEntity<MtgJsonSet>(header());
		return new RestTemplate().exchange(ONE_SET, HttpMethod.GET, entity, MtgJsonSet.class, code)
				.getBody();
	}

	private HttpHeaders header() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("user-agent", "Rest Client");
		return headers;
	}


}
