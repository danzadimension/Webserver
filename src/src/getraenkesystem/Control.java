package src.getraenkesystem;

import java.util.ArrayList;
import java.util.Map;

public class Control {

	public static boolean checkLoginData(Model model, Map<String, String[]> map) {

		ArrayList<Benutzer> benutzer = model.getUser();

		for (int i = 0; i < benutzer.size(); i++) {

			if (benutzer.get(i).getName().equals(map.get("benutzerName")[0])
					&& benutzer.get(i).getPassword()
							.equals(map.get("passwort")[0])) {

				return true;

			}

		}

		return false;

	}

	public static boolean userDataUnique(Model model, Map<String, String[]> map) {

		ArrayList<Benutzer> benutzer = model.getUser();

		for (int i = 0; i < benutzer.size(); i++) {

			if (benutzer.get(i).getName().equals(map.get("benutzerNameNeu")[0])
					&& benutzer.get(i).getPassword()
							.equals(map.get("passwortNeu")[0])) {

				return true;

			}

		}

		return false;

	}

	public static boolean containsUser(ArrayList<Benutzer> benutzer, String name) {

		for (int i = 0; i < benutzer.size(); i++) {

			if (benutzer.get(i).getName().equals(name)) {

				return true;

			}

		}

		return false;

	}
	
	public static boolean containsRole(ArrayList<Rolle> rollen, String name) {

		for (int i = 0; i < rollen.size(); i++) {

			if (rollen.get(i).getName().equals(name)) {

				return true;

			}

		}

		return false;

	}
	
	public static Benutzer getUser(Model model, String name) {
		
		ArrayList<Benutzer> benutzer = model.getUser();
		Benutzer b = null;
		
		for (int i = 0; i < benutzer.size(); i++) {

			if (benutzer.get(i).getName().equals(name)) {

				return benutzer.get(i);

			}

		}
		
		return null;
		
	}

}
