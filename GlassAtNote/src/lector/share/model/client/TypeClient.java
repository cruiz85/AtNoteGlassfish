package lector.share.model.client;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TypeClient extends EntryClient implements IsSerializable {

	public TypeClient() {
		super();
	}

	public TypeClient(String name) {
		super(name);
	}

	public TypeClient(Long id, List<EntryClient> parents, String name,
			CatalogoClient catalog) {
		super(id, parents, name, catalog);

	}

}
