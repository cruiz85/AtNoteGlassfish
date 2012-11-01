package lector.client.catalogo.grafo;

import java.util.ArrayList;
import java.util.List;

import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.share.model.AnnotationSchema;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

public class Elemento {

	private Tipo tipo;
	private Entity E;
	private String name;
	private String Coordenadas;
	private static List<AnnotationSchema> Lista;
	private static CatalogoClient Catalogo;
	
	public Elemento(String Entrada) {
		
		String[] corte=Entrada.split("\"");
		generatipo(corte[3]);
		if (name!=null)
			findentidad(name);
		generacoordenadas(corte[10]);
		
	}

	private void findentidad(String name2) {
		boolean encontrado=false;
		int i=0;
		while ((!encontrado)&&(i<Lista.size()))
		{
			if (name2.equals(Lista.get(i).getId().toString()))
			{
			encontrado=true;	
			AnnotationSchema A=Lista.get(i);
			//TODO ????
			if (A.getFile())
				{
				TypeClient T=findT(A.getId(),Catalogo);
				E=new File(T, Catalogo, null);
				}
			else
			{
				TypeCategoryClient TC=findTC(A.getId(),Catalogo);
				E=new Folder(TC, Catalogo, null);
			}
			}
			i++;
		}
	}

	private TypeCategoryClient findTC(Long id, CatalogoClient catalogo2) {
		return (TypeCategoryClient) find(id,catalogo2);
	}

	private EntryClient find(Long id, CatalogoClient catalogo2) {
		for (EntryClient EE : catalogo2.getEntries()) {
			if (EE.getId().equals(id))
				return EE;
			else 
				if (EE instanceof TypeCategoryClient)
				{
					EntryClient EEE= finddeep(id, (TypeCategoryClient)EE);
					if ( EEE != null)
						return EEE;
				}
		}
		return null;
	}

	private EntryClient finddeep(Long id, TypeCategoryClient eE) {
		for (EntryClient EE : eE.getChildren()) {
			if (EE.getId().equals(id))
				return EE;
			else 
				if (EE instanceof TypeCategoryClient)
				{
					EntryClient EEE= finddeep(id, (TypeCategoryClient)EE);
					if ( EEE != null)
						return EEE;
				}
		}
		return null;
	}

	private TypeClient findT(Long id, CatalogoClient catalogo2) {
		return (TypeClient) find(id,catalogo2);
	}

	private void generacoordenadas(String string) {
		String[] corte=string.split("\\[");
		corte=corte[1].split("\\]");
		Coordenadas=corte[0];
	}

	private void generatipo(String string) {
		Integer i=null;
		try {
			i=Integer.parseInt(string);
		} catch (Exception e) {
			// nada
		}
		
		if (i!=null)
		{
		name=Integer.toString(i);
		tipo=Tipo.Type;
		}
		else 
		{
		name=null;
		tipo=Tipo.Edge;
		}
		
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Entity getE() {
		return E;
	}

	public void setE(Entity e) {
		E = e;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordenadas() {
		return Coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		Coordenadas = coordenadas;
	}
	
	

	public static void setLista(List<AnnotationSchema> compare) {
		Lista = compare;
	}
	
	public static void setCatalogo(CatalogoClient catalogo2) {
		Catalogo = catalogo2;
	}
}
