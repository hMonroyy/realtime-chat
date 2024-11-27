package classes.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBDModel {

	public static final String CONEXIOCLASE = "jdbc:mysql://192.168.119.26/chat";
	public static final String CONEXIOTONI = "jdbc:mysql://192.168.119.0/chat";
	public static final String CONEXIOLOCAL = "jdbc:mysql://localhost:3306/chat";
	public static final String USER = "appuser";
	public static final String PASSWORD = "TiC.123456";
	
	public static Connection connectToBDLocal() throws SQLException {
		Connection con = DriverManager.getConnection(CONEXIOLOCAL, USER, PASSWORD);
		return con;
	}
	
	public static Connection connectToBDClase() throws SQLException {
		Connection con = DriverManager.getConnection(CONEXIOCLASE, USER, PASSWORD);
		return con;
	}
	
	public static Connection connectToBDToni() throws SQLException {
		Connection con = DriverManager.getConnection(CONEXIOTONI, USER, PASSWORD);
		return con;
	}
	
	public static Connection connectToBDPersonalizado(String url) {
	    try {
	    	String urlBD = "jdbc:mysql://" + url + "/chat";
	    	System.out.println(urlBD);
	        Connection con =  DriverManager.getConnection(urlBD, USER, PASSWORD);
	        return con;
	    } catch (SQLException e) {
	        return null;
	    }
	}
	
	public static void disconnectFromBD(Connection con) throws SQLException {
		con.close();
	}
	
}
