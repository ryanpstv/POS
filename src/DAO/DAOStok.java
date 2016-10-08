/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelStok;
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
public class DAOStok {
    private Connection conn = null;
    
    public DAOStok(Connection conn){
        this.conn = conn;
    }
    
    public List<ModelStok> getAllStok(){
        List<ModelStok> getStok = new ArrayList<ModelStok>();
        ModelStok stok;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_stok");
            rs = ps.executeQuery();
            while (rs.next()) {
                stok = new ModelStok();
                stok.setId(rs.getString(1));
                stok.setNama(rs.getString(2));
                stok.setJenis(rs.getString(3));
                stok.setHarga80(rs.getInt(4));
                stok.setHarga90(rs.getInt(5));
                stok.setSatuan(rs.getString(6));
                stok.setStok(rs.getDouble(7));
                stok.setKeterangan(rs.getString(8));
                getStok.add(stok);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getAll-Stok : "+ex.getStackTrace());
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getStok;
    }
    
    public List<ModelStok> getAllStokOrderByJenis(){
        List<ModelStok> getStokOrder = new ArrayList<ModelStok>();
        ModelStok stok;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT DISTINCT jenis FROM tb_stok ORDER BY jenis");
            rs = ps.executeQuery();
            while (rs.next()) {
                stok = new ModelStok();
                stok.setJenis(rs.getString(1));
                getStokOrder.add(stok);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getStokOrder;
    }
    
    public String deleteStok(String id) throws SQLException{
        String hasil = "tidak berhasil";
        String sql = "DELETE from tb_stok WHERE stok_id='"+ id +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil menghapus";
        }
        return hasil;
    }
    
    public List<ModelStok> search(String id, String entiti){
        String sql = "SELECT * from tb_stok WHERE "+ entiti +" LIKE '%"+id+"%'";
        Statement st = null;
        ResultSet rs = null;
        List<ModelStok> list = new ArrayList<ModelStok>();
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ModelStok stok = new ModelStok();
                stok.setId(rs.getString(1));
                stok.setNama(rs.getString(2));
                stok.setJenis(rs.getString(3));
                stok.setHarga80(rs.getInt(4));
                stok.setHarga90(rs.getInt(5));
                stok.setSatuan(rs.getString(6));
                stok.setStok(rs.getDouble(7));
                stok.setKeterangan(rs.getString(8));
                list.add(stok);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"searc-Stok : "+e.getStackTrace());
            e.printStackTrace();
        }
        return list;
    }
    
    public String updateStokJumlah(double jumlah, String namabarang) throws SQLException{
        String hasil = "tidak berhasi update stok";
        String sql = "UPDATE tb_stok SET jumlah='"+ jumlah +"'WHERE nama='"+ namabarang +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil update";
        }
        return hasil;
    }
    
    public List<ModelStok> getAllStokByNama(String nama){
        List<ModelStok> getStok = new ArrayList<ModelStok>();
        ModelStok stok;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_stok WHERE nama = '"+nama+"'");
            rs = ps.executeQuery();
            while (rs.next()) {
                stok = new ModelStok();
                stok.setId(rs.getString(1));
                stok.setNama(rs.getString(2));
                stok.setJenis(rs.getString(3));
                stok.setHarga80(rs.getInt(4));
                stok.setHarga90(rs.getInt(5));
                stok.setSatuan(rs.getString(6));
                stok.setStok(rs.getDouble(7));
                stok.setKeterangan(rs.getString(8));
                getStok.add(stok);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getByNama-Stok : "+ex.getStackTrace());
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getStok;
    }
    
    public List<ModelStok> check(String id, String entiti){
        String sql = "SELECT count(*) from tb_stok WHERE "+ entiti +" = '"+id+"'";
        Statement st = null;
        ResultSet rs = null;
        List<ModelStok> list = new ArrayList<ModelStok>();
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ModelStok stok = new ModelStok();
                stok.setCount(rs.getInt(1));
                list.add(stok);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"check-Stok : "+e.getStackTrace());
            e.printStackTrace();
        }
        return list;
    }   
    
    public boolean  insertStok(ModelStok stok){
        boolean cek = false;
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO tb_stok(stok_id,nama,jenis,harga80,harga90,satuan,jumlah,keterangan) VALUE(?,?,?,?,?,?,?,?)");
            ps.setString(1, stok.getId());
            ps.setString(2, stok.getNama());
            ps.setString(3, stok.getJenis());
            ps.setInt(4, stok.getHarga80());
            ps.setInt(5, stok.getHarga90());
            ps.setString(6, stok.getSatuan());
            ps.setDouble(7, stok.getStok());
            ps.setString(8, stok.getKeterangan());
            
            if (ps.executeUpdate() > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"insert-Stok : "+ex.getStackTrace());
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;      
    }
    
    public List<ModelStok> getAllStokById(String id){
        List<ModelStok> getStok = new ArrayList<ModelStok>();
        ModelStok stok;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_stok WHERE stok_id = '"+id+"'");
            rs = ps.executeQuery();
            while (rs.next()) {
                stok = new ModelStok();
                stok.setId(rs.getString(1));
                stok.setNama(rs.getString(2));
                stok.setJenis(rs.getString(3));
                stok.setHarga80(rs.getInt(4));
                stok.setHarga90(rs.getInt(5));
                stok.setSatuan(rs.getString(6));
                stok.setStok(rs.getDouble(7));
                stok.setKeterangan(rs.getString(8));
                getStok.add(stok);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getStok;
    }
    
    public String updateStok(String id, String nama, String jenis, int harga80, int harga90, String satuan, double stok, String keterangan) throws SQLException{
        String hasil = "tidak berhasil";
        String sql = "UPDATE tb_stok SET nama='"+ nama +"',jenis='"+ jenis +"',harga80='"+ harga80 +"' ,harga90='"+ harga90 +"' ,satuan='"+ satuan +"' ,jumlah='"+ stok +"' ,keterangan='"+ keterangan +"'WHERE stok_id='"+ id +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil update";
        }
        return hasil;
    }
}
