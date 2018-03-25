<%@ page import="src.QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	QueryServer server = new QueryServer();
	String name = session.getAttribute("benutzerName").toString();

	if (session.getAttribute("benutzerName") != null) {

		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);

		if (rights.contains("R08")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'account/kontostand.jsp', { method: 'post' }); return false;">Kontostand
	abfragen</a> |
<%
	}

		if (rights.contains("R16")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'account/kontocharge.jsp', { method: 'post' }); return false;">Konto
	aufladen</a> |
<%
	}

		if (rights.contains("R18")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'account/kontoLimit.jsp', { method: 'post' }); return false;">Kontolimit
	festlegen</a> |
<%
	}

		if (rights.contains("R17")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'account/kontosales.jsp', { method: 'post' }); return false;">Kontoumsaetze
	abfragen</a>
<%
	}
	} else {
%>
<div>Zugriff verweigert</div>
<%
	}
%>