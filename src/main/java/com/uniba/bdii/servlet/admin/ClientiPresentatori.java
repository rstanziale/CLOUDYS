package com.uniba.bdii.servlet.admin;

import com.uniba.bdii.data.DAO;
import com.uniba.bdii.model.Cliente;

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
import java.util.ArrayList;

/**
 * Servlet implementation class ClientiPresentatori
 */
@WebServlet("/ClientiPresentatori")
public class ClientiPresentatori extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ClientiPresentatori() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");

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

        PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT CF, NOME, COGNOME, EMAIL, BONUS "
                + "FROM CLIENTE "
                + "WHERE BONUS > 0 "
                + "ORDER BY BONUS");

        ResultSet result = pstmt.executeQuery();

        ArrayList<Cliente> clienti = new ArrayList<Cliente>();
        while (result.next()) {
          Cliente c = new Cliente(result.getString("CF"),
                  result.getString("NOME"),
                  result.getString("COGNOME"),
                  result.getString("EMAIL"),
                  result.getString("BONUS"));

          clienti.add(c);
        }

        result.close();
        pstmt.close();
        dao.closeConnection();

        request.setAttribute("clienti", clienti);
        request.getRequestDispatcher("/Admin/clientiPresentatori.jsp").forward(request, response);

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
