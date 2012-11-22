package lector.share.model.client;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LocalBookClient extends BookClient implements IsSerializable{

	public LocalBookClient() {
		super();
	}

	public LocalBookClient(Long id,Long professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title,
			List<String> webLinks) {
		super(id,professor, author, ISBN, pagesCount, publishedYear, title, webLinks);
		// TODO Auto-generated constructor stub
	}

}
