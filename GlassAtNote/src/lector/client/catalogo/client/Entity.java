package lector.client.catalogo.client;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	private String Name;

	public Entity(String Namein) {
		Name = Namein;
	}


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
