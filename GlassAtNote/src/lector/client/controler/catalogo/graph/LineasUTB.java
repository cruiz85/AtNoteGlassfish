package lector.client.controler.catalogo.graph;

import com.google.gwt.user.client.ui.Button;

public class LineasUTB implements LineasUT{

	private LineasT LT;
	private Button BT;
	
	public LineasUTB(LineasT lT, Button bT) {
		LT = lT;
		BT = bT;
	}
		
	public int getStartX()
	{
	return BT.getAbsoluteLeft()+(BT.getOffsetWidth()/2);	
	}
	
	public int getStartY()
	{
	return LT.getStartCenterY();	
	}
	
	
	public int getFinalX()
	{
	return BT.getAbsoluteLeft()+(BT.getOffsetWidth()/2);	
	}
	
	public int getFinalY()
	{
	return BT.getAbsoluteTop();	
	}
}
