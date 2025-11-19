package org.openapitools.service;

import org.openapitools.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    
    private final TicketManager ticketManager;
    private final TicketCreator ticketCreator;
    private final UserService userService;

    @Autowired
    public TicketService(UserService userService) {
        this.ticketManager = new TicketManager();
        this.ticketCreator = new TicketCreator(1000, ticketManager);
        this.userService = userService;
        initializeDefaultTickets();
    }

    private void initializeDefaultTickets() {
        // Créer quelques tickets par défaut pour les tests
        User admin = userService.getUserById(1);
        if (admin != null) {
            ticketCreator.createTicket("Bug d'affichage", "L'interface ne s'affiche pas correctement", admin, "HAUTE");
            ticketCreator.createTicket("Amélioration performance", "Optimiser les requêtes base de données", admin, "MOYENNE");
        }
    }

    // ===== CRUD Operations =====
    
    public Ticket createTicket(String title, String description, String priority) {
        User defaultUser = userService.getUserById(1);
        return ticketCreator.createTicket(title, description, defaultUser, priority);
    }

    public Ticket getTicketById(int ticketId) {
        return ticketManager.getTicket(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return ticketManager.getAllTickets();
    }

    public List<Ticket> getTicketsByStatus(String status) {
        return ticketManager.getTicketsByStatus(status);
    }

    public List<Ticket> getTicketsByPriority(String priority) {
        return ticketManager.getTicketsByPriority(priority);
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return List.of();
        }
        return ticketManager.getTicketsByUser(user);
    }

    public List<Ticket> searchTicketsByTitle(String title) {
        return ticketManager.searchTicketsByTitle(title);
    }

    public List<Ticket> getUnassignedTickets() {
        return ticketManager.getUnassignedOpenTickets();
    }

    public List<Ticket> getCriticalTickets() {
        return ticketManager.getCriticalTickets();
    }

    public boolean updateTicket(int ticketId, String title, String priority) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }

        if (title != null && !title.trim().isEmpty()) {
            ticket.setTitle(title);
        }

        if (priority != null && !priority.trim().isEmpty()) {
            User defaultUser = userService.getUserById(1);
            return ticketManager.updateTicketPriority(ticketId, priority, defaultUser);
        }

        return true;
    }

    public boolean deleteTicket(int ticketId) {
        return ticketManager.removeTicket(ticketId);
    }

    // ===== Status Operations =====

    public boolean updateTicketStatus(int ticketId, String newStatus, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1); // Default admin
        }
        return ticketManager.updateTicketStatus(ticketId, newStatus, requester);
    }

    public List<String> getAvailableTransitions(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return List.of();
        }
        return ticketManager.getStatusManager().getValidTransitions(ticket.getStatus());
    }

    // ===== Priority Operations =====

    public boolean updateTicketPriority(int ticketId, String priority, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1);
        }
        return ticketManager.updateTicketPriority(ticketId, priority, requester);
    }

    // ===== Assignment Operations =====

    public boolean assignTicket(int ticketId, int userId, int assignedByUserId) {
        User user = userService.getUserById(userId);
        User assignedBy = userService.getUserById(assignedByUserId);
        
        if (user == null || assignedBy == null) {
            return false;
        }

        return ticketManager.assignTicket(ticketId, user, assignedBy);
    }

    public boolean unassignTicket(int ticketId, int requestedByUserId) {
        User requester = userService.getUserById(requestedByUserId);
        if (requester == null) {
            requester = userService.getUserById(1);
        }
        return ticketManager.unassignTicket(ticketId, requester);
    }

    public boolean closeTicket(int ticketId, int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            user = userService.getUserById(1);
        }
        return ticketManager.closeTicket(ticketId, user);
    }

    // ===== Description Operations =====

    public boolean updateTicketDescription(int ticketId, String textContent, List<String> imagePaths, List<String> videoPaths) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }

        TicketDescription newDesc = new TicketDescription(textContent);
        if (imagePaths != null) {
            for (String path : imagePaths) {
                newDesc.addImagePaths(path);
            }
        }
        if (videoPaths != null) {
            for (String path : videoPaths) {
                newDesc.addVideoPath(path);
            }
        }

        ticket.setDescription(newDesc);
        return true;
    }

    public boolean addImageToTicket(int ticketId, String imagePath) {
        return ticketManager.addImageToTicketDescription(ticketId, imagePath);
    }

    public boolean addVideoToTicket(int ticketId, String videoPath) {
        return ticketManager.addVideoToTicketDescription(ticketId, videoPath);
    }

    public List<String> getTicketImages(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null || ticket.getDescription() == null) {
            return List.of();
        }
        return ticket.getDescription().getImagePaths();
    }

    // ===== Comment Operations =====

    public boolean addComment(int ticketId, String comment, int authorId) {
        User author = userService.getUserById(authorId);
        if (author == null) {
            author = userService.getUserById(1);
        }
        return ticketManager.addCommentToTicket(ticketId, comment, author);
    }

    public List<String> getComments(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return List.of();
        }
        return ticket.getComments();
    }

    public boolean clearComments(int ticketId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }
        ticketManager.getCommentManager().clearComments(ticket);
        return true;
    }

    // ===== Export Operations =====

    public String exportTicketToPDF(int ticketId, int userId) {
        Ticket ticket = ticketManager.getTicket(ticketId);
        if (ticket == null) {
            return null;
        }

        User user = userService.getUserById(userId);
        
        StringBuilder pdf = new StringBuilder();
        pdf.append("==================================================\n");
        pdf.append("       EXPORT PDF - TICKET DESCRIPTION\n");
        pdf.append("==================================================\n\n");
        pdf.append("Ticket #").append(ticket.getTicketID()).append(": ").append(ticket.getTitle()).append("\n");
        pdf.append("Statut: ").append(ticket.getStatus()).append("\n");
        pdf.append("Priorité: ").append(ticket.getPriority()).append("\n");
        pdf.append("Créé le: ").append(ticket.getCreationDate()).append("\n");
        pdf.append("Dernière mise à jour: ").append(ticket.getUpdateDate()).append("\n\n");
        
        if (user != null) {
            pdf.append("Exporté par: ").append(user.getName()).append("\n\n");
        }
        
        pdf.append("SECTION TEXTE\n");
        pdf.append("--------------------------------------------------\n");
        if (ticket.getDescription() != null) {
            pdf.append(ticket.getDescription().getTextContent()).append("\n");
        }
        pdf.append("--------------------------------------------------\n\n");
        
        if (ticket.getDescription() != null && !ticket.getDescription().getImagePaths().isEmpty()) {
            pdf.append("IMAGES:\n");
            for (String imagePath : ticket.getDescription().getImagePaths()) {
                pdf.append("  - ").append(imagePath).append("\n");
            }
            pdf.append("\n");
        }
        
        if (ticket.getDescription() != null && !ticket.getDescription().getVideoPaths().isEmpty()) {
            pdf.append("VIDÉOS:\n");
            for (String videoPath : ticket.getDescription().getVideoPaths()) {
                pdf.append("  - ").append(videoPath).append("\n");
            }
            pdf.append("\n");
        }
        
        if (!ticket.getComments().isEmpty()) {
            pdf.append("COMMENTAIRES:\n");
            for (String comment : ticket.getComments()) {
                pdf.append("  ").append(comment).append("\n");
            }
            pdf.append("\n");
        }
        
        pdf.append("==================================================\n");
        pdf.append("       Fin du document PDF\n");
        pdf.append("==================================================\n");
        
        return pdf.toString();
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }

    public UserService getUserService() {
        return userService;
    }
}

