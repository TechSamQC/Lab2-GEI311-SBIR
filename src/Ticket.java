import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticketID;
    private String title;
    private Description description;
    private String status;
    private String priority;
    private String creationDate;
    private String updateDate;
    private int assignedUserId;
    private List<String> comments;

    //Constructeur
    public Ticket(int ticketID, String title, Description description, String priority) {
        this.ticketID = ticketID;
        this.title = title;
        this.description = description;
        this.status = "OUVERT";
        this.priority = priority;
        this.creationDate = LocalDate.now().toString();
        this.updateDate = LocalDate.now().toString();
        this.assignedUserId = 0; // 0 signifie non assigné
        this.comments = new ArrayList<>();
    }

    // Getters and Setters
    public int getTicketID() {
        return ticketID;
    }

    public String getTitle() {
        return title;
    }

    public Description getDescription() {
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
        this.updateDate = LocalDate.now().toString();
    }

    public void setDescription(Description description) {
        this.description = description;
        this.updateDate = LocalDate.now().toString();
    }

    public void setPriority(String priority) {
        this.priority = priority;
        this.updateDate = LocalDate.now().toString();
    }

    // Méthode toString pour afficher les détails du ticket
    @Override
    public String toString() {
        return "Informations du ticket "+ ticketID + " (" + title + ") : \n" +
                "\t Description : " + description + ". \n" +
                "\t Status : " + status + "\n" +
                "\t Priorité : " + priority + "\n" +
                "\t Date de création : " + creationDate + "\n" +
                "\t Date de mise à jour : " + updateDate + "\n" +
                "\t ID de l'utilisateur assigné : " + assignedUserId + "\n" +
                "\t Commentaires : " + comments + "\n";
    }

    // Méthodes spécifiques au ticket
    public void assignTo(User user) {
        // Assigner un ticket à un utilisateur
        this.status = "ASSIGNÉ";
        this.assignedUserId = user.getUserID();
        this.updateDate = LocalDate.now().toString();
    }

    // Méthode interne pour désassigner un ticket (utilisée par AssignationManager)
    public void desassignTicketInternal(Ticket ticket) {
        ticket.status = "OUVERT";
        ticket.assignedUserId = 0;
        ticket.updateDate = LocalDate.now().toString();
    }

    public boolean isAssigned() {
        return this.assignedUserId != 0;
    }

    public void updateStatus(String status) { // Mettre à jour le statut du ticket
        this.status = status;
        this.updateDate = LocalDate.now().toString();
    }

    public void addComment(String comment) {
        // Ajouter un commentaire au ticket
        comments.add(comment);
        this.updateDate = LocalDate.now().toString();
    }

    public void clearComments() {
        comments.clear();
        this.updateDate = LocalDate.now().toString();
    }
}