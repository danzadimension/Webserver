package src.getraenkesystem;

import java.util.ArrayList;

public class Rolle {
	
	private String name;
	private ArrayList<String> rights;
	
	public Rolle(String name) {
		
		this.name = name;
		this.rights = null;
		
	}
	
	public void setRights(ArrayList<String> rights) {
		
		this.rights = rights;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public ArrayList<String> getRights() {
		
		return this.rights;
		
	}

	@Override
	public String toString() {
		
		return this.name;
	
	}

}
