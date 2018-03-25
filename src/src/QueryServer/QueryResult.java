package src.QueryServer;

import java.io.Serializable;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Ein QueryResult enth�lt die R�ckgabe einer SQL-Select Abfrage in einer allgemeinen Form:
 * 
 * errorCode:
 * 		der SQL-spezifische ErrorCode (positiver Wert) oder
 *		ein von uns generierter ErrorCode(negativer Wert)
 *		0, wenn alles gut gegangen ist
 *
 * queryResult:
 * 		Eine LinkedList <String []>:
 * 		Jedes Listenelement der LinkedList steht f�r eine zur�ckgegebene Zeile
 * 		Das Listenelement der LinkedList String [] enth�lt die Spalteneintr�ge der Zeile
 * 
 * Beispiel: 	Gib mir alle Benutzernamen und deren Kontostand aus
 * 				Man bekommt eine LinkedList mit n Elementen zur�ck,
 * 				wobei n f�r die Anzahl der Benutzer steht.
 * 				Jedes dieser n-Elemente enth�lt einen String[2]
 * 				In diesem an Position 0 der Benutzername
 * 				und an Position 1 das Passwort des Benutzers.
 * 
 * Achtung:		Erst _MUSS_ der ErrorCode ausgewertet werden.
 * 				NUR wenn der ErrorCode = 0 ist, ist das
 * 				queryResult != null
 * 				
 * 				i.d.R. ist das Klassenobjekt der Klasse QueryResult
 * 				aber niemals null, d.h. man kann immer den ErrorCode abfragen
 * 				Es kann aber nicht schade, dass trotzdem vorher zu pr�fen,
 * 				man wei� ja nie : - )
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
