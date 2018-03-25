<%@ page import="QueryServer.*, java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);

		if (rights.contains("R03")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'roles/rolesReg.jsp', { method: 'post' })">Rolle
	registrieren</a>
|
<%
	}

		if (rights.contains("R04") || rights.contains("R05")
				|| rights.contains("Rechtezuweisung")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'roles/rolesSearch.jsp', { method: 'post' })">Rolle
	suchen</a>
|
<%
	}

		if (rights.contains("R04") || rights.contains("R05")
				|| rights.contains("Rechtezuweisung")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'roles/rolesOverview.jsp', { method: 'post' })">Rollen
	bearbeiten</a>
<%
	}
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>