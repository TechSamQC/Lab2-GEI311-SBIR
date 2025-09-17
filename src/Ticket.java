public class Ticket {
    private int ticketID;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String createDate;
    private String updateDate;
    private int assignedUserId;

    //Constructeur
    public Ticket(int ticketID, String title, String description,
     String priority, String createDate, String updateDate) {
        this.ticketID = ticketID;
        this.title = title;
        this.description = description;
        this.status = "OUVERT";
        this.priority = priority;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    // Getters and Setters
    public int getTicketID() {
        return ticketID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void assignTo(User user) {
        // Assigner un ticket à un utilisateur
        this.status = "ASSIGNÉ";
        System.out.println("Ticket " + ticketID + " assigné à l'utilisateur : " + user.getName());
    }

    public void updateStatus(String status) {
        // Mettre à jour le statut du ticket
        this.status = status;
        System.out.println("Le statut du ticket " + ticketID + " a été mis à jour à : " + status);
    }

    public void addComment(String comment) {
        // Ajouter un commentaire au ticket
        System.out.println("Commentaire ajouté au ticket " + ticketID + " : " + comment);
    }
}