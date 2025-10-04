import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketManager {
    private List<Ticket> allTickets;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private commentManager commentManager;
    private AssignationManager assignationManager;
    private descriptionManager descriptionManager;
    private Display display;

    // Constructeur
    public TicketManager() {
        this.allTickets = new ArrayList<>();
        this.statusManager = new statusManager();
        this.priorityManager = new PriorityManager();
        this.commentManager = new commentManager();
        this.assignationManager = new AssignationManager();
        this.descriptionManager = new descriptionManager();
        this.display = new Display();
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
        commentManager.clearComments(ticketID);
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
        return allTickets.stream()
                .filter(ticket -> ticket.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // Obtient les tickets d'un utilisateur (créés ou assignés)
    public List<Ticket> getTicketsByUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        return allTickets.stream()
                .filter(ticket -> ticket.getAssignedUserId() == user.getUserID())
                .collect(Collectors.toList());
    }

    // Obtient les tickets par priorité
    public List<Ticket> getTicketsByPriority(String priority) {
        return allTickets.stream()
                .filter(ticket -> ticket.getPriority() != null && ticket.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    // ============= MODIFICATIONS VIA MANAGERS =============

    // Met à jour le statut d'un ticket
    public boolean updateTicketStatus(int ticketID, String newStatus, User user) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        return statusManager.updateStatus(ticket, newStatus, user);
    }

    // Met à jour la priorité d'un ticket
    public boolean updateTicketPriority(int ticketID, String newPriority, User user) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        return priorityManager.updatePriority(ticket, newPriority, user);
    }

    // Assigne un ticket à un utilisateur
    public boolean assignTicket(int ticketID, User user, User assignedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        return assignationManager.assignTicket(ticket, user, assignedBy);
    }

    // Désassigne un ticket
    public boolean unassignTicket(int ticketID, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        return assignationManager.unassignTicket(ticket, requestedBy);
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

        return commentManager.addComment(ticketID, comment, author);
    }

    // Met à jour la description d'un ticket
    public boolean updateTicketDescription(int ticketID, String newDescription) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        ticket.setDescription(newDescription);
        System.out.println("Description du ticket #" + ticketID + " mise à jour.");
        return true;
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

        return statusManager.updateStatus(ticket, "TERMINÉ", user);
    }

    // ============= AFFICHAGE VIA DISPLAY =============

    // Affiche un ticket
    public void displayTicket(int ticketID) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return;
        }

        List<String> comments = commentManager.getCommentsWithAuthors(ticketID);
        display.displayTicketWithComments(ticket, comments);
    }

    // Affiche tous les tickets
    public void displayAllTickets() {
        display.displayAllTickets(allTickets);
    }

    // Exporte un ticket en PDF
    public boolean exportTicketToPDF(int ticketID, String filePath) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        List<String> comments = commentManager.getCommentsWithAuthors(ticketID);
        return display.exportTicketToPDF(ticket, null, comments, filePath);
    }

    // ============= MÉTHODES UTILITAIRES =============

    // Affiche les statistiques du système
    public void displaySystemStatistics() {
        int total = allTickets.size();
        int open = getTicketsByStatus("OUVERT").size() + getTicketsByStatus("ASSIGNÉ").size();
        int closed = getTicketsByStatus("TERMINÉ").size();
        
        display.displayStatistics(total, open, closed);
    }

    // Obtient les tickets par statut et les affiche
    public void displayTicketsByStatus(String status) {
        List<Ticket> tickets = getTicketsByStatus(status);
        display.displayTicketsByStatus(tickets, status);
    }

    // Obtient les tickets par priorité et les affiche
    public void displayTicketsByPriority(String priority) {
        List<Ticket> tickets = getTicketsByPriority(priority);
        display.displayTicketsByPriority(tickets, priority);
    }

    // Obtient les tickets d'un utilisateur et les affiche
    public void displayTicketsByUser(User user) {
        List<Ticket> tickets = getTicketsByUser(user);
        display.displayTicketsByUser(tickets, user);
    }

    // Obtient les statistiques d'assignation
    public void displayAssignationStatistics() {
        assignationManager.displayAssignmentStatistics();
    }

    // Recherche de tickets par titre (contient)
    public List<Ticket> searchTicketsByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return allTickets.stream()
                .filter(ticket -> ticket.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Obtient les tickets critiques
    public List<Ticket> getCriticalTickets() {
        return getTicketsByPriority("CRITIQUE");
    }

    // Obtient les tickets ouverts non assignés
    public List<Ticket> getUnassignedOpenTickets() {
        return allTickets.stream()
                .filter(ticket -> ticket.getStatus().equals("OUVERT") && ticket.getAssignedUserId() == 0)
                .collect(Collectors.toList());
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

    public Display getDisplay() {
        return display;
    }
}

