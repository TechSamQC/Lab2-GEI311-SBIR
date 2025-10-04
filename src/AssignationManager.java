import java.util.*;

public class AssignationManager {
    private Map<Integer, List<Ticket>> userAssignments;
    private Map<Integer, Integer> userWorkload;

    // Constructeur
    public AssignationManager() {
        this.userAssignments = new HashMap<>();
        this.userWorkload = new HashMap<>();
    }

    // Assigne un ticket à un utilisateur
    public boolean assignTicket(Ticket ticket, User user, User assignedBy) {
        if (ticket == null || user == null || assignedBy == null) {
            System.out.println("Erreur: Le ticket, l'utilisateur ou l'assignateur est null.");
            return false;
        }

        // Vérification des permissions
        if (!canAssign(user, ticket, assignedBy)) {
            System.out.println("Erreur: Vous n'avez pas la permission d'assigner ce ticket.");
            return false;
        }

        // Vérifier si le ticket n'est pas déjà assigné
        if (ticket.getAssignedUserId() != 0) {
            System.out.println("Attention: Le ticket est déjà assigné. Réassignation en cours...");
            // Désassigner d'abord
            unassignTicketInternal(ticket);
        }

        // Assigner le ticket
        ticket.assignTo(user);

        // Mettre à jour les structures de données
        userAssignments.putIfAbsent(user.getUserID(), new ArrayList<>());
        userAssignments.get(user.getUserID()).add(ticket);
        
        userWorkload.put(user.getUserID(), getUserWorkload(user.getUserID()) + 1);

        System.out.println("Ticket " + ticket.getTicketID() + " assigné à " + user.getName() + " par " + assignedBy.getName());
        return true;
    }

    // Désassigne un ticket
    public boolean unassignTicket(Ticket ticket, User requestedBy) {
        if (ticket == null || requestedBy == null) {
            System.out.println("Erreur: Le ticket ou l'utilisateur est null.");
            return false;
        }

        // Vérifier si le ticket est assigné
        if (ticket.getAssignedUserId() == 0) {
            System.out.println("Erreur: Le ticket n'est pas assigné.");
            return false;
        }

        // Vérifier les permissions (admin ou utilisateur assigné)
        if (!isUserAdmin(requestedBy) && ticket.getAssignedUserId() != requestedBy.getUserID()) {
            System.out.println("Erreur: Vous n'avez pas la permission de désassigner ce ticket.");
            return false;
        }

        // Désassigner
        unassignTicketInternal(ticket);
        ticket.updateStatus("OUVERT");

        System.out.println("Ticket " + ticket.getTicketID() + " désassigné par " + requestedBy.getName());
        return true;
    }

    // Méthode interne pour désassigner sans vérifications
    private void unassignTicketInternal(Ticket ticket) {
        int userId = ticket.getAssignedUserId();
        
        if (userAssignments.containsKey(userId)) {
            userAssignments.get(userId).remove(ticket);
        }
        
        if (userWorkload.containsKey(userId)) {
            int currentWorkload = userWorkload.get(userId);
            userWorkload.put(userId, Math.max(0, currentWorkload - 1));
        }
    }

    // Vérifie si un utilisateur peut assigner un ticket
    public boolean canAssign(User user, Ticket ticket, User requestedBy) {
        if (user == null || ticket == null || requestedBy == null) {
            return false;
        }

        // Seuls les admins peuvent assigner des tickets
        if (!isUserAdmin(requestedBy)) {
            return false;
        }

        // Ne peut pas assigner un ticket terminé
        String status = ticket.getStatus();
        if (status.equals("TERMINÉ")) {
            return false;
        }

        return true;
    }

    // Vérifie si un utilisateur est admin
    public boolean isUserAdmin(User user) {
        return user != null && user.isAdmin();
    }

    // Obtient les tickets assignés à un utilisateur
    public List<Ticket> getUserAssignedTickets(int userID) {
        List<Ticket> tickets = userAssignments.get(userID);
        return tickets != null ? new ArrayList<>(tickets) : new ArrayList<>();
    }

    // Obtient la charge de travail d'un utilisateur (nombre de tickets)
    public int getUserWorkload(int userID) {
        return userWorkload.getOrDefault(userID, 0);
    }

    // Vérifie si un utilisateur est disponible
    public boolean isUserAvailable(int userID, int maxTickets) {
        return getUserWorkload(userID) < maxTickets;
    }

    // Réassigne un ticket à un nouvel utilisateur
    public boolean reassignTicket(Ticket ticket, User newUser, User requestedBy) {
        if (ticket == null || newUser == null || requestedBy == null) {
            System.out.println("Erreur: Le ticket, le nouvel utilisateur ou le demandeur est null.");
            return false;
        }

        // Vérifier les permissions
        if (!isUserAdmin(requestedBy)) {
            System.out.println("Erreur: Seul un admin peut réassigner un ticket.");
            return false;
        }

        // Vérifier si le ticket est assigné
        if (ticket.getAssignedUserId() == 0) {
            System.out.println("Erreur: Le ticket n'est pas assigné. Utilisez assignTicket() à la place.");
            return false;
        }

        // Désassigner et réassigner
        unassignTicketInternal(ticket);
        return assignTicket(ticket, newUser, requestedBy);
    }

    // Obtient des statistiques de charge par utilisateur
    public Map<Integer, Integer> getAssignmentStatistics() {
        return new HashMap<>(userWorkload);
    }

    // Affiche les statistiques d'assignation
    public void displayAssignmentStatistics() {
        System.out.println("\n=== Statistiques d'assignation ===");
        
        if (userWorkload.isEmpty()) {
            System.out.println("Aucune assignation enregistrée.");
            return;
        }

        for (Map.Entry<Integer, Integer> entry : userWorkload.entrySet()) {
            System.out.println("User ID " + entry.getKey() + ": " + entry.getValue() + " ticket(s)");
        }
    }

    // Trouve l'utilisateur avec la charge la plus faible
    public Integer findUserWithLowestWorkload(List<User> users) {
        if (users == null || users.isEmpty()) {
            return null;
        }

        int minWorkload = Integer.MAX_VALUE;
        Integer bestUserId = null;

        for (User user : users) {
            int workload = getUserWorkload(user.getUserID());
            if (workload < minWorkload) {
                minWorkload = workload;
                bestUserId = user.getUserID();
            }
        }

        return bestUserId;
    }

    // Nettoie les assignations d'un utilisateur (pour suppression d'utilisateur par exemple)
    public void clearUserAssignments(int userID) {
        userAssignments.remove(userID);
        userWorkload.remove(userID);
        System.out.println("Assignations de l'utilisateur " + userID + " nettoyées.");
    }
}

