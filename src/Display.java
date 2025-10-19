import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame{ // Classe pour l'affichage des tickets et interface GUI
    // Format de date
    private SimpleDateFormat dateFormat;

    // Composants GUI
    private JList<Ticket> ticketList;
    private JList<User> userList;
    private JTextField titreField;
    private JTextArea descriptionArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JButton saveButton;
    private JButton exportPDFButton;
    private JButton createUserButton;
    private JPanel formPanel;
    private JPanel userPanel;
    private JScrollPane affichageTickets;
    private JScrollPane affichageUtilisateurs;

    // Gestionnaires
    private TicketManager ticketManager;
    private descriptionManager descManager;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private TicketCreator ticketCreator;
    private UserCreator userCreator;

    // Constructeur
    public Display() {
        // Configuration de la fenêtre
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        setTitle("Programme de gestion des tickets");
        setSize(1250, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialisation des gestionnaires
        ticketManager = new TicketManager(this);
        descManager = ticketManager.getDescriptionManager();
        statusManager = ticketManager.getStatusManager();
        priorityManager = ticketManager.getPriorityManager();
        ticketCreator = new TicketCreator(0, ticketManager); // ticketCreator sera relié avec ticketManager
        userCreator = new UserCreator();

        // 1 : Initialiser les composants
        initComponents();

        // 2 : Écouter les événements
        //initListeners();

        // 3 : Rendre la fenêtre visible
        setVisible(true);
    }

    private void initComponents() {
        // Configuration du layout du GUI
        // Liste à gauche : affichage des tickets
        ticketList = new JList<>(); // Liste des tickets qui permet de sélectionner un ticket
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Charger les tickets existants sous format JList
        affichageTickets = new JScrollPane(ticketList); // Affichage qui permet le défilement

        // Formulaire au centre : création/modification de tickets
        formPanel = new JPanel(new GridLayout(5, 1)); // Panneau de formulaire pour créer/modifier un ticket
        titreField = new JTextField(); // Champ de texte pour le titre
        descriptionArea = new JTextArea(); // Zone de texte pour la description
        statutBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour le statut
        prioriteBox = new JComboBox<>(priorityManager.getValidPriorities().toArray(new String[0])); // Liste déroulante pour la priorité
        saveButton = new JButton("Créer / Modifier"); // Bouton pour créer ou modifier un ticket
        formPanel.add(new JLabel("Titre :")); // Étiquette pour le titre
        formPanel.add(titreField); // Ajout du champ de titre
        formPanel.add(new JLabel("Description :")); // Étiquette pour la description
        formPanel.add(new JScrollPane(descriptionArea)); // Ajout de la zone de texte pour la description
        formPanel.add(new JLabel("Statut :")); // Étiquette pour le statut
        formPanel.add(statutBox); // Ajout de la liste déroulante pour le statut
        formPanel.add(new JLabel("Priorité :")); // Étiquette pour la priorité
        formPanel.add(prioriteBox); // Ajout de la liste déroulante pour la priorité
        formPanel.add(saveButton); // Ajout du bouton de création/modification

        //Liste à droite : gestion des utilisateurs
        createUserButton = new JButton("Créer un utilisateur"); // Bouton pour créer un utilisateur
        userList = new JList<>(); // Liste des utilisateurs
        affichageUtilisateurs = new JScrollPane(userList); // Affichage qui permet le défilement
        userPanel = new JPanel(new GridLayout(2, 1)); // Panneau pour la gestion des utilisateurs
        userPanel.add(affichageUtilisateurs); // Ajout de la liste des utilisateurs
        userPanel.add(createUserButton); // Ajout du bouton de création d'utilisateur

        // Ajout des panneaux à la fenêtre principale
        affichageTickets.setBorder(BorderFactory.createTitledBorder("Tickets")); // Bordure avec titre pour la liste des tickets
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer / Modifier un Ticket")); // Bordure avec titre pour le formulaire
        affichageUtilisateurs.setBorder(BorderFactory.createTitledBorder("Sélectionner l'utilisateur :")); // Bordure avec titre pour la liste des utilisateurs
        userPanel.setBorder(BorderFactory.createTitledBorder("Utilisateurs")); // Bordure avec titre pour le panneau des utilisateurs
        add(affichageTickets, BorderLayout.WEST); // Ajouter la liste des tickets à gauche
        add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au centre
        add(userPanel, BorderLayout.EAST); // Ajouter la liste des utilisateurs à droite
    }

    // Affiche un ticket simple
    public void displayTicket(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Erreur: Le ticket est null.");
            return;
        }

        System.out.println("\n" + formatTicketInfo(ticket));
    }

    // Affiche un ticket avec ses commentaires (version améliorée)
    public void displayTicketWithComments(Ticket ticket, List<String> comments) {
        if (ticket == null) {
            System.out.println("Erreur: Le ticket est null.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              TICKET #" + ticket.getTicketID() + " - DÉTAILS COMPLETS              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println(formatTicketInfo(ticket));
        
        System.out.println("\n--- Commentaires ---");
        displayCommentsList(comments);
        
        System.out.println("\nNote: Pour voir les images/videos, consultez les chemins indiques dans la description.");
        System.out.println("════════════════════════════════════════════════════════\n");
    }

    // Affiche les détails complets d'un ticket
    public void displayTicketDetails(Ticket ticket, List<String> comments, String additionalStatus) {
        if (ticket == null) {
            System.out.println("Erreur: Le ticket est null.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          DÉTAILS COMPLETS DU TICKET #" + ticket.getTicketID() + "              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.println(formatTicketInfo(ticket));
        
        if (additionalStatus != null && !additionalStatus.isEmpty()) {
            System.out.println("\nStatut additionnel: " + additionalStatus);
        }
        
        System.out.println("\n--- Commentaires ---");
        displayCommentsList(comments);
        System.out.println("\n════════════════════════════════════════════════════════\n");
    }

    // Affiche tous les tickets
    public void displayAllTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("\nAucun ticket à afficher.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              LISTE DE TOUS LES TICKETS                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Total: " + tickets.size() + " ticket(s)\n");

        for (Ticket ticket : tickets) {
            System.out.println(formatTicketInfo(ticket));
            System.out.println("────────────────────────────────────────────────────────");
        }

        ticketList.setListData(tickets.toArray(new Ticket[0]));
    }

    // Affiche les tickets filtrés par statut
    public void displayTicketsByStatus(List<Ticket> tickets, String status) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("\nAucun ticket avec le statut: " + status);
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         TICKETS AVEC LE STATUT: " + status + "              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Total: " + tickets.size() + " ticket(s)\n");

        for (Ticket ticket : tickets) {
            System.out.println(formatTicketInfo(ticket));
            System.out.println("────────────────────────────────────────────────────────");
        }
    }

    // Affiche les tickets filtrés par priorité
    public void displayTicketsByPriority(List<Ticket> tickets, String priority) {
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("\nAucun ticket avec la priorité: " + priority);
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║       TICKETS AVEC LA PRIORITÉ: " + priority + "           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Total: " + tickets.size() + " ticket(s)\n");

        for (Ticket ticket : tickets) {
            System.out.println(formatTicketInfo(ticket));
            System.out.println("────────────────────────────────────────────────────────");
        }
    }

    // Affiche les tickets d'un utilisateur
    public void displayTicketsByUser(List<Ticket> tickets, User user) {
        if (user == null) {
            System.out.println("Erreur: L'utilisateur est null.");
            return;
        }

        if (tickets == null || tickets.isEmpty()) {
            System.out.println("\nAucun ticket pour l'utilisateur: " + user.getName());
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║    TICKETS DE L'UTILISATEUR: " + user.getName() + "           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Total: " + tickets.size() + " ticket(s)\n");

        for (Ticket ticket : tickets) {
            System.out.println(formatTicketInfo(ticket));
            System.out.println("────────────────────────────────────────────────────────");
        }
    }

    // Affiche les informations d'un utilisateur
    public void displayUserInfo(User user) {
        if (user == null) {
            System.out.println("Erreur: L'utilisateur est null.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              INFORMATIONS UTILISATEUR                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("ID: " + user.getUserID());
        System.out.println("Nom: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Rôle: " + user.getRole());
        System.out.println("════════════════════════════════════════════════════════\n");
    }

    // Affiche une description avec détails complets
    public void displayDescription(Description description) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║              DESCRIPTION DÉTAILLÉE                     ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        // Affichage du texte
        System.out.println("\nContenu Textuel:");
        if (description.hasContent()) {
            System.out.println("─────────────────────────────────────────────────");
            System.out.println(description.getTextContent());
            System.out.println("─────────────────────────────────────────────────");
        } else {
            System.out.println("  (Aucun texte)");
        }
        
        // Affichage des images
        System.out.println("\nCaptures d'Ecran (" + description.getImagePaths().size() + "):");
        if (description.hasImages()) {
            int i = 1;
            for (String path : description.getImagePaths()) {
                System.out.println("  [Image " + i + "] " + path);
                System.out.println("            -> Ouvrir avec visionneuse d'images");
                i++;
            }
        } else {
            System.out.println("  (Aucune image attachée)");
        }
        
        // Affichage des vidéos
        System.out.println("\nVideos de Demonstration (" + description.getVideoPaths().size() + "):");
        if (description.hasVideos()) {
            int i = 1;
            for (String path : description.getVideoPaths()) {
                System.out.println("  [Video " + i + "] " + path);
                System.out.println("            -> Ouvrir avec lecteur video");
                i++;
            }
        } else {
            System.out.println("  (Aucune vidéo attachée)");
        }
        
        // Métadonnées
        System.out.println("\nMetadonnees:");
        System.out.println("  Créé le: " + formatDate(description.getCreationDate()));
        System.out.println("  Modifié le: " + formatDate(description.getLastModified()));
        
        System.out.println("\nNote: Dans une application web/GUI, les images et videos");
        System.out.println("      s'afficheraient directement dans l'interface.");
        System.out.println("════════════════════════════════════════════════════════\n");
    }

    // Affiche des statistiques
    public void displayStatistics(int totalTickets, int openTickets, int closedTickets) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  STATISTIQUES                          ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Total de tickets: " + totalTickets);
        System.out.println("Tickets ouverts: " + openTickets);
        System.out.println("Tickets fermés: " + closedTickets);
        
        if (totalTickets > 0) {
            double openPercentage = (openTickets * 100.0) / totalTickets;
            double closedPercentage = (closedTickets * 100.0) / totalTickets;
            System.out.println("Pourcentage ouvert: " + String.format("%.2f", openPercentage) + "%");
            System.out.println("Pourcentage fermé: " + String.format("%.2f", closedPercentage) + "%");
        }
        
        System.out.println("════════════════════════════════════════════════════════\n");
    }

    // Exporte un ticket en PDF (simulation)
    public boolean exportTicketToPDF(Ticket ticket, Description description, List<String> comments, String filePath) {
        if (ticket == null || filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Erreur: Ticket ou chemin de fichier invalide.");
            return false;
        }

        // Utiliser le dossier exports pour les PDFs
        String fullPath = "media/exports/" + filePath;

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                   EXPORT PDF                           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Fichier: " + fullPath);
        System.out.println("\n--- Contenu du ticket ---");
        System.out.println(formatTicketInfo(ticket));
        
        if (description != null) {
            System.out.println("\n--- Description ---");
            System.out.println(description.getContentSummary());
            
            if (description.hasImages()) {
                System.out.println("\nImages incluses:");
                for (String img : description.getImagePaths()) {
                    System.out.println("  - " + img);
                }
            }
            
            if (description.hasVideos()) {
                System.out.println("\nVideos referencees:");
                for (String vid : description.getVideoPaths()) {
                    System.out.println("  - " + vid);
                }
            }
        }
        
        if (comments != null && !comments.isEmpty()) {
            System.out.println("\n--- Commentaires ---");
            System.out.println(formatComments(comments));
        }
        
        System.out.println("\n════════════════════════════════════════════════════════");
        System.out.println("[SIMULATION] Export PDF complete !");
        System.out.println("Emplacement prevu: " + fullPath);
        System.out.println("\nNOTE: L'export PDF reel sera implemente dans la partie");
        System.out.println("      avec interface graphique (GUI) en utilisant une");
        System.out.println("      bibliotheque comme Apache PDFBox ou iText.");
        System.out.println("      Le systeme est pret pour cette integration.");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        return true;
    }

    // Formate les informations d'un ticket
    public String formatTicketInfo(Ticket ticket) {
        if (ticket == null) {
            return "Ticket null";
        }

        StringBuilder info = new StringBuilder();
        info.append("Ticket #").append(ticket.getTicketID()).append("\n");
        info.append("Titre: ").append(ticket.getTitle()).append("\n");
        info.append("Description: ").append(ticket.getDescription()).append("\n");
        info.append("Statut: ").append(ticket.getStatus()).append("\n");
        info.append("Priorité: ").append(ticket.getPriority()).append("\n");
        info.append("Créé le: ").append(ticket.getCreationDate()).append("\n");
        info.append("Mis à jour le: ").append(ticket.getUpdateDate()).append("\n");
        info.append("Assigné à: ").append(ticket.getAssignedUserId() == 0 ? "Non assigné" : "User ID " + ticket.getAssignedUserId());
        
        return info.toString();
    }

    // Formate une date
    public String formatDate(Date date) {
        if (date == null) {
            return "Date inconnue";
        }
        return dateFormat.format(date);
    }

    // Formate une liste de commentaires
    public String formatComments(List<String> comments) {
        if (comments == null || comments.isEmpty()) {
            return "Aucun commentaire";
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < comments.size(); i++) {
            formatted.append("[").append(i + 1).append("] ").append(comments.get(i)).append("\n");
        }
        return formatted.toString();
    }

    // Méthode privée pour afficher une liste de commentaires
    private void displayCommentsList(List<String> comments) {
        if (comments == null || comments.isEmpty()) {
            System.out.println("Aucun commentaire");
        } else {
            for (int i = 0; i < comments.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + comments.get(i));
            }
        }
    }

    //Getters
    public TicketManager getTicketManager() {
        return ticketManager;
    }
}

