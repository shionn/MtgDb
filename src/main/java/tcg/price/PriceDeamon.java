package tcg.price;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import tcg.db.dao.CardDao;
import tcg.db.dao.CardPriceDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.price.goldfish.MtgGoldFishCrawler;
import tcg.price.mkm.MkmCrawler;

@Component
@ApplicationScope
public class PriceDeamon {

	@Autowired
	private SqlSessionFactory factory;

	@Autowired
	private MtgGoldFishCrawler fishCrawler;
	@Autowired
	private MkmCrawler mkmCrawler;

	private Queue<String> priceCardToUpdate = new ConcurrentLinkedQueue<>();
	private Map<String, List<CardPrice>> results = new ConcurrentHashMap<>();

	@Scheduled(fixedRate = 1000)
	public void update() {
		try (SqlSession session = factory.openSession()) {
			String id = priceCardToUpdate.poll();
			if (id != null) {
				synchronized (this) {
					if (results.size() > 50) {
						results.clear();
					}
					CardDao dao = session.getMapper(CardDao.class);
					Card card = dao.read(id);
					List<CardPrice> prices = new ArrayList<>();
					prices.addAll(mkmCrawler.price(card));
					prices.addAll(fishCrawler.price(card));
					prices.stream().forEach(session.getMapper(CardPriceDao.class)::price);
					session.commit();
					results.put(id, prices);
				}
			}
		}
	}

	public void request(Card card) {
		priceCardToUpdate.add(card.getId());
	}

	public List<CardPrice> get(String id) {
		synchronized (this) {
			List<CardPrice> prices = results.get(id);
			results.remove(id);
			return prices;
		}
	}
}
