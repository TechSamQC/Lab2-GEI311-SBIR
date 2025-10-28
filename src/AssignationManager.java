public class AssignationManager {

    // Constructeur
    public AssignationManager()
    {}

    // Assigner un ticket à un utilisateur
    public boolean assignTicket(Ticket ticket, User user, User assignedBy) {
        if (ticket == null || user == null || assignedBy == null) {
            System.out.println("Erreur: Le ticket, l'utilisateur ou l'assignateur est null.");
            return false;
        }

        // Vérification des permissions
        if (!canAssign(user, ticket, assignedBy)) {
            return false;
        }

        // Vérifier si le ticket n'est pas déjà assigné
        if (ticket.isAssigned()) {
            System.out.println("Attention: Le ticket est déjà assigné. Réassignation en cours...");
            // Désassigner d'abord
            unassignTicket(ticket, assignedBy);
        }

        // Assigner le ticket
        ticket.assignTo(user);

        System.out.println("Ticket " + ticket.getTicketID() + " assigné à " + user.getName() + " par " + assignedBy.getName());
        return true;
    }

    // Désassigne un ticket
    public boolean unassignTicket(Ticket ticket, User requestedBy) {
        if (ticket == null || requestedBy == null) {
            System.out.println("Erreur: Le ticket ou le demandeur est null.");
            return false;
        }

        // Vérifier si le ticket est assigné
        if (!ticket.isAssigned()) {
            System.out.println("Erreur: Le ticket n'est pas assigné.");
            return false;
        }

        // Vérifier les permissions (admin ou utilisateur assigné)
        if (!requestedBy.canAssignTickets()) {
            System.out.println("Erreur: Vous n'avez pas la permission de désassigner ce ticket.");
            return false;
        }
        // Désassigner le ticket
        ticket.desassignTicketInternal(ticket);

        System.out.println("Ticket " + ticket.getTicketID() + " désassigné par " + requestedBy.getName());
        return true;
    }

    // Vérifie si un utilisateur peut assigner un ticket
    private boolean canAssign(User user, Ticket ticket, User requestedBy) {
        // Seuls les devs et admins peuvent assigner des tickets
        if (!requestedBy.canAssignTickets()) {
            System.out.println("Erreur: Vous n'avez pas la permission d'assigner ce ticket.");
            return false;
        }

        return true;
    }
}

