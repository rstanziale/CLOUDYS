<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.html">Home</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYSn/LogoutServlet">Logout</a></li>
    </ul>
</nav>
<main>
	<h1>LE MIE FATTURE</h1>
	<table class = "prova">
		<tr>
			<th>CODICE</th>
		    <th>DATA EMISSIONE</th>
		    <th>IMPORTO</th>
		    <th>MACCHINE VIRTUALI</th>

		</tr>
		<c:forEach items="${fatture}" var="fattura">
		<tr>
			<td><c:out value="${fattura.codice}" /></td>
			<td><c:out value="${fattura.dataemissione}" /></td>
			<td><c:out value="${fattura.importo}" /></td>
			<td><c:out value="${macchinevirtualicreate}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>