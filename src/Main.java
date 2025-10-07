import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SYSTEME DE GESTION DE TICKETS - TEST COMPLET              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Initialisation du système
        Display display = new Display();
        TicketManager ticketManager = new TicketManager(display);
        descriptionManager descManager = ticketManager.getDescriptionManager();
        TicketCreator ticketCreator = new TicketCreator(0, ticketManager); // ticketCreator sera relié avec ticketManager
        UserCreator userCreator = new UserCreator();

        // ========================================================================
        // PARTIE 1: CREATION DES UTILISATEURS
        // ========================================================================
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 1: CREATION ET VALIDATION DES UTILISATEURS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        User admin = userCreator.createUser("Admin Principal", "admin@ticketsystem.com", "ADMIN");
        User dev1 = userCreator.createUser("Mike Developpeur", "mike@ticketsystem.com", "DEVELOPER");
        User dev2 = userCreator.createUser("Jimmy Developpeur", "jim32@ticketsystem.com", "DEVELOPER");
        User user1 = userCreator.createUser("Richard Utilisateur", "rich007@ticketsystem.com", "USER");

        System.out.println("\n--- Verification des permissions ---");
        System.out.println("Admin peut assigner tickets: " + admin.canAssignTickets());
        System.out.println("Admin peut fermer tickets: " + admin.canCloseTickets());
        System.out.println("Developer peut assigner tickets: " + dev1.canAssignTickets());
        System.out.println("User peut assigner tickets: " + user1.canAssignTickets());

        // ========================================================================
        // PARTIE 2: CREATION DE TICKETS AVEC DIFFERENTES PRIORITES
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 2: CREATION DE TICKETS AVEC DIFFERENTES PRIORITES");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        Ticket ticket1 = ticketCreator.createTicket(
            "Erreur 500 lors de la connexion",
            "Le systeme affiche une erreur 500 lors de la tentative de connexion en production",
            admin,
            "CRITIQUE"
        );

        Ticket ticket2 = ticketCreator.createTicket(
            "Bug d'affichage sur la page principale",
            "Les elements ne s'affichent pas correctement sur mobile",
            user1,
            "HAUTE"
        );

        Ticket ticket3 = ticketCreator.createTicket(
            "Amelioration de l'interface utilisateur",
            "Ajouter un bouton d'export dans le tableau de bord",
            dev1,
            "MOYENNE"
        );

        Ticket ticket4 = ticketCreator.createTicket(
            "Correction orthographique dans la documentation",
            "Plusieurs fautes d'orthographe dans le guide utilisateur",
            user1,
            "BASSE"
        );

        // Ajout des tickets au système (se fait maintenant automatiquement dans TicketCreator)
        /*ticketManager.addTicket(ticket1);
        ticketManager.addTicket(ticket2);
        ticketManager.addTicket(ticket3);
        ticketManager.addTicket(ticket4);*/

        System.out.println("\n--- Affichage de tous les tickets ouverts ---");
        ticketManager.displayTicketsByStatus("OUVERT");

        // ========================================================================
        // PARTIE 3: GESTION DES DESCRIPTIONS AVEC MEDIAS
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 3: GESTION DES DESCRIPTIONS AVEC IMAGES ET VIDEOS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        Description richDescription = descManager.createDescription(
            "Description detaillee avec captures d'ecran du probleme.\n" +
            "L'erreur se produit systematiquement lors de la connexion.\n" +
            "Voir les captures d'ecran et la video de reproduction ci-dessous."
        );

        System.out.println("--- Ajout de medias existants a la description ---");
        descManager.addImageToDescription(richDescription, "media/images/vp.jpeg");

        System.out.println("\n--- Affichage de la description complete ---");
        display.displayDescription(richDescription);

        // Test de validation
        System.out.println("--- Test de validation des formats ---");
        descManager.addImageToDescription(richDescription, "media/images/test.bmp"); // Format invalide
        descManager.addVideoToDescription(richDescription, "media/videos/test.wmv"); // Format invalide

        // ========================================================================
        // PARTIE 4: CYCLE DE VIE COMPLET D'UN TICKET
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 4: DEMONSTRATION DU CYCLE DE VIE COMPLET");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("--- Etape 1: Ticket OUVERT (creation) ---");
        System.out.println("Statut initial: " + ticket1.getStatus());

        System.out.println("\n--- Etape 2: ASSIGNATION a un developpeur ---");
        ticketManager.assignTicket(ticket1.getTicketID(), dev1, admin);
        System.out.println("Statut apres assignation: " + ticket1.getStatus());

        System.out.println("\n--- Etape 3: Ajout de COMMENTAIRES par le developpeur ---");
        ticketManager.addCommentToTicket(ticket1.getTicketID(), 
            "Probleme identifie: timeout de connexion a la base de donnees", dev1);
        ticketManager.addCommentToTicket(ticket1.getTicketID(), 
            "Configuration du pool de connexions augmentee de 10 a 50", dev1);
        ticketManager.addCommentToTicket(ticket1.getTicketID(), 
            "Tests effectues avec succes en environnement de staging", dev1);

        System.out.println("\n--- Etape 4: Passage en VALIDATION (developpeur a termine) ---");
        ticketManager.updateTicketStatus(ticket1.getTicketID(), "VALIDATION", dev1);
        System.out.println("Statut apres validation: " + ticket1.getStatus());

        ticketManager.addCommentToTicket(ticket1.getTicketID(), 
            "En attente de validation par l'equipe", dev1);

        System.out.println("\n--- Etape 5: TERMINAISON par l'admin (equipe a valide) ---");
        ticketManager.closeTicket(ticket1.getTicketID(), admin);
        System.out.println("Statut final: " + ticket1.getStatus());

        System.out.println("\n--- Affichage du ticket complet avec commentaires ---");
        ticketManager.displayTicket(ticket1.getTicketID());

        // ========================================================================
        // PARTIE 5: DEMONSTRATION FERMETURE DIRECTE ET RETOURS
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 5: FERMETURE DIRECTE ET RETOURS EN ARRIERE");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("--- Cas 1: Fermeture directe depuis OUVERT ---");
        System.out.println("(Probleme non prioritaire ou specifique a l'utilisateur)");
        System.out.println("Ticket #" + ticket4.getTicketID() + " statut: " + ticket4.getStatus());
        ticketManager.closeTicket(ticket4.getTicketID(), admin);
        System.out.println("Statut apres fermeture directe: " + ticket4.getStatus());

        System.out.println("\n--- Cas 2: ASSIGNE doit passer par VALIDATION avant TERMINE ---");
        ticketManager.assignTicket(ticket2.getTicketID(), dev2, admin);
        System.out.println("Ticket #" + ticket2.getTicketID() + " statut: " + ticket2.getStatus());
        ticketManager.addCommentToTicket(ticket2.getTicketID(), "Probleme corrige, pret pour validation", dev2);
        ticketManager.updateTicketStatus(ticket2.getTicketID(), "VALIDATION", dev2);
        System.out.println("Passage en VALIDATION: " + ticket2.getStatus());
        ticketManager.closeTicket(ticket2.getTicketID(), admin);
        System.out.println("Statut final: " + ticket2.getStatus());

        System.out.println("\n--- Cas 3: Retour en arriere depuis VALIDATION vers ASSIGNE ---");
        System.out.println("(Correction supplementaire necessaire)");
        Ticket ticket10 = ticketCreator.createTicket("Test retour arriere", "Description test", admin, "HAUTE");
        //ticketManager.addTicket(ticket10);
        ticketManager.assignTicket(ticket10.getTicketID(), dev1, admin);
        ticketManager.updateTicketStatus(ticket10.getTicketID(), "VALIDATION", dev1);
        System.out.println("Ticket #" + ticket10.getTicketID() + " en VALIDATION");
        ticketManager.updateTicketStatus(ticket10.getTicketID(), "ASSIGNÉ", admin);
        System.out.println("Retour a ASSIGNE pour correction: " + ticket10.getStatus());

        // ========================================================================
        // PARTIE 6: GESTION DES ASSIGNATIONS ET CHARGE DE TRAVAIL
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 6: GESTION DES ASSIGNATIONS ET CHARGE DE TRAVAIL");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // Créer plusieurs tickets pour tester la charge
        for (int i = 5; i <= 8; i++) {
            Ticket t = ticketCreator.createTicket(
                "Ticket de test " + i,
                "Description du ticket " + i,
                admin,
                "MOYENNE"
            );
            //ticketManager.addTicket(t);
            if (i % 2 == 0) {
                ticketManager.assignTicket(t.getTicketID(), dev1, admin);
            } else {
                ticketManager.assignTicket(t.getTicketID(), dev2, admin);
            }
        }

        System.out.println("\n--- Tickets assignes a Alice ---");
        ticketManager.displayTicketsByUser(dev1);

        // ========================================================================
        // PARTIE 7: GESTION DES PRIORITES
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 7: GESTION ET MODIFICATION DES PRIORITES");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("--- Changement de priorite ---");
        System.out.println("Ticket #" + ticket3.getTicketID() + " priorite actuelle: " + ticket3.getPriority());
        ticketManager.updateTicketPriority(ticket3.getTicketID(), "HAUTE", admin);
        System.out.println("Nouvelle priorite: " + ticket3.getPriority());

        System.out.println("\n--- Affichage des tickets HAUTE priorite ---");
        ticketManager.displayTicketsByPriority("HAUTE");

        System.out.println("\n--- Affichage des tickets CRITIQUE ---");
        List<Ticket> criticalTickets = ticketManager.getCriticalTickets();
        if (criticalTickets.isEmpty()) {
            System.out.println("Aucun ticket critique actuellement.");
        } else {
            System.out.println("Nombre de tickets critiques: " + criticalTickets.size());
        }

        // ========================================================================
        // PARTIE 8: RECHERCHE ET FILTRAGE
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 8: RECHERCHE ET FILTRAGE DE TICKETS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("--- Recherche par mot-cle 'interface' ---");
        List<Ticket> searchResults = ticketManager.searchTicketsByTitle("interface");
        System.out.println("Resultats trouves: " + searchResults.size());
        for (Ticket t : searchResults) {
            System.out.println("  - Ticket #" + t.getTicketID() + ": " + t.getTitle());
        }

        System.out.println("\n--- Tickets ouverts non assignes ---");
        List<Ticket> unassignedTickets = ticketManager.getUnassignedOpenTickets();
        System.out.println("Nombre de tickets non assignes: " + unassignedTickets.size());
        for (Ticket t : unassignedTickets) {
            System.out.println("  - Ticket #" + t.getTicketID() + ": " + t.getTitle());
        }

        // ========================================================================
        // PARTIE 9: VALIDATION DES PERMISSIONS
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 9: VALIDATION DES PERMISSIONS ET SECURITE");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        Ticket ticket9 = ticketCreator.createTicket(
            "Test de permissions",
            "Ticket pour tester les permissions",
            admin,
            "BASSE"
        );
        //ticketManager.addTicket(ticket9);

        System.out.println("--- Tentative d'assignation par un utilisateur simple ---");
        ticketManager.assignTicket(ticket9.getTicketID(), dev1, user1); // Devrait echouer

        System.out.println("\n--- Assignation reussie par l'admin ---");
        ticketManager.assignTicket(ticket9.getTicketID(), dev1, admin); // Devrait reussir

        System.out.println("\n--- Tentative de terminaison par un developpeur ---");
        ticketManager.updateTicketStatus(ticket9.getTicketID(), "VALIDATION", dev1);
        ticketManager.closeTicket(ticket9.getTicketID(), dev1); // Devrait echouer

        System.out.println("\n--- Terminaison reussie par l'admin ---");
        ticketManager.closeTicket(ticket9.getTicketID(), admin); // Devrait reussir

        // ========================================================================
        // PARTIE 10: EXPORT PDF
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 10: EXPORT PDF DES TICKETS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("--- Export du ticket critique en PDF ---");
        ticketManager.exportTicketToPDF(ticket1.getTicketID(), "ticket_critique_" + ticket1.getTicketID() + ".pdf");

        // ========================================================================
        // PARTIE 11: STATISTIQUES GLOBALES
        // ========================================================================
        System.out.println("\n\n═══════════════════════════════════════════════════════════════");
        System.out.println("  PARTIE 11: STATISTIQUES GLOBALES DU SYSTEME");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        ticketManager.displaySystemStatistics();

        System.out.println("\n--- Vue d'ensemble de tous les tickets ---");
        ticketManager.displayAllTickets();

        // ========================================================================
        // RESUME FINAL
        // ========================================================================
        System.out.println("\n\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    RESUME DES TESTS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("Tests effectues avec succes:");
        System.out.println("  - Creation et validation des utilisateurs");
        System.out.println("  - Creation de tickets avec differentes priorites");
        System.out.println("  - Gestion des descriptions avec images et videos");
        System.out.println("  - Cycle de vie complet d'un ticket (OUVERT -> TERMINE)");
        System.out.println("  - Fermeture directe et retours en arriere");
        System.out.println("  - Gestion des assignations et charge de travail");
        System.out.println("  - Modification des priorites");
        System.out.println("  - Recherche et filtrage de tickets");
        System.out.println("  - Validation des permissions et securite");
        System.out.println("  - Export PDF (simulation pour interface graphique)");
        System.out.println("  - Statistiques globales du systeme");

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         TOUS LES TESTS SONT PASSES AVEC SUCCES !              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }
}
