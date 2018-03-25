<%@ page
	import="src.getraenkesystem.*, src.QueryServer.*,java.util.*,  java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="JavaScript" src="javascript/prototype.js"></script>
<script language="JavaScript" src="javascript/index.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<title>Florida Drinks</title>
</head>
<body id="body">
	<div class="background"></div>
	<div class="wrapper"></div>
	<%
		boolean correct = false;
		String fehler = "";

		if (request.getParameterMap().containsKey("benutzerName")
				&& request.getParameterMap().containsKey("passwort")) {

			QueryServer server = new QueryServer();

			if (!request.getParameter("benutzerName").isEmpty()
					&& !request.getParameter("passwort").isEmpty()
					&& server.authentifiziereBenutzer(
							request.getParameter("benutzerName"),
							request.getParameter("passwort"), "", "")
							.getErrorCode() == 0) {

				session.setAttribute("server", server);
				session.setAttribute("benutzerName",
						request.getParameter("benutzerName"));
				correct = true;

			} else {

				fehler = "Fehler: Die angegebene Datenkombination wurde nicht im System gefunden.";

			}
		}

		if (correct) {

			QueryServer server = (QueryServer) session
					.getAttribute("server");
			String executor = (String) session.getAttribute("benutzerName");
			LinkedList<String> rights = server.getUsersRights(executor,
					executor);
	%>
	<div class="head center">
		Sei herzlich willkommen, <b><%=session.getAttribute("benutzerName")%></b>!
		(<a href=# onclick="logout();">ausloggen</a>)
	</div>
	<div class="menu center">
		<a href=# onclick="showUsersSubMenu();">Benutzerverwaltung</a> |
		<%
			if (rights.contains("R03") || rights.contains("R04")
						|| rights.contains("R05")
						|| rights.contains("Rechtezuweisung")) {
		%>
		<a href=# onclick="showRolesSubMenu();">Rollenverwaltung</a> |
		<%
			}
				if (rights.contains("R09") || rights.contains("R10")
						|| rights.contains("R11") || rights.contains("R12")
						|| rights.contains("R19") || rights.contains("R20")
						|| rights.contains("R21")) {
		%><a href=# onclick="showDrinksSubMenu();">Getraenkeverwaltung</a> |
		<%
			}
				if (rights.contains("R08") || rights.contains("R16")
						|| rights.contains("R17") || rights.contains("R18")) {
		%>
		<a href=# onclick="showAccountsSubMenu();">Kontoverwaltung</a> |
		<%
			}
				if (rights.contains("R13") || rights.contains("R14")) {
		%><a href=# onclick="showPropsSubMenu();">Systemeinstellungen</a>
		<%
			}
		%>
	</div>
	<div class="submenu center" id="submenu"></div>
	<div class="container center" id="container"></div>
	<%
		} else {
	%>
	<div class="login">
		Benutzername: <input id="benutzerName" name="benutzerName"
			style="width: 250px;" type="text"> Passwort: <input
			id="passwort" name="passwort" style="width: 250px;" type="password">
		<a href=#
			onclick="login(document.getElementById('benutzerName').value, document.getElementById('passwort').value);"><img
			src="images/pfeil.png"></a>
		<%
			if (!fehler.equals("")) {
		%>
		<div class="error"><%=fehler%></div>
		<%
			}
		%>
	</div>
	<%
		}
	%>
</body>
</html>