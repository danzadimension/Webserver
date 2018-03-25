<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	if (session.getAttribute("benutzerName") != null) {
%>
<%
	session.removeAttribute("benutzerName");
%>
<%
	} else {
%>
<div class="error">Zugriff verweigert.</div>
<%
	}
%>