<%@ page
	import="QueryServer.*,java.util.*, java.text.SimpleDateFormat, java.util.Date, java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<form id="kontoUmsatzForm" method="post"
	onsubmit="new Ajax.Updater('new', 'statistik2.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">

		<%
			if (session.getAttribute("benutzerName") != null) {

				boolean correct = true;
				LinkedList<String[]> resultList;
				float umsatz = 0;
				String count = "";

				QueryServer server = new QueryServer();
		%>
		<p>Die Umsätze von jedem Getränk sind:</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Getränk</th>
				<th>Umsatz</th>
			</tr>
			<%
				for (int j = 0; j <= server.frageGetraenkBarcodeListe()
							.getQueryResult().size() - 1; j++) {
						umsatz = 0;
			%><tr>
				<td><%=server.frageGetraenkBarcodeListe()
							.getQueryResult().get(j)[0].toString()%></td>
				<td><%=server
							.frageGetraenkAb(
									server.frageGetraenkBarcodeListe()
											.getQueryResult().get(j)[0]
											.toString()).getQueryResult()
							.get(0)[1].toString()%></td>
				<%
					for (int a = 0; a <= server
									.frageUmsaetzeAb(null, null, null).getQueryResult()
									.size() - 1; a++) {
				%>
				<%
					if (server.frageUmsaetzeAb(null, null, null)
										.getQueryResult().get(a)[0].toString().equals(
										server.frageGetraenkBarcodeListe()
												.getQueryResult().get(j)[0].toString())) {
									count = server.frageUmsaetzeAb(null, null, null)
											.getQueryResult().get(a)[2].toString();
									umsatz = umsatz + Float.valueOf(count);

								}
							}
				%>

				<td><%=umsatz%></td>

				<%
					}
				%>
			</tr>
		</table>
		<%
			} else {
		%>
		<div>Zugriff verweigert</div>
		<%
			}
		%>


	</div>
</form>