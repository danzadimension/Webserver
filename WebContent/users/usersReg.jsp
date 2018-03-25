<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	if (request.getParameterMap().containsKey("user")) {

			QueryServer server = (QueryServer) session
					.getAttribute("server");
			String executor = (String) session
					.getAttribute("benutzerName");
			String user = request.getParameter("user");

			if ((server.createUser(executor, user, "", "",
					"", null, false).getErrorCode()) == 0) {
%>
<div>
	Es wurde ein neuer Benutzer mit dem Benutzernamen '<%=user%>'
	registriert.
</div>
<%
	} else {
%>
<div class="error">
	Es wurde kein neuer Benutzer mit dem Benutzernamen '<%=user%>'
	registriert. Es ist ein Fehler aufgetreten.
</div>
<%
	}

		} else {
%>
<div>
	Benutzer anlegen: <input id="user" type="text"> <a href=#
		onclick="createUser(document.getElementById('user').value);"><img
		alt="edit" src="images/pfeil.png"></a>
</div>
<%
	}
%>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>