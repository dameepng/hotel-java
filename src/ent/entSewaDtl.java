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
import java.util.ArrayList;

/**
 *
 * @author Praktek
 */
public class entSewaDtl {
    private String atNoSewa="";
    private String atKdKamar="";
    public int atLama=0;
    public int atBiaya=0;
    
    public void setNoSewa(String pNoSewa){
        this.atNoSewa=pNoSewa;
    }
    
    public String getNoSewa(){
        return this.atNoSewa;
    }
    
    public void setKdKamar(String pKdKamar){
        this.atKdKamar=pKdKamar;
    }
    
    public String getKdKamar(){
        return this.atKdKamar;
    }
    
    public void cariSewaDtl(){
        String vSql ="SELECT sewadetil.NoSewa, sewadetil.KdKamar, sewadetil.Lama, sewadetil.Biaya "
                   + "FROM sewadetil "
                   + "WHERE sewadetil.NoSewa='"+this.atNoSewa+"' AND sewadetil.KdKamar='"+this.atKdKamar+"'";
        
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoSewa = rs.getString("NoNota");
                this.atKdKamar = rs.getString("KdBrg");
                this.atLama=rs.getInt("Qty");
                this.atBiaya = rs.getInt("Harga");                               
            } else {
                this.atBiaya=0;
                this.atLama=0;
            }
        } catch (SQLException e){
            System.out.println(e);
        }
    }
    
    public void insert(){
        String vSql="INSERT INTO sewadetil "
                  + "(sewadetil.NoSewa, sewadetil.KdKamar, sewadetil.Lama, sewadetil.Biaya) "
                  + "VALUES ('"+this.atNoSewa+"','"+this.atKdKamar+"','"+this.atLama+"','"+this.atBiaya+"')";
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
    
    public void update(){
        String vSql = "UPDATE sewadetil "
                    + "SET sewadetil.Lama='"+this.atLama+"', sewadetil.Biaya='"+this.atBiaya+"' "
                    + "WHERE sewadetil.NoSewa='"+this.atNoSewa+"' AND sewadetil.KdKamar='"+this.atKdKamar+"'";
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
    
    public void delete(){
        String vSql = "DELETE FROM sewadetil "
                    + "WHERE sewadetil.NoSewa='"+this.atNoSewa+"' AND sewadetil.KdKamar='"+this.atKdKamar+"'";
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
    
    public boolean SewaDtlAda(){
        String vSql ="SELECT sewadetil.NoSewa "
                   + "FROM sewadetil "
                   + "WHERE sewadetil.NoSewa='"+this.atNoSewa+"' AND sewadetil.KdKamar='"+this.atKdKamar+"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        boolean flag=true;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            
            flag = rs.next();
        } catch (SQLException e){
            System.out.println(e+"  yudi ");
        }
        return flag;
    }
    
    public ArrayList<String[]> getAllData(){
        ArrayList<String[]> vLst = new ArrayList<>();
        String vSql ="SELECT sewadetil.NoSewa, sewadetil.KdKamar, sewadetil.Lama, sewadetil.Biaya "
                   + "FROM sewadetil ";
        
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            String[] vA;
            while (rs.next()){
                vA = new String[4];
                vA[0] = rs.getString("NoSewa");
                vA[1] = rs.getString("KdKamar");
                vA[2] = String.valueOf(rs.getInt("Lama"));
                vA[3] = String.valueOf(rs.getInt("Biaya"));                               
                vLst.add(vA);
            } 
        } catch (SQLException e){
            System.out.println(e);
        }
        return vLst;
    }

    public String[] setDataSewaDtl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String[] getDataSewaDtl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
