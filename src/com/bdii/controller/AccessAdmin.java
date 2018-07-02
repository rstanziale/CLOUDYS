package com.bdii.controller;


import java.io.IOException;
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
 * Servlet implementation class AccessAdmin
 */
@WebServlet(description = "Access admin Servlet", urlPatterns = { "/AccessAdmin" })
public class AccessAdmin extends HttpServlet {
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
	        
	        if(dao.checkLogin(email, password).equals("A")) {
	        	Cookie ck = new Cookie("role", "A");
	        	ck.setMaxAge(60*60);
	            response.addCookie(ck);
	            
	            RequestDispatcher rd = request.getRequestDispatcher("./Admin/index.jsp");  
	            rd.forward(request, response);  
	        }  
	        else {
	        	request.setAttribute("error", "Errore nella fase di login come amministratore.");
	        	request.setAttribute("role", "A");
	        	RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
	        	rd.include(request, response); 
	        } 
	        
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			request.setAttribute("error", e.getMessage());
			request.setAttribute("role", "A");
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
