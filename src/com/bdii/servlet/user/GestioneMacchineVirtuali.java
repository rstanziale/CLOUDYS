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
import com.bdii.model.VM;

/**
 * Servlet implementation class GestioneMacchineVirtuali
 */
@WebServlet("/GestioneMacchineVirtuali")
public class GestioneMacchineVirtuali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestioneMacchineVirtuali() {
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
					
					PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT DEREF(NT.VM).ID AS ID, "
							+ "DEREF(NT.VM).HDD AS HDD, "
							+ "DEREF(NT.VM).RAM AS RAM, "
							+ "DEREF(NT.VM).PROCESSORE AS PROCESSORE, "
							+ "DEREF(NT.VM).DATACREAZIONE AS DATACREAZIONE, "
							+ "DEREF(NT.VM).COSTO AS COSTO "
							+ "FROM TABLE(SELECT VM FROM CLIENTE WHERE CF = \'" + cf + "\')NT "
							+ "where deref(NT.vm).id != 0");
					
					ResultSet result = pstmt.executeQuery();
					
					ArrayList<VM> macchine = new ArrayList<VM>(); 
					
					while(result.next()) {
						String ID = result.getString("ID");
						
						VM vm = new VM(ID,
								result.getString("HDD"),
								result.getString("RAM"),
								result.getString("PROCESSORE"),
								result.getString("DATACREAZIONE"),
								result.getString("COSTO"));
						
						PreparedStatement pstmt2 = dao.getConnection().prepareStatement("select deref(NT.software).nome as nome "
						 		+ "from table "
						 		+ "(select software from vmcreata where ID = "+ ID +")NT ");
						
						ResultSet result2 = pstmt2.executeQuery();
						result2.next();
						
						
							vm.setOs(result2.getString("NOME"));	
				
						
						while(result2.next()) {
							vm.addApp(result2.getString("NOME"));
						}
						
						macchine.add(vm);
						
						result2.close();
						pstmt2.close();
					}
					
					request.setAttribute("macchine", macchine);
					result.close();
					pstmt.close();
					dao.closeConnection();
					
					request.getRequestDispatcher("/User/gestioneMacchineVirtuali.jsp").forward(request, response);
					
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
