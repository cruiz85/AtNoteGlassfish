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
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.logger.Logger;
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

public class Finder extends Composite {

	protected Node ActualRama;
	protected Long NanotimeOld;
	protected CatalogoClient C;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	//el finder del reading activity tiene lenguaje asociado
	protected boolean InReadingActivity=false;
	protected SimplePanel simplePanel;
	protected Node trtmNewItem;
	protected ScrollPanel scrollPanel;
	protected static ClickHandler clickHandler;
	protected static BotonesStackPanelMio buttonMio;
	private Tree ArbolDeNavegacion;
	private Finder Yo;
	
	public Finder() {
		
		NanotimeOld=0l;
		simplePanel = new SimplePanel();
		initWidget(simplePanel);
		Yo=this;
//		horizontalSplitPanel = new SplitLayoutPanel();
//		simplePanel.add(horizontalSplitPanel);
		simplePanel.setSize("100%", "100%");
	//	simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
//		horizontalSplitPanel.setSize("100%", "100%");
	//	horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		scrollPanel = new ScrollPanel();
//		horizontalSplitPanel.addWest(scrollPanel, 200.0);
//		
//		
		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		
		
		ArbolDeNavegacion = new Tree();
		ArbolDeNavegacion.addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				Node ActualRamaNew=(Node)event.getSelectedItem();
				Long acLong=System.currentTimeMillis();
				Long Dist=acLong-NanotimeOld;
				Boolean Seleccionador=(Dist<1000);
				if (((Node)event.getSelectedItem()).getEntidad() instanceof Folder){
					if (ActualRama!=ActualRamaNew){
						ActualRama=ActualRamaNew;
						NanotimeOld=System.currentTimeMillis();
					//	cargaLaRama();
					}
					else
					{
						if (Seleccionador)
							if ((ActualRamaNew.getEntidad()).getEntry().getId()!=Constants.CATALOGID)
								if (buttonMio!=null)Selecciona();
							else {}
						else NanotimeOld=System.currentTimeMillis();
					}
				}
				else if (ActualRamaNew.getEntidad() instanceof File)
					if (ActualRama!=ActualRamaNew){
						NanotimeOld=System.currentTimeMillis();
						ActualRama=ActualRamaNew;
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getEntry().getId()!=Constants.CATALOGID)
								Selecciona();
							else {}
						else NanotimeOld=System.currentTimeMillis();
					}
				
			}

			private void Selecciona() {
				BotonesStackPanelMio B=buttonMio.Clone();
				B.addClickHandler(clickHandler);
				B.setStyleName("gwt-ButtonCenter");
				B.setSize("100%", "100%");
				if (ActualRama.getEntidad() instanceof File) B.setIcon("File.gif",ActualRama.getEntidad().getName());
				else if (ActualRama.getEntidad() instanceof Folder)B.setIcon("Folder.gif",ActualRama.getEntidad().getName());
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
				B.setEntidad(ActualRama.getEntidad());
				((ClickHandlerMio)clickHandler).onClickMan(B);

				
			}
		});

//		ArbolDeNavegacion.addOpenHandler(new OpenHandler<TreeItem>() {
//			public void onOpen(OpenEvent<TreeItem> event) {
//			//	cargaLaRama();
//			}
//		});

		scrollPanel.setWidget(ArbolDeNavegacion);
		ArbolDeNavegacion.setSize("100%", "100%");
		TypeCategoryClient TCC=new TypeCategoryClient("NULL");
		TCC.setId(Constants.CATALOGID);
		TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), "NULL",
				C, new ArrayList<EntryClient>());
		trtmNewItem = new Node(new Folder(TCC, null,T));
		trtmNewItem.setText("//");
		ArbolDeNavegacion.addItem(trtmNewItem);
		ActualRama=trtmNewItem;
		
		
		
	}



	protected void cargaLaRama() {
		
		LoadingPanel.getInstance().center();
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualState.getLanguage().getLoading());
		else LoadingPanel.getInstance().setLabelTexto("Loading...");
		bookReaderServiceHolder.loadCatalogById(C.getId(),new AsyncCallback<CatalogoClient>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.ERROR_LOADING_CATALOG);
				Logger.GetLogger().severe(Yo.getClass().toString(),ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_CATALOG);
				LoadingPanel.getInstance().hide();
			}

			@Override
			public void onSuccess(CatalogoClient result) {
				EvaluaCatalogo(result);
				LoadingPanel.getInstance().hide();
				
			}

			private void EvaluaCatalogo(CatalogoClient result) {
				List<EntryClient> Lista = result.getEntries();
				sortStringExchange(Lista);
				for (EntryClient Hijo : Lista) {
					if (Hijo instanceof TypeClient)
					{
						TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), C.getCatalogName(),
								C, new ArrayList<EntryClient>());
						EntityCatalogElements entitynew=new File((TypeClient)Hijo, C,T);	
						Node A=new Node(entitynew);
						A.setHTML("File.gif",entitynew.getName());
						trtmNewItem.addItem(A);
						trtmNewItem.setState(true,false);
					}
					else {
						TypeCategoryClient T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), C.getCatalogName(),
								C, new ArrayList<EntryClient>());
						EntityCatalogElements entitynew=new Folder((TypeCategoryClient)Hijo, C,T);	
						Node A=new Node(entitynew);
						A.setHTML("Folder.gif",entitynew.getName());					
						trtmNewItem.addItem(A);
						trtmNewItem.setState(true,false);
						EvaluaCarpeta((TypeCategoryClient)Hijo,A);
					}
				}
				
			}
			
			private void EvaluaCarpeta(TypeCategoryClient hijo, Node Padre) {
				List<EntryClient> Lista = hijo.getChildren();
				sortStringExchange(Lista);
				for (EntryClient Hijo : Lista) {
					if (Hijo instanceof TypeClient)
					{
						EntityCatalogElements entitynew=new File((TypeClient)Hijo, C, Padre.getEntidad().getEntry());	
						Node A=new Node(entitynew);
						A.setHTML("File.gif",entitynew.getName());
						Padre.addItem(A);
						Padre.setState(true,false);
					}
					else {
						EntityCatalogElements entitynew=new Folder((TypeCategoryClient)Hijo, C, Padre.getEntidad().getEntry());	
						Node A=new Node(entitynew);
						A.setHTML("Folder.gif",entitynew.getName());					
						Padre.addItem(A);
						Padre.setState(true,false);
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
		
		
		
//		AsyncCallback<ArrayList<EntityCatalogElements>> callback1 = new AsyncCallback<ArrayList<EntityCatalogElements>>() {
//
//			public void onFailure(Throwable caught) {
//				if (InReadingActivity)  Window.alert(ActualUser.getLanguage().getE_Types_refresh());
//				else Window.alert("Error : I can't refresh the types");
//				LoadingPanel.getInstance().hide();
//			}
//
//			public void onSuccess(ArrayList<EntityCatalogElements> result) {
//				
//				sortStringExchange(result);
//				
//				ActualRama.removeItems();
//				for (EntityCatalogElements entity : result) {
//					entity.setActualFather(ActualRama.getEntidad());
//				}
//				for (EntityCatalogElements entitynew : result) {
//					Node A=new Node(entitynew);
//					if (entitynew instanceof Folder) A.setHTML("Folder.gif",entitynew.getName());					
//					else A.setHTML("File.gif",entitynew.getName());
//					ActualRama.addItem(A);
//					ActualRama.setState(true,false);
//					}
//				LoadingPanel.getInstance().hide();
//			}
//			
//			  
//		};
//		LoadingPanel.getInstance().center();
//		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualUser.getLanguage().getLoading());
//		else LoadingPanel.getInstance().setLabelTexto("Loading...");
//		Long IdPathActual = 0l;
///*		if (ActualRama.getEntidad().getID())
//			IdPathActual = null;
//		else*/
//			IdPathActual = ActualRama.getEntidad().getID();
//		
//		
//			
////		bookReaderServiceHolder.getSons(IdPathActual, C
////				.getId(), callback1);
//		
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
		ActualRama=trtmNewItem;
		trtmNewItem.setText(C.getCatalogName());
		cargaLaRama();
	}
	
	public static void setButtonTipo(BotonesStackPanelMio buttonMio) {
		Finder.buttonMio=buttonMio;

	}

	public static void setBotonClick(ClickHandler clickHandler) {
		Finder.clickHandler=clickHandler;
	}

	public EntityCatalogElements getTopPath() {
				return ActualRama.getEntidad();
	}
	
	public void RefrescaLosDatos()
	{
		ActualRama=trtmNewItem;
		cargaLaRama();
//		simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
//		horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
