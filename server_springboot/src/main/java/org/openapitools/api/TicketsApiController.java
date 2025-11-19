package org.openapitools.api;

import org.openapitools.model.*;
import org.openapitools.service.TicketService;
import org.openapitools.service.UserService;
import org.openapitools.mapper.DomainMapper;
import org.openapitools.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-11-18T07:20:00.634681-05:00[America/Toronto]", comments = "Generator version: 7.10.0")
@Controller
@RequestMapping("${openapi.systmeDeGestionDeTicketsSBIRAPIREST.base-path:/api/v1}")
public class TicketsApiController implements TicketsApi {

    private final NativeWebRequest request;
    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public TicketsApiController(NativeWebRequest request, TicketService ticketService, UserService userService) {
        this.request = request;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    // ==================== TICKET CRUD ====================

    @Override
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest) {
        try {
            String title = createTicketRequest.getTitle();
            String priority = createTicketRequest.getPriority() != null ? 
                createTicketRequest.getPriority().getValue() : "MOYENNE";
            
            String description = "";
            if (createTicketRequest.getDescription() != null) {
                description = createTicketRequest.getDescription().getTextContent();
            }

            Ticket ticket = ticketService.createTicket(title, description, priority);
            
            if (createTicketRequest.getDescription() != null) {
                TicketDescription desc = DomainMapper.toDomainDescription(createTicketRequest.getDescription());
                ticket.setDescription(desc);
            }

            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getAllTickets(
            @Valid @RequestParam(value = "status", required = false) String status,
            @Valid @RequestParam(value = "assignedTo", required = false) Integer assignedTo,
            @Valid @RequestParam(value = "priority", required = false) String priority) {
        try {
            List<Ticket> tickets;
            
            if (status != null && !status.trim().isEmpty()) {
                tickets = ticketService.getTicketsByStatus(status);
            } else if (assignedTo != null) {
                tickets = ticketService.getTicketsByUserId(assignedTo);
            } else if (priority != null && !priority.trim().isEmpty()) {
                tickets = ticketService.getTicketsByPriority(priority);
            } else {
                tickets = ticketService.getAllTickets();
            }

            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable("ticketId") Integer ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody UpdateTicketRequest updateTicketRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String title = updateTicketRequest.getTitle();
            String priority = updateTicketRequest.getPriority() != null ? 
                updateTicketRequest.getPriority().getValue() : null;

            boolean updated = ticketService.updateTicket(ticketId, title, priority);
            if (!updated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTicket(@PathVariable("ticketId") Integer ticketId) {
        try {
            boolean deleted = ticketService.deleteTicket(ticketId);
            if (!deleted) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== STATUS OPERATIONS ====================

    @Override
    public ResponseEntity<List<String>> getAvailableTransitions(@PathVariable("ticketId") Integer ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<String> transitions = ticketService.getAvailableTransitions(ticketId);
            return new ResponseEntity<>(transitions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> updateTicketStatus(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody StatusUpdateDTO statusUpdateDTO) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String newStatus = statusUpdateDTO.getStatus().getValue();
            Integer requestedBy = statusUpdateDTO.getRequestedBy() != null ? 
                statusUpdateDTO.getRequestedBy() : 1;

            boolean updated = ticketService.updateTicketStatus(ticketId, newStatus, requestedBy);
            if (!updated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> updateTicketPriority(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody UpdateTicketPriorityRequest updateTicketPriorityRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String priority = updateTicketPriorityRequest.getPriority().getValue();
            Integer requestedBy = updateTicketPriorityRequest.getRequestedBy() != null ? 
                updateTicketPriorityRequest.getRequestedBy() : 1;

            boolean updated = ticketService.updateTicketPriority(ticketId, priority, requestedBy);
            if (!updated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== ASSIGNMENT OPERATIONS ====================

    @Override
    public ResponseEntity<TicketDTO> assignTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody AssignmentDTO assignmentDTO) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Integer userId = assignmentDTO.getUserId();
            Integer assignedBy = assignmentDTO.getAssignedBy() != null ? 
                assignmentDTO.getAssignedBy() : 1;

            boolean assigned = ticketService.assignTicket(ticketId, userId, assignedBy);
            if (!assigned) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = userService.getUserById(ticket.getAssignedUserId());

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> unassignTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody UnassignTicketRequest unassignTicketRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Integer requestedBy = unassignTicketRequest.getRequestedBy() != null ? 
                unassignTicketRequest.getRequestedBy() : 1;

            boolean unassigned = ticketService.unassignTicket(ticketId, requestedBy);
            if (!unassigned) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, null);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> closeTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody CloseTicketRequest closeTicketRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Integer userId = closeTicketRequest.getUserId() != null ? 
                closeTicketRequest.getUserId() : 1;

            boolean closed = ticketService.closeTicket(ticketId, userId);
            if (!closed) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            ticket = ticketService.getTicketById(ticketId);
            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, null);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== DESCRIPTION OPERATIONS ====================

    @Override
    public ResponseEntity<TicketDTO> updateTicketDescription(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody Description description) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String textContent = description.getTextContent();
            List<String> imagePaths = description.getImagePaths();
            List<String> videoPaths = description.getVideoPaths();

            boolean updated = ticketService.updateTicketDescription(ticketId, textContent, imagePaths, videoPaths);
            if (!updated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== MEDIA OPERATIONS ====================

    @Override
    public ResponseEntity<TicketDTO> addImageToTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody AddImageRequest addImageRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String imagePath = addImageRequest.getImagePath();
            boolean added = ticketService.addImageToTicket(ticketId, imagePath);
            if (!added) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GetTicketImages200Response> getTicketImages(@PathVariable("ticketId") Integer ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<String> imagePaths = ticketService.getTicketImages(ticketId);
            
            GetTicketImages200Response response = new GetTicketImages200Response();
            response.setTicketId(ticketId);
            response.setImagePaths(imagePaths);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TicketDTO> addVideoToTicket(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody AddVideoRequest addVideoRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String videoPath = addVideoRequest.getVideoPath();
            boolean added = ticketService.addVideoToTicket(ticketId, videoPath);
            if (!added) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            ticket = ticketService.getTicketById(ticketId);
            User assignedUser = null;
            if (ticket.getAssignedUserId() != 0) {
                assignedUser = userService.getUserById(ticket.getAssignedUserId());
            }

            TicketDTO ticketDTO = DomainMapper.toTicketDTO(ticket, assignedUser);
            return new ResponseEntity<>(ticketDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== COMMENT OPERATIONS ====================

    @Override
    public ResponseEntity<List<String>> getTicketComments(@PathVariable("ticketId") Integer ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<String> comments = ticketService.getComments(ticketId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> addComment(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestBody CommentRequest commentRequest) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String comment = commentRequest.getComment();
            Integer authorId = commentRequest.getAuthorId() != null ? 
                commentRequest.getAuthorId() : 1;

            boolean added = ticketService.addComment(ticketId, comment, authorId);
            if (!added) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> clearComments(@PathVariable("ticketId") Integer ticketId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            boolean cleared = ticketService.clearComments(ticketId);
            if (!cleared) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== SEARCH OPERATIONS ====================

    @Override
    public ResponseEntity<List<TicketDTO>> getTicketsByStatus(@PathVariable("status") String status) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByStatus(status);
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getTicketsByPriority(@PathVariable("priority") String priority) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByPriority(priority);
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(@PathVariable("userId") Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> searchTicketsByTitle(
            @NotNull @Valid @RequestParam(value = "title", required = true) String title) {
        try {
            if (title == null || title.trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<Ticket> tickets = ticketService.searchTicketsByTitle(title);
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getUnassignedTickets() {
        try {
            List<Ticket> tickets = ticketService.getUnassignedTickets();
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<TicketDTO>> getCriticalTickets() {
        try {
            List<Ticket> tickets = ticketService.getCriticalTickets();
            List<User> users = userService.getAllUsers();
            List<TicketDTO> ticketDTOs = DomainMapper.toTicketDTOList(tickets, users);
            return new ResponseEntity<>(ticketDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== EXPORT OPERATIONS ====================

    @Override
    public ResponseEntity<Resource> exportTicketToPDF(
            @PathVariable("ticketId") Integer ticketId,
            @Valid @RequestParam(value = "userId", required = false) Integer userId) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Integer exportUserId = userId != null ? userId : 1;
            String pdfContent = ticketService.exportTicketToPDF(ticketId, exportUserId);

            if (pdfContent == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            byte[] pdfBytes = pdfContent.getBytes();
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
