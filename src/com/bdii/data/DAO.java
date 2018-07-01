package com.bdii.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	final private String user = "admin";
	final private String pass = "gianvito";
	final private String service_name = "orcl";
	final private String host = "localhost";
	int port = 1521;
	final private Connection conn;
	
	final private String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + service_name;

	public DAO() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, user, pass);
	}
	
	public void closeConnection() throws SQLException {
		this.conn.close();
	}
	
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public String checkLogin(String email, String password) throws SQLException {
		String role = "";
		
		PreparedStatement ps = getConnection().prepareStatement("select * from users where email=\'" + email + "\' and pw=\'" + password + "\'");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			role = rs.getString("RUOLO");
		}
		
		return role;
	}
}