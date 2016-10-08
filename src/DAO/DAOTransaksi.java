/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelTransaksi;
import com.mysql.jdbc.Statement;
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
public class DAOTransaksi {
    private Connection conn = null;
    
    public DAOTransaksi(Connection conn){
        this.conn = conn;
    }
    
    public List<ModelTransaksi> getAllTransaksiByStatus(String status){
        List<ModelTransaksi> getTransaksi = new ArrayList<ModelTransaksi>();
        ModelTransaksi transaksi;
        PreparedStatement ps;
        ResultSet rs;
        try {
            if(status.equals("semua")){
                ps = conn.prepareStatement("SELECT * FROM tb_transaksi ORDER BY tb_transaksi.transaksi_id DESC");
            }else{
                ps = conn.prepareStatement("SELECT * FROM tb_transaksi WHERE status = '"+status+"' ORDER BY tb_transaksi.transaksi_id DESC ");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                transaksi = new ModelTransaksi();
                transaksi.setId(rs.getInt(1));
                transaksi.setTanggal(rs.getString(2));
                transaksi.setHargajual(rs.getInt(3));
                transaksi.setDiskon(rs.getInt(4));
                transaksi.setTotal(rs.getInt(5));
                transaksi.setUangmuka(rs.getInt(6));
                transaksi.setSisa(rs.getInt(7));
                transaksi.setPembeli(rs.getString(8));
                transaksi.setStatus(rs.getString(9));
                transaksi.setPenjual(rs.getString(10));
                getTransaksi.add(transaksi);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getallbystatus-Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAOTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getTransaksi;
    }
    
    public boolean  insertTransaksi(ModelTransaksi transaksi){
        boolean cek = false;
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO tb_transaksi(transaksi_id,tanggal,harga_jual,diskon,total,uang_muka,sisa,pembeli,status,penjual) VALUE(?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, transaksi.getId());
            ps.setString(2, transaksi.getTanggal());
            ps.setInt(3, transaksi.getHargajual());
            ps.setInt(4, transaksi.getDiskon());
            ps.setInt(5, transaksi.getTotal());
            ps.setInt(6, transaksi.getUangmuka());
            ps.setInt(7, transaksi.getSisa());
            ps.setString(8, transaksi.getPembeli());
            ps.setString(9, transaksi.getStatus());
            ps.setString(10, transaksi.getPenjual());
            if (ps.executeUpdate() > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"insert-Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAOTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;      
    }
    
    public String updateTransaksi(String id, int sisa, String status) throws SQLException{
        String hasil = "tidak berhasil";
        String sql = "UPDATE tb_transaksi SET sisa='"+ sisa +"',status='"+ status +"' WHERE transaksi_id='"+ id +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil update";
        }
        return hasil;
    }

    public List<ModelTransaksi> getMax(){
        List<ModelTransaksi> getTransaksi = new ArrayList<ModelTransaksi>();
        ModelTransaksi transaksi;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT MAX(transaksi_id) FROM tb_transaksi");
            rs = ps.executeQuery();
            while (rs.next()) {
                transaksi = new ModelTransaksi();
                transaksi.setMax(rs.getInt(1));
                getTransaksi.add(transaksi);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getMax-Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAOTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getTransaksi;
    }
    
    public List<ModelTransaksi> getAllTransaksiGraph(String dari, String sampai){
        List<ModelTransaksi> getTransaksi = new ArrayList<ModelTransaksi>();
        ModelTransaksi transaksi;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT tanggal, SUM(total) AS totalsemua FROM tb_transaksi WHERE tanggal BETWEEN '"+dari+"' AND '"+sampai+"' GROUP BY tanggal");
            rs = ps.executeQuery();
            while (rs.next()) {
                transaksi = new ModelTransaksi();
                transaksi.setTanggal(rs.getString(1));
                transaksi.setTotal(rs.getInt(2));
                getTransaksi.add(transaksi);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"graph-Transaksi : "+ex.getStackTrace());
            Logger.getLogger(DAOTransaksi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getTransaksi;
    }
}
