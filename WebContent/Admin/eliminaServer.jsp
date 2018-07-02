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
<h1>Elimina Server</h1>
<p>Seleziona il server da eliminare:</p>
<form name="Form" id="serform" method="POST" action="http://localhost:8080/CLOUDYS/CancellaServer">
	<select name="server" form="serform">
    <c:forEach items="${server}" var="s">
		<option value="${s}"><c:out value="${s}" /></option>
	</c:forEach>
	</select>
  
    <input type="submit" value="Elimina">
</form>
</main>
<aside>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/RestituisciHwDisponibile">Crea Server</a></li>
</ul>
</aside>