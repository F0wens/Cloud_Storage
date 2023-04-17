
package com.mycompany.cloudstorage;

import java.sql.*;
        
public class DBConnection {
    Connection con = null;
    
    public static Connection ConnectionDB() {
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:FileStorageDB.db");
            System.out.println("Connection Successful!");
            return con;
        }
        catch(Exception e){
            System.out.println("Connection Failed!" + e);
            return null;
        }
        
    }
}
