<%@ page import="src.QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {

		if (request.getParameterMap().containsKey("user")) {

			QueryServer server = (QueryServer) session
					.getAttribute("server");
			String executor = (String) session
					.getAttribute("benutzerName");
			String user = request.getParameter("user");

			if (server.deleteUser(executor, user).getErrorCode() == 0) {
%>
<div>
	Der Benutzer '<%=user%>' wurde endgueltig geloescht.
</div>
<%
	} else {
%>
<div class="error">
	Das Loeschen des Benutzers '<%=user%>' war nicht erfolgreich.
</div>
<%
	}
		}
%>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>