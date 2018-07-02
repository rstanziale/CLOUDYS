<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.jsp">Home</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
<<<<<<< HEAD
=======
		<li><a href="http://localhost:8080/CLOUDYS/MacchineVirtuali">Macchine Virtuali</a></li>
>>>>>>> branch 'master' of https://github.com/rstanziale/CLOUDYS
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Benvenuto Amministratore</h1>
<hr />
<p>Da qui potrai visualizzare clienti, fatture e macchine virtuali.</p>
<p>Inoltre potrai gestire i server della piattaforma.</p>
</main>

<aside>
<ul>
	<li><a href="" class="disabled">Stato: <c:out value="${stateJob}" /></option></a></li>
</ul>
<hr/>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/Job?Cod=0">Avvia Scheduler Fatture</a></li>
</ul>
<hr/>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/Job?Cod=1">Ferma Scheduler Fatture</a></li>
</ul>
</aside>
</main>
