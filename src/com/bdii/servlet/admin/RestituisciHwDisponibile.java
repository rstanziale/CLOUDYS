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
import com.bdii.model.Hdd;
import com.bdii.model.Processore;
import com.bdii.model.Ram;

/**
 * Servlet implementation class RestituisciHwDisponibile
 */
@WebServlet("/RestituisciHwDisponibile")
public class RestituisciHwDisponibile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestituisciHwDisponibile() {
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
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("Select * from ram");
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<Ram> listRam = new ArrayList<Ram>();
					while(result.next()) {
						Ram ram = new Ram(result.getString("ID"),
								result.getString("CAPACITA"));
								listRam.add(ram);
					}
					
					pstmt = dao.getConnection().prepareStatement("SELECT * FROM processore");
					result = pstmt.executeQuery();
					
					ArrayList<Processore> listProc = new ArrayList<Processore>();
					while(result.next()) {
						Processore proc = new Processore(result.getString("MODELLO"),
								result.getString("CORE"));
								listProc.add(proc);
					}
					
					pstmt = dao.getConnection().prepareStatement("SELECT * FROM hdd");
					result = pstmt.executeQuery();
					
					ArrayList<Hdd> listHdd = new ArrayList<Hdd>();
					while(result.next()) {
						Hdd hdd = new Hdd(result.getString("ID"),
								result.getString("CAPACITA"));
								listHdd.add(hdd);
					}
					
					result.close();
					pstmt.close();
					dao.closeConnection();

					request.setAttribute("listRam", listRam);
					request.setAttribute("listProc", listProc);
					request.setAttribute("listHdd", listHdd);
					request.getRequestDispatcher("/Admin/InserisciServer.jsp").forward(request, response);
					
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
