package com.uniba.bdii.servlet.user;

import com.uniba.bdii.data.DAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class InstallaApp
 */
@WebServlet("/InstallaApp")
public class InstallaApp extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public InstallaApp() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Cookie[] ck = request.getCookies();
    String role = "";

    for (Cookie temp : ck) {
      if (temp.getName().equals("role")) {
        role = temp.getValue();
      }
    }

    if (role.equals("U")) {
      try {
        DAO dao = new DAO();


        PreparedStatement pstmt = dao.getConnection().prepareStatement("select max(id) as idvm from vmcreata");
        ResultSet result = pstmt.executeQuery();
        result.next();

        String idvm = result.getString("idvm");
        String[] app = request.getParameterValues("app");

        for (String a : app) {
          pstmt = dao.getConnection().prepareStatement("begin "
                  + "aggiungi_applicazione_vm(\'" + a + "\', "
                  + idvm + ");"
                  + " end;");
          pstmt.executeUpdate();
        }

        result.close();
        pstmt.close();
        dao.closeConnection();

        request.getRequestDispatcher("/GestioneMacchineVirtuali").forward(request, response);

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();

        request.setAttribute("error", e.getMessage());
        request.setAttribute("role", "U");
        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
        rd.include(request, response);
      }
    } else {
      request.setAttribute("error", "Errore nella fase di inserimento delle app nella macchina virtuale.");
      request.setAttribute("role", "U");
      RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
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
