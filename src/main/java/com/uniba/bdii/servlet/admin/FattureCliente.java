package com.uniba.bdii.servlet.admin;

import com.uniba.bdii.data.DAO;
import com.uniba.bdii.model.Fattura;

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
 * Servlet implementation class FattureCliente
 */
@WebServlet("/FattureCliente")
public class FattureCliente extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public FattureCliente() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");

    String cf = request.getParameter("clienti");

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

        PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT CODICE, DATAEMISSIONE, IMPORTO "
                + "FROM FATTURA "
                + "WHERE DEREF(CLIENTE).CF = \'" + cf + "\'");

        ResultSet result = pstmt.executeQuery();

        ArrayList<Fattura> fatture = new ArrayList<Fattura>();
        while (result.next()) {
          Fattura f = new Fattura(result.getString("CODICE"),
                  result.getString("DATAEMISSIONE"),
                  result.getString("IMPORTO"));

          fatture.add(f);
        }

        result.close();
        pstmt.close();
        dao.closeConnection();

        request.setAttribute("cf", cf);
        request.setAttribute("fatture", fatture);
        request.getRequestDispatcher("/Admin/fattureCliente.jsp").forward(request, response);

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
