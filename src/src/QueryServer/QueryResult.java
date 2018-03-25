package src.QueryServer;

import java.io.Serializable;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Ein QueryResult enthält die Rückgabe einer SQL-Select Abfrage in einer allgemeinen Form:
 * 
 * errorCode:
 * 		der SQL-spezifische ErrorCode (positiver Wert) oder
 *		ein von uns generierter ErrorCode(negativer Wert)
 *		0, wenn alles gut gegangen ist
 *
 * queryResult:
 * 		Eine LinkedList <String []>:
 * 		Jedes Listenelement der LinkedList steht für eine zurückgegebene Zeile
 * 		Das Listenelement der LinkedList String [] enthält die Spalteneinträge der Zeile
 * 
 * Beispiel: 	Gib mir alle Benutzernamen und deren Kontostand aus
 * 				Man bekommt eine LinkedList mit n Elementen zurück,
 * 				wobei n für die Anzahl der Benutzer steht.
 * 				Jedes dieser n-Elemente enthält einen String[2]
 * 				In diesem an Position 0 der Benutzername
 * 				und an Position 1 das Passwort des Benutzers.
 * 
 * Achtung:		Erst _MUSS_ der ErrorCode ausgewertet werden.
 * 				NUR wenn der ErrorCode = 0 ist, ist das
 * 				queryResult != null
 * 				
 * 				i.d.R. ist das Klassenobjekt der Klasse QueryResult
 * 				aber niemals null, d.h. man kann immer den ErrorCode abfragen
 * 				Es kann aber nicht schade, dass trotzdem vorher zu prüfen,
 * 				man weiß ja nie : - )
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class QueryResult implements Serializable {
	
	private int errorCode;
	private LinkedList <String []> queryResult;
	
	private QueryResult(){}
	
	public QueryResult (int errorCode) {
		this.errorCode = errorCode;
		queryResult = null;
	}
	
	public QueryResult(LinkedList <String []> queryResult) {
		this.queryResult=queryResult;
		errorCode = 0;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public LinkedList <String []> getQueryResult() {
		return this.queryResult;
	}
	
	

}
