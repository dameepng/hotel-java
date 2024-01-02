/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

/**
 *
 * @author Adam
 */
import database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
public class entTamu {
    private String atKdTamu="";
    public String atNmTamu="";
    public String atJenkel="";
    public String atPendidikan="";
    public String atPekerjaan="";
    
    public void setKdTamu(String pKdTamu){
        this.atKdTamu=pKdTamu;
    }
    
     public void setJenkel(String jenkel) {
        this.atJenkel = jenkel;
    }
    
    public String getKdTamu(){
        return this.atKdTamu;
    }
    
    public void cariTamu(){
        String vSql = "SELECT tamu.KdTamu, tamu.NmTamu, "
                    + "tamu.Jenkel, tamu.Pendidikan, tamu.Pekerjaan FROM `tamu` "
                    + "WHERE tamu.KdTamu='"+this.atKdTamu+"'";
        dbConnection db = new dbConnection(); Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
            this.atKdTamu=rs.getString("KdTamu");
            this.atNmTamu=rs.getString("NmTamu");
            this.atJenkel=rs.getString("Jenkel");
            this.atPendidikan=rs.getString("Pendidikan");
            this.atPekerjaan=rs.getString("Pekerjaan");
            }else{
                this.atNmTamu = "";
                this.atJenkel = "";
                this.atPendidikan = "";
                this.atPekerjaan = "";
            }
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }                
    }
    
    public void insert() {
       
       String vSql="INSERT INTO tamu "
                 + "( tamu.KdTamu, tamu.NmTamu, "
                    + "tamu.Jenkel, tamu.Pendidikan, tamu.Pekerjaan) VALUES "
                 + "('"+this.atKdTamu+"', '"+this.atNmTamu
                 + "', '"+this.atJenkel+"','"+this.atPendidikan+"','"+this.atPekerjaan+"')";
        //System.out.println(sql);   
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);           
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }  
    
    public void update() {
       String vSql="UPDATE tamu SET "
                 + "NmTamu='"+this.atNmTamu+"', Jenkel='"+this.atJenkel
                 + "', Pendidikan='"+this.atPendidikan+"', Pekerjaan='"+this.atPekerjaan+"'"
                 + "WHERE tamu.KdTamu='"+this.atKdTamu+"'";
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
       String vSql="DELETE FROM tamu WHERE tamu.KdTamu='"+this.atKdTamu+"'";
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

   
    public static void main(String[] args){
        entTamu o = new entTamu();
        /* hapus
        o.setKdPasien("003");
        o.delete();
        */
        
        o.setKdTamu("001");
        o.atNmTamu="Tania";
        o.atJenkel="P";
        o.atPendidikan="S1";
        o.atPekerjaan="PNS";
        o.insert();
        
        
       
//        o.setKdPasien0240("001");
//        o.atNmPasien0240="Tania";
//        o.atAlamat0240="Lengkong";
//        o.atNoTelp0240="0896";
//        o.atNamaAyah0240="0896";
//        o.insert();
//        
               
//        o.setKdPasien("002");
//        o.cariPasien();       
//        System.out.println("Kd Pasien : "+o.getKdPasien0240());
//        System.out.println("Nama Pasien : "+o.atNmPasien0240);
//        System.out.println("Alamat : "+o.atAlamat0240);
//        System.out.println("Telp :"+o.atNoTelp0240);
//        System.out.println("Nama Ayah :"+o.atNamaAyah0240);
//        
    }
}