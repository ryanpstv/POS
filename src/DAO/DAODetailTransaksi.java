/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelDetailTransaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author win 8
 */
public class DAODetailTransaksi {
    private Connection conn = null;
    
    public DAODetailTransaksi(Connection conn){
        this.conn = conn;
    }
    
    public boolean  insertDetailTransaksi(ModelDetailTransaksi transaksi){
        boolean cek = false;
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO tb_detail_transaksi(transaksi_id,nama_barang,jumlah,harga_beli,total) VALUE(?,?,?,?,?)");
            ps.setInt(1, transaksi.getId_transaksi());
            ps.setString(2, transaksi.getNama_barang());
            ps.setDouble(3, transaksi.getJumlah());
            ps.setInt(4, transaksi.getHarga());
            ps.setInt(5, transaksi.getTotal());
            if (ps.executeUpdate() > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"insert-Detail Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAODetailTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;      
    }
    
    public List<ModelDetailTransaksi> getAllDetailTransaksi(String id_transaksi){
        List<ModelDetailTransaksi> getDetailTransaksi = new ArrayList<ModelDetailTransaksi>();
        ModelDetailTransaksi detailtransaksi;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT nama_barang, jumlah, harga_beli, total FROM tb_detail_transaksi WHERE transaksi_id = '"+id_transaksi+"'");
            rs = ps.executeQuery();
            while (rs.next()) {
                detailtransaksi = new ModelDetailTransaksi();
                detailtransaksi.setNama_barang(rs.getString(1));
                detailtransaksi.setJumlah(rs.getDouble(2));
                detailtransaksi.setHarga(rs.getInt(3));
                detailtransaksi.setTotal(rs.getInt(4));
                getDetailTransaksi.add(detailtransaksi);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"GetAll-Detail Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAODetailTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getDetailTransaksi;
    }
}
