package com.bdii.servlet.user;

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
import com.bdii.model.OS;

/**
 * Servlet implementation class NuovaMacchinaVirtuale
 */
@WebServlet("/NuovaMacchinaVirtuale")
public class NuovaMacchinaVirtuale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NuovaMacchinaVirtuale() {
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
			if(role.equals("U")) {
				try {
					DAO dao = new DAO();
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT ID, NOME, COSTO FROM OS");
					
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<OS> sistemi = new ArrayList<OS>();
					
					while(result.next()) {
						OS os = new OS(result.getString("ID"), 
								result.getString("NOME"),
								result.getString("COSTO"));
						
						sistemi.add(os);
					}
					
					request.setAttribute("sistemi", sistemi);
					result.close();
					pstmt.close();
					dao.closeConnection();
					
					request.getRequestDispatcher("/User/creaMacchinaVirtuale.jsp").forward(request, response);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();

        			request.setAttribute("error", e.getMessage());
        			request.setAttribute("role", "U");
        			RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
        			rd.include(request, response);
				}
			}
			else {
	        	request.setAttribute("error", "Errore nella fase di caricamento dei possedimenti.");
	        	request.setAttribute("role", "U");
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
