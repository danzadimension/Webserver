<%@ page import="QueryServer.*" language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	QueryServer server = (QueryServer) session
				.getAttribute("server");
		String executor = (String) session.getAttribute("benutzerName");
		String user = request.getParameter("user");
		String rfid = request.getParameter("rfid");
		String code = request.getParameter("benutzerbarcode");

		boolean banned = false;

		if (Integer.valueOf(request.getParameter("gesperrt")) == 1)
			banned = true;

		if (server.updateUser(executor, user, rfid, code, banned)
				.getErrorCode() == 0) {
%>
<div>
	Der Benutzer '<%=user%>' wurde bearbeitet.
</div>
<%
	} else {
%>
<div class="error">
	Der Benutzer '<%=user%>' wurde nicht bearbeitet.
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