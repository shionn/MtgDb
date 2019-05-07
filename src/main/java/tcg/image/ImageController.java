package tcg.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tcg.db.dao.CardDao;
import tcg.db.dbo.Card;

@Controller
public class ImageController {
	@Autowired
	private SqlSession session;

	@ResponseBody
	@RequestMapping(value = "/c/img/{id}.jpg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public HttpEntity<byte[]> image(@PathVariable("id") String id) throws IOException {
		Card card = session.getMapper(CardDao.class).readImgData(id);
		String filename = fileName(card);
		File file = new File(filename);
		if (!file.exists()) {
			new File(filename.replaceAll("/[^/]+$", "")).mkdir();
			download(card, filename);
		}
		HttpHeaders headers = new HttpHeaders();
		if (!file.exists()) {
			try (InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("img-404.gif")) {
				return new HttpEntity<byte[]>(IOUtils.toByteArray(is), headers);
			}
		}
		headers.setLastModified(file.lastModified());
		try (FileInputStream is = new FileInputStream(file)) {
			return new HttpEntity<byte[]>(IOUtils.toByteArray(is), headers);
		}
	}

	private void download(Card card, String filename) throws IOException {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			Iterator<String> urls = buildUrls(card).iterator();
			while (urls.hasNext() && !download(client, urls.next(), filename))
				;
		}

	}

	private boolean download(CloseableHttpClient client, String url, String filename)
			throws IOException {
		HttpResponse response = client.execute(new HttpGet(url));
		int status = response.getStatusLine().getStatusCode();
		if (status == 200) {
			try (InputStream is = response.getEntity().getContent();
					FileOutputStream os = new FileOutputStream(filename)) {
				int data = -1;
				while ((data = is.read()) != -1) {
					os.write(data);
				}
			}
		}
		EntityUtils.consume(response.getEntity());
		return status == 200;
	}

	private List<String> buildUrls(Card card) {
		List<String> urls = new ArrayList<>();
		urls.add(buildScryFallFromEdiutionNumber(card));
		if (card.getScryfallId() != null) {
			urls.add(buildScryFallFromIllustrationId(card));
		}
		return urls;
	}

	// https://img.scryfall.com/cards/large/front/e/e/ee0ba01b-de96-4f8f-9405-ff3ad288afac.jpg?1549414108
	private String buildScryFallFromIllustrationId(Card card) {
		String id = card.getScryfallId();
		return "https://img.scryfall.com/cards/large/front/" + id.charAt(0) + '/' + id.charAt(1)
				+ '/' + id + ".jpg";
	}

	private String buildScryFallFromEdiutionNumber(Card card) {
		String filename = card.getEdition().getCode() + "/" + card.getNumber();
		if (card.getSide() != null) {
			filename += card.getSide();
		}
		return "https://img.scryfall.com/cards/large/en/" + filename + ".jpg";
	}

	private String fileName(Card card) {
		String fileName = card.getEdition().getCode() + "/" + card.getNumber();
		if (card.getSide() != null) {
			fileName += card.getSide();
		}
		fileName += "+en";
		return path + "/" + fileName + "-en.jpg";
	}

	@Autowired
	@Value("${card.img.path}")
	private String path;

}
