/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entSewa;
import ent.entSewaDtl;
import java.util.ArrayList;

/**
 *
 * @author Praktek
 */
public class ctrSewa {
    ctrTamu oTmu = new ctrTamu();
    ctrKamar oKmr = new ctrKamar();
    
    
    entSewa o = new entSewa();
    entSewaDtl oDtl = new entSewaDtl();
    
    public void setNoSewa(String pNoSewa){
        o.setNoSewa(pNoSewa);
        oDtl.setNoSewa(pNoSewa);
        oDtl.cariSewaDtl();
        o.cariSewa();
        oTmu.setKdTamu(o.atKdTamu);
    }
    
    public String[] getDataNoSewa(){
        String[] vData = new String[3];
        vData[0] = o.getNoSewa();
        vData[1] = o.atTglSewa;
        vData[2] = o.atKdTamu;
        return vData;
    }
    
    public String[] getDataTamu(){
        return oTmu.getDataTamu();
    }
    
    public void setDataSewa(String[] p){
        o.setNoSewa(p[0]);
        o.atTglSewa=p[1];
        o.atKdTamu=p[2];
    }
    
    public void setKdTamu(String p) {
        oTmu.setKdTamu(p);
    }
    
    public void simpan(){
        o.insert();
    }
    
     public void hapus(){
        o.delete();
    }
     public void simpanSewa(){
        if(o.SewaAda()){
            o.update();
        } else {
            o.insert();
        }
    }
    
    public void setKdKamar(String pKdKamar){
        oDtl.setKdKamar(pKdKamar);
        oKmr.setKdKamar(pKdKamar);
        oDtl.cariSewaDtl();
    }
    
    public String[] getDataKamar(){
        return oKmr.getDataKamar();
    }
    
    public String[] getDataSewaDtl(){
        String[] vA = new String[4];
        vA[0] = oDtl.getNoSewa();
        vA[1] = oDtl.getKdKamar();
        vA[2] = String.valueOf(oDtl.atLama);
        vA[3] = String.valueOf(oDtl.atBiaya);
        return vA;
    }
    
    public void setDataSewaDtl(String[] p) {
    try {
        oDtl.setNoSewa(p[0]);
        oDtl.setKdKamar(p[1]);
        oDtl.atLama = Integer.parseInt(p[2]);
        oDtl.atBiaya = Integer.parseInt(p[3]);
        } catch (NumberFormatException e) {
            // Handle exception, berikan pesan kesalahan atau tanggapan sesuai kebutuhan.
        }
    }

    
    public void simpanSewaDtl(){
        if(oDtl.SewaDtlAda()){
            oDtl.update();
        } else {
            oDtl.insert();
        }
    }
    
   public String hitungJumlah(String pBiaya, String pLama) {
    int vJumlah = Integer.parseInt(pBiaya) * Integer.parseInt(pLama);
    return String.valueOf(vJumlah);
}
    /*
    m.addColumn("Kd Brg");
        m.addColumn("Nm Brg");
        m.addColumn("Satuan");
        m.addColumn("Harga");
        m.addColumn("QTY");
        m.addColumn("Jumlah");
    */
    public ArrayList<String[]> getAllDataSewaDtl(String pNoNota){
       ArrayList<String[]> vLst = new ArrayList<>();
       String[] vA;
       for(String[] v:oDtl.getAllData()){
           vA = new String[6];
           if(v[0].equals(pNoNota)){
                vA[0]=v[1];
                this.setKdKamar(v[1]);
                vA[1]=this.getDataKamar()[1];
                vA[2]=this.getDataKamar()[2];
                vA[3]=v[3];
                vA[4]=v[2];
                vA[5]=this.hitungJumlah(v[3], v[2]);
                vLst.add(vA);
           }
       }
       return vLst;
    }
    
    public String hitungTotal(String pNoSewa){
        int vTotal=0;
        for(String[] v:oDtl.getAllData()){
            if(v[0].equals(pNoSewa)){
                //System.out.println(v[2]+" = "+v[3]);
                vTotal+=Integer.parseInt(this.hitungJumlah(v[2], v[3]));
            }
        }
        return String.valueOf(vTotal);
    }
}
