<%@ page import="QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		if (request.getParameterMap().containsKey("role")) {

			QueryServer server = (QueryServer) session
					.getAttribute("server");
			String executor = (String) session
					.getAttribute("benutzerName");
			String role = request.getParameter("role");

			if (server.createRole(executor, role).getErrorCode() == 0) {
%>
<div>
	Es wurde eine neue Rolle mit dem Rollennamen '<%=role%>' registriert.
</div>
<%
	} else {
%>
<div class="error">
	Es wurde keine neue Rolle mit dem Rollennamen '<%=role%>' registriert.
</div>
<%
	}

		} else {
%>
<div>
	Rolle anlegen: <input id="role" type="text"> <a href=#
		onclick="createRole(document.getElementById('role').value);"><img
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