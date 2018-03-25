<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form action="editDrinkForm" method="post"
	onsubmit="new Ajax.Updater('new', 'editDrink.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {

				QueryServer server = new QueryServer();

				String name = session.getAttribute("benutzerName").toString();

				boolean correct = true;
				String drinkName = null;
				String drinkBarcode = "";
				float drinkLiter = 0;
				float drinkPreis = 0;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					float value = 0;

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("editDrink")) {

							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (element.equals("drinkBarcode")) {
										if (valueName[i].toString().matches(
												"[0-9]+")) {
											drinkBarcode = valueName[i]
													.toString();
											System.out.println("Barcode: "
													+ drinkBarcode);
										} else
											correct = false;
									}

									if (element.equals("drinkName")) {
										if (valueName[i].toString().matches(
												"[a-zA-Z]+")) {
											drinkName = valueName[i].toString();
											System.out.println("Name: "
													+ drinkName);
										} else
											correct = false;
									}

									if (element.equals("drinkLiter")) {
										if (!valueName[i].toString().matches(
												"[a-zA-Z]+[0-9]+")
												|| !valueName[i].toString()
														.matches("[a-zA-Z]+")) {
											drinkLiter = Float
													.valueOf(valueName[i]
															.toString());
											System.out.println("Liter: "
													+ drinkLiter);
										} else {
											correct = false;
										}
									}

									if (element.equals("drinkPreis")) {
										if (!valueName[i].toString().matches(
												"[a-zA-Z]+[0-9]+")
												|| !valueName[i].toString()
														.matches("[a-zA-Z]+")) {
											drinkPreis = Float
													.valueOf(valueName[i]
															.toString());
											System.out.println("Menge: "
													+ drinkPreis);
										} else
											correct = false;
									}
								}
							}
						}
					}
					if (correct == true) {

						if (drinkPreis == 0)
							drinkPreis = -1.0f;

						if (drinkLiter == 0)
							drinkLiter = -1;

						System.out.println(drinkBarcode + drinkName
								+ drinkPreis + drinkLiter);
						int code = server.passeGetraenkAn(drinkBarcode,
								drinkName, drinkLiter, drinkPreis);
						System.out.println(code);
						if (code == 0)
							correct = true;
						if (code < 0 || code > 0)
							correct = false;
					} else
						correct = false;
				}

				boolean count = true;

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R21")) {
		%>
		<p>
			<b>Hinweis: </b>Bevor Sie Name, Liter oder Preis des Getränks ändern,
			tragen Sie bitte Barcode des Getränks ein:
		<table style="width: 100%; text-align: center;">
			<thead>
				<tr>
					<th>Barcode</th>
					<th>Name</th>
					<th>Liter</th>
					<th>Preis</th>
				</tr>
				<tr>
					<th><input name="drinkBarcode" style="width: 125px;"
						type="text"></th>
					<th><input name="drinkName" style="width: 100px;" type="text"></th>
					<th><input name="drinkLiter" style="width: 40px;" type="text"></th>
					<th><input name="drinkPreis" style="width: 40px;" type="text"></th>
				</tr>

				<tr>
					<%
						for (int j = 0; j <= server.frageGetraenkBarcodeListe()
											.getQueryResult().size() - 1; j++) {
					%>

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
									.getQueryResult().get(0)[2].toString()%></td>
				</tr>

				<tr>
					<%
						}
					%>
					<td></td>
					<td></td>

					<td><br> <input name="editDrink" type="submit" value="OK"
						align="right"
						style="width: 50px; position: absolute; right: 30px;" /></td>
				</tr>
			</thead>
		</table>
		<%
			count = false;

					}
				}

				if (count == true) {
		%>
		<table>
			<tr>
				<td>Sie haben kein Zugriff auf diese Funktion!</td>
			</tr>
		</table>
		<%
			}

				if (correct == false) {
		%>
		<br>
		<p style="color: red" align="right">
			<b>Getränk konnte nicht geändert werden!</b>
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