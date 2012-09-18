package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "file")
public class FileDB extends Entry implements Serializable, IsSerializable {

	@ManyToMany(mappedBy = "files")
	private List<Annotation> annotations = new ArrayList<Annotation>();

	public FileDB() {
		super();
	}

	public FileDB(String name) {
		super(name);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
