import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticketID;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String creationDate;
    private String updateDate;
    private int assignedUserId;
    private List<String> comments;

    //Constructeur
    public Ticket(int ticketID, String title, String description,String priority) {
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

    public String getDescription() {
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

    public void setDescription(String description) {
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
        //SI le ticket est terminé, on ne peut pas l'assigner
        if (this.status.equals("TERMINÉ")) {
            System.out.println("Impossible d'assigner un utilisateur, le ticket est terminé.\n");
            return;
        }
        // Assigner un ticket à un utilisateur
        this.status = "ASSIGNÉ";
        this.assignedUserId = user.getUserID();
        this.updateDate = LocalDate.now().toString();
        System.out.println("Ticket " + ticketID + " assigné à l'utilisateur " + this.assignedUserId + ".");
    }

    public void updateStatus(String status) { // Mettre à jour le statut du ticket
        //SI le ticket est terminé, il n'est pas dans un état permettant la modification du statut
        if (this.status.equals("TERMINÉ")) {
                System.out.println("Impossible de faire cette manipulation, le ticket est terminé.\n");
                return;
        }
        // Vérifier si le statut est valide
        switch (status) {
            case "OUVERT": //SI le ticket est remis à ouvert, il est désassigné automatiquement
                if (assignedUserId != 0) {
                this.status = status;
                this.updateDate = LocalDate.now().toString();
                System.out.println("Le ticket " + ticketID + " est maintenant ouvert et a été automatiquement désassigné de l'utilisateur " + assignedUserId + ".\n");
                assignedUserId = 0;
                } else {
                System.out.println("Le ticket " + ticketID + " est déjà ouvert.\n");
                }
                break;
            case "VALIDATION": //SI le ticket n'est pas assigné, il ne peut pas être mis en validation
                if (this.status.equals("ASSIGNÉ")) {
                    this.status = status;
                    this.updateDate = LocalDate.now().toString();
                    System.out.println("Le statut du ticket " + ticketID + " a été mis à jour à : " + status);
                } else {
                    System.out.println("Un ticket ne peut être mis en validation que s'il est assigné d'abord.\n");
                }
                break;
            case "TERMINÉ": //SI le ticket n'est pas en validation, il ne peut pas être terminé
                if (this.status.equals("VALIDATION")) {
                    this.status = status;
                    this.updateDate = LocalDate.now().toString();
                    System.out.println("Le statut du ticket " + ticketID + " a été mis à jour à : " + status);
                } else {
                    System.out.println("Un ticket ne peut être terminé que s'il a été validé d'abord.\n");
                }
                break;
                
            default: //SI le statut saisi n'est pas valide, il faut afficher un message d'erreur
                System.out.println("Statut invalide. Le statut doit être l'un des suivants : OUVERT, ASSIGNÉ, VALIDATION, TERMINÉ. \n");
                break;
        }
    }

    public void addComment(String comment) {
        //SI le ticket est terminé, on ne peut pas ajouter de commentaire
        if (this.status.equals("TERMINÉ")) {
            System.out.println("Impossible d'ajouter un commentaire, le ticket est terminé.\n");
            return;
        }
        //SI le ticket n'est pas assigné, on ne peut pas ajouter de commentaire
        else if (assignedUserId == 0) {
            System.out.println("Impossible d'ajouter un commentaire, le ticket n'est pas assigné.\n");
            return;
        }
        // Ajouter un commentaire au ticket
        comments.add(comment);
        this.updateDate = LocalDate.now().toString();
        System.out.println("Commentaire ajouté au ticket " + ticketID + " : " + comment);
    }
}