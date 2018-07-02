<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/MacchineVirtuali">Macchine Virtuali</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/GestioneServer">Server</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Server</h1>
	<table class = "prova">
		<tr>
			<th>ID</th>
		    <th>RAM</th>
		    <th>PROCESSORE</th>
		    <th>HDD (GB)</th>
		    <th>N° VM</th>
		</tr>
		<c:forEach items="${server}" var="s">
		<tr>
			<td><c:out value="${s.getID()}" /></td>
			<td><c:out value="${s.getRam()}" />GB</td>
			<td><c:out value="${s.getProcessore()}" /> Core</td>
			<td><c:out value="${s.getHdd()}" /></td>
			<td><c:out value="${s.getNumeroVM()}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>
<aside>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/RestituisciHwDisponibile">Crea Server</a></li>
</ul>
<hr/>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/EliminaServer">Elimina Server</a></li>
</ul>
</aside>