package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TypeCategoryClient extends EntryClient implements IsSerializable{

	private List<EntryClient> children = new ArrayList<EntryClient>();

	public TypeCategoryClient() {
		super();
	
	}

	public TypeCategoryClient(String name) {
		super(name);
	
	}

	public List<EntryClient> getChildren() {
		return children;
	}

	public void setChildren(List<EntryClient> children) {
		this.children = children;
	}

	

}
