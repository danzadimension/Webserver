<%@ page import="src.QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form id="newKontoForm" method="post"
	onsubmit="new Ajax.Updater('new', 'kontocharge.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {

				QueryServer server = new QueryServer();

				String name = session.getAttribute("benutzerName").toString();

				boolean correct = true;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					float value = 0;

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						System.out.println("ELEMENT> " + element);
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("newKontoCharge")) {

							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (valueName[i].toString().matches(
											"[a-zA-Z]+")
											|| valueName[i].toString().matches(
													"[a-zA-Z]+[0-9]+")) {
										correct = false;
									} else {

										value = Float.valueOf(valueName[i]
												.toString());

										for (int j = 0; j <= server
												.benutzerListeLiefern()
												.getQueryResult().size() - 1; j++) {
											if (server
													.benutzerRFIDListeLiefern()
													.getQueryResult().get(j)[0]
													.toString().equals(element)) {

												int code = server
														.ladeKontoAuf(
																server.benutzerListeLiefern()
																		.getQueryResult()
																		.get(j)[0]
																		.toString(),
																value);

												if (code == 0)
													correct = true;
												if (code == -2)
													correct = false;
											}
										}
									}
								}
							}
						}
					}
				}

				String konto = server.frageKontostandAb(name).getQueryResult()
						.get(0)[0];

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R16")) {
		%>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Name</th>
				<th>Guthaben (euro)</th>
				<th>Konto aufladen (euro)</th>
			</tr>
			<%
				for (int j = 0; j <= server.benutzerListeLiefern()
									.getQueryResult().size() - 1; j++) {
			%>
			<tr>
			<tr>
				<td><%=server.benutzerListeLiefern()
									.getQueryResult().get(j)[0].toString()%></td>
				<td><%=server
									.frageKontostandAb(
											server.benutzerListeLiefern()
													.getQueryResult().get(j)[0]
													.toString())
									.getQueryResult().get(0)[0].toString()%></td>
				<td><input
					name=<%=server.benutzerRFIDListeLiefern()
									.getQueryResult().get(j)[0].toString()%>
					style="width: 50px;" type="text"></td>
			</tr>

			<%
				}
			%>

			<tr>
				<th valign=bottom align="right"><input name="newKontoCharge"
					type="submit" value="OK" align="right"
					style="width: 60px; position: absolute; right: 135px;" /></th>
			</tr>
		</table>
		<%
			}

				}

				if (correct == false) {
		%>
		<br> <br>
		<p style="color: red" align="right">
			<b>Konto kann nicht aufgeladen werden!</b>
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