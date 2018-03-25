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

			if (server.removeRightFromRole(executor, role, right)
					.getErrorCode() == 0) {
%>
<div>
	Die Zuweisung zwischen der Rolle '<%=role%>' und dem Recht '<%=right%>'
	wurde entfernt.
</div>
<%
	} else {
%>
<div class="error">
	Die Zuweisung zwischen der Rolle '<%=role%>' und dem Recht '<%=right%>'
	wurde nicht entfernt.
</div>
<%
	}

		} else {
%>
<%
	String executor = (String) session
					.getAttribute("benutzerName");
			String roleID = request.getParameter("roleID");
			System.out.println(executor);
			System.out.println(roleID);
			LinkedList<String[]> rows = server.getRightsByRole(
					executor, roleID).getQueryResult();

			if (rows == null || rows.isEmpty()) {
%>
<div class="error">
	Die Rolle '<%=roleID%>' hat noch kein Recht zugewiesen bekommen.
</div>
<%
	} else {
%>
<div>
	<table>
		<tr>
			<td>Rolle</td>
			<td>Recht</td>
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
				onclick="removeRight('<%=roleID%>', '<%=row[1]%>');"><img
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
