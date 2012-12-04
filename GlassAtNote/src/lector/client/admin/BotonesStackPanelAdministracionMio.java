package lector.client.admin;



import lector.client.admin.book.EntidadLibro;
import lector.client.catalogo.BotonesStackPanelMio;
import lector.client.catalogo.Finder;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.share.model.GeneralException;

import com.google.gwt.user.client.ui.VerticalPanel;

public class BotonesStackPanelAdministracionMio extends BotonesStackPanelMio{

	protected VerticalPanel Selected;
	protected VerticalPanel Normal;
	private Finder F;
	
	public BotonesStackPanelAdministracionMio(String HTML,VerticalPanel Normal, VerticalPanel Selected, Finder Fin) {
		super(HTML);
		this.Normal=Normal;
		this.Selected=Selected;
		this.Actual=Normal;
		F=Fin;
		Actual.add(this);
	}
	

	public void Swap() {
		Actual.remove(this);
		if (Actual==Normal)
			{
			Actual=Selected;
			}
		else 
			{
			Actual=Normal;
			}
		if((this.getEntidad()!=null)&&!EstainSelected())
			if (Actual==Normal)
			{
				if (checkFamilia())
					Actual.add(this);
			}
			else Actual.add(this);
	}
	
	
	

	private boolean checkFamilia() {
		if (F==null) return true;
		return F.getTopPath().equals(((EntityCatalogElements)super.getEntidad()).getFatherIdCreador());
	}

	public BotonesStackPanelMio Clone()
	{
	BotonesStackPanelAdministracionMio New=new BotonesStackPanelAdministracionMio(getHTML(), Normal, Selected,F);	
	New.setActual(getActual());
	return New;
	}
	
	public VerticalPanel getSelected() {
		return Selected;
	}
	
	public void setSelected(VerticalPanel selected) {
		Selected = selected;
	}
	public void setNormal(VerticalPanel normal) {
		Normal = normal;
	}
	
	public VerticalPanel getNormal() {
		return Normal;
	}

	@Override
	public void setActual(VerticalPanel actual) {
		Normal=actual;
		if (Actual!=null)Actual.remove(this);
		Actual = actual;
		if((this.getEntidad()!=null)&&!EstainSelected())
			Actual.add(this);
	
	}

	protected boolean EstainSelected(){
		if (((Entity)super.getEntidad()) instanceof EntityCatalogElements)
			return processCatalem();
		if (((Entity)super.getEntidad()) instanceof EntidadLibro )
			return processLibro();
		return false;
	}

	private boolean processLibro() {
		for (int i = 0; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelAdministracionMio BSM= (BotonesStackPanelAdministracionMio)Selected.getWidget(i);
			if (((EntidadLibro)BSM.getEntidad()).getBook().getId().intValue()==((EntidadLibro)super.getEntidad()).getBook().getId().intValue()) return true;
		}
		return false;
	}


	private boolean processCatalem() {
		for (int i = 0; i < Selected.getWidgetCount(); i++) {
			BotonesStackPanelAdministracionMio BSM= (BotonesStackPanelAdministracionMio)Selected.getWidget(i);
			if (((EntityCatalogElements)BSM.getEntidad()).getEntry().getId().intValue()==((EntityCatalogElements)super.getEntidad()).getEntry().getId().intValue()) return true;
		}
		return false;
	}


	public Finder getF() {
		return F;
	}
	
	public void setF(Finder f) {
		F = f;
	}

}
