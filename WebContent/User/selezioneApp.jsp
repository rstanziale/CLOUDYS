<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/GestioneMacchineVirtuali">Macchine Virtuali</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/User/deleteUser.html">Disiscriviti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/LogoutServlet">Logout</a></li>
    </ul>
</nav>
<main>
	<h1>SCEGLI LE APPLICAZIONI DA INSTALLARE</h1>
	
	<form action="http://localhost:8080/CLOUDYS/InstallaApp" method="post">
		<c:forEach items="${apps}" var="app">
  			<input type="checkbox" name="app" value="${app.nome}"> <c:out value="${app.nome}" /><br>
  		</c:forEach>
  		<input type="submit" value="Installa">
	</form>
</main>