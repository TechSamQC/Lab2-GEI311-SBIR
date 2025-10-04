import java.util.*;

public class PriorityManager {
    private List<String> validPriorities;
    private Map<String, Integer> priorityLevels;

    // Constructeur
    public PriorityManager() {
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
        this.priorityLevels = new HashMap<>();
        initializePriorityLevels();
    }

    // Initialise les niveaux numériques des priorités
    private void initializePriorityLevels() {
        priorityLevels.put("BASSE", 1);
        priorityLevels.put("MOYENNE", 2);
        priorityLevels.put("HAUTE", 3);
        priorityLevels.put("CRITIQUE", 4);
    }

    // Met à jour la priorité d'un ticket
    public boolean updatePriority(Ticket ticket, String newPriority, User user) {
        if (ticket == null || user == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        // Validation de la priorité
        if (!validatePriority(newPriority)) {
            System.out.println("Erreur: Priorité invalide: " + newPriority);
            return false;
        }

        // Vérification des permissions
        if (!canUserChangePriority(user, ticket)) {
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
    public boolean validatePriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return false;
        }
        return validPriorities.contains(priority.toUpperCase());
    }

    // Vérifie si l'utilisateur peut changer la priorité
    public boolean canUserChangePriority(User user, Ticket ticket) {
        if (user == null || ticket == null) {
            return false;
        }

        // Les admins peuvent toujours changer les priorités
        if (user.isAdmin()) {
            return true;
        }

        // Les utilisateurs assignés peuvent changer la priorité de leurs tickets
        if (ticket.getAssignedUserId() == user.getUserID()) {
            return true;
        }

        return false;
    }

    // Obtient toutes les priorités valides
    public List<String> getValidPriorities() {
        return new ArrayList<>(validPriorities);
    }

    // Compare deux priorités (retourne négatif si p1 < p2, 0 si égales, positif si p1 > p2)
    public int comparePriorities(String p1, String p2) {
        if (p1 == null || p2 == null) {
            return 0;
        }

        Integer level1 = priorityLevels.get(p1.toUpperCase());
        Integer level2 = priorityLevels.get(p2.toUpperCase());

        if (level1 == null || level2 == null) {
            return 0;
        }

        return level1.compareTo(level2);
    }

    // Obtient le niveau numérique d'une priorité
    public int getPriorityLevel(String priority) {
        if (priority == null) {
            return 0;
        }

        Integer level = priorityLevels.get(priority.toUpperCase());
        return level != null ? level : 0;
    }

    // Obtient les tickets avec la priorité la plus haute
    public List<Ticket> getHighestPriorityTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return new ArrayList<>();
        }

        // Trouver la priorité maximale
        int maxPriority = 0;
        for (Ticket ticket : tickets) {
            int level = getPriorityLevel(ticket.getPriority());
            if (level > maxPriority) {
                maxPriority = level;
            }
        }

        // Collecter tous les tickets avec cette priorité
        List<Ticket> highestPriorityTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (getPriorityLevel(ticket.getPriority()) == maxPriority) {
                highestPriorityTickets.add(ticket);
            }
        }

        return highestPriorityTickets;
    }

    // Trie une liste de tickets par priorité (décroissant)
    public void sortTicketsByPriority(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return;
        }

        tickets.sort((t1, t2) -> {
            int level1 = getPriorityLevel(t1.getPriority());
            int level2 = getPriorityLevel(t2.getPriority());
            return Integer.compare(level2, level1); // Ordre décroissant
        });
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

