package tcg.mkm;

import java.io.IOException;
import java.math.BigDecimal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MkmCrawler {

	BigDecimal price() throws IOException {
		Document document = Jsoup
				.connect(
						"https://www.cardmarket.com/en/Magic/Products/Singles/Battle+for+Zendikar/Ob+Nixilis+Reignited")
				.get();
		return new BigDecimal(
				document.select("table.availTable tr.row_Even.row_2 td.outerRight").first().text()
						.replaceAll("[^,0-9]", "")
						.replace(',', '.'));
	}

}
