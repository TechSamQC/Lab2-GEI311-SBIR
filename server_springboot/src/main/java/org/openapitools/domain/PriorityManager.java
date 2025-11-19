package org.openapitools.domain;

import java.util.*;

public class PriorityManager {
    private List<String> validPriorities;

    public PriorityManager() {
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    public boolean updatePriority(Ticket ticket, String newPriority, User requester) {
        if (ticket == null || requester == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        if (!validatePriority(newPriority)) {
            System.out.println("Erreur: Priorité invalide: " + newPriority);
            return false;
        }

        if (!canUserChangePriority(requester, ticket)) {
            System.out.println("Erreur: Vous n'avez pas la permission de changer la priorité.");
            return false;
        }

        String oldPriority = ticket.getPriority();
        ticket.setPriority(newPriority);
        System.out.println("Priorité du ticket " + ticket.getTicketID() + " changée de " + oldPriority + " à " + newPriority);
        return true;
    }

    private boolean validatePriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return false;
        }
        return validPriorities.contains(priority.toUpperCase());
    }

    public boolean canUserChangePriority(User requester, Ticket ticket) {
        if (requester == null || ticket == null) {
            return false;
        }

        if (requester.isAdmin()) {
            return true;
        }

        if (ticket.getAssignedUserId() == requester.getUserID()) {
            return true;
        }

        return false;
    }

    public List<String> getValidPriorities() {
        return new ArrayList<>(validPriorities);
    }
}

