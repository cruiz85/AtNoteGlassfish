package lector.share.model;


import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Entity
@Table(name = "folder")
public class FolderDB extends Entry implements Serializable, IsSerializable {

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
