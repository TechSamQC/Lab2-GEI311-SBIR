package org.openapitools.client.gui;

import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.*;
import org.openapitools.client.model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface graphique Swing pour le système de gestion de tickets
 * Version adaptée pour utiliser l'API REST via le client généré
 * 
 * @author Il'aina Ratefinanahary et Samuel Brassard
 * @assistant Claude 3.5 Sonnet
 * @version 2.0 (Adaptation REST)
 */
public class DisplayGUI extends JFrame {
    
    // ==================== API Clients ====================
    private ApiClient apiClient;
    private TicketsApi ticketsApi;
    private UsersApi usersApi;
    private CommentsApi commentsApi;
    private StatusApi statusApi;
    private SearchApi searchApi;
    private ExportApi exportApi;
    
    // ==================== Variables d'instance ====================
    private List<UserDTO> allUsers;
    private UserDTO currentUser;
    private TicketDTO selectedTicket;
    private List<String> tempImagesPath;
    private List<String> tempVideosPath;

    // ==================== Composants GUI ====================
    private JList<TicketDTO> ticketList;
    private JList<UserDTO> userList;
    private JTextField titreField;
    private JTextField userNameField;
    private JTextField userEmailField;
    private JTextArea descriptionArea;
    private JTextArea currentComments;
    private JTextArea commentArea;
    private JTextArea otherInfoArea;
    private JComboBox<String> statutBox;
    private JComboBox<String> prioriteBox;
    private JComboBox<UserDTO> assignatedUserBox;
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

    // ==================== Constructeur ====================
    public DisplayGUI(String serverUrl) {
        // Configuration de la connexion API
        apiClient = new ApiClient();
        apiClient.setBasePath(serverUrl);
        
        // Initialisation des API clients
        ticketsApi = new TicketsApi(apiClient);
        usersApi = new UsersApi(apiClient);
        commentsApi = new CommentsApi(apiClient);
        statusApi = new StatusApi(apiClient);
        searchApi = new SearchApi(apiClient);
        exportApi = new ExportApi(apiClient);

        // Configuration de la fenêtre principale
        setTitle("Programme de gestion des tickets - Client REST");
        setSize(1350, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialisation des listes temporaires
        tempImagesPath = new ArrayList<>();
        tempVideosPath = new ArrayList<>();

        // Charger les utilisateurs depuis le serveur
        chargerUtilisateurs();

        // Initialiser les composants GUI
        initComponents();

        // Initialiser les écouteurs d'événements
        initListeners();

        // Rendre la fenêtre visible
        setVisible(true);
    }

    // ==================== Méthodes d'initialisation API ====================
    
    private void chargerUtilisateurs() {
        try {
            allUsers = usersApi.getAllUsers();
            System.out.println("Utilisateurs chargés : " + allUsers.size());
        } catch (ApiException e) {
            allUsers = new ArrayList<>();
            JOptionPane.showMessageDialog(this,
                "Erreur de connexion au serveur : " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==================== Initialisation des composants GUI ====================
    
    private void initComponents() {
        // Liste À GAUCHE : affichage des tickets
        ticketList = new JList<>();
        ticketList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TicketDTO) {
                    TicketDTO ticket = (TicketDTO) value;
                    setText("Ticket " + ticket.getTicketID() + " [" + ticket.getTitle() + "]");
                }
                return this;
            }
        });
        
        filterStatusBox = new JComboBox<>(new String[]{"TOUS", "OUVERT", "ASSIGNE", "VALIDATION", "FERME"});
        filterStatusBox.setSelectedIndex(0);
        affichageTickets = new JScrollPane(ticketList);
        ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.add(affichageTickets, BorderLayout.CENTER);
        ticketPanel.add(filterStatusBox, BorderLayout.NORTH);

        // Formulaire AU CENTRE : création/modification de tickets
        titreField = new JTextField();
        descriptionArea = new JTextArea();
        currentComments = new JTextArea();
        currentComments.setEditable(false);
        otherInfoArea = new JTextArea();
        otherInfoArea.setEditable(false);
        commentArea = new JTextArea();
        statutBox = new JComboBox<>(new String[]{"OUVERT", "ASSIGNE", "VALIDATION", "FERME"});
        prioriteBox = new JComboBox<>(new String[]{"BASSE", "MOYENNE", "HAUTE", "CRITIQUE"});
        assignatedUserBox = new JComboBox<>(allUsers.toArray(new UserDTO[0]));
        assignatedUserBox.insertItemAt(null, 0);
        assignatedUserBox.setSelectedIndex(0);
        assignatedUserBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UserDTO) {
                    UserDTO user = (UserDTO) value;
                    setText(user.getName() + " (" + user.getRole() + ")");
                } else {
                    setText("Non assigné");
                }
                return this;
            }
        });
        
        addImageButton = new JButton("Ajouter une image à la description");
        addVideoButton = new JButton("Ajouter une vidéo à la description");
        showImagesButton = new JButton("Voir les images");
        showVideosButton = new JButton("Voir les vidéos");
        saveButton = new JButton("Modifier ticket");
        createButton = new JButton("Créer ticket");
        desassignButton = new JButton("Désassigner le ticket");
        exportPDFButton = new JButton("Exporter le ticket en PDF");

        // Panneau de création/modification
        formPanel = new JPanel(new GridLayout(12, 1));
        formPanel.add(new JLabel("Titre :"));
        formPanel.add(titreField);
        formPanel.add(new JLabel("Informations sur le ticket :"));
        formPanel.add(new JScrollPane(otherInfoArea));
        formPanel.add(new JLabel("Description :"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(addImageButton);
        formPanel.add(addVideoButton);
        formPanel.add(showImagesButton);
        formPanel.add(showVideosButton);
        formPanel.add(new JLabel("Commentaires :"));
        formPanel.add(new JScrollPane(currentComments));
        formPanel.add(new JLabel("Ajouter un commentaire :"));
        formPanel.add(new JScrollPane(commentArea));
        formPanel.add(new JLabel("Statut :"));
        formPanel.add(statutBox);
        formPanel.add(new JLabel("Priorité :"));
        formPanel.add(prioriteBox);
        formPanel.add(new JLabel("Utilisateur assigné :"));
        formPanel.add(assignatedUserBox);
        formPanel.add(createButton);
        formPanel.add(saveButton);
        formPanel.add(desassignButton);
        formPanel.add(exportPDFButton);

        // Formulaire À DROITE : Création d'utilisateurs
        createUserButton = new JButton("Créer un utilisateur");
        deleteUserButton = new JButton("Supprimer l'utilisateur");
        userTypeBox = new JComboBox<>(new String[]{"ADMIN", "DEVELOPER", "USER"});
        userNameField = new JTextField();
        userEmailField = new JTextField();

        userPanel = new JPanel(new GridLayout(4, 1));
        userPanel.add(new JLabel("Rôle utilisateur :"));
        userPanel.add(userTypeBox);
        userPanel.add(new JLabel("Nom d'utilisateur :"));
        userPanel.add(userNameField);
        userPanel.add(new JLabel("Email :"));
        userPanel.add(userEmailField);
        userPanel.add(createUserButton);
        userPanel.add(deleteUserButton);

        // Liste de sélection des utilisateurs EN HAUT
        userList = new JList<>(allUsers.toArray(new UserDTO[0]));
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UserDTO) {
                    UserDTO user = (UserDTO) value;
                    setText("User " + user.getUserID() + ": " + user.getName() + 
                           " (" + user.getRole() + ") <" + user.getEmail() + ">");
                }
                return this;
            }
        });
        affichageUtilisateurs = new JScrollPane(userList);

        // Ajout des panneaux à la fenêtre principale
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Tickets"));
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer / Modifier un Ticket"));
        affichageUtilisateurs.setBorder(BorderFactory.createTitledBorder("Utilisateur connecté :"));
        userPanel.setBorder(BorderFactory.createTitledBorder("Créer un nouvel utilisateur"));
        add(ticketPanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);
        add(affichageUtilisateurs, BorderLayout.NORTH);
        add(userPanel, BorderLayout.EAST);
    }

    // ==================== Initialisation des listeners ====================
    
    private void initListeners() {
        createButton.addActionListener(e -> creerTicket());
        saveButton.addActionListener(e -> modifierTicket());
        exportPDFButton.addActionListener(e -> exporterTicketPDF());
        desassignButton.addActionListener(e -> desassignerUtilisateur());
        createUserButton.addActionListener(e -> creerUtilisateur());
        deleteUserButton.addActionListener(e -> supprimerUtilisateur());
        addImageButton.addActionListener(e -> ajouterImage());
        showImagesButton.addActionListener(e -> showImages());
        addVideoButton.addActionListener(e -> ajouterVideo());
        showVideosButton.addActionListener(e -> showVideos());

        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    remplirFormulaireTicket();
                }
            }
        });

        userList.addListSelectionListener(e -> {
            if (userList.getSelectedValue() != null) {
                currentUser = userList.getSelectedValue();
                filtrerTickets();
                viderFormulaireTicket();
            }
        });

        filterStatusBox.addActionListener(e -> filtrerTickets());
    }

    // ==================== Méthodes de gestion des tickets ====================
    
    private void filtrerTickets() {
        try {
            String statutFiltre = (String) filterStatusBox.getSelectedItem();
            List<TicketDTO> tickets;

            if (currentUser == null) {
                ticketList.setListData(new TicketDTO[0]);
                return;
            }
            else if (statutFiltre.equals("TOUS")) {
                if (currentUser.getIsAdmin()) {
                    tickets = ticketsApi.getAllTickets(null, null, null);
                } else if (isUserDeveloper(currentUser)) {
                    // Pour les développeurs : tickets ouverts + leurs tickets assignés
                    List<TicketDTO> openTickets = searchApi.getUnassignedTickets();
                    List<TicketDTO> userTickets = searchApi.getTicketsByUser(currentUser.getUserID());
                    tickets = new ArrayList<>(openTickets);
                    for (TicketDTO t : userTickets) {
                        if (!tickets.stream().anyMatch(ticket -> ticket.getTicketID().equals(t.getTicketID()))) {
                            tickets.add(t);
                        }
                    }
                } else {
                    tickets = searchApi.getTicketsByUser(currentUser.getUserID());
                }
            } else if ((statutFiltre.equals("OUVERT") && isUserDeveloper(currentUser)) || currentUser.getIsAdmin()) {
                tickets = searchApi.getTicketsByStatus(statutFiltre);
            } else {
                tickets = searchApi.getTicketsByStatusUser(statutFiltre, currentUser.getUserID());
            }

            ticketList.setListData(tickets.toArray(new TicketDTO[0]));
        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des tickets : " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isUserDeveloper(UserDTO user) {
        return user.getRole() == UserDTO.RoleEnum.DEVELOPER || user.getRole() == UserDTO.RoleEnum.ADMIN;
    }

    private void viderFormulaireTicket() {
        titreField.setText("");
        descriptionArea.setText("");
        commentArea.setText("");
        currentComments.setText("");
        otherInfoArea.setText("");
        statutBox.setSelectedIndex(0);
        prioriteBox.setSelectedIndex(0);
        assignatedUserBox.setSelectedIndex(0);
        tempImagesPath.clear();
        tempVideosPath.clear();
    }

    private void remplirFormulaireTicket() {
        titreField.setText(selectedTicket.getTitle());
        
        if (selectedTicket.getDescription() != null) {
            descriptionArea.setText(selectedTicket.getDescription().getTextContent());
        }
        
        statutBox.setSelectedItem(selectedTicket.getStatus().getValue());
        prioriteBox.setSelectedItem(selectedTicket.getPriority().getValue());
        
        otherInfoArea.setText("Créé le : " + selectedTicket.getCreationDate() + 
                            "\nDernière mise à jour : " + selectedTicket.getUpdateDate());

        // Récupérer l'utilisateur assigné
        Integer assignedUserId = selectedTicket.getAssignedUserId();
        if (assignedUserId != null && assignedUserId != 0) {
            for (UserDTO user : allUsers) {
                if (user.getUserID().equals(assignedUserId)) {
                    assignatedUserBox.setSelectedItem(user);
                    break;
                }
            }
        } else {
            assignatedUserBox.setSelectedIndex(0);
        }

        // Charger les commentaires
        try {
            List<String> comments = commentsApi.getTicketComments(selectedTicket.getTicketID());
            currentComments.setText(String.join("\n", comments));
        } catch (ApiException e) {
            currentComments.setText("Erreur de chargement des commentaires");
        }
    }

    private void creerTicket() {
        try {
            String titre = titreField.getText().trim();
            String description = descriptionArea.getText().trim();
            String commentaire = commentArea.getText().trim();
            String priorite = (String) prioriteBox.getSelectedItem();
            UserDTO utilisateurAssigne = (UserDTO) assignatedUserBox.getSelectedItem();

            if (currentUser == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un utilisateur créateur du ticket !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Créer la requête de création
            CreateTicketRequest request = new CreateTicketRequest();
            request.setTitle(titre);
            request.setPriority(CreateTicketRequest.PriorityEnum.fromValue(priorite));
            
            Description desc = new Description();
            desc.setTextContent(description);
            desc.setImagePaths(new ArrayList<>(tempImagesPath));
            desc.setVideoPaths(new ArrayList<>(tempVideosPath));
            request.setDescription(desc);

            // Créer le ticket
            TicketDTO newTicket = ticketsApi.createTicket(request);

            // Assigner un utilisateur si sélectionné
            if (utilisateurAssigne != null && isUserDeveloper(currentUser)) {
                AssignmentDTO assignment = new AssignmentDTO();
                assignment.setUserId(utilisateurAssigne.getUserID());
                assignment.setAssignedBy(currentUser.getUserID());
                ticketsApi.assignTicket(newTicket.getTicketID(), assignment);
            }

            // Ajouter un commentaire si fourni et ticket assigné
            if (!commentaire.isEmpty() && newTicket.getAssignedUserId() != null && newTicket.getAssignedUserId() != 0) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setComment(commentaire);
                commentRequest.setAuthorId(currentUser.getUserID());
                commentsApi.addComment(newTicket.getTicketID(), commentRequest);
            }

            JOptionPane.showMessageDialog(this,
                "Ticket #" + newTicket.getTicketID() + " créé avec succès !",
                "Succès", JOptionPane.INFORMATION_MESSAGE);

            filtrerTickets();
            viderFormulaireTicket();
            tempImagesPath.clear();
            tempVideosPath.clear();

        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la création du ticket : " + e.getResponseBody(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierTicket() {
        try {
            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un ticket à modifier !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedTicket.getStatus() == TicketDTO.StatusEnum.FERME || 
                selectedTicket.getStatus() == TicketDTO.StatusEnum.VALIDATION) {
                JOptionPane.showMessageDialog(this,
                    "Ce ticket est terminé et ne peut pas être modifié !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String description = descriptionArea.getText().trim();
            String commentaire = commentArea.getText().trim();
            String statut = (String) statutBox.getSelectedItem();
            String priorite = (String) prioriteBox.getSelectedItem();
            UserDTO utilisateurAssigne = (UserDTO) assignatedUserBox.getSelectedItem();

            // Mettre à jour l'utilisateur assigné si changé
            if (utilisateurAssigne != null && !utilisateurAssigne.getUserID().equals(selectedTicket.getAssignedUserId())) {
                if (!isUserDeveloper(currentUser)) {
                    JOptionPane.showMessageDialog(this,
                        "Vous n'avez pas les droits pour assigner des tickets.",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                AssignmentDTO assignment = new AssignmentDTO();
                assignment.setUserId(utilisateurAssigne.getUserID());
                assignment.setAssignedBy(currentUser.getUserID());
                ticketsApi.assignTicket(selectedTicket.getTicketID(), assignment);
            }

            // Mettre à jour le statut si changé
            if (!statut.equals(selectedTicket.getStatus().getValue())) {
                StatusUpdateDTO statusUpdate = new StatusUpdateDTO();
                statusUpdate.setStatus(StatusUpdateDTO.StatusEnum.fromValue(statut));
                statusUpdate.setRequestedBy(currentUser.getUserID());
                statusApi.updateTicketStatus(selectedTicket.getTicketID(), statusUpdate);
            }

            // Mettre à jour la description
            if (!description.isEmpty() || !tempImagesPath.isEmpty() || !tempVideosPath.isEmpty()) {
                Description desc = new Description();
                desc.setTextContent(description);
                
                // Ajouter les images et vidéos existantes + nouvelles
                List<String> allImages = new ArrayList<>();
                if (selectedTicket.getDescription() != null && selectedTicket.getDescription().getImagePaths() != null) {
                    allImages.addAll(selectedTicket.getDescription().getImagePaths());
                }
                allImages.addAll(tempImagesPath);
                desc.setImagePaths(allImages);
                
                List<String> allVideos = new ArrayList<>();
                if (selectedTicket.getDescription() != null && selectedTicket.getDescription().getVideoPaths() != null) {
                    allVideos.addAll(selectedTicket.getDescription().getVideoPaths());
                }
                allVideos.addAll(tempVideosPath);
                desc.setVideoPaths(allVideos);
                
                ticketsApi.updateTicketDescription(selectedTicket.getTicketID(), desc);
            }

            // Ajouter un commentaire si fourni
            if (!commentaire.isEmpty() && selectedTicket.getAssignedUserId() != null && selectedTicket.getAssignedUserId() != 0) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setComment(commentaire);
                commentRequest.setAuthorId(currentUser.getUserID());
                commentsApi.addComment(selectedTicket.getTicketID(), commentRequest);
            }

            // Mettre à jour la priorité si changée
            if (!priorite.equals(selectedTicket.getPriority().getValue())) {
                UpdateTicketPriorityRequest priorityRequest = new UpdateTicketPriorityRequest();
                priorityRequest.setPriority(UpdateTicketPriorityRequest.PriorityEnum.fromValue(priorite));
                priorityRequest.setRequestedBy(currentUser.getUserID());
                ticketsApi.updateTicketPriority(selectedTicket.getTicketID(), priorityRequest);
            }

            JOptionPane.showMessageDialog(this,
                "Ticket #" + selectedTicket.getTicketID() + " modifié avec succès !",
                "Succès", JOptionPane.INFORMATION_MESSAGE);

            filtrerTickets();
            viderFormulaireTicket();
            tempImagesPath.clear();
            tempVideosPath.clear();

        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la modification : " + e.getResponseBody(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exporterTicketPDF() {
        try {
            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un ticket à exporter !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Enregistrer le ticket en PDF");
            fileChooser.setSelectedFile(new File("ticket_" + selectedTicket.getTicketID() + ".pdf"));
            int result = fileChooser.showSaveDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) return;

            File pdfFile = exportApi.exportTicketToPDF(selectedTicket.getTicketID(), currentUser.getUserID());
            
            // Copier le fichier vers la destination choisie
            if (pdfFile != null && pdfFile.exists()) {
                java.nio.file.Files.copy(pdfFile.toPath(), fileChooser.getSelectedFile().toPath(), 
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                
                JOptionPane.showMessageDialog(this,
                    "Ticket exporté avec succès !",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new Exception("Fichier PDF non généré par le serveur");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'export PDF : " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterImage() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                tempImagesPath.add(fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Image ajoutée (sera envoyée lors de la sauvegarde)",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'ajout d'image : " + ex.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showImages() {
        if (selectedTicket == null || selectedTicket.getDescription() == null) {
            JOptionPane.showMessageDialog(this,
                "Aucune image disponible",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout());

            for (String path : selectedTicket.getDescription().getImagePaths()) {
                ImageIcon icon = new ImageIcon(path);
                JLabel label = new JLabel(icon);
                panel.add(label);
            }

            JFrame frame = new JFrame("Images dans la description");
            frame.add(new JScrollPane(panel));
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'affichage d'images : " + ex.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterVideo() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                tempVideosPath.add(fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this,
                    "Vidéo ajoutée (sera envoyée lors de la sauvegarde)",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'ajout de vidéo : " + ex.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showVideos() {
        if (selectedTicket == null || selectedTicket.getDescription() == null) {
            JOptionPane.showMessageDialog(this,
                "Aucune vidéo disponible",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout());

            int i = 0;
            for (String path : selectedTicket.getDescription().getVideoPaths()) {
                JButton play = new JButton("▶ Play vidéo " + (i + 1));
                panel.add(play);
                play.addActionListener(e -> {
                    try {
                        Desktop.getDesktop().open(new File(path));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                            "Erreur lecture vidéo : " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
                i++;
            }

            JFrame frame = new JFrame("Vidéos dans la description");
            frame.add(new JScrollPane(panel));
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de l'affichage des vidéos : " + ex.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void desassignerUtilisateur() {
        if (selectedTicket == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un ticket à désassigner !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentUser == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un utilisateur connecté !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UnassignTicketRequest request = new UnassignTicketRequest();
            request.setRequestedBy(currentUser.getUserID());
            ticketsApi.unassignTicket(selectedTicket.getTicketID(), request);

            JOptionPane.showMessageDialog(this,
                "Utilisateur désassigné avec succès du ticket #" + selectedTicket.getTicketID(),
                "Succès", JOptionPane.INFORMATION_MESSAGE);
            filtrerTickets();
        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la désassignation : " + e.getResponseBody(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==================== Méthodes de gestion des utilisateurs ====================
    
    private void rafraichirListeUtilisateurs() {
        chargerUtilisateurs();
        userList.setListData(allUsers.toArray(new UserDTO[0]));
        assignatedUserBox.setModel(new DefaultComboBoxModel<>(allUsers.toArray(new UserDTO[0])));
        assignatedUserBox.insertItemAt(null, 0);
        assignatedUserBox.setSelectedIndex(0);
    }

    private void viderFormulaireUtilisateur() {
        userNameField.setText("");
        userEmailField.setText("");
        userTypeBox.setSelectedIndex(0);
    }

    private void creerUtilisateur() {
        try {
            String nom = userNameField.getText().trim();
            String email = userEmailField.getText().trim();
            String role = (String) userTypeBox.getSelectedItem();

            UserCreate newUser = new UserCreate();
            newUser.setName(nom);
            newUser.setEmail(email);
            newUser.setRole(UserCreate.RoleEnum.fromValue(role));

            UserDTO createdUser = usersApi.createUser(newUser);

            rafraichirListeUtilisateurs();
            viderFormulaireUtilisateur();

            JOptionPane.showMessageDialog(this,
                "Utilisateur créé avec succès !\nNom : " + createdUser.getName() + 
                "\nID : " + createdUser.getUserID(),
                "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la création de l'utilisateur : " + e.getResponseBody(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerUtilisateur() {
        int confirmation = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer cet utilisateur ? CETTE ACTION EST IRRÉVERSIBLE.",
            "Confirmer la suppression ?",
            JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un utilisateur à supprimer !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (currentUser.getUserID() <= 3) {
                JOptionPane.showMessageDialog(this,
                    "Les utilisateurs par défaut ne peuvent pas être supprimés.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usersApi.deleteUser(currentUser.getUserID());

            rafraichirListeUtilisateurs();

            JOptionPane.showMessageDialog(this,
                "Utilisateur supprimé avec succès !",
                "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (ApiException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la suppression : " + e.getResponseBody(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

