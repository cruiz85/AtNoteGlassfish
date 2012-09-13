package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

import lector.client.reader.Annotation;
//TODO : Revisar esta clase, la herencia esta con ids, debería ser con objetos?.
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

	public FileDB(ArrayList<Long> fathers, String name) {
		super(fathers, name);
	}

	@Override
	public Long getCatalogId() {
		return super.getCatalogId();
	}

	@Override
	public void setCatalogId(Long catalogId) {
		super.setCatalogId(catalogId);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
