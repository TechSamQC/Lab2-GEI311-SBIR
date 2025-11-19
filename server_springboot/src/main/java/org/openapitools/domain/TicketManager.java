package org.openapitools.domain;

import java.util.ArrayList;
import java.util.List;

public class TicketManager {
    private List<Ticket> allTickets;
    private StatusManager statusManager;
    private PriorityManager priorityManager;
    private CommentManager commentManager;
    private AssignationManager assignationManager;
    private DescriptionManager descriptionManager;

    public TicketManager() {
        this.allTickets = new ArrayList<>();
        this.statusManager = new StatusManager();
        this.priorityManager = new PriorityManager();
        this.commentManager = new CommentManager();
        this.assignationManager = new AssignationManager();
        this.descriptionManager = new DescriptionManager();
    }

    public void addTicket(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Erreur: Impossible d'ajouter un ticket null.");
            return;
        }
        allTickets.add(ticket);
        System.out.println("Ticket #" + ticket.getTicketID() + " ajouté au système.");
    }

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

    public Ticket getTicket(int ticketID) {
        for (Ticket ticket : allTickets) {
            if (ticket.getTicketID() == ticketID) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(allTickets);
    }

    public List<Ticket> getTicketsByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != null && ticket.getStatus().equalsIgnoreCase(status)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    public List<Ticket> getTicketsByPriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getPriority() != null && ticket.getPriority().equalsIgnoreCase(priority)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    public List<Ticket> getTicketsByUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        List<Ticket> userTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getAssignedUserId() == user.getUserID()) {
                userTickets.add(ticket);
            }
        }
        return userTickets;
    }

    public boolean updateTicketStatus(int ticketID, String newStatus, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (newStatus.equals("TERMINE") || newStatus.equals("FERME")) {
            return closeTicket(ticketID, requestedBy);
        }

        if (newStatus.equals("OUVERT") && ticket.isAssigned()) {
            System.out.println("Erreur: Un ticket assigné ne peut pas être remis à OUVERT directement.");
            return false;
        }

        return statusManager.updateStatus(ticket, newStatus, requestedBy);
    }

    public boolean updateTicketPriority(int ticketID, String newPriority, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (ticket.getStatus().equals("TERMINE") || ticket.getStatus().equals("FERME")) {
            System.out.println("Erreur: La priorité d'un ticket terminé ne peut pas être changée.");
            return false;
        }

        return priorityManager.updatePriority(ticket, newPriority, requestedBy);
    }

    public boolean assignTicket(int ticketID, User user, User assignedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (ticket.getStatus().equals("TERMINE") || ticket.getStatus().equals("FERME")) {
            System.out.println("Impossible d'assigner un utilisateur, le ticket est terminé.");
            return false;
        }

        statusManager.updateStatus(ticket, "ASSIGNE", assignedBy);
        return assignationManager.assignTicket(ticket, user, assignedBy);
    }

    public boolean unassignTicket(int ticketID, User requestedBy) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (!ticket.getStatus().equals("TERMINE") && !ticket.getStatus().equals("FERME")) {
            statusManager.updateStatus(ticket, "OUVERT", requestedBy);
            return assignationManager.unassignTicket(ticket, requestedBy);
        }

        return false;
    }

    public boolean addCommentToTicket(int ticketID, String comment, User author) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (ticket.getStatus().equals("TERMINE") || ticket.getStatus().equals("FERME")) {
            System.out.println("Erreur: Impossible d'ajouter un commentaire à un ticket terminé.");
            return false;
        }

        return commentManager.addComment(ticket, comment, author);
    }

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

    public boolean closeTicket(int ticketID, User user) {
        Ticket ticket = getTicket(ticketID);
        if (ticket == null) {
            System.out.println("Erreur: Ticket #" + ticketID + " introuvable.");
            return false;
        }

        if (ticket.isAssigned()) {
            if (!user.canCloseTickets()) {
                System.out.println("Erreur: Vous n'avez pas la permission de terminer un ticket.");
                return false;
            }
            unassignTicket(ticketID, user);
        } else {
            if (!user.canAssignTickets()) {
                System.out.println("Erreur: Vous n'avez pas la permission de terminer un ticket.");
                return false;
            }
        }

        if (statusManager.updateStatus(ticket, "FERME", user)) {
            return true;
        }
        
        return false;
    }

    public List<Ticket> searchTicketsByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getTitle() != null && ticket.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    public List<Ticket> getCriticalTickets() {
        return getTicketsByPriority("CRITIQUE");
    }

    public List<Ticket> getUnassignedOpenTickets() {
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != null && ticket.getStatus().equals("OUVERT") && !ticket.isAssigned()) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }

    public PriorityManager getPriorityManager() {
        return priorityManager;
    }

    public CommentManager getCommentManager() {
        return commentManager;
    }

    public AssignationManager getAssignationManager() {
        return assignationManager;
    }

    public DescriptionManager getDescriptionManager() {
        return descriptionManager;
    }
}

