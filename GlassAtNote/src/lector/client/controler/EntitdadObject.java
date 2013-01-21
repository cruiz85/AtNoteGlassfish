package lector.client.controler;

import java.io.Serializable;

public abstract class EntitdadObject implements Serializable {

	private String Name;

	public EntitdadObject(String Namein) {
		Name = Namein;
	}


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
