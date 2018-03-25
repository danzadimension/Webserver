<%@ page import="src.QueryServer.*,java.util.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	if (request.getParameterMap().containsKey("role")) {

			QueryServer server = (QueryServer) session
					.getAttribute("server");
			String executor = (String) session
					.getAttribute("benutzerName");
			String role = request.getParameter("role");

			if (server.deleteRole(executor, role).getErrorCode() == 0) {
%>
<div>
	Die Rolle '<%=role%>' wurde endgueltig geloescht.
</div>
<%
	} else {
%>
<div class="error">
	Das Loeschen der Rolle '<%=role%>' war nicht erfolgreich.
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