<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);

		if (request.getParameterMap().containsKey("user")) {

			String user = request.getParameter("user");

			LinkedList<String[]> rows = server.searchUser(executor,
					user).getQueryResult();

			if (rows.isEmpty()) {
%><div class="error">
	Die Suche nach '<%=user%>' ergab kein Ergebnis.
</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Benutzername</td>
			<%
				if (rights.contains("R15")) {
			%>
			<td>RFID</td>
			<td>Benutzerbarcode</td>
			<td>Gesperrt</td>
			<td>bearbeiten</td>
			<%
				}

							if (rights.contains("R06")) {
			%>
			<td>Rolle zuweisen</td>
			<td>Rolle entfernen</td>
			<%
				}

							if (rights.contains("R02")) {
			%>
			<td>loeschen</td>
			<%
				}
			%>
		</tr>
		<%
			for (String[] row : rows) {
		%>
		<tr>
			<td><%=row[0]%></td>
			<%
				if (rights.contains("R15")) {
			%>
			<td><%=row[1]%></td>
			<td><%=row[2]%></td>
			<td><%=row[3]%></td>
			<td><a href=# onclick="editUser('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil.png"></a></td>
			<%
				}

								if (rights.contains("R06")) {
			%>
			<td><a href=# onclick="assignRoleToUser('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_blau.png"></a></td>
			<td><a href=# onclick="removeRoleFromUser('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_rot.png"></a></td>
			<%
				}

								if (rights.contains("R02")) {
			%>
			<td><a href=# onclick="deleteUser('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_rot.png"></a></td>
			<%
				}
			%>
		</tr>
		<%
			}
		%>
	</table>
</div>
<%
	}

		} else {
%>
<div>
	Benutzername, RFID oder Benutzerbarcode: <input id="user" type="text">
	<a href=# onclick="searchUser(document.getElementById('user').value);"><img
		alt="edit" src="images/pfeil.png"></a>
</div>
<%
	}
%>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>