package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Rollenverwaltung {

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
	protected static QueryResult assignRightToRole(String executor,
			String role, String right, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;

		// Null Werte abfangen
		if (role == null)
			return new QueryResult(-2);
		if (role == right)
			return new QueryResult(-2);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "INSERT INTO Bekommt_zugewiesen (Gruppenname, Nummer) "
					+ "VALUES (?, ?) ";

			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, role);
			prest.setString(2, right);

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
	 *         -2, wenn role ist<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	protected static QueryResult createRole(String executor, String role,
			Connection conn) {
		PreparedStatement prest = null;
		String sql = null;

		// Kontostand normaliesieren
		if (role == null)
			return new QueryResult(-2);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "INSERT INTO GRUPPE (Gruppenname, Gruppenbeschreibung)"
					+ " VALUES (?, ?)";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, role);
			prest.setString(2, null);

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
	 * Methode um eine Rolle zu löschen <br>
	 * <br>
	 * 
	 * @param executor
	 *            wird ignoriert<br>
	 * @param role
	 *            Benutzerrolle, die geloescht werden soll<br>
	 * @return ein QueryResult, siehe gleichnamige Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         >0, SQL Fehler<br>
	 * <br>
	 */
	protected static QueryResult deleteRole(String role, Connection conn) {

		PreparedStatement prest1 = null;
		PreparedStatement prest2 = null;
		PreparedStatement prest3 = null;
		int rows = 0;

		String sql = null;

		// Kontostand normaliesieren
		if (role == null)
			return new QueryResult(-2);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "DELETE FROM Bekommt_zugewiesen " + "WHERE Gruppenname=?";
			prest1 = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest1.setString(1, role);

			sql = "DELETE FROM Ist_in " + "WHERE Gruppenname=?";
			prest2 = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest2.setString(1, role);

			sql = "DELETE FROM Gruppe " + "WHERE Gruppenname=?";
			prest3 = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest3.setString(1, role);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
		try {
			conn.setAutoCommit(false);
			rows = prest1.executeUpdate();
			rows = prest2.executeUpdate();
			rows = prest3.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			// TODO
			// prest1.closeOnCompletion();
			// prest2.closeOnCompletion();
			// prest3.closeOnCompletion();
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
		System.out.println("Kommando erfolgreich ausgeführt: "
				+ prest3.toString());
		if (rows == 0)
			return new QueryResult(-4);
		return new QueryResult(0);
	}

	/**
	 * Methode zur Abfrage der Benutzerrollen <br>
	 * <br>
	 * 
	 * @param role
	 *            Die Rolle, deren Rechte angezeigt werden soll<br>
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Gruppenbeschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -2, wenn role null<br>
	 *         -4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte
	 *         zugewiesen bekommen hat<br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult getRightsByRole(String role, Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Gruppenname, Nummer "
					+ "FROM Bekommt_zugewiesen " + "WHERE Gruppenname=? ";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, role);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}

		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO macht ebenfalls Fehler
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
	 * Methode zur Abfrage der Rollen <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname, "Gruppenbeschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte
	 *         zugewiesen bekommen hat<br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult getRoles(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Gruppenname, Gruppenbeschreibung FROM Gruppe";
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
	 * Methode zur Abfrage von Gruppennamen und deren zugewiesenen Rechten <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Gruppenname", "Nummer"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn kein Benutzer einer Gruppe zugewiesen ist<br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult getRolesRights(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Gruppenname, Nummer FROM Bekommt_zugewiesen";
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
	 * Methode zur Abfrage der Rechte <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         Spalten:<br>
	 *         "Nummer", "Beschreibung"<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -4, wenn Rolle nicht vorhandne ist oder Rolle in keine Rechte
	 *         zugewiesen bekommen hat<br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult getRights(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Nummer, Beschreibung FROM Recht";
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
	 * Methode zur Löschung der Zuweisung eines Rechtes zu einer Rolle <br>
	 * <br>
	 * 
	 * @return Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse<br>
	 *         ErrorCodes:<br>
	 *         0, wenn ok<br>
	 *         -1, wenn Benutzer versucht, die Rolle in der er sich selbst
	 *         befindet zu löschen -2, wenn Benutzer versucht, den Administrator
	 *         zu löschen -3, wenn role oder right null -4, wenn Rolle oder
	 *         Recht nicht verknüpft gewesen sind<br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static QueryResult removeRightFromRole(String role, String right,
			Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Null Werte bafangen
		if (role == null)
			return new QueryResult(-3);
		if (right == null)
			return new QueryResult(-3);

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "DELETE FROM Bekommt_zugewiesen " + "WHERE Gruppenname=? "
					+ "AND Nummer=? ";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, role);
			prest.setString(2, right);

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
	 * Methode, um nach einer bestimmten Rolle zu suchen
	 * 
	 * @param role
	 *            die Rolle, nach der gesucht werden soll
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 *         Spalten: "Gruppenname", "Gruppenbeschreibung" ErrorCodes: 0, wenn
	 *         ok -3, wenn rolle null -4, wenn rolle nicht gefunden
	 */
	protected static QueryResult searchRole(String role, Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Gruppenname, Gruppenbeschreibung "
					+ "FROM Gruppe " + "WHERE Gruppenname=? ";
			prest = conn.prepareStatement(pst);

			// SQL Injections vermeiden
			prest.setString(1, role);

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
	 * Methode zum Ändern der Beschreibung einer Gruppe
	 * 
	 * @param gruppenname
	 *            Name der zu ändernden Gruppe - erforderlich
	 * @param beschreibung
	 *            Neue beschreibung der zu ändernden Gruppe - erforderlich
	 * @return Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 *         ErrorCodes: 0, wenn ok -2, wenn gruppenname oder beschreibung
	 *         null -4, wenn gruppenname nicht in der DB existiert >0, SQL
	 *         Fehler
	 */
	protected static QueryResult changeRoleDescription(String gruppenname,
			String beschreibung, Connection conn) {
		// Nullwerte abfangen
		if (gruppenname == null)
			return new QueryResult(-3);
		if (beschreibung == null)
			return new QueryResult(-3);

		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Gruppe " + "SET Gruppenbeschreibung=? "
					+ "WHERE Gruppenname=? ";
			;
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, beschreibung);
			prest.setString(2, gruppenname);

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
}
