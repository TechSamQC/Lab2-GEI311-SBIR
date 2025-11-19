package org.openapitools.domain;

import java.util.List;

public class CommentManager {
    
    public CommentManager() {}

    public boolean addComment(Ticket ticket, String comment, User author) {
        if (ticket == null || author == null || comment == null || comment.trim().isEmpty()) {
            System.out.println("Erreur: Le ticket ne peut pas être null, l'utilisateur ne peut pas être null et le commentaire ne peut pas être vide.");
            return false;
        }

        ticket.addComment("[" + author.getName() + "] : " + comment);
        System.out.println("Commentaire ajouté au ticket " + ticket.getTicketID() + " par " + author.getName());
        return true;
    }

    public List<String> getComments(Ticket ticket) {
        return ticket.getComments();
    }

    public void clearComments(Ticket ticket) {
        ticket.clearComments();
        System.out.println("Tous les commentaires du ticket " + ticket.getTicketID() + " ont été supprimés.");
    }
}

