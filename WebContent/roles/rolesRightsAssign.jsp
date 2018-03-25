<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	QueryServer server = (QueryServer) session
				.getAttribute("server");

		if (request.getParameterMap().containsKey("role")
				&& request.getParameterMap().containsKey("right")) {

			String executor = (String) session
					.getAttribute("benutzerName");
			String role = request.getParameter("role");
			String right = request.getParameter("right");

			if (server.assignRightToRole(executor, role, right)
					.getErrorCode() == 0) {
%>
<div>
	Die Zuweisung zwischen der Rolle '<%=role%>' und dem Recht '<%=right%>'
	wurde erstellt.
</div>
<%
	} else {
%>
<div class="error">
	Die Zuweisung zwischen der Rolle '<%=role%>' und dem Recht '<%=right%>'
	wurde nicht erstellt.
</div>
<%
	}

		} else {
%>
<%
	String executor = (String) session
					.getAttribute("benutzerName");
			LinkedList<String[]> rows = server.getRights(executor)
					.getQueryResult();
			String role = request.getParameter("roleID");

			if (rows.isEmpty()) {
%>
<div class="error">Es wurden noch keine Rechte registriert.</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Rolle</td>
			<td>Recht</td>
			<td>zuweisen</td>
		</tr>
		<%
			for (String[] row : rows) {
		%>
		<tr>
			<td><%=role%></td>
			<td><%=row[0]%></td>
			<td><a href=#
				onclick="assignRight('<%=role%>', '<%=row[0]%>');"><img
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
			<td>Recht</td>
			<td>Beschreibung</td>
		</tr>
		<%
			rows = server.getRights(role).getQueryResult();

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