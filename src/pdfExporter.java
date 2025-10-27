import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javax.swing.*;
import java.io.*;
import java.util.List;

public class pdfExporter {
    public pdfExporter() {}
    
    public void exportTicketToPDF (Ticket ticket, String destinationPath) {
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.beginText();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.newLineAtOffset(50, 700);
            content.showText("Mon ticket ici !");
            content.endText();
            content.close();

            File file = new File(destinationPath);
            doc.save(file);
            doc.close();
            JOptionPane.showMessageDialog(null, "Ticket exporté en PDF avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'export PDF : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

