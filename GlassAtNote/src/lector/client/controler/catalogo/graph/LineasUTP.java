package lector.client.controler.catalogo.graph;

import com.google.gwt.user.client.ui.SimplePanel;

public class LineasUTP implements LineasUT{

	private LineasT LT;
	private SimplePanel SP;
	private SimplePanel SPOrigen;
	
	public LineasUTP(Connect2vs3 lineasunion, SimplePanel sP) {
		LT = lineasunion.getLinea();
		SP = sP;
		SPOrigen=lineasunion.getsP();
	}
		
	public int getStartX()
	{
	return SP.getAbsoluteLeft()+(SP.getOffsetWidth()/2);	
	}
	
	public int getStartY()
	{
	return SP.getAbsoluteTop()+(SP.getOffsetHeight()/2);	
	}
	
	public int getCodoX()
	{
	return SPOrigen.getAbsoluteLeft()+(SPOrigen.getOffsetWidth()/2);	
	}
	
	public int getCodoY()
	{
	return SP.getAbsoluteTop()+(SP.getOffsetHeight()/2);	
	}
	
	public int getFinalX()
	{
	return SPOrigen.getAbsoluteLeft()+(SPOrigen.getOffsetWidth()/2);	
	}
	
	public int getFinalY()
	{
	return LT.getStartCenterY();	
	}
}
