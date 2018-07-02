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
import com.bdii.model.Fattura;

/**
 * Servlet implementation class Fatture
 */
@WebServlet("/Fatture")
public class Fatture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fatture() {
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
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT CODICE, DATAEMISSIONE, IMPORTO, "
							+ "DEREF(CLIENTE).CF AS CF "
							+ "FROM FATTURA");
					
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<Fattura> fatture = new ArrayList<Fattura>();
					while(result.next()) {
						Fattura f = new Fattura(result.getString("CODICE"),
								result.getString("DATAEMISSIONE"),
								result.getString("IMPORTO"));
						
						f.setProprietario(result.getString("CF"));
						fatture.add(f);
					}
					
					pstmt = dao.getConnection().prepareStatement("SELECT CF FROM CLIENTE");
					
					result = pstmt.executeQuery();
					
					ArrayList<String> clienti = new ArrayList<String>();
					while(result.next()) {
						clienti.add(result.getString("CF"));
					}
					
					result.close();
					pstmt.close();
					dao.closeConnection();

					request.setAttribute("fatture", fatture);
					request.setAttribute("clienti", clienti);
					request.getRequestDispatcher("/Admin/fatture.jsp").forward(request, response);
					
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
