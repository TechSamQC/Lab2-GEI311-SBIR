import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame{ // Classe pour l'affichage des tickets et interface GUI
    // Utilisateurs
    private List<User> allUsers;
    private User currentUser;

    // Format de date
    private SimpleDateFormat dateFormat;

    // Composants GUI
    private JList<Ticket> ticketList;
    private JList<User> userList;
    private JTextField titreField;
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextArea descriptionArea;
    private JTextArea commentArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<String> userTypeBox;
    private JComboBox<String> filterStatusBox;
    private JButton saveButton;
    private JButton createButton;
    private JButton exportPDFButton;
    private JButton createUserButton;
    private JPanel formPanel;
    private JPanel ticketPanel;
    private JPanel userPanel;
    private JPanel exportPanel;
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
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Format de date
        setTitle("Programme de gestion des tickets"); // Titre de la fenêtre
        setSize(1350, 650); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Action à la fermeture

        // Initialisation des gestionnaires
        ticketManager = new TicketManager(); // Création du ticketManager
        descManager = ticketManager.getDescriptionManager(); // Récupération du descriptionManager
        statusManager = ticketManager.getStatusManager(); // Récupération du statusManager
        priorityManager = ticketManager.getPriorityManager(); // Récupération du priorityManager
        ticketCreator = new TicketCreator(0, ticketManager); // ticketCreator sera relié avec ticketManager
        userCreator = new UserCreator(); // Création du userCreator

        // Initialisation de la liste des utilisateurs
        allUsers = new ArrayList<>(List.of(new User(0, "SYS ADMIN", "SYSTEM@EXAMPLE.COM", "ADMIN"))); /*Initialisation de la liste
        des utilisateurs avec utilisateur système par défaut*/

        // 1 : Initialiser les composants GUI
        initComponents();

        // 2 : Écouter les événements
        //initListeners();

        // 3 : Rendre la fenêtre visible
        setVisible(true);
    }

    private void initComponents() {
        // Configuration du layout du GUI
        // Liste À GAUCHE : affichage des tickets
        ticketList = new JList<>(); // Liste des tickets qui permet de sélectionner un ticket
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Charger les tickets existants sous format JList
        filterStatusBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour filtrer par statut
        affichageTickets = new JScrollPane(ticketList); // Affichage qui permet le défilement
        ticketPanel = new JPanel(new BorderLayout()); // Panneau pour la liste des tickets
        ticketPanel.add(affichageTickets, BorderLayout.CENTER); // Ajouter l'affichage des tickets au panneau des tickets
        ticketPanel.add(filterStatusBox, BorderLayout.NORTH); // Ajouter la liste de filtre au panneau des tickets en haut

        // Formulaire AU CENTRE : création/modification de tickets
        // Champs de formulaire
        titreField = new JTextField(); // Champ de texte pour le titre
        descriptionArea = new JTextArea(); // Zone de texte pour la description
        commentArea = new JTextArea(); // Zone de texte pour les commentaires
        statutBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour le statut
        prioriteBox = new JComboBox<>(priorityManager.getValidPriorities().toArray(new String[0])); // Liste déroulante pour la priorité
        saveButton = new JButton("Modifier ticket"); // Bouton pour modifier un ticket
        createButton = new JButton("Créer ticket"); // Bouton pour créer un ticket
        // Panneau de creation/modification
        formPanel = new JPanel(new GridLayout(6, 1)); // Panneau de formulaire pour créer/modifier un ticket
        formPanel.add(new JLabel("Titre :")); // Étiquette pour le titre
        formPanel.add(titreField); // Ajout du champ de titre
        formPanel.add(new JLabel("Description :")); // Étiquette pour la description
        formPanel.add(new JScrollPane(descriptionArea)); // Ajout de la zone de texte pour la description
        formPanel.add(new JLabel("Commentaire/Ajouter un commentaire :")); // Étiquette pour les commentaires
        formPanel.add(new JScrollPane(commentArea)); // Ajout de la zone de texte pour les commentaires
        formPanel.add(new JLabel("Statut :")); // Étiquette pour le statut
        formPanel.add(statutBox); // Ajout de la liste déroulante pour le statut
        formPanel.add(new JLabel("Priorité :")); // Étiquette pour la priorité
        formPanel.add(prioriteBox); // Ajout de la liste déroulante pour la priorité
        formPanel.add(createButton); // Ajout du bouton de création de ticket
        formPanel.add(saveButton); // Ajout du bouton de modification de ticket

        //Formulaire À DROITE : Création d'utilisateurs
        // Champs de gestion des utilisateurs
        createUserButton = new JButton("Créer"); // Bouton pour créer un utilisateur
        userTypeBox = new JComboBox<>(new String[]{"ADMIN", "DEVELOPER", "USER"}); // Liste déroulante pour le rôle utilisateur
        userNameField = new JTextField(); // Champ de texte pour le nom d'utilisateur
        userEmailField = new JTextField(); // Champ de texte pour l'email
        // Panneau des utilisateurs
        userPanel = new JPanel(new GridLayout(4, 1)); // Panneau pour la gestion des utilisateurs
        userPanel.add(new JLabel("Rôle utilisateur :")); // Étiquette pour le rôle utilisateur
        userPanel.add(userTypeBox); // Ajout de la liste déroulante pour le rôle utilisateur
        userPanel.add(new JLabel("Nom d'utilisateur :")); // Étiquette pour le nom d'utilisateur
        userPanel.add(userNameField); // Champ de texte pour le nom d'utilisateur
        userPanel.add(new JLabel("Email :")); // Étiquette pour l'email
        userPanel.add(userEmailField); // Champ de texte pour l'email
        userPanel.add(createUserButton); // Ajout du bouton de création d'utilisateur

        // Liste de sélection des utilisateurs EN HAUT
        userList = new JList<>(allUsers.toArray(new User[0])); // Liste des utilisateurs qui permet de sélectionner un utilisateur
        affichageUtilisateurs = new JScrollPane(userList); // Affichage qui permet le défilement

        // Boutons d'export PDF EN BAS
        exportPDFButton = new JButton("Exporter le ticket en PDF"); // Bouton pour exporter un ticket en PDF
        exportPanel = new JPanel(); // Panneau pour le bouton d'export
        exportPanel.add(exportPDFButton); // Ajout du bouton d'export au panneau

        // Ajout des panneaux à la fenêtre principale
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Tickets")); // Bordure avec titre pour la liste des tickets
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer / Modifier un Ticket")); // Bordure avec titre pour le formulaire
        affichageUtilisateurs.setBorder(BorderFactory.createTitledBorder("Sélectionner l'utilisateur :")); // Bordure avec titre pour la liste des utilisateurs
        userPanel.setBorder(BorderFactory.createTitledBorder("Créer un nouvel utilisateur")); // Bordure avec titre pour le panneau des utilisateurs
        add(ticketPanel, BorderLayout.WEST); // Ajouter la liste des tickets à gauche
        add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au centre
        add(affichageUtilisateurs, BorderLayout.NORTH); // Ajouter la liste des utilisateurs en haut
        add(userPanel, BorderLayout.EAST); // Ajouter la liste des utilisateurs à droite
        add(exportPanel, BorderLayout.SOUTH); // Ajouter le panneau d'export en bas
    }}

    /*// Méthode pour initialiser les écouteurs d'événements
    private void initListeners() {
        // Événement pour le bouton "Créer" de ticket
        saveButton.addActionListener(e -> creerTicket());

        // Événement pour le bouton "Modifier" de ticket
        //createButton.addActionListener(e -> modifierTicket());

        // Événement pour le bouton "Exporter en PDF"
        //exportPDFButton.addActionListener(e -> exporterTicketPDF());

        // Événement pour le bouton "Créer" d'utilisateur
        createUserButton.addActionListener(e -> creerUtilisateur());

        // Événement pour la sélection d'un ticket dans la liste
        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ticket selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    remplirFormulaireTicket(selectedTicket);
                    updateActionsPanel(selectedTicket);
                }
            }
        });
        
        // Événement pour le changement d'utilisateur connecté
        userList.addListSelectionListener(e -> {
            currentUser = (User) userList.getSelectedValue();
            Ticket selectedTicket = ticketList.getSelectedValue();
            updateActionsPanel(selectedTicket);
        });
        
        // Événement pour le filtre par statut
        filterStatusBox.addActionListener(e -> filtrerTickets());
    }

    // Méthode pour créer un ticket
    private void creerTicket() {
        try {
            // Récupération des champs
            String titre = titreField.getText().trim();
            String description = descriptionArea.getText().trim();
            String priorite = (String) prioriteBox.getSelectedItem();
            
            // Vérifier qu'un utilisateur est sélectionné
            User selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur créateur du ticket !", 
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return;
            }
            
            // Création : créer un nouveau ticket
            Ticket newTicket = ticketCreator.createTicket(titre, description, selectedUser, priorite);

            if (newTicket != null) {
                JOptionPane.showMessageDialog(this, 
                    "Ticket #" + newTicket.getTicketID() + " créé avec succès !", "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Rafraîchir la liste des tickets
                rafraichirListeTickets();
                
                // Vider le formulaire
                viderFormulaireTicket();
            }
            else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la création du ticket.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la création/modification du ticket : " + ex.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour créer un utilisateur
    private void creerUtilisateur() {
        try {
            // Récupérer les valeurs des champs
            String nom = userNameField.getText().trim();
            String email = userEmailField.getText().trim();
            String role = (String) userTypeBox.getSelectedItem();

            // Créer l'utilisateur avec UserCreator (qui fait sa propre validation)
            User newUser = userCreator.createUser(nom, email, role);

            if (newUser == null) {
                // La création a échoué.
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la création de l'utilisateur .", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Ajouter à la liste des utilisateurs
            allUsers.add(newUser);
            
            // Rafraîchir la liste
            rafraichirListeUtilisateurs();
            
            // Vider les champs
            userNameField.setText("");
            userEmailField.setText("");
            userTypeBox.setSelectedIndex(0);
            
            JOptionPane.showMessageDialog(this, 
                "Utilisateur créé avec succès !\nNom : " + newUser.getName() + "\nID : " + newUser.getUserID(), 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création de l'utilisateur : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/

