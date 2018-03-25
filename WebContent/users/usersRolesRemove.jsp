<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	QueryServer server = (QueryServer) session
				.getAttribute("server");

		if (request.getParameterMap().containsKey("user")
				&& request.getParameterMap().containsKey("role")) {

			String executor = (String) session
					.getAttribute("benutzerName");
			String user = request.getParameter("user");
			String role = request.getParameter("role");

			if (server.removeRoleFromUser(executor, user, role)
					.getErrorCode() == 0) {
%>
<div>
	Die Zuweisung zwischen dem Benutzer '<%=user%>' und der Rolle '<%=role%>'
	wurde entfernt.
</div>
<%
	} else {
%>
<div class="error">
	Die Zuweisung zwischen dem Benutzer '<%=user%>' und der Rolle '<%=role%>'
	wurde nicht entfernt.
</div>
<%
	}

		} else {
%>
<%
	String executor = (String) session
					.getAttribute("benutzerName");
			String userID = request.getParameter("userID");
			LinkedList<String[]> rows = server.getUsersRoles(executor,
					userID).getQueryResult();

			if (rows.isEmpty()) {
%>
<div class="error">
	Der Benutzer '<%=userID%>' hat noch keine Rolle zugewiesen bekommen.
</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Benutzername</td>
			<td>Rolle</td>
			<td>entfernen</td>
		</tr>
		<%
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
			<td><a href=#
				onclick="removeRole('<%=row[0]%>', '<%=row[1]%>');"><img
					alt="edit" src="images/pfeil_rot.png"></a></td>
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
			<td>Beschreibung</td>
		</tr>
		<%
			rows = server.getRolesDescriptions(executor)
								.getQueryResult();

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
			<td>Rollenname</td>
			<td>Recht</td>
		</tr>
		<%
			rows = server.getRolesRights(executor).getQueryResult();

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
		}
%>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>