package View;

import DAO.DAODetailTransaksi;
import DAO.DAOShipment;
import DAO.DAOStok;
import DAO.DAOTransaksi;
import DAO.DAOUser;
import Koneksi.DBConnection;
import Method.PDF;
import Method.Receipt;
import Model.ModelDetailTransaksi;
import Model.ModelShipment;
import Model.ModelStok;
import Model.ModelTransaksi;
import Model.ModelUser;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaRootPaneUI;
import de.javasoft.plaf.synthetica.SyntheticaTitlePane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import sun.applet.Main;


public class Fitur extends javax.swing.JFrame {
    private DAOStok st = new DAOStok(new DBConnection().getConnection());
    private DAOTransaksi t = new DAOTransaksi(new DBConnection().getConnection());
    private DAOUser u = new DAOUser(new DBConnection().getConnection());
    DBConnection a = new DBConnection();
    
    Receipt r = new Receipt();
    
    final JDialog formTambahStok = new JDialog(this, "", true);
    final JDialog formEditStok = new JDialog(this, "", true);
    
    String Admin = "";
    ArrayList daftarhapus = new ArrayList();
    
    public Fitur() {
        this.setName("MainFrame");
        this.getRootPane().updateUI();
        initComponents();
        setLocationRelativeTo(null);
        JButton b = (JButton)SyntheticaLookAndFeel.findComponent("RootPane.titlePane.menuButton", this);
        ((SyntheticaTitlePane)((SyntheticaRootPaneUI)getRootPane().getUI()).getTitlePane()).setUserComponent(ToolbarPanel);
        
        ToolbarPanel.add(Box.createHorizontalGlue());
        ToolbarPanel.add(btnSettingFitur);
        btnSettingFitur.setVisible(false);
        ToolbarPanel.add(btnLogoutFitur);
        
        JRootPane root = this.getRootPane();
        SyntheticaLookAndFeel.findComponent("RootPane.titlePane.menuButton", root).setVisible(false);
        
        SplitPanelTransaksi.setOneTouchExpandable(false);
        SplitPanelStok.setOneTouchExpandable(false);
        
        txtPcsTransaksi.addTrailingComponent(btnTambahBarangTransaksi);
        NumberFormatter pcsdefaultFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        pcsdefaultFormatter.setValueClass(Double.class);
        NumberFormatter pcsdisplayFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        pcsdisplayFormatter.setValueClass(Double.class);
        NumberFormatter pcseditFormatter = new NumberFormatter(new DecimalFormat("#.##")); 
        pcseditFormatter.setValueClass(Double.class);   
        DefaultFormatterFactory factorypcs = new DefaultFormatterFactory(pcsdefaultFormatter,pcsdisplayFormatter,pcseditFormatter);
        txtPcsTransaksi.setFormatterFactory(factorypcs);
        
        NumberFormatter diskondefaultFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        diskondefaultFormatter.setValueClass(Double.class);
        NumberFormatter diskondisplayFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        diskondisplayFormatter.setValueClass(Double.class);
        NumberFormatter diskoneditFormatter = new NumberFormatter(new DecimalFormat("#.##")); 
        diskoneditFormatter.setValueClass(Double.class);   
        DefaultFormatterFactory diskonfactory = new DefaultFormatterFactory(diskondefaultFormatter,diskondisplayFormatter,diskoneditFormatter);
        txtDiskonTransaksi.setFormatterFactory(diskonfactory);
        txtDiskonTransaksi.setValue(0);
        
        NumberFormatter cashdefaultFormatter = new NumberFormatter(new DecimalFormat("#"));
        cashdefaultFormatter.setValueClass(Float.class);
        NumberFormatter cashdisplayFormatter = new NumberFormatter(new DecimalFormat("Rp #,### "));
        cashdisplayFormatter.setValueClass(Float.class);
        NumberFormatter casheditFormatter = new NumberFormatter(new DecimalFormat("#")); 
        casheditFormatter.setValueClass(Float.class);
        DefaultFormatterFactory factorycash = new DefaultFormatterFactory(cashdefaultFormatter,cashdisplayFormatter,casheditFormatter);
        txtCashTransaksi.setFormatterFactory(factorycash);
        txtCashTransaksi.setValue(0);
        
        NumberFormatter jumlahshipdefaultFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        pcsdefaultFormatter.setValueClass(Double.class);
        NumberFormatter jumlahshipdisplayFormatter = new NumberFormatter(new DecimalFormat("#.##"));
        pcsdisplayFormatter.setValueClass(Double.class);
        NumberFormatter jumlahshipeditFormatter = new NumberFormatter(new DecimalFormat("#.##")); 
        pcseditFormatter.setValueClass(Double.class);   
        DefaultFormatterFactory factoryjumlahship = new DefaultFormatterFactory(jumlahshipdefaultFormatter,jumlahshipdisplayFormatter,jumlahshipeditFormatter);
        txtJumlahShipment.setFormatterFactory(factoryjumlahship);
        
        formTambahStok.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        formTambahStok.getContentPane().add(panelTambahStok);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        formTambahStok.setLocation(dim.width/2-350/2, dim.height/2-480/2);
        formTambahStok.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                txtIdFormTambahStok.setText("");
                txtNamaFormTambahStok.setText("");
                txtJenisFormTambahStok.setText("");
                txtHarga80FormTambahStok.setText("");
                txtHarga90FormTambahStok.setText("");
                txtSatuanFormTambahStok.setText("");
                txtJumlahFormTambahStok.setText("");
                txtKeteranganFormTambahStok.setText("");
            }
            
        });
        
        formEditStok.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        formEditStok.getContentPane().add(panelEditStok);
        formEditStok.setLocation(dim.width/2-350/2, dim.height/2-480/2);
        formEditStok.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                txtIdFormEditStok.setText("");
                txtNamaFormEditStok.setText("");
                txtJenisFormEditStok.setText("");
                txtHarga80FormEditStok.setText("");
                txtHarga90FormEditStok.setText("");
                txtSatuanFormEditStok.setText("");
                txtJumlahFormEditStok.setText("");
                txtKeteranganFormEditStok.setText("");
            }
            
        });
        
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    HapusSemuaDaftarTransaksi();
                    System.exit(0);
                }
            }
            
        });
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ToolbarPanel = new javax.swing.JToolBar();
        btnTansaksiFitur = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnStokFitur = new javax.swing.JButton();
        btnPenjualanFitur = new javax.swing.JButton();
        btnChartFitur = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnUserFitur = new javax.swing.JButton();
        btnSettingFitur = new javax.swing.JButton();
        btnLogoutFitur = new javax.swing.JButton();
        btnTambahBarangTransaksi = new javax.swing.JButton();
        btnGroupShipment = new javax.swing.ButtonGroup();
        btnGroupKeluarMasuk = new javax.swing.ButtonGroup();
        btnGroupPenjualan = new javax.swing.ButtonGroup();
        panelTambahStok = new javax.swing.JPanel();
        jXTitledSeparator7 = new org.jdesktop.swingx.JXTitledSeparator();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtHarga80FormTambahStok = new javax.swing.JTextField();
        txtHarga90FormTambahStok = new javax.swing.JTextField();
        txtSatuanFormTambahStok = new javax.swing.JTextField();
        txtJumlahFormTambahStok = new javax.swing.JTextField();
        txtKeteranganFormTambahStok = new javax.swing.JTextField();
        txtNamaFormTambahStok = new javax.swing.JTextField();
        txtIdFormTambahStok = new javax.swing.JTextField();
        txtJenisFormTambahStok = new javax.swing.JTextField();
        cmbJenisFormTambahStok = new de.javasoft.swing.JYComboBox();
        btnTambahkanFormTambahStok = new javax.swing.JButton();
        panelEditStok = new javax.swing.JPanel();
        txtNamaFormEditStok = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtKeteranganFormEditStok = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtJenisFormEditStok = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtIdFormEditStok = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        btnTambahkanFormEditStok = new javax.swing.JButton();
        cmbJenisFormEditStok = new de.javasoft.swing.JYComboBox();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtHarga90FormEditStok = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtHarga80FormEditStok = new javax.swing.JTextField();
        txtJumlahFormEditStok = new javax.swing.JTextField();
        jXTitledSeparator8 = new org.jdesktop.swingx.JXTitledSeparator();
        txtSatuanFormEditStok = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelBarangFormEditStok = new javax.swing.JTable();
        PanelFitur = new javax.swing.JPanel();
        Transaksi = new javax.swing.JPanel();
        SplitPanelTransaksi = new javax.swing.JSplitPane();
        PanelKiriTransaksi = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarangTransaksi = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNamaBarangTransaksi = new de.javasoft.swing.JYSearchField();
        txtPcsTransaksi = new de.javasoft.swing.JYTextField();
        txtPembeliTransaksi = new de.javasoft.swing.JYTextField();
        txtDiskonTransaksi = new de.javasoft.swing.JYTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        txtCashTransaksi = new de.javasoft.swing.JYFormattedTextField();
        btnOkeTransaksi = new javax.swing.JButton();
        PanelKananTransaksi = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDaftarBeliTransaksi = new javax.swing.JTable();
        btnHapusDaftarTransaksi = new javax.swing.JButton();
        btnHapusSemuaDaftarTransaksi = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelHargaJual = new javax.swing.JLabel();
        labelDiskonTransaksi = new javax.swing.JLabel();
        labelTotalTransaksi = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        labelSisaPembayaran = new javax.swing.JLabel();
        labelUangMukaTransaksi = new javax.swing.JLabel();
        labelPembeliTransaksi = new javax.swing.JLabel();
        labelTanggalTransaksi = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        labelCashTransaksi = new javax.swing.JLabel();
        labelTotal2Transaksi = new javax.swing.JLabel();
        labelKembaliTransaksi = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        Stok = new javax.swing.JPanel();
        SplitPanelStok = new javax.swing.JSplitPane();
        PanelKiriStok = new javax.swing.JPanel();
        jYTableScrollPane1 = new de.javasoft.swing.JYTableScrollPane();
        tabelStokStok = new de.javasoft.swing.JYTable();
        btnHapusStokStok = new javax.swing.JButton();
        btnTambahStokStok = new javax.swing.JButton();
        btnEditStokStok = new javax.swing.JButton();
        btnPDFStokStok = new javax.swing.JButton();
        jXTitledSeparator1 = new org.jdesktop.swingx.JXTitledSeparator();
        PanelKananStok = new javax.swing.JPanel();
        jYTableScrollPane2 = new de.javasoft.swing.JYTableScrollPane();
        tabelShipment = new de.javasoft.swing.JYTable();
        jXTitledSeparator2 = new org.jdesktop.swingx.JXTitledSeparator();
        buttonBar1 = new de.javasoft.swing.ButtonBar();
        tgbKeluarShipment = new javax.swing.JToggleButton();
        tgbMasukShipment = new javax.swing.JToggleButton();
        tgbSemuaShipment = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        rbtnKeluarShipment = new javax.swing.JRadioButton();
        rbtnMasukShipment = new javax.swing.JRadioButton();
        cmbBarangShipment = new de.javasoft.swing.JYComboBox();
        dtcmbTanggalShipment = new de.javasoft.swing.DateComboBox();
        txtKeteranganShipment = new javax.swing.JTextField();
        btnOkeShipment = new javax.swing.JButton();
        txtJumlahShipment = new javax.swing.JFormattedTextField();
        jSeparator12 = new javax.swing.JSeparator();
        Penjualan = new javax.swing.JPanel();
        jXTitledSeparator3 = new org.jdesktop.swingx.JXTitledSeparator();
        buttonBar2 = new de.javasoft.swing.ButtonBar();
        tgbtnLunasPenjualan = new javax.swing.JToggleButton();
        tgbtnBelumPenjualan = new javax.swing.JToggleButton();
        tgbtnSemuaPenjualan = new javax.swing.JToggleButton();
        jYTableScrollPane3 = new de.javasoft.swing.JYTableScrollPane();
        tabelTransaksiPenjualan = new de.javasoft.swing.JYTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelDetailTransaksiPenjualan = new javax.swing.JTable();
        jSeparator13 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        labelIdTransaksiPenjualan = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        labelTotalTransaksiPenjualan = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        labelSisaTransaksiPenjualan = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        labelPembeliTransaksiPenjualan = new javax.swing.JLabel();
        jXTitledSeparator4 = new org.jdesktop.swingx.JXTitledSeparator();
        txtUangPelunasanPenjualan = new de.javasoft.swing.JYTextField();
        btnTambahPelunasanPenjualan = new javax.swing.JButton();
        Graph = new javax.swing.JPanel();
        jXTitledSeparator5 = new org.jdesktop.swingx.JXTitledSeparator();
        jLabel23 = new javax.swing.JLabel();
        dtcmbDariGraph = new de.javasoft.swing.DateComboBox();
        jLabel24 = new javax.swing.JLabel();
        dtcmbSampaiGraph = new de.javasoft.swing.DateComboBox();
        btnOkeGraph = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JSeparator();
        panelGraph = new javax.swing.JPanel();
        User = new javax.swing.JPanel();
        jXTitledSeparator6 = new org.jdesktop.swingx.JXTitledSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelUser = new javax.swing.JTable();
        jSeparator15 = new javax.swing.JSeparator();
        btnDeleteUser = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtUsernameUserTambah = new javax.swing.JTextField();
        txtNamaUserTambah = new javax.swing.JTextField();
        txtPassUserTambah = new javax.swing.JTextField();
        swbtnAdminTambah = new de.javasoft.swing.JYSwitchButton();
        btnOkeUserTambah = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtNamaUserEdit = new javax.swing.JTextField();
        txtUsernameUserEdit = new javax.swing.JTextField();
        txtPassLamaUserEdit = new javax.swing.JPasswordField();
        txtPassBaruUserEdit = new javax.swing.JTextField();
        swbtnAdminEdit = new de.javasoft.swing.JYSwitchButton();
        btnOkeEditUser = new javax.swing.JButton();
        MenuBar = new javax.swing.JMenuBar();
        menuFitur = new javax.swing.JMenu();
        submenuStok = new javax.swing.JMenu();
        itemmenuTambahStok = new javax.swing.JMenuItem();
        itemmenuEditStok = new javax.swing.JMenuItem();
        itemmenuHapusStok = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itemmenuLihatStok = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        submenuPenjualan = new javax.swing.JMenu();
        itemmenuPelunasanPenjualan = new javax.swing.JMenuItem();
        submenuShipment = new javax.swing.JMenu();
        itemmenuKeluarShipment = new javax.swing.JMenuItem();
        itemmenuMasukShipment = new javax.swing.JMenuItem();
        submenuExport = new javax.swing.JMenu();
        itemmenuStokPDF = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        submenuExit = new javax.swing.JMenuItem();
        menuBantuan = new javax.swing.JMenu();
        submenuKontenBantuan = new javax.swing.JMenuItem();
        submenuTentang = new javax.swing.JMenuItem();
        menuCurrentUser = new javax.swing.JMenu();
        submenuLogout = new javax.swing.JMenuItem();

        ToolbarPanel.setFloatable(false);
        ToolbarPanel.setRollover(true);

        btnTansaksiFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/cashier.png"))); // NOI18N
        btnTansaksiFitur.setText("Transaksi");
        btnTansaksiFitur.setToolTipText("Transaksi pembelian barang");
        btnTansaksiFitur.setFocusable(false);
        btnTansaksiFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTansaksiFitur.setIconTextGap(0);
        btnTansaksiFitur.setMargin(new java.awt.Insets(0, 15, 2, 15));
        btnTansaksiFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTansaksiFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTansaksiFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnTansaksiFitur);
        ToolbarPanel.add(jSeparator1);

        btnStokFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/stok.png"))); // NOI18N
        btnStokFitur.setText("Stok");
        btnStokFitur.setToolTipText("Stok barang");
        btnStokFitur.setFocusable(false);
        btnStokFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStokFitur.setIconTextGap(0);
        btnStokFitur.setMargin(new java.awt.Insets(0, 0, 2, 0));
        btnStokFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStokFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnStokFitur);

        btnPenjualanFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/penjualan.png"))); // NOI18N
        btnPenjualanFitur.setText("Penjualan");
        btnPenjualanFitur.setToolTipText("Daftar transaksi dan pelunasan");
        btnPenjualanFitur.setFocusable(false);
        btnPenjualanFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPenjualanFitur.setIconTextGap(0);
        btnPenjualanFitur.setMargin(new java.awt.Insets(0, 15, 2, 15));
        btnPenjualanFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPenjualanFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjualanFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnPenjualanFitur);

        btnChartFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/chart.png"))); // NOI18N
        btnChartFitur.setText("Chart");
        btnChartFitur.setToolTipText("Grafik penjualan");
        btnChartFitur.setFocusable(false);
        btnChartFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChartFitur.setIconTextGap(0);
        btnChartFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChartFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnChartFitur);
        ToolbarPanel.add(jSeparator2);

        btnUserFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/user.png"))); // NOI18N
        btnUserFitur.setText("User");
        btnUserFitur.setToolTipText("Daftar user");
        btnUserFitur.setFocusable(false);
        btnUserFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserFitur.setIconTextGap(0);
        btnUserFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnUserFitur);

        btnSettingFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/tool.png"))); // NOI18N
        btnSettingFitur.setText("Setting");
        btnSettingFitur.setToolTipText("Setting");
        btnSettingFitur.setFocusable(false);
        btnSettingFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSettingFitur.setIconTextGap(0);
        btnSettingFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSettingFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnSettingFitur);

        btnLogoutFitur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/logout.png"))); // NOI18N
        btnLogoutFitur.setText("Logout");
        btnLogoutFitur.setToolTipText("Logout");
        btnLogoutFitur.setFocusable(false);
        btnLogoutFitur.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogoutFitur.setIconTextGap(0);
        btnLogoutFitur.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogoutFitur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutFiturActionPerformed(evt);
            }
        });
        ToolbarPanel.add(btnLogoutFitur);

        btnTambahBarangTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add.png"))); // NOI18N
        btnTambahBarangTransaksi.setBorderPainted(false);
        btnTambahBarangTransaksi.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnTambahBarangTransaksi.setNextFocusableComponent(txtPembeliTransaksi);
        btnTambahBarangTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBarangTransaksiActionPerformed(evt);
            }
        });

        jXTitledSeparator7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator7.setTitle("Tambah Stok");

        jLabel34.setText("Id Barang:");

        jLabel35.setText("Nama:");

        jLabel36.setText("Jenis:");

        jLabel37.setText("Harga < 80:");

        jLabel38.setText("Harga > 90:");

        jLabel39.setText("Satuan:");

        jLabel40.setText("Jumlah:");

        jLabel41.setText("Keterangan:");

        txtJumlahFormTambahStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJumlahFormTambahStokKeyReleased(evt);
            }
        });

        txtJenisFormTambahStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJenisFormTambahStokKeyReleased(evt);
            }
        });

        cmbJenisFormTambahStok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisFormTambahStokItemStateChanged(evt);
            }
        });

        btnTambahkanFormTambahStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add2.png"))); // NOI18N
        btnTambahkanFormTambahStok.setText("Tambahkan");
        btnTambahkanFormTambahStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahkanFormTambahStokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTambahStokLayout = new javax.swing.GroupLayout(panelTambahStok);
        panelTambahStok.setLayout(panelTambahStokLayout);
        panelTambahStokLayout.setHorizontalGroup(
            panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahStokLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahStokLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(46, 46, 46)
                        .addComponent(txtNamaFormTambahStok, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                    .addGroup(panelTambahStokLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(panelTambahStokLayout.createSequentialGroup()
                        .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTambahStokLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(40, 40, 40)
                                .addComponent(txtJumlahFormTambahStok))
                            .addGroup(panelTambahStokLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(39, 39, 39)
                                .addComponent(txtSatuanFormTambahStok))
                            .addGroup(panelTambahStokLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(txtHarga90FormTambahStok))
                            .addGroup(panelTambahStokLayout.createSequentialGroup()
                                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel36))
                                .addGap(18, 18, 18)
                                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJenisFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHarga80FormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbJenisFormTambahStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTambahStokLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(17, 17, 17)
                        .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKeteranganFormTambahStok, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                            .addGroup(panelTambahStokLayout.createSequentialGroup()
                                .addComponent(btnTambahkanFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelTambahStokLayout.setVerticalGroup(
            panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtIdFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtNamaFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtJenisFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbJenisFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtHarga80FormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtHarga90FormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtSatuanFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtJumlahFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelTambahStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtKeteranganFormTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTambahkanFormTambahStok)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jLabel42.setText("Harga > 90:");

        jLabel43.setText("Harga < 80:");

        txtJenisFormEditStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJenisFormEditStokKeyReleased(evt);
            }
        });

        jLabel44.setText("Jenis:");

        txtIdFormEditStok.setEnabled(false);

        jLabel45.setText("Nama:");

        btnTambahkanFormEditStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/edit2.png"))); // NOI18N
        btnTambahkanFormEditStok.setText("Edit");
        btnTambahkanFormEditStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahkanFormEditStokActionPerformed(evt);
            }
        });

        cmbJenisFormEditStok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisFormEditStokItemStateChanged(evt);
            }
        });

        jLabel46.setText("Satuan:");

        jLabel47.setText("Keterangan:");

        jLabel48.setText("Jumlah:");

        jLabel49.setText("Id Barang:");

        txtJumlahFormEditStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtJumlahFormEditStokKeyReleased(evt);
            }
        });

        jXTitledSeparator8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator8.setTitle("Edit Stok");

        tabelBarangFormEditStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Barang", "Nama"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBarangFormEditStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangFormEditStokMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelBarangFormEditStok);

        javax.swing.GroupLayout panelEditStokLayout = new javax.swing.GroupLayout(panelEditStok);
        panelEditStok.setLayout(panelEditStokLayout);
        panelEditStokLayout.setHorizontalGroup(
            panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditStokLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEditStokLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(46, 46, 46)
                        .addComponent(txtNamaFormEditStok, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                    .addGroup(panelEditStokLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(panelEditStokLayout.createSequentialGroup()
                        .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelEditStokLayout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addGap(40, 40, 40)
                                .addComponent(txtJumlahFormEditStok))
                            .addGroup(panelEditStokLayout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(39, 39, 39)
                                .addComponent(txtSatuanFormEditStok))
                            .addGroup(panelEditStokLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(18, 18, 18)
                                .addComponent(txtHarga90FormEditStok))
                            .addGroup(panelEditStokLayout.createSequentialGroup()
                                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel44))
                                .addGap(18, 18, 18)
                                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJenisFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHarga80FormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbJenisFormEditStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelEditStokLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(17, 17, 17)
                        .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKeteranganFormEditStok, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                            .addGroup(panelEditStokLayout.createSequentialGroup()
                                .addComponent(btnTambahkanFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelEditStokLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelEditStokLayout.setVerticalGroup(
            panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtIdFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtNamaFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtJenisFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbJenisFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtHarga80FormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtHarga90FormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtSatuanFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtJumlahFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelEditStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtKeteranganFormEditStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTambahkanFormEditStok)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        PanelFitur.setPreferredSize(new java.awt.Dimension(1200, 640));
        PanelFitur.setLayout(new java.awt.CardLayout());

        SplitPanelTransaksi.setDividerLocation(300);

        tblBarangTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Barang", "Hrg. < 80", "Hrg. > 90", "Stok"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBarangTransaksi.setNextFocusableComponent(txtPcsTransaksi);
        jScrollPane1.setViewportView(tblBarangTransaksi);

        jLabel1.setText("Pembeli:");

        jLabel2.setText("Diskon:");

        txtNamaBarangTransaksi.setNextFocusableComponent(tblBarangTransaksi);
        txtNamaBarangTransaksi.setPromptText("Nama Barang");
        txtNamaBarangTransaksi.setSearchMode(de.javasoft.swing.JYSearchField.SearchMode.AUTO);
        txtNamaBarangTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaBarangTransaksiKeyReleased(evt);
            }
        });

        txtPcsTransaksi.setNextFocusableComponent(btnTambahBarangTransaksi);
        txtPcsTransaksi.setPromptText("Pcs.");

        txtPembeliTransaksi.setNextFocusableComponent(txtDiskonTransaksi);
        txtPembeliTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPembeliTransaksiKeyReleased(evt);
            }
        });

        txtDiskonTransaksi.setText("0");
        txtDiskonTransaksi.setNextFocusableComponent(txtCashTransaksi);
        txtDiskonTransaksi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiskonTransaksiFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Uang yang dibayarkan:");

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        txtCashTransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCashTransaksi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCashTransaksi.setNextFocusableComponent(btnOkeTransaksi);
        txtCashTransaksi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCashTransaksiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCashTransaksiFocusLost(evt);
            }
        });

        btnOkeTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/oke.png"))); // NOI18N
        btnOkeTransaksi.setBorderPainted(false);
        btnOkeTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkeTransaksiActionPerformed(evt);
            }
        });
        btnOkeTransaksi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnOkeTransaksiFocusGained(evt);
            }
        });

        javax.swing.GroupLayout PanelKiriTransaksiLayout = new javax.swing.GroupLayout(PanelKiriTransaksi);
        PanelKiriTransaksi.setLayout(PanelKiriTransaksiLayout);
        PanelKiriTransaksiLayout.setHorizontalGroup(
            PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtPembeliTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                        .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(25, 25, 25)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDiskonTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE))
                            .addComponent(txtCashTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelKiriTransaksiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtPcsTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOkeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNamaBarangTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelKiriTransaksiLayout.setVerticalGroup(
            PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNamaBarangTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPcsTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPembeliTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelKiriTransaksiLayout.createSequentialGroup()
                        .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelKiriTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiskonTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCashTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator7))
                .addGap(18, 18, 18)
                .addComponent(btnOkeTransaksi)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        SplitPanelTransaksi.setLeftComponent(PanelKiriTransaksi);

        tblDaftarBeliTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Pcs.", "Satuan", "Harga", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDaftarBeliTransaksi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPane2.setViewportView(tblDaftarBeliTransaksi);

        btnHapusDaftarTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/hapus.png"))); // NOI18N
        btnHapusDaftarTransaksi.setToolTipText("Hapus");
        btnHapusDaftarTransaksi.setBorderPainted(false);
        btnHapusDaftarTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusDaftarTransaksiActionPerformed(evt);
            }
        });

        btnHapusSemuaDaftarTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/hapus_semua.png"))); // NOI18N
        btnHapusSemuaDaftarTransaksi.setToolTipText("Hapus Semua");
        btnHapusSemuaDaftarTransaksi.setBorderPainted(false);
        btnHapusSemuaDaftarTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSemuaDaftarTransaksiActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Transaksi"));

        labelHargaJual.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelHargaJual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelHargaJual.setText("Rp 0,-");

        labelDiskonTransaksi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelDiskonTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelDiskonTransaksi.setText("Rp 0,-");

        labelTotalTransaksi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotalTransaksi.setText("Rp 0,-");

        jLabel6.setText("Harga jual:");

        jLabel7.setText("Diskon:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Total:");

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel9.setText("Tanggal:");

        jLabel10.setText("Pembeli:");

        jLabel11.setText("Uang muka:");

        jLabel12.setText("Sisa pembayaran:");

        labelSisaPembayaran.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelSisaPembayaran.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSisaPembayaran.setText("Rp 0,-");

        labelUangMukaTransaksi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelUangMukaTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelUangMukaTransaksi.setText("Rp 0,-");

        labelPembeliTransaksi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelPembeliTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        labelTanggalTransaksi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTanggalTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTanggalTransaksi.setText("tanggal");

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel13.setText("Total:");

        jLabel14.setText("Uang yang dibayarkan:");

        jLabel15.setText("Kembali:");

        labelCashTransaksi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelCashTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCashTransaksi.setText("Rp 0,-");

        labelTotal2Transaksi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTotal2Transaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTotal2Transaksi.setText("Rp 0,-");

        labelKembaliTransaksi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelKembaliTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelKembaliTransaksi.setText("Rp 0,-");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelDiskonTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(labelTotalTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTanggalTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelSisaPembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(labelUangMukaTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPembeliTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelKembaliTransaksi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCashTransaksi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(labelTotal2Transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDiskonTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTotalTransaksi)
                    .addComponent(jLabel8))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(labelTanggalTransaksi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelPembeliTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(labelUangMukaTransaksi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(labelSisaPembayaran)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(labelTotal2Transaksi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(labelCashTransaksi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(labelKembaliTransaksi))))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout PanelKananTransaksiLayout = new javax.swing.GroupLayout(PanelKananTransaksi);
        PanelKananTransaksi.setLayout(PanelKananTransaksiLayout);
        PanelKananTransaksiLayout.setHorizontalGroup(
            PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelKananTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelKananTransaksiLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnHapusDaftarTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapusSemuaDaftarTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelKananTransaksiLayout.setVerticalGroup(
            PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKananTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9)
                    .addGroup(PanelKananTransaksiLayout.createSequentialGroup()
                        .addGroup(PanelKananTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelKananTransaksiLayout.createSequentialGroup()
                                .addComponent(btnHapusDaftarTransaksi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapusSemuaDaftarTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        SplitPanelTransaksi.setRightComponent(PanelKananTransaksi);

        javax.swing.GroupLayout TransaksiLayout = new javax.swing.GroupLayout(Transaksi);
        Transaksi.setLayout(TransaksiLayout);
        TransaksiLayout.setHorizontalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
            .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SplitPanelTransaksi))
        );
        TransaksiLayout.setVerticalGroup(
            TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SplitPanelTransaksi))
        );

        PanelFitur.add(Transaksi, "cardTransaksi");

        Stok.setPreferredSize(new java.awt.Dimension(1200, 494));

        SplitPanelStok.setDividerLocation(730);

        PanelKiriStok.setPreferredSize(new java.awt.Dimension(700, 492));

        tabelStokStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nama", "Jenis", "Harga < 80", "Harga > 90", "Satuan", "Stok", "Keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jYTableScrollPane1.setViewportView(tabelStokStok);

        btnHapusStokStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/hapus.png"))); // NOI18N
        btnHapusStokStok.setToolTipText("Hapus");
        btnHapusStokStok.setBorderPainted(false);
        btnHapusStokStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusStokStokActionPerformed(evt);
            }
        });

        btnTambahStokStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add2.png"))); // NOI18N
        btnTambahStokStok.setToolTipText("Tambah");
        btnTambahStokStok.setBorderPainted(false);
        btnTambahStokStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahStokStokActionPerformed(evt);
            }
        });

        btnEditStokStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/edit2.png"))); // NOI18N
        btnEditStokStok.setToolTipText("Edit");
        btnEditStokStok.setBorderPainted(false);
        btnEditStokStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditStokStokActionPerformed(evt);
            }
        });

        btnPDFStokStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/pdf.png"))); // NOI18N
        btnPDFStokStok.setToolTipText("Simpan PDF");
        btnPDFStokStok.setBorderPainted(false);
        btnPDFStokStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFStokStokActionPerformed(evt);
            }
        });

        jXTitledSeparator1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator1.setTitle("Stok");

        javax.swing.GroupLayout PanelKiriStokLayout = new javax.swing.GroupLayout(PanelKiriStok);
        PanelKiriStok.setLayout(PanelKiriStokLayout);
        PanelKiriStokLayout.setHorizontalGroup(
            PanelKiriStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKiriStokLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelKiriStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelKiriStokLayout.createSequentialGroup()
                        .addComponent(btnHapusStokStok, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahStokStok, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditStokStok, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPDFStokStok, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jYTableScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                    .addComponent(jXTitledSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelKiriStokLayout.setVerticalGroup(
            PanelKiriStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKiriStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYTableScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelKiriStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapusStokStok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahStokStok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditStokStok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPDFStokStok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        SplitPanelStok.setLeftComponent(PanelKiriStok);

        PanelKananStok.setPreferredSize(new java.awt.Dimension(450, 597));

        tabelShipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "Nama Barang", "Jumlah", "Status", "Keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jYTableScrollPane2.setViewportView(tabelShipment);

        jXTitledSeparator2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator2.setTitle("Shipment");

        btnGroupShipment.add(tgbKeluarShipment);
        tgbKeluarShipment.setText("Keluar");
        tgbKeluarShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbKeluarShipmentActionPerformed(evt);
            }
        });
        buttonBar1.add(tgbKeluarShipment);

        btnGroupShipment.add(tgbMasukShipment);
        tgbMasukShipment.setText("Masuk");
        tgbMasukShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbMasukShipmentActionPerformed(evt);
            }
        });
        buttonBar1.add(tgbMasukShipment);

        btnGroupShipment.add(tgbSemuaShipment);
        tgbSemuaShipment.setText("Semua");
        tgbSemuaShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbSemuaShipmentActionPerformed(evt);
            }
        });
        buttonBar1.add(tgbSemuaShipment);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Keluar / Masuk Barang"));

        jLabel4.setText("Barang:");

        jLabel5.setText("Jumlah:");

        jLabel16.setText("Keluar / Masuk:");

        jLabel17.setText("Tanggal:");

        jLabel18.setText("Keterangan:");

        btnGroupKeluarMasuk.add(rbtnKeluarShipment);
        rbtnKeluarShipment.setText("Keluar");

        btnGroupKeluarMasuk.add(rbtnMasukShipment);
        rbtnMasukShipment.setText("Masuk");

        btnOkeShipment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/oke.png"))); // NOI18N
        btnOkeShipment.setBorderPainted(false);
        btnOkeShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkeShipmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtJumlahShipment, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel4))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(rbtnKeluarShipment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbtnMasukShipment))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cmbBarangShipment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtcmbTanggalShipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKeteranganShipment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOkeShipment, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBarangShipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtJumlahShipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(rbtnKeluarShipment)
                            .addComponent(rbtnMasukShipment))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(dtcmbTanggalShipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnOkeShipment, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtKeteranganShipment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout PanelKananStokLayout = new javax.swing.GroupLayout(PanelKananStok);
        PanelKananStok.setLayout(PanelKananStokLayout);
        PanelKananStokLayout.setHorizontalGroup(
            PanelKananStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelKananStokLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelKananStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelKananStokLayout.createSequentialGroup()
                        .addComponent(buttonBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jYTableScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(PanelKananStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelKananStokLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(452, Short.MAX_VALUE)))
        );
        PanelKananStokLayout.setVerticalGroup(
            PanelKananStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelKananStokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYTableScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(PanelKananStokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelKananStokLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator12, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        SplitPanelStok.setRightComponent(PanelKananStok);

        javax.swing.GroupLayout StokLayout = new javax.swing.GroupLayout(Stok);
        Stok.setLayout(StokLayout);
        StokLayout.setHorizontalGroup(
            StokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
            .addGroup(StokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SplitPanelStok, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE))
        );
        StokLayout.setVerticalGroup(
            StokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
            .addGroup(StokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(SplitPanelStok, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
        );

        PanelFitur.add(Stok, "cardStok");

        Penjualan.setPreferredSize(new java.awt.Dimension(1200, 620));

        jXTitledSeparator3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator3.setTitle("Data Penjualan");

        btnGroupPenjualan.add(tgbtnLunasPenjualan);
        tgbtnLunasPenjualan.setText("Lunas");
        tgbtnLunasPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbtnLunasPenjualanActionPerformed(evt);
            }
        });
        buttonBar2.add(tgbtnLunasPenjualan);

        btnGroupPenjualan.add(tgbtnBelumPenjualan);
        tgbtnBelumPenjualan.setText("Belum Lunas");
        tgbtnBelumPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbtnBelumPenjualanActionPerformed(evt);
            }
        });
        buttonBar2.add(tgbtnBelumPenjualan);

        btnGroupPenjualan.add(tgbtnSemuaPenjualan);
        tgbtnSemuaPenjualan.setText("Semua");
        tgbtnSemuaPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgbtnSemuaPenjualanActionPerformed(evt);
            }
        });
        buttonBar2.add(tgbtnSemuaPenjualan);

        tabelTransaksiPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tanggal", "Harga Jual", "Diskon", "Total", "Uang Muka", "Sisa", "Pembeli", "Status", "Penjual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelTransaksiPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiPenjualanMouseClicked(evt);
            }
        });
        jYTableScrollPane3.setViewportView(tabelTransaksiPenjualan);

        tabelDetailTransaksiPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Barang", "Harga", "Jumlah", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelDetailTransaksiPenjualan);

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pelunasan"));

        jLabel19.setText("Id Transaksi:");

        jLabel20.setText("Total:");

        labelTotalTransaksiPenjualan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel21.setText("Sisa:");

        jLabel22.setText("Pembeli:");

        jXTitledSeparator4.setTitle("Menambahkan");

        txtUangPelunasanPenjualan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUangPelunasanPenjualan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUangPelunasanPenjualan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUangPelunasanPenjualanKeyReleased(evt);
            }
        });

        btnTambahPelunasanPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add2.png"))); // NOI18N
        btnTambahPelunasanPenjualan.setBorderPainted(false);
        btnTambahPelunasanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPelunasanPenjualanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelIdTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTotalTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSisaTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPembeliTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtUangPelunasanPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahPelunasanPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(labelIdTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(labelTotalTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(labelSisaTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(labelPembeliTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTitledSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambahPelunasanPenjualan)
                    .addComponent(txtUangPelunasanPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout PenjualanLayout = new javax.swing.GroupLayout(Penjualan);
        Penjualan.setLayout(PenjualanLayout);
        PenjualanLayout.setHorizontalGroup(
            PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PenjualanLayout.createSequentialGroup()
                        .addComponent(buttonBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PenjualanLayout.createSequentialGroup()
                        .addGroup(PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jYTableScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PenjualanLayout.setVerticalGroup(
            PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PenjualanLayout.createSequentialGroup()
                        .addComponent(jYTableScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator13)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
                .addContainerGap())
        );

        PanelFitur.add(Penjualan, "cardPenjualan");

        Graph.setPreferredSize(new java.awt.Dimension(1200, 620));

        jXTitledSeparator5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator5.setTitle("Graph");

        jLabel23.setText("Dari Tanggal:");

        jLabel24.setText("Sampai Tanggal:");

        btnOkeGraph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/oke.png"))); // NOI18N
        btnOkeGraph.setBorderPainted(false);
        btnOkeGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkeGraphActionPerformed(evt);
            }
        });

        jSeparator14.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout panelGraphLayout = new javax.swing.GroupLayout(panelGraph);
        panelGraph.setLayout(panelGraphLayout);
        panelGraphLayout.setHorizontalGroup(
            panelGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1073, Short.MAX_VALUE)
        );
        panelGraphLayout.setVerticalGroup(
            panelGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout GraphLayout = new javax.swing.GroupLayout(Graph);
        Graph.setLayout(GraphLayout);
        GraphLayout.setHorizontalGroup(
            GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GraphLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GraphLayout.createSequentialGroup()
                        .addGroup(GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(dtcmbDariGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(dtcmbSampaiGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(GraphLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnOkeGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        GraphLayout.setVerticalGroup(
            GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GraphLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtcmbDariGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtcmbSampaiGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOkeGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(421, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GraphLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        PanelFitur.add(Graph, "cardGraph");

        jXTitledSeparator6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jXTitledSeparator6.setTitle("User");

        tabelUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Username", "Admin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelUserMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelUser);

        jSeparator15.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnDeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/hapus.png"))); // NOI18N
        btnDeleteUser.setBorderPainted(false);
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tambah User"));

        jLabel25.setText("Nama:");

        jLabel26.setText("Username:");

        jLabel27.setText("Password:");

        jLabel28.setText("Admin:");

        swbtnAdminTambah.setOffText("Tidak");
        swbtnAdminTambah.setOnText("Iya");

        btnOkeUserTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add2.png"))); // NOI18N
        btnOkeUserTambah.setText("Tambahkan");
        btnOkeUserTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkeUserTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsernameUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel25)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(txtNamaUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(swbtnAdminTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOkeUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(455, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtNamaUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtUsernameUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtPassUserTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(swbtnAdminTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOkeUserTambah)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit User"));

        jLabel29.setText("Nama:");

        jLabel30.setText("Username:");

        jLabel31.setText("Password Lama:");

        jLabel32.setText("Password Baru:");

        jLabel33.setText("Admin:");

        swbtnAdminEdit.setOffText("Tidak");
        swbtnAdminEdit.setOnText("Iya");

        btnOkeEditUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/edit2.png"))); // NOI18N
        btnOkeEditUser.setText("Edit");
        btnOkeEditUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkeEditUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOkeEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(swbtnAdminEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNamaUserEdit)
                        .addComponent(txtUsernameUserEdit)
                        .addComponent(txtPassLamaUserEdit)
                        .addComponent(txtPassBaruUserEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtNamaUserEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtUsernameUserEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtPassLamaUserEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtPassBaruUserEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(swbtnAdminEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOkeEditUser)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UserLayout = new javax.swing.GroupLayout(User);
        User.setLayout(UserLayout);
        UserLayout.setHorizontalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(UserLayout.createSequentialGroup()
                        .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        UserLayout.setVerticalGroup(
            UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator15)
                    .addGroup(UserLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteUser))
                    .addGroup(UserLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        PanelFitur.add(User, "cardUser");

        menuFitur.setText("Fitur");

        submenuStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/box.png"))); // NOI18N
        submenuStok.setText("Stok");

        itemmenuTambahStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/add.png"))); // NOI18N
        itemmenuTambahStok.setText("Tambah Stok Baru");
        itemmenuTambahStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuTambahStokActionPerformed(evt);
            }
        });
        submenuStok.add(itemmenuTambahStok);

        itemmenuEditStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/edit.png"))); // NOI18N
        itemmenuEditStok.setText("Edit Stok");
        itemmenuEditStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuEditStokActionPerformed(evt);
            }
        });
        submenuStok.add(itemmenuEditStok);

        itemmenuHapusStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/bin_closed.png"))); // NOI18N
        itemmenuHapusStok.setText("Hapus Stok");
        itemmenuHapusStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuHapusStokActionPerformed(evt);
            }
        });
        submenuStok.add(itemmenuHapusStok);
        submenuStok.add(jSeparator3);

        itemmenuLihatStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/bricks.png"))); // NOI18N
        itemmenuLihatStok.setText("Lihat Stok");
        itemmenuLihatStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuLihatStokActionPerformed(evt);
            }
        });
        submenuStok.add(itemmenuLihatStok);

        menuFitur.add(submenuStok);
        menuFitur.add(jSeparator4);

        submenuPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/calculator.png"))); // NOI18N
        submenuPenjualan.setText("Penjualan");

        itemmenuPelunasanPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/page_white_edit.png"))); // NOI18N
        itemmenuPelunasanPenjualan.setText("Pelunasan");
        itemmenuPelunasanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuPelunasanPenjualanActionPerformed(evt);
            }
        });
        submenuPenjualan.add(itemmenuPelunasanPenjualan);

        menuFitur.add(submenuPenjualan);

        submenuShipment.setText("Shipment...");

        itemmenuKeluarShipment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/arrow_right.png"))); // NOI18N
        itemmenuKeluarShipment.setText("Barang Keluar");
        itemmenuKeluarShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuKeluarShipmentActionPerformed(evt);
            }
        });
        submenuShipment.add(itemmenuKeluarShipment);

        itemmenuMasukShipment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/arrow_left.png"))); // NOI18N
        itemmenuMasukShipment.setText("Barang Masuk");
        itemmenuMasukShipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuMasukShipmentActionPerformed(evt);
            }
        });
        submenuShipment.add(itemmenuMasukShipment);

        menuFitur.add(submenuShipment);

        submenuExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/doc_pdf.png"))); // NOI18N
        submenuExport.setText("Export..");

        itemmenuStokPDF.setText("Stok (PDF)");
        itemmenuStokPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemmenuStokPDFActionPerformed(evt);
            }
        });
        submenuExport.add(itemmenuStokPDF);

        menuFitur.add(submenuExport);
        menuFitur.add(jSeparator5);

        submenuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        submenuExit.setText("Exit");
        submenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submenuExitActionPerformed(evt);
            }
        });
        menuFitur.add(submenuExit);

        MenuBar.add(menuFitur);

        menuBantuan.setText("Bantuan");

        submenuKontenBantuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/help.png"))); // NOI18N
        submenuKontenBantuan.setText("Konten Bantuan");
        submenuKontenBantuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submenuKontenBantuanActionPerformed(evt);
            }
        });
        menuBantuan.add(submenuKontenBantuan);

        submenuTentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/exclamation.png"))); // NOI18N
        submenuTentang.setText("Tentang");
        submenuTentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submenuTentangActionPerformed(evt);
            }
        });
        menuBantuan.add(submenuTentang);

        MenuBar.add(menuBantuan);

        menuCurrentUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/current_user.png"))); // NOI18N
        menuCurrentUser.setText("Current User");

        submenuLogout.setText("Logout");
        submenuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submenuLogoutActionPerformed(evt);
            }
        });
        menuCurrentUser.add(submenuLogout);

        MenuBar.add(menuCurrentUser);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFitur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFitur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
//Start toolbar button function
    private void btnStokFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokFiturActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardStok");
        ShowDataStok();
        ShowDataShipment();
    }//GEN-LAST:event_btnStokFiturActionPerformed

    private void btnTansaksiFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTansaksiFiturActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardTransaksi");
    }//GEN-LAST:event_btnTansaksiFiturActionPerformed

    private void btnPenjualanFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjualanFiturActionPerformed
        ShowDataTransaksi("semua");
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardPenjualan");
    }//GEN-LAST:event_btnPenjualanFiturActionPerformed

    private void btnChartFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartFiturActionPerformed
        dtcmbDariGraph.setDate(new Date());
        dtcmbSampaiGraph.setDate(new Date());
        panelGraph.removeAll();
        panelGraph.validate();
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardGraph");
    }//GEN-LAST:event_btnChartFiturActionPerformed

    private void btnUserFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserFiturActionPerformed
        ShowDataUser();
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardUser");
    }//GEN-LAST:event_btnUserFiturActionPerformed
//End
    
//Menubar function    
    private void itemmenuTambahStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuTambahStokActionPerformed
        if (Admin.equals("Iya")) {
            HapusSemuaDaftarTransaksi();
            
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStokOrderByJenis();
            String[] datacmb = new String[getAllStok.size()+1];
            datacmb[0] = "";
            int j = 1;
            for (ModelStok s : getAllStok) {
                datacmb[j] = String.valueOf(s.getJenis());
                j++;
            }
            cmbJenisFormTambahStok.setModel(new DefaultComboBoxModel(datacmb));
            
            formTambahStok.pack();
            formTambahStok.setVisible(true);
            
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat menambahkan stok baru");
        }
    }//GEN-LAST:event_itemmenuTambahStokActionPerformed

    private void itemmenuEditStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuEditStokActionPerformed
        if (Admin.equals("Iya")) {
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStok();
            Object[][] data = new Object[getAllStok.size()][2];
            int i = 0;
            for (ModelStok s : getAllStok) {
                data[i][0] = s.getId();
                data[i][1] = s.getNama();
                i++;
            }
            
            List<ModelStok> getAllStokjenis = new DAOStok(a.getConnection()).getAllStokOrderByJenis();
            String[] datacmb = new String[getAllStokjenis.size()+1];
            datacmb[0] = "";
            int j = 1;
            for (ModelStok q : getAllStokjenis) {
                datacmb[j] = String.valueOf(q.getJenis());
                j++;
            }
            cmbJenisFormEditStok.setModel(new DefaultComboBoxModel(datacmb));
            
            final Class<?>[] columnClasses = {String.class, String.class};
            DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"ID","Nama"}){
                @Override
                  public Class<?> getColumnClass(int columnIndex)
                  {
                    return columnClasses[columnIndex];
                  }
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tabelBarangFormEditStok.setModel(tabelmodel);
            
            HapusSemuaDaftarTransaksi();
            formEditStok.pack();
            formEditStok.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat mengedit stok");
        }
    }//GEN-LAST:event_itemmenuEditStokActionPerformed

    private void itemmenuHapusStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuHapusStokActionPerformed
        if (Admin.equals("Iya")) {
            HapusSemuaDaftarTransaksi();
            CardLayout card = (CardLayout)PanelFitur.getLayout();
            card.show(PanelFitur, "cardStok");
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat mengedit stok");
        }
    }//GEN-LAST:event_itemmenuHapusStokActionPerformed

    private void itemmenuLihatStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuLihatStokActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardStok");
        ShowDataShipment();
    }//GEN-LAST:event_itemmenuLihatStokActionPerformed

    private void itemmenuKeluarShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuKeluarShipmentActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardStok");
        HapusSemuaDaftarTransaksi();
        ShowDataShipment();
    }//GEN-LAST:event_itemmenuKeluarShipmentActionPerformed

    private void itemmenuMasukShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuMasukShipmentActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardStok");
        HapusSemuaDaftarTransaksi();
        ShowDataShipment();
    }//GEN-LAST:event_itemmenuMasukShipmentActionPerformed

    private void itemmenuStokPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuStokPDFActionPerformed
        HapusSemuaDaftarTransaksi();
        try {
            JOptionPane.showMessageDialog(this, "Export PDF berhasil" , "Message", JOptionPane.INFORMATION_MESSAGE);
            PDF pdf = new PDF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export PDF gagal "+e , "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_itemmenuStokPDFActionPerformed

    private void submenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submenuExitActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            HapusSemuaDaftarTransaksi();
            System.exit(0);
        }
    }//GEN-LAST:event_submenuExitActionPerformed

    private void submenuKontenBantuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submenuKontenBantuanActionPerformed
        HapusSemuaDaftarTransaksi();
    }//GEN-LAST:event_submenuKontenBantuanActionPerformed

    private void submenuTentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submenuTentangActionPerformed
        HapusSemuaDaftarTransaksi();
    }//GEN-LAST:event_submenuTentangActionPerformed

    private void submenuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submenuLogoutActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            Login l = new Login();
            l.setVisible(true);
            l.User = "";
            l.Admin = "";
            Admin = "";
            HapusSemuaDaftarTransaksi();
            this.dispose();
        }
    }//GEN-LAST:event_submenuLogoutActionPerformed

    private void itemmenuPelunasanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemmenuPelunasanPenjualanActionPerformed
        CardLayout card = (CardLayout)PanelFitur.getLayout();
        card.show(PanelFitur, "cardPenjualan");
        HapusSemuaDaftarTransaksi();
    }//GEN-LAST:event_itemmenuPelunasanPenjualanActionPerformed
//End

    
//Fitur Transaksi    
    private void txtNamaBarangTransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaBarangTransaksiKeyReleased
        if (txtNamaBarangTransaksi.getText().isEmpty()) {
            DefaultTableModel kosong = new DefaultTableModel(new Object[]{"Nama","Harga < 80","Harga > 90","Stok"},0);
            tblBarangTransaksi.setModel(kosong);
        }else{
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).search(txtNamaBarangTransaksi.getText(), "nama");
            Object[][] data = new Object[getAllStok.size()][4];
            int i = 0;
            for (ModelStok s : getAllStok) {
                data[i][0] = s.getNama();
                data[i][1] = s.getHarga80();
                data[i][2] = s.getHarga90();
                data[i][3] = s.getStok();
                i++;
            }
            DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Nama","Harga < 80","Harga > 90","Stok"}){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblBarangTransaksi.setModel(tabelmodel);
        }
    }//GEN-LAST:event_txtNamaBarangTransaksiKeyReleased

    private void btnTambahBarangTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBarangTransaksiActionPerformed
        if (this.tblBarangTransaksi.getSelectedRowCount()==0 || txtPcsTransaksi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Pilih barang dan tentukan jumlah beli");
        }else{
            double pcs = Double.parseDouble(txtPcsTransaksi.getValue().toString());
            double sisastok = Double.parseDouble(this.tblBarangTransaksi.getValueAt(this.tblBarangTransaksi.getSelectedRow(), 3).toString())-pcs;
            
            if (sisastok<0) {
                JOptionPane.showMessageDialog(null,"Stok tidak mencukupi");
            }else{
                double harga = 0;
                String satuan="";
                List<ModelStok> getAllStok = new DAOStok(a.getConnection()).search(this.tblBarangTransaksi.getValueAt(this.tblBarangTransaksi.getSelectedRow(), 0).toString(), "nama");
                String[][] data = new String[getAllStok.size()][1];
                int i = 0;
                for (ModelStok s : getAllStok) {
                    data[i][0] = String.valueOf(s.getSatuan());
                    satuan=data[i][0];
                    i++;
                }
                String nama = this.tblBarangTransaksi.getValueAt(this.tblBarangTransaksi.getSelectedRow(), 0).toString();
                if(pcs>90){
                    harga = Double.parseDouble(this.tblBarangTransaksi.getValueAt(this.tblBarangTransaksi.getSelectedRow(), 2).toString());
                }else{
                    harga = Double.parseDouble(this.tblBarangTransaksi.getValueAt(this.tblBarangTransaksi.getSelectedRow(), 1).toString());
                }
                double total = pcs*harga;
                if(sisastok<=0){
                    JOptionPane.showMessageDialog(this, "Stok tidak mencukupi", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    DefaultTableModel kosong = new DefaultTableModel(new Object[]{"Nama","Harga < 80","Harga > 90","Stok"},0);
                    DefaultTableModel model = (DefaultTableModel)tblDaftarBeliTransaksi.getModel();
                    model.addRow(new Object[]{nama,pcs,satuan,(int)harga,(int)total});
                    txtNamaBarangTransaksi.setText("");
                    txtPcsTransaksi.setText("");
                    txtPcsTransaksi.setValue(null);
                    UpdateStok(sisastok, nama);
                    tblBarangTransaksi.setModel(kosong);
                    Counting();
                }
            }
        
        }
    }//GEN-LAST:event_btnTambahBarangTransaksiActionPerformed

    private void txtPembeliTransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPembeliTransaksiKeyReleased
        labelPembeliTransaksi.setText(txtPembeliTransaksi.getText());
    }//GEN-LAST:event_txtPembeliTransaksiKeyReleased

    private void txtDiskonTransaksiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiskonTransaksiFocusLost
        Counting();
    }//GEN-LAST:event_txtDiskonTransaksiFocusLost

    private void txtCashTransaksiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashTransaksiFocusLost
        Counting();
    }//GEN-LAST:event_txtCashTransaksiFocusLost

    private void btnHapusDaftarTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDaftarTransaksiActionPerformed
        if (this.tblDaftarBeliTransaksi.getSelectedRow()<0) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang batal dibeli", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
        }else{
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStokByNama(this.tblDaftarBeliTransaksi.getValueAt(tblDaftarBeliTransaksi.getSelectedRow(), 0).toString());
            double stoklama = 0;
            double pcs = Double.parseDouble(this.tblDaftarBeliTransaksi.getValueAt(tblDaftarBeliTransaksi.getSelectedRow(), 1).toString());
            String nama ="";
            int j = 0;
            for (ModelStok s : getAllStok) {
                nama = s.getNama();
                stoklama = s.getStok();
                j++;
            }
            UpdateStok(stoklama+pcs, nama);
            DefaultTableModel model = (DefaultTableModel)tblDaftarBeliTransaksi.getModel();
            model.removeRow(tblDaftarBeliTransaksi.getSelectedRow());
            tblDaftarBeliTransaksi.setModel(model);
        }
    }//GEN-LAST:event_btnHapusDaftarTransaksiActionPerformed

    private void btnHapusSemuaDaftarTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSemuaDaftarTransaksiActionPerformed
        HapusSemuaDaftarTransaksi();
    }//GEN-LAST:event_btnHapusSemuaDaftarTransaksiActionPerformed

    private void btnOkeTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkeTransaksiActionPerformed
        if (this.tblDaftarBeliTransaksi.getRowCount()==0) {
            JOptionPane.showMessageDialog(this, "Tidak ada barang yang dibeli", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
        }else{
            if (JOptionPane.showConfirmDialog(null, "Semua sudah pasti?", "Transaksi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                try {
                    r.idTransaksi = "";
                    r.UserLogin = menuCurrentUser.getText();
                    r.Pembeli = labelPembeliTransaksi.getText();
                    r.total = labelTotal2Transaksi.getText();
                    r.diskon = labelDiskonTransaksi.getText();
                    for (int i = 0; i < tblDaftarBeliTransaksi.getRowCount(); i++) {
                        r.daftarbeli.add(tblDaftarBeliTransaksi.getValueAt(i, 0).toString());
                        r.daftarpcs.add(tblDaftarBeliTransaksi.getValueAt(i, 1).toString());
                        r.daftarsatuan.add(tblDaftarBeliTransaksi.getValueAt(i, 2).toString());
                        r.daftarharga.add(tblDaftarBeliTransaksi.getValueAt(i, 3).toString());
                        r.daftartotal.add(tblDaftarBeliTransaksi.getValueAt(i, 4).toString());
                    }
                    InsertTransaksi();
                    InsertDetailTransaksi();
                    DefaultTableModel kosong = new DefaultTableModel(new Object[]{"Nama","Pcs.","Satuan","Harga","Jumlah"},0);
                    tblDaftarBeliTransaksi.setModel(kosong);
                    txtCashTransaksi.setValue(0);
                    txtDiskonTransaksi.setValue(0);
                    txtPembeliTransaksi.setText("");
                    r.Struk();
                    r.daftarbeli.clear();
                    r.daftarharga.clear();
                    r.daftarpcs.clear();
                    r.daftarsatuan.clear();
                    r.daftartotal.clear();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Transaksi gagal", "Peringatan", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnOkeTransaksiActionPerformed
//End


//Fitur Stok
    private void btnHapusStokStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusStokStokActionPerformed
        HapusSemuaDaftarTransaksi();
        if (Admin.equals("Iya")) {
            if (tabelStokStok.getSelectedRow()==-1) {
                JOptionPane.showMessageDialog(null, "Pilih barang yang akan anda hapus");
            }else{
                for (int i = 0; i < tabelStokStok.getSelectedRowCount(); i++) {
                    daftarhapus.add(tabelStokStok.getValueAt(tabelStokStok.getSelectedRow()+i, 0));
                }
                if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin menghapus?", "Menghapus ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    for (int i = 0; i < daftarhapus.size(); i++) {
                        try {
                            String hasil = st.deleteStok(daftarhapus.get(i).toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ShowDataStok();
                        ShowDataShipment();
                    }
                }
                daftarhapus.clear();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat menghapus stok");
        }
    }//GEN-LAST:event_btnHapusStokStokActionPerformed

    private void btnTambahStokStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahStokStokActionPerformed
        if (Admin.equals("Iya")) {
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStokOrderByJenis();
            String[] datacmb = new String[getAllStok.size()+1];
            datacmb[0] = "";
            int j = 1;
            for (ModelStok s : getAllStok) {
                datacmb[j] = String.valueOf(s.getJenis());
                j++;
            }
            cmbJenisFormTambahStok.setModel(new DefaultComboBoxModel(datacmb));
            
            formTambahStok.pack();
            formTambahStok.setVisible(true);
            HapusSemuaDaftarTransaksi();
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat menambahkan stok baru");
        }
    }//GEN-LAST:event_btnTambahStokStokActionPerformed

    private void btnEditStokStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditStokStokActionPerformed
        if (Admin.equals("Iya")) {
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStok();
            Object[][] data = new Object[getAllStok.size()][2];
            int i = 0;
            for (ModelStok s : getAllStok) {
                data[i][0] = s.getId();
                data[i][1] = s.getNama();
                i++;
            }
            
            List<ModelStok> getAllStokjenis = new DAOStok(a.getConnection()).getAllStokOrderByJenis();
            String[] datacmb = new String[getAllStokjenis.size()+1];
            datacmb[0] = "";
            int j = 1;
            for (ModelStok q : getAllStokjenis) {
                datacmb[j] = String.valueOf(q.getJenis());
                j++;
            }
            cmbJenisFormEditStok.setModel(new DefaultComboBoxModel(datacmb));
            
            final Class<?>[] columnClasses = {String.class, String.class};
            DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"ID","Nama"}){
                @Override
                  public Class<?> getColumnClass(int columnIndex)
                  {
                    return columnClasses[columnIndex];
                  }
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tabelBarangFormEditStok.setModel(tabelmodel);
            
            HapusSemuaDaftarTransaksi();
            formEditStok.pack();
            formEditStok.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Hanya administrator yang dapat mengedit stok");
        }
    }//GEN-LAST:event_btnEditStokStokActionPerformed

    private void btnPDFStokStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFStokStokActionPerformed
        HapusSemuaDaftarTransaksi();
        try {
            JOptionPane.showMessageDialog(this, "Export PDF berhasil" , "Message", JOptionPane.INFORMATION_MESSAGE);
            PDF pdf = new PDF();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export PDF gagal "+e , "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPDFStokStokActionPerformed

    private void btnOkeShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkeShipmentActionPerformed
        try {
            double jumlah = Double.parseDouble(txtJumlahShipment.getValue().toString());
            String status = "";
            if (rbtnMasukShipment.isSelected()) {
                status = "masuk";
            }
            if (rbtnKeluarShipment.isSelected()) {
                status = "keluar";
            }
            if (txtJumlahShipment.getText().isEmpty()||status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Maaf, tentukan jumlah dan statusnya dahulu");
            }else{

                if (status.equals("masuk")) {
                    InsertShipment(status);
                }else{
                    InsertShipment(status);
                }
            }
            ShowDataShipment();
            ShowDataStok();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf, jumlah harus angka");
        }
    }//GEN-LAST:event_btnOkeShipmentActionPerformed

    private void tgbKeluarShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbKeluarShipmentActionPerformed
        ShowDataShipmentBy("status", "keluar");
    }//GEN-LAST:event_tgbKeluarShipmentActionPerformed

    private void tgbMasukShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbMasukShipmentActionPerformed
        ShowDataShipmentBy("status", "masuk");
    }//GEN-LAST:event_tgbMasukShipmentActionPerformed

    private void tgbSemuaShipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbSemuaShipmentActionPerformed
        ShowDataShipment();
    }//GEN-LAST:event_tgbSemuaShipmentActionPerformed
//End    

    
//Counting    
    private void txtCashTransaksiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashTransaksiFocusGained
        Counting();
    }//GEN-LAST:event_txtCashTransaksiFocusGained

    private void btnOkeTransaksiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnOkeTransaksiFocusGained
        Counting();
    }//GEN-LAST:event_btnOkeTransaksiFocusGained
//End  
   
    
//Setting & Logout    
    private void btnSettingFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingFiturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSettingFiturActionPerformed

    private void btnLogoutFiturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutFiturActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            Login l = new Login();
            l.setVisible(true);
            l.User = "";
            l.Admin = "";
            Admin = "";
            HapusSemuaDaftarTransaksi();
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutFiturActionPerformed
//End


//Data Penejualan    
    private void tgbtnSemuaPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbtnSemuaPenjualanActionPerformed
        ShowDataTransaksi("semua");
    }//GEN-LAST:event_tgbtnSemuaPenjualanActionPerformed

    private void tgbtnLunasPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbtnLunasPenjualanActionPerformed
        ShowDataTransaksi("Lunas");
    }//GEN-LAST:event_tgbtnLunasPenjualanActionPerformed

    private void tgbtnBelumPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgbtnBelumPenjualanActionPerformed
        ShowDataTransaksi("Belum");
    }//GEN-LAST:event_tgbtnBelumPenjualanActionPerformed

    private void btnTambahPelunasanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPelunasanPenjualanActionPerformed
        if (tabelTransaksiPenjualan.getSelectedRow()==-1||txtUangPelunasanPenjualan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pilih Transaksi yang akan di tambahkan pelunasannya, dan isi yang dibayarkan");
        }else{
            if (this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 8).toString().equals("Lunas")) {
                JOptionPane.showMessageDialog(null, "Transaksi sudah lunas");
            }else{
                int sisa = Integer.parseInt(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 6).toString());
                int tambahan = Integer.parseInt(txtUangPelunasanPenjualan.getText());
                int sisabaru = sisa-tambahan;
                if(tambahan>sisa){
                    JOptionPane.showMessageDialog(null, "Jumlah yang akan ditambahkan melebihi sisa yang harus dibayarkan");
                }else{
                    if(sisabaru==0){
                        try {
                            String hasil = t.updateTransaksi(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 0).toString(), sisabaru, "lunas");
                            JOptionPane.showMessageDialog(this, hasil, "Message", JOptionPane.INFORMATION_MESSAGE);
                            ShowDataTransaksi("semua");
                            ShowDetailTransaksi("a");
                            txtUangPelunasanPenjualan.setText("");
                            labelIdTransaksiPenjualan.setText("");
                            labelTotalTransaksiPenjualan.setText("");
                            labelSisaTransaksiPenjualan.setText("");
                            labelPembeliTransaksiPenjualan.setText("");
                        } catch (Exception e) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }else{
                        try {
                            String hasil = t.updateTransaksi(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 0).toString(), sisabaru, "belum");
                            JOptionPane.showMessageDialog(this, hasil, "Message", JOptionPane.INFORMATION_MESSAGE);
                            ShowDataTransaksi("semua");
                            ShowDetailTransaksi("a");
                            txtUangPelunasanPenjualan.setText("");
                            labelIdTransaksiPenjualan.setText("");
                            labelTotalTransaksiPenjualan.setText("");
                            labelSisaTransaksiPenjualan.setText("");
                            labelPembeliTransaksiPenjualan.setText("");
                        } catch (Exception e) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnTambahPelunasanPenjualanActionPerformed

    private void txtUangPelunasanPenjualanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUangPelunasanPenjualanKeyReleased
        try {
            int jumlah = Integer.parseInt(txtUangPelunasanPenjualan.getText());
            if (txtUangPelunasanPenjualan.getText().length()==1) {
                if (txtUangPelunasanPenjualan.getText().equals("0")){
                    txtUangPelunasanPenjualan.setText(""+txtUangPelunasanPenjualan.getText().substring(0, txtUangPelunasanPenjualan.getText().length() - 1));
                }
            }
                      
        } catch (Exception z) { 
            if (txtUangPelunasanPenjualan.getText().length()>=1) {
                txtUangPelunasanPenjualan.setText(""+txtUangPelunasanPenjualan.getText().substring(0, txtUangPelunasanPenjualan.getText().length() - 1));
            }
            return;
        }
    }//GEN-LAST:event_txtUangPelunasanPenjualanKeyReleased

    private void tabelTransaksiPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiPenjualanMouseClicked
        ShowDetailTransaksi(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 0).toString());
        labelIdTransaksiPenjualan.setText(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 0).toString());
        labelTotalTransaksiPenjualan.setText(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 4).toString());
        labelSisaTransaksiPenjualan.setText(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 6).toString());
        labelPembeliTransaksiPenjualan.setText(this.tabelTransaksiPenjualan.getValueAt(this.tabelTransaksiPenjualan.getSelectedRow(), 7).toString());
    }//GEN-LAST:event_tabelTransaksiPenjualanMouseClicked
//End
    

//Graph    
    private void btnOkeGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkeGraphActionPerformed
        JFreeChart barChart = ChartFactory.createBarChart("","","Transaksi",createDataset(dtcmbDariGraph.getFormattedDate(),dtcmbSampaiGraph.getFormattedDate()),PlotOrientation.VERTICAL,true, true, false);
        ChartPanel cp = new ChartPanel(barChart);
        cp.setDomainZoomable(true);
        panelGraph.setLayout(new java.awt.BorderLayout());
        panelGraph.add(cp,BorderLayout.CENTER);
        panelGraph.validate();
    }//GEN-LAST:event_btnOkeGraphActionPerformed
//End
    

//User    
    private void btnOkeUserTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkeUserTambahActionPerformed
        if (Admin.equals("Iya")) {
            String adminbaru = "";
            if (swbtnAdminTambah.isSelected()){
                adminbaru = "Iya";
            }else{
                adminbaru = "Tidak";
            }
            if(txtNamaUserTambah.getText().isEmpty()||txtUsernameUserTambah.getText().isEmpty()||txtPassUserTambah.getText().isEmpty()||adminbaru.isEmpty()){
                JOptionPane.showMessageDialog(this, "Harap Diisi dengan Lengkap","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
            }else{
                List<ModelUser> getbyUsername = new DAOUser(a.getConnection()).check(txtUsernameUserTambah.getText(), "username");
                String[][] data = new String[getbyUsername.size()][1];
                int count = 0;
                int i = 0;
                for (ModelUser m : getbyUsername){

                    data[i][0] = Integer.toString(m.getCount());

                    count = Integer.parseInt(data[i][0]);

                    i++;
                }

                if(count>=1){
                    JOptionPane.showMessageDialog(this, "Username tersebut sudah ada,Coba yang lain","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    ModelUser u = new ModelUser();
                    u.setNama(txtNamaUserTambah.getText());
                    u.setUsername(txtUsernameUserTambah.getText());
                    u.setPassword(txtPassUserTambah.getText());
                    u.setAdmin(adminbaru);

                    if (new DAOUser(new DBConnection().getConnection()).insertUser(u) == true) {
                        JOptionPane.showMessageDialog(this, "Data User berhasil di input", "Message", JOptionPane.INFORMATION_MESSAGE);
                        txtNamaUserTambah.setText("");
                        txtUsernameUserTambah.setText("");
                        txtPassUserTambah.setText("");
                        ShowDataUser();
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Maaf, anda bukan administrator");
        }
        
    }//GEN-LAST:event_btnOkeUserTambahActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        if (Admin.equals("Iya")) {
            if(tabelUser.getSelectedRow()==-1){
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus", "PERINGATAN", JOptionPane.WARNING_MESSAGE);
            }else{
                if(this.tabelUser.getValueAt(tabelUser.getSelectedRow(), 0).toString().equals(menuCurrentUser.getText())){
                    JOptionPane.showMessageDialog(this, "Tidak bisa menghapus akun anda sendiri", "PERINGATAN", JOptionPane.WARNING_MESSAGE);
                }else{
                    List<ModelUser> getbyId = new DAOUser(a.getConnection()).search(this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 1).toString(), "username", this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 0).toString(),"nama");
                    String[][] data = new String[getbyId.size()][4];

                    String id = "";
                    int i = 0;
                    for (ModelUser m : getbyId){

                        data[i][0] = Integer.toString(m.getId());

                        id=data[i][0];
                        i++;
                    }
                    if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin?", "Menghapus "+this.tabelUser.getValueAt(tabelUser.getSelectedRow(), 0).toString(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        try {
                            String hasil = u.deleteUser(id);
                            JOptionPane.showMessageDialog(this, hasil, "Message", JOptionPane.INFORMATION_MESSAGE);
                            ShowDataUser();

                        } catch (SQLException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Maaf, anda bukan administrator");
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnOkeEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkeEditUserActionPerformed
        if (Admin.equals("Iya")) {
            String id = "";
            String adminbaru="";
            if (swbtnAdminEdit.isSelected()){
                adminbaru = "Iya";
            }else{
                adminbaru = "Tidak";
            }
            if (tabelUser.getSelectedRow()==-1||txtNamaUserEdit.getText().isEmpty()||txtUsernameUserEdit.getText().isEmpty()||txtPassLamaUserEdit.getText().isEmpty()||txtPassBaruUserEdit.getText().isEmpty()||adminbaru.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap Diisi dengan Lengkap","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
            }else{
                List<ModelUser> getbyId = new DAOUser(a.getConnection()).search(this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 1).toString(), "username", this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 0).toString(),"nama");
                String[][] data = new String[getbyId.size()][4];
                
                String passlama = "";
                int i = 0;
                for (ModelUser m : getbyId){

                    data[i][0] = Integer.toString(m.getId());
                    data[i][1] = String.valueOf(m.getPassword());
                    
                    id=data[i][0];
                    passlama = data[i][1];
                    i++;
                }
                if (!txtPassLamaUserEdit.getText().equals(passlama)) {
                    JOptionPane.showMessageDialog(this, "Password lama harus benar", "Message", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    try {
                        String hasil = u.updateUser(id, txtNamaUserEdit.getText(), txtUsernameUserEdit.getText(), txtPassBaruUserEdit.getText(), adminbaru);
                        JOptionPane.showMessageDialog(this, hasil, "Message", JOptionPane.INFORMATION_MESSAGE);
                        id = "";
                        txtNamaUserEdit.setText("");
                        txtUsernameUserEdit.setText("");
                        txtPassLamaUserEdit.setText("");
                        txtPassBaruUserEdit.setText("");
                        adminbaru = "";
                        ShowDataUser();
                    } catch (Exception e) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Maaf, anda bukan administrator");
        }
    }//GEN-LAST:event_btnOkeEditUserActionPerformed

    private void tabelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelUserMouseClicked
        txtNamaUserEdit.setText(this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 0).toString());
        txtUsernameUserEdit.setText(this.tabelUser.getValueAt(this.tabelUser.getSelectedRow(), 1).toString());
    }//GEN-LAST:event_tabelUserMouseClicked
//End
    

//Edit Stok    
    private void btnTambahkanFormTambahStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahkanFormTambahStokActionPerformed
        String jenis="";
        if(txtJenisFormTambahStok.getText().isEmpty()){
            jenis = cmbJenisFormTambahStok.getSelectedItem().toString();
        }else{
            jenis = txtJenisFormTambahStok.getText();
        }
        if (txtIdFormTambahStok.getText().isEmpty()||txtNamaFormTambahStok.getText().isEmpty()||txtHarga80FormTambahStok.getText().isEmpty()||txtHarga90FormTambahStok.getText().isEmpty()||txtSatuanFormTambahStok.getText().isEmpty()||txtJumlahFormTambahStok.getText().isEmpty()||jenis.equals("")) {
            JOptionPane.showMessageDialog(this, "Harap id, jenis, nama, harga,dan jumlah stok diisi dengan Lengkap","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            List<ModelStok> getbyNamaBarang = new DAOStok(a.getConnection()).check(txtNamaFormTambahStok.getText(), "nama");
            String[][] data = new String[getbyNamaBarang.size()][1];
            int count = 0;
            int i = 0;
            for (ModelStok m : getbyNamaBarang){
                data[i][0] = Integer.toString(m.getCount());
                count = Integer.parseInt(data[i][0]);
                i++;
            }   
            if(count>=1){
                JOptionPane.showMessageDialog(this, "Nama barang tersebut sudah ada, Coba yang lain","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
            }else{
                ModelStok st = new ModelStok();
                st.setId(txtIdFormTambahStok.getText());
                st.setNama(txtNamaFormTambahStok.getText());
                st.setJenis(jenis);
                st.setHarga80(Integer.parseInt(txtHarga80FormTambahStok.getText()));
                st.setHarga90(Integer.parseInt(txtHarga90FormTambahStok.getText()));
                st.setSatuan(txtSatuanFormTambahStok.getText());
                st.setStok(Double.parseDouble(txtJumlahFormTambahStok.getText()));
                st.setKeterangan(txtKeteranganFormTambahStok.getText());
                if (new DAOStok(new DBConnection().getConnection()).insertStok(st) == true) {
                    JOptionPane.showMessageDialog(this, "Data Barang berhasil di input", "Message", JOptionPane.INFORMATION_MESSAGE);
                    txtIdFormTambahStok.setText("");
                    txtNamaFormTambahStok.setText("");
                    txtJenisFormTambahStok.setText("");
                    txtHarga80FormTambahStok.setText("");
                    txtHarga90FormTambahStok.setText("");
                    txtSatuanFormTambahStok.setText("");
                    txtJumlahFormTambahStok.setText("");
                    txtKeteranganFormTambahStok.setText("");
                    jenis = "";
                    ShowDataStok();
                    ShowDataShipment();
                    formTambahStok.setVisible(false);
                    formTambahStok.dispose();
                }
            }
        }
    }//GEN-LAST:event_btnTambahkanFormTambahStokActionPerformed

    private void tabelBarangFormEditStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangFormEditStokMouseClicked
        txtIdFormEditStok.setEnabled(false);
        List<ModelStok> getbyEdit = new DAOStok(a.getConnection()).getAllStokById(this.tabelBarangFormEditStok.getValueAt(this.tabelBarangFormEditStok.getSelectedRow(), 0).toString());
        for (ModelStok m : getbyEdit){
            txtIdFormEditStok.setText(String.valueOf(m.getId()));
            txtNamaFormEditStok.setText(String.valueOf(m.getNama()));
            txtJenisFormEditStok.setText(String.valueOf(m.getJenis()));
            txtHarga80FormEditStok.setText(Integer.toString(m.getHarga80()));
            txtHarga90FormEditStok.setText(Integer.toString(m.getHarga90()));
            txtSatuanFormEditStok.setText(m.getSatuan());
            txtJumlahFormEditStok.setText(Double.toString(m.getStok()));
            txtKeteranganFormEditStok.setText(String.valueOf(m.getKeterangan()));
        }
    }//GEN-LAST:event_tabelBarangFormEditStokMouseClicked

    private void cmbJenisFormTambahStokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisFormTambahStokItemStateChanged
        if(cmbJenisFormTambahStok.getSelectedIndex()!=0){
            txtJenisFormTambahStok.setText("");
        }
    }//GEN-LAST:event_cmbJenisFormTambahStokItemStateChanged

    private void txtJenisFormTambahStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJenisFormTambahStokKeyReleased
        cmbJenisFormTambahStok.setSelectedIndex(0);
    }//GEN-LAST:event_txtJenisFormTambahStokKeyReleased

    private void txtJenisFormEditStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJenisFormEditStokKeyReleased
        cmbJenisFormEditStok.setSelectedIndex(0);
    }//GEN-LAST:event_txtJenisFormEditStokKeyReleased

    private void cmbJenisFormEditStokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisFormEditStokItemStateChanged
        if(cmbJenisFormEditStok.getSelectedIndex()!=0){
            txtJenisFormEditStok.setText("");
        }
    }//GEN-LAST:event_cmbJenisFormEditStokItemStateChanged

    private void btnTambahkanFormEditStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahkanFormEditStokActionPerformed
        String jenis="";
        if(txtJenisFormEditStok.getText().isEmpty()){
            jenis = cmbJenisFormEditStok.getSelectedItem().toString();
        }else{
            jenis = txtJenisFormEditStok.getText();
        }
        if (txtIdFormEditStok.getText().isEmpty()||txtNamaFormEditStok.getText().isEmpty()||txtHarga80FormEditStok.getText().isEmpty()||txtHarga90FormEditStok.getText().isEmpty()||txtSatuanFormEditStok.getText().isEmpty()||txtJumlahFormEditStok.getText().isEmpty()||jenis.equals("")) {
            JOptionPane.showMessageDialog(this, "Harap id, jenis, nama, harga,dan jumlah stok diisi dengan Lengkap","PERINGATAN!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                String hasil = st.updateStok(txtIdFormEditStok.getText(), txtNamaFormEditStok.getText(), jenis, Integer.parseInt(txtHarga80FormEditStok.getText()), Integer.parseInt(txtHarga90FormEditStok.getText()),txtSatuanFormEditStok.getText(), Double.parseDouble(txtJumlahFormEditStok.getText()), txtKeteranganFormEditStok.getText());
                JOptionPane.showMessageDialog(this, hasil, "Message", JOptionPane.INFORMATION_MESSAGE);
                txtIdFormEditStok.setText("");
                txtNamaFormEditStok.setText("");
                txtJenisFormEditStok.setText("");
                txtHarga80FormEditStok.setText("");
                txtHarga90FormEditStok.setText("");
                txtSatuanFormEditStok.setText("");
                txtJumlahFormEditStok.setText("");
                txtKeteranganFormEditStok.setText("");
                jenis = "";
                ShowDataStok();
                ShowDataShipment();
                formEditStok.setVisible(false);
                formEditStok.dispose();
            } catch (Exception e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_btnTambahkanFormEditStokActionPerformed

    private void txtJumlahFormTambahStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahFormTambahStokKeyReleased
        try {
            double jumlah = Double.parseDouble(txtJumlahFormTambahStok.getText());
            if (txtJumlahFormTambahStok.getText().length()==1) {
                if (txtJumlahFormTambahStok.getText().equals("0")){
                    txtJumlahFormTambahStok.setText(""+txtJumlahFormTambahStok.getText().substring(0, txtJumlahFormTambahStok.getText().length() - 1));
                }
            }
                      
        } catch (Exception z) { 
            if (txtJumlahFormTambahStok.getText().length()>=1) {
                txtJumlahFormTambahStok.setText(""+txtJumlahFormTambahStok.getText().substring(0, txtJumlahFormTambahStok.getText().length() - 1));
            }
            return;
        }
    }//GEN-LAST:event_txtJumlahFormTambahStokKeyReleased

    private void txtJumlahFormEditStokKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahFormEditStokKeyReleased
        try {
            double jumlah = Double.parseDouble(txtJumlahFormEditStok.getText());
            if (txtJumlahFormEditStok.getText().length()==1) {
                if (txtJumlahFormEditStok.getText().equals("0")){
                    txtJumlahFormEditStok.setText(""+txtJumlahFormEditStok.getText().substring(0, txtJumlahFormEditStok.getText().length() - 1));
                }
            }
                      
        } catch (Exception z) { 
            if (txtJumlahFormEditStok.getText().length()>=1) {
                txtJumlahFormEditStok.setText(""+txtJumlahFormEditStok.getText().substring(0, txtJumlahFormEditStok.getText().length() - 1));
            }
            return;
        }
    }//GEN-LAST:event_txtJumlahFormEditStokKeyReleased
//End
    
    
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel(){

                @Override
                protected void loadCustomXML() throws ParseException {
                    loadXMLConfig("/Asset/custom.xml");
                }
            
            });
            UIManager.put("Synthetica.rootPane.headerShadow.type", "TITLEPANE_ONLY");


        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fitur().setVisible(true);
            }
        });
    }
    
    
    private void ShowDataStok(){
        List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStok();
        Object[][] data = new Object[getAllStok.size()][8];
        int i = 0;
        for (ModelStok s : getAllStok) {
            data[i][0] = s.getId();
            data[i][1] = s.getNama();
            data[i][2] = s.getJenis();
            data[i][3] = s.getHarga80();
            data[i][4] = s.getHarga90();
            data[i][5] = s.getSatuan();
            data[i][6] = s.getStok();
            data[i][7] = s.getKeterangan();
            i++;
        }
        final Class<?>[] columnClasses = {String.class, String.class, String.class, Integer.class, Integer.class, String.class, Integer.class, String.class};
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"ID","Nama","Jenis","Harga < 80","Harga > 90","Satuan","Stok","Keterangan"}){
            @Override
              public Class<?> getColumnClass(int columnIndex)
              {
                return columnClasses[columnIndex];
              }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelStokStok.setModel(tabelmodel);
    }
    
    private void UpdateStok(double jumlah, String namabarang){
        try {
            String hasil = st.updateStokJumlah(jumlah, namabarang);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    

    private void ShowDataShipment(){
        List<ModelShipment> getAllShipment = new DAOShipment(a.getConnection()).getAllShipment();
        Object[][] data = new Object[getAllShipment.size()][5];
        int i = 0;
        for (ModelShipment s : getAllShipment) {
            data[i][0] = s.getTanggal();
            data[i][1] = s.getNamabarang();            
            data[i][2] = s.getJumlah();
            data[i][3] = s.getStatus();
            data[i][4] = s.getKeterangan();
            i++;
        }
        final Class<?>[] columnClasses = {String.class, String.class, Integer.class, String.class, String.class};
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Tanggal","Nama Barang","Jumlah","Status","Keterangan"}){
            @Override
              public Class<?> getColumnClass(int columnIndex)
              {
                return columnClasses[columnIndex];
              }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelShipment.setModel(tabelmodel);
        txtJumlahShipment.setText("");
        txtKeteranganShipment.setText("");
        List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStok();
        String[] datacmb = new String[getAllStok.size()];
        int j = 0;
        for (ModelStok s : getAllStok) {
            datacmb[j] = String.valueOf(s.getNama());
            j++;
        }
        cmbBarangShipment.setModel(new DefaultComboBoxModel(datacmb));
    }
    
    private void ShowDataShipmentBy(String entiti,String kode){
        List<ModelShipment> getAllShipment = new DAOShipment(a.getConnection()).getAllShipmentBy(entiti, kode);
        Object[][] data = new Object[getAllShipment.size()][5];
        int i = 0;
        for (ModelShipment s : getAllShipment) {
            data[i][0] = s.getTanggal();
            data[i][1] = s.getNamabarang();            
            data[i][2] = s.getJumlah();
            data[i][3] = s.getStatus();
            data[i][4] = s.getKeterangan();
            i++;
        }
        final Class<?>[] columnClasses = {String.class, String.class, Integer.class, String.class, String.class};
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Tanggal","Nama Barang","Jumlah","Status","Keterangan"}){
            @Override
              public Class<?> getColumnClass(int columnIndex)
              {
                return columnClasses[columnIndex];
              }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelShipment.setModel(tabelmodel);
    }
    
    private void InsertShipment(String status){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dtcmbTanggalShipment.setDateFormat(dateFormat);
        ModelShipment s = new ModelShipment();
        s.setNamabarang(cmbBarangShipment.getSelectedItem().toString());
        s.setJumlah(Double.parseDouble(txtJumlahShipment.getValue().toString()));
        s.setStatus(status);
        s.setTanggal(dtcmbTanggalShipment.getFormattedDate());
        s.setKeterangan(txtKeteranganShipment.getText());
        
        List<ModelStok> getAllStok = new DAOStok(a.getConnection()).search(cmbBarangShipment.getSelectedItem().toString(), "nama");
        Double[] data1 = new Double[getAllStok.size()];
        double stoklama = 0;
        int i = 0;
        for (ModelStok st : getAllStok) {
            data1[i] = st.getStok();
            stoklama = data1[i];
            i++;
        }
        
        double jumlah = Double.parseDouble(txtJumlahShipment.getValue().toString());
        double stokbaru;
        if(status.equals("masuk")){
            if (new DAOShipment(new DBConnection().getConnection()).insertShipment(s) == true) {
                stokbaru = jumlah + stoklama;
                try {
                    String hasil = st.updateStokJumlah(stokbaru,cmbBarangShipment.getSelectedItem().toString());
                } catch (Exception e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
                JOptionPane.showMessageDialog(this, "Barang telah "+status, "Message", JOptionPane.INFORMATION_MESSAGE);
                ShowDataShipment();
                txtJumlahShipment.setText("");
                txtKeteranganShipment.setText("");
                rbtnKeluarShipment.setSelected(false);
                rbtnMasukShipment.setSelected(false);
            }
        }else{
            if(stoklama<jumlah){
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi", "Message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                if (new DAOShipment(new DBConnection().getConnection()).insertShipment(s) == true) {
                stokbaru = stoklama-jumlah;
                try {
                    String hasil = st.updateStokJumlah(stokbaru,cmbBarangShipment.getSelectedItem().toString());
                } catch (Exception e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
                JOptionPane.showMessageDialog(this, "Barang telah "+status, "Message", JOptionPane.INFORMATION_MESSAGE);
                ShowDataShipment();
                txtJumlahShipment.setText("");
                txtKeteranganShipment.setText("");
                rbtnKeluarShipment.setSelected(false);
                rbtnMasukShipment.setSelected(false);
            }
            }
        }
    }
    
    
    private void ShowDataTransaksi(String status){
        List<ModelTransaksi> getAllTransaksi = new DAOTransaksi(a.getConnection()).getAllTransaksiByStatus(status);
        Object[][] data = new Object[getAllTransaksi.size()][10];
        int i = 0;
        for (ModelTransaksi t : getAllTransaksi){
            data[i][0] = t.getId();
            data[i][1] = t.getTanggal();
            data[i][2] = t.getHargajual();
            data[i][3] = t.getDiskon();
            data[i][4] = t.getTotal();
            data[i][5] = t.getUangmuka();
            data[i][6] = t.getSisa();
            data[i][7] = t.getPembeli();
            data[i][8] = t.getStatus();
            data[i][9] = t.getPenjual();
            i++;
        }
        final Class<?>[] columnClasses = {String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class, String.class};
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Id","Tanggal","Harga Jual","Diskon","Total","Uang Muka","Sisa","Pembeli","Status","Penjual"}){
            @Override
              public Class<?> getColumnClass(int columnIndex)
              {
                return columnClasses[columnIndex];
              }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelTransaksiPenjualan.setModel(tabelmodel);
    }
    
    private void InsertTransaksi(){
        ModelTransaksi t = new ModelTransaksi();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        t.setTanggal(dateFormat.format(date));
        
        int hargajual = 0;
        for (int i = 0; i < tblDaftarBeliTransaksi.getRowCount(); i++) {
            hargajual += Integer.parseInt(tblDaftarBeliTransaksi.getValueAt(i, 4).toString());
        }
        t.setHargajual(hargajual);
        
        double diskon = Double.parseDouble(txtDiskonTransaksi.getValue().toString());
        double totaldiskon = (hargajual*diskon)/100;
        int totaldiskon2 = (int)totaldiskon;
        t.setDiskon(totaldiskon2);
        
        int total = hargajual-totaldiskon2;
        t.setTotal(total);
        
        double uangbayar = Double.parseDouble(txtCashTransaksi.getValue().toString());
        int uangbayar2 = (int)uangbayar;
        t.setUangmuka(uangbayar2);
        
        int sisabayar = total-uangbayar2;
        if (sisabayar <= 0) {
            t.setSisa(0);
            t.setStatus("Lunas");
        }else{
            t.setSisa(sisabayar);
            t.setStatus("Belum");
        }
        
        t.setPembeli(labelPembeliTransaksi.getText());
        
        t.setPenjual(menuCurrentUser.getText());
        
        if (new DAOTransaksi(new DBConnection().getConnection()).insertTransaksi(t) == true) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil" , "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    private void InsertDetailTransaksi(){
        for (int i = 0; i < tblDaftarBeliTransaksi.getRowCount(); i++) {
            List<ModelTransaksi> getMax = new DAOTransaksi(a.getConnection()).getMax();
            int idtrans=0;
            for (ModelTransaksi t : getMax){
                idtrans = t.getMax();
            }
            ModelDetailTransaksi dt = new ModelDetailTransaksi();
            dt.setId_transaksi(idtrans);
            dt.setNama_barang(tblDaftarBeliTransaksi.getValueAt(i, 0).toString());
            dt.setJumlah(Double.parseDouble(tblDaftarBeliTransaksi.getValueAt(i, 1).toString()));
            dt.setHarga(Integer.parseInt(tblDaftarBeliTransaksi.getValueAt(i, 3).toString()));
            dt.setTotal(Integer.parseInt(tblDaftarBeliTransaksi.getValueAt(i, 4).toString()));
            if (new DAODetailTransaksi(new DBConnection().getConnection()).insertDetailTransaksi(dt) == true) {
            }
        }
    }
    
    private void ShowDetailTransaksi(String id){
        List<ModelDetailTransaksi> getDetailTransaksi = new DAODetailTransaksi(a.getConnection()).getAllDetailTransaksi(id);
        Object[][] data = new Object[getDetailTransaksi.size()][4];
        int i = 0;
        for (ModelDetailTransaksi t : getDetailTransaksi){
            data[i][0] = t.getNama_barang();
            data[i][1] = t.getHarga();
            data[i][2] = t.getJumlah();
            data[i][3] = t.getTotal();
            i++;
        }
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Nama Barang", "Harga", "Jumlah", "Total"}){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelDetailTransaksiPenjualan.setModel(tabelmodel);
    }
    
    
    private void ShowDataUser(){
        List<ModelUser> getAllUser = new DAOUser(a.getConnection()).getAllUser();
        Object[][] data = new Object[getAllUser.size()][3];
        int i = 0;
        for (ModelUser u : getAllUser){
            data[i][0] = u.getNama();
            data[i][1] = u.getUsername();
            data[i][2] = u.getAdmin();
            i++;
        }
        DefaultTableModel tabelmodel = new DefaultTableModel(data, new String[]{"Nama", "Username", "Admin"}){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelUser.setModel(tabelmodel);
    }
    
    
    private void Counting(){
        if (tblDaftarBeliTransaksi.getRowCount()==-1) {
            
        }else{
            NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.GERMANY);
            int hargajual = 0;
            for (int i = 0; i < tblDaftarBeliTransaksi.getRowCount(); i++) {
                hargajual += Integer.parseInt(tblDaftarBeliTransaksi.getValueAt(i, 4).toString());
            }

            double diskon = Double.valueOf(txtDiskonTransaksi.getValue().toString());
            double totaldiskon = (hargajual*diskon)/100;
            int totaldiskon2 = (int)totaldiskon;

            int total = hargajual-totaldiskon2;

            double uangbayar = Double.valueOf(txtCashTransaksi.getValue().toString());
            int uangbayar2 = (int)uangbayar;

            int sisabayar = total-uangbayar2;

            int kembalian = uangbayar2-total;

            labelHargaJual.setText("Rp "+numberFormatter.format(hargajual)+",-");
            labelDiskonTransaksi.setText("Rp "+numberFormatter.format(totaldiskon2)+",-");
            labelTotalTransaksi.setText("Rp "+numberFormatter.format(total)+",-");
            labelTotal2Transaksi.setText("Rp "+numberFormatter.format(total)+",-");
            labelPembeliTransaksi.setText(txtPembeliTransaksi.getText());
            labelUangMukaTransaksi.setText("Rp "+numberFormatter.format(uangbayar2)+",-");
            labelCashTransaksi.setText("Rp "+numberFormatter.format(uangbayar2)+",-");
            if (sisabayar <= 0) {
                labelSisaPembayaran.setText("Lunas");
            }else{
                labelSisaPembayaran.setText("Rp "+numberFormatter.format(sisabayar)+",-");
            }
            if (kembalian <= 0) {
                labelKembaliTransaksi.setText("Rp 0,-");
            }else{
                labelKembaliTransaksi.setText("Rp "+numberFormatter.format(kembalian)+",-");
            }
        }
    }
    
    private void HapusSemuaDaftarTransaksi(){
        for (int i = 0; i < tblDaftarBeliTransaksi.getRowCount(); i++) {
            List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStokByNama(this.tblDaftarBeliTransaksi.getValueAt(i, 0).toString());
            double stoklama = 0;
            double pcs = Double.parseDouble(this.tblDaftarBeliTransaksi.getValueAt(i, 1).toString());
            String nama ="";
            int j = 0;
            for (ModelStok s : getAllStok) {
                nama = s.getNama();
                stoklama = s.getStok();
                j++;
            }
            UpdateStok(stoklama+pcs, nama);
        }
        DefaultTableModel kosong = new DefaultTableModel(new Object[]{"Nama","Pcs.","Satuan","Harga","Jumlah"},0);
        tblDaftarBeliTransaksi.setModel(kosong);
        txtPembeliTransaksi.setText("");
        txtDiskonTransaksi.setValue(0);
        txtCashTransaksi.setValue(0);
        Counting();
        
    }
    
    
    private CategoryDataset createDataset(String dari,String sampai){
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        List<ModelTransaksi> getAllTransaksi = new DAOTransaksi(a.getConnection()).getAllTransaksiGraph(dari, sampai);
        String[][] tanggal = new String[getAllTransaksi.size()][1];
        double[][] total = new double[getAllTransaksi.size()][1];
        int i = 0;
        for (ModelTransaksi t : getAllTransaksi) {
            tanggal[i][0] = String.valueOf(t.getTanggal());
            total[i][0] = Double.parseDouble(String.valueOf(t.getTotal()));
            dataset.addValue(total[i][0], "Transaksi", tanggal[i][0]);
            i++;
        }
        return dataset;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Graph;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JPanel PanelFitur;
    private javax.swing.JPanel PanelKananStok;
    private javax.swing.JPanel PanelKananTransaksi;
    private javax.swing.JPanel PanelKiriStok;
    private javax.swing.JPanel PanelKiriTransaksi;
    private javax.swing.JPanel Penjualan;
    private javax.swing.JSplitPane SplitPanelStok;
    private javax.swing.JSplitPane SplitPanelTransaksi;
    private javax.swing.JPanel Stok;
    private javax.swing.JToolBar ToolbarPanel;
    private javax.swing.JPanel Transaksi;
    private javax.swing.JPanel User;
    private javax.swing.JButton btnChartFitur;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnEditStokStok;
    private javax.swing.ButtonGroup btnGroupKeluarMasuk;
    private javax.swing.ButtonGroup btnGroupPenjualan;
    private javax.swing.ButtonGroup btnGroupShipment;
    private javax.swing.JButton btnHapusDaftarTransaksi;
    private javax.swing.JButton btnHapusSemuaDaftarTransaksi;
    private javax.swing.JButton btnHapusStokStok;
    private javax.swing.JButton btnLogoutFitur;
    private javax.swing.JButton btnOkeEditUser;
    private javax.swing.JButton btnOkeGraph;
    private javax.swing.JButton btnOkeShipment;
    private javax.swing.JButton btnOkeTransaksi;
    private javax.swing.JButton btnOkeUserTambah;
    private javax.swing.JButton btnPDFStokStok;
    private javax.swing.JButton btnPenjualanFitur;
    private javax.swing.JButton btnSettingFitur;
    private javax.swing.JButton btnStokFitur;
    private javax.swing.JButton btnTambahBarangTransaksi;
    private javax.swing.JButton btnTambahPelunasanPenjualan;
    private javax.swing.JButton btnTambahStokStok;
    private javax.swing.JButton btnTambahkanFormEditStok;
    private javax.swing.JButton btnTambahkanFormTambahStok;
    private javax.swing.JButton btnTansaksiFitur;
    private javax.swing.JButton btnUserFitur;
    private de.javasoft.swing.ButtonBar buttonBar1;
    private de.javasoft.swing.ButtonBar buttonBar2;
    private de.javasoft.swing.JYComboBox cmbBarangShipment;
    private de.javasoft.swing.JYComboBox cmbJenisFormEditStok;
    private de.javasoft.swing.JYComboBox cmbJenisFormTambahStok;
    private de.javasoft.swing.DateComboBox dtcmbDariGraph;
    private de.javasoft.swing.DateComboBox dtcmbSampaiGraph;
    private de.javasoft.swing.DateComboBox dtcmbTanggalShipment;
    private javax.swing.JMenuItem itemmenuEditStok;
    private javax.swing.JMenuItem itemmenuHapusStok;
    private javax.swing.JMenuItem itemmenuKeluarShipment;
    private javax.swing.JMenuItem itemmenuLihatStok;
    private javax.swing.JMenuItem itemmenuMasukShipment;
    private javax.swing.JMenuItem itemmenuPelunasanPenjualan;
    private javax.swing.JMenuItem itemmenuStokPDF;
    private javax.swing.JMenuItem itemmenuTambahStok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator1;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator2;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator3;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator4;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator5;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator6;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator7;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator8;
    private de.javasoft.swing.JYTableScrollPane jYTableScrollPane1;
    private de.javasoft.swing.JYTableScrollPane jYTableScrollPane2;
    private de.javasoft.swing.JYTableScrollPane jYTableScrollPane3;
    private javax.swing.JLabel labelCashTransaksi;
    private javax.swing.JLabel labelDiskonTransaksi;
    private javax.swing.JLabel labelHargaJual;
    private javax.swing.JLabel labelIdTransaksiPenjualan;
    private javax.swing.JLabel labelKembaliTransaksi;
    private javax.swing.JLabel labelPembeliTransaksi;
    private javax.swing.JLabel labelPembeliTransaksiPenjualan;
    private javax.swing.JLabel labelSisaPembayaran;
    private javax.swing.JLabel labelSisaTransaksiPenjualan;
    public javax.swing.JLabel labelTanggalTransaksi;
    private javax.swing.JLabel labelTotal2Transaksi;
    private javax.swing.JLabel labelTotalTransaksi;
    private javax.swing.JLabel labelTotalTransaksiPenjualan;
    private javax.swing.JLabel labelUangMukaTransaksi;
    private javax.swing.JMenu menuBantuan;
    public javax.swing.JMenu menuCurrentUser;
    private javax.swing.JMenu menuFitur;
    private javax.swing.JPanel panelEditStok;
    private javax.swing.JPanel panelGraph;
    private javax.swing.JPanel panelTambahStok;
    private javax.swing.JRadioButton rbtnKeluarShipment;
    private javax.swing.JRadioButton rbtnMasukShipment;
    private javax.swing.JMenuItem submenuExit;
    private javax.swing.JMenu submenuExport;
    private javax.swing.JMenuItem submenuKontenBantuan;
    private javax.swing.JMenuItem submenuLogout;
    private javax.swing.JMenu submenuPenjualan;
    private javax.swing.JMenu submenuShipment;
    private javax.swing.JMenu submenuStok;
    private javax.swing.JMenuItem submenuTentang;
    private de.javasoft.swing.JYSwitchButton swbtnAdminEdit;
    private de.javasoft.swing.JYSwitchButton swbtnAdminTambah;
    private javax.swing.JTable tabelBarangFormEditStok;
    private javax.swing.JTable tabelDetailTransaksiPenjualan;
    private de.javasoft.swing.JYTable tabelShipment;
    private de.javasoft.swing.JYTable tabelStokStok;
    private de.javasoft.swing.JYTable tabelTransaksiPenjualan;
    private javax.swing.JTable tabelUser;
    private javax.swing.JTable tblBarangTransaksi;
    private javax.swing.JTable tblDaftarBeliTransaksi;
    private javax.swing.JToggleButton tgbKeluarShipment;
    private javax.swing.JToggleButton tgbMasukShipment;
    private javax.swing.JToggleButton tgbSemuaShipment;
    private javax.swing.JToggleButton tgbtnBelumPenjualan;
    private javax.swing.JToggleButton tgbtnLunasPenjualan;
    private javax.swing.JToggleButton tgbtnSemuaPenjualan;
    private de.javasoft.swing.JYFormattedTextField txtCashTransaksi;
    private de.javasoft.swing.JYTextField txtDiskonTransaksi;
    private javax.swing.JTextField txtHarga80FormEditStok;
    private javax.swing.JTextField txtHarga80FormTambahStok;
    private javax.swing.JTextField txtHarga90FormEditStok;
    private javax.swing.JTextField txtHarga90FormTambahStok;
    private javax.swing.JTextField txtIdFormEditStok;
    private javax.swing.JTextField txtIdFormTambahStok;
    private javax.swing.JTextField txtJenisFormEditStok;
    private javax.swing.JTextField txtJenisFormTambahStok;
    private javax.swing.JTextField txtJumlahFormEditStok;
    private javax.swing.JTextField txtJumlahFormTambahStok;
    private javax.swing.JFormattedTextField txtJumlahShipment;
    private javax.swing.JTextField txtKeteranganFormEditStok;
    private javax.swing.JTextField txtKeteranganFormTambahStok;
    private javax.swing.JTextField txtKeteranganShipment;
    private de.javasoft.swing.JYSearchField txtNamaBarangTransaksi;
    private javax.swing.JTextField txtNamaFormEditStok;
    private javax.swing.JTextField txtNamaFormTambahStok;
    private javax.swing.JTextField txtNamaUserEdit;
    private javax.swing.JTextField txtNamaUserTambah;
    private javax.swing.JTextField txtPassBaruUserEdit;
    private javax.swing.JPasswordField txtPassLamaUserEdit;
    private javax.swing.JTextField txtPassUserTambah;
    private de.javasoft.swing.JYTextField txtPcsTransaksi;
    private de.javasoft.swing.JYTextField txtPembeliTransaksi;
    private javax.swing.JTextField txtSatuanFormEditStok;
    private javax.swing.JTextField txtSatuanFormTambahStok;
    private de.javasoft.swing.JYTextField txtUangPelunasanPenjualan;
    private javax.swing.JTextField txtUsernameUserEdit;
    private javax.swing.JTextField txtUsernameUserTambah;
    // End of variables declaration//GEN-END:variables
}
