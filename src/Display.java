import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame{ // Classe pour l'affichage des tickets et interface GUI
    // Utilisateurs
    private List<User> allUsers;

    // Format de date
    private SimpleDateFormat dateFormat;

    // Composants GUI
    private JList<Ticket> ticketList;
    private JList<User> userList;
    private JTextField titreField;
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextArea descriptionArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<String> userTypeBox;
    private JButton saveButton;
    private JButton createUserButton;
    private JPanel formPanel;
    private JPanel userPanel;
    private JScrollPane affichageTickets;
    private JScrollPane affichageUtilisateurs;

    // Nouveaux composants pour fonctionnalités avancées
    private JComboBox<User> connectedUserBox; // Utilisateur connecté
    private JComboBox<String> filterStatusBox; // Filtre par statut
    private JPanel actionsPanel; // Panel d'actions dynamiques
    private JTextArea historiqueArea; // Historique des modifications
    private JPanel statsPanel; // Panel de statistiques
    private JLabel statsTotal, statsOuvert, statsAssigne, statsValidation, statsTermine, statsFerme;
    
    // Utilisateur actuellement connecté
    private User currentUser;

    // Gestionnaires
    private TicketManager ticketManager;
    private descriptionManager descManager;
    private statusManager statusManager;
    private PriorityManager priorityManager;
    private TicketCreator ticketCreator;
    private UserCreator userCreator;
    
    // Validateurs
    private ticketValidator ticketValidator;
    private userValidator userValidator;

    // Constructeur
    public Display() {
        // Configuration de la fenêtre
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Format de date
        setTitle("Programme de gestion des tickets"); // Titre de la fenêtre
        setSize(1350, 650); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Action à la fermeture

        // Initialisation des gestionnaires
        ticketManager = new TicketManager(this); // Création du ticketManager avec liaison à Display
        descManager = ticketManager.getDescriptionManager(); // Récupération du descriptionManager
        statusManager = ticketManager.getStatusManager(); // Récupération du statusManager
        priorityManager = ticketManager.getPriorityManager(); // Récupération du priorityManager
        ticketCreator = new TicketCreator(0, ticketManager); // ticketCreator sera relié avec ticketManager
        userCreator = new UserCreator(); // Création du userCreator
        
        // Initialisation des validateurs
        ticketValidator = new ticketValidator(); // Validateur de tickets
        userValidator = new userValidator(); // Validateur d'utilisateurs

        // Initialisation de la liste des utilisateurs
        allUsers = new ArrayList<>(List.of(new User(0, "SYS ADMIN", "SYSTEM@EXAMPLE.COM", "ADMIN"))); /*Initialisation de la liste
        des utilisateurs avec utilisateur système par défaut*/
        
        // Définir l'utilisateur connecté par défaut (admin système)
        currentUser = allUsers.get(0);

        // 1 : Initialiser les composants GUI
        initComponents();

        // 2 : Écouter les événements
        initListeners();

        // 3 : Rendre la fenêtre visible
        setVisible(true);
    }

    private void initComponents() {
        // Configuration principale de la fenêtre
        setLayout(new BorderLayout(10, 10));
        
        // ============================================================
        // PANEL TOUT EN HAUT : Utilisateur connecté + Statistiques
        // ============================================================
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        // Utilisateur connecté
        JPanel userConnectedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userConnectedPanel.add(new JLabel("👤 Utilisateur connecté : "));
        connectedUserBox = new JComboBox<>(allUsers.toArray(new User[0]));
        connectedUserBox.setSelectedItem(currentUser);
        connectedUserBox.setFont(new Font("Arial", Font.BOLD, 12));
        connectedUserBox.setPreferredSize(new Dimension(250, 30));
        userConnectedPanel.add(connectedUserBox);
        topPanel.add(userConnectedPanel, BorderLayout.WEST);
        
        // Panel de statistiques
        statsPanel = createStatsPanel();
        topPanel.add(statsPanel, BorderLayout.EAST);
        
        // ============================================================
        // PANEL SUPÉRIEUR : Sélection d'utilisateur
        // ============================================================
        userList = new JList<>(allUsers.toArray(new User[0]));
        userList.setFont(new Font("Arial", Font.PLAIN, 12));
        affichageUtilisateurs = new JScrollPane(userList);
        affichageUtilisateurs.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Sélectionner l'utilisateur créateur",
            0, 0, new Font("Arial", Font.BOLD, 13)));
        affichageUtilisateurs.setPreferredSize(new Dimension(0, 150));
        
        // ============================================================
        // PANEL GAUCHE : Filtre + Liste des tickets
        // ============================================================
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Filtre par statut
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filtre : "));
        filterStatusBox = new JComboBox<>(new String[]{
            "Tous les tickets", "OUVERT", "ASSIGNÉ", "VALIDATION", "TERMINÉ", "FERMÉ"
        });
        filterStatusBox.setFont(new Font("Arial", Font.PLAIN, 11));
        filterStatusBox.setPreferredSize(new Dimension(250, 30));
        filterPanel.add(filterStatusBox);
        leftPanel.add(filterPanel, BorderLayout.NORTH);
        
        // Liste des tickets
        ticketList = new JList<>();
        ticketList.setListData(ticketManager.getAllTickets().toArray(new Ticket[0]));
        ticketList.setFont(new Font("Arial", Font.PLAIN, 12));
        affichageTickets = new JScrollPane(ticketList);
        affichageTickets.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
            "Liste des Tickets",
            0, 0, new Font("Arial", Font.BOLD, 13)));
        leftPanel.add(affichageTickets, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(320, 0));
        
        // ============================================================
        // PANEL CENTRAL : Formulaire de création/modification de ticket
        // ============================================================
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
                "Créer / Modifier un Ticket",
                0, 0, new Font("Arial", Font.BOLD, 14)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Ligne 1 : Titre
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        JLabel lblTitre = new JLabel("Titre :");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitre.setPreferredSize(new Dimension(120, 40));
        formPanel.add(lblTitre, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        titreField = new JTextField();
        titreField.setFont(new Font("Arial", Font.PLAIN, 12));
        titreField.setPreferredSize(new Dimension(0, 40));
        formPanel.add(titreField, gbc);
        
        // Ligne 2 : Description
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescription = new JLabel("Description :");
        lblDescription.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(lblDescription, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        descriptionArea = new JTextArea(8, 40);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new Dimension(0, 200));
        formPanel.add(descScrollPane, gbc);
        
        // Ligne 3 : Statut
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblStatut = new JLabel("Statut :");
        lblStatut.setFont(new Font("Arial", Font.BOLD, 12));
        lblStatut.setPreferredSize(new Dimension(120, 40));
        formPanel.add(lblStatut, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        statutBox = new JComboBox<>(statusManager.getValidStatuses().toArray(new String[0]));
        statutBox.setFont(new Font("Arial", Font.PLAIN, 12));
        statutBox.setPreferredSize(new Dimension(200, 40));
        formPanel.add(statutBox, gbc);
        
        // Ligne 4 : Priorité
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel lblPriorite = new JLabel("Priorité :");
        lblPriorite.setFont(new Font("Arial", Font.BOLD, 12));
        lblPriorite.setPreferredSize(new Dimension(120, 40));
        formPanel.add(lblPriorite, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        prioriteBox = new JComboBox<>(priorityManager.getValidPriorities().toArray(new String[0]));
        prioriteBox.setFont(new Font("Arial", Font.PLAIN, 12));
        prioriteBox.setPreferredSize(new Dimension(200, 40));
        formPanel.add(prioriteBox, gbc);
        
        // Ligne 5 : Bouton Créer/Modifier
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(20, 10, 10, 10);
        saveButton = new JButton("Créer / Modifier");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(180, 45));
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(saveButton, gbc);
        
        // Ligne 6 : Panel d'actions dynamiques (selon statut)
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
            "Actions disponibles",
            0, 0, new Font("Arial", Font.BOLD, 12)));
        actionsPanel.setPreferredSize(new Dimension(0, 80));
        formPanel.add(actionsPanel, gbc);
        
        // Ligne 7 : Historique des modifications
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        historiqueArea = new JTextArea(6, 40);
        historiqueArea.setEditable(false);
        historiqueArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        historiqueArea.setLineWrap(true);
        historiqueArea.setWrapStyleWord(true);
        JScrollPane historiqueScroll = new JScrollPane(historiqueArea);
        historiqueScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "📜 Historique des modifications",
            0, 0, new Font("Arial", Font.BOLD, 12)));
        historiqueScroll.setPreferredSize(new Dimension(0, 120));
        formPanel.add(historiqueScroll, gbc);
        
        // ============================================================
        // PANEL DROIT : Création d'utilisateur
        // ============================================================
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(186, 85, 211), 2),
                "Créer un nouvel utilisateur",
                0, 0, new Font("Arial", Font.BOLD, 13)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        userPanel.setPreferredSize(new Dimension(300, 0));
        
        // Rôle utilisateur
        JLabel lblRole = new JLabel("Rôle utilisateur :");
        lblRole.setFont(new Font("Arial", Font.BOLD, 12));
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(lblRole);
        userPanel.add(Box.createVerticalStrut(5));
        
        userTypeBox = new JComboBox<>(new String[]{"ADMIN", "DEVELOPER", "USER"});
        userTypeBox.setFont(new Font("Arial", Font.PLAIN, 12));
        userTypeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        userTypeBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(userTypeBox);
        userPanel.add(Box.createVerticalStrut(15));
        
        // Nom d'utilisateur
        JLabel lblNom = new JLabel("Nom d'utilisateur :");
        lblNom.setFont(new Font("Arial", Font.BOLD, 12));
        lblNom.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(lblNom);
        userPanel.add(Box.createVerticalStrut(5));
        
        userNameField = new JTextField();
        userNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        userNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        userNameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(userNameField);
        userPanel.add(Box.createVerticalStrut(15));
        
        // Email
        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(lblEmail);
        userPanel.add(Box.createVerticalStrut(5));
        
        userEmailField = new JTextField();
        userEmailField.setFont(new Font("Arial", Font.PLAIN, 12));
        userEmailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        userEmailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(userEmailField);
        userPanel.add(Box.createVerticalStrut(20));
        
        // Bouton Créer
        createUserButton = new JButton("Créer Utilisateur");
        createUserButton.setFont(new Font("Arial", Font.BOLD, 13));
        createUserButton.setPreferredSize(new Dimension(250, 40));
        createUserButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        createUserButton.setBackground(new Color(60, 179, 113));
        createUserButton.setForeground(Color.WHITE);
        createUserButton.setFocusPainted(false);
        createUserButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(createUserButton);
        userPanel.add(Box.createVerticalGlue());
        
        // ============================================================
        // ASSEMBLAGE FINAL
        // ============================================================
        // Container principal
        JPanel mainContainer = new JPanel(new BorderLayout(5, 5));
        mainContainer.add(topPanel, BorderLayout.NORTH);
        
        JPanel middleContainer = new JPanel(new BorderLayout(5, 5));
        middleContainer.add(affichageUtilisateurs, BorderLayout.NORTH);
        middleContainer.add(leftPanel, BorderLayout.WEST);
        middleContainer.add(formPanel, BorderLayout.CENTER);
        middleContainer.add(userPanel, BorderLayout.EAST);
        
        mainContainer.add(middleContainer, BorderLayout.CENTER);
        add(mainContainer);
    }

    // Méthode pour initialiser les écouteurs d'événements
    private void initListeners() {
        // Événement pour le bouton "Créer / Modifier" de ticket
        saveButton.addActionListener(e -> creerOuModifierTicket());

        // Événement pour le bouton "Créer" d'utilisateur
        createUserButton.addActionListener(e -> creerUtilisateur());

        // Événement pour la sélection d'un ticket dans la liste
        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ticket selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    remplirFormulaireTicket(selectedTicket);
                    updateActionsPanel(selectedTicket);
                    afficherHistorique(selectedTicket);
                }
            }
        });
        
        // Événement pour le changement d'utilisateur connecté
        connectedUserBox.addActionListener(e -> {
            currentUser = (User) connectedUserBox.getSelectedItem();
            Ticket selectedTicket = ticketList.getSelectedValue();
            updateActionsPanel(selectedTicket);
        });
        
        // Événement pour le filtre par statut
        filterStatusBox.addActionListener(e -> filtrerTickets());
    }

    // Méthode pour créer ou modifier un ticket
    private void creerOuModifierTicket() {
        try {
            // Récupération des champs
            String titre = titreField.getText().trim();
            String description = descriptionArea.getText().trim();
            String priorite = (String) prioriteBox.getSelectedItem();
            
            // Vérifier qu'un utilisateur est sélectionné
            User selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez sélectionner un utilisateur créateur du ticket !", 
                    "Erreur de validation", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

            // Vérifier que la description n'est pas vide (pas dans ticketValidator)
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "La description du ticket ne peut pas être vide !", 
                    "Erreur de validation", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier si on modifie un ticket existant ou on en crée un nouveau
            Ticket selectedTicket = ticketList.getSelectedValue();
            int ticketID = selectedTicket != null ? selectedTicket.getTicketID() : 0;
            
            // UTILISER LE VALIDATEUR pour valider le titre et la priorité
            List<String> errors = ticketValidator.getValidationErrors(titre, priorite, ticketID);
            
            if (!errors.isEmpty()) {
                // Afficher toutes les erreurs de validation
                StringBuilder errorMessage = new StringBuilder("Erreurs de validation :\n\n");
                for (String error : errors) {
                    errorMessage.append("• ").append(error).append("\n");
                }
                JOptionPane.showMessageDialog(this, 
                    errorMessage.toString(), 
                    "Erreur de validation", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

            if (selectedTicket != null) {
                // Mode modification : mettre à jour le ticket existant
                selectedTicket.setTitle(titre);
                selectedTicket.setDescription(descManager.createDescription(description));
                selectedTicket.setPriority(priorite);
                
                JOptionPane.showMessageDialog(this, 
                    "Ticket #" + selectedTicket.getTicketID() + " modifié avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mode création : créer un nouveau ticket
                Ticket newTicket = ticketCreator.createTicket(titre, description, selectedUser, priorite);
                
                JOptionPane.showMessageDialog(this, 
                    "Ticket #" + newTicket.getTicketID() + " créé avec succès !", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

            // Rafraîchir la liste des tickets
            rafraichirListeTickets();
            
            // Vider le formulaire
            viderFormulaireTicket();
            
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

            // UTILISER LE VALIDATEUR pour valider les données utilisateur
            // Note: on passe 0 comme userID car c'est une création (l'ID sera généré)
            List<String> errors = userValidator.getValidationErrors(nom, email, role, 0);
            
            if (!errors.isEmpty()) {
                // Afficher toutes les erreurs de validation
                StringBuilder errorMessage = new StringBuilder("Erreurs de validation :\n\n");
                for (String error : errors) {
                    errorMessage.append("• ").append(error).append("\n");
                }
                JOptionPane.showMessageDialog(this, 
                    errorMessage.toString(), 
                    "Erreur de validation", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

            // Créer l'utilisateur avec UserCreator (qui fait aussi sa propre validation)
            User newUser = userCreator.createUser(nom, email, role);
            
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
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la création de l'utilisateur : " + ex.getMessage(), 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour remplir le formulaire avec les données d'un ticket sélectionné
    private void remplirFormulaireTicket(Ticket ticket) {
        titreField.setText(ticket.getTitle());
        descriptionArea.setText(ticket.getDescription().toString());
        statutBox.setSelectedItem(ticket.getStatus());
        prioriteBox.setSelectedItem(ticket.getPriority());
    }

    // Méthode pour vider le formulaire de ticket
    private void viderFormulaireTicket() {
        titreField.setText("");
        descriptionArea.setText("");
        statutBox.setSelectedIndex(0);
        prioriteBox.setSelectedIndex(0);
        ticketList.clearSelection();
    }

    // Méthode pour rafraîchir la liste des tickets
    private void rafraichirListeTickets() {
        List<Ticket> tickets = ticketManager.getAllTickets();
        ticketList.setListData(tickets.toArray(new Ticket[0]));
    }

    // Méthode pour rafraîchir la liste des utilisateurs
    private void rafraichirListeUtilisateurs() {
        userList.setListData(allUsers.toArray(new User[0]));
        // Mettre à jour également le combobox des utilisateurs connectés
        User selectedUser = (User) connectedUserBox.getSelectedItem();
        connectedUserBox.removeAllItems();
        for (User u : allUsers) {
            connectedUserBox.addItem(u);
        }
        if (selectedUser != null && allUsers.contains(selectedUser)) {
            connectedUserBox.setSelectedItem(selectedUser);
        }
    }

    // ═══════════════════════════════════════════════════════════════════
    // MÉTHODES POUR FONCTIONNALITÉS AVANCÉES
    // ═══════════════════════════════════════════════════════════════════
    
    // Crée le panel de statistiques
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("📊 Statistiques"),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        statsTotal = createStatLabel("Total", "0", new Color(100, 149, 237));
        statsOuvert = createStatLabel("Ouvert", "0", new Color(255, 165, 0));
        statsAssigne = createStatLabel("Assigné", "0", new Color(255, 215, 0));
        statsValidation = createStatLabel("Validation", "0", new Color(138, 43, 226));
        statsTermine = createStatLabel("Terminé", "0", new Color(34, 139, 34));
        statsFerme = createStatLabel("Fermé", "0", new Color(220, 20, 60));
        
        panel.add(statsTotal);
        panel.add(statsOuvert);
        panel.add(statsAssigne);
        panel.add(statsValidation);
        panel.add(statsTermine);
        panel.add(statsFerme);
        
        updateStatistics();
        return panel;
    }
    
    // Crée un label de statistique coloré
    private JLabel createStatLabel(String title, String value, Color color) {
        JLabel label = new JLabel("<html><center><b>" + title + "</b><br><font size='5'>" + value + "</font></center></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        label.setPreferredSize(new Dimension(80, 50));
        return label;
    }
    
    // Met à jour les statistiques
    private void updateStatistics() {
        List<Ticket> allTickets = ticketManager.getAllTickets();
        int total = allTickets.size();
        int ouvert = (int) allTickets.stream().filter(t -> "OUVERT".equals(t.getStatus())).count();
        int assigne = (int) allTickets.stream().filter(t -> "ASSIGNÉ".equals(t.getStatus())).count();
        int validation = (int) allTickets.stream().filter(t -> "VALIDATION".equals(t.getStatus())).count();
        int termine = (int) allTickets.stream().filter(t -> "TERMINÉ".equals(t.getStatus())).count();
        int ferme = (int) allTickets.stream().filter(t -> "FERMÉ".equals(t.getStatus())).count();
        
        statsTotal.setText("<html><center><b>Total</b><br><font size='5'>" + total + "</font></center></html>");
        statsOuvert.setText("<html><center><b>Ouvert</b><br><font size='5'>" + ouvert + "</font></center></html>");
        statsAssigne.setText("<html><center><b>Assigné</b><br><font size='5'>" + assigne + "</font></center></html>");
        statsValidation.setText("<html><center><b>Validation</b><br><font size='5'>" + validation + "</font></center></html>");
        statsTermine.setText("<html><center><b>Terminé</b><br><font size='5'>" + termine + "</font></center></html>");
        statsFerme.setText("<html><center><b>Fermé</b><br><font size='5'>" + ferme + "</font></center></html>");
    }
    
    // Crée les boutons d'action selon le statut du ticket et les permissions
    private void updateActionsPanel(Ticket ticket) {
        actionsPanel.removeAll();
        
        if (ticket == null) {
            JLabel noTicketLabel = new JLabel("Sélectionnez un ticket pour voir les actions disponibles");
            noTicketLabel.setFont(new Font("Arial", Font.ITALIC, 11));
            actionsPanel.add(noTicketLabel);
        } else {
            String status = ticket.getStatus();
            User user = (User) connectedUserBox.getSelectedItem();
            
            if (user == null) {
                JLabel noUserLabel = new JLabel("⚠️ Aucun utilisateur connecté");
                actionsPanel.add(noUserLabel);
            } else {
                // Afficher le statut actuel
                JLabel statusLabel = new JLabel("📌 Statut: " + status + " | ");
                statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
                actionsPanel.add(statusLabel);
                
                // Boutons selon le statut
                switch (status) {
                    case "OUVERT":
                        if (user.canAssignTickets()) {
                            actionsPanel.add(createActionButton("👤 Assigner", () -> assignerTicket(ticket, user)));
                            actionsPanel.add(createActionButton("❌ Fermer", () -> fermerTicket(ticket, user)));
                        }
                        break;
                        
                    case "ASSIGNÉ":
                        // Afficher à qui c'est assigné
                        User assignedUser = getUserById(ticket.getAssignedUserId());
                        if (assignedUser != null) {
                            JLabel assignedLabel = new JLabel("Assigné à: " + assignedUser.getName() + " | ");
                            assignedLabel.setFont(new Font("Arial", Font.ITALIC, 11));
                            actionsPanel.add(assignedLabel);
                        }
                        
                        if (user.canAssignTickets()) {
                            actionsPanel.add(createActionButton("✅ Mettre en Validation", () -> mettreEnValidation(ticket, user)));
                        }
                        if (user.getRole().equals("ADMIN")) {
                            actionsPanel.add(createActionButton("🔄 Réassigner", () -> reassignerTicket(ticket, user)));
                        }
                        break;
                        
                    case "VALIDATION":
                        if (user.canAssignTickets()) {
                            actionsPanel.add(createActionButton("✔️ Valider", () -> validerTicket(ticket, user)));
                            actionsPanel.add(createActionButton("↩️ Rejeter", () -> rejeterTicket(ticket, user)));
                        }
                        break;
                        
                    case "TERMINÉ":
                    case "FERMÉ":
                        if (user.getRole().equals("ADMIN")) {
                            actionsPanel.add(createActionButton("🔓 Rouvrir", () -> rouvrirTicket(ticket, user)));
                        }
                        JLabel closedLabel = new JLabel(status.equals("TERMINÉ") ? "✓ TERMINÉ" : "✗ FERMÉ");
                        closedLabel.setFont(new Font("Arial", Font.BOLD, 14));
                        closedLabel.setForeground(status.equals("TERMINÉ") ? new Color(34, 139, 34) : new Color(220, 20, 60));
                        actionsPanel.add(closedLabel);
                        break;
                }
                
                // Bouton commentaire (toujours disponible)
                actionsPanel.add(Box.createHorizontalStrut(20));
                actionsPanel.add(createActionButton("💬 Commentaire", () -> ajouterCommentaire(ticket, user)));
            }
        }
        
        actionsPanel.revalidate();
        actionsPanel.repaint();
    }
    
    // Crée un bouton d'action stylisé
    private JButton createActionButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }
    
    // Actions sur les tickets
    private void assignerTicket(Ticket ticket, User user) {
        // Dialog pour sélectionner un développeur
        User[] developers = allUsers.stream()
            .filter(u -> u.getRole().equals("DEVELOPER") || u.getRole().equals("ADMIN"))
            .toArray(User[]::new);
        
        if (developers.length == 0) {
            JOptionPane.showMessageDialog(this, 
                "Aucun développeur disponible !", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User selectedDev = (User) JOptionPane.showInputDialog(
            this,
            "Sélectionnez un développeur :",
            "Assigner le ticket",
            JOptionPane.QUESTION_MESSAGE,
            null,
            developers,
            developers[0]);
        
        if (selectedDev != null) {
            ticketManager.assignTicket(ticket.getTicketID(), selectedDev, user);
            ajouterHistorique(ticket, user, "Ticket assigné à " + selectedDev.getName());
            rafraichirTicketSelectionne();
            JOptionPane.showMessageDialog(this, 
                "Ticket assigné avec succès à " + selectedDev.getName(), 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void fermerTicket(Ticket ticket, User user) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous fermer ce ticket sans résolution ?",
            "Fermer le ticket",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ticketManager.closeTicket(ticket.getTicketID(), user);
            ajouterHistorique(ticket, user, "Ticket fermé sans résolution");
            rafraichirTicketSelectionne();
            JOptionPane.showMessageDialog(this, 
                "Ticket fermé avec succès", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mettreEnValidation(Ticket ticket, User user) {
        ticketManager.updateTicketStatus(ticket.getTicketID(), "VALIDATION", user);
        ajouterHistorique(ticket, user, "Ticket mis en validation");
        rafraichirTicketSelectionne();
        JOptionPane.showMessageDialog(this, 
            "Ticket mis en validation", 
            "Succès", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void validerTicket(Ticket ticket, User user) {
        ticketManager.closeTicket(ticket.getTicketID(), user);
        ajouterHistorique(ticket, user, "Ticket validé et terminé");
        rafraichirTicketSelectionne();
        JOptionPane.showMessageDialog(this, 
            "Ticket validé et terminé avec succès", 
            "Succès", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void rejeterTicket(Ticket ticket, User user) {
        String raison = JOptionPane.showInputDialog(this, 
            "Raison du rejet :", 
            "Rejeter le ticket", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (raison != null && !raison.trim().isEmpty()) {
            ticketManager.updateTicketStatus(ticket.getTicketID(), "ASSIGNÉ", user);
            ticketManager.addCommentToTicket(ticket.getTicketID(), "REJET: " + raison, user);
            ajouterHistorique(ticket, user, "Ticket rejeté: " + raison);
            rafraichirTicketSelectionne();
            JOptionPane.showMessageDialog(this, 
                "Ticket rejeté et renvoyé en ASSIGNÉ", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void reassignerTicket(Ticket ticket, User user) {
        assignerTicket(ticket, user);
    }
    
    private void rouvrirTicket(Ticket ticket, User user) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous rouvrir ce ticket ?",
            "Rouvrir le ticket",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            ticketManager.updateTicketStatus(ticket.getTicketID(), "OUVERT", user);
            ajouterHistorique(ticket, user, "Ticket rouvert");
            rafraichirTicketSelectionne();
            JOptionPane.showMessageDialog(this, 
                "Ticket rouvert avec succès", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void ajouterCommentaire(Ticket ticket, User user) {
        String commentaire = JOptionPane.showInputDialog(this, 
            "Entrez votre commentaire :", 
            "Ajouter un commentaire", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (commentaire != null && !commentaire.trim().isEmpty()) {
            ticketManager.addCommentToTicket(ticket.getTicketID(), commentaire, user);
            ajouterHistorique(ticket, user, "Commentaire ajouté");
            rafraichirTicketSelectionne();
            JOptionPane.showMessageDialog(this, 
                "Commentaire ajouté avec succès", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Ajoute une ligne à l'historique
    private void ajouterHistorique(Ticket ticket, User user, String action) {
        String ligne = String.format("[%s] %s - %s - %s → %s\n",
            dateFormat.format(new Date()),
            user.getName(),
            action,
            "",
            ticket.getStatus());
        historiqueArea.append(ligne);
        historiqueArea.setCaretPosition(historiqueArea.getDocument().getLength());
    }
    
    // Affiche l'historique complet d'un ticket
    private void afficherHistorique(Ticket ticket) {
        historiqueArea.setText("");
        if (ticket != null) {
            historiqueArea.append("═══════════════════════════════════════════════════════\n");
            historiqueArea.append("  HISTORIQUE DU TICKET #" + ticket.getTicketID() + "\n");
            historiqueArea.append("═══════════════════════════════════════════════════════\n\n");
            
            // Historique basé sur les commentaires
            List<String> comments = ticket.getComments();
            if (comments.isEmpty()) {
                historiqueArea.append("Aucun historique disponible.\n");
        } else {
                for (String comment : comments) {
                    historiqueArea.append("• " + comment + "\n");
                }
            }
        }
    }
    
    // Filtre les tickets par statut
    private void filtrerTickets() {
        String filtre = (String) filterStatusBox.getSelectedItem();
        List<Ticket> tickets;
        
        if (filtre.equals("Tous les tickets")) {
            tickets = ticketManager.getAllTickets();
        } else {
            tickets = ticketManager.getTicketsByStatus(filtre);
        }
        
        ticketList.setListData(tickets.toArray(new Ticket[0]));
        updateStatistics();
    }
    
    // Rafraîchit le ticket sélectionné
    private void rafraichirTicketSelectionne() {
        Ticket selectedTicket = ticketList.getSelectedValue();
        filtrerTickets();
        updateActionsPanel(selectedTicket);
        if (selectedTicket != null) {
            afficherHistorique(selectedTicket);
        }
    }
    
    // Récupère un utilisateur par ID
    private User getUserById(int userId) {
        return allUsers.stream()
            .filter(u -> u.getUserID() == userId)
            .findFirst()
            .orElse(null);
    }

    //Getters
    public TicketManager getTicketManager() {
        return ticketManager;
    }
}

