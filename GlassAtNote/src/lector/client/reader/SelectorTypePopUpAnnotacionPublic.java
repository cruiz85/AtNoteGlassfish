package lector.client.reader;

import lector.client.reader.PanelTextComent.CatalogTipo;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class SelectorTypePopUpAnnotacionPublic extends
		SelectorTypePopUpAnnotacion {

	public SelectorTypePopUpAnnotacionPublic(HorizontalPanel penelBotonesTipo,CatalogoClient Cata, CatalogTipo catalog2) {
		super(penelBotonesTipo,Cata, catalog2);
		setAllowCreate(true);
	}

}
