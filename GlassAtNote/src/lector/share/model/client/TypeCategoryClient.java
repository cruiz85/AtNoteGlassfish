package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TypeCategoryClient extends EntryClient implements IsSerializable{

	private List<EntryClient> children = new ArrayList<EntryClient>();

	public TypeCategoryClient() {
		super();
	
	}

	public TypeCategoryClient(Long id, List<EntryClient> parents, String name,
			CatalogoClient catalog, List<EntryClient> children) {
		super(id, parents, name, catalog);
		this.children=children;

	}

	public TypeCategoryClient(String name) {
		super(name);
		children = new ArrayList<EntryClient>();
	
	}

	public List<EntryClient> getChildren() {
		return children;
	}

	public void setChildren(List<EntryClient> children) {
		this.children = children;
	}

	

}
