package lector.share.model.client;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RemoteBookClient extends BookClient implements IsSerializable{

	private String libraryName;

	public RemoteBookClient() {
		super();
	}

	public RemoteBookClient(Long id, Long professor, String author, String ISBN,
			String pagesCount, String publishedYear, String title,
			List<String> webLinks, String libraryName) {
		super(id, professor, author, ISBN, pagesCount, publishedYear, title, webLinks);
		this.libraryName = libraryName;
	}

	public RemoteBookClient(String libraryName) {
		super();
		this.libraryName = libraryName;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

}
