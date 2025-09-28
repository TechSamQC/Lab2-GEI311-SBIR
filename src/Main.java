import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();
        int UserID = 1;
        int adminID = 1;

        // Création des utilisateurs et admin
        User user1 = new User(UserID, "Alice", "alice@example.com", "USER");
        UserID++;

        Admin admin1 = new Admin(adminID, "Bob", "bob@example.com");
        adminID++;

        // Création du premier ticket
        System.out.println("--- Création du ticket haute priorité ---");
        tickets.add(new Ticket((tickets.size() + 1), "Problème de connexion", "Impossible de se connecter au système", "HAUTE"));
        System.out.println("Ticket créé: " + tickets.get(0).getTitle() + " (ID: " + tickets.get(0).getTicketID() + ")");
        
        // Tests de validation des statuts invalides
        System.out.println("\n--- Tests de validation des statuts ---");
        tickets.get(0).updateStatus("TEST"); // Statut invalide
        tickets.get(0).updateStatus("ASSIGNÉ"); // Un ticket doit être assigné pour avoir ce statut
        tickets.get(0).updateStatus("OUVERT"); // Le ticket est déjà ouvert
        tickets.get(0).updateStatus("VALIDATION"); // Un ticket doit être assigné pour avoir ce statut
        
        // Assignation
        System.out.println("\n--- Assignation du ticket par l'admin ---");
        admin1.assignTicket(tickets.get(0), user1);
        
        // désassignation de ticket
        System.out.println("\n--- Test de remise à ouvert ---");
        tickets.get(0).updateStatus("OUVERT"); // Le ticket est remis à ouvert, il est désassigné automatiquement
        
        // ajout de commentaire d'un ticket non assigné
        System.out.println("\n--- Test d'ajout de commentaire ---");
        tickets.get(0).addComment("L'utilisateur a été contacté pour plus de détails."); // Le ticket n'est pas assigné, on ne peut pas ajouter de commentaire
        
        // Réassignation
        System.out.println("\n--- Réassignation ---");
        admin1.assignTicket(tickets.get(0), user1); // Le ticket est assigné à user1
        tickets.get(0).addComment("L'utilisateur a été contacté pour plus de détails."); // Le commentaire est ajouté
        tickets.get(0).addComment("Le problème semble être lié au serveur."); // Un autre commentaire est ajouté
        tickets.get(0).updateStatus("VALIDATION"); // Le ticket est mis en validation
        tickets.get(0).addComment("En attente de la validation finale."); // Un autre commentaire est ajouté
        
        System.out.println("\n--- État du ticket ---");
        System.out.println(tickets.get(0));

   
        // Création du deuxième ticket
        System.out.println("--- Création du ticket moyenne priorité ---");
        tickets.add(new Ticket((tickets.size() + 1), "Erreur 404", "Page non trouvée lors de l'accès au tableau de bord", "MOYENNE"));
        System.out.println("Ticket créé: " + tickets.get(1).getTitle() + " (ID: " + tickets.get(1).getTicketID() + ")");
        
        // Assignation directe par l'admin
        System.out.println("\n--- Assignation par l'admin ---");
        admin1.assignTicket(tickets.get(1), user1);
        
        // Tentative de fermeture sans validation
        System.out.println("\n--- Tentative de fermeture sans validation ---");
        admin1.closeTicket(tickets.get(1)); // Le ticket doit être en validation pour être terminé
        
        // Fermeture du premier ticket (en validation)
        System.out.println("--- Fermeture du ticket haute priorité ---");
        admin1.closeTicket(tickets.get(0)); // Le ticket est en validation, il peut être terminé
        
        // Tentative d'assignation sur ticket terminé
        System.out.println("\n--- Tentative d'assignation sur ticket terminé ---");
        admin1.assignTicket(tickets.get(0), user1); // Le ticket est terminé, il ne peut plus être assigné
        
        // Vue d'ensemble finale
        System.out.println("\n--- Vue d'ensemble de tous les tickets ---");
        admin1.viewAllTickets(tickets);

        // Test de workflow complet
        System.out.println("\n=== TEST DU CYCLE DE VIE D'UN TICKET ===\n");
        
        // Création d'un deuxième utilisateur pour les tests
        User user2 = new User(2, "Charlie", "charlie@example.com", "USER");
        
        // Étape 1: Création d'un nouveau ticket par user1
        System.out.println("--- Étape 1: Création du ticket par Alice (user1) ---");
        Ticket workflowTicket = new Ticket((tickets.size() + 1), "Problème d'impression", "L'imprimante ne répond pas", "BASSE");
        user1.createTicket(workflowTicket);
        tickets.add(workflowTicket);
        user1.viewTicket(workflowTicket);
        
        // Étape 2: Tentative de modification par user1 (ticket ouvert - doit échouer)
        System.out.println("\n--- Étape 2: Tentative de modification par Alice (ticket ouvert) ---");
        user1.updateTicket(workflowTicket);
        
        // Étape 3: Assignation du ticket à user1 par l'admin
        System.out.println("\n--- Étape 3: Assignation du ticket à Alice par l'admin ---");
        admin1.assignTicket(workflowTicket, user1);
        
        // Étape 4: Modification réussie par user1 (ASSIGNÉ vers VALIDATION)
        System.out.println("\n--- Étape 4: Modification par Alice (ASSIGNÉ vers VALIDATION) ---");
        user1.updateTicket(workflowTicket);
        
        // Étape 5: Tentative de modification par user2 (pas assigné - doit échouer)
        System.out.println("\n--- Étape 5: Tentative de modification par Charlie (pas assigné) ---");
        user2.updateTicket(workflowTicket);
        
        // Étape 6: Tentative de nouvelle modification par user1 (en validation - doit échouer)
        System.out.println("\n--- Étape 6: Tentative de nouvelle modification par Alice (en validation) ---");
        user1.updateTicket(workflowTicket);
        
        // Étape 7: Fermeture du ticket par l'admin
        System.out.println("\n--- Étape 7: Fermeture du ticket par l'admin ---");
        admin1.closeTicket(workflowTicket);
        
        // Étape 8: Tentative de modification après fermeture (doit échouer)
        System.out.println("\n--- Étape 8: Tentative de modification après fermeture ---");
        user1.updateTicket(workflowTicket);
        
        // Étape 9: État final du ticket
        System.out.println("\n--- Étape 9: État final du ticket ---");
        user1.viewTicket(workflowTicket);
        
        System.out.println("\n=== FIN DU TEST ===");
    }
}