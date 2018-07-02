package com.bdii.servlet.admin;

import java.io.IOException;
import java.sql.PreparedStatement;
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
 * Servlet implementation class Job
 */
@WebServlet("/Job")
public class Job extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Job() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Cookie ck[] = request.getCookies();
		String role = "";
		String state = "";


		for(Cookie temp : ck) {
			if(temp.getName().equals("role")) 
			{
				role = temp.getValue();
			}
		}
		
		if(ck != null) {
			if(role.equals("A")) {
				try {
					DAO dao = new DAO();
					PreparedStatement pstmt = null;
					String cod = request.getParameter("Cod");
					
					if(cod.equals("0"))
					{
						pstmt = dao.getConnection().prepareStatement("BEGIN "
								+ "DBMS_SCHEDULER.CREATE_JOB (job_name=>'AGGIORNA_FATTURE',program_name=>'SCHEDULER_FATTURA',"
								+ "schedule_name=>'MINUTO',enabled=>TRUE); END; ");
						 pstmt.executeUpdate();
						 
						 Cookie statejob = new Cookie("stateJob", "Attivo");
						 statejob.setMaxAge(60*60);
				         response.addCookie(statejob);
						 pstmt.close();


					}
					else if(cod.equals("1"))
					{
						pstmt = dao.getConnection().prepareStatement("BEGIN "
								+ "DBMS_SCHEDULER.DROP_JOB('AGGIORNA_FATTURE');"
								+ " END; ");
						
						 pstmt.executeUpdate();
						 Cookie statejob = new Cookie("stateJob", "Inattivo");
						 statejob.setMaxAge(60*60);
				         response.addCookie(statejob);
				         pstmt.close();

					}
					
					dao.closeConnection();
					
					for(Cookie temp : ck) {
						if(temp.getName().equals("stateJob"))
						{
							state = temp.getValue();	
						}
					}
					
					request.setAttribute("stateJob", state);
					request.getRequestDispatcher("/Admin/index.jsp").forward(request, response);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();

        			request.setAttribute("error", e.getMessage());
        			request.setAttribute("role", "A");
        			RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");  
        			rd.include(request, response);
				}
			}
			else {
	        	request.setAttribute("error", "Errore nella fase di caricamento del job.");
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
