package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "folder")
@DiscriminatorValue("FOLDER")
public class FolderDB extends Entry implements Serializable {

	@OneToMany
	private List<Relation> relations = new ArrayList<Relation>();

	public FolderDB() {
		super();
	
	}

	public FolderDB(String name) {
		super(name);
	
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}


	

}
