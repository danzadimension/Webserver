<%@ page import="QueryServer.*, java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);

		if (rights.contains("R19")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/newDrink.jsp', { method: 'post' }); return false;">Getraenk
	hinzufuegen</a>
|
<%
	}
		if (rights.contains("R21")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/editDrink.jsp', { method: 'post' }); return false;">Getraenk
	bearbeiten</a>
|
<%
	}
		if (rights.contains("R20")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/deleteDrink.jsp', { method: 'post' }); return false;">Getraenk
	loeschen</a>
|
<%
	}
		if (rights.contains("R09")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/trackingInventory.jsp', { method: 'post' }); return false;">Bestand
	abfragen</a>
|
<%
	}
		if (rights.contains("R10")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/replenishInventory.jsp', { method: 'post' }); return false;">Bestand
	auffuellen</a>
|
<%
	}
		if (rights.contains("R11")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/lossAccount.jsp', { method: 'post' }); return false;">Bestand
	anpassen</a>
|
<%
	}
		if (rights.contains("R12")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'drinks/statistikDrink.jsp', { method: 'post' }); return false;">Getränkestatistik</a>
<%
	}
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>