package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;


public class CatalogoClient {


	private Long id;

	private List<EntryClient> entries = new ArrayList<EntryClient>();

	private boolean isPrivate = true;
	private Long professorId;
	private String catalogName;

	public CatalogoClient() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<EntryClient> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryClient> entries) {
		this.entries = entries;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
}
