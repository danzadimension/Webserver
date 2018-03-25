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

			if (server.assignRoleToUser(executor, user, role)
					.getErrorCode() == 0) {
%>
<div>
	Die Zuweisung zwischen dem Benutzer '<%=user%>' und der Rolle '<%=role%>'
	wurde erstellt.
</div>
<%
	} else {
%>
<div class="error">
	Die Zuweisung zwischen dem Benutzer '<%=user%>' und der Rolle '<%=role%>'
	wurde nicht erstellt.
</div>
<%
	}

		} else {
%>
<%
	String executor = (String) session
					.getAttribute("benutzerName");
			LinkedList<String[]> rows = server.getRoles(executor)
					.getQueryResult();
			String userID = request.getParameter("userID");

			if (rows.isEmpty()) {
%>
<div class="error">Es wurden noch keine Rollen registriert.</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Benutzername</td>
			<td>Rolle</td>
			<td>zuweisen</td>
		</tr>
		<%
			for (String[] row : rows) {
		%>
		<tr>
			<%
				for (String col : row) {
			%>
			<td><%=userID%></td>
			<td><%=col%></td>
			<%
				}
			%>
			<td><a href=#
				onclick="assignRole('<%=userID%>', '<%=row[0]%>');"><img
					alt="edit" src="images/pfeil_blau.png"></a></td>
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
			rows = server.getRolesRights(userID).getQueryResult();

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
			rows = server.getRights(executor)
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