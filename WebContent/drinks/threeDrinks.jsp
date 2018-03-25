<%@ page import="java.awt.Graphics, QueryServer.*,java.util.*,  java.sql.*" language="java"
	contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
				
						<% QueryServer server = new QueryServer(); %>
						<p>Die Benutzer haben folgende Favourites:</p>
							<table style="width: 100%; text-align: center;">
							<tr>
								<th>Name</th>
								<th>Getränk</th>
							</tr>	
							
							<tr>
				
							<%
							LinkedList <String []> resultList;
							for (int a = 0; a <= server.benutzerListeLiefern().getQueryResult().size() - 1; a++){

							resultList = server.dreiMeistGekauftenGetraenke(server.benutzerListeLiefern().getQueryResult().get(a)[0].toString()).getQueryResult();
							%>
							<tr>
								<td><%=server.benutzerListeLiefern().getQueryResult().get(a)[0].toString()%></td>

									
								<td><%for(int j = 0; j <= resultList.size()-1; j++ ){%><%=resultList.get(j)[0].toString() + ',' %> 
								<%}
							}
							%>
								</td>
					</tr>
		
				</table>
						
</body>
</html>