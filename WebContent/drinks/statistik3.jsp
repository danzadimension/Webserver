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
				LinkedList<Integer> list = new LinkedList<Integer>();
				int umsatz = 0;
				String count = "";

				QueryServer server = new QueryServer();
		%>
		<p>Die Anzahl von verkauften Getränken sind:</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Getränk</th>
				<th>Menge (Stk.)</th>
			</tr>
			<%
				for (int j = 0; j <= server.frageGetraenkBarcodeListe()
							.getQueryResult().size() - 1; j++) {
						umsatz = 0;
			%><tr>
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
									umsatz = umsatz + 1;

								}
							}
				%>
				<td><%=server.frageGetraenkBarcodeListe()
							.getQueryResult().get(j)[0].toString()%></td>
				<td><%=server
							.frageGetraenkAb(
									server.frageGetraenkBarcodeListe()
											.getQueryResult().get(j)[0]
											.toString()).getQueryResult()
							.get(0)[1].toString()%></td>

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