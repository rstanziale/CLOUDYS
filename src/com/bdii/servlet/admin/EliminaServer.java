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

/**
 * Servlet implementation class EliminaServer
 */
@WebServlet("/EliminaServer")
public class EliminaServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaServer() {
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
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT ID "
							+ "FROM SERVER");
					
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<String> server = new ArrayList<String>();
					while(result.next()) {
						server.add(result.getString("ID"));
					}
					
					result.close();
					pstmt.close();
					dao.closeConnection();

					request.setAttribute("server", server);
					request.getRequestDispatcher("/Admin/eliminaServer.jsp").forward(request, response);
					
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
