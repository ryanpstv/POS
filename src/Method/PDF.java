/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import DAO.DAOStok;
import Koneksi.DBConnection;
import Model.ModelStok;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author win 8
 */
public class PDF {
    public PDF() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Date date = new Date();
        String judul = "G:/Stok "+dateFormat.format(date).toString()+".pdf";
        String outputFileName = judul;
        System.out.println(judul);
//        if (args.length > 0)
//            outputFileName = args[0];
 
        // Create a document and add a page to it
        PDDocument document = new PDDocument();
        PDPage page1 = new PDPage(PDRectangle.A4);
            // PDRectangle.LETTER and others are also possible
        PDRectangle rect = page1.getMediaBox();
            // rect can be used to get the page width and height
        document.addPage(page1);
 
        // Create a new font object selecting one of the PDF base fonts
        PDFont fontPlain = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDFont fontMono = PDType1Font.COURIER;
 
        // Start a new content stream which will "hold" the to be created content
        PDPageContentStream cos = new PDPageContentStream(document, page1);
 
        int line = 0;
        
        DBConnection a = new DBConnection();
        List<ModelStok> getAllStok = new DAOStok(a.getConnection()).getAllStok();
        String[][] data = new String[getAllStok.size()][2];
        int i = 0;
        cos.beginText();
        cos.setFont(fontPlain, 8);
        cos.newLineAtOffset(100, rect.getHeight() - 100*(++line));
        cos.endText();
        for (ModelStok s : getAllStok) {
            data[i][0] = String.valueOf(s.getNama());
            data[i][1] = Double.toString(s.getStok());
            cos.beginText();
            cos.setFont(fontPlain, 8);
            cos.setNonStrokingColor(Color.BLACK);
            cos.newLineAtOffset(100, rect.getHeight() - 20*(++line));
            cos.showText(data[i][0]+"     ");
            cos.setNonStrokingColor(Color.BLUE);
            cos.showText(data[i][1]);
            cos.endText();
            if (i+1%40 == 0) {
                cos.close();
                PDPage pagelanjut = new PDPage(PDRectangle.A4);
                document.addPage(pagelanjut);
                cos = new PDPageContentStream(document, pagelanjut);
            }
            i++;
        }
 
        // Make sure that the content stream is closed:
        cos.close();
 
//        PDPage page2 = new PDPage(PDRectangle.A4);
//        document.addPage(page2);
//        cos = new PDPageContentStream(document, page2);
// 
//        // draw a red box in the lower left hand corner
//        cos.setNonStrokingColor(Color.RED);
//        cos.addRect(10, 10, 100, 100);
//        cos.fill();
// 
//        // add two lines of different widths
//        cos.setLineWidth(1);
//        cos.moveTo(200, 250);
//        cos.lineTo(400, 250);
//        cos.closeAndStroke();
//        cos.setLineWidth(5);
//        cos.moveTo(200, 300);
//        cos.lineTo(400, 300);
//        cos.closeAndStroke();
// 
        // add an image
//        try {
//            PDImageXObject ximage = PDImageXObject.createFromFile("Simple.jpg", document);
//            float scale = 0.5f; // alter this value to set the image size
//            cos.drawImage(ximage, 100, 400, ximage.getWidth()*scale, ximage.getHeight()*scale);
//        } catch (IOException ioex) {
//            System.out.println("No image for you");
//        }
 
        // close the content stream for page 2
//        cos.close();
 
        // Save the results and ensure that the document is properly closed:
        document.save(outputFileName);
        document.close();
    }
}
