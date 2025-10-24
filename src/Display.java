import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame{ // Classe pour l'affichage des tickets et interface GUI
    // Variables d'instance
    private List<User> allUsers;
    private User currentUser;
    private Ticket selectedTicket;

    // Composants GUI
    private JList<Ticket> ticketList;
    private JList<User> userList;
    private JTextField titreField;
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextArea descriptionArea;
    private JTextArea currentComments;
    private JTextArea commentArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<User> assignatedUserBox;
    private JComboBox<String> userTypeBox;
    private JComboBox<String> filterStatusBox;
    private JButton saveButton;
    private JButton createButton;
    private JButton exportPDFButton;
    private JButton createUserButton;
    private JButton desassignButton;
    private JPanel formPanel;
    private JPanel ticketPanel;
    private JPanel userPanel;
    private JScrollPane affichageTickets;
    private JScrollPane affichageUtilisateurs;

    // Gestionnaires
    private TicketManager ticketManager;
    private descriptionManager descManager;
    private commentManager commManager;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private TicketCreator ticketCreator;
    private UserCreator userCreator;

    // *********************************** Constructeur *********************************** //
    public Display() {
        // Configuration de la fenêtre principale
        setTitle("Programme de gestion des tickets"); // Titre de la fenêtre
        setSize(1350, 650); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Action à la fermeture

        // Initialisation des gestionnaires
        ticketManager = new TicketManager(); // Création du ticketManager
        descManager = ticketManager.getDescriptionManager(); // Récupération du descriptionManager
        commManager = ticketManager.getCommentManager(); // Récupération du commentManager
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
        initListeners();

        // 3 : Rendre la fenêtre visible
        setVisible(true);
    }
    // ********************************************************************************************* //

    // ************************ Méthode pour initialiser les composants GUI ************************ //
    private void initComponents() {
        // Configuration du layout du GUI
        // Liste À GAUCHE : affichage des tickets
        ticketList = new JList<>(); // Liste des tickets qui permet de sélectionner un ticket
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Charger les tickets existants sous format JList
        filterStatusBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour filtrer par statut
        filterStatusBox.insertItemAt("TOUS", 0); // Option pour afficher tous les statuts
        filterStatusBox.setSelectedIndex(0); // Valeur par défaut = TOUS
        affichageTickets = new JScrollPane(ticketList); // Affichage qui permet le défilement
        ticketPanel = new JPanel(new BorderLayout()); // Panneau pour la liste des tickets
        ticketPanel.add(affichageTickets, BorderLayout.CENTER); // Ajouter l'affichage des tickets au panneau des tickets
        ticketPanel.add(filterStatusBox, BorderLayout.NORTH); // Ajouter la liste de filtre au panneau des tickets en haut

        // Formulaire AU CENTRE : création/modification de tickets
        // Champs de formulaire
        titreField = new JTextField(); // Champ de texte pour le titre
        descriptionArea = new JTextArea(); // Zone de texte pour la description
        currentComments = new JTextArea(); // Zone de texte pour les commentaires courants
        currentComments.setEditable(false); // Les commentaires courants ne sont pas éditables
        commentArea = new JTextArea(); // Zone de texte pour ajouter un commentaire
        statutBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour le statut
        prioriteBox = new JComboBox<>(priorityManager.getValidPriorities().toArray(new String[0])); // Liste déroulante pour la priorité
        assignatedUserBox = new JComboBox<>(allUsers.toArray(new User[0])); // Liste déroulante pour l'utilisateur assigné
        assignatedUserBox.insertItemAt(null, 0); // Option pour ne pas assigner d'utilisateur
        assignatedUserBox.setSelectedIndex(0); // Valeur par défaut = null
        saveButton = new JButton("Modifier ticket"); // Bouton pour modifier un ticket
        createButton = new JButton("Créer ticket"); // Bouton pour créer un ticket
        desassignButton = new JButton("Désassigner le ticket"); // Bouton pour désassigner un utilisateur d'un ticket
        exportPDFButton = new JButton("Exporter le ticket en PDF"); // Bouton pour exporter un ticket en PDF
        // Panneau de creation/modification
        formPanel = new JPanel(new GridLayout(9, 1)); // Panneau de formulaire pour créer/modifier un ticket
        formPanel.add(new JLabel("Titre :")); // Étiquette pour le titre
        formPanel.add(titreField); // Ajout du champ de titre
        formPanel.add(new JLabel("Description :")); // Étiquette pour la description
        formPanel.add(new JScrollPane(descriptionArea)); // Ajout de la zone de texte pour la description
        formPanel.add(new JLabel("Commentaires :")); // Étiquette pour les commentaires courants
        formPanel.add(new JScrollPane(currentComments)); // Ajout de la zone de texte pour les commentaires courants
        formPanel.add(new JLabel("Ajouter un commentaire :")); // Étiquette pour les commentaires
        formPanel.add(new JScrollPane(commentArea)); // Ajout de la zone de texte pour les commentaires
        formPanel.add(new JLabel("Statut :")); // Étiquette pour le statut
        formPanel.add(statutBox); // Ajout de la liste déroulante pour le statut
        formPanel.add(new JLabel("Priorité :")); // Étiquette pour la priorité
        formPanel.add(prioriteBox); // Ajout de la liste déroulante pour la priorité
        formPanel.add(new JLabel("Utilisateur assigné :")); // Étiquette pour l'utilisateur assigné
        formPanel.add(assignatedUserBox); // Ajout de la liste déroulante pour l'utilisateur assigné
        formPanel.add(createButton); // Ajout du bouton de création de ticket
        formPanel.add(saveButton); // Ajout du bouton de modification de ticket
        formPanel.add(desassignButton); // Ajout du bouton de désassignation de ticket
        formPanel.add(exportPDFButton); // Ajout du bouton d'export PDF

        //Formulaire À DROITE : Création d'utilisateurs
        // Champs de gestion des utilisateurs
        createUserButton = new JButton("Créer un utilisateur"); // Bouton pour créer un utilisateur
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

        // Ajout des panneaux à la fenêtre principale
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Tickets")); // Bordure avec titre pour la liste des tickets
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer / Modifier un Ticket")); // Bordure avec titre pour le formulaire
        affichageUtilisateurs.setBorder(BorderFactory.createTitledBorder("Utilisateur connecté :")); // Bordure avec titre pour la liste des utilisateurs
        userPanel.setBorder(BorderFactory.createTitledBorder("Créer un nouvel utilisateur")); // Bordure avec titre pour le panneau des utilisateurs
        add(ticketPanel, BorderLayout.WEST); // Ajouter la liste des tickets à gauche
        add(formPanel, BorderLayout.CENTER); // Ajouter le formulaire au centre
        add(affichageUtilisateurs, BorderLayout.NORTH); // Ajouter la liste des utilisateurs en haut
        add(userPanel, BorderLayout.EAST); // Ajouter la liste des utilisateurs à droite
    }
    // ***************************************************************************************************** //

    // ************************ Méthode pour initialiser les écouteurs d'événements ************************ //
    private void initListeners() {
        // Événement pour le bouton "Créer" de ticket
        createButton.addActionListener(e -> creerTicket());

        // Événement pour le bouton "Modifier" de ticket
        //saveButton.addActionListener(e -> modifierTicket());

        // Événement pour le bouton "Exporter en PDF"
        //exportPDFButton.addActionListener(e -> exporterTicketPDF());

        // Événement pour le bouton "Désassigner"
        desassignButton.addActionListener(e -> desassignerUtilisateur());

        // Événement pour le bouton "Créer" d'utilisateur
        createUserButton.addActionListener(e -> creerUtilisateur());

        // Événement pour la sélection d'un ticket dans la liste
        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ticket selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    remplirFormulaireTicket(selectedTicket);
                }
            }
        });

        // Événement pour le changement d'utilisateur connecté
        userList.addListSelectionListener(e -> {
            currentUser = (User) userList.getSelectedValue();
            filtrerTicketsUser();
        });

        // Événement pour le filtre par statut
        filterStatusBox.addActionListener(e -> filtrerTicketsStatut());
    }
    // ********************************************************************************* //

    // ************************ Méthodes de gestion des tickets ************************ //
    // Méthode pour rafraîchir la liste des tickets affichés
    private void rafraichirListeTickets() {
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Met à jour la liste des tickets affichés
    }

    // Méthode pour vider le formulaire de ticket
    private void viderFormulaireTicket() {
        titreField.setText(""); // Vider le champ du titre
        descriptionArea.setText(""); // Vider le champ de description
        commentArea.setText(""); // Vider le champ de commentaire
        statutBox.setSelectedIndex(0); // Réinitialiser le statut
        prioriteBox.setSelectedIndex(0); // Réinitialiser la priorité
        assignatedUserBox.setSelectedIndex(0); // Réinitialiser l'utilisateur assigné
    }

    // Méthode pour remplir le formulaire de ticket avec les données d'un ticket sélectionné
    private void remplirFormulaireTicket(Ticket ticket) {
        titreField.setText(ticket.getTitle()); // Remplir le champ du titre
        descriptionArea.setText(descManager.getDescriptionSummary(ticket.getDescription())); // Remplir le champ de description
        statutBox.setSelectedItem(ticket.getStatus()); // Récupérer le statut
        prioriteBox.setSelectedItem(ticket.getPriority()); // Récupérer la priorité

        // Récupérer l'utilisateur assigné dans la liste déroulante
        int assignedUserId = ticket.getAssignedUserId();
        if (assignedUserId != 0) {
            // Parcourir la liste des utilisateurs pour trouver l'utilisateur assigné
            for (User user : allUsers) {
                if (user.getUserID() == assignedUserId) {
                    assignatedUserBox.setSelectedItem(user);
                    break;
                }
            }
        } else {
            assignatedUserBox.setSelectedIndex(0); // Aucun utilisateur assigné
        }

        // Récupérer et afficher les commentaires existants
        List<String> comments = commManager.getComments(ticket);
        currentComments.setText(String.join("\n", comments));
    }

    // Méthode pour créer un ticket
    private void creerTicket() {
        try {
            // Récupération des champs
            String titre = titreField.getText().trim(); // Titre du ticket
            String description = descriptionArea.getText().trim(); // Description du ticket
            String commentaire = commentArea.getText().trim(); // Commentaire du ticket
            String priorite = (String) prioriteBox.getSelectedItem(); // Priorité du ticket
            User utilisateurAssigne = (User) assignatedUserBox.getSelectedItem(); // Utilisateur assigné au ticket

            // Vérifier qu'un utilisateur est sélectionné
            User selectedUser = userList.getSelectedValue(); // Utilisateur créateur du ticket
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur créateur du ticket !", 
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return;
            }
            
            // Création : créer un nouveau ticket
            Ticket newTicket = ticketCreator.createTicket(titre, description, selectedUser, priorite);

            if (newTicket != null) {
                
                
                // Assigner un utilisateur si sélectionné
                if (utilisateurAssigne != null) {
                    ticketManager.assignTicket(newTicket.getTicketID(), utilisateurAssigne, selectedUser);
                    // Si un statut autre que "ASSIGNÉ" est sélectionné, avertir l'utilisateur qu'il sera ignoré
                    if (statutBox.getSelectedItem() != "ASSIGNÉ") {
                        JOptionPane.showMessageDialog(this, 
                            "Le statut initial d'un nouveau ticket avec utilisateur assigné est toujours 'ASSIGNÉ'. Le statut sélectionné sera ignoré.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    // Si un statut autre que "OUVERT" est sélectionné, avertir l'utilisateur qu'il sera ignoré
                    if (statutBox.getSelectedItem() != "OUVERT") {
                        JOptionPane.showMessageDialog(this, 
                            "Le statut initial d'un nouveau ticket est toujours 'OUVERT'. Le statut sélectionné sera ignoré.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Avertir que aucun utilisateur n'a été assigné et qu'aucun commentaire ne sera ajouté
                    JOptionPane.showMessageDialog(this, "Vous n'avez pas assigné d'utilisateur au ticket. Aucun commentaire ne sera ajouté.", 
                    "Assignation", JOptionPane.INFORMATION_MESSAGE);
                    commentaire = "";
                }

                // Ajouter un commentaire si fourni
                if (!commentaire.isEmpty()) {
                    ticketManager.addCommentToTicket(newTicket.getTicketID(), commentaire, selectedUser);
                }

                // Afficher un message de succès de création
                JOptionPane.showMessageDialog(this, 
                    "Ticket #" + newTicket.getTicketID() + " créé avec succès !", "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            
                // Rafraîchir la liste des tickets
                rafraichirListeTickets();
                
                // Vider le formulaire
                viderFormulaireTicket();
            }
            else {
                // La création a échoué, afficher un message d'erreur
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la création du ticket.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la création/modification du ticket : " + ex.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour filtrer les tickets par statut
    private void filtrerTicketsStatut() {
        String statutFiltre = (String) filterStatusBox.getSelectedItem(); // Récupérer le statut sélectionné
        // Si le filtre est "TOUS", afficher tous les tickets
        if (statutFiltre.equals("TOUS")) {
            ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Afficher tous les tickets
            return;
        }
        ticketList.setListData(ticketManager.getTicketsByStatus(statutFiltre).toArray(new Ticket[0])); // Met à jour la liste des tickets affichés selon le filtre
    }

    // Méthode pour filtrer les tickets par utilisateur connecté
    private void filtrerTicketsUser() {

        if (currentUser.getRole().equals("ADMIN") || currentUser == null) {
            rafraichirListeTickets();
            return;
        }
        ticketList.setListData(ticketManager.getTicketsByUser(currentUser).toArray(new Ticket[0])); // Met à jour la liste des tickets affichés selon le user connecté
    }

    // Méthode pour désassigner l'utilisateur d'un ticket
    private void desassignerUtilisateur() {
        Ticket selectedTicket = ticketList.getSelectedValue(); // Récupérer le ticket sélectionné

        // SI aucun ticket sélectionné, afficher un message d'erreur
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ticket à désassigner !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SI aucun utilisateur connecté sélectionné, afficher un message d'erreur
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur connecté !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si tout est OK, procéder à la désassignation
        boolean success = ticketManager.unassignTicket(selectedTicket.getTicketID(), currentUser);

        // SI la désassignation a réussi, afficher un message de succès
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Utilisateur désassigné avec succès du ticket #" + selectedTicket.getTicketID() + ".", 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
            rafraichirListeTickets(); // Rafraîchir la liste des tickets pour refléter le changement
        } else {
            // SI la désassignation a échoué, afficher un message d'erreur
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la désassignation de l'utilisateur du ticket.", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ************************************************************************************** //

    // ************************ Méthodes de gestion des utilisateurs ************************ //
    // Méthode pour rafraîchir la liste des utilisateurs affichés
    private void rafraichirListeUtilisateurs() {
        userList.setListData(allUsers.toArray(new User[0])); // Met à jour la liste des utilisateurs affichés
        assignatedUserBox.setModel(new DefaultComboBoxModel<>(allUsers.toArray(new User[0]))); // Met à jour la liste des utilisateurs assignables
        assignatedUserBox.insertItemAt(null, 0); // Option pour ne pas assigner d'utilisateur
        assignatedUserBox.setSelectedIndex(0); // Valeur par défaut = null
    }

    // Méthode pour vider le formulaire d'utilisateur
    private void viderFormulaireUtilisateur() {
        userNameField.setText(""); // Vider le champ du nom d'utilisateur
        userEmailField.setText(""); // Vider le champ de l'email
        userTypeBox.setSelectedIndex(0); // Réinitialiser le rôle utilisateur
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
                // La création a échoué, afficher un message d'erreur
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la création de l'utilisateur .", "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si la création a réussi, ajouter à la liste des utilisateurs
            allUsers.add(newUser);
            
            // Rafraîchir la liste des utilisateurs
            rafraichirListeUtilisateurs();
            
            // Vider les champs de création d'utilisateur
            viderFormulaireUtilisateur();

            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, 
                "Utilisateur créé avec succès !\nNom : " + newUser.getName() + "\nID : " + newUser.getUserID(), 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de la création de l'utilisateur : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

