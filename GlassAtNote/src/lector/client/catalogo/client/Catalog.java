package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

import lector.share.model.Catalogo;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Catalog implements Serializable,IsSerializable{

	private ArrayList<Entity> Sons;
	
	private Long id;
	
	private short isPrivate=1;
	
	private Long professorId;
	
	private String catalogName;
	
	public Catalog() {
		Sons=new ArrayList<Entity>();
	}
	
	public Catalog(ArrayList<Entity> Sonsin){
		Sons=Sonsin;
	}
	
	public void addSon(Entity entity){
		if (Sons==null) Sons=new ArrayList<Entity>();
		Sons.add(entity);
	}
	
	public void removeSon(Entity entity){
		if (Sons==null) Sons=new ArrayList<Entity>();
		Sons.remove(entity);
	}
	
	public ArrayList<Entity> getSons() {
		return Sons;
	}
	
	public void setSons(ArrayList<Entity> sons) {
		Sons = sons;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short isPrivate() {
		return isPrivate;
	}

	public void setPrivate(short s) {
		this.isPrivate = s;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public static Catalog cloneCatalogo(Catalogo catalog) {
		Catalog newe=new Catalog();
		newe.setCatalogName(catalog.getCatalogName());
		newe.setId(catalog.getId());
		newe.setPrivate(catalog.getIsPrivate());
		newe.setProfessorId(catalog.getProfessorId());
		return newe;
	}

	
	
}
