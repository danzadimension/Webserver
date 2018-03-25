<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		LinkedList<String> rights = server.getUsersRights(executor,
				executor);
		// TODO
		rights.addLast("Rechtezuweisung");

		if (request.getParameterMap().containsKey("role")) {

			String role = request.getParameter("role");

			LinkedList<String[]> rows = server.searchRole(executor,
					role).getQueryResult();

			if (rows == null || rows.isEmpty()) {
%><div class="error">
	Die Suche nach '<%=role%>' ergab kein Ergebnis.
</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Rollenname</td>
			<%
				if (rights.contains("R05")) {
			%>
			<td>Rollenbeschreibung</td>
			<td>bearbeiten</td>
			<%
				}

							if (rights.contains("Rechtezuweisung")) {
			%>
			<td>Rechte zuweisen</td>
			<td>Rechte entfernen</td>
			<%
				}

							if (rights.contains("R04")) {
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
				if (rights.contains("R05")) {
			%>
			<td><%=row[1]%></td>
			<td><a href=# onclick="editRole('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil.png"></a></td>
			<%
				}

								if (rights.contains("Rechtezuweisung")) {
			%>
			<td><a href=# onclick="assignRightToRole('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_blau.png"></a></td>
			<td><a href=# onclick="removeRightFromRole('<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_rot.png"></a></td>
			<%
				}

								if (rights.contains("R04")) {
			%>
			<td><a href=# onclick="deleteRole('<%=row[0]%>');"><img
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
<div style="margin-top: 30px;">
	<table>
		<tr>
			<td>Rollenname</td>
			<td>Recht</td>
		</tr>
		<%
			rows = server.getRolesRights(role).getQueryResult();

						for (String[] row : rows) {
		%>
		<tr>
			<%
				for (String col : row) {
			%>
			<td><%=col%></td>
			<%
				}
			%>
		</tr>
		<%
			}
		%>
	</table>
</div>
<div style="margin-top: 30px;">
	<table>
		<tr>
			<td>Recht</td>
			<td>Beschreibung</td>
		</tr>
		<%
			rows = server.getRights(executor).getQueryResult();

						for (String[] row : rows) {
		%>
		<tr>
			<%
				for (String col : row) {
			%>
			<td><%=col%></td>
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
	Rollenname: <input id="role" type="text"> <a href=#
		onclick="searchRole(document.getElementById('role').value);"><img
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