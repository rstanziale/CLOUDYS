<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="java.util.List" %> 
<nav>
    <ul>
        <li><a href="http://localhost:8080/CLOUDYS/Admin/index.jsp">Home</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Clienti">Clienti</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Fatture">Fatture</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/MacchineVirtuali">Macchine Virtuali</a></li>
		<li><a href="http://localhost:8080/CLOUDYS/Logout">Logout</a></li>
    </ul>
</nav>
<main>
<h1>Registra un nuovo server</h1>

	<form name="Form" id="hwform" method="POST" action="http://localhost:8080/CLOUDYS/InserisciServer">
		SCEGLI RAM:<br>
		<select name="ram" form="hwform">
		    <c:forEach items="${listRam}" var="ram">
				<option value="${ram.capacita}"><c:out value="${ram.capacita}" /> GB</option>
			</c:forEach>
		</select>
		SCEGLI PROCESSORE:<br>
		<select name="processori" form="hwform">
			<c:forEach items="${listProc}" var="processore">
				<option value="${processore}"><c:out value="${processore.modello}" /> <c:out value="${processore.core}" /> core</option>
			</c:forEach>
	  	</select>
	  	SCEGLI HARD DISK:<br>
	  	<br>
		  	<c:forEach items="${listHdd}" var="hdd">
	  			<input type="checkbox" name="hdd" value="${hdd.getCapacita()}"> <c:out value="${hdd.getCapacita()}" /> GB<br>
	  		</c:forEach>
 		<br>
	    <input type="submit" value="Seleziona">
 	</form>
</main>