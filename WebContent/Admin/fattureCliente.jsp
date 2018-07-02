<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.html">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Fatture del cliente <c:out value="${cf}" /></h1>
	<table class = "prova">
		<tr>
			<th>CODICE</th>
		    <th>DATAEMISSIONE</th>
		    <th>IMPORTO</th>
		</tr>
		<c:forEach items="${fatture}" var="f">
		<tr>
			<td><c:out value="${f.getCodice()}" /></td>
			<td><c:out value="${f.getDataemssione()}" /></td>
			<td><c:out value="${f.getImporto()}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>