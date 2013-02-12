package lector.client.controler.catalogo.client;

import java.io.Serializable;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;

public class Folder extends EntityCatalogElements implements Serializable {


    public Folder(TypeCategoryClient EntryClient, CatalogoClient catalogClient,EntryClient FatherIdCreador) {
        super(EntryClient.getName(), EntryClient, catalogClient, FatherIdCreador);
    }

  
}
