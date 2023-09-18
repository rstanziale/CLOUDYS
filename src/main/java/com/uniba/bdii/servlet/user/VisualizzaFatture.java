package com.uniba.bdii.servlet.user;

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
 * Servlet implementation class VisualizzaFatture
 */
@WebServlet("/VisualizzaFatture")
public class VisualizzaFatture extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public VisualizzaFatture() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        ArrayList<Fattura> fatture = new ArrayList<Fattura>();

        PreparedStatement pstmt = dao.getConnection().prepareStatement("select  codice, dataemissione, importo "
                + "from fattura "
                + "where deref(cliente).cf = \'" + cf + "\'");
        ResultSet result = pstmt.executeQuery();

        while (result.next()) {
          Fattura s = new Fattura(result.getString("codice"),
                  result.getString("dataemissione"),
                  result.getString("importo"));
          fatture.add(s);
        }


        ArrayList<String> macchinevirtualicreate = new ArrayList<String>();
        PreparedStatement pstmt2 = dao.getConnection().prepareStatement("select deref(NT.vm).id as id "
                + "from table ( select vm from cliente where cf = \'" + cf + "\')NT "
                + "where deref(NT.vm).id != 0");

        ResultSet result2 = pstmt2.executeQuery();

        while (result2.next()) {
          macchinevirtualicreate.add(result2.getString("id"));

        }

        request.setAttribute("fatture", fatture);
        request.setAttribute("macchinevirtualicreate", macchinevirtualicreate.toString());
        result.close();
        pstmt.close();
        dao.closeConnection();

        request.getRequestDispatcher("/User/gestionefatture.jsp").forward(request, response);

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();

        request.setAttribute("error", e.getMessage());
        request.setAttribute("role", "A");
        RequestDispatcher rd = request.getRequestDispatcher("./error.jsp");
        rd.include(request, response);
      }
    } else {
      request.setAttribute("error", "Errore nella fase di visualizzazione delle fatture.");
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
