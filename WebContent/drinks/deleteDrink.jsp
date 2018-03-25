<%@ page import="src.QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form action="deleteDrinkForm" method="post"
	onsubmit="new Ajax.Updater('new', 'deleteDrink.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {

				QueryServer server = new QueryServer();

				boolean correct = true;
				String name = session.getAttribute("benutzerName").toString();
				boolean count = true;

				if (request.getParameterMap().containsKey("deleteDrink")) {

					String[] allParams = request.getParameterValues("drink");

					if (allParams != null) {
						for (int i = 0; i < allParams.length; i++) {

							for (int j = 0; j < server
									.frageGetraenkBarcodeListe()
									.getQueryResult().size() - 1; j++) {

								if (server.frageGetraenkBarcodeListe()
										.getQueryResult().get(j)[0].toString()
										.equals(allParams[i])) {
									server.loescheGetraenk(server
											.frageGetraenkBarcodeListe()
											.getQueryResult().get(j)[0]
											.toString());
									break;
								}
							}
						}
					} else
						correct = false;

				}

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R20")) {
		%>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Name</th>
				<th>Liter</th>
				<th>Preis</th>
				<th>Löschen</th>
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
									.getQueryResult().get(0)[2].toString()%></td>
				<td><%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[4].toString()%></td>


				<td><input name="drink" type="checkbox"
					value="<%=server.frageGetraenkBarcodeListe()
									.getQueryResult().get(j)[0].toString()%>"></td>

				<%
					}
				%>
			
			<tr>
				<td></td>
				<td></td>
				<td colspan="2" valign=bottom align=center><input
					name="deleteDrink" type="submit" value="OK" align="right"
					style="width: 30px; position: absolute; right: 65px;" /></td>
			</tr>
		</table>
		<%
			count = false;
					}
				}

				if (correct == false) {
		%>
		<br>
		<p style="color: red" align="right">
			<b>Getränk konnte nicht gelöscht werden!</b>
		</p>
		<%
			}

			} else {
		%>
		<div>Zugriff verweigert</div>
		<%
			}
		%>
	</div>
</form>