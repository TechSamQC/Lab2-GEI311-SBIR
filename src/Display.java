import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class Display extends JFrame{ // Classe pour l'affichage des tickets et interface GUI
    // Constantes de couleurs
    private static final Color PRIMARY_COLOR = new Color(55, 65, 81); // #374151 (Gris foncé)
    private static final Color BACKGROUND_COLOR = new Color(249, 250, 251); // #F9FAFB
    private static final Color BORDER_COLOR = new Color(229, 231, 235); // #E5E7EB
    
    // Variables d'instance
    private List<User> allUsers;
    private User currentUser;
    private Ticket selectedTicket;
    private List<String> tempImagesPath;
    private List<String> tempVideosPath;

    // Composants GUI
    private JList<Ticket> ticketList;
    private JList<User> userList;
    private JTextField titreField;
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextArea descriptionArea;
    private JTextArea currentComments;
    private JTextArea commentArea;
    private JTextArea otherInfoArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<User> assignatedUserBox;
    private JComboBox<String> userTypeBox;
    private JComboBox<String> filterStatusBox;
    private JButton saveButton;
    private JButton createButton;
    private JButton addImageButton;
    private JButton showImagesButton;
    private JButton addVideoButton;
    private JButton showVideosButton;
    private JButton exportPDFButton;
    private JButton createUserButton;
    private JButton deleteUserButton;
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
        // Initialiser le Look and Feel moderne
        initLookAndFeel();
        
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

        // Initialisation de la liste d'images temporaires (pour les ajouter au ticket)
        tempImagesPath = new ArrayList<>();
        // Initialisation de la liste de vidéos temporaires (pour les ajouter au ticket)
        tempVideosPath = new ArrayList<>();

        // 1 : Initialiser les composants GUI
        initComponents();

        // 2 : Écouter les événements
        initListeners();

        // 3 : Rendre la fenêtre visible
        setVisible(true);
    }
    // ********************************************************************************************* //
    
    // ************************ Méthode pour initialiser le Look and Feel ************************ //
    private void initLookAndFeel() {
        try {
            // Essayer d'abord FlatLightLaf
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            try {
                // Si FlatLaf n'est pas disponible, utiliser Nimbus
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // Si Nimbus n'est pas disponible, utiliser le Look and Feel par défaut
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
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
        otherInfoArea = new JTextArea(); // Zone de texte pour d'autres informations
        otherInfoArea.setEditable(false); // Les autres informations ne sont pas éditables
        commentArea = new JTextArea(); // Zone de texte pour ajouter un commentaire
        statutBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0])); // Liste déroulante pour le statut
        prioriteBox = new JComboBox<>(priorityManager.getValidPriorities().toArray(new String[0])); // Liste déroulante pour la priorité
        assignatedUserBox = new JComboBox<>(allUsers.toArray(new User[0])); // Liste déroulante pour l'utilisateur assigné
        assignatedUserBox.insertItemAt(null, 0); // Option pour ne pas assigner d'utilisateur
        assignatedUserBox.setSelectedIndex(0); // Valeur par défaut = null
        addImageButton = new JButton("Ajouter une image à la description"); // Boutton pour ajouter une image à la description
        addVideoButton = new JButton("Ajouter une vidéo à la description"); // Boutton pour ajouter une vidéo à la description
        showImagesButton = new JButton("Voir les images"); // Boutton pour voir les images
        showVideosButton = new JButton("Voir les vidéos"); // Boutton pour voir les vidéos
        saveButton = new JButton("Modifier ticket"); // Bouton pour modifier un ticket
        createButton = new JButton("Créer ticket"); // Bouton pour créer un ticket
        desassignButton = new JButton("Désassigner le ticket"); // Bouton pour désassigner un utilisateur d'un ticket
        exportPDFButton = new JButton("Exporter le ticket en PDF"); // Bouton pour exporter un ticket en PDF
        // Panneau de creation/modification
        formPanel = new JPanel(new GridLayout(12, 1, 0, 10)); // Panneau de formulaire avec espacement de 10px
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Marges de 15px
        
        // Appliquer les styles aux boutons
        styleButton(addImageButton);
        styleButton(addVideoButton);
        styleButton(showImagesButton);
        styleButton(showVideosButton);
        styleButton(saveButton);
        styleButton(createButton);
        styleButton(desassignButton);
        styleButton(exportPDFButton);
        
        // Appliquer les styles aux champs de texte
        styleTextField(titreField);
        styleTextArea(descriptionArea);
        styleTextArea(commentArea);
        styleTextArea(currentComments);
        styleTextArea(otherInfoArea);
        
        JLabel titleLabel = new JLabel("Titre :");
        styleTitleLabel(titleLabel);
        formPanel.add(titleLabel); // Étiquette pour le titre
        formPanel.add(titreField); // Ajout du champ de titre
        
        JLabel infoLabel = new JLabel("Informations sur le ticket :");
        styleTitleLabel(infoLabel);
        formPanel.add(infoLabel); // Étiquette pour les autres informations
        formPanel.add(new JScrollPane(otherInfoArea)); // Ajout de la zone de texte pour les autres informations
        
        JLabel descLabel = new JLabel("Description :");
        styleTitleLabel(descLabel);
        formPanel.add(descLabel); // Étiquette pour la description
        formPanel.add(new JScrollPane(descriptionArea)); // Ajout de la zone de texte pour la description
        formPanel.add(addImageButton); // Ajout du bouton pour ajouter des images
        formPanel.add(addVideoButton); // Ajout du bouton pour ajouter des images
        formPanel.add(showImagesButton); // Ajout du bouton pour montrer les images
        formPanel.add(showVideosButton); // Ajout du bouton pour montrer les images
        
        JLabel commentsLabel = new JLabel("Commentaires :");
        styleTitleLabel(commentsLabel);
        formPanel.add(commentsLabel); // Étiquette pour les commentaires courants
        formPanel.add(new JScrollPane(currentComments)); // Ajout de la zone de texte pour les commentaires courants
        
        JLabel addCommentLabel = new JLabel("Ajouter un commentaire :");
        styleTitleLabel(addCommentLabel);
        formPanel.add(addCommentLabel); // Étiquette pour les commentaires
        formPanel.add(new JScrollPane(commentArea)); // Ajout de la zone de texte pour les commentaires
        
        JLabel statutLabel = new JLabel("Statut :");
        styleTitleLabel(statutLabel);
        formPanel.add(statutLabel); // Étiquette pour le statut
        formPanel.add(statutBox); // Ajout de la liste déroulante pour le statut
        
        JLabel prioriteLabel = new JLabel("Priorité :");
        styleTitleLabel(prioriteLabel);
        formPanel.add(prioriteLabel); // Étiquette pour la priorité
        formPanel.add(prioriteBox); // Ajout de la liste déroulante pour la priorité
        
        JLabel userAssignLabel = new JLabel("Utilisateur assigné :");
        styleTitleLabel(userAssignLabel);
        formPanel.add(userAssignLabel); // Étiquette pour l'utilisateur assigné
        formPanel.add(assignatedUserBox); // Ajout de la liste déroulante pour l'utilisateur assigné
        formPanel.add(createButton); // Ajout du bouton de création de ticket
        formPanel.add(saveButton); // Ajout du bouton de modification de ticket
        formPanel.add(desassignButton); // Ajout du bouton de désassignation de ticket
        formPanel.add(exportPDFButton); // Ajout du bouton d'export PDF

        //Formulaire À DROITE : Création d'utilisateurs
        // Champs de gestion des utilisateurs
        createUserButton = new JButton("Créer un utilisateur"); // Bouton pour créer un utilisateur
        deleteUserButton = new JButton("Supprimer l'utilisateur"); // Bouton pour supprimer un utilisateur
        userTypeBox = new JComboBox<>(new String[]{"ADMIN", "DEVELOPER", "USER"}); // Liste déroulante pour le rôle utilisateur
        userNameField = new JTextField(); // Champ de texte pour le nom d'utilisateur
        userEmailField = new JTextField(); // Champ de texte pour l'email
        
        // Appliquer les styles aux composants utilisateur
        styleButton(createUserButton);
        styleButton(deleteUserButton);
        styleTextField(userNameField);
        styleTextField(userEmailField);
        
        // Panneau des utilisateurs
        userPanel = new JPanel(new GridLayout(4, 1, 0, 10)); // Panneau avec espacement de 10px
        userPanel.setBackground(BACKGROUND_COLOR);
        userPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Marges de 15px
        
        JLabel roleLabel = new JLabel("Rôle utilisateur :");
        styleTitleLabel(roleLabel);
        userPanel.add(roleLabel); // Étiquette pour le rôle utilisateur
        userPanel.add(userTypeBox); // Ajout de la liste déroulante pour le rôle utilisateur
        
        JLabel nameLabel = new JLabel("Nom d'utilisateur :");
        styleTitleLabel(nameLabel);
        userPanel.add(nameLabel); // Étiquette pour le nom d'utilisateur
        userPanel.add(userNameField); // Champ de texte pour le nom d'utilisateur
        
        JLabel emailLabel = new JLabel("Email :");
        styleTitleLabel(emailLabel);
        userPanel.add(emailLabel); // Étiquette pour l'email
        userPanel.add(userEmailField); // Champ de texte pour l'email
        userPanel.add(createUserButton); // Ajout du bouton de création d'utilisateur
        userPanel.add(deleteUserButton); // Ajout du bouton de suppression d'utilisateur

        // Liste de sélection des utilisateurs EN HAUT
        userList = new JList<>(allUsers.toArray(new User[0])); // Liste des utilisateurs qui permet de sélectionner un utilisateur
        styleList(userList);
        affichageUtilisateurs = new JScrollPane(userList); // Affichage qui permet le défilement
        
        // Styliser la liste des tickets
        styleList(ticketList);
        ticketPanel.setBackground(BACKGROUND_COLOR);
        ticketPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Marges de 15px

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
        saveButton.addActionListener(e -> modifierTicket());

        // Événement pour le bouton "Exporter en PDF"
        exportPDFButton.addActionListener(e -> exporterTicketPDF());

        // Événement pour le bouton "Désassigner"
        desassignButton.addActionListener(e -> desassignerUtilisateur());

        // Événement pour le bouton "Créer" d'utilisateur
        createUserButton.addActionListener(e -> creerUtilisateur());

        // Événement pour le bouton "Supprimer" d'utilisateur
        deleteUserButton.addActionListener(e -> supprimerUtilisateur());

        // Événement pour le bouton "AjouterImage"
        addImageButton.addActionListener(e -> ajouterImage());

        // Événement pour le bouton "AfficherImages"
        showImagesButton.addActionListener(e -> showImages());

        // Événement pour le bouton "AjouterImage"
        addVideoButton.addActionListener(e -> ajouterVideo());

        // Événement pour le bouton "AfficherImages"
        showVideosButton.addActionListener(e -> showVideos());

        // Événement pour la sélection d'un ticket dans la liste
        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    remplirFormulaireTicket();
                }
            }
        });

        // Événement pour le changement d'utilisateur connecté
        userList.addListSelectionListener(e -> {
            // Vérification que la valeur actuel de la liste n'est pas nulle (pour éviter une exception lors de suppression)
            if (userList.getSelectedValue() != null)
            {
                currentUser = (User) userList.getSelectedValue();
                filtrerTickets(); // Rafraîchir la liste des tickets affichés
                viderFormulaireTicket(); // Vider le formulaire de ticket
            }
        });

        // Événement pour le filtre par statut (utilise la méthode filtre générique)
        filterStatusBox.addActionListener(e -> filtrerTickets());
    }
    // ********************************************************************************* //

    // ************************ Méthodes de gestion des tickets ************************ //
    // Méthode pour filtrer/rafraîchir la liste des tickets
    private void filtrerTickets() {
        String statutFiltre = (String) filterStatusBox.getSelectedItem(); // Récupérer le statut sélectionné
        if (currentUser == null) {
            ticketList.setListData(new Ticket[0]);
        }
        // Sinon si le statut est "TOUS"
        else if (statutFiltre.equals("TOUS")) {
            // Si le user sélectionné est un admin ou null, on veut tous les tickets
            if (currentUser.isAdmin()) {
                ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Afficher tous les tickets
            }
            // Si ce n'est pas un Admin, mais qu'il peut assigner des tickets (developeur)
            else if (currentUser.canAssignTickets()) {
                ticketList.setListData(ticketManager.getTicketsDeveloper(currentUser).toArray(new Ticket[0])); // Afficher tous les tickets du developpeur (ouvert et les siens)
            }
            // Sinon, c'est un utilisateur, filtrer par utilisateur
            else {
                ticketList.setListData(ticketManager.getTicketsByUser(currentUser).toArray(new Ticket[0])); // Met à jour la liste des tickets affichés selon le user connecté
            }
        }
        // Sinon si le statut demandé est "OUVERT" et que le user est un dev ou un admin, ou si c'est un admin, récupérer tous les tickets selon le statut
        else if ((statutFiltre.equals("OUVERT") && currentUser.canAssignTickets()) || currentUser.isAdmin()) {
            ticketList.setListData(ticketManager.getTicketsByStatus(statutFiltre).toArray(new Ticket[0])); // Met à jour la liste des tickets affichés selon le filtre
        }
        // Sinon récupérer les tickets de l'utilisateur correspondant selon le statut
        else {
            ticketList.setListData(ticketManager.getTicketsByStatusUser(statutFiltre, currentUser).toArray(new Ticket[0]));
        }
    }

    // Méthode pour vider le formulaire de ticket
    private void viderFormulaireTicket() {
        titreField.setText(""); // Vider le champ du titre
        descriptionArea.setText(""); // Vider le champ de description
        commentArea.setText(""); // Vider le champ de commentaire
        currentComments.setText(""); // Vider les commentaires courants
        otherInfoArea.setText(""); // Vider les autres informations
        statutBox.setSelectedIndex(0); // Réinitialiser le statut
        prioriteBox.setSelectedIndex(0); // Réinitialiser la priorité
        assignatedUserBox.setSelectedIndex(0); // Réinitialiser l'utilisateur assigné
        tempImagesPath.clear(); // Libération de la liste d'images temporaires pour ne pas garder les images d'un ticket à un autre ou les ajouter plusieurs fois.
        tempVideosPath.clear(); // Libération de la liste de vidéos temporaires pour ne pas garder les vidéos d'un ticket à un autre ou les ajouter plusieurs fois.
    }

    // Méthode pour remplir le formulaire de ticket avec les données d'un ticket sélectionné
    private void remplirFormulaireTicket() {
        titreField.setText(selectedTicket.getTitle()); // Remplir le champ du titre
        descriptionArea.setText(descManager.getDescriptionSummary(selectedTicket.getDescription())); // Remplir le champ de description
        statutBox.setSelectedItem(selectedTicket.getStatus()); // Récupérer le statut
        prioriteBox.setSelectedItem(selectedTicket.getPriority()); // Récupérer la priorité
        otherInfoArea.setText("Créé le : " + selectedTicket.getCreationDate() + "\nDernière mise à jour : " + selectedTicket.getUpdateDate()); // Afficher les dates de création et de mise à jour

        // Récupérer l'utilisateur assigné dans la liste déroulante
        int assignedUserId = selectedTicket.getAssignedUserId();
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
        List<String> comments = commManager.getComments(selectedTicket);
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
            currentUser = userList.getSelectedValue(); // Utilisateur créateur du ticket
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur créateur du ticket !", 
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
            return;
            }
            
            // Création : créer un nouveau ticket
            Ticket newTicket = ticketCreator.createTicket(titre, description, currentUser, priorite);

            if (newTicket != null) {                
                // Assigner un utilisateur si sélectionné et si le user courant peut assigner des tickets
                if (utilisateurAssigne != null && currentUser.canAssignTickets()) {
                    ticketManager.assignTicket(newTicket.getTicketID(), utilisateurAssigne, currentUser);
                    // Si un statut autre que "ASSIGNÉ" est sélectionné, avertir l'utilisateur qu'il sera ignoré
                    if (statutBox.getSelectedItem() != "ASSIGNÉ") {
                        JOptionPane.showMessageDialog(this, 
                            "Le statut initial d'un nouveau ticket avec utilisateur assigné est toujours 'ASSIGNÉ'. Le statut sélectionné sera ignoré.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else if (utilisateurAssigne != null && !currentUser.canAssignTickets()) {
                    // Avertir que l'utilisateur n'a pas les droits pour assigner des tickets
                    JOptionPane.showMessageDialog(this, 
                        "Vous n'avez pas les droits nécessaires pour assigner un utilisateur au ticket. Aucun utilisateur ne sera assigné et le statut sera 'OUVERT'.", 
                        "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
                    // Aucun utilisateur ne sera assigné et le commentaire sera ignoré, avertir l'utilisateur
                    JOptionPane.showMessageDialog(this, "Aucun utilisateur assigné au ticket. Aucun commentaire ne sera ajouté.", 
                        "Assignation", JOptionPane.INFORMATION_MESSAGE);
                    commentaire = "";
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

                // Ajouter les images si il y en a
                if (!tempImagesPath.isEmpty()) {
                    for (String path : tempImagesPath) {
                        ticketManager.addImageToTicketDescription(newTicket.getTicketID(), path);
                    }
                    tempImagesPath.clear(); // Libération de la liste d'images temporaires pour ne pas garder les images d'un ticket à un autre ou les ajouter plusieurs fois.
                }

                // Ajouter les vidéos si il y en a
                if (!tempVideosPath.isEmpty()) {
                    for (String path : tempVideosPath) {
                        ticketManager.addVideoToTicketDescription(newTicket.getTicketID(), path);
                    }
                    tempVideosPath.clear(); // Libération de la liste de vidéos temporaires pour ne pas garder les vidéos d'un ticket à un autre ou les ajouter plusieurs fois.
                }

                // Ajouter un commentaire si fourni
                if (!commentaire.isEmpty()) {
                    ticketManager.addCommentToTicket(newTicket.getTicketID(), commentaire, currentUser);
                }

                // Afficher un message de succès de création
                JOptionPane.showMessageDialog(this, 
                    "Ticket #" + newTicket.getTicketID() + " créé avec succès !", "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            
                // Rafraîchir la liste des tickets
                filtrerTickets();
                
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

    // Méthode pour modifier un ticket
    private void modifierTicket() {
        try {
            // Si aucun ticket n'est sélectionné, afficher un message d'erreur
            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ticket à modifier !", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Récupération des champs
            String description = descriptionArea.getText().trim(); // Description du ticket
            String commentaire = commentArea.getText().trim(); // Commentaire du ticket
            String statut = (String) statutBox.getSelectedItem(); // Statut du ticket
            String priorite = (String) prioriteBox.getSelectedItem(); // Priorité du ticket
            User utilisateurAssigne = (User) assignatedUserBox.getSelectedItem(); // Utilisateur assigné au ticket

            // Vérifier qu'un utilisateur est sélectionné
            if (currentUser == null) {
                // Afficher un message d'erreur si aucun utilisateur n'est sélectionné.
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur qui peut modifier le ticket !", 
                    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (currentUser.getUserID() != selectedTicket.getAssignedUserId()) {
                if (currentUser.canAssignTickets()) {
                    // Un admin et un dev peuvent modifier/assigner n'importe quel ticket
                } else {
                    // Afficher un message d'erreur si l'utilisateur n'a pas les droits nécessaires.
                    JOptionPane.showMessageDialog(this, 
                        "Vous n'avez pas les droits nécessaires pour modifier ce ticket !", 
                        "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Modification : le titre n'est pas modifiable
            if (!titreField.getText().trim().equals(selectedTicket.getTitle())) {
                // Avertir que le titre ne peut pas et ne sera pas modifié
                JOptionPane.showMessageDialog(this, 
                    "Le titre d'un ticket ne peut pas être modifié. Le champ du titre sera ignoré.", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }

            boolean success; // Variable pour suivre le succès des opérations

            // Mettre à jour l'utilisateur assigné si le champ n'est pas null
            if (utilisateurAssigne != null) {
                if (utilisateurAssigne.getUserID() != selectedTicket.getAssignedUserId()) {
                    if (!currentUser.canAssignTickets()) {
                        // Avertir que l'utilisateur n'a pas les droits pour assigner des tickets
                        JOptionPane.showMessageDialog(this, 
                            "Vous n'avez pas les droits nécessaires pour assigner un utilisateur à ce ticket.", 
                            "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    // Mettre à jour l'utilisateur assigné via le ticketManager
                    success = ticketManager.assignTicket(selectedTicket.getTicketID(), utilisateurAssigne, currentUser);
                    if (!success) {
                        // Afficher un message d'erreur si la mise à jour de l'utilisateur assigné a échoué
                        JOptionPane.showMessageDialog(this, 
                            "Erreur lors de la mise à jour de l'utilisateur assigné.", 
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else {
                        // Si l'utilisateur assigné a changé, le statut devient automatiquement "ASSIGNÉ", avertir l'utilisateur
                        JOptionPane.showMessageDialog(this, 
                            "L'utilisateur assigné a été modifié. Le statut du ticket est automatiquement mis à jour à 'ASSIGNÉ'.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (statut != selectedTicket.getStatus()) { // Mettre à jour le statut seulement si l'utilisateur assigné n'a pas changé (un ticket assigné change automatiquement de statut).
                    if (statut == "TERMINÉ" && !currentUser.canCloseTickets()) {
                        // Avertir que l'utilisateur n'a pas les droits pour fermer le ticket
                        JOptionPane.showMessageDialog(this, 
                            "Vous n'avez pas les droits nécessaires pour fermer ce ticket.", 
                            "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    // Mettre à jour le statut via le ticketManager
                    success = ticketManager.updateTicketStatus(selectedTicket.getTicketID(), statut, currentUser);
                    if (!success) {
                        // Afficher un message d'erreur si la mise à jour du statut a échoué
                        JOptionPane.showMessageDialog(this, 
                            "Erreur lors de la mise à jour du statut.", 
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            else { //Si le champ utilisateurAssigne est null, il faut prévenir que le statut et commentaires ne seront pas pris en compte
                JOptionPane.showMessageDialog(this, 
                "Aucun utilisateur assigné. Le statut du ticket restera à 'OUVERT' et aucun commentaire ne sera ajouté.", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
                commentaire = "";
            }
            

            // Mettre à jour la description
            if (!description.isEmpty() && !description.equals(descManager.getDescriptionSummary(selectedTicket.getDescription()))) {
                // Mettre à jour la description via le ticketManager
                success = ticketManager.updateTicketDescription(selectedTicket.getTicketID(), description);
                if (!success) {
                    // Afficher un message d'erreur si la mise à jour a échoué
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de la mise à jour de la description.", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Ajouter les images si il y en a
            if (!tempImagesPath.isEmpty()) {
                for (String path : tempImagesPath) {
                    ticketManager.addImageToTicketDescription(selectedTicket.getTicketID(), path);
                }
                tempImagesPath.clear(); // Libération de la liste d'images temporaires pour ne pas garder les images d'un ticket à un autre ou les ajouter plusieurs fois.
            }

            // Ajouter les vidéos si il y en a
            if (!tempVideosPath.isEmpty()) {
                for (String path : tempVideosPath) {
                    ticketManager.addVideoToTicketDescription(selectedTicket.getTicketID(), path);
                }
                tempVideosPath.clear(); // Libération de la liste de vidéos temporaires pour ne pas garder les vidéos d'un ticket à un autre ou les ajouter plusieurs fois.
            }

            // Ajouter un commentaire si fourni
            if (!commentaire.isEmpty()) {
                // Ajouter le commentaire via le ticketManager
                success = ticketManager.addCommentToTicket(selectedTicket.getTicketID(), commentaire, currentUser);
                if (!success) {
                    // Afficher un message d'erreur si l'ajout du commentaire a échoué
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de l'ajout du commentaire.", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Mettre à jour la priorité
            if (priorite != selectedTicket.getPriority()) {
                if (priorityManager.canUserChangePriority(currentUser, selectedTicket)) { // SI l'utilisateur peut changer la priorité
                    // Mettre à jour la priorité via le ticketManager
                    success = ticketManager.updateTicketPriority(selectedTicket.getTicketID(), priorite, currentUser);
                    if (!success) {
                        // Afficher un message d'erreur si la mise à jour de la priorité a échoué
                        JOptionPane.showMessageDialog(this, 
                            "Erreur lors de la mise à jour de la priorité.", 
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else {
                    // Avertir que l'utilisateur n'a pas les droits pour changer la priorité de ce ticket
                    JOptionPane.showMessageDialog(this, 
                        "Vous n'avez pas les droits nécessaires pour changer la priorité de ce ticket.", 
                        "Droits insuffisants", JOptionPane.WARNING_MESSAGE);
                }
            }

            // Si tout a réussi, afficher un message de succès
            JOptionPane.showMessageDialog(this, 
                    "Ticket #" + selectedTicket.getTicketID() + " modifié avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);

            // Rafraîchir la liste des tickets
            filtrerTickets();

            // Vider le formulaire
            viderFormulaireTicket();
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la création/modification du ticket : " + ex.getMessage(), "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exporterTicketPDF() {
        try {
            // SI aucun ticket sélectionné, afficher un message d'erreur
            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ticket l'exporter en PDF !", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // L'utilisateur doit choisir l'emplacement et le nom de son fichier pdf
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Enregistrer le ticket en PDF");
            fileChooser.setSelectedFile(new File("ticket_" + selectedTicket.getTicketID() + ".pdf"));
            int result = fileChooser.showSaveDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) return; // Si l'utilisateur n'approuve pas, on annule

            // Récuperer l'id de l'utilisateur assigné
            int assignedUserId = selectedTicket.getAssignedUserId();
            User assignatedUser = null; // SI l'utilisateur assigné est trouvé, ça donnera un utilisateur, sinon on enverra null.

            // Parcourir la liste des utilisateurs pour trouver l'utilisateur assigné s'il existe et récuperer l'objet user
            for (User user : allUsers) {
                if (user.getUserID() == assignedUserId) {
                    assignatedUser = user;
                    break;
                }
            }

            // Export en pdf du ticket avec le chemin choisi
            ticketManager.exportTicketToPDF(selectedTicket.getTicketID(), fileChooser.getSelectedFile().getAbsolutePath(), assignatedUser);

        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de l'export en PDF : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour ajouter une image à la description du ticket
    private void ajouterImage() {
        try {
            // Filechooser pour choisir l'image.
            JFileChooser fileChooser = new JFileChooser();
            // S'assurer que le FileChooser démarre dans le répertoire utilisateur (pas dans /media)
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);

            // Si le résultat est l'approbation, on peut récuperer le fichier.
            if (result == JFileChooser.APPROVE_OPTION) {
                // Ajouter le chemin de l'image choisi dans la liste de chemin temporaire d'images
                tempImagesPath.add(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout d'image : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showImages() {
        // SI aucun ticket sélectionné, afficher un message d'erreur
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ticket pour afficher les images !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Création du panneau qui affichera les images
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout());

            // Pour tous les images dans la description du ticket, les récuperer et les ajouter au paneau
            for (String path : selectedTicket.getDescription().getImagePaths()) {
                ImageIcon icon = new ImageIcon(path);
                JLabel label = new JLabel(icon);
                panel.add(label);
            }

            // Ajouter le panel (le rendre scrollable) à un JFrame pour afficher les images
            JFrame frame = new JFrame("Images dans la description");
            frame.add(new JScrollPane(panel)); // scrollable si trop d'images
            
            // Rendre la fenêtre responsive - adapter à la taille de l'écran
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int maxWidth = (int) (screenSize.width * 0.9); // 90% de la largeur de l'écran
            int maxHeight = (int) (screenSize.height * 0.9); // 90% de la hauteur de l'écran
            frame.setSize(Math.min(maxWidth, 1200), Math.min(maxHeight, 800));
            frame.setLocationRelativeTo(null); // Centrer la fenêtre
            frame.setVisible(true);
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de l'affichage d'images : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour ajouter une vidéo à la description du ticket
    private void ajouterVideo() {
        try {
            // Filechooser pour choisir la vidéo.
            JFileChooser fileChooser = new JFileChooser();
            // S'assurer que le FileChooser démarre dans le répertoire utilisateur (pas dans /media)
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);

            // Si le résultat est l'approbation, on peut récuperer le fichier.
            if (result == JFileChooser.APPROVE_OPTION) {
                // Ajouter le chemin de la vidéo choisie dans la liste de chemin temporaire de vidéos
                tempVideosPath.add(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la vidéo : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showVideos() {
        // SI aucun ticket sélectionné, afficher un message d'erreur
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ticket pour afficher les vidéos !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Création du panneau qui affichera les images
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout());

            int i = 0; // Variable pour compter les vidéos
            // Pour tous les images dans la description du ticket, les récuperer et les ajouter au paneau
            for (String path : selectedTicket.getDescription().getVideoPaths()) {
                // Ajout d'un boutton play et d'un listener pour faire jouer la vidéo (avec une autre fonction).
                JButton play = new JButton("▶ Play vidéo " + (i + 1));
                panel.add(play);
                play.addActionListener(e -> lancerVideo(path));
                i++;
            }

            // Ajouter le panel (le rendre scrollable) à un JFrame pour afficher les vidéos
            JFrame frame = new JFrame("Vidéos dans la description");
            frame.add(new JScrollPane(panel)); // scrollable si trop d'images
            
            // Rendre la fenêtre responsive - adapter à la taille de l'écran
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int maxWidth = (int) (screenSize.width * 0.9); // 90% de la largeur de l'écran
            int maxHeight = (int) (screenSize.height * 0.9); // 90% de la hauteur de l'écran
            frame.setSize(Math.min(maxWidth, 800), Math.min(maxHeight, 600));
            frame.setLocationRelativeTo(null); // Centrer la fenêtre
            frame.setVisible(true);
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de l'affichage des vidéos : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lancerVideo(String path) {
        try {
            Desktop.getDesktop().open(new File(path)); // Lance la vidéo avec VLC / Windows Media Player
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de la lecture de la vidéo : " + path + " error : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour désassigner l'utilisateur d'un ticket
    private void desassignerUtilisateur() {
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
            filtrerTickets(); // Rafraîchir la liste des tickets pour refléter le changement
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

    // Méthode pour supprimer un utilisateur
    private void supprimerUtilisateur() {
        // Confirmer la suppression
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cet utilisateur ? CETTE ACTION EST IRRÉVERSIBLE.", 
            "Confirmer la suppression ?", 
            JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return; // Annuler la suppression si l'utilisateur choisit "Non"
        }

        try {
            // Si aucun utilisateur n'est sélectionné, afficher un message d'erreur
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer !", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (currentUser.getUserID() == 0) {
                // Empêcher la suppression de l'utilisateur admin par défaut
                JOptionPane.showMessageDialog(this, 
                    "L'utilisateur administrateur par défaut ne peut pas être supprimé.", "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Désassigner l'utilisateur de tous les tickets qui lui sont assignés
            if (!ticketManager.getTicketsByUser(currentUser).isEmpty()) {
                for (Ticket t : ticketManager.getTicketsByUser(currentUser)) {
                    ticketManager.unassignTicket(t.getTicketID(), allUsers.get(0));
                }
                filtrerTickets(); // Rafraîchir la liste des tickets pour refléter les changements
            }
            
            // Supprimer l'utilisateur de la liste
            allUsers.remove(currentUser);

            // Rafraîchir la liste des utilisateurs
            rafraichirListeUtilisateurs();

            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, 
                "Utilisateur supprimé avec succès !\nNom : " + currentUser.getName() + "\nID : " + currentUser.getUserID(), 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'utilisateur : " + ex.getMessage(), 
            "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ************************************************************************************** //
    
    // ************************ Méthodes de style pour les composants ************************ //
    /**
     * Applique un style moderne aux boutons
     */
    private void styleButton(JButton button) {
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, button.getFont().getSize()));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Supprimer la bordure focus
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(8, 16, 8, 16)); // Padding (8,16,8,16)
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }
    
    /**
     * Applique un style moderne aux labels de titre
     */
    private void styleTitleLabel(JLabel label) {
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
    }
    
    /**
     * Applique un style moderne aux champs de texte
     */
    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(5, 5, 5, 5) // Padding interne de 5px
        ));
    }
    
    /**
     * Applique un style moderne aux zones de texte
     */
    private void styleTextArea(JTextArea textArea) {
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            new EmptyBorder(5, 5, 5, 5) // Padding interne de 5px
        ));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }
    
    /**
     * Applique un style moderne aux listes (JList)
     */
    private void styleList(JList<?> list) {
        list.setFixedCellHeight(30); // Hauteur de ligne 30px
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(PRIMARY_COLOR);
        list.setSelectionForeground(Color.WHITE);
        list.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    // ************************************************************************************** //
}