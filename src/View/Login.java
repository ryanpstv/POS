package View;

import DAO.DAOUser;
import Koneksi.DBConnection;
import Model.ModelUser;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


public class Login extends javax.swing.JFrame {
    final JDialog form = new JDialog(this, "", true);
    String User="";
    String Admin="";
    Fitur f = new Fitur();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    
    public Login() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        form.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
        JRootPane root = this.getRootPane();
        SyntheticaLookAndFeel.findComponent("RootPane.titlePane.toggleButton", root).setVisible(false);
        SyntheticaLookAndFeel.findComponent("RootPane.titlePane.iconifyButton", root).setVisible(false);
        SyntheticaLookAndFeel.findComponent("RootPane.titlePane.menuButton", root).setVisible(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUserLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassLogin = new javax.swing.JPasswordField();
        btnLoginLogin = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelNotif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/username.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Asset/pass.png"))); // NOI18N

        btnLoginLogin.setForeground(new java.awt.Color(102, 102, 102));
        btnLoginLogin.setText("LOGIN");
        btnLoginLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginLoginActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        labelNotif.setForeground(new java.awt.Color(255, 51, 51));
        labelNotif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNotif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNotif, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPassLogin))
                    .addComponent(btnLoginLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(75, 75, 75))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(btnLoginLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginLoginActionPerformed
        if (txtPassLogin.getText().isEmpty() || txtUserLogin.getText().isEmpty()) {
            labelNotif.setText("Harap isi dengan lengkap");
        }else{
            DBConnection a = new DBConnection();
            List<ModelUser> getbyLogin = new DAOUser(a.getConnection()).search(txtUserLogin.getText(), "username", txtPassLogin.getText(),"password");
            String[][] data = new String[getbyLogin.size()][4];
            String u = "", p = "", n = "";
            int i = 0;
            for (ModelUser m : getbyLogin){
            
                data[i][0] = String.valueOf(m.getUsername());
                data[i][1] = String.valueOf(m.getPassword());
                data[i][2] = String.valueOf(m.getNama());
                data[i][3] = String.valueOf(m.getAdmin());
                Admin=data[i][3];
                User=data[i][0];
                p=data[i][1];
                n=data[i][2];
                i++;
                
            }
            if(txtUserLogin.getText().equals(User) && txtPassLogin.getText().equals(p)){
                f.setVisible(true);
                this.dispose();
                f.menuCurrentUser.setText(n);
                f.Admin = this.Admin;
                f.labelTanggalTransaksi.setText(dateFormat.format(date).toString());
            }else{
                labelNotif.setText("Username atau passwor salah");
            }  
        }
    }//GEN-LAST:event_btnLoginLoginActionPerformed

    
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
                new Login().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoginLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelNotif;
    private javax.swing.JPasswordField txtPassLogin;
    private javax.swing.JTextField txtUserLogin;
    // End of variables declaration//GEN-END:variables
}
