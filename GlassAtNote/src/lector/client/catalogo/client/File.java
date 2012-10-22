package lector.client.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

import lector.share.model.FileException;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.TypeClient;

public class File extends EntityCatalogElements implements Serializable {


    public File(TypeClient EntryClient, CatalogoClient catalogClient,Long FatherIdCreador) {
        super(EntryClient.getName(), EntryClient, catalogClient, FatherIdCreador);

    }

  
}
