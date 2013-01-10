package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("TAG")
// @Table(name="Tag")
public class Tag extends Entry implements Serializable, IsSerializable {

	@ManyToMany
	// @JoinTable(name = "tag_annotation", joinColumns = { @JoinColumn(name =
	// "tag_id", referencedColumnName = "id") }, inverseJoinColumns = {
	// @JoinColumn(name = "annotation_id", referencedColumnName = "id") })
	private List<Annotation> annotations = new ArrayList<Annotation>();

	public Tag() {
		super();
	}

	public Tag(String name) {
		super(name);
	}
	public Tag(String name, Catalogo catalogo) {
		super(name, catalogo);
	}
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
