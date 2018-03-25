<%@ page
	import="java.awt.Graphics, QueryServer.*,java.util.*,  java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<form id="statistikForm" method="post"
	onsubmit="new Ajax.Updater('new', 'statistikDrink.jsp', {method: 'get', asynchronous:false, parameters:Form.serialize(this) }); return false;">
	<div id="new">

		<%
			if (session.getAttribute("benutzerName") != null) {
				QueryServer server = new QueryServer();

				QueryResult result = new QueryResult(0);
				String name = session.getAttribute("benutzerName").toString();

				boolean count = true;

				for (int i = 0; i <= server.benutzerrechteAbfragen(name)
						.getQueryResult().size() - 1; i++) {
					if (server.benutzerrechteAbfragen(name).getQueryResult()
							.get(i)[0].equals("R12")) {
		%>

		<p>
			<a href=#
				onclick="new Ajax.Updater('StatistikNew', 'drinks/threeDrinks.jsp', { method: 'post' }); return false;">
				1. Drei Favoriten von jedem Benutzer</a><br> <a href=#
				onclick="new Ajax.Updater('StatistikNew', 'drinks/statistik2.jsp', { method: 'post' });return false;">
				2. Wie viel Umsatz hat jedes Getränk gebracht?</a><br> <a href=#
				onclick="new Ajax.Updater('StatistikNew', 'drinks/statistik3.jsp', { method: 'post' });return false;">
				3. Welche Getränk ist unser Bestlseller?</a><br>
		</p>

		<hr style="color: black;" />

		<div id="StatistikNew"></div>
		<%
			}
				}

			} else {
		%>
		<div>Zugriff verweigert</div>
		<%
			}
		%>
	</div>
</form>