package org.openapitools.service;

import org.openapitools.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.io.*;

@Service
public class TicketService {
    
    private final TicketManager ticketManager;
    private final TicketCreator ticketCreator;
    private final UserService userService;

    @Autowired
    public TicketService(UserService userService) {
        this.ticketManager = new TicketManager();
        this.ticketCreator = new TicketCreator(1000, ticketManager);
        this.userService = userService;
        initializeDefaultTickets();
    }

    private void initializeDefaultTickets() {
        // Créer quelques tickets par défaut pour les tests
        User admin = userService.getUserById(1);
        if (admin != null) {
            ticketCreator.createTicket("Bug d'affichage", "L'interface ne s'affiche pas correctement", admin, "HAUTE");
            ticketCreator.createTicket("Amélioration performance", "Optimiser les requêtes base de données", admin, "MOYENNE");
        }
    }

    // ===== CRUD Operations =====
    
    public Ticket createTicket(String title, String description, String priority) {
        User defaultUser = userService.getUserById(1);
        return ticketCreator.createTicket(title, description, defaultUser, priority);
    }

    public Ticket getTicketById(int ticketId) {
        return ticketManager.getTicket(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return ticketManager.getAllTickets();
    }

    public List<Ticket> getTicketsByStatus(String status) {
        return ticketManager.getTicketsByStatus(status);
    }

    public List<Ticket> getTicketsByPriority(String priority) {
        return ticketManager.getTicketsByPriority(priority);
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return List.of();
        }
        return ticketManager.getTicketsByUser(user);
    }

    public List<Ticket> searchTicketsByTitle(String title) {
        return ticketManager.searchTicketsByTitle(title);
    }

    public List<Ticket> getUnassignedTickets() {
        return ticketManager.getUnassignedOpenTickets();
    }

    public List<Ticket> getCriticalTickets() {
        return ticketManager.getCriticalTickets();
    }

    public boolean updateTicket(int ticketId, String title, String priority) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }

        if (title != null && !title.trim().isEmpty()) {
            ticket.setTitle(title);
        }

        if (priority != null && !priority.trim().isEmpty()) {
            User defaultUser = userService.getUserById(1);
            return ticketManager.updateTicketPriority(ticketId, priority, defaultUser);
        }

        return true;
    }

    public boolean deleteTicket(int ticketId) {
        return ticketManager.removeTicket(ticketId);
    }

    // ===== Status Operations =====

    public boolean updateTicketStatus(int ticketId, String newStatus, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1); // Default admin
        }
        return ticketManager.updateTicketStatus(ticketId, newStatus, requester);
    }

    public List<String> getAvailableTransitions(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return List.of();
        }
        return ticketManager.getStatusManager().getValidTransitions(ticket.getStatus());
    }

    // ===== Priority Operations =====

    public boolean updateTicketPriority(int ticketId, String priority, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1);
        }
        return ticketManager.updateTicketPriority(ticketId, priority, requester);
    }

    // ===== Assignment Operations =====

    public boolean assignTicket(int ticketId, int userId, int assignedByUserId) {
        User user = userService.getUserById(userId);
        User assignedBy = userService.getUserById(assignedByUserId);
        
        if (user == null || assignedBy == null) {
            return false;
        }

        return ticketManager.assignTicket(ticketId, user, assignedBy);
    }

    public boolean unassignTicket(int ticketId, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1);
        }
        return ticketManager.unassignTicket(ticketId, requester);
    }

    public boolean closeTicket(int ticketId, int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            user = userService.getUserById(1);
        }
        return ticketManager.closeTicket(ticketId, user);
    }

    // ===== Description Operations =====

    public boolean updateTicketDescription(int ticketId, String textContent, List<String> imagePaths, List<String> videoPaths) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }

        TicketDescription newDesc = new TicketDescription(textContent);
        if (imagePaths != null) {
            for (String path : imagePaths) {
                newDesc.addImagePaths(path);
            }
        }
        if (videoPaths != null) {
            for (String path : videoPaths) {
                newDesc.addVideoPath(path);
            }
        }

        ticket.setDescription(newDesc);
        return true;
    }

    public boolean addImageToTicket(int ticketId, String imagePath) {
        return ticketManager.addImageToTicketDescription(ticketId, imagePath);
    }

    public boolean addVideoToTicket(int ticketId, String videoPath) {
        return ticketManager.addVideoToTicketDescription(ticketId, videoPath);
    }

    public List<String> getTicketImages(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null || ticket.getDescription() == null) {
            return List.of();
        }
        return ticket.getDescription().getImagePaths();
    }

    // ===== Comment Operations =====

    public boolean addComment(int ticketId, String comment, int authorId) {
        User author = userService.getUserById(authorId);
        if (author == null) {
            author = userService.getUserById(1);
        }
        return ticketManager.addCommentToTicket(ticketId, comment, author);
    }

    public List<String> getComments(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return List.of();
        }
        return ticket.getComments();
    }

    public boolean clearComments(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }
        ticketManager.getCommentManager().clearComments(ticket);
        return true;
    }

    // ===== Export Operations =====

    public File exportTicketToPDF(int ticketId, int userId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return null;
        }

        User assignatedUser = userService.getUserById(userId);

        List<String> comments = getComments(ticketId);

        
        // Méthode principal, pour exporter un ticket en pdf :)
        try {
            // *****************INITIALISATION DU CONTENU DU PDF****************
            PDDocument doc = new PDDocument(); //Création du document
            PDPage page = new PDPage(); //Ajout nouvelle page
            doc.addPage(page); //Création de la page
            PDPageContentStream content = new PDPageContentStream(doc, page); //Ajout de contenu (ce dans quoi on pourra insérer des choses)
            content.beginText(); //Début de la zone de texte

            // *****************NUMERO DU TICKET ET TITRE*****************
            content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
            content.newLineAtOffset(50, 700);
            content.showText(ticket.toString());
            
            // *****************INFORMATIONS DU TICKET****************
            content.setFont(PDType1Font.HELVETICA, 9); //Modification de la police
            content.newLineAtOffset(0, -12); // descend de 12 points sous le titre
            content.showText("Créé le : " + ticket.getCreationDate());
            content.newLineAtOffset(0, -10); // descend de 10 points
            content.showText("Dernière mise à jour : " + ticket.getUpdateDate());

            // *****************DESCRIPTION DU TICKET****************
            content.setFont(PDType1Font.HELVETICA, 12); //Modification de la police
            content.newLineAtOffset(0, -30); // descendre sous le titre et les infos
            // Découper le texte selon les sauts de ligne
            String[] lines = ticket.getDescription().getContentSummary().split("[\\r?\\n,]+"); //Sépare la description par ligne selon les sauts de lignes
                                                                                                                    // et sépare les liens (virgule) pour qu'ils rentrent tous dans le pdf
            for (String line : lines) { // Pour l'ensemble des lignes
                content.showText(line); // Ajouter la ligne sur le pdf
                content.newLineAtOffset(0, -15); // descend de 15 points à chaque ligne
            }

            // *****************STATUT DU TICKET****************
            content.newLineAtOffset(0, -20); // descendre sous la description (de 20 pts)
            content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
            content.showText("Statut : "); // Ajoute le titre de la section
            content.setFont(PDType1Font.HELVETICA, 12); //Modification de la police
            content.showText(ticket.getStatus()); // Ajoute le statut du ticket

            // *****************UTILISATEUR ASSIGNÉ DU TICKET****************
            if (assignatedUser != null) { //SI il y en a un on l'affiche
                content.newLineAtOffset(0, -15); // descend de 15 points
                content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
                content.showText("Utilisateur assigné : "); // Ajoute le titre de la section
                content.setFont(PDType1Font.HELVETICA, 12); //Modification de la police
                content.showText(assignatedUser.toString()); // affichage de l'utilisateur assigné
            }

            // *****************PRIORITÉ DU TICKET****************
            content.newLineAtOffset(0, -15); // descend de 15 points
            content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
            content.showText("Priorité : "); // Ajoute le titre de la section
            content.setFont(PDType1Font.HELVETICA, 12); //Modification de la police
            content.showText(ticket.getPriority()); // Ajoute la priorité du ticket

            // *****************COMMENTAIRES DU TICKET****************
            content.newLineAtOffset(0, -15); // descend de 15 points
            content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
            content.showText("Commentaires : "); // Ajoute le titre de la section
            content.setFont(PDType1Font.HELVETICA, 12); //Modification de la police
            content.newLineAtOffset(0, -15); // descend de 15 points
            for (String line : comments) { // Pour touts les commentaires
                content.showText(line); // Ajouter le commentaire
                content.newLineAtOffset(0, -15); // descendre de 15 points à chaque fois
            }
            content.setFont(PDType1Font.HELVETICA_BOLD, 12); //Modification de la police
            content.showText("IMAGES : "); // Ajoute le titre de la prochaine section

            // *****************FIN DU CONTENU TEXTE****************
            content.endText();

            // *****************IMAGES DU TICKET****************
            PDImageXObject pdImage;
            int x = 50; // position x initiale de l'image
            int y = 100; // position verticale initiale (en bas de la page)
            int hauteurmaxImage = 300; // hauteur maximum de l'image
            int largeurmaxImage = 500; // largeur maximum de l'imageS
            if (!getTicketImages(ticketId).isEmpty()) { //SI il y a des images
                for (String imagePath : getTicketImages(ticketId)) { //Pour tout les images récupérer le chemin d'accès
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

            File file = new File("ticket.pdf"); // Définition de la destination du fichier
            doc.save(file); // Sauvegarde du fichier pdf
            doc.close(); // Fermeture du document
            JOptionPane.showMessageDialog(null, "Ticket exporté en PDF avec succès !"); // Message de succès
            return file; // Retourne le fichier créé
        } catch (Exception ex) {
            // Affichage d'un message d'erreur si un erreur d'exécution survient
            JOptionPane.showMessageDialog(null, "Erreur lors de l'export PDF : " + ex.getMessage());
            ex.printStackTrace();
            return null; // Retourne le fichier créé
        }
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }

    public UserService getUserService() {
        return userService;
    }
}

