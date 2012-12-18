package lector.client.reader;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.controler.ActualState;
import lector.client.reader.PanelTextComent.CatalogTipo;

public class ClickHandlerMioSelector extends ClickHandlerMio {


	private CatalogTipo CT;
	
	public ClickHandlerMioSelector(CatalogTipo cT) {
		CT=cT;
	}
	
	@Override
	public void onClickMan(BotonesStackPanelMio event) {
		
     	BotonesStackPanelReaderSelectMio BS=(BotonesStackPanelReaderSelectMio) event;
     EntityCatalogElements Act=(EntityCatalogElements) BS.getEntidad();
     
     if (Act instanceof File)
     {
     	ButtonTipo nuevo=new ButtonTipo(Act,CT.getTexto(),BS.getLabeltypo());
     	nuevo.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
					
				}
			});
		
     	nuevo.addMouseDownHandler(new MouseDownHandler() {
				public void onMouseDown(MouseDownEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
				}
			});
			

     	nuevo.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
			

     	nuevo.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					
					((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
				
			}
		});

     	nuevo.setStyleName("gwt-ButtonCenter");
     	nuevo.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					ButtonTipo Yo=(ButtonTipo)event.getSource();
					Yo.getPertenezco().remove(Yo);
					
					
				}
			});
     	if (!ExistPreview(BS.getLabeltypo(),Act))
     			BS.getLabeltypo().add(nuevo);
     	else Window.alert(ActualState.getLanguage().getE_ExistBefore());
     }
     }

		private boolean ExistPreview(HorizontalPanel labeltypo, EntityCatalogElements act) {
			for (int i = 0; i < labeltypo.getWidgetCount(); i++) {
				EntityCatalogElements temp = ((ButtonTipo)labeltypo.getWidget(i)).getEntidad();
				if (temp.getEntry().getId()==act.getEntry().getId()) return true;
				
			}
			return false;
		}
}
