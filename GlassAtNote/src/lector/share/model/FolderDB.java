package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "folder")
public class FolderDB extends Entry implements Serializable {

	@OneToMany
	private List<Entry> children = new ArrayList<Entry>();

	public FolderDB() {
		super();
	
	}

	public FolderDB(String name) {
		super(name);
	
	}

	public List<Entry> getChildren() {
		return children;
	}

	public void setChildren(List<Entry> children) {
		this.children = children;
	}

	

}
