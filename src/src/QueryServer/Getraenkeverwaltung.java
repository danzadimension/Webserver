package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Getraenkeverwaltung {
	
	@Deprecated
	/**
	 * Methode zur Aufflüllung des Bestandes eines Getraenks 
	 * 
	 * @param getraenk getraenk, dessen Bestand aufgefuellt werden soll - erforderlich 
	 * @param menge Neue Anzahl der Einheiten des Aufzufuellenden Getraenkes - dieser Wert wird NICHT addiert, sondern ERSETZT den alten Wert
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	 0, wenn ok (wenn kein Getraenk des uebergebenene Getraenkenamens vorhanden ist, ist dieser Wert ebenfalls 0!)
	 * 			-2, wenn getraenk null ist
	 * 			-3, wenn menge < 0 ist
	 * 			-4, wenn der Getraenkebarcode nicht vorhanden ist
	 * 		  else,	SQL-Fehler
	 */
	protected static int replenishStock(String getraenkebarcode, int menge, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;
		
		// Null Werte abfangen
		if (getraenkebarcode == null) return -2;
		
		// Falsche Werte abfangen
		if (menge < 0) return -3;
		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"UPDATE Getraenk "+
					"SET Menge=? "+
					"WHERE Getraenkebarcode=? ";
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			prest.setInt(1, menge);
			prest.setString(2, getraenkebarcode);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString()+". Es wurden "+rows+" Zeilen aktualisiert.");
		if (rows == 0) return -4;
		return 0;
	}

	@Deprecated
	/**
	 * Methode zur Abfrage des aktuellen Bestands
	 * 
	 * @param getraenkebarcode Barcode des Getraenks, dessen Bestand abgefragt werden soll. Wird null übergeben, werden alle Getraenkebestaende alphabetisch ausgegeben
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse
	 * 			Spalten:
	 * 			"Getraenkebarcode", "Name", "Menge"
	 * 			ErrorCodes:
	 * 			 0, wenn ok
	 * 			-4, wenn das entsprechende Getraenk nicht existiert oder aktuell gar kein Bestand in der Datenbank eingetragen ist <br>
	 * 		 	>0,	SQL-Fehler
	 */
	protected static QueryResult getStock(String getraenkebarcode, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		LinkedList<String[]> rows = null;
				
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"Select Getraenkebarcode, Name, Menge "+
					"FROM Getraenk ";
			
			if (getraenkebarcode != null) sql = sql+"WHERE getraenkebarcode=? ";
	
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			if (getraenkebarcode!= null) prest.setString(1, getraenkebarcode);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
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
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		// RückgabeTyp bauen
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				String [] columns = new String[3];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				columns[2] = rs.getString(3);
				rows.add(columns);
			}
			
			// Anzahl der Zeilen zurückgeben
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
				System.out.println("Es wurden "+rs.getRow()+" Zeilen zurueckgegeben");
				// Wenn das Result 0 Zeilen hat
			} else return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	
	/**
	 * Methode zum Anpassen des Bestandes<br>
	 * Achtung: Der alte Bestand und die alte Verlustwerte werden ueberschrieben!<br>
	 * <br>
	 * @param getraenkebarcode Barcode des Getraenks, dessen Bestand aufgefuellt werden soll - erforderlich <br>
	 * @param menge Neue Anzahl der Einheiten des Aufzufuellenden Getraenkes - dieser Wert wird NICHT addiert, sondern ERSETZT den alten Wert <br>
	 * @param verluste Neue Anzahl der Einheiten des Getraenkes, die verlorgen gegangen sind - dieser Wert wird NICHT addiert, sondern ERSETZT den alten Wert. Bei -1 werden keine Verluste ueberschrieben.
	 * @return 	 0, wenn ok <br>
	 * 			-2, getraenk null ist <br>
	 * 			-3, wenn menge < 0oder verluste < -1 sind <br>
	 * 	 * 		-4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 * 		  	>0, SQL-Fehler <br>
	 */
	public static int changeStock(String getraenkebarcode, int menge, int verluste, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;
		
		// Null Werte abfangen
		if (getraenkebarcode == null) return -2;
		
		// Falsche Werte abfangen
		if (menge < 0) return -3;
		if (verluste < -10) return -3;
		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"UPDATE Getraenk "+
					"SET Menge=? ";
	
			if (verluste > -1)
				sql = sql +	", Verluste=? ";
			sql = sql + "WHERE Getraenkebarcode=? ";
			
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			prest.setInt(1, menge);
			for (int i = 2; i <=3; i++) {
				if (verluste > -1) {
					prest.setInt(i, verluste);
					verluste = -1;
					continue;
				}
				prest.setString(i, getraenkebarcode);
				break;
			}
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString()+". Es wurden "+rows+" Zeilen aktualisiert.");
		if (rows == 0) return -4;
		return 0;
	}

	
	/**
	 * Methode zur Anpassung eines Getraenkes<br>
	 * Achtung: Die alten Werte werden ueberschreiben!<br>
	 * <br>
	 * @param getraenkebarcode Barcode des Getraenks, dessen Bestand aufgefuellt werden soll - erforderlich <br>
	 * @param neuerName Neuer Name des Getraenkes. Setze null, wenn dieser nicht geandert werden soll <br>
	 * @param neueLiter Neue Flaschengroeße des Getraenks. Setze -1, wenn diese nicht geandert werden soll <br>
	 * @param neuerPreis Neuer preis des Getraenks. Setze -1.0f, wenn dieser nicht geandert werden soll <br>
	 * @return 	 0, wenn ok <br>
	 * 			-2, getraenkebarcode null ist oder wenn neueLiter oder neuerPreis < -1 / -1.0f sind <br>
	 * 			-3, getraenkebarcode null ist und wenn neueLiter und neuerPreis < -1 / -1.0f sind <br>
	 * 	 * 		-4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 * 		  	>0, SQL-Fehler <br>
	 */
	public static int changeDrink(String getraenkebarcode, String neuerName, float neueLiter, float neuerPreis, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;
		
		// Null Werte abfangen / Falsche Werte abfangen
		if (getraenkebarcode==null) return -2;
		if (neueLiter < -1) return -2;
		if (neuerPreis < -1.0f) return -2;
		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql = "UPDATE Getraenk "+
				  "SET ";
			if (neuerName != null)
				sql = sql+"Name=? , ";
			if (neueLiter != -1.0f)
				sql = sql+"Liter=? , ";
			if (neuerPreis != -1.0f)
				sql = sql+"Preis=? , ";
			
			if (sql.endsWith("SET "))
				return -3;
			
			if (sql.endsWith(", "))
				sql = sql.substring(0, sql.length()-2);
			
			sql = sql+"WHERE Getraenkebarcode=? ";
			
			System.out.println(sql);

			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			for (int i = 1; i <= 4; i++) {
				if (neuerName != null) {
					prest.setString(i, neuerName);
					neuerName = null;
					continue;
				}
				if (neueLiter != -1) {
					prest.setFloat(i, neueLiter);
					neueLiter = -1;
					continue;
				}
				if (neuerPreis != -1.0f) {
					prest.setFloat(i, neuerPreis);
					neuerPreis = -1.0f;
					continue;
				}
				prest.setString(i, getraenkebarcode);
				break;
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString()+". Es wurden "+rows+" Zeilen aktualisiert.");
		if (rows == 0) return -4;
		return 0;
	}

	
	/**
	 * Methode zum Löschen eines Getraenkess<br>
	 * <br>
	 * @param getraenkebarcode Barcode des Getraenks, welches geloescht werden soll - erforderlich <br>
	 * @return 	 0, wenn ok <br>
	 * 			-2, getraenkebarcode null ist <br>
	 * 	 * 		-4, wenn der Getraenkebarcode nicht vorhanden ist <br>
	 * 		  	>0, SQL-Fehler <br>
	 */
	public static int deleteDrink(String getraenkebarcode, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		int rows = 0;
		
		// Null Werte abfangen
		if (getraenkebarcode == null) return -2;

		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"DELETE FROM Getraenk "+
					"WHERE Getraenkebarcode=? ";
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			prest.setString(1, getraenkebarcode);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// PreparedStatement ausführen
		try {
			rows = prest.executeUpdate();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return e.getErrorCode();
		}
		
		// Fertig
		System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString()+". Es wurden "+rows+" Zeilen aktualisiert.");
		if (rows == 0) return -4;
		return 0;
	}

	
	/**
	 * Methode zum Hinzufügen eines Getraenkes <br>
	 * <br>
	 * @param getraenkebarcode Barcode des getraenks - erforderlich<br>
	 * @param name Name des Getraenks - erforderlich <br>
	 * @param preis Preis des Getraenks - erforderlich <br>
	 * @param liter Flaschengroeße des getraenks - erforderlich <br>
	 * @param menge Anzahl der Flaschen. 0 wenn Wert < 1 <br>
	 * @return 	 0, wenn ok <br>
	 * 			-2, wenn getraenkebarcode oder name null sind oder preis oder liter < 0.0f sind <br>
	 * 		    >0	SQL-Fehler <br>
	 */
	public static int addDrink(String getraenkebarcode, String name, float preis, float liter, int menge, Connection conn) {
		PreparedStatement prest = null;
		String sql = null;
		
		// Falsche Werte abfangen / veraendern
		if (name==null) return -2;
		if (getraenkebarcode==null) return -2;
		if (preis < 0.00f) return -2;
		if (liter < 0.0f) return -2;
		if (menge < 0) menge = 0;
		
		// SQL Syntax in ein Prepared Statement packen
		try {
			sql =	"INSERT INTO Getraenk (Getraenkebarcode, Name, Preis, Menge, Liter)" +
					" VALUES (?, ?, ?, ?, ?)";
			prest = conn.prepareStatement(sql);
			
			// SQL Injections vermeiden
			prest.setString(1, getraenkebarcode);
			prest.setString(2, name);
			prest.setFloat(3, preis);
			prest.setFloat(4, menge);
			prest.setFloat(5, liter);
			
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

	
	/**
	 * Methode zur Abfrage der Getraenketabelle - entweder ein bestimmtes Getraenk, oder ALLE Getraenke <br>
	 * <br>
	 * @param getraenkebarcode Das getraenk, welches abgefragt werden soll - bei null werden alle Getraenke angezeigth<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			Getraenkebarcode, Name, Preis, Menge, Liter, Verluste<br>
	 * 			ErrorCodes:<br>
	 * 			0, wenn ok <br>
	 * 			-4, wenn das Getraenk nicht existiert oder gar kein getraenk in der Datenbank vorhanden ist <br>
	 * 		 	>0, SQL-Fehler<br>
	 */
	public static QueryResult getDrinks(String getraenkebarcode, Connection conn) {
		LinkedList <String []> rows;
		PreparedStatement prest = null;
		Boolean alleGetraenke = false;
		
		if (getraenkebarcode==null) 
			alleGetraenke = true;		
				
		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = 	"SELECT Getraenkebarcode, Name, Preis, Menge, Liter, Verluste "+ 
							"FROM Getraenk ";

			if (!alleGetraenke)
				pst = pst+"WHERE Getraenkebarcode=? ";

			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden
			if (!alleGetraenke)
				prest.setString(1, getraenkebarcode);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		
		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			// TODO
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {			
				String [] columns = new String[6];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				columns[2] = String.valueOf(rs.getFloat(3));
				columns[3] = String.valueOf(rs.getInt(4));
				columns[4] = String.valueOf(rs.getFloat(5));
				columns[5] = String.valueOf(rs.getInt(6));	
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
				System.out.println("Es wurden "+rs.getRow()+" Zeilen zurueckgegeben");
			} else 	return (new QueryResult(-4));			
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}
	
	/**
	 * Methode zur Abfrage von Barcode des Getränks in der Datenbank
	 * 
	 * @return 	Ein QueryRFesult, siehe Beschreibung der gleichnamigen Klasse
	 * 			Spalten:
	 * 			Getränkebarcode
	 * 			ErrorCodes:
	 * 			 0, wenn ok
	 *			-4, wenn Datenbank keine Benutzer enthält
	 * 		 	>0,	SQL-Fehler
	 */
	protected static QueryResult getDrinksList (Connection conn) {
		LinkedList <String []> rows;
		PreparedStatement prest = null;
		
		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = 	"SELECT Getraenkebarcode " +
							"FROM Getraenk ";
			prest = conn.prepareStatement(pst);
						
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
			//	System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
			//	System.out.println("Es wurden "+rs.getRow()+" Zeilen zurueckgegeben");
			} else 	return (new QueryResult(-4));			
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}
}
