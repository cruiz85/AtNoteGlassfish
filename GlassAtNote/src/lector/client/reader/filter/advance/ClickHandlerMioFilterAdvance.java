package lector.client.reader.filter.advance;

import lector.client.admin.tagstypes.ClickHandlerMio;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.reader.browser.BotonesStackPanelBrowser;

import com.google.gwt.event.dom.client.ClickHandler;

public class ClickHandlerMioFilterAdvance extends ClickHandlerMio implements
		ClickHandler {

	
	
	public void onClickMan(BotonesStackPanelMio event) {
		Rule R=FilterAdvance.getActualRule();
		if (R!=null)
			{
			BotonesStackPanelBrowser BSPB=(BotonesStackPanelBrowser) event;
			AssertRule A= new AssertRule(BSPB.getEntidad().getName(),
					R.getRulePanel(),
					BSPB.getEntidad(),
					Tiposids.Tipo);
			R.addAssertRule(A);
			}

	}
	
}
