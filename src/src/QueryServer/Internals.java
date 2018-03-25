package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Internals {
	
	/**
	 * @param name Benutzername, dessen Favourites ausgegeben werden sollen
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 * 			Spalten:
	 * 			"getraenk.Name", "Anzahl"
	 * 		   	ErrorCode:
	 * 			 0, wenn ok
	 * 			-2, wenn Benutzername null
	 * 			-4, wenn der Benutzername nicht existiert oder noch keine Getraenke gekauft hat
	 *          >0, wenn SQL-Fehler
	 */
	protected static QueryResult threeMostWantedDrinks(String name, Connection conn) {
		PreparedStatement prest = null;
		LinkedList <String []> rows;
		
		// Überprüfe, ob erforderliche Werte nicht null sind
		if (name==null) return (new QueryResult(-2));
				
		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst =	"SELECT (Getraenk.Name), COUNT(Getraenk.Name) AS Anzahl "+
							"FROM Checkout, Getraenk "+
							"WHERE CHECKOUT.Getraenkebarcode = Getraenk.Getraenkebarcode "+
							"AND CHECKOUT.Benutzername=? "+
							"GROUP BY Getraenk.Name "+
							"LIMIT 3";

			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden
			prest.setString(1, name);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				String [] columns = new String[1];
				columns[0] = rs.getString(1);
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
				System.out.println("Es wurden "+rs.getRow()+" Zeilen zurueckgegeben");
			} else 	return (new QueryResult(-4));			
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}
	
	/**
	 * Methode zur Durchfuehrung des Checkouts <br>
	 * <br>
	 * @param benutzername Benutzername des Benutzers, der den Checkout durchführt - erforderlich<br>
	 * @param getraenkebarcode Barcode des Getraenks, welches ausgecheckt wird - erforderlich<br>
	 * @param umsatz Betrag, mit dem der Checkout gebucht wird. Erforderlich, muss größer als 0.0f sein<br>
	 * @param conn Connection Objetk zur Verbindung mit der Datenbank
	 * @return ErrorCodes:<br>
	 * 			 0, wenn ok<br>
	 * 			-2, wenn Benutzername oder getraenkebarcode = null oder umsatz <= 0.0f<br>
	 * 			>0, SQL Fehler<br>
	 */
	protected static int doCheckout(String benutzername, String getraenkebarcode, float umsatz, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		
		// Falsche Werte abfangen
		if (benutzername==null) return -2;
		if (getraenkebarcode==null) return -2;
		if (umsatz <= 0.0f) return -2;
		
		
		
		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"INSERT INTO Checkout (Benutzername, Getraenkebarcode, Umsatz, Datum)" +
					" VALUES (?, ?, ?, ?)";
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			prest.setString(1, benutzername);
			prest.setString(2, getraenkebarcode);
			prest.setFloat(3, umsatz);
			
				// Aktuelles Datum + Uhrzeit formatieren
				java.util.Date javaDate = new java.util.Date(); 
				java.sql.Timestamp sqlDate = new java.sql.Timestamp(javaDate.getTime());
				
			prest.setTimestamp(4, sqlDate);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// PreparedStatement ausführen
		try {
			prest.execute();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
		return 0;
	}
}
