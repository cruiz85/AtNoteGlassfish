package lector.share.model.client;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GoogleBookClient extends RemoteBookClient  implements IsSerializable{

	private String tbURL;
	private String imagesPath;
	private String url;

	public GoogleBookClient() {
		super("GOOGLE LIBRARY");
	}

	public GoogleBookClient(String author, String ISBN, String pagesCount,
			String publishedYear, String title, String tbURL, String unscapedURL, List<String> webLinks) {
		this();
		super.setAuthor(author);
		super.setISBN(ISBN);
		super.setPagesCount(pagesCount);
		super.setPublishedYear(publishedYear);
		super.setTitle(title);
		super.setWebLinks(webLinks);
		this.tbURL = tbURL;
		this.url = unscapedURL;
	}

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTbURL() {
		return tbURL;
	}

	public void setTbURL(String tbURL) {
		this.tbURL = tbURL;
	}

}
