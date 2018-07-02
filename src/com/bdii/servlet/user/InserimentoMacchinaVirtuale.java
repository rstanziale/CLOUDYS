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
import com.bdii.model.APP;

/**
 * Servlet implementation class InserimentoMacchinaVirtuale
 */
@WebServlet("/InserimentoMacchinaVirtuale")
public class InserimentoMacchinaVirtuale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserimentoMacchinaVirtuale() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Cookie ck[] = request.getCookies();
		String role = "";
		String cf = "";

		for(Cookie temp : ck) {
			if(temp.getName().equals("role")) {
				role = temp.getValue();
			}
			if(temp.getName().equals("cf")) {
				cf = temp.getValue();
			}
		}
		
		if(ck != null) {
			if(role.equals("U")) {
				try {
					DAO dao = new DAO();
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("BEGIN"
							+ " CREA_VM(" + request.getParameter("hdd") + ", "
									+ request.getParameter("ram") + ", "
									+ request.getParameter("processore") + ", "
									+ "\'" + request.getParameter("sistemi") + "\',"
									+ "\'" + cf + "\');"
							+ " END;");
					
					pstmt.executeUpdate();
					
					pstmt = dao.getConnection().prepareStatement("select deref(NT.app).nome as nome, "
							+ "deref(NT.app).id as id, "
							+ "deref(NT.app).licenza as licenza, "
							+ "deref(NT.app).versione as versione, "
							+ "deref(NT.app).costo as costo from "
							+ "table (select app from os where nome = \'" + request.getParameter("sistemi") + "\')NT "
							+ "WHERE deref(NT.APP).ID !=0");
					
					ResultSet result = pstmt.executeQuery();
					ArrayList<APP> apps = new ArrayList<APP>();
					
					while(result.next()) {
						APP a = new APP(result.getString("id"), 
								result.getString("nome"),
								result.getString("costo"),
								result.getString("licenza"),
								result.getString("versione"));
						
						apps.add(a);
					}
					
					request.setAttribute("apps", apps);
					result.close();
					pstmt.close();
					dao.closeConnection();
					
					request.getRequestDispatcher("/User/selezioneApp.jsp").forward(request, response);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();

        			request.setAttribute("error", e.getMessage());
        			request.setAttribute("role", "U");
        			RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
        			rd.include(request, response);
				}
			}
			else {
	        	request.setAttribute("error", "Errore nella fase di inserimento della macchina virtuale.");
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
