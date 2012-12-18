package lector.client.catalogo;


import java.util.ArrayList;
import java.util.List;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ScrollPanel;

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
	private TypeCategoryClient T;
	
	public FinderKeys() {
		Yo=this;
		simplePanel.clear();
		simplePanel.setSize("100%", "100%");

		scrollPanel = new ScrollPanel();

		simplePanel.add(scrollPanel);
		scrollPanel.setSize("100%", "100%");
		
		ElementKey.setFinderAct(Yo);
		scrollPanel.add(EK);
		T=new TypeCategoryClient(Constants.CATALOGID,new ArrayList<EntryClient>(), "NULL",
				C, new ArrayList<EntryClient>());
		EK=new ElementKey(new Folder(T, null,T));
		EK.setBotonDownState(false);
		EK.setBotonUpState(false);
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
						ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDoble");
						ActualEle.setSelected(false);
						ActualEle=ActualRamaNew;
						ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
						ActualEle.setSelected(true);
					}
					else
					{
						if (Seleccionador)
							if (ActualRamaNew.getEntidad().getEntry().getId()!=Constants.CATALOGID)
								{
								Selecciona();
								ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
								}
						
							else {
								ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
							}
						else{
							NanotimeOld=System.currentTimeMillis();
							ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
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
		if (InReadingActivity)  LoadingPanel.getInstance().setLabelTexto(ActualState.getLanguage().getLoading());
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
				T.getChildren().clear();
				C=result;
				T.setCatalog(result);
				EvaluaCatalogo(result);
				LoadingPanel.getInstance().hide();
				
			}

			private void EvaluaCatalogo(CatalogoClient result) {
				List<EntryClient> Lista = result.getEntries();
//				sortStringExchange(Lista);
				for (EntryClient Hijo : Lista) {
					if (Hijo instanceof TypeClient)
					{
						T.getChildren().add(Hijo);
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
						T.getChildren().add(Hijo);
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
		T.setCatalog(C);
		ActualEle.setSelected(true);
		ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
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
		ActualEle.getLabel().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
		ActualEle.setSelected(true);
		cargaLaRama(EK);
		//simplePanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
		//horizontalSplitPanel.setHeight(Integer.toString(Window.getClientHeight())+"px");
	}


}
