package src.QueryServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService
@SOAPBinding(style = Style.RPC)

public class QueryServer {

	Connection conn = null;
	static Object sync = new Object();

	/**
	 * Konstruktor des QueryServers Er bindet den JDBC mySQL Treiber ein und
	 * stellt eine Verbindung zur Datenbank her
	 */
	public QueryServer() {

		// JDBC Treiber
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return;
		}

		// Datenbankverbindung
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/Floridadrinks?user=root&password=qwertz");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return;
		}
	}

	public QueryServer(String benutzername, String passwort) {

		// JDBC Treiber
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return;
		}

		// Datenbankverbindung
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/Floridadrinks?user="
							+ benutzername + "&password=" + passwort);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) {
		QueryServer server = new QueryServer();
		// System.out.println(server.erstelleBenutzer(null, "robert", "qwertz",
		// "1234567890", "0987654321", null, false));
		/*
		 * QueryResult queryResult = server.authentifiziereBenutzer("Robert",
		 * "qwertz", "4321435865", null);
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0)
		 * System.out.println("Authentifizierter Benutzer: "
		 * +queryResult.getQueryResult().get(0)[0]);
		 */
		/*
		 * QueryResult queryResult =
		 * server.dreiMeistGekauftenGetraenke("Martin Wende");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.print("Der Benutzer hat folgende Favourites: "); while
		 * (iterator.hasNext()) { System.out.print(iterator.next()[0]+", "); } }
		 */
		// System.out.println(server.setzeCheckoutverhalten("Administrator",
		// "Countdown"));
		// System.out.println(server.setzeCountdownlaenge(null, 6));
		// System.out.println(server.fuelleBestandAuf("Administrator",
		// "648359422", 10));
		/*
		 * QueryResult queryResult = server.frageBestandAb(null, "94835942");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Bestand: "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+" - ");
		 * System.out.print(table[1]+": "); System.out.println(table[2]); } }
		 */
		// System.out.println(server.passeBestandAn("65839403", 12, -1));
		// System.out.println(server.passeGetraenkAn("75739743", null, 2.0f,
		// 2.50f));
		// System.out.println(server.loescheGetraenk("75398490"));
		// System.out.println(server.fuegeGetraenkHinzu("123456788",
		// "Club-Freund", 5.0f, 0.1f, 3));
		// System.out.println(server.setzeGlobalesKontolimit(-30.0f));
		// System.out.println(server.ladeKontoAuf("Administratorr", -20f));
		/*
		 * QueryResult queryResult = server.frageKontostandAb("Administratorr");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.print("Der Benutzer hat folgenden Kontostand: "); while
		 * (iterator.hasNext()) { System.out.print(iterator.next()[0]+", "); } }
		 */
		/*
		 * QueryResult queryResult = server.frageUmsaetzeAb(null, null, null,
		 * null); System.out.println("ErrorCode: "+queryResult.getErrorCode());
		 * if (queryResult.getErrorCode() == 0) { Iterator <String []> iterator
		 * = queryResult.getQueryResult().iterator();
		 * System.out.println("Die Umsaetze: "); while (iterator.hasNext()) {
		 * String table [] = iterator.next(); System.out.print(table[0]+" | ");
		 * System.out.print(table[1]+" | "); System.out.print(table[2]+" | ");
		 * System.out.print(table[3]); if (table.length>4)
		 * System.out.print(" | "+table[4]); System.out.println(""); } }
		 */
		/*
		 * QueryResult queryResult = server.frageGruppenAb("Robert");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Gruppen + Beschreibung: "); while
		 * (iterator.hasNext()) { String table [] = iterator.next();
		 * System.out.print(table[0]+" | "); System.out.print(table[1]);
		 * System.out.println(""); } }
		 */
		/*
		 * QueryResult queryResult =
		 * server.benutzerrechteAbfragen("Administrator");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Rechte + Beschreibung: "); while
		 * (iterator.hasNext()) { String table [] = iterator.next();
		 * System.out.print(table[0]+" | "); System.out.print(table[1]);
		 * System.out.println(""); } }
		 */
		/*
		 * QueryResult queryResult = server.frageKontolimitAb(); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Globales Konotlimit: "); while
		 * (iterator.hasNext()) { String table [] = iterator.next();
		 * System.out.print(table[0]); } }
		 */
		/*
		 * QueryResult queryResult = server.frageGetraenkAb(null);
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Die Getraenke: "); while (iterator.hasNext()) {
		 * String table [] = iterator.next(); System.out.print(table[0]+" | ");
		 * System.out.print(table[1]+" | "); System.out.print(table[2]+" | ");
		 * System.out.print(table[3]+" | "); System.out.print(table[4]+" | ");
		 * System.out.print(table[5]); System.out.println(""); } }
		 */
		/*
		 * QueryResult queryResult = server.frageCountdownAb();
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.print("Countdown: "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.println(table[0]); } }
		 */
		/*
		 * QueryResult queryResult = server.frageLogoutverhaltenAb();
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.print("Countdown: "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.println(table[0]); } }
		 */
		// System.out.println(server.aenderePasswort("Daniel Graff",
		// "bla").getErrorCode());
		// System.out.println(server.aendereRollenbeschreibung(null,
		// "Looser").getErrorCode());
		// System.out.println(server.fuehreCheckoutDurch("Administrator",
		// "64835942", 2.0f));

		// System.out.println(server.assignRightToRole(null,
		// "Getraenkeverwalter", "R01").getErrorCode());
		// System.out.println(server.c(null, "Martin Wende",
		// "Administrator").getErrorCode());
		// System.out.println(server.createRole(null,
		// "Plueschtier").getErrorCode());
		// System.out.println(server.deleteRole(null,
		// "Getraenkeverwalter").getErrorCode());
		// System.out.println(server.deleteUser(null,
		// "Martin Wende").getErrorCode());

		/*
		 * QueryResult queryResult = server.getRightsByRole(null,
		 * "Administrator");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Roles "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+ " | ");
		 * System.out.println(table[1]); } }
		 */

		/*
		 * QueryResult queryResult = server.getUsersOverview("bla");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Roles "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+ " | ");
		 * System.out.print(table[1]+ " | "); System.out.print(table[2]+ " | ");
		 * System.out.println(table[3]); } }
		 */

		/*
		 * QueryResult queryResult = server.getUsersRoles(null,
		 * "Michael Kamme");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Roles "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+ " | ");
		 * System.out.println(table[1]); } }
		 */

		/*
		 * QueryResult queryResult = server.getRights(null);
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Rechte: "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+ " | ");
		 * System.out.println(table[1]); } }
		 */
		// System.out.println(server.removeRightFromRole(null,
		// "Getraenkeverwalter", "R09").getErrorCode());
		// System.out.println(server.removeRoleFromUser(null, "Martin Wende",
		// "Getraenkeverwalter").getErrorCode());
		// System.out.println(server.updateUser(null, "martin Wende", "blakex",
		// "kexbla", true, "Max Mustermann").getErrorCode());
		/*
		 * QueryResult queryResult = server.searchRole("bla", "Administrator");
		 * System.out.println("ErrorCode: "+queryResult.getErrorCode()); if
		 * (queryResult.getErrorCode() == 0) { Iterator <String []> iterator =
		 * queryResult.getQueryResult().iterator();
		 * System.out.println("Rechte: "); while (iterator.hasNext()) { String
		 * table [] = iterator.next(); System.out.print(table[0]+ " | ");
		 * System.out.println(table[1]); } }
		 */

		QueryResult queryResult = server.searchUser("bla", "blakex");
		System.out.println("ErrorCode: " + queryResult.getErrorCode());
		if (queryResult.getErrorCode() == 0) {
			Iterator<String[]> iterator = queryResult.getQueryResult()
					.iterator();
			System.out.println("Rechte: ");
			while (iterator.hasNext()) {
				String table[] = iterator.next();
				System.out.print(table[0] + " | ");
				System.out.print(table[1] + " | ");
				System.out.print(table[2] + " | ");
				System.out.println(table[3]);
			}
		}
	}

	/**
	 * Methode um einer Rolle ein Recht zuzuweisen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param role
	 *            Benutzerrolle, der ein Recht zugewiesen werden soll<br>
	 * @param right
	 *            Recht, welches der Rolle zugewiesen werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn role oder right null sind<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	public QueryResult assignRightToRole(String executor, String role,
			String right) {

		if (role == null)
			return (new QueryResult(-2));
		if (right == null)
			return (new QueryResult(-2));
		synchronized (QueryServer.sync) {
			return Rollenverwaltung.assignRightToRole(executor, role, right,
					this.conn);
		}
	}

	/**
	 * Methode um einem Benutzer eine Role zuzuweisen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param role
	 *            Benutzerrolle, die einem Benutzer zugewiesen werden soll<br>
	 * @param user
	 *            Benutzer, dem eine Rolle zugewiesen werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn role oder usert null sind<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	public QueryResult assignRoleToUser(String executor, String user,
			String role) {

		// Null Werte abfangen
		if (role == null)
			return new QueryResult(-2);
		if (role == user)
			return new QueryResult(-2);

		synchronized (QueryServer.sync) {
			return Benutzerverwaltung.assignRoleToUser(executor, user, role,
					this.conn);
		}
	}

	/**
	 * Methode um eine neue Rolle zu erstellen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param role
	 *            Benutzerrolle, die erstellt werden soll<br>
	 * @param user
	 *            Benutzer, dem eine Rolle zugewiesen werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn role null ist<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	public QueryResult createRole(String executor, String role) {

		if (role == null)
			return new QueryResult(-2);

		synchronized (QueryServer.this) {
			return Rollenverwaltung.createRole(executor, role, this.conn);
		}
	}

	/**
	 * Methode um eine Rolle zu löschen <br>
	 * <br>
	 * 
	 * @param executor
	 *            derjenige, der den Befehl ausführt - optional, dienst zur
	 *            Überprüfung, ob er seine eigenen Rollen löschen würde <br>
	 * @param role
	 *            Benutzerrolle, die geloescht werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn Benutzer seine eigene Rolle loeschen moechte <br>
	 *         -2, wenn der Administrator geloescht werden wuerde <br>
	 *         -3, wenn role null ist <br>
	 *         -4, wenn die Rolle nicht existiert oder der Executor keine Rollen
	 *         hat <br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	public QueryResult deleteRole(String executor, String role) {

		// Null Werte abfangen
		if (role == null)
			return new QueryResult(-3);

		// RPC-Anpassung
		if (executor.equals(""))
			executor = null;

		if (role.equals("Administrator")) {
			System.out
					.println("Fehler: Benutzer versucht den Administrator zu loeschen.");
			return new QueryResult(-2);
		}

		synchronized (QueryServer.sync) {
			// Überprüfe, ob der Executor eine seiner eigenen Rollen löschen
			// würde
			if (executor != null) {
				QueryResult result = this.getUsersRoles(executor, executor);
				if (result.getErrorCode() != 0) {
					if (result.getErrorCode() != -4)
						return new QueryResult(result.getErrorCode());
				} else {
					LinkedList<String[]> rows = result.getQueryResult();
					for (String[] row : rows) {
						if (row[1].equals(role)) {
							System.out
									.println("Fehler: Benutzer versucht eine seiner eigenen Rollen zu loeschen.");
							return new QueryResult(-1);
						}
					}
				}
			}
			return Rollenverwaltung.deleteRole(role, this.conn);
		}

	}

	/**
	 * Methode um einen Benutzer zu löschen <br>
	 * <br>
	 * 
	 * @param executor
	 *            derjenige, der den Befehl ausführt - optional, dienst zur
	 *            Überprüfung, ob er sich selbst löschen würde<br>
	 * @param user
	 *            Benutzer, der geloescht werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn Benutzer sich selbst loeschen moechte <br>
	 *         -2, wenn der Administrator geloescht werden wuerde <br>
	 *         -3, wenn user null <br>
	 *         -4, wenn user nicht existiert <br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	public QueryResult deleteUser(String executor, String user) {

		if (user == null)
			return new QueryResult(-3);

		// RPC Anpassung
		if (executor != null)
			if (executor.equals(""))
				executor = null;

		if (executor != null) {
			if (executor.equals(user)) {
				System.err
						.println("Fehler: Benutzer versucht sich selbst zu loeschen.");
				return new QueryResult(-1);
			}
		}

		if (user.equals("Administrator")) {
			System.err
					.println("Fehler: Benutzer versucht den Administrator zu loeschen.");
			return new QueryResult(-2);
		}
		synchronized (QueryServer.sync) {
			return Benutzerverwaltung.deleteUser(executor, user, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der Benutzerrollen und deren Rechte <br>
	 * <br>
	 * 
	 * @param role
	 *            wird ignoriert<br>
	 * @param executor
	 *            wird ignoriert <br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Rechtnummer"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn keine Rollen vorhanden sind<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getRightsByRole(String executor, String role) {

		// Überprüfe, ob erforderliche Werte nicht null sind
		if (role == null)
			return (new QueryResult(-2));

		synchronized (QueryServer.sync) {
			return Rollenverwaltung.getRightsByRole(role, this.conn);
		}
	}

	@Deprecated
	/**
	 * Methode zur Abfrage der Rechtebeschreibung - nutze besser getRights! <br>
	 * <br>
	 * @return 	Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			"Nummer", "Beschreibung"<br>
	 * 			ErrorCodes:<br>
	 * 			 0, wenn ok<br>
	 * 			-4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte zugewiesen bekommen hat<br>
	 * 		 	>0,	SQL-Fehler<br>
	 */
	public QueryResult getRightsDescription(String executor) {
		synchronized (QueryServer.sync) {
			return Rollenverwaltung.getRights(this.conn);
		}
	}

	@Deprecated
	/**
	 * Methode zur Abfrage der Rechtebeschreibung - nutze besser getRoles! <br>
	 * <br>
	 * @return 	Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			"Gruppenname, "Gruppenbeschreibung"<br>
	 * 			ErrorCodes:<br>
	 * 			 0, wenn ok<br>
	 * 			-4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte zugewiesen bekommen hat<br>
	 * 		 	>0,	SQL-Fehler<br>
	 */
	public QueryResult getRolesDescriptions(String executor) {
		synchronized (QueryServer.sync) {
			return Rollenverwaltung.getRoles(this.conn);
		}
	}

	/**
	 * Gibt die drei meistgekauften Getraenke des übergebenen Benutzers zurück
	 * 
	 * @param name
	 *            Der Benutzer
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 */
	public QueryResult getRolle(String name) {
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserGroups(name, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage aller Gruppennamen und deren Beschreibung <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Gruppenbeschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn keine Rollen vorhanden sind<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getRoles(String executor) {
		synchronized (QueryServer.sync) {
			return Rollenverwaltung.getRoles(this.conn);
		}
	}

	/**
	 * Methode zur Abfrage von Gruppennamen und deren zugewiesenen Rechten <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Nummer"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn keine Gruppen existieren<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getRolesRights(String executor) {
		synchronized (QueryServer.sync) {
			return Rollenverwaltung.getRolesRights(this.conn);
		}
	}

	/**
	 * Methode zur Abfrage aller Benutzernamen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Benutzername" "RFID", "Benutzerbarcode", "Gesperrt" <br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn keine Benutzer in der Datenbank <br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getUsersOverview(String executor) {
		synchronized (QueryServer.sync) {
			return Benutzerverwaltung.getUsersOverview(this.conn);
		}
	}

	public LinkedList<String> getUsersRights(String executor, String user) {

		synchronized (QueryServer.this) {

			return Benutzerverwaltung.getUsersRights(executor, user, this,
					this.conn);

		}

	}

	/**
	 * Methode zur Abfrage von von den zugewiesenen Gruppen eines Benutzers <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert <br>
	 * @param user
	 *            benutzer, dessen Rechte angezeigt werden sollen <br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Benutzername", "Gruppenname"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -3, wenn Benutzername null -4, wenn kein Benutzer einer Gruppe
	 *         zugewiesen ist<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getUsersRoles(String executor, String user) {

		// Falsche Werte abfangen
		if (user == null)
			return new QueryResult(-3);

		synchronized (QueryServer.sync) {
			return Benutzerverwaltung.getUserRoles(user, this.conn);
		}

	}

	/**
	 * Methode zur Abfrage der Rechte <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert <br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Nummer", "Beschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte
	 *         zugewiesen bekommen hat<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult getRights(String executor) {
		synchronized (this.sync) {
			return Rollenverwaltung.getRights(this.conn);
		}
	}

	/**
	 * Methode zur Löschung der Zuweisung eines Rechtes zu einer Rolle <br>
	 * <br>
	 * 
	 * @param executor
	 *            Benutzer, der den Befehl ausführt, dienst zur Überprüfung, ob
	 *            er sich selbst entmachtet - optional
	 * @param role
	 *            Rolle, von der ein Recht entfern werden soll
	 * @param right
	 *            Recht, welches von der Rolle entfernt werden soll
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn Benutzer versucht, die Rollen in der er sich selbst
	 *         befindet die Rechte zu entziehen -2, wenn Benutzer versucht, den
	 *         Administrator zu löschen -3, wenn role oder right null -4, wenn
	 *         Rolle oder Recht nicht verknüpft gewesen sind<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult removeRightFromRole(String executor, String role,
			String right) {

		// Null Werte bafangen
		if (role == null)
			return new QueryResult(-3);
		if (right == null)
			return new QueryResult(-3);

		// RPC Anpassung
		if (executor != null)
			if (executor.equals(""))
				executor = null;

		if (role.equals("Administrator")) {
			System.out
					.println("Fehler: Benutzer versucht den Administrator zu loeschen.");
			return new QueryResult(-2);
		}

		synchronized (this.sync) {
			if (executor != null) {
				QueryResult result = this.getUsersRoles(executor, executor);
				if (result.getErrorCode() != 0) {
					if (result.getErrorCode() != -4)
						return (new QueryResult(result.getErrorCode()));
				} else {
					LinkedList<String[]> rows = result.getQueryResult();
					for (String[] row : rows) {
						if (row[1].equals(role)) {
							System.out
									.println("Fehler: Benutzer versucht sich selbst zu entrechten.");
							return new QueryResult(-1);
						}
					}
				}
			}
			return Rollenverwaltung.removeRightFromRole(role, right, this.conn);
		}
	}

	/**
	 * Methode zur Löschung der Zuweisung eines Rolle zu einem Benutzer <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn der Benutzer sich selbst eine Rolle entziehen will -2,
	 *         wenn Benutzer versucht, die Rollen des Administrators zu
	 *         entziehen -3, wenn role oder right oder executor null -4, wenn
	 *         Rolle oder Recht nicht verknüpft gewesen sind<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult removeRoleFromUser(String executor, String user,
			String role) {

		// Null Werte bafangen
		if (role == null)
			return new QueryResult(-3);
		if (user == null)
			return new QueryResult(-3);

		// RPC-Anpassung
		if (executor != null)
			if (executor.equals(""))
				executor = null;

		if (executor != null) {
			if (executor.equals(user)) {
				System.out
						.println("Fehler: Benutzer versucht sich selbst seine Rolle(n) zu entziehen.");
				return new QueryResult(-1);
			}
		}
		if (user.equals("Administrator")) {
			System.out
					.println("Fehler: Benutzer versucht dem Administrator seine Rolle(n) zu entziehen.");
			return new QueryResult(-2);

		}
		synchronized (this.sync) {
			return Benutzerverwaltung.removeRoleFromUser(user, role, this.conn);
		}

	}

	/**
	 * Methode, um einen Benutzer zu aktualisieren <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert <br>
	 * @param user
	 *            Benutzer, der aktualisiert werden soll - erforderlich <br>
	 * @param rfid
	 *            Neue RFID des benutzers <br>
	 * @param benutzerbarcode
	 *            Neuer Benutzerbarcode des Benutzers <br>
	 * @param gesperrt
	 *            Neuer Sperrzustand des Benutzers <br>
	 * @param newUsername
	 *            Neuer Benutzername <br>
	 *            Will man einzelne Spalten nicht aendern, einfach den alten
	 *            Wert einsetzen
	 * 
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Kasse
	 *         Errorcodes: 0, wenn ok -2, wenn Benutzer versucht, den
	 *         Administrator zu ändern -3, wenn die eroderlichen Werte null sind
	 *         -4, wenn der Benutzer nicht in der Datenbank existiert >0, SQL
	 *         Fehler
	 */
	public QueryResult updateUser(String executor, String user, String rfid,
			String benutzerbarcode, boolean gesperrt, String newUsername) {

		// Nullwerte abfangen
		if (user == null)
			return new QueryResult(-3);
		if (rfid == null)
			return new QueryResult(-3);
		if (benutzerbarcode == null)
			return new QueryResult(-3);
		if (newUsername == null)
			return new QueryResult(-3);

		if (user.equals("Administrator")) {
			System.out
					.println("Fehler: Benutzer versucht Administrator zu veraendern.");
			return new QueryResult(-2);
		}

		synchronized (this.sync) {
			return Benutzerverwaltung.updateUser(user, rfid, benutzerbarcode,
					gesperrt, this.conn, newUsername);
		}

	}

	/**
	 * Methode, um nach einer bestimmten Rolle zu suchen<br>
	 * <br>
	 * 
	 * @param role
	 *            die Rolle, nach der gesucht werden soll<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Gruppenbeschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -3, wenn rolle null<br>
	 *         -4, wenn rolle nicht gefunden<br>
	 */
	public QueryResult searchRole(String executor, String role) {
		if (role == null)
			return new QueryResult(-3);

		synchronized (this.sync) {
			return Rollenverwaltung.searchRole(role, this.conn);
		}
	}

	/**
	 * Methode, um nach einem bestimmten Benutzer zu suchen <br>
	 * <br>
	 * 
	 * @param user
	 *            Der Benutzername, der Benutzerbarcode oder die RFID, nach der
	 *            gesucht werden soll<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Benutzername", "RFID", "benutzerbarcode", "Geperrt"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -3, wenn user null<br>
	 *         -4, wenn user nicht gefunden<br>
	 */
	public QueryResult searchUser(String executor, String user) {

		if (user == null)
			return new QueryResult(-3);

		synchronized (this.sync) {
			return Benutzerverwaltung.searchUser(user, this.conn);
		}
	}

	/**
	 * Methode zur Authentifizierung eines Benutzers gegen die Datenbank <br>
	 * <br>
	 * Es ist ENTWEDER Benutzername & Passwort <br>
	 * ODER Barcode <br>
	 * ODER RFID <br>
	 * <br>
	 * erforderlich. Sind mehrere Methoden möglich, wird nur gegen die erste
	 * mögliche Variante in dieser Liste authentifiziert <br>
	 * <br>
	 * 
	 * @param name
	 *            Benutzername <br>
	 * @param passwort
	 *            Passworthash des Benututzers <br>
	 * @param rfid
	 *            RFID Code des Benutzers <br>
	 * @param benutzerbarcode
	 *            Barcode des Benutzers <br>
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse <br>
	 *         * Spalten: <br>
	 *         "Recht" <br>
	 *         ErrorCodes: <br>
	 *         0, wenn ok <br>
	 *         -2, wenn (name && rfid && benutzerbarcode) == null oder wenn
	 *         (passwort, rfid, benutzerbarcode) == null oder alle parameter ==
	 *         null <br>
	 *         -4, Unbekannter Benutzername oder falsches Kennwort <br>
	 *         -5, Benutzer gesperrt <br>
	 *         >0, SQL-Fehler <br>
	 */
	public QueryResult authentifiziereBenutzer(String name, String passwort,
			String rfid, String benutzerbarcode) {
		synchronized (this.sync) {

			if (rfid.equals("")) {
				rfid = null;
			}

			if (benutzerbarcode.equals("")) {
				benutzerbarcode = null;
			}

			if (name.equals("") || passwort.equals("")) {
				name = null;
				passwort = null;
			}

			return Authentifizierung.authenticateUser(name, passwort, rfid,
					benutzerbarcode, this.conn);
		}
	}

	/**
	 * Gibt die drei meistgekauften Getraenke des übergebenen Benutzers zurück <br>
	 * <br>
	 * 
	 * @param name
	 *            Der Benutzer <br>
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse <br>
	 *         Spalten: "getraenk.Name", "Anzahl" ErrorCodes: <br>
	 *         0, wenn ok <br>
	 *         -2, wenn Benutzername null <br>
	 *         -4, wenn Benutzer nicht existiert oder (noch) keine Getränke
	 *         gekauft hat <br>
	 *         >0, wenn SQL-Fehler <br>
	 */
	public QueryResult dreiMeistGekauftenGetraenke(String name) {
		synchronized (this.sync) {
			return Internals.threeMostWantedDrinks(name, this.conn);
		}
	}

	public QueryResult createUser(String executor, String name,
			String passwort, String rfid, String benutzerbarcode,
			Float kontostand, boolean gesperrt) {

		synchronized (QueryServer.sync) {

			return Benutzerverwaltung.createUser(executor, name, passwort,
					rfid, benutzerbarcode, kontostand, gesperrt, this.conn);

		}
	}

	/**
	 * Methode zum Erstellen eines Benutzers <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert <br>
	 * @param name
	 *            Benutzername - erforderlich <br>
	 * @param passwort
	 *            PasswortHASH - erforderlich <br>
	 * @param rfid
	 *            RFID-Code - null, wenn nicht angegeben <br>
	 * @param benutzerbarcode
	 *            Barcode - null, wenn nicht angegegben <br>
	 * @param kontostand
	 *            Initialer Kontostand - 0.00 wenn nicht angegegben <br>
	 * @param gesperrt
	 *            - false, wenn nicht angegeben <br>
	 * @return 0, wenn ok <br>
	 *         -2, wenn Benutzername oder Passwort null sind <br>
	 *         >0 SQL-Fehler <br>
	 * 
	 */
	public int createUser(String executor, String name, String passwort,
			String rfid, String benutzerbarcode, Float kontostand,
			Boolean gesperrt) {

		if (rfid.equals("")) {
			rfid = null;
		}

		if (benutzerbarcode.equals("")) {
			benutzerbarcode = null;
		}

		if (kontostand.equals("")) {
			kontostand = null;
		}

		// Überprüfe, ob erforderliche Werte nicht null sind
		if (name == null)
			return -2;
		if (passwort == null)
			return -2;

		synchronized (this.sync) {
			return Benutzerverwaltung.createUser(name, passwort, rfid,
					benutzerbarcode, kontostand, gesperrt, this.conn);
		}
	}

	/**
	 * Methode zur Festlegung des Checkoutverhaltens <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param verhalten
	 *            Welches Verhalten soll eingestellt werden? "Sofort" oder
	 *            "Countdown" <br>
	 * @return 0, wenn ok<br>
	 *         -2, wenn Executor oder Verhalten gleich null sind<br>
	 *         -3, wenn das Verhalten nicht "Sofort" oder "Countdown" ist<br>
	 *         -4, wenn Checkoutverhalten nicht in der Datenbank <br>
	 *         >0 SQL-Fehler<br>
	 */
	public int setzeCheckoutverhalten(String executor, String verhalten) {

		// Falsche Werte abfangen
		if (verhalten == null)
			return -2;
		if (!(verhalten.equals("Sofort") || verhalten.equals("Countdown")))
			return -3;

		synchronized (this.sync) {
			return Systemkonfiguration
					.setCheckoutBehavior(verhalten, this.conn);
		}

	}

	/**
	 * Methode zur Festlegung der Laenge des Countdowns<br>
	 * <br>
	 * 
	 * @param executor
	 *            wird inoriert <br>
	 * @param countdown
	 *            Länge des Countdowns (in Sekunden), mindestens 5 <br>
	 * @param conn
	 *            Connection Objekt für die Verbindung zur Datenbank <br>
	 * @return 0, wenn ok <br>
	 *         -3, wenn Countdown < 5 ist <br>
	 *         -4, wenn Countdownlänge nicht in der Datenbank <br>
	 *         >0, SQL-Fehler<br>
	 */
	public int setzeCountdownlaenge(String executor, int countdown) {

		// Falsche Werte abfangen
		if (countdown < 5)
			return -3;

		synchronized (this.sync) {
			return Systemkonfiguration.setCountdownLength(countdown, this.conn);
		}
	}

	@Deprecated
	/**
	 * Methode zum Auffüllen des Bestandes<br>
	 * Achtung: Der alte Bestand wird ueberschrieben!<br>
	 * <br>
	 * @param executor wird ignoriert <br>
	 * @param getraenkebarcode Barcode des Getraenks, dessen Bestand aufgefuellt werden soll - erforderlich <br>
	 * @param menge Neue Anzahl der Einheiten des Aufzufuellenden Getraenkes - dieser Wert wird NICHT addiert, sondern ERSETZT den alten Wert <br>
	 * @return 	 0, wenn ok <br>
	 * 			-2, getraenk null ist <br>
	 * 			-3, wenn menge < 0 ist <br>
	 * 	 * 		-4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 * 		  	>0, SQL-Fehler <br>
	 */
	public int fuelleBestandAuf(String executor, String getraenkebarcode,
			int menge) {

		// Falsche Werte abfangen
		if (getraenkebarcode == null)
			return -2;
		if (menge < 0)
			return -3;

		synchronized (this.sync) {
			return Getraenkeverwaltung.replenishStock(getraenkebarcode, menge,
					this.conn);
		}
	}

	@Deprecated
	/**
	 * Methode zum Abfragen des aktuellen Bestandes <br>
	 * <br>
	 * @param executor wird ignoriert<br>
	 * @param getraenkebarcode Barcode des Getraenks, dessen Bestand abgefragt werden soll. Wird null übergeben, werden alle Getraenkebestaende alphabetisch ausgegeben <br>
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse <br>
	 * 	 		Spalten: <b>
	 * 			"Getraenkebarcode", "Name", "Menge" <b>
	 * 			ErrorCodes: <br>
	 * 			 0, wenn ok <br>
	 * -4, wenn das entsprechende Getraenk nicht existiert oder aktuell gar kein Bestand in der Datenbank eingetragen ist <br>
	 * 		 	>0,	SQL-Fehler <br>
	 */
	public QueryResult frageBestandAb(String executor, String getraenkebarcode) {
		synchronized (this.sync) {
			return Getraenkeverwaltung.getStock(getraenkebarcode, this.conn);
		}
	}

	/*
	 * Methode zur Abfrage der gesamten Umsätze - entweder Benutzerbezogen oder
	 * global <br>
	 * <br>
	 * 
	 * @param name
	 *            Der Benutzername, dessen Konto abgefragt werden soll - bei
	 *            null werden alle Umsaetze angezeigth<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Getraenkebarcode", "Name" (des Getraenks), "Umsatz", "Datum",
	 *         "Benutzername" (optional, ohne ist das Array um die Spalte
	 *         "Benutzername" erweitert)<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok <br>
	 *         -4, wenn Benutzer nicht existiert oder keine getaetigten Umsaetze
	 *         vorliegen<br>
	 *         >0, SQL-Fehler<br>
	 *
	public QueryResult frageUmsaetzeAb(String benutzername, Date start, Date end) {

		synchronized (this.sync) {
			return Statistiken.getSales(benutzername, start, end, this.conn);
		}
	}*/

	@Deprecated
	/**
	 * Methode zur Abfrage der Benutzerrechte <br>
	 * <br>
	 * @param Der Benutzername, dessen Rechte ausgegeben werden sollen<br>
	 * @return 	Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			"Recht.Nummer", "Beschreibung"<br>
	 * 			ErrorCodes:<br>
	 * 			 0, wenn ok<br>
	 * 			-2, wenn Benutzername null<br>
	 * 			-4, wenn Benutzername nicht vorhandne ist oder Benutzer keine Rechte hat<br>
	 * 		 	>0,	SQL-Fehler<br>
	 */
	public QueryResult benutzerrechteAbfragen(String benutzername) {
		if (benutzername == null)
			return (new QueryResult(-2));
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserRights(benutzername, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der Benutzerrechte <br>
	 * <br>
	 * 
	 * @param Der
	 *            Benutzername, dessen Rechte ausgegeben werden sollen<br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Recht.Nummer", "Beschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn Benutzername null<br>
	 *         -4, wenn Benutzername nicht vorhandne ist oder Benutzer keine
	 *         Rechte hat<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult frageBenutzerrechteAb(String benutzername) {
		if (benutzername == null)
			return (new QueryResult(-2));
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserRights(benutzername, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage aller Benutzer in der Datenbank <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse <br>
	 *         Spalten: <br>
	 *         "Benutzername" <br>
	 *         ErrorCodes: <br>
	 *         0, wenn ok <br>
	 *         -4, wenn Datenbank keine Benutzer enthält <br>
	 *         >0, SQL-Fehler <br>
	 */
	public QueryResult benutzerListeLiefern() {
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserList(this.conn);
		}
	}

	/**
	 * Methode zum Anpassen des Bestandes<br>
	 * Achtung: Der alte Bestand und die alte Verlustwerte werden
	 * ueberschrieben!<br>
	 * <br>
	 * 
	 * @param getraenkebarcode
	 *            Barcode des Getraenks, dessen Bestand aufgefuellt werden soll
	 *            - erforderlich <br>
	 * @param menge
	 *            Neue Anzahl der Einheiten des Aufzufuellenden Getraenkes -
	 *            dieser Wert wird NICHT addiert, sondern ERSETZT den alten Wert <br>
	 * @param verluste
	 *            Neue Anzahl der Einheiten des Getraenkes, die verlorgen
	 *            gegangen sind - dieser Wert wird NICHT addiert, sondern
	 *            ERSETZT den alten Wert. Bei -1 werden keine Verluste
	 *            ueberschrieben.
	 * @return 0, wenn ok <br>
	 *         -2, getraenk null ist <br>
	 *         -3, wenn menge < 0 oder verluste < -1 sind <br>
	 *         * -4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 *         >0, SQL-Fehler <br>
	 */
	public int passeBestandAn(String getraenkebarcode, int menge, int verluste) {

		// Falsche Werte abfangen
		if (getraenkebarcode == null)
			return -2;
		if (menge < 0)
			return -3;
		if (verluste < -1)
			return -3;

		synchronized (this.sync) {
			return Getraenkeverwaltung.changeStock(getraenkebarcode, menge,
					verluste, this.conn);
		}
	}

	/**
	 * Methode zur Anpassung eines Getraenkes<br>
	 * Achtung: Die alten Werte werden ueberschreiben!<br>
	 * <br>
	 * 
	 * @param getraenkebarcode
	 *            Barcode des Getraenks, dessen Bestand aufgefuellt werden soll
	 *            - erforderlich <br>
	 * @param neuerName
	 *            Neuer Name des Getraenkes. Setze null, wenn dieser nicht
	 *            geandert werden soll <br>
	 * @param neueLiter
	 *            Neue Flaschengroeße des Getraenks. Setze -1, wenn diese nicht
	 *            geandert werden soll <br>
	 * @param neuerPreis
	 *            Neuer preis des Getraenks. Setze -1.0f, wenn dieser nicht
	 *            geandert werden soll <br>
	 * @return 0, wenn ok <br>
	 *         -2, getraenkebarcode null ist oder wenn neueLiter oder neuerPreis
	 *         < -1 / -1.0f sind <br>
	 *         -3, getraenkebarcode null ist und wenn neueLiter und neuerPreis <
	 *         -1 / -1.0f sind <br>
	 *         * -4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 *         >0, SQL-Fehler <br>
	 */
	public int passeGetraenkAn(String getraenkebarcode, String neuerName,
			float neueLiter, float neuerPreis) {
		if (neuerName.equals("")) {
			neuerName = null;
		}

		// Null Werte abfangen / Falsche Werte abfangen
		if (getraenkebarcode == null)
			return -2;
		if (neueLiter < -1)
			return -2;
		if (neuerPreis < -1.0f)
			return -2;

		synchronized (this.sync) {
			return Getraenkeverwaltung.changeDrink(getraenkebarcode, neuerName,
					neueLiter, neuerPreis, this.conn);
		}
	}

	/**
	 * Methode zum Löschen eines Getraenkess<br>
	 * <br>
	 * 
	 * @param getraenkebarcode
	 *            Barcode des Getraenks, welches geloescht werden soll -
	 *            erforderlich <br>
	 * @return 0, wenn ok <br>
	 *         -2, getraenkebarcode null ist <br>
	 *         * -4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 *         >0, SQL-Fehler <br>
	 */
	public int loescheGetraenk(String getraenkebarcode) {
		// Null Werte abfangen / Falsche Werte abfangen
		if (getraenkebarcode == null)
			return -2;

		synchronized (this.sync) {
			return Getraenkeverwaltung.deleteDrink(getraenkebarcode, this.conn);
		}
	}

	/**
	 * Methode zum Hinzufügen eines Getraenkes <br>
	 * <br>
	 * 
	 * @param getraenkebarcode
	 *            Barcode des getraenks - erforderlich<br>
	 * @param name
	 *            Name des Getraenks - erforderlich <br>
	 * @param preis
	 *            Preis des Getraenks - erforderlich <br>
	 * @param liter
	 *            Flaschengroeße des getraenks - erforderlich <br>
	 * @param menge
	 *            Anzahl der Flaschen. 0 wenn Wert < 1 <br>
	 * @return 0, wenn ok <br>
	 *         -2, wenn getraenkebarcode oder name null sind oder preis oder
	 *         liter < 0.0f sind <br>
	 *         >0 SQL-Fehler <br>
	 */
	public int fuegeGetraenkHinzu(String getraenkebarcode, String name,
			float preis, float liter, int menge) {

		// Falsche Werte abfangen / veraendern
		if (name == null)
			return -2;
		if (getraenkebarcode == null)
			return -2;
		if (preis < 0.00f)
			return -2;
		if (liter < 0.0f)
			return -2;
		if (menge < 0)
			menge = 0;

		synchronized (this.sync) {
			return Getraenkeverwaltung.addDrink(getraenkebarcode, name, preis,
					liter, menge, this.conn);
		}
	}

	/**
	 * Methode zur Festlegung des globalen Kontolimits<br>
	 * <br>
	 * 
	 * @param limit
	 *            Neuer Wert des globalen Kontolimits - muss <= 0.0f sein <br>
	 * @return 0, wenn ok <br>
	 *         -2, wenn limit > 0 <br>
	 *         -4, wenn Countdownlänge nicht in der Datenbank <br>
	 *         >0, SQL-Fehler<br>
	 */
	public int setzeGlobalesKontolimit(float limit) {
		// Falsche Werte abfangen
		if (limit > 0.0f)
			return -2;
		synchronized (this.sync) {
			return Systemkonfiguration.setGlobalLimit(limit, this.conn);
		}
	}

	/**
	 * Methode zum Aufladen eines Benutzerkontos<br>
	 * <br>
	 * 
	 * @param name
	 *            Das Benutzerkonto - erforderlich
	 * @param betrag
	 *            Neuer Kontostand des Benutzers. Der alte Stand wird
	 *            ueberschrieben! - erforderlich <br>
	 * @return 0, wenn ok <br>
	 *         -2, wenn name == null <br>
	 *         >0, SQL-Fehler<br>
	 */
	public int ladeKontoAuf(String name, Float betrag) {
		// Falsche Werte abfangen
		if (name == null)
			return -2;
		synchronized (this.sync) {
			return Benutzerverwaltung.loadAccount(name, betrag, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage des Kontostandes
	 * 
	 * @param name
	 *            Der Benutzername, dessen Konto abgefragt werden soll -
	 *            erforderlich
	 * 
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 *         Spalten: "Kontostand" ErrorCodes: 0, wenn ok <br>
	 *         -2, wenn name == null <br>
	 *         -4, wenn Benutzer nicht existiert >0, SQL-Fehler<br>
	 */
	public QueryResult frageKontostandAb(String benutzername) {

		if (benutzername == null)
			return (new QueryResult(-2));
		synchronized (this.sync) {
			return Benutzerverwaltung.getAccountValue(benutzername, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der gesamten Umsätze - Benutzerbezogen und/oder
	 * Getraenkebezogen oder global <br>
	 * <br>
	 * 
	 * @param name
	 *            Der Benutzername, dessen Konto abgefragt werden soll - bei
	 *            null werden alle Umsaetze angezeigth<br>
	 * @param start
	 *            Startdatum, ab wann Umsaetze angezeigt werden
	 * @param start
	 *            Endatumdatum, ab wann Umsaetze angezeigt werden
	 * @param
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Getraenkebarcode", "Name" (des Getraenks), "Umsatz", "Datum",
	 *         "Benutzername" (optional, wenn Benutzername = null ist das Array
	 *         um die Spalte "Benutzername" erweitert)<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok <br>
	 *         -4, wenn Benutzer nicht existiert oder keine getaetigten Umsaetze
	 *         vorliegen<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult frageUmsaetzeAb(String benutzername,
			XMLGregorianCalendar start, XMLGregorianCalendar end,
			String getraenkebarcode) {
		if (benutzername.equals("")) {
			benutzername = null;
		}

		if (getraenkebarcode.equals("")) {
			getraenkebarcode = null;
		}

		if (start.equals("")) {
			start = null;
		}

		if (end.equals("")) {
			end = null;
		}

		synchronized (this.sync) {
			return Statistiken.getSales(benutzername, start
					.toGregorianCalendar().getTime(), end.toGregorianCalendar()
					.getTime(), getraenkebarcode, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der Benutzerrollen <br>
	 * <br>
	 * 
	 * @param Der
	 *            Benutzername, dessen Rechte ausgegeben werden sollen<br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Gruppenbeschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn Benutzername null<br>
	 *         -4, wenn Benutzername nicht vorhandne ist oder Benutzer in keiner
	 *         Gruppe ist<br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult frageGruppenAb(String benutzername) {
		if (benutzername == null)
			return (new QueryResult(-2));
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserGroups(benutzername, this.conn);
		}
	}

	/**
	 * Methode zur Durchfuehrung des Checkouts <br>
	 * <br>
	 * 
	 * @param benutzername
	 *            Benutzername des Benutzers, der den Checkout durchführt -
	 *            erforderlich<br>
	 * @param getraenkebarcode
	 *            Barcode des Getraenks, welches ausgecheckt wird - erforderlich<br>
	 * @param umsatz
	 *            Betrag, mit dem der Checkout gebucht wird. Erforderlich, muss
	 *            größer als 0.0f sein<br>
	 * @return ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn Benutzername oder getraenkebarcode = null oder umsatz <=
	 *         0.0f<br>
	 *         -4, wenn Benutzername oder getraenkebarcode nicht in der DB
	 *         vorhanden sind<br>
	 *         -5, wenn der Bestand durch den Checkout einen negativen Wert
	 *         annehmen wuerde<br>
	 *         -6, wenn der Benutzer nicht genug Guthaben (inkl. globals Limit)
	 *         ueberschreiten wuerde<br>
	 *         >0, SQL Fehler<br>
	 */
	public int fuehreCheckoutDurch(String benutzername,
			String getraenkebarcode, float umsatz) {

		// Überprüfe, ob erforderliche Werte nicht null oder < 0.0f sind
		if (benutzername == null)
			return -2;
		if (getraenkebarcode == null)
			return -2;
		if (umsatz <= 0.0f)
			return -2;

		synchronized (this.sync) {
			float kontostand = 0.0f;
			float limit = 0.0f;
			int bestand = 0;
			int errorCode = 0;

			// Frage aktuellen Kontostand ab
			QueryResult result = Benutzerverwaltung.getAccountValue(
					benutzername, this.conn);
			if (result.getErrorCode() == 0) {
				Iterator<String[]> iterator = result.getQueryResult()
						.iterator();
				while (iterator.hasNext()) {
					kontostand = Float.valueOf(iterator.next()[0]);
				}
			} else
				return result.getErrorCode();

			// Berechne neuen Kontostand
			kontostand = kontostand - umsatz;

			// Limit ausrechnen
			result = Systemkonfiguration.getLimit(this.conn);
			if (result.getErrorCode() == 0) {
				Iterator<String[]> iterator = result.getQueryResult()
						.iterator();
				while (iterator.hasNext()) {
					limit = kontostand - Float.valueOf(iterator.next()[0]);
				}
			} else
				return result.getErrorCode();

			if (limit < 0)
				return -6;

			// Frage aktuellen Getraenkebestand ab
			result = Getraenkeverwaltung.getStock(getraenkebarcode, this.conn);
			if (result.getErrorCode() == 0) {
				Iterator<String[]> iterator = result.getQueryResult()
						.iterator();
				while (iterator.hasNext()) {
					bestand = Integer.valueOf(iterator.next()[2]);
				}
			} else
				return result.getErrorCode();

			// Berechne neuen Bestand
			bestand = bestand - 1;
			if (bestand < 0)
				return -5;

			// Mehrere Veränderungen an der Datenbank, die im Zusammenhang
			// stehen: AutoCommit aus!
			try {
				this.conn.setAutoCommit(false);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				// e.printStackTrace();
				return e.getErrorCode();
			}

			// Konto belasten
			errorCode = Benutzerverwaltung.loadAccount(benutzername,
					kontostand, this.conn);
			if (errorCode != 0) {
				try {
					this.conn.rollback();
					this.conn.setAutoCommit(true);
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					// e.printStackTrace();
					return e.getErrorCode();
				}
				return errorCode;
			}

			// Getraenkebestand belasten
			errorCode = Getraenkeverwaltung.changeStock(getraenkebarcode,
					bestand, -1, this.conn);
			if (errorCode != 0) {
				try {
					this.conn.rollback();
					this.conn.setAutoCommit(true);
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					// e.printStackTrace();
					return e.getErrorCode();
				}
				return errorCode;
			}

			// Checkout durchfüren
			errorCode = Internals.doCheckout(benutzername, getraenkebarcode,
					umsatz, this.conn);
			if (errorCode != 0) {
				try {
					this.conn.rollback();
					this.conn.setAutoCommit(true);
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					// e.printStackTrace();
					return e.getErrorCode();
				}
				return errorCode;
			}

			// Alles ok, Änderungen durchführen!
			try {
				this.conn.commit();
				this.conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				// e.printStackTrace();
				try {
					this.conn.rollback();
				} catch (SQLException e1) {
					System.err.println(e1.getMessage());
					// e.printStackTrace();
					return e.getErrorCode();
				}
				return e.getErrorCode();
			}

			return errorCode;
		}
	}

	/**
	 * Methode zum Abfragen des globalen Kontolimits <br>
	 * <br>
	 * 
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse <br>
	 *         Spalten: <b> "Limit" (minimaler Kontostand als String, z.B.
	 *         "-30") <b> ErrorCodes: <br>
	 *         0, wenn ok <br>
	 *         -4, wenn kein Limit konfiguriert ist >0, SQL-Fehler <br>
	 */
	public QueryResult frageKontolimitAb() {
		synchronized (this.sync) {
			return Systemkonfiguration.getLimit(this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der Getraenketabelle - entweder ein bestimmtes
	 * Getraenk, oder ALLE Getraenke <br>
	 * <br>
	 * 
	 * @param getraenkebarcode
	 *            Das getraenk, welches abgefragt werden soll - bei null werden
	 *            alle Getraenke angezeigth<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         Getraenkebarcode, Name, Preis, Menge, Liter, Verluste<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok <br>
	 *         -4, wenn das Getraenk nicht existiert oder gar kein getraenk in
	 *         der Datenbank vorhanden ist <br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult frageGetraenkAb(String getraenkebarcode) {
		if (getraenkebarcode.equals("")) {
			getraenkebarcode = null;
		}

		synchronized (this.sync) {
			return Getraenkeverwaltung.getDrinks(getraenkebarcode, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage des Countdowns/Timeouts aus der Datenbank
	 * 
	 * @return ein QueryResult, sie Beschreibung der gleichnamenigen Klasse
	 *         Spalten: "Attributwert" - ein positiver Int Wert für den
	 *         Countdown als String ErrorCodes: 0, wenn ok -4, wenn keine Zeilen
	 *         zurueckgegeben ("Timeout" nicht in der Systemkonfiguration
	 *         vorhanden) >0, SQL Fehler
	 */
	public QueryResult frageCountdownAb() {

		synchronized (this.sync) {
			return Systemkonfiguration.getCountdown(this.conn);
		}
	}

	/**
	 * Methode zur Abfrage des Logoutverhaltens aus der Datenbank
	 * 
	 * @return ein QueryResult, sie Beschreibung der gleichnamenigen Klasse
	 *         Spalten: "Attributwert" - ein String, der entweder "Sofort" oder
	 *         "Countdown" zurückliefert, je nach eingestelltem Logoutverhalten
	 *         ErrorCodes: 0, wenn ok -4, wenn keine Zeilen zurueckgegeben
	 *         ("Logoutverhalten" nicht in der Systemkonfiguration vorhanden)
	 *         >0, SQL Fehler
	 */
	public QueryResult frageLogoutverhaltenAb() {

		synchronized (this.sync) {
			return Systemkonfiguration.getLogoutBehavior(this.conn);
		}
	}

	/**
	 * Methode zum Aendern des Passwortes eines uebergebenen Benutzers
	 * 
	 * @param benutzername
	 *            Benutzernames des Benutzers, dessen passwort geaendert werden
	 *            soll
	 * @param passwort
	 *            Neues Passwort (HASH!)
	 * @return ein QueryResult, siehe beschreibung der gleichnamigen Klasse
	 *         ErrorCodes: 0, wenn ok -2, wenn Benutzer oder Passwort null sind
	 *         -4, wenn Benutzer nicht existiert >0, SQL Fehler
	 */
	public QueryResult aenderePasswort(String benutzername, String passwort) {

		// Fange nullwerte ab
		if (benutzername == null)
			return new QueryResult(-2);
		if (passwort == null)
			return new QueryResult(-2);

		synchronized (this.sync) {
			return Benutzerverwaltung.changePassword(benutzername, passwort,
					this.conn);
		}
	}

	/**
	 * Methode zum Ändern der Beschreibung einer Gruppe
	 * 
	 * @param gruppenname
	 *            Name der zu ändernden Gruppe - erforderlich
	 * @param beschreibung
	 *            Neue beschreibung der zu ändernden Gruppe - erforderlich
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 *         ErrorCodes: 0, wenn ok -1, wenn gruppenname gleich
	 *         "Administratoren" -2, wenn gruppenname oder beschreibung null -4,
	 *         wenn gruppenname nicht in der DB existiert >0, SQL Fehler
	 */
	public QueryResult aendereRollenbeschreibung(String gruppenname,
			String beschreibung) {

		if ((gruppenname == null) || (beschreibung == null))
			return new QueryResult(-2);
		if (gruppenname.equals("Administratoren"))
			return new QueryResult(-1);

		synchronized (this.sync) {
			return Rollenverwaltung.changeRoleDescription(gruppenname,
					beschreibung, this.conn);
		}
	}

	/**
	 * Methode zur Abfrage der Getraenketabelle - entweder ein bestimmtes
	 * Getraenk, oder ALLE Getraenke <br>
	 * <br>
	 * 
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         Getraenkebarcode<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok <br>
	 *         >0, SQL-Fehler<br>
	 */
	public QueryResult frageGetraenkBarcodeListe() {
		synchronized (this.sync) {
			return Getraenkeverwaltung.getDrinksList(this.conn);
		}
	}

	/**
	 * Methode zur Abfrage aller Benutzer in der Datenbank <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse <br>
	 *         Spalten: <br>
	 *         "Benutzername" <br>
	 *         ErrorCodes: <br>
	 *         0, wenn ok <br>
	 *         -4, wenn Datenbank keine Benutzer enthält <br>
	 *         >0, SQL-Fehler <br>
	 */
	public QueryResult benutzerRFIDListeLiefern() {
		synchronized (this.sync) {
			return Benutzerverwaltung.getUserRFIDList(this.conn);
		}
	}

	public QueryResult updateUser(String executor, String user, String rfid,
			String benutzerbarcode, boolean gesperrt) throws SQLException {

		synchronized (QueryServer.sync) {

			return Benutzerverwaltung.updateUser(executor, user, rfid,
					benutzerbarcode, gesperrt, this.conn);

		}

	}
}