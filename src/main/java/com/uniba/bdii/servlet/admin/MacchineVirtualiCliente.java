package com.uniba.bdii.servlet.admin;

import com.uniba.bdii.data.DAO;
import com.uniba.bdii.model.VM;

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
 * Servlet implementation class MacchineVirtualiCliente
 */
@WebServlet("/MacchineVirtualiCliente")
public class MacchineVirtualiCliente extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public MacchineVirtualiCliente() {
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

        PreparedStatement pstmt = dao.getConnection().prepareStatement("SELECT ID, "
                + "HDD, "
                + "RAM, "
                + "PROCESSORE, "
                + "DATACREAZIONE, "
                + "COSTO "
                + "FROM VMCREATA "
                + "WHERE DEREF(CLIENTE).CF = \'" + cf + "\'");

        ResultSet result = pstmt.executeQuery();

        ArrayList<VM> macchine = new ArrayList<VM>();
        while (result.next()) {
          VM vm = new VM(result.getString("ID"),
                  result.getString("HDD"),
                  result.getString("RAM"),
                  result.getString("PROCESSORE"),
                  result.getString("DATACREAZIONE"),
                  result.getString("COSTO"));

          macchine.add(vm);
        }

        result.close();
        pstmt.close();
        dao.closeConnection();

        request.setAttribute("cf", cf);
        request.setAttribute("macchine", macchine);
        request.getRequestDispatcher("/Admin/macchineVirtualiCliente.jsp").forward(request, response);

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
