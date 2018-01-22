package tcg.price;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tcg.db.dao.CardDao;
import tcg.db.dbo.Card;
import tcg.db.dbo.CardPrice;
import tcg.price.goldfish.MtgGoldFishCrawler;
import tcg.price.mkm.MkmCrawler;

@Component
public class PriceDeamon {

	@Autowired
	private SqlSessionFactory factory;

	@Autowired
	private MtgGoldFishCrawler fishCrawler;
	@Autowired
	private MkmCrawler mkmCrawler;

	@Autowired
	@Qualifier("PriceCardToUpdate")
	private Queue<String> priceCardToUpdate;

	@Scheduled(fixedRate = 1000)
	public void update() {
		try (SqlSession session = factory.openSession()) {
			String id = priceCardToUpdate.poll();
			if (id != null) {
				CardDao dao = session.getMapper(CardDao.class);
				Card card = dao.read(id);
				List<CardPrice> prices = new ArrayList<>();
				prices.addAll(mkmCrawler.price(card));
				prices.addAll(fishCrawler.price(card));
				prices.stream().forEach(session.getMapper(CardDao.class)::price);
				session.commit();
			}
		}
	}

}
