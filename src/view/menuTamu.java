/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ctr.ctrTamu;
import database.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adam
 */
public class menuTamu extends javax.swing.JPanel {
    private ctrTamu o = new ctrTamu();
    private DefaultTableModel tabModel;
    private dbConnection db = new dbConnection();
    private Connection con = db.koneksiDB();

    /**
     * Creates new form menuTamu
     */
    public menuTamu() {
        initComponents();
        judul();
        tampilData("");
    }

    public void judul() {
        Object[] judul = {
            "Kd Tamu", "Nama Tamu", "Jenis Kelamin", "Pendidikan", "Pekerjaan"
        };
        tabModel = new DefaultTableModel(null, judul);
        tabeltamu.setModel(tabModel);
    }

    public void tampilData(String where) {
        try {
            Statement st = con.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            ResultSet rs = st.executeQuery("SELECT * FROM tamu " + where);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("KdTamu"),
                    rs.getString("NmTamu"),
                    rs.getString("Jenkel"),
                    rs.getString("Pendidikan"),
                    rs.getString("Pekerjaan"),
                };

                tabModel.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bukaForm() {
        txtKdTamu.setText("");
        txtNmTamu.setText("");
        rblaki.setSelected(false);
        rbpr.setSelected(false);
        txtPendidikan.setText("");
        txtPekerjaan.setText("");
    }

    public void setKdTamu(String p) {
        o.setKdTamu(p);
        txtKdTamu.setText(p);
        txtNmTamu.setText(o.getDataTamu()[1]);

        // Set radio button jenis kelamin
        String jenisKelamin = o.getDataTamu()[2];
        if (jenisKelamin.equalsIgnoreCase("Laki-laki")) {
            rblaki.setSelected(true);
            rbpr.setSelected(false);
        } else if (jenisKelamin.equalsIgnoreCase("Perempuan")) {
            rblaki.setSelected(false);
            rbpr.setSelected(true);
        }

        txtPendidikan.setText(o.getDataTamu()[3]);
        txtPekerjaan.setText(o.getDataTamu()[4]);
    }

   private void setDataFromTable(int row) {
    // Mendapatkan data dari tabel untuk baris yang diklik
    String KdTamu = tabeltamu.getValueAt(row, 0).toString();
    String NmTamu = tabeltamu.getValueAt(row, 1).toString();
    String Jenkel = tabeltamu.getValueAt(row, 2).toString();  // Ambil jenis kelamin

    // Mengisi data pada panelAdd
    txtKdTamu.setText(KdTamu);
    txtNmTamu.setText(NmTamu);

    // Set radio button jenis kelamin
    if (Jenkel.equalsIgnoreCase("Laki-laki")) {
        rblaki.setSelected(true);
        rbpr.setSelected(false);
    } else if (Jenkel.equalsIgnoreCase("Perempuan")) {
        rblaki.setSelected(false);
        rbpr.setSelected(true);
    }

    String Pendidikan = tabeltamu.getValueAt(row, 3).toString();
    String Pekerjaan = tabeltamu.getValueAt(row, 4).toString();

    txtPendidikan.setText(Pendidikan);
    txtPekerjaan.setText(Pekerjaan);
    }


    private void switchToPanelAdd() {
        panelMain.removeAll();
        panelMain.add(panelAdd);
        panelMain.repaint();
        panelMain.revalidate();
    }

    public void Simpan() {
        String vAPlg[] = new String[5];
        vAPlg[0] = txtKdTamu.getText();
        vAPlg[1] = txtNmTamu.getText();
        
        if (rblaki.isSelected()) {
            vAPlg[2] = "Laki-laki";
        } else if (rbpr.isSelected()) {
            vAPlg[2] = "Perempuan";
        }
        
        vAPlg[3] = txtPendidikan.getText();
        vAPlg[4] = txtPekerjaan.getText();
        
        o.setDataTamu(vAPlg);
        o.simpan();
        
        // Memanggil kembali method tampilData untuk memperbarui tabel
        tampilData("");
    }

    public void Update() {
        String vAPlg[] = new String[5];
        vAPlg[0] = txtKdTamu.getText();
        vAPlg[1] = txtNmTamu.getText();
        
        if (rblaki.isSelected()) {
            vAPlg[2] = "Laki-laki";
        } else if (rbpr.isSelected()) {
            vAPlg[2] = "Perempuan";
        }
        
        vAPlg[3] = txtPendidikan.getText();
        vAPlg[4] = txtPekerjaan.getText();
        
        o.setDataTamu(vAPlg);
        o.update();
        
        javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
        
        // Memanggil kembali method tampilData untuk memperbarui tabel
        tampilData("");
    }

    public void Hapus() {
        String vAPlg[] = new String[5];
        vAPlg[0] = txtKdTamu.getText();
        vAPlg[1] = txtNmTamu.getText();
        
        if (rblaki.isSelected()) {
            vAPlg[2] = "Laki-laki";
        } else if (rbpr.isSelected()) {
            vAPlg[2] = "Perempuan";
        }
        
        vAPlg[3] = txtPendidikan.getText();
        vAPlg[4] = txtPekerjaan.getText();
        
        o.setDataTamu(vAPlg);
        o.hapus();
        
        javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
        
        // Memanggil kembali method tampilData untuk memperbarui tabel
        tampilData("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        panelAdd = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPendidikan = new javax.swing.JTextField();
        txtKdTamu = new javax.swing.JTextField();
        txtNmTamu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPekerjaan = new javax.swing.JTextField();
        rblaki = new javax.swing.JRadioButton();
        rbpr = new javax.swing.JRadioButton();
        panelView = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeltamu = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        panelMain.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setText("TAMBAH DATA TAMU");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-account-40.png"))); // NOI18N

        btn_simpan.setBackground(new java.awt.Color(255, 255, 255));
        btn_simpan.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(0, 204, 0));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-add-25.png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_simpanMouseClicked(evt);
            }
        });

        btn_ubah.setBackground(new java.awt.Color(255, 255, 255));
        btn_ubah.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 153, 0));
        btn_ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-edit-25.png"))); // NOI18N
        btn_ubah.setText("Ubah");
        btn_ubah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ubahMouseClicked(evt);
            }
        });

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(255, 255, 255));
        btn_hapus.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 0, 51));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-25.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_hapusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_simpan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_ubah)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
                        .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setText("Kd Tamu  :");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Nama Tamu :");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setText("Pendidikan        :");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("Jenis Kelamin    :");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setText("Pekerjaan        :");

        rblaki.setText("Laki-laki");

        rbpr.setText("Perempuan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKdTamu)
                    .addComponent(txtNmTamu)
                    .addComponent(txtPendidikan)
                    .addComponent(txtPekerjaan)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rblaki)
                                .addGap(18, 18, 18)
                                .addComponent(rbpr))
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtKdTamu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNmTamu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rblaki)
                    .addComponent(rbpr))
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelAddLayout = new javax.swing.GroupLayout(panelAdd);
        panelAdd.setLayout(panelAddLayout);
        panelAddLayout.setHorizontalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelAddLayout.setVerticalGroup(
            panelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMain.add(panelAdd, "card2");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel9.setText("TAMBAH DATA TAMU");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-account-40.png"))); // NOI18N

        btn_tambah.setText("Tambah");
        btn_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_tambahMouseClicked(evt);
            }
        });

        jButton11.setText("Cari");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        tabeltamu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeltamu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltamuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeltamu);

        javax.swing.GroupLayout panelViewLayout = new javax.swing.GroupLayout(panelView);
        panelView.setLayout(panelViewLayout);
        panelViewLayout.setHorizontalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );
        panelViewLayout.setVerticalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
        );

        panelMain.add(panelView, "card2");

        add(panelMain, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        panelMain.removeAll();
        panelMain.add(panelView);
        panelMain.repaint();
        panelMain.revalidate();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void btn_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_simpanMouseClicked
        this.Simpan();
        bukaForm();
        txtKdTamu.requestFocus();
    }//GEN-LAST:event_btn_simpanMouseClicked

    private void btn_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusMouseClicked
        this.Hapus();
        bukaForm();
        txtKdTamu.requestFocus();
    }//GEN-LAST:event_btn_hapusMouseClicked

    private void btn_ubahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseClicked
        this.Update();
        bukaForm();
        txtKdTamu.requestFocus();
    }//GEN-LAST:event_btn_ubahMouseClicked

    private void tabeltamuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltamuMouseClicked
        int row = tabeltamu.rowAtPoint(evt.getPoint());
        int col = tabeltamu.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            // Panggil method untuk mengisi data pada panelAdd
            setDataFromTable(row);
            // Pindah ke panelAdd
            switchToPanelAdd();
        }
    }//GEN-LAST:event_tabeltamuMouseClicked

    private void btn_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahMouseClicked
        panelMain.removeAll();
        panelMain.add(panelAdd);
        panelMain.repaint();
        panelMain.revalidate();
    }//GEN-LAST:event_btn_tambahMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAdd;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelView;
    private javax.swing.JRadioButton rblaki;
    private javax.swing.JRadioButton rbpr;
    private javax.swing.JTable tabeltamu;
    private javax.swing.JTextField txtKdTamu;
    private javax.swing.JTextField txtNmTamu;
    private javax.swing.JTextField txtPekerjaan;
    private javax.swing.JTextField txtPendidikan;
    // End of variables declaration//GEN-END:variables
}
