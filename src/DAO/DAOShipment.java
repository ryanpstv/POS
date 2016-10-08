/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelShipment;
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
public class DAOShipment {
    private Connection conn = null;
    
    public DAOShipment(Connection conn){
    this.conn = conn;
    }
    
    public List<ModelShipment> getAllShipment(){
        List<ModelShipment> getShipment = new ArrayList<ModelShipment>();
        ModelShipment shipment;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_shipment ORDER BY  tb_shipment.shipment_id DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                shipment = new ModelShipment();
                shipment.setId(rs.getInt(1));
                shipment.setNamabarang(rs.getString(2));
                shipment.setJumlah(rs.getDouble(3));
                shipment.setTanggal(rs.getString(4));
                shipment.setStatus(rs.getString(5));
                shipment.setKeterangan(rs.getString(6));
                getShipment.add(shipment);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getAll-Shipment : "+ex.getStackTrace());
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getShipment;
    }
    
    public boolean  insertShipment(ModelShipment shipment){
        boolean cek = false;
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO tb_shipment(shipment_id,nama_barang,jumlah,tanggal,status,keterangan) VALUE(?,?,?,?,?,?)");
            ps.setInt(1, shipment.getId());
            ps.setString(2, shipment.getNamabarang());
            ps.setDouble(3, shipment.getJumlah());
            ps.setString(4, shipment.getTanggal());
            ps.setString(5, shipment.getStatus());
            ps.setString(6, shipment.getKeterangan());
            
            if (ps.executeUpdate() > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"insert-Shipment : "+ex.getStackTrace());
            Logger.getLogger(DAOShipment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;      
    }
    
    public List<ModelShipment> getAllShipmentBy(String entiti, String kode){
        List<ModelShipment> getShipment = new ArrayList<ModelShipment>();
        ModelShipment shipment;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_shipment WHERE "+ entiti +" = '"+kode+"' ORDER BY  tb_shipment.shipment_id DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                shipment = new ModelShipment();
                shipment.setId(rs.getInt(1));
                shipment.setNamabarang(rs.getString(2));
                shipment.setJumlah(rs.getDouble(3));
                shipment.setTanggal(rs.getString(4));
                shipment.setStatus(rs.getString(5));
                shipment.setKeterangan(rs.getString(6));
                getShipment.add(shipment);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"getBy-Shipment : "+ex.getStackTrace());
            Logger.getLogger(DAOStok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getShipment;
    }
}
