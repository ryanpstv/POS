/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelUser;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author win 8
 */
public class DAOUser {
    private Connection conn = null;
    
    public DAOUser(Connection conn){
        this.conn = conn;
    }
    
    public List<ModelUser> getAllUser(){
        List<ModelUser> getUser = new ArrayList<ModelUser>();
        ModelUser user;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT * FROM tb_user");
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new ModelUser();
                user.setId(rs.getInt(1));
                user.setNama(rs.getString(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setAdmin(rs.getString(5));
                getUser.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getUser;
    }
    
    public List<ModelUser> search(String id, String entiti, String id2, String entiti2){
        String sql = "SELECT * from tb_user WHERE "+ entiti +" = '"+id+"' AND "+ entiti2 +" = '"+id2+"'";
        Statement st = null;
        ResultSet rs = null;
        List<ModelUser> list = new ArrayList<ModelUser>();
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ModelUser user = new ModelUser();
                user.setId(rs.getInt(1));
                user.setNama(rs.getString(2));
                user.setUsername(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setAdmin(rs.getString(5));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ModelUser> check(String id, String entiti){
        String sql = "SELECT count(*) from tb_user WHERE "+ entiti +" = '"+id+"'";
        Statement st = null;
        ResultSet rs = null;
        List<ModelUser> list = new ArrayList<ModelUser>();
        try {
            st = (Statement) conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ModelUser user = new ModelUser();
                user.setCount(rs.getInt(1));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean  insertUser(ModelUser user){
        boolean cek = false;
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO tb_user(user_id,nama,username,password,admin) VALUE(?,?,?,?,?)");
            ps.setInt(1, user.getId());
            ps.setString(2, user.getNama());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAdmin());
            
            if (ps.executeUpdate() > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;      
    }
    
    public String deleteUser(String userid) throws SQLException{
        String hasil = "tidak berhasil";
        String sql = "DELETE from tb_user WHERE user_id='"+ userid +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil menghapus";
        }
        return hasil;
    }
    
    public String updateUser(String id, String nama, String username, String pass, String admin) throws SQLException{
        String hasil = "tidak berhasil";
        String sql = "UPDATE tb_user SET nama='"+ nama +"',username='"+ username +"',password='"+ pass +"' ,admin='"+ admin +"'WHERE user_id='"+ id +"'";
        Statement st = null;
        st = (Statement) conn.createStatement();
        if (st.executeUpdate(sql) > 0) {
            hasil = "berhasil update";
        }
        return hasil;
    }
}
