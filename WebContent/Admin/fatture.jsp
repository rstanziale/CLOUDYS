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
<h1>Fatture</h1>
	<form name="Form" id="cliform" method="POST" action="http://localhost:8080/CLOUDYS/FattureCliente">
		<select name="clienti" form="cliform">
	    <c:forEach items="${clienti}" var="c">
			<option value="${c}"><c:out value="${c}" /></option>
		</c:forEach>
		</select>
	  
	    <input type="submit" value="Seleziona">
 	</form>
 	<hr/>
	<table class = "prova">
		<tr>
			<th>CODICE</th>
		    <th>DATAEMISSIONE</th>
		    <th>IMPORTO</th>
		    <th>CLIENTE</th>
		</tr>
		<c:forEach items="${fatture}" var="f">
		<tr>
			<td><c:out value="${f.getCodice()}" /></td>
			<td><c:out value="${f.getDataemissione()}" /></td>
			<td><c:out value="${f.getImporto()}" />€</td>
			<td><c:out value="${f.getProprietario()}" /></td>
		</tr>
	 	</c:forEach>	
	</table>
</main>