<%@ page import="QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<form id="newCountdownForm" method="post"
	onsubmit="new Ajax.Updater('new', 'newCountdown.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">
		<%
			if (session.getAttribute("benutzerName") != null) {

				QueryServer server = new QueryServer();
				boolean correct = true;

				if (!request.getParameterMap().isEmpty()) {

					Enumeration<String> requestParameters = request
							.getParameterNames();

					int value = 0;

					while (requestParameters.hasMoreElements()) {

						String element = (String) requestParameters
								.nextElement().toString();
						String[] valueName = request
								.getParameterValues(element);

						if (!element.equals("newCountdown")) {

							for (int i = 0; i <= valueName.length - 1; i++) {

								if (!valueName[i].toString().equals("")) {

									if (valueName[i].toString().matches(
											"[a-zA-Z]+[0-9]+")
											|| valueName[i].toString().matches(
													"[a-zA-Z]+")) {
										correct = false;
									} else {

										value = Integer.parseInt(valueName[i]
												.toString());

										int code = server.setzeCountdownlaenge(
												session.getAttribute(
														"benutzerName")
														.toString(), value);

										if (code == 0)
											correct = true;
										if (code == -2 || code == -3)
											correct = false;
									}
								}
							}
						} else
							correct = false;
					}
				}

				String name = session.getAttribute("benutzerName").toString();
				String countdownOld = server.frageCountdownAb()
						.getQueryResult().get(0)[0].toString();

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R13")) {
		%>
		<p>
			Zur Zeit ist das Verhalten auf <b><%=countdownOld%> sekunden </b>
			eingestellt
		</p>
		<p>Stellen Sie einen Countdown ein:</p>
		<table style="width: 100%;">
			<tr>
				<th colspan="2" style="text-align: right;">Countdown: <input
					name="countdown" type="text"> <input name="newCountdown"
					type="submit" value="OK"></th>
			</tr>
		</table>
		<%
			}
				}

				if (correct == false) {
		%>

		<p style="color: red" align="right">
			<b>Countdown wurde nicht eingestellt!</b>
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