/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Praktek
 */
public class entSewa {
    private String atNoSewa ="";
    public String atTglSewa="";
    public String atKdTamu="";
    
    public void setNoSewa(String pNoSewa){
        this.atNoSewa=pNoSewa;
    }
    
    public String getNoSewa(){
        return this.atNoSewa;
    }
    
    public void cariSewa(){
        String vSql ="SELECT sewa.NoSewa, "
                   + "sewa.Nosewa, "
                   + "sewa.KdTamu "
                   + "FROM sewa "
                   + "WHERE sewa.NoSewa='"+this.atNoSewa+"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoSewa = rs.getString("NoSewa");
                this.atTglSewa = rs.getString("TglSewa");
                this.atKdTamu=rs.getString("KdTamu");                
            } else {
                this.atTglSewa="";
                this.atKdTamu="";                
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }
    
    public boolean SewaAda(){
        String vSql ="SELECT sewa.NoSewa "
                   + "FROM sewa "
                   + "WHERE sewa.NoSewa='"+this.atNoSewa+"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        boolean flag=true;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            
            flag = rs.next();
        } catch (SQLException e){
            System.out.println(e);
        }
        return flag;
    }
    
    public void insert(){
        String vSql="INSERT INTO sewa "
                +   "(sewa.NoSewa, sewa.TglSewa, sewa.KdTamu) "
                +   "VALUES ('"+this.atNoSewa+"', '"+this.atTglSewa+"','"+this.atKdTamu+"')";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);
            Stat.executeUpdate();
        } catch (SQLException e) {
            
            System.out.println("error --> "+e.toString());
        }         
    }
    
    public void update() {
       String vSql="UPDATE sewa SET "
                 + "TglSewa='"+this.atTglSewa+"', KdTamu='"+this.atKdTamu
                 + "'"
                 + "WHERE sewa.NoSewa='"+this.atNoSewa+"'";
       dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
       //System.out.println(sql);       
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
   }
    
     public void delete() {
       String vSql="DELETE FROM sewa WHERE sewa.NoSewa='"+this.atNoSewa+"'";
       dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
       //System.out.println(sql);       
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
   }
}
