<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form action="verhaltenForm" method="post"
	onsubmit="new Ajax.Updater('new', 'verhalten.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {

				QueryServer server = new QueryServer();

				String behaviorOld = server.frageLogoutverhaltenAb()
						.getQueryResult().get(0)[0].toString();

				boolean correct = true;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("newBehavior")) {
							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (valueName[i].toString().matches(
											"[0-9]+")
											|| valueName[i].toString().matches(
													"[0-9]+[a-zA-Z]+")
											|| valueName[i].toString().equals(
													behaviorOld)) {
										correct = false;
									}
									if (!valueName[i].toString().equals(
											behaviorOld)) {

										int code = server
												.setzeCheckoutverhalten(
														session.getAttribute(
																"benutzerName")
																.toString(),
														valueName[i].toString());

										if (code == 0)
											correct = true;
										if (code == -2 || code == -3)
											correct = false;
									}
								} else
									correct = false;
							}
						}
					}
				}
				behaviorOld = server.frageLogoutverhaltenAb().getQueryResult()
						.get(0)[0].toString();
				String name = session.getAttribute("benutzerName").toString();

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R14")) {
		%>
		<p>Sie können zwischen Sofort und Countdown auswählen.</p>
		<p>
			Zur Zeit ist das Verhalten auf <b><%=behaviorOld%></b> eingestellt.
		</p>
		<table style="width: 100%; text-align: center;">
			<tr>
				<th colspan="2" style="text-align: right;">Verhalten: <input
					name="behavior" type="text"> <input name="newBehavior"
					type="submit" value="OK" align="left" /></th>
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
			} else {
		%>
		<div>Zugriff verweigert</div>
		<%
			}
		%>
	</div>
</form>