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
public class entKamar {
    private String atKdKamar="";
    public String atNmKamar="";
    public String atJenis="";
    public int atBiaya=0;
    
    public void setKdKamar(String pKdKamar){
        this.atKdKamar=pKdKamar;
    }
    
    public String getKdKamar(){
        return this.atKdKamar;
    }
    
    public void cariKamar(){
        String vSql = "SELECT kamar.KdKamar, kamar.NmKamar, "
                    + "kamar.Jenis, kamar.Biaya FROM `kamar` "
                    + "WHERE pasien.KdKamar='"+this.atKdKamar+"'";
        dbConnection db = new dbConnection(); Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
            this.atKdKamar=rs.getString("KdKamar");
            this.atNmKamar=rs.getString("NmKamar");
            this.atJenis=rs.getString("Jenis");
            this.atBiaya = rs.getInt("Biaya");
            }else{
                this.atNmKamar = "";
                this.atJenis = "";
                this.atBiaya =0;
            }
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }                
    }
    
    public void insert() {
       
       String vSql="INSERT INTO kamar "
                 + "( kamar.KdKamar, kamar.NmKamar, "
                    + "kamar.Jenis, kamar.Biaya) VALUES "
                 + "('"+this.atKdKamar+"', '"+this.atNmKamar
                 + "', '"+this.atJenis+"','"+this.atBiaya+"')";
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
       String vSql="UPDATE kamar SET "
                 + "NmKamar='"+this.atNmKamar+"', Jenis='"+this.atJenis
                 + "', Biaya='"+this.atBiaya+"'"
                 + "WHERE kamar.KdKamar='"+this.atKdKamar+"'";
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
       String vSql="DELETE FROM kamar WHERE kamar.KdKamar='"+this.atKdKamar+"'";
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
        entKamar o = new entKamar();
        /* hapus
        o.setKdPasien("003");
        o.delete();
        */
        
        o.setKdKamar("K01");
        o.atNmKamar="Mawar";
        o.atJenis="Standard Room";
        o.atBiaya= 500000;
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