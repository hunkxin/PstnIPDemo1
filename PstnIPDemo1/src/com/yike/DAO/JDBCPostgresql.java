package com.yike.DAO;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCPostgresql {
	private Connection con;
	public Connection getconnect() {
		
		//System.out.println("-------- PostgreSQL "
	                    //+ "JDBC Connection Testing ------------");
	
	   try {
	
            Class.forName("org.postgresql.Driver");
	
	   } catch (ClassNotFoundException e) {
	
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                            + "Include in your library path!");
            e.printStackTrace();
            return null;
	
	    }
	
	   System.out.println("PostgreSQL JDBC Driver Registered!");
	
//	   Connection connection = null;
	
	   try {
//		   	con = DriverManager.getConnection(
//                            "jdbc:postgresql://192.168.99.177:5432/freeswitch_cdr", "freeswitch",
//                            "123");
		   	con = DriverManager.getConnection(
                    "jdbc:postgresql://139.196.9.180:5432/fswcdr", "fsw",
                    "fsw");
	   } catch (SQLException e) {
	
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
	
	    }
	
	   if (con != null) {
	        //System.out.println("You made it, take control your database now!");
	   } else {
	        System.out.println("Failed to make connection!");
	        return null;
	   }
	   return con;
	}
}
