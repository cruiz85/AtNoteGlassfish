package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

import lector.share.model.FileException;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;

public abstract class EntityCatalogElements extends Entity implements Serializable {

	private CatalogoClient catalogId;
	private EntryClient Entry;
	private EntryClient FatherIdCreador;

	public EntityCatalogElements(String Namein, EntryClient Entry, CatalogoClient catalogClient,EntryClient FatherIdCreador) {
		super(Namein);
		this.catalogId = catalogClient;
		this.FatherIdCreador=FatherIdCreador;
		this.Entry=Entry;
	}

	public CatalogoClient getCatalogo() {
		return catalogId;
	}

	public void setCatalogId(CatalogoClient catalogClient) {
		this.catalogId = catalogClient;
	}

	public EntryClient getEntry() {
		return Entry;
	}

	public void setEntry(EntryClient entry) {
		Entry = entry;
	}

	public EntryClient getFatherIdCreador() {
		return FatherIdCreador;
	}

	public void setFatherIdCreador(EntryClient fatherIdCreador) {
		FatherIdCreador = fatherIdCreador;
	}

	


}
