package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("TAG")
public class Tag extends Entry implements Serializable, IsSerializable {

	@ManyToMany
	private List<Annotation> annotations = new ArrayList<Annotation>();

	@ManyToOne
	@JoinColumn(name = "catalogId")
	private Catalogo catalog;
	
	public Tag() {
		super();
	}

	public Tag(String name) {
		super(name);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
