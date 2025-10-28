import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private List<Ticket> allTickets;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private commentManager commentManager;
    private AssignationManager assignationManager;
    private descriptionManager descriptionManager;
    private pdfExporter PDFexporter;

    // Constructeur
    public TicketManager() {
        this.allTickets = new ArrayList<>();
        this.statusManager = new statusManager();
        this.priorityManager = new PriorityManager();
        this.commentManager = new commentManager();
        this.assignationManager = new AssignationManager();
        this.descriptionManager = new descriptionManager();
        this.PDFexporter = new pdfExporter(descriptionManager);
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

    // Obtient les tickets par statut et utilisateur
    public List<Ticket> getTicketsByStatusUser(String status, User user) {
        if (user == null || status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Filtrer les tickets par statut ET utilisateur
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != null && ticket.getStatus().equalsIgnoreCase(status)) {
                if (ticket.getAssignedUserId() == user.getUserID()) {
                    filteredTickets.add(ticket);
                }
            }
        }
        return filteredTickets;
    }

    // Obtient les tickets ouverts + ceux de l'utilisateur (pour les devs)
    public List<Ticket> getTicketsDeveloper(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        // Filtrer les tickets par utilisateur + OUVERTS
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus().equalsIgnoreCase("OUVERT") || ticket.getAssignedUserId() == user.getUserID()) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
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

    // Exporte un ticket en PDF
    public boolean exportTicketToPDF(int ticketID, String filePath) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }
        PDFexporter.exportTicketToPDF(ticket, filePath);
        return true;
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

