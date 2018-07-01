package com.bdii.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bdii.data.DAO;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		String cf = request.getParameter("cf");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String citta = request.getParameter("città");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			DAO dao = new DAO();
			
			PreparedStatement pstmt = dao.getConnection().prepareStatement("insert into cliente values (\'" + cf + "\',"
				+ "\'" + nome + "\',"
				+ "\'" + cognome + "\',"
				+ "\'" + email + "\',"
				+ "\'" + citta + "\',"
				+ "0, null, null)");
			
			pstmt.executeUpdate();
			
			pstmt = dao.getConnection().prepareStatement("insert into users values(\'" + email + "\',"
				+ "\'" + password + "\',"
				+ "\'U\', null)");
			
			pstmt.executeUpdate();
			
			pstmt = dao.getConnection().prepareStatement("update users"
					+ " set dati_utente = ((select ref(c) from cliente c"
					+ " where cf = \'" + cf + "\'))"
					+ " where email = \'" + email + "\'");
			
			pstmt.executeUpdate();
			pstmt.close();
			dao.closeConnection();
			
			RequestDispatcher view = request.getRequestDispatcher("./User/accessUser.html");
			view.forward(request, response);
			
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
