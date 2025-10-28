import java.util.*;

public class PriorityManager {
    private List<String> validPriorities;

    // Constructeur
    public PriorityManager() {
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    // Met à jour la priorité d'un ticket
    public boolean updatePriority(Ticket ticket, String newPriority, User requester) {
        if (ticket == null || requester == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        // Validation de la priorité
        if (!validatePriority(newPriority)) {
            System.out.println("Erreur: Priorité invalide: " + newPriority);
            return false;
        }

        // Vérification des permissions
        if (!canUserChangePriority(requester, ticket)) {
            System.out.println("Erreur: Vous n'avez pas la permission de changer la priorité.");
            return false;
        }

        // Mise à jour de la priorité
        String oldPriority = ticket.getPriority();
        ticket.setPriority(newPriority);
        System.out.println("Priorité du ticket " + ticket.getTicketID() + " changée de " + oldPriority + " à " + newPriority);
        return true;
    }

    // Valide si une priorité est dans la liste des priorités valides
    private boolean validatePriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return false;
        }
        return validPriorities.contains(priority.toUpperCase());
    }

    // Vérifie si l'utilisateur peut changer la priorité
    public boolean canUserChangePriority(User requester, Ticket ticket) {
        if (requester == null || ticket == null) {
            return false;
        }

        // Les admins peuvent toujours changer les priorités
        if (requester.isAdmin()) {
            return true;
        }

        // Les utilisateurs assignés peuvent changer la priorité de leurs tickets
        if (ticket.getAssignedUserId() == requester.getUserID()) {
            return true;
        }

        return false;
    }

    // Obtient toutes les priorités valides
    public List<String> getValidPriorities() {
        return new ArrayList<>(validPriorities);
    }

    // Obtient la description d'une priorité
    public String getPriorityDescription(String priority) {
        if (priority == null) {
            return "Priorité inconnue";
        }

        switch (priority.toUpperCase()) {
            case "BASSE":
                return "Priorité basse - Problème mineur sans urgence";
            case "MOYENNE":
                return "Priorité moyenne - Problème à traiter dans un délai raisonnable";
            case "HAUTE":
                return "Priorité haute - Problème important nécessitant une attention rapide";
            case "CRITIQUE":
                return "Priorité critique - Problème bloquant nécessitant une résolution immédiate";
            default:
                return "Priorité inconnue";
        }
    }
}

