package lector.server;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.FolderDB;
import lector.share.model.Relation;
import lector.share.model.Tag;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;

public class CatalogoGenerator {

	private static ArrayList<EntryClient> listActual=new ArrayList<>();
	private static CatalogoClient catalogoGeneral;

	public static void Start(CatalogoClient catalogoClient,Catalogo catalogo) {
		listActual=new ArrayList<>();
		catalogoGeneral=catalogoClient;
		List<Entry> sons=catalogo.getEntries();
		for (int i = 0; i < sons.size(); i++) {
			Entry nextDB=sons.get(i);
			EntryClient next=exist(nextDB);
			if (next==null)
				 {
				next= produce(nextDB);
				catalogoGeneral.getEntries().add(next);
				listActual.add(next);
				if (nextDB instanceof FolderDB)
					DeepStart((TypeCategoryClient)next,(FolderDB)nextDB);
				
				 }
			else
			{
				catalogoGeneral.getEntries().add(next);
			}
			
		}
	}

	private static void DeepStart(TypeCategoryClient father, FolderDB fatherDB) {
		List<Relation> relations=fatherDB.getRelations();
		for (int i = 0; i < relations.size(); i++) {	
			Entry nextDB=relations.get(i).getChild();
			EntryClient next=exist(nextDB);
			if (next==null)
				 {
				next= produce(nextDB);
				father.getChildren().add(next);
				next.getParents().add(father);
				listActual.add(next);
				if (nextDB instanceof FolderDB)
					DeepStart((TypeCategoryClient)next,(FolderDB)nextDB);
				
				 }
			else
			{
				father.getChildren().add(next);
				next.getParents().add(father);
			}
					
		}
		
	}

	private static EntryClient exist(Entry nextDB) {
		int i=0;
		boolean found=false;
		while (i<listActual.size()&&!found) {
			if (listActual.get(i).getId().equals(nextDB.getId()))
				found=true;
			i++;
			
		}
		
		if (found) return listActual.get(i-1);
		else return null;
	}

	private static EntryClient produce(Entry nextDB) {
		if (nextDB instanceof FolderDB)
			return ServiceManagerUtils.produceTypeCategoryClient((FolderDB)nextDB, catalogoGeneral);
		else return ServiceManagerUtils.produceTypeClientEager((Tag)nextDB, catalogoGeneral);
	}


	
	

}
