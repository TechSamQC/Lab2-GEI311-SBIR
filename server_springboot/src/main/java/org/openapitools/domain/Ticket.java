package org.openapitools.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticketID;
    private String title;
    private TicketDescription description;
    private String status;
    private String priority;
    private String creationDate;
    private String updateDate;
    private int assignedUserId;
    private List<String> comments;

    public Ticket(int ticketID, String title, TicketDescription description, String priority) {
        this.ticketID = ticketID;
        this.title = title;
        this.description = description;
        this.status = "OUVERT";
        this.priority = priority;
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.assignedUserId = 0;
        this.comments = new ArrayList<>();
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getTitle() {
        return title;
    }

    public TicketDescription getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setDescription(TicketDescription description) {
        this.description = description;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setPriority(String priority) {
        this.priority = priority;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String toString() {
        return "Ticket " + ticketID + " [" + title + "]";
    }

    public void assignTo(User user) {
        this.assignedUserId = user.getUserID();
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void desassignTicketInternal(Ticket ticket) {
        ticket.assignedUserId = 0;
        ticket.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public boolean isAssigned() {
        return this.assignedUserId != 0;
    }

    public void updateStatus(String status) {
        this.status = status;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void addComment(String comment) {
        comments.add(comment);
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void clearComments() {
        comments.clear();
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}

