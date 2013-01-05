package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
//@Table(name = "folder")
@DiscriminatorValue("FOLDER")
public class FolderDB extends Entry implements Serializable {

	@OneToMany(mappedBy="father", orphanRemoval=true)
	private List<Relation> relations = new ArrayList<Relation>();
	private List<Long> orders = new ArrayList<Long>();

	public FolderDB() {
		super();

	}

	public FolderDB(String name) {
		super(name);

	}
	public FolderDB(String name,Catalogo catalogo) {
		super(name, catalogo);

	}
	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

}
