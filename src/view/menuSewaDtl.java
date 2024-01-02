package view;

import ctr.ctrKamar;
import ctr.ctrSewa;
import ctr.ctrTamu;
import database.dbConnection;
import ent.entSewaDtl;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * author Adam
 */
public class menuSewaDtl extends javax.swing.JPanel {
    dbConnection db = new dbConnection();
    Connection con = db.koneksiDB();
    entSewaDtl oDtl = new entSewaDtl();
    ctrSewa o = new ctrSewa();
    ctrTamu oTmu = new ctrTamu();
    ctrKamar oKmr = new ctrKamar();
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    private Object viewer;

    public menuSewaDtl() {
        initComponents();
        populateJenisKamarComboBox();
        populateNoSewaComboBox();
        judul();
        tampilData("");
        
        // Tambahkan listener pada txtLama
        txtLama.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateTotal();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateTotal();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateTotal();
        }
    });
    
    }
    
    public void bukaForm(){
        cmbNoSewa.setSelectedIndex(0);
        cmbKamar.setSelectedIndex(0);
        txtLama.setText("");
        txtBiaya.setText("");
        updateTotal();
    }
    
    public void judul() {
        Object[] judul = {
            "No Sewa", "Tgl Sewa", "Nama Tamu", "Jenkel", "Nama Kamar", "Lama", "Biaya", "Total"
        };
        tabModel = new DefaultTableModel(null, judul);
        tabelSewa.setModel(tabModel);
    }

    public void tampilData(String where) {
    try {
        st = con.createStatement();
        tabModel.getDataVector().removeAllElements();
        tabModel.fireTableDataChanged();
        
        // Mengganti query untuk mengambil data dari kedua tabel
        rs = st.executeQuery("SELECT sewa.NoSewa, sewa.TglSewa, tamu.NmTamu, tamu.JENKEL, sewadetil.NmKamar, sewadetil.Lama, sewadetil.Biaya, sewadetil.Total " +
                    "FROM sewa " +
                    "INNER JOIN tamu ON sewa.KdTamu = tamu.KdTamu " +
                    "INNER JOIN sewadetil ON sewa.NoSewa = sewadetil.NoSewa " +
                    where);

        while (rs.next()) {
            Object[] data = {
                rs.getString("NoSewa"),
                rs.getString("TglSewa"),
                rs.getString("NmTamu"),
                rs.getString("JENKEL"),
                rs.getString("NmKamar"),
                rs.getString("Lama"),
                rs.getString("Biaya"),
                rs.getString("Total"),
            };

            tabModel.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setNoSewaDetil(String pNoSewa) {
    // Gantilah 'oDtl' dengan objek sewa detil yang sesuai di kelas ctrSewaDetil
    oDtl.setNoSewa(pNoSewa);
    String[] dataSewaDetil = oDtl.getDataSewaDtl();
    
    // Ambil data kamar berdasarkan kode kamar
    oKmr.setKdKamar(dataSewaDetil[1]);
    
    // Dapatkan data kamar
    String[] dataKamar = oKmr.getDataKamar();

    // Tambahkan data sewa detil ke dalam tabel
    Object[] data = {
        dataSewaDetil[0],   // NoSewa
        dataKamar[1],       // Nama Kamar
        dataKamar[2],       // Jenis Kamar
        dataSewaDetil[3],   // Harga
        dataSewaDetil[2],   // Qty
        dataSewaDetil[3],   // Jumlah
    };

    // Tambahkan data ke dalam tabel
    tabModel.addRow(data);
}

private void setDataSewaDetilFromTable(int row) {
    // Mendapatkan data dari tabel untuk baris yang diklik
    String nosewa = tabelSewa.getValueAt(row, 0).toString();
    String nmkamar = tabelSewa.getValueAt(row, 4).toString();
    String lama = tabelSewa.getValueAt(row, 5).toString();
    String biaya = tabelSewa.getValueAt(row, 6).toString();
    String total = tabelSewa.getValueAt(row, 7).toString();

    // Mengisi data pada panelAdd
    // Gantilah 'oDtl' dengan objek sewa detil yang sesuai di kelas ctrSewaDetil
    cmbNoSewa.setSelectedItem(nosewa);
    cmbKamar.setSelectedItem(nmkamar);
    txtLama.setText(lama);
    txtBiaya.setText(biaya);
    txtTotal.setText(total);
}

    
    private void switchToPanelAdd() {
    panelMain.removeAll();
    panelMain.add(panelAdd);
    panelMain.repaint();
    panelMain.revalidate();
    }
    
    private void updateTotal() {
    // Dapatkan nilai lama dan biaya dari inputan
    String lamaStr = txtLama.getText();
    String biayaStr = txtBiaya.getText();

    // Pastikan input tidak kosong
    if (!lamaStr.isEmpty() && !biayaStr.isEmpty()) {
        try {
            // Konversi input menjadi tipe numerik (misalnya, Integer)
            int lama = Integer.parseInt(lamaStr);
            int biaya = Integer.parseInt(biayaStr);

            // Hitung total dan setel nilai pada txtTotal
            int total = lama * biaya;
            txtTotal.setText(String.valueOf(total));
        } catch (NumberFormatException ex) {
            // Tangani jika input tidak valid (bukan angka)
            ex.printStackTrace(); // Tindakan sesuai kebutuhan Anda
        }
    }
}


    private void populateJenisKamarComboBox() {
        try {
            String query = "SELECT NmKamar, Biaya FROM kamar";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                List<String> jenisKamarList = new ArrayList<>();
                while (resultSet.next()) {
                    jenisKamarList.add(resultSet.getString("NmKamar"));
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(jenisKamarList.toArray(new String[0]));
                cmbKamar.setModel(model);
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateNoSewaComboBox() {
        try {
            String query = "SELECT NoSewa FROM sewa";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                List<String> noSewaList = new ArrayList<>();
                while (resultSet.next()) {
                    noSewaList.add(resultSet.getString("NoSewa"));
                }
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(noSewaList.toArray(new String[0]));
                cmbNoSewa.setModel(model);
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void simpanSewaDtl() {
    try {
        // Dapatkan nilai kdkamar (jenis kamar)
        String selectedJenisKamar = cmbKamar.getSelectedItem().toString();
//        String kdkamar = getKdKamarFromJenis(selectedJenisKamar);

        // Gunakan nilai kdkamar untuk menyimpan data
        String query = "INSERT INTO sewadetil (NoSewa, NmKamar, Lama, Biaya, Total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, cmbNoSewa.getSelectedItem().toString());
            statement.setString(2, cmbKamar.getSelectedItem().toString());
            statement.setInt(3, Integer.parseInt(txtLama.getText()));
            statement.setInt(4, Integer.parseInt(txtBiaya.getText()));
            statement.setInt(5, Integer.parseInt(txtTotal.getText()));
            statement.executeUpdate();
            tampilData("");
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void ubahSewaDtl() {
    try {
        // Ambil data dari inputan
        String noSewa = cmbNoSewa.getSelectedItem().toString();
        String namaKamar = cmbKamar.getSelectedItem().toString();
        int lama = Integer.parseInt(txtLama.getText());
        int biaya = Integer.parseInt(txtBiaya.getText());
        int total = Integer.parseInt(txtTotal.getText());

        // Query untuk melakukan update data
        String query = "UPDATE sewadetil SET Lama = ?, Biaya = ?, Total = ? WHERE NoSewa = ? AND NmKamar = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Set parameter pada statement
            statement.setInt(1, lama);
            statement.setInt(2, biaya);
            statement.setInt(3, total);
            statement.setString(4, noSewa);
            statement.setString(5, namaKamar);

            // Eksekusi query
            statement.executeUpdate();

            // Tampilkan data setelah perubahan
            tampilData("");
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void hapusSewaDtl() {
    try {
        // Ambil data dari inputan
        String noSewa = cmbNoSewa.getSelectedItem().toString();
        String namaKamar = cmbKamar.getSelectedItem().toString();

        // Query untuk melakukan hapus data
        String query = "DELETE FROM sewadetil WHERE NoSewa = ? AND NmKamar = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Set parameter pada statement
            statement.setString(1, noSewa);
            statement.setString(2, namaKamar);

            // Eksekusi query
            statement.executeUpdate();

            // Tampilkan data setelah penghapusan
            tampilData("");
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//private String getKdKamarFromJenis(String jenisKamar) {
//    // Mendapatkan kdkamar berdasarkan jenis kamar
//    try {
//        String query = "SELECT KdKamar FROM kamar WHERE NmKamar = ?";
//        try (PreparedStatement statement = con.prepareStatement(query)) {
//            statement.setString(1, jenisKamar);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getString("KdKamar");
//            }
//        }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    
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
        btn_cetak = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLama = new javax.swing.JTextField();
        cmbKamar = new javax.swing.JComboBox();
        cmbNoSewa = new javax.swing.JComboBox();
        txtBiaya = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        panelView = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSewa = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        panelMain.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setText("TAMBAH DATA SEWADETAIL");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-lease-40.png"))); // NOI18N

        btn_simpan.setBackground(new java.awt.Color(255, 255, 255));
        btn_simpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(0, 204, 0));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-add-25.png"))); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_simpanMouseClicked(evt);
            }
        });

        btn_ubah.setBackground(new java.awt.Color(255, 255, 255));
        btn_ubah.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
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
        btn_hapus.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 0, 51));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-25.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_hapusMouseClicked(evt);
            }
        });

        btn_cetak.setBackground(new java.awt.Color(255, 255, 255));
        btn_cetak.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(153, 153, 153));
        btn_cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-document-40.png"))); // NOI18N
        btn_cetak.setText("Cetak");
        btn_cetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cetakMouseClicked(evt);
            }
        });
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
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
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_simpan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_ubah)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cetak)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setText("NoSewa  :");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Nama Kamar :");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setText("Lama Menginap       :");

        cmbKamar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKamarActionPerformed(evt);
            }
        });

        cmbNoSewa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbNoSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNoSewaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setText("Biaya       :");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel7.setText("TOTAL :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLama)
                    .addComponent(cmbKamar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBiaya)
                    .addComponent(cmbNoSewa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbNoSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLama, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel9.setText("DATA SEWADETAIL");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-lease-40.png"))); // NOI18N

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

        tabelSewa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSewaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelSewa);

        javax.swing.GroupLayout panelViewLayout = new javax.swing.GroupLayout(panelView);
        panelView.setLayout(panelViewLayout);
        panelViewLayout.setHorizontalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );
        panelViewLayout.setVerticalGroup(
            panelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelViewLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelMain.add(panelView, "card2");

        add(panelMain, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_simpanMouseClicked
        this.simpanSewaDtl();
        bukaForm();
        cmbNoSewa.requestFocus();
    }//GEN-LAST:event_btn_simpanMouseClicked

    private void btn_ubahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ubahMouseClicked
        //        // Panggil method untuk melakukan perubahan data
        ubahSewaDtl();
        // Bersihkan dan fokuskan ke inputan NoSewa setelah perubahan
        bukaForm();
        cmbNoSewa.requestFocus();
    }//GEN-LAST:event_btn_ubahMouseClicked

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        panelMain.removeAll();
        panelMain.add(panelView);
        panelMain.repaint();
        panelMain.revalidate();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusMouseClicked
        // Panggil method untuk menghapus data
            hapusSewaDtl();
            // Bersihkan dan fokuskan ke inputan NoSewa setelah penghapusan
            bukaForm();
            cmbNoSewa.requestFocus();
    }//GEN-LAST:event_btn_hapusMouseClicked

    private void btn_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tambahMouseClicked
        panelMain.removeAll();
        panelMain.add(panelAdd);
        panelMain.repaint();
        panelMain.revalidate();
    }//GEN-LAST:event_btn_tambahMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void tabelSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSewaMouseClicked
                                     
    int row = tabelSewa.rowAtPoint(evt.getPoint());
    int col = tabelSewa.columnAtPoint(evt.getPoint());
    if (row >= 0 && col >= 0) {
        // Panggil method untuk mengisi data pada panelAdd
        setDataSewaDetilFromTable(row);
        // Pindah ke panelAdd
        switchToPanelAdd();
        // Tampilkan data setelah switch ke panel add
        tampilData("");
        }
    }//GEN-LAST:event_tabelSewaMouseClicked

    private void cmbNoSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNoSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNoSewaActionPerformed

    private void cmbKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKamarActionPerformed
         // Mendapatkan biaya kamar saat jenis kamar dipilih
        String selectedJenisKamar = cmbKamar.getSelectedItem().toString();
        try {
            String query = "SELECT Biaya FROM kamar WHERE NmKamar = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setString(1, selectedJenisKamar);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Mengambil biaya kamar dan menampilkannya di txtBiaya
                    txtBiaya.setText(resultSet.getString("Biaya"));
                }
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cmbKamarActionPerformed

    private void btn_cetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cetakMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cetakMouseClicked

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        try {
        String reportPath = "src/report/report_sewakamar.jasper";
        Connection con = db.koneksiDB();
        HashMap<String, Object> parameters = new HashMap<>();
        JasperPrint print = JasperFillManager.fillReport(reportPath, parameters, con);
        JasperViewer jasperViewer = new JasperViewer(print, false);
        jasperViewer.setVisible(true);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_btn_cetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox cmbKamar;
    private javax.swing.JComboBox cmbNoSewa;
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
    private javax.swing.JTable tabelSewa;
    private javax.swing.JTextField txtBiaya;
    private javax.swing.JTextField txtLama;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

   
}
