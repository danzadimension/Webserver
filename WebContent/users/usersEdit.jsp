<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	QueryServer server = (QueryServer) session.getAttribute("server");
	String executor = (String) session.getAttribute("benutzerName");
	String user = request.getParameter("user");
	LinkedList<String[]> users = server.searchUser(executor, user)
			.getQueryResult();
%>
<div>
	<table>
		<tr>
			<td>Benutzername</td>
			<td>Neuer RFID</td>
			<td>Neuer Benutzerbarcode</td>
			<td>Gesperrt</td>
			<td>uebernehmen</td>
		</tr>
		<%
			for (String[] u : users) {
		%>
		<tr>
			<td><%=u[0]%></td>
			<td><input id="rfid" type="text" value="<%=u[1]%>"></td>
			<td><input id="barcode" type="text" value="<%=u[2]%>"></td>
			<td><select id="banned">
					<%
						if (u[3].equals("0")) {
					%>
					<option selected>0</option>
					<option>1</option>
					<%
						} else {
					%>
					<option>0</option>
					<option selected>1</option>
					<%
						}
					%>
			</select></td>
			<td><a href=#
				onclick="updateUser('<%=user%>', document.getElementById('rfid').value, document.getElementById('barcode').value, document.getElementById('banned').value);"><img
					alt="edit" src="images/pfeil.png"></a></td>
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