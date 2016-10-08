package Koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnection {
    private Connection conn = null;
    
    public String sDbDriver;
    public String sDbUrl;
    public String sDbUser;
    public String sDbPass;
    
    //Connector, set the path of database. also user and pass 
    public DBConnection(){
        sDbDriver = "com.mysql.jdbc.Driver";
        sDbUrl = "jdbc:mysql://localhost:3306/db_megajayaperkasa?zeroDateTimeBehavior=convertToNull";
        sDbUser = "root";
        sDbPass = "";
        
        //connecting db,set var 'conn' to method that running db
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(sDbUrl, sDbUser, sDbPass);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //get value of var 'conn'
    public Connection getConnection() {
        return this.conn;
    }
    
    public static void main(String[] args) {
        DBConnection a = new DBConnection();
        System.out.println(a.getConnection());
    }
}
