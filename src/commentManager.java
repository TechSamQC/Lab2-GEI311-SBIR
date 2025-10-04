import java.util.*;

public class commentManager {
    private Map<Integer, List<String>> ticketComments;
    private Map<Integer, List<User>> commentAuthors;
    private Map<Integer, List<Date>> commentDates;

    // Constructeur
    public commentManager() {
        this.ticketComments = new HashMap<>();
        this.commentAuthors = new HashMap<>();
        this.commentDates = new HashMap<>();
    }

    // Ajoute un commentaire à un ticket
    public boolean addComment(int ticketID, String comment, User author) {
        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("Erreur: Le commentaire ne peut pas être vide.");
            return false;
        }

        if (author == null) {
            System.out.println("Erreur: L'auteur ne peut pas être null.");
            return false;
        }

        // Initialiser les listes si nécessaire
        ticketComments.putIfAbsent(ticketID, new ArrayList<>());
        commentAuthors.putIfAbsent(ticketID, new ArrayList<>());
        commentDates.putIfAbsent(ticketID, new ArrayList<>());

        // Ajouter le commentaire
        ticketComments.get(ticketID).add(comment);
        commentAuthors.get(ticketID).add(author);
        commentDates.get(ticketID).add(new Date());

        System.out.println("Commentaire ajouté au ticket " + ticketID + " par " + author.getName());
        return true;
    }

    // Obtient tous les commentaires d'un ticket
    public List<String> getComments(int ticketID) {
        List<String> comments = ticketComments.get(ticketID);
        return comments != null ? new ArrayList<>(comments) : new ArrayList<>();
    }

    // Obtient les commentaires avec leurs auteurs
    public List<String> getCommentsWithAuthors(int ticketID) {
        List<String> result = new ArrayList<>();
        
        List<String> comments = ticketComments.get(ticketID);
        List<User> authors = commentAuthors.get(ticketID);

        if (comments == null || authors == null) {
            return result;
        }

        for (int i = 0; i < comments.size(); i++) {
            String comment = comments.get(i);
            User author = authors.get(i);
            result.add(author.getName() + ": " + comment);
        }

        return result;
    }

    // Obtient le nombre de commentaires d'un ticket
    public int getCommentCount(int ticketID) {
        List<String> comments = ticketComments.get(ticketID);
        return comments != null ? comments.size() : 0;
    }

    // Obtient le dernier commentaire d'un ticket
    public String getLastComment(int ticketID) {
        List<String> comments = ticketComments.get(ticketID);
        if (comments == null || comments.isEmpty()) {
            return null;
        }
        return comments.get(comments.size() - 1);
    }

    // Supprime un commentaire par index
    public boolean deleteComment(int ticketID, int index, User user) {
        if (user == null) {
            System.out.println("Erreur: L'utilisateur ne peut pas être null.");
            return false;
        }

        List<String> comments = ticketComments.get(ticketID);
        List<User> authors = commentAuthors.get(ticketID);
        List<Date> dates = commentDates.get(ticketID);

        if (comments == null || index < 0 || index >= comments.size()) {
            System.out.println("Erreur: Index de commentaire invalide.");
            return false;
        }

        // Vérifier les permissions (l'auteur ou un admin peut supprimer)
        User commentAuthor = authors.get(index);
        if (!user.isAdmin() && user.getUserID() != commentAuthor.getUserID()) {
            System.out.println("Erreur: Vous ne pouvez supprimer que vos propres commentaires.");
            return false;
        }

        // Supprimer le commentaire
        comments.remove(index);
        authors.remove(index);
        dates.remove(index);

        System.out.println("Commentaire supprimé du ticket " + ticketID);
        return true;
    }

    // Vérifie si un ticket a des commentaires
    public boolean hasComments(int ticketID) {
        List<String> comments = ticketComments.get(ticketID);
        return comments != null && !comments.isEmpty();
    }

    // Formate l'historique complet des commentaires
    public String formatCommentHistory(int ticketID) {
        StringBuilder history = new StringBuilder();
        history.append("=== Historique des commentaires du ticket ").append(ticketID).append(" ===\n");

        List<String> comments = ticketComments.get(ticketID);
        List<User> authors = commentAuthors.get(ticketID);
        List<Date> dates = commentDates.get(ticketID);

        if (comments == null || comments.isEmpty()) {
            history.append("Aucun commentaire.\n");
            return history.toString();
        }

        for (int i = 0; i < comments.size(); i++) {
            history.append("[").append(i + 1).append("] ");
            history.append(dates.get(i)).append(" - ");
            history.append(authors.get(i).getName()).append(": ");
            history.append(comments.get(i)).append("\n");
        }

        return history.toString();
    }

    // Obtient les auteurs des commentaires d'un ticket
    public List<User> getCommentAuthors(int ticketID) {
        List<User> authors = commentAuthors.get(ticketID);
        return authors != null ? new ArrayList<>(authors) : new ArrayList<>();
    }

    // Obtient les dates des commentaires d'un ticket
    public List<Date> getCommentDates(int ticketID) {
        List<Date> dates = commentDates.get(ticketID);
        return dates != null ? new ArrayList<>(dates) : new ArrayList<>();
    }

    // Nettoie tous les commentaires d'un ticket
    public void clearComments(int ticketID) {
        ticketComments.remove(ticketID);
        commentAuthors.remove(ticketID);
        commentDates.remove(ticketID);
        System.out.println("Tous les commentaires du ticket " + ticketID + " ont été supprimés.");
    }
}
