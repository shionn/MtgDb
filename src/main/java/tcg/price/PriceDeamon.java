package tcg.price;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import tcg.db.dao.CardDao;
import tcg.db.dao.CardPriceDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.Card.Foil;
import tcg.db.dbo.CardPrice;
import tcg.price.goldfish.MtgGoldFishCrawler;
import tcg.price.mkm.MkmCrawler;

@Component
@ApplicationScope
public class PriceDeamon implements DisposableBean {

	@Autowired
	private SqlSessionFactory factory;

	@Autowired
	private MtgGoldFishCrawler fishCrawler;
	@Autowired
	private MkmCrawler mkmCrawler;

	private ExecutorService executors = Executors.newFixedThreadPool(4);

	private Queue<String> priceCardToUpdate = new ConcurrentLinkedQueue<>();
	private Map<String, List<CardPrice>> results = new ConcurrentHashMap<>();

	@Scheduled(fixedRate = 100)
	public void update() throws InterruptedException, ExecutionException {
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
					for (Future<List<CardPrice>> future : executors.invokeAll(tasks(card))) {
						prices.addAll(future.get());
					}
					prices.stream().forEach(session.getMapper(CardPriceDao.class)::price);
					session.commit();
					results.put(id, prices);
				}
			}
		}
	}

	private List<Callable<List<CardPrice>>> tasks(Card card) {
		List<Callable<List<CardPrice>>> calls = new ArrayList<>();
		if (!card.getEdition().isOnlineOnly()) {
			if (card.getFoil() != Foil.onlyfoil) {
				calls.add(new Callable<List<CardPrice>>() {
					@Override
					public List<CardPrice> call() throws Exception {
						return mkmCrawler.priceForNotFoil(card);
					}
				});
			}
			if (card.getFoil() != Foil.nofoil) {
				calls.add(new Callable<List<CardPrice>>() {
					@Override
					public List<CardPrice> call() throws Exception {
						return mkmCrawler.priceForFoil(card);
					}
				});
			}
		}
		if (card.getFoil() != Foil.onlyfoil) {
			calls.add(new Callable<List<CardPrice>>() {
				@Override
				public List<CardPrice> call() throws Exception {
					return fishCrawler.priceForNotFoil(card);
				}
			});
		}
		if (card.getFoil() != Foil.nofoil) {
			calls.add(new Callable<List<CardPrice>>() {
				@Override
				public List<CardPrice> call() throws Exception {
					return fishCrawler.priceForFoil(card);
				}
			});
		}
		return calls;
	}

	public void request(Card card) {
		priceCardToUpdate.add(card.getId());
	}

	public void request(String id) {
		priceCardToUpdate.add(id);
	}

	public List<CardPrice> get(String id) {
		return results.get(id);
	}

	public List<CardPrice> waitFor(String card) throws InterruptedException {
		List<CardPrice> prices = get(card);
		int count = 0;
		while (prices == null && count++ < 10) {
			TimeUnit.MILLISECONDS.sleep(100);
			prices = get(card);
		}
		return prices;
	}

	@Override
	@PreDestroy
	public void destroy() throws Exception {
		executors.shutdownNow();
	}
}
