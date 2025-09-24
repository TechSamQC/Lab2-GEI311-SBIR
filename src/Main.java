import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();
        int UserID = 1;
        int adminID = 1;

        // Test des méthodes de la classe Ticket
        tickets.add(new Ticket((tickets.size() + 1), "Problème de connexion", "Impossible de se connecter au système", "HAUTE"));
        tickets.add(new Ticket((tickets.size() + 1), "Erreur 404", "Page non trouvée lors de l'accès au tableau de bord", "MOYENNE"));
        User user1 = new User(UserID, "Alice", "alice@example.com", "USER");
        UserID++;
        tickets.get(0).updateStatus("TEST"); //Statut invalide
        tickets.get(0).updateStatus("ASSIGNÉ"); //Un ticket doit être assigné pour avoir ce statut
        tickets.get(0).updateStatus("OUVERT"); //Le ticket est déjà ouvert
        tickets.get(0).updateStatus("VALIDATION"); //Un ticket ne peut être mis en validation que s'il est assigné d'abord
        tickets.get(0).assignTo(user1); //Le ticket est assigné à user1
        tickets.get(0).updateStatus("OUVERT"); //Le ticket est remis à ouvert, il est désassigné automatiquement
        tickets.get(0).addComment("L'utilisateur a été contacté pour plus de détails."); //Le ticket n'est pas assigné, on ne peut pas ajouter de commentaire
        tickets.get(0).assignTo(user1); //Le ticket est assigné à user1
        tickets.get(0).addComment("L'utilisateur a été contacté pour plus de détails."); //Le commentaire est ajouté
        tickets.get(0).addComment("Le problème semble être lié au serveur."); //Un autre commentaire est ajouté
        tickets.get(0).updateStatus("VALIDATION"); //Le ticket est mis en validation
        tickets.get(0).addComment("En attente de la validation finale."); //Un autre commentaire est ajouté
        System.out.println(tickets.get(0));

        // Test des méthodes de la classe Admin
        Admin admin1 = new Admin(adminID, "Bob", "bob@example.com");
        adminID++;
        admin1.assignTicket(tickets.get(1), user1);
        admin1.closeTicket(tickets.get(1)); //Le ticket doit être en validation pour être terminé
        admin1.closeTicket(tickets.get(0)); //Le ticket est en validation, il peut être terminé
        admin1.assignTicket(tickets.get(0), user1); //Le ticket est terminé, il ne peut plus être assigné
        admin1.viewAllTickets(tickets);

        // Test des méthodes de User
        tickets.add(new Ticket((tickets.size() + 1), "Problème d'impression", "L'imprimante ne répond pas", "BASSE"));
        user1.createTicket(tickets.get(2));  // User1 cree le ticket
        tickets.add(tickets.get(2));      // Ajoute le ticket à la liste
        user1.viewTicket(tickets.get(2));
        System.out.println("Alice(user1) met à jour le ticket : " + tickets.get(2).getTicketID());
        user1.updateTicket(tickets.get(2));
    }
}