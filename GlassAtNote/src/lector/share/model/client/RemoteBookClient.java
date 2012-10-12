package lector.share.model.client;

public class RemoteBookClient extends BookClient {

	private String libraryName;

	public RemoteBookClient() {
		super();
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
