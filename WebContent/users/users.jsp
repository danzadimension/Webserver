<%@ page import="QueryServer.*, java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);

		if (rights.contains("R01")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'users/usersReg.jsp', { method: 'post' });">Benutzer
	registrieren</a>
|
<%
	}

		if (rights.contains("R02") || rights.contains("R06")
				|| rights.contains("R15")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'users/usersSearch.jsp', { method: 'post' });">Benutzer
	suchen</a>
|
<%
	}

		if (rights.contains("R02") || rights.contains("R06")
				|| rights.contains("R15")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'users/usersOverview.jsp', { method: 'post' });">Benutzer
	bearbeiten</a>
|
<%
	}
%>
<a href=#
	onclick="new Ajax.Updater('container', 'users/usersPassword.jsp', { method: 'post' });">Passwort
	aendern</a>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>