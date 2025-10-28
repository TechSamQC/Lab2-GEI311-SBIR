import java.util.*;

public class statusManager {
    
    private List<String> validStatuses;
    private Map<String, List<String>> statusTransitions;

    // Constructeur
    public statusManager() {
        this.validStatuses = Arrays.asList("OUVERT", "ASSIGNÉ", "VALIDATION", "TERMINÉ");
        this.statusTransitions = new HashMap<>();
        initializeTransitions();
    }

    // Initialise les règles de transition entre statuts
    public void initializeTransitions() {
        // Depuis OUVERT, on peut aller vers ASSIGNÉ, VALIDATION (inutile) ou TERMINÉ (fermeture directe)
        statusTransitions.put("OUVERT", Arrays.asList("ASSIGNÉ", "VALIDATION", "TERMINÉ"));
        
        // Depuis ASSIGNÉ, on peut aller vers OUVERT ou VALIDATION (DOIT passer par VALIDATION avant TERMINÉ)
        statusTransitions.put("ASSIGNÉ", Arrays.asList("OUVERT", "VALIDATION"));
        
        // Depuis VALIDATION, on peut aller vers OUVERT, ASSIGNÉ ou TERMINÉ
        statusTransitions.put("VALIDATION", Arrays.asList("OUVERT", "ASSIGNÉ", "TERMINÉ"));
        
        // Depuis TERMINÉ, aucune transition possible (état final)
        statusTransitions.put("TERMINÉ", new ArrayList<>());
    }

    // Met à jour le statut d'un ticket
    public boolean updateStatus(Ticket ticket, String newStatus, User requester) {
        if (ticket == null || requester == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        // Validation du nouveau statut
        if (!validateStatus(newStatus)) {
            System.out.println("Erreur: Statut invalide: " + newStatus);
            return false;
        }

        // Vérification de la transition
        if (!validateTransition(ticket.getStatus(), newStatus)) {
            System.out.println("Erreur: Transition de statut invalide de " + ticket.getStatus() + " vers " + newStatus);
            System.out.println("Transitions valides depuis " + ticket.getStatus() + ": " + getValidTransitions(ticket.getStatus()));
            return false;
        }

        // Vérification des permissions
        if (!canUserChangeStatus(requester, ticket, newStatus)) {
            System.out.println("Erreur: Vous n'avez pas la permission de changer ce statut.");
            return false;
        }

        // Mise à jour du statut
        String oldStatus = ticket.getStatus();
        ticket.updateStatus(newStatus);
        System.out.println("Statut du ticket " + ticket.getTicketID() + " changé de " + oldStatus + " à " + newStatus);
        return true;
    }

    // Valide si un statut est dans la liste des statuts valides
    private boolean validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return validStatuses.contains(status.toUpperCase());
    }

    // Valide si une transition est autorisée
    private boolean validateTransition(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }

        // Si le statut est déjà celui demandé, pas de transition nécessaire
        if (currentStatus.equalsIgnoreCase(newStatus)) {
            return false;
        }

        // Obtenir les transitions possibles depuis le statut actuel
        List<String> allowedTransitions = statusTransitions.get(currentStatus.toUpperCase());
        if (allowedTransitions == null) {
            return false;
        }

        return allowedTransitions.contains(newStatus.toUpperCase());
    }

    // Vérifie si l'utilisateur peut changer le statut
    private boolean canUserChangeStatus(User requester, Ticket ticket, String newStatus) {
        if (requester == null || ticket == null) {
            return false;
        }

        // Les admins peuvent toujours changer les statuts
        if (requester.isAdmin()) {
            return true;
        }

        // Les développeurs peuvent changer les statuts SAUF passer de VALIDATION à TERMINÉ
        if (requester.canAssignTickets()) {
            // Bloquer la transition de VALIDATION vers TERMINÉ (seul admin peut le faire)
            if (ticket.getStatus().equalsIgnoreCase("VALIDATION") && 
                newStatus.equalsIgnoreCase("TERMINÉ")) {
                System.out.println("Erreur: Seul un administrateur peut passer un ticket de VALIDATION à TERMINÉ.");
                return false;
            }
            return true;
        }

        // Les utilisateurs réguliers ne peuvent PAS modifier le statut du tout
        System.out.println("Erreur: Les utilisateurs sans privilèges ne peuvent pas modifier le statut des tickets.");
        return false;
    }

    // Obtient les transitions valides depuis un statut donné
    private List<String> getValidTransitions(String currentStatus) {
        if (currentStatus == null) {
            return new ArrayList<>();
        }
        
        List<String> transitions = statusTransitions.get(currentStatus.toUpperCase());
        return transitions != null ? new ArrayList<>(transitions) : new ArrayList<>();
    }

    // Obtient tous les statuts valides
    public List<String> getValidStatuses() {
        return new ArrayList<>(validStatuses);
    }

    // Obtient une description d'un statut
    public String getStatusDescription(String status) {
        if (status == null) {
            return "Statut inconnu";
        }

        switch (status.toUpperCase()) {
            case "OUVERT":
                return "Le ticket est ouvert et en attente d'assignation";
            case "ASSIGNÉ":
                return "Le ticket est assigné à un développeur";
            case "VALIDATION":
                return "Le ticket est en cours de validation par l'équipe";
            case "TERMINÉ":
                return "Le ticket est terminé après validation de l'équipe";
            default:
                return "Statut inconnu";
        }
    }
}
