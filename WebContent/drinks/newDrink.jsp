<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<form action="newDrinkForm" method="post"
	onsubmit="new Ajax.Updater('new', 'newDrink.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">

		<%
			QueryServer server = new QueryServer();

				String name = session.getAttribute("benutzerName").toString();

				boolean correct = true;
				String drinkName = "";
				String drinkBarcode = "";
				float drinkLiter = 0;
				float drinkPreis = 0;
				int drinkCount = 0;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					float value = 0;

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("createNewDrink")) {

							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (element.equals("drinkBarcode")) {
										if (valueName[i].toString().matches(
												"[0-9]+"))
											drinkBarcode = valueName[i]
													.toString();
										else
											correct = false;
										break;
									}

									if (element.equals("drinkName")) {
										if (valueName[i].toString().matches(
												"[a-zA-Z]+"))
											drinkName = valueName[i].toString();
										else
											correct = false;
										break;
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
										} else
											correct = false;
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

									if (element.equals("drinkMenge")) {
										if (valueName[i].toString().matches(
												"[0-9]+")) {
											drinkCount = Integer
													.parseInt(valueName[i]
															.toString());
											System.out.println("Count: "
													+ drinkCount);
										} else
											correct = false;
									}

								} else
									correct = false;
							}
						}
					}
					if (correct == true) {
						System.out.println(drinkBarcode + drinkName
								+ drinkPreis + drinkLiter + drinkCount);
						int code = server.fuegeGetraenkHinzu(drinkBarcode,
								drinkName, drinkPreis, drinkLiter, drinkCount);

						if (code == 0)
							correct = true;
						if (code == -2 || code > 0)
							correct = false;
					}
				}

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R19")) {
		%>
		<p>Um ein neues Getränk hinzufügen, füllen Sie bitte folgendes
			Form auf:</p>
		<br>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th>Barcode</th>
				<th>Name</th>
				<th>Liter</th>
				<th>Preis</th>
				<th>Menge
				<th>
			</tr>
			<tr>
				<td><input name="drinkBarcode" style="width: 150px;"
					type="text"></td>
				<td><input name="drinkName" style="width: 150px;" type="text"></td>
				<td><input name="drinkLiter" style="width: 50px;" type="text"></td>
				<td><input name="drinkPreis" style="width: 50px;" type="text"></td>
				<td><input name="drinkCount" style="width: 50px;" type="text"></td>
				<td valign=bottom align=right><input name="createNewDrink"
					type="submit" value="OK" align="right" /></td>
			</tr>

		</table>
		<%
			}
				}

				if (correct == false) {
		%>

		<p style="color: red" align="right">
			<b>Verhalten ist nicht aufgestellt!</b>
		</p>
		<%
			}
		%>
	</div>
</form>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>