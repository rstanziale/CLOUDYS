<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/GestioneMacchineVirtuali">Gestione Macchine Virtuali</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/User/deleteUser.html">Disiscriviti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Benvenuto <c:out value="${utente}" /></h1>
<hr />
<p>Da qui potrai gestire le tue macchine virtuali navigando attraverso il menu e visualizzare le tue fatture.</p>
<p>Il tuo bonus ammonta a: <c:out value="${bonus}" />€.</p>
</main>