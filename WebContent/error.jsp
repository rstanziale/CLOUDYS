<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/index.html">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/LogoutServlet">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Errore</h1>
<hr/>
<p><c:out value="${error}" /></p>
<c:choose>
	<c:when test = "${role == A}">
		Clicca <a href="http://localhost:8080/CLOUDYS/Admin/index.html">qui</a> per tornare alla home.
	</c:when>
	<c:when test = "${role == U}">
		Clicca <a href="http://localhost:8080/CLOUDYS/User/index.html">qui</a> per tornare alla home.
	</c:when>
	<c:otherwise>
		Clicca <a href="http://localhost:8080/CLOUDYS/index.html">qui</a> per tornare alla home.
	</c:otherwise>
</c:choose>
</main>