    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;
import ent.entKamar;
/**
 *
 * @author Adam
 */
public class ctrKamar {
    entKamar o = new entKamar();
    
    public void setKdKamar(String p){
        o.setKdKamar(p);
        o.cariKamar();
    }
    
    public String[] getDataKamar(){
        String[] vABrg = new String[4];
        vABrg[0]=o.getKdKamar();
        vABrg[1]=o.atNmKamar;
        vABrg[2]=o.atJenis;
        vABrg[3]=String.valueOf(o.atBiaya);
        return vABrg;
    }
    
    public void setDataKamar(String[] p){
        o.setKdKamar(p[0]);
        o.atNmKamar=p[1];
        o.atJenis=p[2];
        o.atBiaya=Integer.parseInt(p[3]);
    }
    
    public void simpan(){
        o.insert();
    }
    
    public void update(){
        o.update();
    }
    
    public void hapus(){
        o.delete();
    }
}
    
