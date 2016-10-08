/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author win 8
 */
public class Receipt implements Printable{
    public String idTransaksi = "";
    public String UserLogin = "";
    public String Pembeli = "";
    public String diskon = "";
    public String total = "";
    public ArrayList daftarbeli = new ArrayList();
    public ArrayList daftarpcs = new ArrayList();
    public ArrayList daftarsatuan = new ArrayList();
    public ArrayList daftarharga = new ArrayList();
    public ArrayList daftartotal = new ArrayList();
    
    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
        
        int baris = 90;
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.GERMANY);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
 
        g2.setFont(new Font("default", Font.BOLD, 14));
        g2.drawString("MEGA JAYA PERKASA", 15, 15);
        g2.setFont(new Font("default", Font.PLAIN, 8));
        g2.drawString("  Jl. H.M Sarbini No 118 Kebumen, Jawa Tengah", 2, 25);
        g2.drawString("082226553711", 65, 35);
        
        g2.drawLine(0, 45, 180, 45);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Date date = new Date();
        g2.drawString(dateFormat.format(date).toString(), 1, 60);
        FontMetrics metrics = g2.getFontMetrics();
        g2.drawString(UserLogin, 180 - metrics.stringWidth(UserLogin), 60);
        g2.drawLine(0, 75, 180, 75);
        
        g2.setFont(new Font("default", Font.PLAIN, 8));
        
        for (int i = 0; i < daftarbeli.size(); i++) {
            double harga = Double.parseDouble(String.valueOf(daftarharga.get(i)));
            String harga2 = numberFormatter.format(harga);
            double jumlah = Double.parseDouble(String.valueOf(daftartotal.get(i)));
            String jumlah2 = numberFormatter.format(jumlah);
            String pcs = String.valueOf(daftarpcs.get(i)).replace('.', ',');
            String barang = String.valueOf(daftarbeli.get(i));
            FontMetrics metrics2 = g2.getFontMetrics();
            String no = String.valueOf(i+1);
            
            g2.drawString(no, 3, baris);
            g2.drawString(barang, 15,baris);
            g2.drawString(pcs, 15,baris+10);
            g2.drawString(daftarsatuan.get(i).toString()+" x", 40, baris+10);
            g2.drawString(harga2, 75, baris+10);
            g2.drawString(jumlah2, 180 - metrics2.stringWidth(jumlah2) ,baris+10);

            baris+=20;
        }
        int tutupan = (daftarbeli.size()*20)+90;

        g2.drawLine(0, tutupan, 180, tutupan);
        
        if (diskon.equals("Rp 0,-")) {
            g2.setFont(new Font("default", Font.BOLD, 10));
            g2.drawString("Total", 1, tutupan+10);
            g2.drawString(":", 45, tutupan+10);
            g2.setFont(new Font("default", Font.PLAIN, 10));
            metrics = g2.getFontMetrics();
            g2.drawString(total, 180 - metrics.stringWidth(total), tutupan+10);
            g2.setFont(new Font("default", Font.PLAIN, 9));
            metrics = g2.getFontMetrics();
            g2.drawString("Pembeli", 1, tutupan+20);
            g2.drawString(":", 45, tutupan+20);
            g2.drawString(Pembeli, 180 - metrics.stringWidth(Pembeli) , tutupan+20);
            g2.setFont(new Font("default", Font.PLAIN, 6));
            g2.drawString("Barang yang sudah dibeli tidak dapat dikembalikan.", 20, tutupan+60);
            g2.drawString("Terima Kasih", 70, tutupan+70);
        }else{
            g2.setFont(new Font("default", Font.PLAIN, 9));
            g2.drawString("Diskon", 1, tutupan+10);
            g2.drawString(":", 45, tutupan+10);
            g2.setFont(new Font("default", Font.PLAIN, 9));
            metrics = g2.getFontMetrics();
            g2.drawString(diskon, 180 - metrics.stringWidth(diskon), tutupan+10);
            g2.setFont(new Font("default", Font.BOLD, 10));
            g2.drawString("Total", 1, tutupan+20);
            g2.drawString(":", 45, tutupan+20);
            g2.setFont(new Font("default", Font.PLAIN, 10));
            metrics = g2.getFontMetrics();
            g2.drawString(total, 180 - metrics.stringWidth(total), tutupan+20);
            g2.setFont(new Font("default", Font.PLAIN, 9));
            metrics = g2.getFontMetrics();
            g2.drawString("Pembeli", 1, tutupan+30);
            g2.drawString(":", 45, tutupan+30);
            g2.drawString(Pembeli, 180 - metrics.stringWidth(Pembeli) , tutupan+30);
            g2.setFont(new Font("default", Font.PLAIN, 6));
            g2.drawString("Barang yang sudah dibeli tidak dapat dikembalikan.", 20, tutupan+70);
            g2.drawString("Terima Kasih", 70, tutupan+80);
        }
        return PAGE_EXISTS;
    }
    
    public void Struk(){
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        double margin = 36; // half inch
        paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
        pf.setPaper(paper);
        
        job.setPrintable(this, pf);

    //         boolean ok = job.printDialog();
    //         if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null,"print : "+ex.getStackTrace());
    //             }
            }
    }
}
