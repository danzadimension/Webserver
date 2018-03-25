package src.QueryServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Statistiken {

	
	/**
	 * Methode zur Abfrage der gesamten Umsätze - entweder Benutzerbezogen oder global <br>
	 * <br>
	 * @param name Der Benutzername, dessen Konto abgefragt werden soll - bei null werden alle Umsaetze angezeigth<br>
	 * @return ein QueryResult, siehe Beschreibung der gleichnamigen Klasse<br>
	 * 			Spalten:<br>
	 * 			"Getraenkebarcode", "Name" (des Getraenks), "Umsatz", "Datum", "Benutzername" (optional, ohne ist das Array um die Spalte "Benutzername" erweitert)<br>
	 * 			ErrorCodes:<br>
	 * 			0, wenn ok <br>
	 * 			-4, wenn Benutzer nicht existiert oder keine getaetigten Umsaetze vorliegen<br>
	 * 		 	>0, SQL-Fehler<br>
	 */
	protected static QueryResult getSales(String benutzername, Date start, Date end, String getraenkebarcode, Connection conn) {
		LinkedList <String []> rows;
		PreparedStatement prest = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Boolean benutzerspezifisch = false;
		Boolean getraenkespezifisch = false;
		
		if (benutzername!=null) 
			benutzerspezifisch = true;	
		
		if (getraenkebarcode!=null)
			getraenkespezifisch = true;
				
		// SQL Abfrage in ein PreparedStatement packen
		try {
			String pst = 	"SELECT Getraenk.Getraenkebarcode, Getraenk.Name, Checkout.Umsatz, Datum, Checkout.Benutzername ";
			
			pst = pst+		"FROM Checkout, Getraenk " +
							"WHERE Checkout.Getraenkebarcode=getraenk.Getraenkebarcode ";
			
			if (benutzerspezifisch)
				pst= pst+ 	"AND Checkout.Benutzername=? ";
			if (getraenkespezifisch)
				pst=pst+ "AND Checkout.Getraenkebarcode=? ";
			
			if (start!=null)
				pst = pst+"AND Datum>=? ";
			if (start!=null)
				pst = pst+"AND Datum <=? ";
			prest = conn.prepareStatement(pst);
			
			// SQL Injections vermeiden
			for (int i = 1; i <=4; i++) {
				if (benutzername!=null) {
					prest.setString(i, benutzername);
					benutzername=null;
					continue;
				}
				
				if (getraenkebarcode!=null) {
					prest.setString(i, getraenkebarcode);
					getraenkebarcode=null;
					continue;
				}
				if (start!=null) {
					prest.setDate(i, new java.sql.Date(start.getTime()));
					start = null;
					continue;
				}
				if (end!=null) {
					prest.setDate(i, new java.sql.Date(end.getTime()));
					end = null;
					continue;
				}
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		ResultSet rs = null;
		try {
			rs = prest.executeQuery();
			//prest.closeOnCompletion();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
			return (new QueryResult(e.getErrorCode()));
		}
		
		try {
			rows = new LinkedList <String []>();
			while (rs.next()) {
				String [] columns = new String[5];
				columns[0] = rs.getString(1);
				columns[1] = rs.getString(2);
				columns[2] = String.valueOf(rs.getFloat(3));
				columns[3] = df.format(rs.getTimestamp(4));
				columns[4] = rs.getString(5);
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
