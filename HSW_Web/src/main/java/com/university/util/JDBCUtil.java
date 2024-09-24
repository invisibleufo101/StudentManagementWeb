package com.university.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
	
	/**
	 * DriverManager 객체를 이용하여 H2 데이터베이스에 연결을 합니다.
	 * 
	 * 연결을 실패할 경우 null값을 반환합니다.
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		
		try {	
			DriverManager.registerDriver(new org.h2.Driver());
			
			String jdbcUrl = "jdbc:h2:tcp://localhost/~/HSW_Web;CASE_INSENSITIVE_IDENTIFIERS=TRUE";
			String userName = "sa";
			String password = "1234";
			conn = DriverManager.getConnection(jdbcUrl, userName, password);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	/**
	 * H2 데이터베이스에 연결을 끊습니다.
	 * PreparedStatement, Connection 객체들이 null 상태인지 먼저 확인한 후 연결을 끊습니다.
	 * 
	 * @param pstmt
	 * @param conn
	 */
	public static void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null) {				
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (conn != null) {				
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * H2 데이터베이스에 연결을 끊습니다. QueryBuilder에서 get(), getAll() 메소드를 위한 연결을 끊는 방식입니다.
	 * 각 ResultSet, PreparedStatement, Connection 객체들이 null 상태인지 먼저 확인한 후 연결을 끊습니다.
	 * 
	 * @param pstmt
	 * @param conn
	 */
	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null) {				
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (pstmt != null) {				
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (conn != null) {				
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
