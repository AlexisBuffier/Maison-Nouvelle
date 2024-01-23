package service;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class Connexion {
	
	public static Connection getConnection() throws Exception
	{
		try {
			String url = "jdbc:mysql://localhost:3306/restaurant";
			String username = "root";
			String password = "";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
