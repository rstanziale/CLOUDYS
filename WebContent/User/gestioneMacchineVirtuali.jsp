<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/GestioneMacchineVirtuali">Macchine Virtuali</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/User/deleteUser.html">Disiscriviti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Gestione Macchine Virtuali</h1>
<p>Macchine Virtuali attive</p>
	<table class = "prova">
		<tr>
			<th>ID</th>
		    <th>HDD</th>
		    <th>RAM</th>
		    <th>CORE</th>
		   	<th>DATA CREAZIONE</th>
		   	<th>COSTO</th>
		   	<th>OS</th>
		   	<th>APP</th>
		</tr>
		<c:forEach items="${macchine}" var="vm">
		<tr>
			<td><c:out value="${vm.id}" /></td>
			<td><c:out value="${vm.hdd}" />GB</td>
			<td><c:out value="${vm.ram}" />GB</td>
			<td><c:out value="${vm.processore}" /></td>
			<td><c:out value="${vm.datacreazione}" /></td>
			<td><c:out value="${vm.costo}" />€/giorno</td>
			<td><c:out value="${vm.getOsName()}" /></td>
			<td><c:out value="${vm.getApp()}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>
<aside>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/NuovaMacchinaVirtuale">Nuova Macchina Virtuale</a></li>
</ul>
<hr/>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/User/eliminaMacchinaVirtuale.html">Elimina Macchina Virtuale</a></li>
</ul>
</ul>
</aside>