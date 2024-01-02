/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entTamu;

/**
 *
 * @author Adam
 */
public class ctrTamu {
    private entTamu o = new entTamu();

    public void setKdTamu(String p) {
        o.setKdTamu(p);
        o.cariTamu();
    }

    public String[] getDataTamu() {
        String[] vABrg = new String[5];
        vABrg[0] = o.getKdTamu();
        vABrg[1] = o.atNmTamu;
        vABrg[2] = o.atJenkel;
        vABrg[3] = o.atPendidikan;
        vABrg[4] = o.atPekerjaan;
        return vABrg;
    }

    public void setDataTamu(String[] p) {
        o.setKdTamu(p[0]);
        o.atNmTamu = p[1];
        setJenkel(p[2]);  // Memanggil metode untuk mengatur jenis kelamin
        o.atPendidikan = p[3];
        o.atPekerjaan = p[4];
    }

    public void simpan() {
        o.insert();
    }

    public void update() {
        o.update();
    }

    public void hapus() {
        o.delete();
    }

    // Metode untuk mengatur jenis kelamin
    private void setJenkel(String jenkel) {
        if (jenkel.equalsIgnoreCase("Laki-laki") || jenkel.equalsIgnoreCase("Laki")
                || jenkel.equalsIgnoreCase("L")) {
            o.atJenkel = "Laki-laki";
        } else if (jenkel.equalsIgnoreCase("Perempuan") || jenkel.equalsIgnoreCase("P")) {
            o.atJenkel = "Perempuan";
        } else {
            // Default jika jenis kelamin tidak valid
            o.atJenkel = "Tidak valid";
        }
    }
}
