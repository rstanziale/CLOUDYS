package com.uniba.bdii.servlet.admin;

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
import java.sql.SQLException;

/**
 * Servlet implementation class CancellaServer
 */
@WebServlet("/CancellaServer")
public class CancellaServer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CancellaServer() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");

    String server = request.getParameter("server");

    Cookie[] ck = request.getCookies();
    String role = "";

    for (Cookie temp : ck) {
      if (temp.getName().equals("role")) {
        role = temp.getValue();
      }
    }

    if (role.equals("A")) {
      try {
        DAO dao = new DAO();

        PreparedStatement pstmt = dao.getConnection().prepareStatement("DELETE FROM SERVER "
                + "WHERE ID = \'" + server + "\'");

        pstmt.executeUpdate();

        pstmt.close();
        dao.closeConnection();

        request.getRequestDispatcher("/GestioneServer").forward(request, response);

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();

        request.setAttribute("error", e.getMessage());
        request.setAttribute("role", "A");
        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
        rd.include(request, response);
      }
    } else {
      request.setAttribute("error", "Permesso negato.");
      request.setAttribute("role", "A");
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
