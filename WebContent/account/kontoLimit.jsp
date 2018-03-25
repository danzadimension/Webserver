<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form id="newKontoLimitForm" method="post"
	onsubmit="new Ajax.Updater('new', 'kontoLimit.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {
				QueryServer server = new QueryServer();

				boolean correct = true;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					float value = 0;

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("newKontoLimit")) {

							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (valueName[i].toString().matches(
											"[a-zA-Z]+")
											|| valueName[i].toString().matches(
													"[a-zA-Z]+[0-9]+")) {
										correct = false;
										System.out.println("Value: "
												+ valueName[i].toString());
									} else {

										value = Float.valueOf(valueName[i]
												.toString());
										System.out.println("Value: "
												+ valueName[i].toString());

										int code = server
												.setzeGlobalesKontolimit(value);

										if (code == 0)
											correct = true;
										if (code == -2 || code == -4)
											correct = false;
									}
								}
							}
						} else
							correct = false;
					}
				}

				String name = session.getAttribute("benutzerName").toString();
				String kontolimitOld = server.frageKontolimitAb()
						.getQueryResult().get(0)[0].toString();

				boolean count = true;

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R18")) {
		%>
		<p>
			Zur Zeit ist das Kontolimit beträgt <b><%=kontolimitOld%> euro </b>
		</p>
		<p>Hier können sie das Kontolimit ändern:</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th colspan="2" style="text-align: right;">Kontolimit: <input
					name="kontolimit" type="text"> <input name="newKontoLimit"
					type="submit" value="OK" align="right" />
				</th>
			</tr>
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

		<p style="color: red" align="right">
			<b>Kontolimit wurde nicht eingesetzt!</b>
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