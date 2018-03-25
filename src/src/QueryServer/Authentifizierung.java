package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Authentifizierung {

	/**
	 * Methode zur Authentifizierung eines Benutzers gegen die Datenbank
	 * 
	 * Es ist ENTWEDER 	Benutzername & Passwort
	 * 			ODER	Barcode
	 * 			ODER 	RFID
	 * 
	 * erforderlich. Sind mehrere Methoden möglich, wird nur gegen die erste mögliche Variante in dieser Liste authentifiziert
	 * 
	 * @param name Benutzername
	 * @param passwort Passworthash des Benututzers
	 * @param rfid RFID Code des Benutzers
	 * @param benutzerbarcode Barcode des Benutzers
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse	
	 * 			ErrorCodes: 			
	 * 			 0, wenn ok
	 * 			-2, wenn (name && rfid && benutzerbarcode) == null oder wenn (passwort, rfid, benutzerbarcode) == null oder alle parameter == null
	 * 			-4, Unbekannter Benutzername oder falsches Kennwort
	 * 			-5, Benutzername gesperrt
	 * 		 	>0,	SQL-Fehler
	 */	
	protected static QueryResult authenticateUser (String name, String passwort, String rfid, String benutzerbarcode, Connection conn) {
		
		// Authentifizierung mit Passwort
		if (name != null && passwort != null) {				
			return authenticatePassword(name, passwort, conn);				
		}
		
		//Authentifizierzung mit RFID
		if (rfid!=null) {
			return authenticateRFID(rfid, conn);
		}
		
		//Authentifizierung mit Benutzerbarcode
		if (benutzerbarcode!=null) {
			return authenticateUserBarcode(benutzerbarcode, conn);
		}
		
	//Wenn keine Methode zutrifft, dann kann aufgrund von null-Parametern keine Authentifizierung durchgeführt werden
	return (new QueryResult(-2));
	}
	
	/**
	 * Methode zur Authentifizierung des Benutzerbarcodes gegen die Datenbank
	 * 
	 * @param benutzerbarcode Barcode des Benutzers
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse	
	 * 			ErrorCodes: 			
	 * 			 0, wenn ok
	 * 			-4, Unbekannter Benutzerbarcode
	 * 			-5, Benutzer gesperrt
	 * 		 	>0,	SQL-Fehler
	 */
	private static QueryResult authenticateUserBarcode(String benutzerbarcode, Connection conn) {
		PreparedStatement prest = null;
		ResultSet rs = null;
		LinkedList <String []> rows;
		
		// SQL Code
		try {
			String pst =	"SELECT Benutzername, Gesperrt "+
							"FROM Benutzer "+
							"WHERE benutzerbarcode=? ";
			
			// Code in ein Prepared Statement packen
			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden durch nachträgliches Einfügen der Variablen
			prest.setString(1, benutzerbarcode);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		//Query ausführen
		try {
			rs = prest.executeQuery();
			//prest.closeOnCompletion();			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		// Ergebnis in eine LinkedList speichern
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				// Benutzer gesperrt
				if (rs.getBoolean("Gesperrt")) return (new QueryResult(-5));
				String [] columns = new String[1];
				columns[0] = rs.getString("Benutzername");
				rows.add(columns);
			}
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
	 * Methode zur Authentifizierung der RFID des Benutzers gegen die Datenbank
	 * 
	 * @param rfid Die RFID des Benutzers
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse	
	 * 			ErrorCodes: 			
	 * 			 0, wenn ok
	 * 			-4, Unbekannte RFID
	 * 			-5, Benutzer gesperrt
	 * 		 	>0,	SQL-Fehler
	 */
	private static QueryResult authenticateRFID(String rfid, Connection conn) {
		PreparedStatement prest = null;
		LinkedList <String []> rows;
		ResultSet rs = null;
		
		// SQL Code
		try {
			String pst =	"SELECT Benutzername, Gesperrt "+
							"FROM Benutzer "+
							"WHERE rfid=? ";
			//Code in ein PreparedStatement packen
			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden durch seperates hinzufügen der Variablen
			prest.setString(1, rfid);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		//Query ausführen
		try {
			rs = prest.executeQuery();
			//prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		// Query in eine LinkedList übertragen
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				// Benutzer gesperrt
				if (rs.getBoolean("Gesperrt")) return (new QueryResult(-5));
				String [] columns = new String[1];
				columns[0] = rs.getString("Benutzername");
				rows.add(columns);
			}
			if (rs.last()) {
				System.out.println("Kommando erfolgreich ausgeführt: "+prest.toString());
				System.out.println("Es wurden "+rs.getRow()+" Zeilen zurueckgegeben");
			} else return (new QueryResult(-4));
			return (new QueryResult(rows));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
	}

	/**
	 * Methode zur Authentifizierung des Benutzernamens + Passworts gegen die Datenbank
	 * 
	 * @param name Der Benutzernamen
	 * @param passwort Der Passwort-Hash
	 * @param conn Connection Objekt für die Verbindung zur Datenbank
	 * @return 	Ein QueryResult, siehe Beschreibung der gleichnamigen Klasse	
	 * 			ErrorCodes: 			
	 * 			 0, wenn ok
	 * 			-4, Unbekannter Benutzername oder falsches Passwort
	 * 			-5, Benutzer gesperrt
	 * 		 	>0,	SQL-Fehler
	 */
	private static QueryResult authenticatePassword(String name, String passwort, Connection conn) {
		PreparedStatement prest = null;
		LinkedList <String []> rows;
		ResultSet rs = null;
		
		try {
			//SQL Code
			String pst =	"SELECT Benutzername, Gesperrt "+
							"FROM Benutzer "+
							"WHERE Benutzername=? "+
							"AND PW_HASH=?";
			
			//Code in einer PreparedStatement schreiben
			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden durch späteres hinzufügen der Variablen
			prest.setString(1, name);
			prest.setString(2, passwort);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		//Query ausführen
		try {
			rs = prest.executeQuery();
			// TODO produziert Fehler bei Anmeldung
			// prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		// Query in eine LinkedList packen
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				// Benutzer gesperrt
				if (rs.getBoolean("Gesperrt")) return (new QueryResult(-5));
				String [] columns = new String[1];
				columns[0] = rs.getString("Benutzername");
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


}
