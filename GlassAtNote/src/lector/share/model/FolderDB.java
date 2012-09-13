package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

@Entity
@Table(name = "folder")
public class FolderDB extends Entry implements Serializable, IsSerializable {

	@ManyToMany(mappedBy = "entries")
	private List<Entry> children = new ArrayList<Entry>();

	public FolderDB() {
		super();
		this.entryIds = new ArrayList<Long>();
	}

	public FolderDB(String name) {
		super(name);
		this.entryIds = new ArrayList<Long>();
	}

	public FolderDB(ArrayList<Long> fathers, String name) {
		super(fathers, name);
	}

	public ArrayList<Long> getEntryIds() {
		return entryIds;
	}

	public void setEntryIds(ArrayList<Long> entryIds) {
		this.entryIds = entryIds;
	}

	@Override
	public Long getCatalogId() {
		return super.getCatalogId();
	}

	@Override
	public void setCatalogId(Long catalogId) {
		super.setCatalogId(catalogId);
	}

}
