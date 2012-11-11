package lector.client.catalogo;


import java.util.ArrayList;
import java.util.List;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Tree.Node;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class FinderKeys extends Finder {

	
	protected ElementKey ActualEle;
	protected ArrayList<EstadoElementKey> Lista;
	protected CatalogoClient C;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	//el finder del reading activity tiene lenguaje asociado
	protected boolean InReadingActivity=false;
	private ElementKey EK;
	private ClickHandler CHM;
	private ClickHandler CHS;
	private FinderKeys Yo;
	
	public FinderKeys() {
		Yo=this;
		simplePanel.clear();
		simplePanel.setSize("100%", "100%");

		scrollPanel = new ScrollPanel();

		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		scrollPanel.add(EK);
		TypeCategoryClient TCC=new TypeCategoryClient("NULL");
		TCC.setId(Constants.CATALOGID);
		TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), "NULL",
				C, new ArrayList<EntryClient>());
		EK=new ElementKey(new Folder(TCC, null,T));
		Lista=new ArrayList<EstadoElementKey>();
		
		AddElementLista(new EstadoElementKey(EK,false));
		CHM=new ClickHandler() {
			
			public void onClick(ClickEvent event) {
			
//				ButtonKey B0=(ButtonKey)event.getSource();
//				B0.getPadreKey();
//				FinderKeysArbitro.getInstance().addfather(B0.getPadreKey());
//				cargaLaRama(B0.getPadreKey());
				
			}
		};
		
		CHS=new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				ButtonKey Bk=(ButtonKey)event.getSource();
				ElementKey ActualRamaNew = Bk.getPadreKey();
				
				Long acLong=System.currentTimeMillis();
				Long Dist=acLong-NanotimeOld;
				Boolean Seleccionador=(Dist<1000);
				
					if (ActualEle!=ActualRamaNew){
						NanotimeOld=System.currentTimeMillis();
						ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaMIN");
						ActualEle.setSelected(false);
						ActualEle=ActualRamaNew;
						ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
						ActualEle.setSelected(true);
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getEntry().getId()!=Constants.CATALOGID)
								{
								Selecciona();
								ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
								}
						
							else {
								ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
							}
						else{
							NanotimeOld=System.currentTimeMillis();
							ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
						}
					}
					
									
			}
			
			private void Selecciona() {
				BotonesStackPanelMio B=buttonMio.Clone();
				B.addClickHandler(clickHandler);
				B.setStyleName("gwt-ButtonCenter");
				B.setSize("100%", "100%");
				if (ActualEle.getEntidad() instanceof File) B.setIcon("File.gif",ActualEle.getEntidad().getName());
				else if (ActualEle.getEntidad() instanceof Folder)B.setIcon("Folder.gif",ActualEle.getEntidad().getName());
				B.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
						
					}
				});

				B.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
					}
				});
				
				B.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
				}
				});
				
				B.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						
						((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
					
				}
			});
				B.setEntidad(ActualEle.getEntidad());
				if (clickHandler instanceof ClickHandlerMio )
					((ClickHandlerMio)clickHandler).onClickMan(B);
				else B.click();

				
			}
		};

		
		EK.addClickButtonMas(CHM);
		
		EK.addClickButton(CHS);
		ActualEle=EK;
		scrollPanel.add(EK);

		

			
		
			
		
		
	}



	private void AddElementLista(EstadoElementKey estadoElementKey) {
		
		ElementKey EK=IsIn(estadoElementKey.getEK());
		if (EK==null)
		{
			Lista.add(estadoElementKey);
		}
		else 
		{
			ElementKey eKNuevo=estadoElementKey.getEK();
			EK.getOthers().setVisible(true);
			eKNuevo.getOthers().setVisible(true);
			EK.getOtros().add(eKNuevo);
			ArrayList<ElementKey> ListaEK=EK.getOtros();
			for (ElementKey elementKey : ListaEK) {
				elementKey.setOtros(ListaEK);
				elementKey.getOthers().setVisible(true);
			}

		}
		
	}



	private ElementKey IsIn(ElementKey ek2) {
		for (EstadoElementKey Element : Lista) {
			if (Element.getEK().getEntidad().getEntry().getId().equals(ek2.getEntidad().getEntry().getId()))
				return Element.getEK();
		}
		return null;
	}



	protected void cargaLaRama(ElementKey elementKeyllamada) {
		
		
		LoadingPanel.getInstance().center();
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
		else LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.loadCatalogById(C.getId(),new AsyncCallback<CatalogoClient>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
				Logger.GetLogger().severe(Yo.getClass().toString(), ErrorConstants.ERROR_LOADING_CATALOG);
				LoadingPanel.getInstance().hide();
			}

			@Override
			public void onSuccess(CatalogoClient result) {
				C=result;
				EvaluaCatalogo(result);
				LoadingPanel.getInstance().hide();
				
			}

			private void EvaluaCatalogo(CatalogoClient result) {
				List<EntryClient> Lista = result.getEntries();
//				sortStringExchange(Lista);
				for (EntryClient Hijo : Lista) {
					if (Hijo instanceof TypeClient)
					{
						TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), C.getCatalogName(),
								C, new ArrayList<EntryClient>());
						
						EntityCatalogElements entitynew=new File((TypeClient)Hijo, C, T);	
						ElementKey A=new ElementKey(entitynew);
						A.setHTML("File.gif",entitynew.getName());
						EK.addItem(A);
						A.addClickButtonMas(CHM);					
						A.addClickButton(CHS);
						A.isAFile();
						AddElementLista(new EstadoElementKey(A,false));

					}
					else {
						TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), C.getCatalogName(),
								C, new ArrayList<EntryClient>());
						EntityCatalogElements entitynew=new Folder((TypeCategoryClient)Hijo, C,T);	
						ElementKey A=new ElementKey(entitynew);
						A.setHTML("Folder.gif",entitynew.getName());					
						EK.addItem(A);
						A.addClickButtonMas(CHM);					
						A.addClickButton(CHS);
						AddElementLista(new EstadoElementKey(A,false));
						EvaluaCarpeta((TypeCategoryClient)Hijo,A);
					}
				}
				
			}
			
			private void EvaluaCarpeta(TypeCategoryClient hijo, ElementKey Padre) {
				List<EntryClient> Lista = hijo.getChildren();
				sortStringExchange(Lista);
				for (EntryClient Hijo : Lista) {
					if (Hijo instanceof TypeClient)
					{
						EntityCatalogElements entitynew=new File((TypeClient)Hijo, C, Padre.getEntidad().getEntry());	
						ElementKey A=new ElementKey(entitynew);
						A.setHTML("File.gif",entitynew.getName());
						A.addClickButtonMas(CHM);					
						A.addClickButton(CHS);
						A.isAFile();
						AddElementLista(new EstadoElementKey(A,false));
						Padre.addItem(A);

					}
					else {
						EntityCatalogElements entitynew=new Folder((TypeCategoryClient)Hijo, C, Padre.getEntidad().getEntry());	
						ElementKey A=new ElementKey(entitynew);
						A.setHTML("Folder.gif",entitynew.getName());					
						Padre.addItem(A);
						A.addClickButtonMas(CHM);					
						A.addClickButton(CHS);
						AddElementLista(new EstadoElementKey(A,false));
						EvaluaCarpeta((TypeCategoryClient)Hijo,A);
					}
				}
				
			}

			public void sortStringExchange( List<EntryClient>  lista )
		      {
		            int i, j;
		            EntryClient temp;

		            for ( i = 0;  i < lista.size() - 1;  i++ )
		            {
		                for ( j = i + 1;  j < lista.size();  j++ )
		                {  
		                         if ( lista.get(i).getName().compareToIgnoreCase( lista.get(j).getName()) > 0 )
		                          {                                             // ascending sort
		                                      temp = lista.get(i);
		                                      lista.set(i, lista.get(j));    // swapping
		                                      lista.set(j, temp); 
		                                      
		                           } 
		                   } 
		             } 
		      } 	
		});
		
		
//		AsyncCallback<ArrayList<Entity>> callback1 = new AsyncCallback<ArrayList<Entity>>() {
//
//			public void onFailure(Throwable caught) {
//				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
//				else Window.alert("Error : I can't refresh the types");
//				LoadingPanel.getInstance().hide();
//			}
//
//			public void onSuccess(ArrayList<Entity> result) {
//				
//				
//				sortStringExchange(result);
//				ArrayList<ElementKey> ListaTemp=new ArrayList<ElementKey>();
//				
//				ElementKey PadreEle = FinderKeysArbitro.getInstance().getPadre();
//				PadreEle.removeItems();
//				for (Entity entity : result) {
//					entity.setActualFather(PadreEle.getEntidad());
//				}
//				for (Entity entitynew : result) {
//					ElementKey A=new ElementKey(entitynew);
//					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
//					else {
//						A.setHTML("File.gif",entitynew.getName());
//						A.isAFile();
//					}
//					PadreEle.addItem(A);
//					A.addClickButtonMas(CHM);					
//					A.addClickButton(CHS);
//					ListaTemp.add(A);
//					AddElementLista(new EstadoElementKey(A,false));
//					}
//				FinderKeysArbitro.getInstance().setfalse();
//				for (ElementKey elementKey : ListaTemp) {
//					FinderKeysArbitro.getInstance().add(elementKey);
//				}
//				LoadingPanel.getInstance().hide();
//				
//			}
//			
//			  public void sortStringExchange( ArrayList<Entity>  x )
//		      {
//		            int i, j;
//		            Entity temp;
//
//		            for ( i = 0;  i < x.size() - 1;  i++ )
//		            {
//		                for ( j = i + 1;  j < x.size();  j++ )
//		                {  
//		                         if ( x.get(i).getName().compareToIgnoreCase( x.get(j).getName()) > 0 )
//		                          {                                             // ascending sort
//		                                      temp = x.get(i);
//		                                      x.set(i, x.get(j));    // swapping
//		                                      x.set(j, temp); 
//		                                      
//		                           } 
//		                   } 
//		             } 
//		      } 
//		};
//		LoadingPanel.getInstance().center();
//		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
//		else LoadingPanel.getInstance().setLabelTexto("Loading...");
//		Long IdPathActual = 0l;
///*		if (ActualRama.getEntidad().getID())
//			IdPathActual = null;
//		else*/
//			IdPathActual = elementKeyllamada.getEntidad().getID();
////TODO
////		bookReaderServiceHolder.getSons(IdPathActual, C
////				.getId(), callback1);
		
	}

	
	
	public boolean isInReadingActivity() {
		return InReadingActivity;
	}
	
	public void setInReadingActivity(boolean inReadingActivity) {
		InReadingActivity = inReadingActivity;
	}
	
	public CatalogoClient getCatalogo() {
		return C;
	}
	
	public void setCatalogo(CatalogoClient c) {
		C = c;
		ActualEle=EK;
		EK.setText(C.getCatalogName());
		ActualEle.setSelected(true);
		ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
		ActualEle.setSelected(true);
//		cargaLaRama();
	}
	
	public static void setButtonTipo(BotonesStackPanelMio buttonMio) {
		Finder.buttonMio=buttonMio;

	}

	public static void setBotonClick(ClickHandler clickHandler) {
		Finder.clickHandler=clickHandler;
	}

	public EntityCatalogElements getTopPath() {
				return ActualEle.getEntidad();
	}
	
	public void RefrescaLosDatos()
	{
		Lista.clear();
		ActualEle=EK;
		EK.removeItems();
		ActualEle.getLabel().setStyleName("gwt-ButtonIzquierdaSelectMIN");
		ActualEle.setSelected(true);
		cargaLaRama(EK);
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
