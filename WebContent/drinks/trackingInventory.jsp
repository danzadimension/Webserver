<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form action="replenishInventory.jsp" method="post">
	<%
		if (session.getAttribute("benutzerName") != null) {
			QueryServer server = new QueryServer();

			QueryResult result = new QueryResult(0);
			LinkedList<String[]> rows;
			String name = session.getAttribute("benutzerName").toString();

			boolean count = true;
			int number = 0;

			for (int i = 0; i <= server.benutzerrechteAbfragen(name)
					.getQueryResult().size() - 1; i++) {

				if (server.benutzerrechteAbfragen(name).getQueryResult()
						.get(i)[0].equals("R09")) {
	%>
	<table style="width: 100%; text-align: center;">
		<tr>
			<th>Barcode</th>
			<th>Getränk</th>
			<th>Liter</th>
			<th>Menge (Stk.)</th>
			<th>Verluste (Stk.)</th>
		</tr>

		<%
			for (int j = 0; j <= server.frageGetraenkBarcodeListe()
								.getQueryResult().size() - 1; j++) {
		%>

		<tr>
			<td><%=server.frageGetraenkBarcodeListe()
									.getQueryResult().get(j)[0].toString()%></td>
			<td><%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[1].toString()%></td>
			<td><%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[4].toString()%></td>
			<td><%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[3].toString()%></td>
			<td><%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[5].toString()%></td>
			<%
				}
						}
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
</form>