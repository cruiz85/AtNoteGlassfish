package lector.client.reader.filter.advance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class Rule extends Composite {

	private VerticalPanel RulePanel;
	private String NameRule;
	private ArrayList<EntryClient> TiposPos=new ArrayList<EntryClient>();
	private ArrayList<EntryClient> TiposNeg=new ArrayList<EntryClient>();
	private ArrayList<UserClient> UsuariosPos=new ArrayList<UserClient>();
	private ArrayList<UserClient> UsuariosNeg=new ArrayList<UserClient>();
	private ArrayList<AnnotationClient> ResultadoRule=new ArrayList<AnnotationClient>();
	private ScrollPanel scrollPanel;
	private Rule Yo;
	private Button btnNewButton;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	

	public Rule(String Name) {
		
		NameRule=Name;
		Yo=this;
		SimplePanel decoratorPanel = new SimplePanel();
		initWidget(decoratorPanel);
		decoratorPanel.setSize("100%", "100%");
		
		DockPanel dockPanel = new DockPanel();
		decoratorPanel.setWidget(dockPanel);
		dockPanel.setSize("100%", "100%");
		
		FlowPanel flowPanel = new FlowPanel();
		dockPanel.add(flowPanel, DockPanel.NORTH);
		flowPanel.setSize("100%", "100%");
		
		btnNewButton = new Button(ActualState.getLanguage().getRule()+" : " + NameRule);
		btnNewButton.setStyleName("gwt-ButtonBlack");
		btnNewButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				scrollPanel.setVisible(!scrollPanel.isVisible());
				FilterAdvance.setActualRule(Yo);
				btnNewButton.setStyleName("gwt-ButtonIzquierdaSelect");
			}
		});
		flowPanel.add(btnNewButton);
		btnNewButton.setSize("80%", "100%");
		
		btnNewButton.setStyleName("gwt-ButtonIzquierda");
		
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonIzquierdaPush");
			}
		});
		
		Button btnNewButton_1 = new Button(ActualState.getLanguage().getRemove());
		btnNewButton_1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilterAdvance.getRules().remove(Yo);
			}
		});
		flowPanel.add(btnNewButton_1);
		btnNewButton_1.setSize("20%", "100%");
		
		btnNewButton_1.setStyleName("gwt-ButtonDerecha");
		btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaPush");
			}
		});
		btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerecha");
			}
		});
		btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonDerechaOver");
			}
		});
		
		SimplePanel simplePanel = new SimplePanel();
		dockPanel.add(simplePanel, DockPanel.CENTER);
		simplePanel.setSize("100%", "100%");
		
		scrollPanel = new ScrollPanel();
		simplePanel.setWidget(scrollPanel);
		scrollPanel.setVisible(false);
		scrollPanel.setSize("100%", "100%");
		
		
		
		RulePanel = new VerticalPanel();
		RulePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		scrollPanel.setWidget(RulePanel);
		RulePanel.setSize("100%", "100%");
		
		
		
//		AssertRule AR=new AssertRule(RulePanel,0L,Tiposids.Tipo);
//		AR.setWidth("100%");
//		RulePanel.add(AR);
		
	}
	
	
	public void addAssertRule(AssertRule AR)
	{
		AR.setParental(RulePanel);
		RulePanel.add(AR);
		RulePanel.setSize("100%", "100");
	}
	
	public void evaluarReglas()
	{
		TiposPos=new ArrayList<EntryClient>();
		TiposNeg=new ArrayList<EntryClient>();
		UsuariosPos=new ArrayList<UserClient>();
		UsuariosNeg=new ArrayList<UserClient>();
		
		for (int i = 0; i < RulePanel.getWidgetCount(); i++) {
			AssertRule AR = (AssertRule) RulePanel.getWidget(i);
			if (AR.isStateImage())
				if (AR.getAssertName().getIdtipo()==Tiposids.Tipo)
					TiposPos.add(((EntityCatalogElements)AR.getAssertName().getEntityClient()).getEntry());
				else UsuariosPos.add(((EntityUser)AR.getAssertName().getEntityClient()).getAU());
			else 
				if (AR.getAssertName().getIdtipo()==Tiposids.Tipo)
					TiposNeg.add(((EntityCatalogElements)AR.getAssertName().getEntityClient()).getEntry());
				else UsuariosNeg.add(((EntityUser)AR.getAssertName().getEntityClient()).getAU());
		}
		
		TiposPos=generatiposRecursivo(TiposPos);
		TiposNeg=generatiposRecursivo(TiposNeg);
		
		ArrayList<Long> TiposPosIds = new ArrayList<Long>();
		for (EntryClient long1 : TiposPos) {
			TiposPosIds.add(long1.getId());
		}
		
		AsyncCallback<List<AnnotationClient>> callback= new AsyncCallback<List<AnnotationClient>>() {
			
			@Override
			public void onSuccess(List<AnnotationClient> result) {
				ResultadoRule=new ArrayList<AnnotationClient>();
				result=borrarTiposNegativos(result);
				result=borrarTiposNoPositivos(result);
				result=borrarUsuariosNegativos(result);
				ArrayList<AnnotationClient> Auxnot=FilterAsyncSystem.getAnotaciones();
				result=clearRep(result,Auxnot);
				Auxnot.addAll(result);
				FilterAsyncSystem.setAnotaciones(Auxnot);
				FilterAsyncSystem.nextRule();
				
			}
			
			private List<AnnotationClient> borrarTiposNoPositivos(
					List<AnnotationClient> result) {
				ArrayList<AnnotationClient> ResultadoAux = new ArrayList<AnnotationClient>();
				for (AnnotationClient annotation : result) {
					boolean esta=false;
					for (UserClient User : UsuariosPos) {
						if (annotation.getCreator().getId().equals(User.getId())) 
							{
							esta=true;
							break;
							}
					}
					if (esta) ResultadoAux.add(annotation);
					
				} 
				return ResultadoAux;
			}

			private ArrayList<AnnotationClient> clearRep(
						List<AnnotationClient> result,
						ArrayList<AnnotationClient> auxnot) {
					ArrayList<AnnotationClient> Salida=new ArrayList<AnnotationClient>();
					for (AnnotationClient A1 : result) {
						if (!Esta(A1,auxnot))
							Salida.add(A1);
					}
					return Salida;
				}



				private boolean Esta(AnnotationClient a1,
						ArrayList<AnnotationClient> auxnot) {
					for (AnnotationClient A2 : auxnot) {
						if (a1.getId().equals(A2.getId()))
							return true;
					}
					return false;
				}



				private ArrayList<AnnotationClient> borrarUsuariosNegativos(List<AnnotationClient> result) {
					ArrayList<AnnotationClient> ResultadoAux = new ArrayList<AnnotationClient>();
					for (AnnotationClient annotation : result) {
						boolean esta=false;
						for (UserClient User : UsuariosNeg) {
							if (annotation.getCreator().getId().equals(User.getId())) 
								{
								esta=true;
								break;
								}
						}
						if (!esta) ResultadoAux.add(annotation);
						
					} 
					return ResultadoAux;
				}

				private ArrayList<AnnotationClient> borrarTiposNegativos(List<AnnotationClient> result) {
					ArrayList<AnnotationClient> ResultadoAux = new ArrayList<AnnotationClient>();
					for (AnnotationClient annotation : result) {
						boolean esta=false;
						for (EntryClient Type : TiposNeg) {
							for (TypeClient  TypeInAnnot : annotation.getTags()) {
								if (Type.getId().equals(TypeInAnnot.getId())) 
								{
								esta=true;
								break;
								}	
							}
							if (esta) break;
							
						}
						if (!esta) ResultadoAux.add(annotation);
						
					} 
					return ResultadoAux;
				}

			@Override
			public void onFailure(Throwable caught) {
				FilterAsyncSystem.nextRule();
				Window.alert(ActualState.getLanguage().getE_filteringmesageAnnotations() + ActualState.getLanguage().getRule()+" :" + NameRule  );
				
			}
		};
		
		
		if (ActualState.getUser() instanceof StudentClient) 
			bookReaderServiceHolder.getAnnotationsByTypeClientIdsForStudent(TiposPosIds, ActualState.getReadingactivity().getId(), ActualState.getUser().getId(), callback);
		else 
			bookReaderServiceHolder.getAnnotationsByTypeClientIdsForProfessor(TiposPosIds, ActualState.getReadingactivity().getId(), ActualState.getUser().getId(), callback);
		
		
		
	}
	
	private ArrayList<EntryClient> generatiposRecursivo(ArrayList<EntryClient> tiposPos2) {
		ArrayList<EntryClient> Salida=new ArrayList<EntryClient>();
		for (EntryClient entryClient : tiposPos2) {
			if (entryClient instanceof TypeClient)
				Incluir(Salida,entryClient);
			else {
				ArrayList<EntryClient> nuevos=generatiposRecursivo((ArrayList<EntryClient>) ((TypeCategoryClient)entryClient).getChildren());
				for (EntryClient entryClient2 : nuevos) {
					Incluir(Salida,entryClient2);
				}
			}
		}
		return Salida;
	}


	private void Incluir(ArrayList<EntryClient> salida, EntryClient entryClient) {
		boolean encontrado = false;
		int indice = 0;
		while(!encontrado&&indice<salida.size())
		{
			if (salida.get(indice).getId().equals(entryClient.getId()))
				encontrado=true;
		}
		if (!encontrado)
			salida.add(entryClient);
		
	}


	public ArrayList<AnnotationClient> getResultadoRule() {
		return ResultadoRule;
	}
	
	
	public void setResultadoRule(ArrayList<AnnotationClient> resultadoRule) {
		ResultadoRule = resultadoRule;
	}

	public void setActual(boolean estado)
	{
		scrollPanel.setVisible(estado);
		if (estado) btnNewButton.setStyleName("gwt-ButtonIzquierdaSelect");
		else btnNewButton.setStyleName("gwt-ButtonIzquierda");
		
	}
	
	public VerticalPanel getRulePanel() {
		return RulePanel;
	}
	
	public void setRulePanel(VerticalPanel rulePanel) {
		RulePanel = rulePanel;
	}
}
