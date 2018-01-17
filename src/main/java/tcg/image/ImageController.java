package tcg.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tcg.db.dao.CardDao;

@Controller
public class ImageController {
	@Autowired
	private SqlSession session;

	@ResponseBody
	@RequestMapping(value = "/c/img/{id}.jpg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable("id") String id) throws IOException {
		String filename = session.getMapper(CardDao.class).readImg(id);
		File file = new File(fullFileName(filename));
		if (!file.exists()) {
			download(filename);
		}
		// TODO une image par defaut
		try (FileInputStream is = new FileInputStream(file)) {
			return IOUtils.toByteArray(is);
		}
	}

	private void download(String filename) throws IOException {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = "http://magiccards.info/scans/en/" + filename + ".jpg";
			HttpResponse response = client.execute(new HttpGet(url));
			if (response.getStatusLine().getStatusCode() == 200) {
				new File(fullFileName(filename).replaceAll("/[^/]+$", "")).mkdir();
				try (InputStream is = response.getEntity().getContent();
						FileOutputStream os = new FileOutputStream(fullFileName(filename))) {
					int data = -1;
					while ((data = is.read()) != -1) {
						os.write(data);
					}
				}
			}
			EntityUtils.consume(response.getEntity());
		}

	}

	private String fullFileName(String filename) {
		return "/tmp/" + filename + ".jpg";
	}

}