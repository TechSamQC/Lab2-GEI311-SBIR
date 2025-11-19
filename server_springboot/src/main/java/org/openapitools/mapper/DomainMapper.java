package org.openapitools.mapper;

import org.openapitools.domain.*;
import org.openapitools.model.*;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DomainMapper {

    // ====== Conversion User Domain -> UserDTO ======
    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        dto.setUserID(user.getUserID());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        
        // Conversion du rôle String -> Enum
        try {
            dto.setRole(UserDTO.RoleEnum.fromValue(user.getRole()));
        } catch (IllegalArgumentException e) {
            dto.setRole(UserDTO.RoleEnum.USER); // Par défaut
        }
        
        dto.setIsAdmin(user.isAdmin());
        
        return dto;
    }

    // ====== Conversion UserCreate -> User Domain ======
    public static User toDomainUser(UserCreate userCreate, int userId) {
        if (userCreate == null) {
            return null;
        }
        
        String role = userCreate.getRole() != null ? userCreate.getRole().getValue() : "USER";
        return new User(userId, userCreate.getName(), userCreate.getEmail(), role);
    }

    // ====== Conversion Ticket Domain -> TicketDTO ======
    public static TicketDTO toTicketDTO(Ticket ticket, User assignedUser) {
        if (ticket == null) {
            return null;
        }
        
        TicketDTO dto = new TicketDTO();
        dto.setTicketID(ticket.getTicketID());
        dto.setTitle(ticket.getTitle());
        
        // Conversion statut
        try {
            dto.setStatus(TicketDTO.StatusEnum.fromValue(ticket.getStatus()));
        } catch (IllegalArgumentException e) {
            dto.setStatus(TicketDTO.StatusEnum.OUVERT);
        }
        
        // Conversion priorité
        try {
            dto.setPriority(TicketDTO.PriorityEnum.fromValue(ticket.getPriority()));
        } catch (IllegalArgumentException e) {
            dto.setPriority(TicketDTO.PriorityEnum.MOYENNE);
        }
        
        // Conversion des dates String -> OffsetDateTime
        dto.setCreationDate(convertToOffsetDateTime(ticket.getCreationDate()));
        dto.setUpdateDate(convertToOffsetDateTime(ticket.getUpdateDate()));
        dto.setAssignedUserId(ticket.getAssignedUserId());
        
        // Utilisation de JsonNullable pour setAssignedToName
        if (assignedUser != null) {
            dto.setAssignedToName(JsonNullable.of(assignedUser.getName()));
        } else {
            dto.setAssignedToName(JsonNullable.undefined());
        }
        
        // Conversion description
        if (ticket.getDescription() != null) {
            dto.setDescription(toDescriptionDTO(ticket.getDescription()));
        }
        
        // Conversion commentaires
        if (ticket.getComments() != null) {
            dto.setComments(new ArrayList<>(ticket.getComments()));
        }
        
        return dto;
    }

    // ====== Conversion TicketDescription Domain -> Description DTO ======
    public static Description toDescriptionDTO(TicketDescription desc) {
        if (desc == null) {
            return null;
        }
        
        Description dto = new Description();
        dto.setTextContent(desc.getTextContent());
        dto.setImagePaths(new ArrayList<>(desc.getImagePaths()));
        dto.setVideoPaths(new ArrayList<>(desc.getVideoPaths()));
        
        return dto;
    }

    // ====== Conversion Description DTO -> TicketDescription Domain ======
    public static TicketDescription toDomainDescription(Description desc) {
        if (desc == null) {
            return new TicketDescription();
        }
        
        TicketDescription domain = new TicketDescription(desc.getTextContent());
        
        if (desc.getImagePaths() != null) {
            for (String path : desc.getImagePaths()) {
                domain.addImagePaths(path);
            }
        }
        
        if (desc.getVideoPaths() != null) {
            for (String path : desc.getVideoPaths()) {
                domain.addVideoPath(path);
            }
        }
        
        return domain;
    }

    // ====== Conversion CreateTicketRequest -> Ticket Domain ======
    public static Ticket toDomainTicket(CreateTicketRequest request, int ticketId) {
        if (request == null) {
            return null;
        }
        
        TicketDescription desc = toDomainDescription(request.getDescription());
        String priority = request.getPriority() != null ? request.getPriority().getValue() : "MOYENNE";
        
        return new Ticket(ticketId, request.getTitle(), desc, priority);
    }

    // ====== Conversion List ======
    public static List<UserDTO> toUserDTOList(List<User> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(DomainMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public static List<TicketDTO> toTicketDTOList(List<Ticket> tickets, List<User> users) {
        if (tickets == null) {
            return new ArrayList<>();
        }
        return tickets.stream()
                .map(ticket -> {
                    User assignedUser = null;
                    if (ticket.getAssignedUserId() != 0 && users != null) {
                        assignedUser = users.stream()
                            .filter(u -> u.getUserID() == ticket.getAssignedUserId())
                            .findFirst()
                            .orElse(null);
                    }
                    return toTicketDTO(ticket, assignedUser);
                })
                .collect(Collectors.toList());
    }

    // ====== Méthode utilitaire pour convertir String -> OffsetDateTime ======
    private static OffsetDateTime convertToOffsetDateTime(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return OffsetDateTime.now();
        }
        try {
            // Essayer de parser comme ISO_DATE_TIME
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
            return localDateTime.atOffset(ZoneOffset.UTC);
        } catch (Exception e) {
            // Si le parsing échoue, retourner la date actuelle
            return OffsetDateTime.now();
        }
    }
}

