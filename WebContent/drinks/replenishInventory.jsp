<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<form id="replenishInventoryForm" method="post"
	onsubmit="new Ajax.Updater('new', 'replenishInventory.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {
				QueryServer server = new QueryServer();

				boolean correct = true;

				if (request.getParameterMap().containsKey("bestandAufgefuellt")) {

					if (!request.getParameterMap().isEmpty()) {

						Enumeration<String> requestParameters = request
								.getParameterNames();
						int value = 0;

						while (requestParameters.hasMoreElements()) {

							String element = (String) requestParameters
									.nextElement().toString();
							String[] valueName = request
									.getParameterValues(element);

							if (!element.equals("bestandAufgefuellt")) {

								for (int i = 0; i <= valueName.length - 1; i++) {

									if (!valueName[i].toString().equals("")) {

										if (valueName[i].toString().matches(
												"[a-zA-Z]+[0-9]+")
												|| valueName[i].toString()
														.matches("[a-zA-Z]+")) {
											correct = false;
										} else {

											value = Integer
													.parseInt(valueName[i]
															.toString());

											for (int j = 0; j <= server
													.frageGetraenkBarcodeListe()
													.getQueryResult().size() - 1; j++) {
												if (server
														.frageGetraenkAb(
																server.frageGetraenkBarcodeListe()
																		.getQueryResult()
																		.get(j)[0]
																		.toString())
														.getQueryResult()
														.get(0)[0].toString()
														.equals(element)) {

													int countOfDrinks = Integer
															.parseInt(server
																	.frageGetraenkAb(
																			server.frageGetraenkBarcodeListe()
																					.getQueryResult()
																					.get(j)[0]
																					.toString())
																	.getQueryResult()
																	.get(0)[3]
																	.toString());

													if (value <= countOfDrinks) {
														correct = false;
													} else {
														int code = server
																.passeBestandAn(
																		server.frageGetraenkBarcodeListe()
																				.getQueryResult()
																				.get(j)[0]
																				.toString(),
																		value,
																		-1);
														System.out
																.println(code);

														if (code < 0
																|| code > 0)
															correct = false;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

				String name = session.getAttribute("benutzerName").toString();

				boolean count = true;
				int number = 0;

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {

					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R10")) {
		%>
		<p>
			<b>ACHTUNG:</b> Neue Anzahl der Einheiten des Aufzufuellenden
			Getraenkes - dieser Wert wird NICHT addiert, sondern ERSETZT den
			alten Wert
		</p>
		<br>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Getränk</th>
				<th>Menge (Stk.)</th>
				<th>Neue Menge (Stk.)</th>
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
									.getQueryResult().get(0)[3].toString()%></td>
				<td><input
					name=<%=server
									.frageGetraenkAb(
											server.frageGetraenkBarcodeListe()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[0].toString()%>
					style="width: 50px;" type="text"></td>
				<%
					}
				%>
			</tr>


			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td colspan="2" valign=bottom align=left><input
					name="bestandAufgefuellt" type="submit" value="OK" align="right"
					style="width: 60px; position: absolute; right: 100px;" /></td>
			</tr>
		</table>
		<br> <br>

		<%
			}
				}

				if (correct == false) {
		%>

		<p style="color: red" align="right">
			<b>Bestand ist nicht aufgefüllt!</b>
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