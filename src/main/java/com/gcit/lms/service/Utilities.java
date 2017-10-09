package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilities {

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library?useSSL=false";
	private String user = "root";
	private String password = "12345";

	public Connection getConnection()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}
}
