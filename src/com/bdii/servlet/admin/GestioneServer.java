package com.bdii.servlet.admin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bdii.data.DAO;
import com.bdii.model.Server;

/**
 * Servlet implementation class GestioneServer
 */
@WebServlet("/GestioneServer")
public class GestioneServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneServer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Cookie ck[] = request.getCookies();
		String role = "";

		for(Cookie temp : ck) {
			if(temp.getName().equals("role")) {
				role = temp.getValue();
			}
		}
		
		if(ck != null) {
			if(role.equals("A")) {
				try {
					DAO dao = new DAO();
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT ID, "
							+ "DEREF(RAM).CAPACITA AS RAM, "
							+ "DEREF(PROCESSORE).CORE AS PROCESSORE "
							+ "FROM SERVER");
					
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<Server> server = new ArrayList<Server>();
					while(result.next()) {
						Server s = new Server(result.getString("ID"),
								result.getString("RAM"),
								result.getString("PROCESSORE"));
						
						PreparedStatement pstmt2 = dao.getConnection().prepareStatement("SELECT DEREF(NT.HDD).CAPACITA AS HDD "
								+ "FROM TABLE( "
								+ "SELECT HDD FROM SERVER WHERE ID = \'" + s.getID() + "\')NT");
						
						ResultSet result2 = pstmt2.executeQuery();
						
						while(result2.next()) {
							s.addHdd(result2.getString("HDD"));
						}
						
						pstmt2 = dao.getConnection().prepareStatement("SELECT COUNT(DEREF(NT.VM).ID) AS NUMERO "
								+ "FROM TABLE(SELECT MACCHINEVIRTUALI FROM SERVER WHERE ID = \'" + s.getID() + "\')NT");
						
						result2 = pstmt2.executeQuery();
						result2.next();
						
						s.setNumeroVM(Integer.parseInt(result2.getString("NUMERO")));
						
						server.add(s);
						
						result2.close();
						pstmt2.close();
					}
					
					result.close();
					pstmt.close();
					dao.closeConnection();

					request.setAttribute("server", server);
					request.getRequestDispatcher("/Admin/gestioneServer.jsp").forward(request, response);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();

        			request.setAttribute("error", e.getMessage());
        			request.setAttribute("role", "A");
        			RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
        			rd.include(request, response);
				}
			}
			else {
	        	request.setAttribute("error", "Permesso negato.");
	        	request.setAttribute("role", "A");
	            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");  
	            rd.include(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
