import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javax.swing.*;
import java.io.*;

public class pdfExporter {
    // Initialisation des managers
    private descriptionManager descManager;
    private commentManager commManager;

    // Constructeur
    public pdfExporter(descriptionManager descManager, commentManager commManager) {this.descManager = descManager; this.commManager = commManager;}
    
    // Méthode principal, pour exporter un ticket en pdf :)
    public void exportTicketToPDF (Ticket ticket, String destinationPath, User assignatedUser) {
        try {
            // *****************INITIALISATION DU CONTENU DU PDF****************
            PDDocument doc = new PDDocument(); //Création du document
            PDPage page = new PDPage(); //Ajout nouvelle page
            doc.addPage(page); //Création de la page
            PDPageContentStream content = new PDPageContentStream(doc, page); //Ajout de contenu (ce dans quoi on pourra insérer des choses)
            content.beginText(); //Début de la zone de texte

            // *****************NUMERO DU TICKET ET TITRE*****************
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
            content.newLineAtOffset(50, 700);
            content.showText(ticket.toString());
            
            // *****************INFORMATIONS DU TICKET****************
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9); //Modification de la police
            content.newLineAtOffset(0, -12); // descend de 12 points sous le titre
            content.showText("Créé le : " + ticket.getCreationDate());
            content.newLineAtOffset(0, -10); // descend de 10 points
            content.showText("Dernière mise à jour : " + ticket.getUpdateDate());

            // *****************DESCRIPTION DU TICKET****************
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12); //Modification de la police
            content.newLineAtOffset(0, -30); // descendre sous le titre et les infos
            // Découper le texte selon les sauts de ligne
            String[] lines = descManager.getDescriptionSummary(ticket.getDescription()).split("[\\r?\\n,]+"); //Sépare la description par ligne selon les sauts de lignes
                                                                                                                    // et sépare les liens (virgule) pour qu'ils rentrent tous dans le pdf
            for (String line : lines) { // Pour l'ensemble des lignes
                content.showText(line); // Ajouter la ligne sur le pdf
                content.newLineAtOffset(0, -15); // descend de 15 points à chaque ligne
            }

            // *****************STATUT DU TICKET****************
            content.newLineAtOffset(0, -20); // descendre sous la description (de 20 pts)
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
            content.showText("Statut : "); // Ajoute le titre de la section
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12); //Modification de la police
            content.showText(ticket.getStatus()); // Ajoute le statut du ticket

            // *****************UTILISATEUR ASSIGNÉ DU TICKET****************
            if (assignatedUser != null) { //SI il y en a un on l'affiche
                content.newLineAtOffset(0, -15); // descend de 15 points
                content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
                content.showText("Utilisateur assigné : "); // Ajoute le titre de la section
                content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12); //Modification de la police
                content.showText(assignatedUser.toString()); // affichage de l'utilisateur assigné
            }

            // *****************PRIORITÉ DU TICKET****************
            content.newLineAtOffset(0, -15); // descend de 15 points
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
            content.showText("Priorité : "); // Ajoute le titre de la section
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12); //Modification de la police
            content.showText(ticket.getPriority()); // Ajoute la priorité du ticket

            // *****************COMMENTAIRES DU TICKET****************
            content.newLineAtOffset(0, -15); // descend de 15 points
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
            content.showText("Commentaires : "); // Ajoute le titre de la section
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12); //Modification de la police
            content.newLineAtOffset(0, -15); // descend de 15 points
            for (String line : commManager.getComments(ticket)) { // Pour touts les commentaires
                content.showText(line); // Ajouter le commentaire
                content.newLineAtOffset(0, -15); // descendre de 15 points à chaque fois
            }
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12); //Modification de la police
            content.showText("IMAGES : "); // Ajoute le titre de la prochaine section

            // *****************FIN DU CONTENU TEXTE****************
            content.endText();

            // *****************IMAGES DU TICKET****************
            PDImageXObject pdImage;
            int x = 50; // position x initiale de l'image
            int y = 100; // position verticale initiale (en bas de la page)
            int hauteurmaxImage = 300; // hauteur maximum de l'image
            int largeurmaxImage = 500; // largeur maximum de l'imageS
            if (!ticket.getDescription().getImagePaths().isEmpty()) { //SI il y a des images
                for (String imagePath : ticket.getDescription().getImagePaths()) { //Pour tout les images récupérer le chemin d'accès
                    //SI y est plus petit que 0, on change de page.
                    if (y < 0) {
                        content.close(); //Fermeture du contenu actuel
                        page = new PDPage(); //Nouvel page
                        doc.addPage(page); // Création de la page
                        content = new PDPageContentStream(doc, page); // Nouveau contenu
                        y = 700 - hauteurmaxImage; // réinitialisation de y
                    }
                    pdImage = PDImageXObject.createFromFile(imagePath, doc); // Création de l'image sous le format pour le pdf
                    content.drawImage(pdImage, x, y, largeurmaxImage, hauteurmaxImage); // Dessiner l'image dans le pdf
                    y -= hauteurmaxImage + 10; // descendre pour la prochaine image
                }
            }
            content.close(); // Fermeture du contenu

            File file = new File(destinationPath); // Définition de la destination du fichier
            doc.save(file); // Sauvegarde du fichier pdf
            doc.close(); // Fermeture du document
            JOptionPane.showMessageDialog(null, "Ticket exporté en PDF avec succès !"); // Message de succès
        } catch (Exception ex) {
            // Affichage d'un message d'erreur si un erreur d'exécution survient
            JOptionPane.showMessageDialog(null, "Erreur lors de l'export PDF : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

