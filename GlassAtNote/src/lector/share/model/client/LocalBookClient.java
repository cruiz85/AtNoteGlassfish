package lector.share.model.client;

import java.util.List;

public class LocalBookClient extends BookClient {

	public LocalBookClient() {
		super();
	}

	public LocalBookClient(Long professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title,
			List<String> webLinks) {
		super(professor, author, ISBN, pagesCount, publishedYear, title, webLinks);
		// TODO Auto-generated constructor stub
	}

}
