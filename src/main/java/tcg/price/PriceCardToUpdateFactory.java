package tcg.price;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
public class PriceCardToUpdateFactory {

	@Bean(name = "PriceCardToUpdate")
	@ApplicationScope
	public Queue<String> priceCardToUpdate() {
		return new ConcurrentLinkedQueue<>();
	}

}
