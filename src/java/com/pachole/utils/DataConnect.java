package com.pachole.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsenderdb?useSSL=false", "root", "mysqlpassword");
            return con;
        } catch(Exception e) {
            System.out.println("Database.getConnection() error" + e.getMessage());
            return null;
        }
    }
    
    public static void close(Connection con){
        try{
            con.close();
        }catch(Exception e){
            
        }
    }

}
