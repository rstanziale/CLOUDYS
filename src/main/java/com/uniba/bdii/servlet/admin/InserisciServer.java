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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class InserisciServer
 */
@WebServlet("/InserisciServer")
public class InserisciServer extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public InserisciServer() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setCharacterEncoding("UTF-8");

    StringBuilder str = new StringBuilder();
    StringBuilder stringaQuery = new StringBuilder();

    for (String a : request.getParameterValues("processori")) {
      str.append(str.toString().concat(a));
    }

    String[] newS = str.toString().split(",");
    String modello = newS[0].substring(20);
    String core = newS[1].substring(6, newS[1].length() - 1);

    String ram = request.getParameter("ram");
    String[] hdd = request.getParameterValues("hdd");

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


        PreparedStatement pstmt = dao.getConnection().prepareStatement("insert into server values("
                + "null, "
                + "((select ref(r) from ram r where capacita = " + ram + ")), "
                + "((select ref(p) from processore p where modello=\'" + modello + "\' and core = " + core + ")), "
                + "null, "
                + "null "
                + ")");
        pstmt.executeUpdate();


        pstmt = dao.getConnection().prepareStatement("select max(id) as idserver from server");
        ResultSet result = pstmt.executeQuery();
        result.next();
        String idserver = result.getString("idserver");


        int i = 1;
        for (String a : hdd) {
          if (hdd.length == 1)
            stringaQuery.append("h.capacita = ").append(a).append(" ");
          else if (i == 1)
            stringaQuery.append("h.capacita >= ").append(a).append(" ").append(" AND ");
          else if (i == hdd.length)
            stringaQuery.append("h.capacita <= ").append(a).append(" ");
          i++;
        }

        pstmt = dao.getConnection().prepareStatement("update server "
                + "set hdd = cast(multiset(select ref(h) from hdd h where " + stringaQuery + " )as hddnt) "
                + "where id=" + idserver);

        pstmt.executeUpdate();

        result.close();
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
