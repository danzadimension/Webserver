<%@ page
	import="QueryServer.*,java.util.*, java.text.SimpleDateFormat, java.util.Date, java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<form id="kontoUmsatzForm" method="post"
	onsubmit="new Ajax.Updater('new', 'kontosales.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">

		<%
			if (session.getAttribute("benutzerName") != null) {

				boolean count = true;
				QueryServer server = new QueryServer();
				String name = session.getAttribute("benutzerName").toString();

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R17")) {
		%>
		<p>Tragen sie bitte hier die gewünschte Zeitraum (der Tag und die
			Uhrzeit) ein:</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Datum:</th>
				<td>von <input name="datum1" type="text"> bis <input
					name="datum2" type="text"></td>
				<td colspan="2" valign=bottom align=center><input
					name="newUmsatz" type="submit" value="OK" align="right" /></td>
			</tr>
		</table>
		<%
			}
				}

				boolean correct = true;
				Date date1 = new Date();
				Date date2 = new Date();
				String datum1 = "";
				String datum2 = "";

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					if (request.getParameterMap().containsKey("newUmsatz")) {

						while (requestParameters.hasMoreElements()) {

							String element = (String) requestParameters
									.nextElement().toString();
							String[] valueName = request
									.getParameterValues(element);

							if (!element.equals("newUmsatz")) {

								for (int i = 0; i <= valueName.length - 1; i++) {

									if (!valueName[i].toString().equals("")) {

										if (valueName[i].toString().matches(
												"[a-zA-Z]+[0-9]+")
												|| valueName[i].toString()
														.matches("[a-zA-Z]+")) {
											correct = false;
										} else {

											if (element.equals("datum1")) {

												if (element.equals("datum1")) {
													date1 = new SimpleDateFormat(
															"yyyy-MM-dd HH:mm:ss")
															.parse(valueName[i]);
													datum1 = valueName[i];
												}
											}
											if (element.equals("datum2")) {

												if (element.equals("datum2")) {
													date2 = new SimpleDateFormat(
															"yyyy-MM-dd HH:mm:ss")
															.parse(valueName[i]);
													datum2 = valueName[i];
												}
											}
										}
									} else
										correct = false;
								}
							}
						}
		%>
		<p>
			Die Umsätze von <b><%=datum1%></b> bis <b><%=datum2%></b> aller
			Benutzern sind:
		</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Name</th>
				<th>Umsatz</th>
				<th>Datum</th>
				<th>Benutzername</th>
			</tr>
			<%
				if (date1 != null && date2 != null) {

								if (server.getRolle(name).getQueryResult().get(0)[0]
										.toString().equals("Tutor")
										|| server.getRolle(name).getQueryResult()
												.get(0)[0].toString().equals(
												"Benutzer")
										|| server.getRolle(name).getQueryResult()
												.get(0)[0].toString().equals(
												"Wissenschaftlicher Mitarbeiter")) {

									int code = server.frageUmsaetzeAb(name, date1,
											date2).getErrorCode();

									if (code == 0) {
			%>

			<tr>
				<td><%=server
											.frageUmsaetzeAb(name, date1, date2)
											.getQueryResult().get(0)[0]
											.toString()%></td>
				<td><%=server
											.frageUmsaetzeAb(name, date1, date2)
											.getQueryResult().get(0)[1]
											.toString()%></td>
				<td><%=server
											.frageUmsaetzeAb(name, date1, date2)
											.getQueryResult().get(0)[2]
											.toString()%></td>
				<td><%=server
											.frageUmsaetzeAb(name, date1, date2)
											.getQueryResult().get(0)[3]
											.toString()%></td>
				<td><%=name%></td>
				<%
					correct = true;
										}
										if (code < 0 || code > 0)
											correct = false;
									}

									if (server.getRolle(name).getQueryResult().get(0)[0]
											.toString().equals("Administratoren")) {
										for (int a = 0; a <= server
												.benutzerListeLiefern()
												.getQueryResult().size() - 1; a++) {

											int code = server
													.frageUmsaetzeAb(
															server.benutzerListeLiefern()
																	.getQueryResult()
																	.get(a)[0], date1,
															date2).getErrorCode();

											if (code == 0) {
				%>
			
			<tr>
				<td><%=server
												.frageUmsaetzeAb(
														server.benutzerListeLiefern()
																.getQueryResult()
																.get(a)[0]
																.toString(),
														date1, date2)
												.getQueryResult().get(0)[0]
												.toString()%></td>
				<td><%=server
												.frageUmsaetzeAb(
														server.benutzerListeLiefern()
																.getQueryResult()
																.get(a)[0]
																.toString(),
														date1, date2)
												.getQueryResult().get(0)[1]
												.toString()%></td>
				<%
					System.out
														.println(server
																.frageUmsaetzeAb(
																		server.benutzerListeLiefern()
																				.getQueryResult()
																				.get(a)[0]
																				.toString(),
																		date1, date2)
																.getQueryResult()
																.get(0)[1].toString());
				%>
				<td><%=server
												.frageUmsaetzeAb(
														server.benutzerListeLiefern()
																.getQueryResult()
																.get(a)[0]
																.toString(),
														date1, date2)
												.getQueryResult().get(0)[2]
												.toString()%></td>
				<td><%=server
												.frageUmsaetzeAb(
														server.benutzerListeLiefern()
																.getQueryResult()
																.get(a)[0]
																.toString(),
														date1, date2)
												.getQueryResult().get(0)[3]
												.toString()%></td>
				<td><%=server.benutzerListeLiefern()
												.getQueryResult().get(a)[0]
												.toString()%></td>
				<%
					correct = true;
											}
										}
									}

								}
							}
						}
				%>
			</tr>
		</table>
		<%
			if (correct == false) {
		%>

		<p style="color: red" align="right">
			<b>Es gibt keine Kontoumsatze für den Abfragezeitraum!</b>
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