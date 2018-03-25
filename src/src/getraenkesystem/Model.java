package src.getraenkesystem;

import java.util.ArrayList;

public class Model {

	private ArrayList<Benutzer> benutzer;
	private ArrayList<Rolle> rollen;

	public Model() {

		this.benutzer = this.initBenutzer();
		this.rollen = this.initRollen();
		this.fill();

	}

	private ArrayList<Benutzer> initBenutzer() {

		ArrayList<Benutzer> benutzer = new ArrayList<Benutzer>();

		return benutzer;

	}

	private ArrayList<Rolle> initRollen() {

		this.rollen = new ArrayList<Rolle>();

		return this.rollen;

	}

	public void fill() {

		benutzer.add(new Benutzer("Chuck Norris", "kannalles"));
		benutzer.add(new Benutzer("Olga", "B"));
		benutzer.add(new Benutzer("Dirk", "H"));

		this.rollen.add(new Rolle("Admin"));
		ArrayList<String> adminRights = new ArrayList<String>();
		adminRights.add("R1");
		adminRights.add("R4");
		adminRights.add("R5");
		adminRights.add("R6");
		adminRights.add("R7");
		this.rollen.get(0).setRights(adminRights);
		adminRights.add("R10");
		this.rollen.add(new Rolle("Getraenkeverwalter"));
		this.rollen.get(1).setRights(adminRights);

		benutzer.get(0).setRole(this.rollen.get(0));
		benutzer.get(0).setKontostand(100);

	}

	public ArrayList<Benutzer> getUser() {

		return this.benutzer;

	}

	public ArrayList<Rolle> getRoles() {

		return this.rollen;

	}

	public void remove(Benutzer benutzer) {

		this.benutzer.remove(benutzer);

	}

	public void remove(Rolle rolle) {

		this.rollen.remove(rolle);

	}

}
