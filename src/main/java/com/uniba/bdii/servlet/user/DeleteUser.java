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
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteUser() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");

    String password = request.getParameter("password");

    Cookie[] ck = request.getCookies();
    String role = "";
    String cf = "";

    for (Cookie temp : ck) {
      if (temp.getName().equals("role")) {
        role = temp.getValue();
      }
      if (temp.getName().equals("cf")) {
        cf = temp.getValue();
      }
    }

    if (role.equals("U")) {
      try {
        DAO dao = new DAO();

        PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT PW FROM USERS WHERE"
                + " DEREF(DATI_UTENTE).CF = \'" + cf + "\'");

        ResultSet result = pstmt.executeQuery();
        result.next();

        if (result.getString("PW").equals(password)) {

          pstmt = dao.getConnection().prepareStatement("DELETE FROM USERS WHERE"
                  + " DEREF(DATI_UTENTE).CF = \'" + cf + "\'");

          pstmt.executeUpdate();

          pstmt = dao.getConnection().prepareStatement("DELETE FROM CLIENTE WHERE"
                  + " CF = \'" + cf + "\'");

          pstmt.executeUpdate();

        } else {
          request.setAttribute("error", "Errore nella fase di cancellazione dell'account.");
          request.setAttribute("role", "U");
          RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
          rd.include(request, response);
        }

        result.close();
        pstmt.close();
        dao.closeConnection();

        request.getRequestDispatcher("/index.html").forward(request, response);
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();

        request.setAttribute("error", e.getMessage());
        request.setAttribute("role", "U");
        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
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
