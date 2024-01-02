/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class dbConnection {        
   private Connection conn;
    
   public Connection koneksiDB() {
      if(this.conn==null) {
         try{  
            Class.forName("com.mysql.jdbc.Driver");  
            String vDriver = "jdbc:mysql://localhost/db_hotel";
            this.conn=DriverManager.getConnection(vDriver,"root","");  
//            JOptionPane.showMessageDialog(null, "Koneksi sukses");
         }catch(ClassNotFoundException e){ 
            System.out.println(e);} catch (SQLException e) {  
            JOptionPane.showMessageDialog(null, "Koneksi Gagal");                 
         }  
      }  
      return conn;
    }  
    
    public static void main(String[] args){
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();                
    }

    public Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}