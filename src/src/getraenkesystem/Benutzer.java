package src.getraenkesystem;

public class Benutzer {

	private String name;
	private String passwort;
	private Rolle role;
	private int kontostand;

	public Benutzer(String name, String passwort) {

		this.name = name;
		this.passwort = passwort;
		this.role = null;
		this.kontostand = 0;

	}

	public String getName() {

		return this.name;

	}

	public String getPassword() {

		return this.passwort;

	}

	public Rolle getRole() {

		return this.role;

	}

	public int getKontostand() {

		return this.kontostand;

	}

	public void setKontostand(int amount) {

		this.kontostand = amount;

	}

	public void setRole(Rolle role) {

		this.role = role;

	}
	
	public void setName(String name) {
		
		this.name = name;
		
	}

}
