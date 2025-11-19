package org.openapitools.domain;

import java.util.*;

public class StatusManager {
    
    private List<String> validStatuses;
    private Map<String, List<String>> statusTransitions;

    public StatusManager() {
        this.validStatuses = Arrays.asList("OUVERT", "ASSIGNE", "TERMINE", "FERME");
        this.statusTransitions = new HashMap<>();
        initializeTransitions();
    }

    public void initializeTransitions() {
        statusTransitions.put("OUVERT", Arrays.asList("ASSIGNE", "FERME"));
        statusTransitions.put("ASSIGNE", Arrays.asList("TERMINE", "FERME"));
        statusTransitions.put("TERMINE", new ArrayList<>());
        statusTransitions.put("FERME", new ArrayList<>());
    }

    public boolean updateStatus(Ticket ticket, String newStatus, User requester) {
        if (ticket == null || requester == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        if (!validateStatus(newStatus)) {
            System.out.println("Erreur: Statut invalide: " + newStatus);
            return false;
        }

        if (!validateTransition(ticket.getStatus(), newStatus)) {
            System.out.println("Erreur: Transition de statut invalide de " + ticket.getStatus() + " vers " + newStatus);
            System.out.println("Transitions valides depuis " + ticket.getStatus() + ": " + getValidTransitions(ticket.getStatus()));
            return false;
        }

        if (!canUserChangeStatus(requester, ticket, newStatus)) {
            System.out.println("Erreur: Vous n'avez pas la permission de changer ce statut.");
            return false;
        }

        String oldStatus = ticket.getStatus();
        ticket.updateStatus(newStatus);
        System.out.println("Statut du ticket " + ticket.getTicketID() + " changé de " + oldStatus + " à " + newStatus);
        return true;
    }

    private boolean validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return validStatuses.contains(status.toUpperCase());
    }

    private boolean validateTransition(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }

        if (currentStatus.equalsIgnoreCase(newStatus)) {
            return false;
        }

        List<String> allowedTransitions = statusTransitions.get(currentStatus.toUpperCase());
        if (allowedTransitions == null) {
            return false;
        }

        return allowedTransitions.contains(newStatus.toUpperCase());
    }

    private boolean canUserChangeStatus(User requester, Ticket ticket, String newStatus) {
        if (requester == null || ticket == null) {
            return false;
        }

        if (requester.isAdmin()) {
            return true;
        }

        if (requester.canAssignTickets()) {
            return true;
        }

        System.out.println("Erreur: Les utilisateurs sans privilèges ne peuvent pas modifier le statut des tickets.");
        return false;
    }

    public List<String> getValidTransitions(String currentStatus) {
        if (currentStatus == null) {
            return new ArrayList<>();
        }
        
        List<String> transitions = statusTransitions.get(currentStatus.toUpperCase());
        return transitions != null ? new ArrayList<>(transitions) : new ArrayList<>();
    }

    public List<String> getValidStatuses() {
        return new ArrayList<>(validStatuses);
    }
}

