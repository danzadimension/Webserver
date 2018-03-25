<%@ page import="QueryServer.*, java.util.*,  java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
		QueryServer server = new QueryServer();

		QueryResult result = new QueryResult(0);
		LinkedList<String[]> rows;
		String name = session.getAttribute("benutzerName").toString();

		boolean count = true;

		for (int i = 0; i <= server.benutzerrechteAbfragen(name)
				.getQueryResult().size() - 1; i++) {

			if (server.benutzerrechteAbfragen(name).getQueryResult()
					.get(i)[0].equals("R08")) {

				if (server.getRolle(name).getQueryResult().get(0)[0]
						.toString().equals("Tutor")
						|| server.getRolle(name).getQueryResult()
								.get(0)[0].toString()
								.equals("Benutzer")
						|| server.getRolle(name).getQueryResult()
								.get(0)[0].toString().equals(
								"Wissenschaftlicher Mitarbeiter")) {
%>
<p>
	Eigene Kontostand ist: <b><%=server.frageKontostandAb(name)
									.getQueryResult().get(0)[0].toString()%></b> euro
</p>
<%
	}
				if (server.getRolle(name).getQueryResult().get(0)[0]
						.toString().equals("Administratoren")) {
%>
<p>Der Kontostand aller Benutzern ist:</p>
<table style="width: 100%; text-align: center;">
	<tr>
		<th>Name</th>
		<th>Guthaben (euro)</th>
	</tr>

	<tr>

		<%
			for (int a = 0; a <= server.benutzerListeLiefern()
									.getQueryResult().size() - 1; a++) {
		%>
	
	<tr>
		<td><%=server.benutzerListeLiefern()
										.getQueryResult().get(a)[0].toString()%></td>
		<td><%=server
										.frageKontostandAb(
												server.benutzerListeLiefern()
														.getQueryResult()
														.get(a)[0].toString())
										.getQueryResult().get(0)[0].toString()%></td>
	</tr>
	<%
		}
					}
				}
			}
	%>


</table>
<%
	} else {
%>
<div>Zugriff verweigert</div>
<%
	}
%>