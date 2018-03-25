<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
		QueryServer server = new QueryServer();
		String name = session.getAttribute("benutzerName").toString();

		for (int i = 0; i <= server.benutzerrechteAbfragen(name)
				.getQueryResult().size() - 1; i++) {

			if (server.benutzerrechteAbfragen(name).getQueryResult()
					.get(i)[0].toString().equals("R13")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'sys/newCountdown.jsp', { method: 'post' }); return false;">Countdown
	einstellen</a> |
<%
	}
			if (server.benutzerrechteAbfragen(name).getQueryResult()
					.get(i)[0].toString().equals("R14")) {
%>
<a href=#
	onclick="new Ajax.Updater('container', 'sys/verhalten.jsp', { method: 'post' }); return false;">Verhalten
	einstellen</a>
<%
	}
		}
	} else {
%>
<div>Zugriff verweigert</div>
<%
	}
%>