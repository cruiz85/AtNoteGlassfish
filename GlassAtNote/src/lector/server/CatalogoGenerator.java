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

	private static ArrayList<EntryClient> listActual = new ArrayList<EntryClient>();
	private static CatalogoClient catalogoGeneral;

	public static void Start(CatalogoClient catalogoClient, Catalogo catalogo) {
		listActual = new ArrayList<EntryClient>();
		catalogoGeneral = catalogoClient;
		List<Entry> sons = getOrderedCatalogEntries(catalogo.getEntries(),
				catalogo.getOrders());
		for (int i = 0; i < sons.size(); i++) {
			Entry nextDB = sons.get(i);
			EntryClient next = exist(nextDB);
			if (next == null) {
				next = produce(nextDB);
				catalogoGeneral.getEntries().add(next);
				listActual.add(next);
				if (nextDB instanceof FolderDB)
					DeepStart((TypeCategoryClient) next, (FolderDB) nextDB);

			} else {
				catalogoGeneral.getEntries().add(next);
			}

		}
	}

	private static List<Entry> getOrderedCatalogEntries(List<Entry> entries,
			List<Long> orderEntries) {
		List<Entry> orderListEntries = new ArrayList<Entry>();
		for (Long id : orderEntries) {
			orderListEntries.add(getOrderedEntry(entries,id));
		}
		return null;
	}

	private static Entry getOrderedEntry(List<Entry> entries, Long id) {
		for (Entry entry : entries) {
			if(entry.getId().equals(id)){
				return entry;
			}
		}
		return null;
	}

	private static void DeepStart(TypeCategoryClient father, FolderDB fatherDB) {
		List<Relation> relations = getOrderedRelations(fatherDB.getRelations(), fatherDB.getOrders());
		for (int i = 0; i < relations.size(); i++) {
			Entry nextDB = relations.get(i).getChild();
			EntryClient next = exist(nextDB);
			if (next == null) {
				next = produce(nextDB);
				father.getChildren().add(next);
				next.getParents().add(father);
				listActual.add(next);
				if (nextDB instanceof FolderDB)
					DeepStart((TypeCategoryClient) next, (FolderDB) nextDB);

			} else {
				father.getChildren().add(next);
				next.getParents().add(father);
			}

		}

	}

	private static List<Relation> getOrderedRelations(List<Relation> relations, List<Long> orderRelations) {

		List<Relation> orderListRelations = new ArrayList<Relation>();
		for (Long id : orderRelations) {
			orderListRelations.add(getOrderedRelation(relations,id));
		}
		return null;
	}

	private static Relation getOrderedRelation(List<Relation> entries, Long id) {
		for (Relation relation : entries) {
			if(relation.getId().equals(id)){
				return relation;
			}
		}
		return null;
	}
	private static EntryClient exist(Entry nextDB) {
		int i = 0;
		boolean found = false;
		while (i < listActual.size() && !found) {
			if (listActual.get(i).getId().equals(nextDB.getId()))
				found = true;
			i++;

		}

		if (found)
			return listActual.get(i - 1);
		else
			return null;
	}

	private static EntryClient produce(Entry nextDB) {
		if (nextDB instanceof FolderDB)
			return ServiceManagerUtils.produceTypeCategoryClient(
					(FolderDB) nextDB, catalogoGeneral);
		else
			return ServiceManagerUtils.produceTypeClientEager((Tag) nextDB,
					catalogoGeneral);
	}

}
