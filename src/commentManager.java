import java.util.*;

public class commentManager {
    // Constructeur
    public commentManager() {
    }

    // Ajoute un commentaire à un ticket
    public boolean addComment(Ticket ticket, String comment, User author) {
        if (ticket == null || author == null || comment == null || comment.trim().isEmpty()) {
            System.out.println("Erreur: Le ticket ne peut pas être null, l'utilisateur ne peut pas être null et le commentaire ne peut pas être vide.");
            return false;
        }

        // Ajouter le commentaire
        ticket.addComment("[" + author.getName() + "] :" + comment);
        System.out.println("Commentaire ajouté au ticket " + ticket.getTicketID() + " par " + author.getName());
        return true;
    }

    // Obtient tous les commentaires d'un ticket
    public List<String> getComments(Ticket ticket) {
        return ticket.getComments();
    }

    // Nettoie tous les commentaires d'un ticket
    public void clearComments(Ticket ticket) {
        ticket.clearComments();
        System.out.println("Tous les commentaires du ticket " + ticket.getTicketID() + " ont été supprimés.");
    }
}
