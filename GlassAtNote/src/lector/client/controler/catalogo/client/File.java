package lector.client.controler.catalogo.client;

import java.io.Serializable;
import java.util.ArrayList;

import lector.share.model.FileException;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeClient;

public class File extends EntityCatalogElements implements Serializable {


    public File(TypeClient EntryClient, CatalogoClient catalogClient,EntryClient FatherIdCreador) {
        super(EntryClient.getName(), EntryClient, catalogClient, FatherIdCreador);

    }

  
}
