<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.html">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/MacchineVirtuali">Macchine Virtuali</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Macchine Virtuali  del cliente <c:out value="${cf}" /></h1>
	<table class = "prova">
		<tr>
			<th>ID</th>
		    <th>HDD</th>
		    <th>RAM</th>
		    <th>PROCESSORE</th>
		    <th>DATAEMISSIONE</th>
		    <th>COSTO</th>
		</tr>
		<c:forEach items="${macchine}" var="m">
		<tr>
			<td><c:out value="${m.getId()}" /></td>
			<td><c:out value="${m.getHdd()}" /></td>
			<td><c:out value="${m.getRam()}" /></td>
			<td><c:out value="${m.getProcessore()}" /></td>
			<td><c:out value="${m.getDatacreazione()}" /></td>
			<td><c:out value="${m.getCosto()}" />€/giorno</td>
		</tr>
	 	</c:forEach>	
	</table>
</main>