package org.openapitools.domain;

public class AssignationManager {

    public AssignationManager() {}

    public boolean assignTicket(Ticket ticket, User user, User assignedBy) {
        if (ticket == null || user == null || assignedBy == null) {
            System.out.println("Erreur: Le ticket, l'utilisateur ou l'assignateur est null.");
            return false;
        }

        if (!canAssign(user, ticket, assignedBy)) {
            return false;
        }

        if (ticket.isAssigned()) {
            System.out.println("Attention: Le ticket est déjà assigné. Réassignation en cours...");
            unassignTicket(ticket, assignedBy);
        }

        ticket.assignTo(user);

        System.out.println("Ticket " + ticket.getTicketID() + " assigné à " + user.getName() + " par " + assignedBy.getName());
        return true;
    }

    public boolean unassignTicket(Ticket ticket, User requestedBy) {
        if (ticket == null || requestedBy == null) {
            System.out.println("Erreur: Le ticket ou le demandeur est null.");
            return false;
        }

        if (!ticket.isAssigned()) {
            System.out.println("Erreur: Le ticket n'est pas assigné.");
            return false;
        }

        if (!requestedBy.canAssignTickets()) {
            System.out.println("Erreur: Vous n'avez pas la permission de désassigner ce ticket.");
            return false;
        }

        ticket.desassignTicketInternal(ticket);

        System.out.println("Ticket " + ticket.getTicketID() + " désassigné par " + requestedBy.getName());
        return true;
    }

    private boolean canAssign(User user, Ticket ticket, User requestedBy) {
        if (!requestedBy.canAssignTickets()) {
            System.out.println("Erreur: Vous n'avez pas la permission d'assigner ce ticket.");
            return false;
        }
        return true;
    }
}

