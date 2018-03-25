<%@ page import="QueryServer.*, java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		String role = request.getParameter("role");
		LinkedList<String[]> roles = server.searchRole(executor, role)
				.getQueryResult();
		LinkedList<String[]> rows = server.getRolesDescriptions(
				executor).getQueryResult();
%>
<div>
	<table>
		<tr>
			<td>Rollenname</td>
			<td>Neue Beschreibung</td>
			<td>uebernehmen</td>
		</tr>
		<%
			for (String[] r : roles) {
		%>
		<tr>
			<td><%=r[0]%></td>
			<td><textarea id="desc" rows="10"><%=r[1]%></textarea></td>
			<td><a href=#
				onclick="updateRole('<%=role%>', document.getElementById('desc').value);"><img
					alt="edit" src="images/pfeil.png"></a></td>
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
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>