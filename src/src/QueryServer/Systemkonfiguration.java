package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Systemkonfiguration {

	/**
	 * @param verhalten
	 *            Welches Verhalten soll eingestellt werden? "Sofort" oder
	 *            "Countdown"
	 * @param conn
	 *            Connection Objekt für die Verbindung zur Datenbank
	 * @return 0, wenn ok -1, wenn Rechte des Executors nicht ausreichen -2,
	 *         wenn Executor oder Verhalten gleich null sind -3, wenn das
	 *         Verhalten nicht "Sofort" oder "Countdown" ist else, SQL-Fehler
	 */
	protected static int setCheckoutBehavior(String verhalten, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Falsche Werte abfangen
		if (verhalten == null)
			return -2;
		if (!(verhalten.equals("Sofort") || verhalten.equals("Countdown")))
			return -3;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Systemkonfiguration " + "SET Attributwert=? "
					+ "WHERE Attributname='Logoutverhalten'";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setString(1, verhalten);

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
	 * @param countdown
	 *            Länge des Countdowns (in Sekunden), mindestens 5
	 * @param conn
	 *            Connection Objekt für die Verbindung zur Datenbank
	 * @return 0, wenn ok -3, wenn Countdown < 5 ist else, SQL-Fehler
	 */
	protected static int setCountdownLength(int countdown, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Falsche Werte abfangen
		if (countdown < 5)
			return -3;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Systemkonfiguration " + "SET Attributwert=? "
					+ "WHERE Attributname='Timeout'";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setInt(1, countdown);

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
	 * Methode zur Festlegung des globalen Kontolimits<br>
	 * <br>
	 * 
	 * @param limit
	 *            Neuer Wert des globalen Kontolimits - muss <= 0.0f sein <br>
	 * @return 0, wenn ok <br>
	 *         -2, wenn limit > 0 -4, wenn Countdownlänge nicht in der Datenbank <br>
	 *         >0, SQL-Fehler<br>
	 */
	protected static int setGlobalLimit(float limit, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;

		// Falsche Werte abfangen
		if (limit > 0.0f)
			return -2;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Systemkonfiguration " + "SET Attributwert=? "
					+ "WHERE Attributname='Limit'";
			prest = conn.prepareStatement(sql);

			// SQL Injections vermeiden
			prest.setFloat(1, limit);

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

	protected static QueryResult getLimit(Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		LinkedList<String[]> rows = null;

		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "Select Attributwert " + "FROM Systemkonfiguration "
					+ "WHERE Attributname='Limit' ";

			prest = conn.prepareStatement(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			// e.printStackTrace();
			return new QueryResult(e.getErrorCode());
		}

		// PreparedStatement ausführen
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

		// RückgabeTyp bauen
		try {
			rows = new LinkedList<String[]>();
			while (rs.next()) {
				String[] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}

			// Anzahl der Zeilen zurückgeben
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "
						+ prest.toString());
				System.out.println("Es wurden " + rs.getRow()
						+ " Zeilen zurueckgegeben");
				// Wenn das Result 0 Zeilen hat
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
	 * Methode zur Abfrage des Countdowns/Timeouts aus der Datenbank
	 * 
	 * @return ein QueryResult, sie Beschreibung der gleichnamenigen Klasse
	 *         Spalten: "Attributwert" - ein positiver Int Wert für den
	 *         Countdown als String ErrorCodes: 0, wenn ok -4, wenn keine Zeilen
	 *         zurueckgegeben ("Timeout" nicht in der Systemkonfiguration
	 *         vorhanden) >0, SQL Fehler
	 */
	protected static QueryResult getCountdown(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Attributwert " + "FROM Systemkonfiguration "
					+ "WHERE Attributname='Timeout' ";
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
	 * Methode zur Abfrage des Logoutverhaltens aus der Datenbank
	 * 
	 * @return ein QueryResult, sie Beschreibung der gleichnamenigen Klasse
	 *         Spalten: "Attributwert" - ein String, der entweder "Sofort" oder
	 *         "Countdown" zurückliefert, je nach eingestelltem Logoutverhalten
	 *         ErrorCodes: 0, wenn ok -4, wenn keine Zeilen zurueckgegeben
	 *         ("Logoutverhalten" nicht in der Systemkonfiguration vorhanden)
	 *         >0, SQL Fehler
	 */
	protected static QueryResult getLogoutBehavior(Connection conn) {
		LinkedList<String[]> rows;
		PreparedStatement prest = null;

		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = "SELECT Attributwert " + "FROM Systemkonfiguration "
					+ "WHERE Attributname='Logoutverhalten' ";
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
}
