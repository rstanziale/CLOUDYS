<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.html">Home</a></li>
<<<<<<< HEAD
		<li><a href="http://localhost:8080/CLOUDYS/GestioneMacchineVirtuali">Gestione Macchine Virtuali</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/User/deleteUser.html">Disiscriviti</a></li>
=======
         <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
>>>>>>> branch 'master' of https://github.com/rstanziale/CLOUDYS
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Benvenuto <c:out value="${utente}" /></h1>
<hr />
<p>Da qui potrai gestire i tuoi possedimenti navigando attraverso il menu.</p>
</main>