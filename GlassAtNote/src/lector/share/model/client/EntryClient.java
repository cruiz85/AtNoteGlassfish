package lector.share.model.client;



import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class EntryClient implements IsSerializable{


	private Long id;

	private String name;

	private CatalogoClient catalog;

	private List<EntryClient> parents = new ArrayList<EntryClient>();

	public EntryClient() {

	}


	public EntryClient(Long id,List<EntryClient> parents, String name, CatalogoClient catalog) {
		super();
		this.parents = parents;
		this.name = name;
		this.catalog = catalog;
		this.id = id;
		
	}


	public EntryClient(String name) {
		this();
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<EntryClient> getParents() {
		return parents;
	}


	public void setParents(List<EntryClient> parents) {
		this.parents = parents;
	}


	public CatalogoClient getCatalog() {
		return catalog;
	}


	public void setCatalog(CatalogoClient catalog) {
		this.catalog = catalog;
	}



	private String uppercaseName;


	public void prePersist() {
		if (name != null) {
			setUppercaseName(name.toUpperCase());
		} else {
			setUppercaseName(null);
		}
	}


	public String getUppercaseName() {
		return uppercaseName;
	}


	public void setUppercaseName(String uppercaseName) {
		this.uppercaseName = uppercaseName;
	}
}
