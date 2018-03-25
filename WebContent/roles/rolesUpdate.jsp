<%@ page import="QueryServer.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		String role = request.getParameter("role");
		String desc = request.getParameter("desc");

		if (server.aendereRollenbeschreibung(role, desc).getErrorCode() == 0) {
%>
<div>
	Die Rolle '<%=role%>' wurde bearbeitet.
</div>
<%
	} else {
%>
<div class="error">
	Die Rolle '<%=role%>' wurde nicht bearbeitet.
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