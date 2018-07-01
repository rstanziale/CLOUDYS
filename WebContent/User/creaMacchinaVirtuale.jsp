<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/User/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/GestionesMacchineVirtuali">Gestione Macchine Virtuali</a></li>
        <li><a href="http://localhost:8080/CLOUDYS/VisualizzaFatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/User/deleteUser.html">Disiscriviti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Nuova Macchina Virtuale</h1>
<p>Inserisci i dati della macchina che vuoi creare</p>
<div>
  <form name="Form" onsubmit="return validateForm()" id="osform" method="POST" action="http://localhost:8080/CLOUDYS/InserimentoMacchinaVirtuale">
    <label for="hdd">Hard Disk</label>
    <input type="text" id="hdd" name="hdd" data-alert="L'hdd" placeholder="L'hard disk...">
    
    <label for="ram">RAM</label>
    <input type="text" id="ram" name="ram" data-alert="La RAM" placeholder="La RAM...">
    
    <label for="processore">Numero Core</label>
    <input type="text" id="processore" name="processore" data-alert="Il processore" placeholder="Il numero di core...">
    
    <label for="sistemi">Sistema Operativo</label>
    <select name="sistemi" form="osform">
    <c:forEach items="${sistemi}" var="sistema">
		<option value="${sistema.nome}"><c:out value="${sistema.nome}" /> 	(<c:out value="${sistema.costo}" /> €)</option>
	</c:forEach>
	</select>
  
    <input type="submit" value="Crea">
 </form>
</div>
</main>
<aside>
<ul>
	<li><a href="http://localhost:8080/CLOUDYS/User/eliminaMacchinaVirtuale.html">Elimina Macchina Virtuale</a></li>
</ul>
</aside>