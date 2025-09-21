import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();
        int UserID = 1;
        int adminID = 1;

        // Test de la classe Ticket
        tickets.add(new Ticket((tickets.size() + 1), "Problème de connexion", "Impossible de se connecter au système", "HAUTE"));
        tickets.add(new Ticket((tickets.size() + 1), "Erreur 404", "Page non trouvée lors de l'accès au tableau de bord", "MOYENNE"));
        User user1 = new User(UserID, "Alice", "alice@example.com", "USER");
        UserID++;
        tickets.get(0).assignTo(user1);
        tickets.get(0).updateStatus("EN COURS");
        tickets.get(0).addComment("L'utilisateur a été contacté pour plus de détails.");
        tickets.get(0).addComment("Le problème semble être lié au serveur.");
        tickets.get(0).updateStatus("RÉSOLU");
        System.out.println(tickets.get(0));

        // Test de la classe Admin
        Admin admin1 = new Admin(adminID, "Bob", "bob@example.com");
        adminID++;
        admin1.assignTicket(tickets.get(1), user1);
        admin1.closeTicket(tickets.get(1));
        admin1.viewAllTickets(tickets);
    }
}