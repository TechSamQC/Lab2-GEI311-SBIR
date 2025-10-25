import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private List<Ticket> allTickets;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private commentManager commentManager;
    private AssignationManager assignationManager;
    private descriptionManager descriptionManager;

    // Constructeur
    public TicketManager() {
        this.allTickets = new ArrayList<>();
        this.statusManager = new statusManager();
        this.priorityManager = new PriorityManager();
        this.commentManager = new commentManager();
        this.assignationManager = new AssignationManager();
        this.descriptionManager = new descriptionManager();
    }

    // ============= GESTION DES TICKETS =============

    // Ajoute un ticket au système
    public void addTicket(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Erreur: Impossible d'ajouter un ticket null.");
            return;
        }

        allTickets.add(ticket);
        System.out.println("Ticket #" + ticket.getTicketID() + " ajouté au système.");
    }

    // Supprime un ticket du système
    public boolean removeTicket(int ticketID) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket introuvable.");
            return false;
        }

        allTickets.remove(ticket);
        System.out.println("Ticket #" + ticketID + " supprimé du système.");
        return true;
    }

    // Obtient un ticket par son ID
    public Ticket getTicket(int ticketID) {
        for (Ticket ticket : allTickets) {
            if (ticket.getTicketID() == ticketID) {
                return ticket;
            }
        }
        return null;
    }

    // Obtient tous les tickets
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(allTickets);
    }

    // Obtient les tickets par statut
    public List<Ticket> getTicketsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Filtrer les tickets par statut
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != null && ticket.getStatus().equalsIgnoreCase(status)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    // Obtient les tickets par priorité
    public List<Ticket> getTicketsByPriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Filtrer les tickets par priorité
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getPriority() != null && ticket.getPriority().equalsIgnoreCase(priority)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    // Obtient les tickets d'un utilisateur (créés ou assignés)
    public List<Ticket> getTicketsByUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        // Filtrer les tickets par utilisateur
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getAssignedUserId() == user.getUserID()) {
                userTickets.add(ticket);
            }
        }

        return userTickets;
    }

    // ============= MODIFICATIONS VIA MANAGERS =============

    // Met à jour le statut d'un ticket
    public boolean updateTicketStatus(int ticketID, String newStatus, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        // Si le nouveau statut est "TERMINÉ", désassigner le ticket après mise à jour
        if (newStatus.equals("TERMINÉ")) {
            return closeTicket(ticketID, requestedBy);
        }

        // Empêcher de remettre un ticket assigné à "OUVERT" sans le désassigner d'abord
        if (newStatus.equals("OUVERT") && ticket.isAssigned()) {
            System.out.println("Erreur: Un ticket assigné ne peut pas être remis à OUVERT directement, il doit d'abord être désassigné.");
            return false;
        }

        return statusManager.updateStatus(ticket, newStatus, requestedBy);
    }

    // Met à jour la priorité d'un ticket
    public boolean updateTicketPriority(int ticketID, String newPriority, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        // Un ticket terminé ne peut pas voir sa priorité changée
        if (ticket.getStatus().equals("TERMINÉ")) {
            System.out.println("Erreur: La priorité d'un ticket terminé ne peut pas être changée.");
            return false;
        }

        return priorityManager.updatePriority(ticket, newPriority, requestedBy);
    }

    // Assigne un ticket à un utilisateur
    public boolean assignTicket(int ticketID, User user, User assignedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        // Ne peut pas assigner un ticket terminé
        if (ticket.getStatus().equals("TERMINÉ")) {
            System.out.println("Impossible d'assigner un utilisateur, le ticket est terminé.");
            return false;
        }

        statusManager.updateStatus(ticket, "ASSIGNÉ", assignedBy);
        return assignationManager.assignTicket(ticket, user, assignedBy);
    }

    // Désassigne un ticket
    public boolean unassignTicket(int ticketID, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        // Si le ticket n'est pas terminé, le remettre à OUVERT et désassigner
        if (!ticket.getStatus().equals("TERMINÉ")) {
            statusManager.updateStatus(ticket, "OUVERT", requestedBy);
            return assignationManager.unassignTicket(ticket, requestedBy);
        }

        return false; // Rien à faire si le ticket est déjà terminé
    }

    // Ajoute un commentaire à un ticket
    public boolean addCommentToTicket(int ticketID, String comment, User author) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        // Vérifier que le ticket n'est pas terminé
        if (ticket.getStatus().equals("TERMINÉ")) {
            System.out.println("Erreur: Impossible d'ajouter un commentaire à un ticket terminé.");
            return false;
        }

        // Vérifier que le ticket est assigné
        if (ticket.isAssigned() == false) {
            System.out.println("Erreur: Impossible d'ajouter un commentaire à un ticket non assigné.");
            return false;
        }

        return commentManager.addComment(ticket, comment, author);
    }

    // Met à jour la description d'un ticket
    public boolean updateTicketDescription(int ticketID, String newDescription) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (descriptionManager.updateDescription(ticket.getDescription(), newDescription)) {
            System.out.println("Description du ticket #" + ticketID + " mise à jour.");
            return true;
        }
        return false;
    }

    // Ajoute une image à la description d'un ticket
    public boolean addImageToTicketDescription(int ticketID, String imagePath) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (descriptionManager.addImageToDescription(ticket.getDescription(), imagePath)) {
            System.out.println("Image ajoutée à la description du ticket #" + ticketID + ".");
            return true;
        }
        return false;
    }

    // Ajoute une vidéo à la description d'un ticket
    public boolean addVideoToTicketDescription(int ticketID, String videoPath) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (descriptionManager.addVideoToDescription(ticket.getDescription(), videoPath)) {
            System.out.println("Vidéo ajoutée à la description du ticket #" + ticketID + ".");
            return true;
        }
        return false;
    }

    // Termine un ticket (après validation)
    public boolean closeTicket(int ticketID, User user) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (!user.canCloseTickets()) {
            System.out.println("Erreur: Vous n'avez pas la permission de terminer un ticket.");
            return false;
        }

        if (ticket.isAssigned()) {
            unassignTicket(ticketID, user);
        }
        if (statusManager.updateStatus(ticket, "TERMINÉ", user)) {
            return true;
        }
        
        return false;
    }

    /**
     * Exporte un ticket en PDF avec toutes ses informations
     * @param ticketID L'ID du ticket à exporter
     * @param filePath Le chemin du fichier PDF à créer
     * @return true si l'export a réussi, false sinon
     */
    public boolean exportTicketToPDF(int ticketID, String filePath) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        try {
            // Vérifier si iText est disponible
            Class.forName("com.itextpdf.kernel.pdf.PdfWriter");
            
            // Si iText est disponible, l'utiliser pour générer le PDF
            return exporterAvecIText(ticket, filePath);
            
        } catch (ClassNotFoundException e) {
            // Si iText n'est pas disponible, utiliser une solution alternative simple
            System.out.println("Avertissement: iText n'est pas disponible. Export en texte simple.");
            return exporterEnTexteSimple(ticket, filePath);
        }
    }

    /**
     * Exporte le ticket en PDF en utilisant iText 7
     * Cette méthode nécessite que les JAR d'iText soient dans le classpath
     * Inclut les vraies images dans le PDF
     */
    private boolean exporterAvecIText(Ticket ticket, String filePath) {
        try {
            // Imports dynamiques pour éviter les erreurs si iText n'est pas présent
            Class<?> pdfWriterClass = Class.forName("com.itextpdf.kernel.pdf.PdfWriter");
            Class<?> pdfDocumentClass = Class.forName("com.itextpdf.kernel.pdf.PdfDocument");
            Class<?> documentClass = Class.forName("com.itextpdf.layout.Document");
            Class<?> paragraphClass = Class.forName("com.itextpdf.layout.element.Paragraph");
            Class<?> imageClass = Class.forName("com.itextpdf.layout.element.Image");
            Class<?> imageDataFactoryClass = Class.forName("com.itextpdf.io.image.ImageDataFactory");
            
            // Créer le PdfWriter
            Object writer = pdfWriterClass.getConstructor(String.class).newInstance(filePath);
            
            // Créer le PdfDocument
            Object pdfDoc = pdfDocumentClass.getConstructor(pdfWriterClass).newInstance(writer);
            
            // Créer le Document
            Object document = documentClass.getConstructor(pdfDocumentClass).newInstance(pdfDoc);
            
            // === EN-TÊTE DU DOCUMENT ===
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "═══════════════════════════════════════════════════════════════");
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "TICKET #" + ticket.getTicketID() + " - " + ticket.getTitle());
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "═══════════════════════════════════════════════════════════════");
            addParagraphToDocument(document, paragraphClass, documentClass, "");
            
            // === INFORMATIONS DU TICKET ===
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "📋 INFORMATIONS GÉNÉRALES");
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "   Statut : " + ticket.getStatus());
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "   Priorité : " + ticket.getPriority());
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "   Date de création : " + ticket.getCreationDate());
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "   Dernière mise à jour : " + ticket.getUpdateDate());
            addParagraphToDocument(document, paragraphClass, documentClass, "");
            
            // === DESCRIPTION ===
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "───────────────────────────────────────────────────────────────");
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "📝 DESCRIPTION");
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "───────────────────────────────────────────────────────────────");
            String description = ticket.getDescription().getTextContent();
            if (description != null && !description.trim().isEmpty()) {
                addParagraphToDocument(document, paragraphClass, documentClass, description);
            } else {
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "   (Aucune description)");
            }
            addParagraphToDocument(document, paragraphClass, documentClass, "");
            
            // === IMAGES ===
            if (ticket.getDescription().hasImages()) {
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "🖼️  IMAGES (" + ticket.getDescription().getImagePaths().size() + ")");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                
                int imageNumber = 1;
                for (String imagePath : ticket.getDescription().getImagePaths()) {
                    try {
                        // Vérifier si le fichier existe
                        java.io.File imageFile = new java.io.File(imagePath);
                        if (imageFile.exists()) {
                            // Ajouter le nom de l'image
                            addParagraphToDocument(document, paragraphClass, documentClass, 
                                "\n   Image " + imageNumber + " : " + imageFile.getName());
                            
                            // Charger et ajouter l'image au PDF
                            Object imageData = imageDataFactoryClass.getMethod("create", String.class)
                                .invoke(null, imagePath);
                            Object pdfImage = imageClass.getConstructor(
                                Class.forName("com.itextpdf.io.image.ImageData"))
                                .newInstance(imageData);
                            
                            // Redimensionner l'image si nécessaire (largeur max 400 points)
                            imageClass.getMethod("setAutoScale", boolean.class).invoke(pdfImage, true);
                            imageClass.getMethod("scaleToFit", float.class, float.class)
                                .invoke(pdfImage, 400f, 400f);
                            
                            // Ajouter l'image au document
                            documentClass.getMethod("add", Class.forName("com.itextpdf.layout.element.IBlockElement"))
                                .invoke(document, pdfImage);
                            
                            imageNumber++;
                        } else {
                            addParagraphToDocument(document, paragraphClass, documentClass, 
                                "   Image " + imageNumber + " : " + imagePath + " (fichier introuvable)");
                            imageNumber++;
                        }
                    } catch (Exception imgEx) {
                        System.out.println("Impossible d'ajouter l'image au PDF : " + imagePath);
                        addParagraphToDocument(document, paragraphClass, documentClass, 
                            "   Image " + imageNumber + " : " + imagePath + " (erreur de chargement)");
                        imageNumber++;
                    }
                }
                addParagraphToDocument(document, paragraphClass, documentClass, "");
            }
            
            // === VIDÉOS ===
            if (ticket.getDescription().hasVideos()) {
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "🎬 VIDÉOS (" + ticket.getDescription().getVideoPaths().size() + ")");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                
                int videoNumber = 1;
                for (String videoPath : ticket.getDescription().getVideoPaths()) {
                    java.io.File videoFile = new java.io.File(videoPath);
                    String status = videoFile.exists() ? "" : " (fichier introuvable)";
                    addParagraphToDocument(document, paragraphClass, documentClass, 
                        "   " + videoNumber + ". " + videoFile.getName() + status);
                    addParagraphToDocument(document, paragraphClass, documentClass, 
                        "      Chemin : " + videoPath);
                    videoNumber++;
                }
                addParagraphToDocument(document, paragraphClass, documentClass, "");
            }
            
            // === COMMENTAIRES ===
            if (!ticket.getComments().isEmpty()) {
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "💬 COMMENTAIRES (" + ticket.getComments().size() + ")");
                addParagraphToDocument(document, paragraphClass, documentClass, 
                    "───────────────────────────────────────────────────────────────");
                
                int commentNumber = 1;
                for (String comment : ticket.getComments()) {
                    addParagraphToDocument(document, paragraphClass, documentClass, 
                        "   " + commentNumber + ". " + comment);
                    commentNumber++;
                }
                addParagraphToDocument(document, paragraphClass, documentClass, "");
            }
            
            // === PIED DE PAGE ===
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "═══════════════════════════════════════════════════════════════");
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "Fin du ticket - Généré le " + java.time.LocalDate.now());
            addParagraphToDocument(document, paragraphClass, documentClass, 
                "═══════════════════════════════════════════════════════════════");
            
            // Fermer le document
            documentClass.getMethod("close").invoke(document);
            
            System.out.println("✅ Ticket #" + ticket.getTicketID() + " exporté en PDF avec succès : " + filePath);
            return true;
            
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'export PDF avec iText : " + e.getMessage());
            e.printStackTrace();
            System.out.println("Tentative d'export en format texte...");
            return exporterEnTexteSimple(ticket, filePath);
        }
    }

    /**
     * Méthode auxiliaire pour ajouter un paragraphe au document iText
     */
    private void addParagraphToDocument(Object document, Class<?> paragraphClass, 
                                       Class<?> documentClass, String text) throws Exception {
        Object paragraph = paragraphClass.getConstructor(String.class).newInstance(text);
        documentClass.getMethod("add", Class.forName("com.itextpdf.layout.element.IBlockElement"))
            .invoke(document, paragraph);
    }

    /**
     * Export en fichier texte simple si iText n'est pas disponible
     * Crée un fichier .txt au lieu de .pdf
     */
    private boolean exporterEnTexteSimple(Ticket ticket, String filePath) {
        try {
            // Remplacer l'extension .pdf par .txt
            String txtFilePath = filePath.replace(".pdf", ".txt");
            
            // Créer le contenu du fichier
            StringBuilder content = new StringBuilder();
            content.append("=".repeat(60)).append("\n");
            content.append("TICKET #").append(ticket.getTicketID()).append("\n");
            content.append("=".repeat(60)).append("\n\n");
            
            content.append("Titre : ").append(ticket.getTitle()).append("\n");
            content.append("Statut : ").append(ticket.getStatus()).append("\n");
            content.append("Priorité : ").append(ticket.getPriority()).append("\n");
            content.append("Date de création : ").append(ticket.getCreationDate()).append("\n");
            content.append("Dernière mise à jour : ").append(ticket.getUpdateDate()).append("\n");
            
            content.append("\n").append("-".repeat(60)).append("\n");
            content.append("DESCRIPTION\n");
            content.append("-".repeat(60)).append("\n");
            content.append(ticket.getDescription().getTextContent()).append("\n");
            
            if (ticket.getDescription().hasImages()) {
                content.append("\n").append("-".repeat(60)).append("\n");
                content.append("IMAGES (").append(ticket.getDescription().getImagePaths().size()).append(")\n");
                content.append("-".repeat(60)).append("\n");
                for (String imagePath : ticket.getDescription().getImagePaths()) {
                    content.append("  - ").append(imagePath).append("\n");
                }
            }
            
            if (ticket.getDescription().hasVideos()) {
                content.append("\n").append("-".repeat(60)).append("\n");
                content.append("VIDÉOS (").append(ticket.getDescription().getVideoPaths().size()).append(")\n");
                content.append("-".repeat(60)).append("\n");
                for (String videoPath : ticket.getDescription().getVideoPaths()) {
                    content.append("  - ").append(videoPath).append("\n");
                }
            }
            
            if (!ticket.getComments().isEmpty()) {
                content.append("\n").append("-".repeat(60)).append("\n");
                content.append("COMMENTAIRES\n");
                content.append("-".repeat(60)).append("\n");
                for (String comment : ticket.getComments()) {
                    content.append("  • ").append(comment).append("\n");
                }
            }
            
            content.append("\n").append("=".repeat(60)).append("\n");
            content.append("Fin du ticket\n");
            content.append("=".repeat(60)).append("\n");
            
            // Écrire dans le fichier
            java.nio.file.Files.write(java.nio.file.Paths.get(txtFilePath), content.toString().getBytes());
            
            System.out.println("Ticket #" + ticket.getTicketID() + " exporté en TXT : " + txtFilePath);
            System.out.println("Note: Pour l'export PDF, installez iText (voir INSTALLATION_ITEXT.md)");
            return true;
            
        } catch (Exception e) {
            System.out.println("Erreur lors de l'export en texte simple : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ============= MÉTHODES UTILITAIRES =============

    // Recherche de tickets par titre (contient)
    public List<Ticket> searchTicketsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Rechercher les tickets par titre
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getTitle() != null && ticket.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    // Obtient les tickets critiques
    public List<Ticket> getCriticalTickets() {
        return getTicketsByPriority("CRITIQUE");
    }

    // Obtient les tickets ouverts non assignés
    public List<Ticket> getUnassignedOpenTickets() {
        // Filtrer les tickets ouverts non assignés
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != null && ticket.getStatus().equals("OUVERT") && !ticket.isAssigned()) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    // ============= GETTERS POUR LES MANAGERS =============

    public statusManager getStatusManager() {
        return statusManager;
    }

    public PriorityManager getPriorityManager() {
        return priorityManager;
    }

    public commentManager getCommentManager() {
        return commentManager;
    }

    public AssignationManager getAssignationManager() {
        return assignationManager;
    }

    public descriptionManager getDescriptionManager() {
        return descriptionManager;
    }
}

