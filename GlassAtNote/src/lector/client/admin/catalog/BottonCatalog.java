package lector.client.admin.catalog;

import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BottonCatalog extends Button {

	private CatalogoClient catalog;
	private VerticalPanel Actual;
	private VerticalPanel Normal;
	private VerticalPanel Selected;
	
	public BottonCatalog(VerticalPanel Normalin,VerticalPanel Selectedin,CatalogoClient c) {
		super(c.getCatalogName());
		if (c.getIsPrivate()) setHTML("<b>*"+c.getCatalogName()+"</b>");
		
		this.catalog=c;
		Normal=Normalin;
		Selected=Selectedin;
		Actual=Normal;
		Actual.add(this);
	}
	
	public CatalogoClient getCatalog() {
		return catalog;
	}
	
	public void setCatalog(CatalogoClient catalog) {
		this.catalog = catalog;
	}
	
	
	public void swap()
	{
		if (Actual==Normal)
			{
			Actual.remove(this);
			Actual=Selected;
			Selected.add(this);
			}
		else if (Actual==Selected)
			{
			Selected.remove(this);
			Actual=Normal;
			Normal.add(this);
			}
	}
	
}
