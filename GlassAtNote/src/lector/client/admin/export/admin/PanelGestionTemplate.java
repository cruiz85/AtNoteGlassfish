package lector.client.admin.export.admin;

import java.util.ArrayList;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PanelGestionTemplate extends Composite {

	public TemplateClient T;
	private VerticalPanel PanelTemplate;
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private RepresentacionTemplateCategory ActualBase;
	private TemplateCategoryClient ActualBaseT;
	private static RepresentacionTemplateCategory Actual;
	
	public PanelGestionTemplate(TemplateClient t) {
		
		T=t;
		ActualBaseT = new TemplateCategoryClient(T.getName(),(ArrayList)T.getCategories(),
				new ArrayList<Long>(), null, T);
		ActualBaseT.setId(Constants.TEMPLATEID);
		ActualBase=new RepresentacionTemplateCategory(ActualBaseT,null,0);
		Actual=ActualBase;
		Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
		Actual.setSelected(true);
		ActualBase.setclickHandel(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
				Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
				Actual.setSelected(false);
				Actual=Mas.getTRep();
				Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
				Actual.setSelected(true);
				
			}
		});
		PanelTemplate = new VerticalPanel();
		PanelTemplate.add(Actual);
		initWidget(PanelTemplate);
		refresh();
		
		
	
	}

	private void procesaHijo(RepresentacionTemplateCategory nuevo) {
		for (TemplateCategoryClient templateCategory : nuevo.getT().getSubCategories()) {
			RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, nuevo,nuevo.getProfundidad()+1);
			Nuevo.setclickHandel(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
					Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
					Actual.setSelected(false);
					Actual=Mas.getTRep();
					Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
					Actual.setSelected(true);
					
				}
			});
			nuevo.addSon(Nuevo);
			procesaHijo(Nuevo);
			
		}
		
	}

	public TemplateCategoryClient getActualNode() {
		return Actual.getT();
	}
	
	public static void setActual(RepresentacionTemplateCategory actual) {
		Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
		Actual.setSelected(false);
		Actual = actual;
		Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
		Actual.setSelected(true);
	}

	public void refresh() {
		Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
		Actual.setSelected(false);
		Actual=ActualBase;
		Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
		Actual.setSelected(true);
		ActualBase.clear();
		PanelTemplate.clear();
		PanelTemplate.add(Actual);
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.LOADING);
		exportServiceHolder.loadTemplateById(T.getId(), new AsyncCallback<TemplateClient>() {
			
			public void onSuccess(TemplateClient result) {
				LoadingPanel.getInstance().hide();
				ActualBaseT.setSubCategories(result.getCategories());
				T=result;
				for (TemplateCategoryClient templateCategory : T.getCategories()) {
					RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, Actual,Actual.getProfundidad()+1);
					Nuevo.setclickHandel(new ClickHandler() {
						
						public void onClick(ClickEvent event) {
							ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
							Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
							Actual.setSelected(false);
							Actual=Mas.getTRep();
							Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
							Actual.setSelected(true);
							
						}
					});
					ActualBase.addSon(Nuevo);
					procesaHijo(Nuevo);
					
				}
//				exportServiceHolder.getTemplatesByIds(T.getCategories(), new AsyncCallback<ArrayList<TemplateCategory>>() {
//					
//					public void onSuccess(ArrayList<TemplateCategory> result) {
//						LoadingPanel.getInstance().hide();
//						for (TemplateCategory templateCategory : result) {
//							RepresentacionTemplateCategory Nuevo=new RepresentacionTemplateCategory(templateCategory, Actual,Actual.getProfundidad());
//							Nuevo.setclickHandel(new ClickHandler() {
//								
//								public void onClick(ClickEvent event) {
//									ButtonTemplateRep Mas=(ButtonTemplateRep)event.getSource();
//									Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDoble");
//									Actual.setSelected(false);
//									Actual=Mas.getTRep();
//									Actual.getBotonSelect().setStyleName("gwt-ButtonCenterContinuoDobleSelect");
//									Actual.setSelected(true);
//									
//								}
//							});
//							ActualBase.addSon(Nuevo);
//							ArbitroLlamadasTemplates.getInstance().addElement(Nuevo);
//						}
//						
//						
//					}
//					
//					public void onFailure(Throwable caught) {
//						LoadingPanel.getInstance().hide();
//						Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_CATEGORIES1+ T.getName() + ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_CATEGORIES2);
//						
//					}
//				});
			}
			
			public void onFailure(Throwable caught) {
				LoadingPanel.getInstance().hide();
				Window.alert(ConstantsError.ERROR_REFRESH_TEMPLATES);
				
			}
		});
		
	}
	
	public static RepresentacionTemplateCategory getActual() {
		return Actual;
	}

}
