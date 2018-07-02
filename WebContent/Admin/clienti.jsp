<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Clienti</h1>
	<table class = "prova">
		<tr>
			<th>CF</th>
		    <th>NOME</th>
		    <th>COGNOME</th>
		    <th>EMAIL</th>
		   	<th>BONUS</th>
		</tr>
		<c:forEach items="${clienti}" var="c">
		<tr>
			<td><c:out value="${c.getCf()}" /></td>
			<td><c:out value="${c.getNome()}" /></td>
			<td><c:out value="${c.getCognome()}" /></td>
			<td><c:out value="${c.getEmail()}" /></td>
			<td><c:out value="${c.getBonus()}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>
<aside>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/ClientiPresentatori">Clienti con presentazioni</a></li>
</ul>
</aside>