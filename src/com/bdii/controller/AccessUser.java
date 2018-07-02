package com.bdii.controller;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bdii.data.DAO;

/**
 * Servlet implementation class AccessUser
 */
@WebServlet(description = "Access user Servlet", urlPatterns = { "/AccessUser" })
public class AccessUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		DAO dao;
		
		try {
			dao = new DAO();
			
			String email = request.getParameter("email");  
	        String password = request.getParameter("password");  
	          
	        if(dao.checkLogin(email, password).equals("U")) {
	        	PreparedStatement pstmt = dao.getConnection().prepareStatement("select deref(dati_utente).cf as cf,"
	        			+ " deref(dati_utente).nome as nome,"
	        			+ " deref(dati_utente).bonus as bonus"
	        			+ " from users"
	        			+ " where email = \'" + email + "\'");
	        	
	        	ResultSet rs = pstmt.executeQuery();
	        	rs.next();
	        	String cf = rs.getString("cf");
	        	String nomeCliente = rs.getString("nome");
	        	String bonusCliente = rs.getString("bonus");
	        	
	        	Cookie ck_cliente = new Cookie("cf", cf);
	        	ck_cliente.setMaxAge(60*60);
	        	response.addCookie(ck_cliente);
	        	
	        	Cookie ck = new Cookie("role", "U");
	        	ck.setMaxAge(60*60);
	            response.addCookie(ck);
	            
	            rs.close();
	            pstmt.close();
	            
	            request.setAttribute("utente", nomeCliente);
	            request.setAttribute("bonus", bonusCliente);
	            RequestDispatcher rd = request.getRequestDispatcher("./User/index.jsp");  
	            rd.forward(request, response);  
	        }  
	        else {
	        	request.setAttribute("error", "Errore nella fase di login come utente.");
	        	request.setAttribute("role", "U");
	        	RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
	        	rd.include(request, response); 
	        } 
	        
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			request.setAttribute("error", e.getMessage());
			request.setAttribute("role", "U");
			RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
			rd.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
