import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
    private JTextArea otherInfoArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<User> assignatedUserBox;
    private JComboBox<String> userTypeBox;
    private JComboBox<String> filterStatusBox;
    private JButton saveButton;
    private JButton createButton;
    private JButton exportPDFButton;
    private JButton createUserButton;
    private JButton deleteUserButton;
    private JButton desassignButton;
    private JButton addImageButton;
    private JButton addVideoButton;
    private JPanel formPanel;
    private JPanel ticketPanel;
    private JPanel userPanel;
    private JPanel mediaPanel;
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
        otherInfoArea = new JTextArea(); // Zone de texte pour d'autres informations
        otherInfoArea.setEditable(false); // Les autres informations ne sont pas éditables
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
        addImageButton = new JButton("Ajouter Image"); // Bouton pour ajouter une image
        addVideoButton = new JButton("Ajouter Vidéo"); // Bouton pour ajouter une vidéo
        
        // Panneau pour les médias (images/vidéos)
        mediaPanel = new JPanel();
        mediaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mediaPanel.setBorder(BorderFactory.createTitledBorder("Images et Vidéos"));
        
        // Panneau de creation/modification
        formPanel = new JPanel(new GridLayout(13, 1)); // Panneau de formulaire pour créer/modifier un ticket
        formPanel.add(new JLabel("Titre :")); // Étiquette pour le titre
        formPanel.add(titreField); // Ajout du champ de titre
        formPanel.add(new JLabel("Informations sur le ticket :")); // Étiquette pour les autres informations
        formPanel.add(new JScrollPane(otherInfoArea)); // Ajout de la zone de texte pour les autres informations
        formPanel.add(new JLabel("Description :")); // Étiquette pour la description
        formPanel.add(new JScrollPane(descriptionArea)); // Ajout de la zone de texte pour la description
        
        // Panneau de prévisualisation des médias (sans les boutons)
        formPanel.add(new JLabel("Images et Vidéos :")); // Étiquette pour la section médias
        formPanel.add(new JScrollPane(mediaPanel)); // Ajout du panneau de prévisualisation
        
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
        
        // Panneau pour les boutons d'ajout de médias (déplacé en bas)
        JPanel mediaBtnPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        mediaBtnPanel.add(addImageButton);
        mediaBtnPanel.add(addVideoButton);
        formPanel.add(mediaBtnPanel); // Ajout des boutons d'ajout de médias
        
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
        // Panneau des utilisateurs
        userPanel = new JPanel(new GridLayout(4, 1)); // Panneau pour la gestion des utilisateurs
        userPanel.add(new JLabel("Rôle utilisateur :")); // Étiquette pour le rôle utilisateur
        userPanel.add(userTypeBox); // Ajout de la liste déroulante pour le rôle utilisateur
        userPanel.add(new JLabel("Nom d'utilisateur :")); // Étiquette pour le nom d'utilisateur
        userPanel.add(userNameField); // Champ de texte pour le nom d'utilisateur
        userPanel.add(new JLabel("Email :")); // Étiquette pour l'email
        userPanel.add(userEmailField); // Champ de texte pour l'email
        userPanel.add(createUserButton); // Ajout du bouton de création d'utilisateur
        userPanel.add(deleteUserButton); // Ajout du bouton de suppression d'utilisateur

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
        saveButton.addActionListener(e -> modifierTicket());

        // Événement pour le bouton "Exporter en PDF"
        exportPDFButton.addActionListener(e -> exporterTicketPDF());

        // Événement pour le bouton "Désassigner"
        desassignButton.addActionListener(e -> desassignerUtilisateur());

        // Événement pour le bouton "Créer" d'utilisateur
        createUserButton.addActionListener(e -> creerUtilisateur());

        // Événement pour le bouton "Supprimer" d'utilisateur
        deleteUserButton.addActionListener(e -> supprimerUtilisateur());

        // Événement pour le bouton "Ajouter Image"
        addImageButton.addActionListener(e -> ajouterImage());

        // Événement pour le bouton "Ajouter Vidéo"
        addVideoButton.addActionListener(e -> ajouterVideo());

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
            currentUser = (User) userList.getSelectedValue();
            rafraichirListeTickets(); // Rafraîchir la liste des tickets affichés
            viderFormulaireTicket(); // Vider le formulaire de ticket
        });

        // Événement pour le filtre par statut
        filterStatusBox.addActionListener(e -> filtrerTicketsStatut());
    }
    // ********************************************************************************* //

    // ************************ Méthodes de gestion des tickets ************************ //
    // Méthode pour rafraîchir la liste des tickets affichés
    private void rafraichirListeTickets() {
        // Si un utilisateur est sélectionné, autre qu'un admin ou un dev (peut assigner des tickets), obtenir la liste de tickets pour cet utilisateur
        if (!currentUser.canAssignTickets() && currentUser != null) {
            filtrerTicketsUser();
            return;
        }
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0])); // Afficher tous les tickets
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
        viderMediaPanel(); // Vider le panneau des médias
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
        
        // Rafraîchir l'affichage des médias (images et vidéos)
        rafraichirMediaPanel();
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
                    if (!"ASSIGNÉ".equals(statutBox.getSelectedItem())) {
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
                    if (!"OUVERT".equals(statutBox.getSelectedItem())) {
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
                    ticketManager.addCommentToTicket(newTicket.getTicketID(), commentaire, currentUser);
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

            // Mettre à jour l'utilisateur assigné
            if (utilisateurAssigne != null && utilisateurAssigne.getUserID() != selectedTicket.getAssignedUserId()) {
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
            } else if (!statut.equals(selectedTicket.getStatus())) { // Mettre à jour le statut seulement si l'utilisateur assigné n'a pas changé (un ticket assigné change automatiquement de statut).
                if (statut.equals("TERMINÉ") && !currentUser.canCloseTickets()) {
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
            if (!priorite.equals(selectedTicket.getPriority())) {
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

            // Si tout a réussi, afficher un message de succès
            JOptionPane.showMessageDialog(this, 
                    "Ticket #" + selectedTicket.getTicketID() + " modifié avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);

            // Rafraîchir la liste des tickets
            rafraichirListeTickets();

            // Vider le formulaire
            viderFormulaireTicket();
        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la création/modification du ticket : " + ex.getMessage(), "Erreur", 
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
        ticketList.setListData(ticketManager.getTicketsByUser(currentUser).toArray(new Ticket[0])); // Met à jour la liste des tickets affichés selon le user connecté
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

        // SI 
        if (!currentUser.canAssignTickets()) {

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
                JOptionPane.showMessageDialog(this, 
                    "Aucun utilisateur sélectionné\n\n" +
                    "Veuillez d'abord sélectionner un utilisateur dans la liste en haut,\n" +
                    "puis cliquer sur \"Supprimer l'utilisateur\".", 
                    "Sélection requise", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (currentUser.getUserID() == 0) {
                // Empêcher la suppression de l'utilisateur admin par défaut
                JOptionPane.showMessageDialog(this, 
                    " Suppression interdite\n\n" +
                    "L'utilisateur administrateur système (SYS ADMIN) ne peut pas être supprimé.\n", 
                    "Utilisateur protégé", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Sauvegarder les informations avant suppression
            String userName = currentUser.getName();
            int userID = currentUser.getUserID();
            int ticketCount = ticketManager.getTicketsByUser(currentUser).size();

            // Désassigner l'utilisateur de tous les tickets qui lui sont assignés
            if (ticketCount > 0) {
                for (Ticket t : ticketManager.getTicketsByUser(currentUser)) {
                    ticketManager.unassignTicket(t.getTicketID(), allUsers.get(0));
                }
                rafraichirListeTickets(); // Rafraîchir la liste des tickets pour refléter les changements
            }
            
            // Supprimer l'utilisateur de la liste
            allUsers.remove(currentUser);
            currentUser = null; // Réinitialiser currentUser après suppression

            // Rafraîchir la liste des utilisateurs
            rafraichirListeUtilisateurs();

            // Afficher un message de succès
            String message = " Utilisateur supprimé avec succès !\n\n" +
                           "Nom : " + userName + "\n" +
                           "ID : " + userID;
            if (ticketCount > 0) {
                message += "\n\n" + ticketCount + " ticket(s) ont été désassignés et remis à l'état OUVERT.";
            }
            JOptionPane.showMessageDialog(this, message, "Suppression réussie", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            // Afficher un message d'erreur en cas d'exception d'exécution
            JOptionPane.showMessageDialog(this, 
                "❌ Erreur lors de la suppression\n\n" +
                "Une erreur inattendue s'est produite :\n" + ex.getMessage() + "\n\n" +
                "Veuillez réessayer ou contacter le support technique.", 
                "Erreur système", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    // ************************************************************************************** //

    // ************************ Méthodes de gestion des médias (images/vidéos) ************************ //
    /**
     * Méthode pour ajouter une image au ticket sélectionné
     * Ouvre un dialogue de sélection de fichier, copie l'image dans le dossier media/images/
     * et l'ajoute à la description du ticket
     */
    private void ajouterImage() {
        // Vérifier qu'un ticket est sélectionné
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un ticket pour ajouter une image !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer un sélecteur de fichiers avec filtre pour les images
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images (JPG, JPEG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        // Afficher le dialogue et vérifier si l'utilisateur a sélectionné un fichier
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Copier le fichier vers le dossier media/images/
            String newPath = copierFichierVersMedia(selectedFile, "images");
            
            if (newPath != null) {
                // Ajouter l'image à la description du ticket
                boolean success = ticketManager.addImageToTicketDescription(
                    selectedTicket.getTicketID(), newPath);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Image ajoutée avec succès !", 
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Rafraîchir l'affichage des médias
                    rafraichirMediaPanel();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de l'ajout de l'image à la description.", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Méthode pour ajouter une vidéo au ticket sélectionné
     * Ouvre un dialogue de sélection de fichier, copie la vidéo dans le dossier media/videos/
     * et l'ajoute à la description du ticket
     */
    private void ajouterVideo() {
        // Vérifier qu'un ticket est sélectionné
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un ticket pour ajouter une vidéo !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer un sélecteur de fichiers avec filtre pour les vidéos
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sélectionner une vidéo");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Vidéos (MP4, AVI, MOV)", "mp4", "avi", "mov");
        fileChooser.setFileFilter(filter);

        // Afficher le dialogue et vérifier si l'utilisateur a sélectionné un fichier
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Copier le fichier vers le dossier media/videos/
            String newPath = copierFichierVersMedia(selectedFile, "videos");
            
            if (newPath != null) {
                // Ajouter la vidéo à la description du ticket
                boolean success = ticketManager.addVideoToTicketDescription(
                    selectedTicket.getTicketID(), newPath);
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Vidéo ajoutée avec succès !", 
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Rafraîchir l'affichage des médias
                    rafraichirMediaPanel();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de l'ajout de la vidéo à la description.", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Copie un fichier vers le dossier media/ du projet
     * @param fichierSource Le fichier source à copier
     * @param sousRepertoire Le sous-répertoire dans media/ (images ou videos)
     * @return Le chemin relatif du fichier copié, ou null en cas d'erreur
     */
    private String copierFichierVersMedia(File fichierSource, String sousRepertoire) {
        try {
            // Créer le répertoire de destination s'il n'existe pas
            Path dossierMedia = Paths.get("media", sousRepertoire);
            Files.createDirectories(dossierMedia);

            // Générer un nom unique pour éviter les écrasements
            String nomFichier = fichierSource.getName();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String extension = nomFichier.substring(nomFichier.lastIndexOf("."));
            String nomUnique = nomFichier.substring(0, nomFichier.lastIndexOf(".")) + "_" + timestamp + extension;

            // Copier le fichier
            Path destination = dossierMedia.resolve(nomUnique);
            Files.copy(fichierSource.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Retourner le chemin relatif
            String cheminRelatif = "media/" + sousRepertoire + "/" + nomUnique;
            System.out.println("Fichier copié vers: " + cheminRelatif);
            return cheminRelatif;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la copie du fichier : " + e.getMessage(), 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Rafraîchit le panneau d'affichage des médias avec les images et vidéos du ticket sélectionné
     * Affiche les miniatures des images et des icônes pour les vidéos
     */
    private void rafraichirMediaPanel() {
        // Vider le panneau
        mediaPanel.removeAll();

        if (selectedTicket == null) {
            mediaPanel.revalidate();
            mediaPanel.repaint();
            return;
        }

        Description description = selectedTicket.getDescription();
        
        // Afficher les images avec miniatures
        List<String> imagePaths = description.getImagePaths();
        for (String imagePath : imagePaths) {
            try {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    // Charger et redimensionner l'image
                    BufferedImage img = ImageIO.read(imageFile);
                    if (img != null) {
                        // Créer une miniature (100x100)
                        Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaledImg);
                        
                        // Créer un panneau pour chaque image
                        JPanel imagePanel = new JPanel();
                        imagePanel.setLayout(new BorderLayout());
                        JLabel imageLabel = new JLabel(icon);
                        imageLabel.setToolTipText(imagePath);
                        imagePanel.add(imageLabel, BorderLayout.CENTER);
                        
                        // Ajouter le nom du fichier sous l'image
                        String fileName = imageFile.getName();
                        if (fileName.length() > 15) {
                            fileName = fileName.substring(0, 12) + "...";
                        }
                        JLabel nameLabel = new JLabel(fileName, SwingConstants.CENTER);
                        nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                        imagePanel.add(nameLabel, BorderLayout.SOUTH);
                        
                        mediaPanel.add(imagePanel);
                    }
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image: " + imagePath);
            }
        }

        // Afficher les vidéos avec icônes
        List<String> videoPaths = description.getVideoPaths();
        for (String videoPath : videoPaths) {
            File videoFile = new File(videoPath);
            if (videoFile.exists()) {
                // Créer un panneau pour chaque vidéo avec une icône
                JPanel videoPanel = new JPanel();
                videoPanel.setLayout(new BorderLayout());
                videoPanel.setPreferredSize(new Dimension(100, 120));
                
                // Icône vidéo (symbole play)
                JLabel videoIcon = new JLabel("▶", SwingConstants.CENTER);
                videoIcon.setFont(new Font("Arial", Font.BOLD, 50));
                videoIcon.setForeground(Color.BLUE);
                videoIcon.setToolTipText(videoPath);
                videoPanel.add(videoIcon, BorderLayout.CENTER);
                
                // Nom du fichier
                String fileName = videoFile.getName();
                if (fileName.length() > 15) {
                    fileName = fileName.substring(0, 12) + "...";
                }
                JLabel nameLabel = new JLabel(fileName, SwingConstants.CENTER);
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                videoPanel.add(nameLabel, BorderLayout.SOUTH);
                
                mediaPanel.add(videoPanel);
            }
        }

        // Rafraîchir l'affichage
        mediaPanel.revalidate();
        mediaPanel.repaint();
    }

    /**
     * Vide le panneau d'affichage des médias
     */
    private void viderMediaPanel() {
        mediaPanel.removeAll();
        mediaPanel.revalidate();
        mediaPanel.repaint();
    }
    // ************************************************************************************** //

    // ************************ Méthode d'export PDF ************************ //
    /**
     * Exporte le ticket sélectionné en PDF
     * Demande à l'utilisateur où sauvegarder le fichier PDF
     */
    private void exporterTicketPDF() {
        // Vérifier qu'un ticket est sélectionné
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un ticket à exporter !", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créer un dialogue de sauvegarde
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le PDF");
        fileChooser.setSelectedFile(new File("Ticket_" + selectedTicket.getTicketID() + ".pdf"));
        
        // Filtre pour les fichiers PDF
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers PDF (*.pdf)", "pdf");
        fileChooser.setFileFilter(filter);

        // Afficher le dialogue et vérifier si l'utilisateur a choisi un emplacement
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            // S'assurer que le fichier a l'extension .pdf
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Exporter le ticket en PDF via le TicketManager
            boolean success = ticketManager.exportTicketToPDF(selectedTicket.getTicketID(), filePath);

            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Ticket exporté avec succès !\nEmplacement : " + filePath, 
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'export du PDF.\nAssurez-vous que la bibliothèque iText est installée.\nConsultez INSTALLATION_ITEXT.md pour plus d'informations.", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // ********************************************************************** //
}