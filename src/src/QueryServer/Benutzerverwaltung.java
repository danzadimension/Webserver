package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Benutzerverwaltung {

	/**
	 * Diese Methode liefert die Rechte des uebergebenen Benutzers zurück.
	 * 
	 * @param Der
	 *            Benutzername, dessen Rechte ausgegeben werden sollen
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse
	 *         Spalten: "Recht.Nummer", "Beschreibung" ErrorCodes: 0, wenn ok
	 *         -2, wenn Benutzername null >0, SQL-Fehler
	 */
	protected static QueryResult getUserRights(String benutzername,
			Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// Überprüfe, ob erforderliche Werte nicht null sind
		if (benutzername == null)
			return (new QueryResult(-2));

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT DISTINCT Recht.Nummer, Beschreibung "
					+ "FROM Benutzer, ist_in, Gruppe, bekommt_zugewiesen, Recht "
					+ "WHERE Benutzer.Benutzername=ist_in.Benutzername "
					+ "AND ist_in.Gruppenname=Gruppe.Gruppenname "
					+ "AND Gruppe.Gruppenname=bekommt_zugewiesen.Gruppenname "
					+ "AND bekommt_zugewiesen.Nummer=Recht.Nummer "
					+ "AND Benutzer.Benutzername=?";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, benutzername);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[2];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	protected static QueryResult createUser(String executor, String name,
			String passwort, String rfid, String benutzerbarcode,
			Float kontostand, Boolean gesperrt, Connection conn) {

		PreparedStatement prest = null;

		if (kontostand == null)
			kontostand = 0.00f;
		kontostand = Math.round(kontostand * 100) / 100.0f;

		int banned = 0;

		if (gesperrt)
			banned = 1;

		try {

			prest = conn
					.prepareStatement("INSERT INTO Benutzer (Benutzername, PW_Hash, RFID, Benutzerbarcode, Kontostand, Gesperrt) VALUES ('"
							+ name
							+ "', MD5('"
							+ passwort
							+ "'), '"
							+ rfid
							+ "', '"
							+ benutzerbarcode
							+ "', '"
							+ kontostand
							+ "', '" + banned + "')");

		} catch (SQLException e) {

			System.err.println(e.getMessage());
			return new QueryResult(e.getErrorCode());

		}

		try {

			prest.execute();

		} catch (SQLException e) {

			System.err.println(e.getMessage());
			return new QueryResult(e.getErrorCode());

		}

		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString());
		return new QueryResult(0);

	}

	/**
	 * Methode zum Erstellen eines neuen Benutzers *
	 * 
	 * @param name
	 *            Benutzername - erforderlich
	 * @param passwort
	 *            PasswortHASH - erforderlich
	 * @param rfid
	 *            RFID-Code - null, wenn nicht angegeben
	 * @param benutzerbarcode
	 *            Barcode - null, wenn nicht angegegben
	 * @param kontostand
	 *            Initialer Kontostand - 0.00 wenn nicht angegegben
	 * @param gesperrt
	 *            - false, wenn nicht angegeben
	 * @param conn
	 *            Connection Objekt für die Verbindung zur Datenbank
	 * @return SQL-Errorcodes bei Fehlern, sonst 0
	 */
	protected static int createUser(String name, String passwort, String rfid,
			String benutzerbarcode, Float kontostand, Boolean gesperrt,
			Connection conn) {
		PreparedStatement prest = null;
		String sql = null;

		// Kontostand normaliesieren
		if (kontostand == null)
			kontostand = 0.00f;
		kontostand = Math.round(kontostand * 100) / 100.0f;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "INSERT INTO Benutzer (Benutzername, PW_Hash, RFID, Benutzerbarcode, Kontostand, Gesperrt)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, name);
			prest.setString(2, passwort);
			prest.setString(3, rfid);
			prest.setString(4, benutzerbarcode);
			prest.setFloat(5, kontostand);
			prest.setBoolean(6, gesperrt);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return e.getErrorCode();
		}

		// PreparedStatement ausführen
		try {
			prest.execute();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return e.getErrorCode();
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString());
		return 0;
	}

	/**
	 * Methode zur Abfrage aller Benutzer in der Datenbank
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse
	 *         Spalten: "Benutzername" ErrorCodes: 0, wenn ok -4, wenn Datenbank
	 *         keine Benutzer enthält >0, SQL-Fehler
	 */
	protected static QueryResult getUserList(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Benutzername " + "FROM Benutzer ";
			prest = conn.prepareStatement(pst);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	/**
	 * Methode zur Abfrage aller Benutzer in der Datenbank
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse
	 *         Spalten: RFID ErrorCodes: 0, wenn ok -4, wenn Datenbank keine
	 *         Benutzer enthält >0, SQL-Fehler
	 */
	protected static QueryResult getUserRFIDList(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT RFID " + "FROM Benutzer ";
			prest = conn.prepareStatement(pst);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
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
	protected static int loadAccount(String name, Float betrag, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Falsche Werte abfangen
		if (name == null)
			return -2;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Benutzer " + "SET Kontostand=? "
					+ "WHERE Benutzername=? ";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setFloat(1, betrag);
			prest.setString(2, name);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return e.getErrorCode();
		}

		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return e.getErrorCode();
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString() + ". Es wurden " + rows
				+ " Zeilen aktualisiert.");
		if (rows == 0)
			return -4;
		return 0;
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
	protected static QueryResult getAccountValue(String benutzername,
			Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// Überprüfe, ob erforderliche Werte nicht null sind
		if (benutzername == null)
			return (new QueryResult(-2));

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Kontostand " + "FROM Benutzer "
					+ "WHERE Benutzer.Benutzername=? ";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, benutzername);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
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
	protected static QueryResult getUserGroups(String benutzername,
			Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// Überprüfe, ob erforderliche Werte nicht null sind
		if (benutzername == null)
			return (new QueryResult(-2));

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT DISTINCT Gruppe.Gruppenname, Gruppenbeschreibung "
					+ "FROM Benutzer, ist_in, Gruppe "
					+ "WHERE Benutzer.Benutzername=ist_in.Benutzername "
					+ "AND ist_in.Gruppenname=Gruppe.Gruppenname "
					+ "AND Benutzer.Benutzername=?";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, benutzername);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[2];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
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
	protected static QueryResult assignRoleToUser(String executor, String user,
			String role, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;

		// Null Werte abfangen
		if (role == null)
			return new QueryResult(-2);
		if (role == user)
			return new QueryResult(-2);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "INSERT INTO ist_in (Benutzername, Gruppenname) "
					+ "VALUES (?, ?) ";

			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, user);
			prest.setString(2, role);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			prest.execute();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString());
		return new QueryResult(0);
	}

	/**
	 * Methode um einen Benutzer zu löschen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param user
	 *            Benutzer, der geloescht werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	protected static QueryResult deleteUser(String executor, String user,
			Connection conn) {
		PreparedStatement prest1 = null;
		PreparedStatement prest2 = null;
		int rows = 0;

		String sql = null;

		// Kontostand normaliesieren
		if (user == null)
			return new QueryResult(-2);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "DELETE FROM Ist_in " + " WHERE Benutzername=? ";
			prest1 = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest1.setString(1, user);

			sql = "DELETE FROM Benutzer " + "WHERE Benutzername=? ";
			prest2 = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest2.setString(1, user);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			conn.setAutoCommit(false);
			prest1.execute();
			rows = prest2.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			// TODO
			// prest1.closeOnCompletion();
			// prest2.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				conn.setAutoCommit(true);
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest1.toString());
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest2.toString());
		if (rows == 0)
			return new QueryResult(-4);
		return new QueryResult(0);
	}

	@Deprecated
	/**
	 * Methode zur Abfrage aller Gruppennamen <br>
	 * <br>
	 * @return 	Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			"Gruppenname"<br>
	 * 			ErrorCodes:<br>
	 * 			 0, wenn ok<br>
	 * 			-4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte zugewiesen bekommen hat<br>
	 * 		 	>0,	SQL-Fehler<br>
	 */
	protected static QueryResult getRoles(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Gruppenname FROM Gruppe";
			prest = conn.prepareStatement(pst);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	/**
	 * Methode zur Abfrage aller Benutzernamen <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Benutzername" "RFID", "Benutzerbarcode", "Gesperrt" <br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn keine Benutzer in der Datenbank <br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult getUsersOverview(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Benutzername, RFID, Benutzerbarcode, Gesperrt FROM Benutzer";
			prest = conn.prepareStatement(pst);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO macht Fehler
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] cols = new String[4];
				cols[0] = rs.getString(1);
				cols[1] = rs.getString(2);
				cols[2] = rs.getString(3);
				cols[3] = rs.getString(4);
				rows.add(cols);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	protected static LinkedList<String> getUsersRights(String executor,
			String user, QueryServer server, Connection conn) {

		LinkedList<String> usersRights = new LinkedList<String>();
		LinkedList<String[]> roles = getUsersRoles(executor, user, conn)
				.getQueryResult();

		for (String[] role : roles) {

			LinkedList<String[]> rights = server.getRightsByRole(executor,
					role[1]).getQueryResult();

			for (String[] right : rights) {

				if (!usersRights.contains(right[1])) {

					usersRights.addLast(right[1]);

				}

			}

		}

		return usersRights;

	}

	protected static QueryResult getUsersRoles(String executor, String user,
			Connection conn) {

		LinkedList<String[]> rows = new LinkedList<String[]>();
		PreparedStatement prest = null;
		ResultSet rs = null;

		try {

			prest = conn
					.prepareStatement("SELECT Benutzername, Gruppenname FROM Ist_In WHERE Benutzername ='"
							+ user + "'");
			rs = prest.executeQuery();
			System.out.println("Kommando erfolgreich ausgefuehrt: "
					+ prest.toString());

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return new QueryResult(e.getErrorCode());

		}

		try {

			while (rs.next()) {

				String[] cols = new String[2];
				cols[0] = rs.getString(1);
				cols[1] = rs.getString(2);
				rows.add(cols);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return new QueryResult(e.getErrorCode());

		}

		return new QueryResult(rows);
	}

	/**
	 * Methode zur Abfrage von von den zugewiesenen Gruppen eines Benutzers <br>
	 * <br>
	 * 
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
	protected static QueryResult getUserRoles(String user, Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// Falsche Werte abfangen
		if (user == null)
			return new QueryResult(-2);

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Benutzername, Gruppenname " + "FROM Ist_In "
					+ "WHERE Benutzername=? ";
			prest = conn.prepareStatement(pst);

			// SQL Injektions vermeiden
			prest.setString(1, user);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[2];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	/**
	 * Methode zur Löschung der Zuweisung eines Rolle zu einem Benutzer <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn ??? -2, wenn Benutzer versucht, die Rollen des
	 *         Administrators zu entziehen -3, wenn role oder right oder
	 *         executor null -4, wenn Rolle oder Recht nicht verknüpft gewesen
	 *         sind<br>
	 **/
	protected static QueryResult removeRoleFromUser(String user, String role,
			Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Null Werte bafangen
		if (role == null)
			return new QueryResult(-3);
		if (user == null)
			return new QueryResult(-3);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "DELETE FROM Ist_In " + "WHERE Benutzername=? "
					+ "AND Gruppenname=? ";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, user);
			prest.setString(2, role);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString() + ". Es wurden " + rows
				+ " Zeilen aktualisiert.");
		if (rows == 0)
			return new QueryResult(-4);
		return new QueryResult(0);
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
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Kasse
	 *         Errorcodes: 0, wenn ok -2, wenn Benutzer versucht, den
	 *         Administrator zu ändern -3, wenn die eroderlichen Werte null sind
	 *         -4, wenn der Benutzer nicht in der Datenbank existiert >0, SQL
	 *         Fehler
	 */
	protected static QueryResult updateUser(String user, String rfid,
			String benutzerbarcode, boolean gesperrt, Connection conn,
			String newUsername) {
		// Nullwerte abfangen
		if (user == null)
			return new QueryResult(-3);
		if (rfid == null)
			return new QueryResult(-3);
		if (benutzerbarcode == null)
			return new QueryResult(-3);

		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Benutzer "
					+ "SET RFID=? , Benutzerbarcode=? , Gesperrt=? , Benutzername=? "
					+ "WHERE Benutzername=? ";
			;
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, rfid);
			prest.setString(2, benutzerbarcode);
			prest.setBoolean(3, gesperrt);
			prest.setString(4, newUsername);
			prest.setString(5, user);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString() + ". Es wurden " + rows
				+ " Zeilen aktualisiert.");
		if (rows == 0)
			return new QueryResult(-4);
		return new QueryResult(0);
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
	 *         -4, wenn user nicht gefunden<br>
	 */
	protected static QueryResult searchUser(String user, Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Benutzername, RFID, Benutzerbarcode, Gesperrt "
					+ "FROM Benutzer "
					+ "WHERE Benutzername=? "
					+ "OR RFID=? "
					+ "OR Benutzerbarcode=? ";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, user);
			prest.setString(2, user);
			prest.setString(3, user);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[4];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				columns[2] = rs.getString(3);
				columns[3] = rs.getString(4);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
			} else
				return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
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
	protected static QueryResult changePassword(String benutzername,
			String passwort, Connection conn) {
		// Nullwerte abfangen
		if (benutzername == null)
			return new QueryResult(-3);
		if (passwort == null)
			return new QueryResult(-3);

		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Benutzer " + "SET PW_HASH=? "
					+ "WHERE Benutzername=? ";
			;
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, passwort);
			prest.setString(2, benutzername);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest.toString() + ". Es wurden " + rows
				+ " Zeilen aktualisiert.");
		if (rows == 0)
			return new QueryResult(-4);
		return new QueryResult(0);
	}

	protected static QueryResult updateUser(String executor, String user,
			String rfid, String code, boolean banned, Connection conn)
			throws SQLException {

		if (user.equals("Administrator")) {

			System.out
					.println("Fehler: Benutzer versucht Administrator zu veraendern.");
			return new QueryResult(-2);

		}

		PreparedStatement prest = null;
		int b = 0;

		if (banned) {

			b = 1;

		}

		try {

			prest = conn.prepareStatement("UPDATE Benutzer SET RFID='" + rfid
					+ "', Benutzerbarcode='" + code + "', Gesperrt='" + b
					+ "' WHERE Benutzername ='" + user + "'");
			prest.execute();
			System.out.println("Kommando erfolgreich ausgefuehrt: "
					+ prest.toString());

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return new QueryResult(e.getErrorCode());

		}

		return new QueryResult(0);

	}

}
