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
        // Validation de l'ID du ticket
        if (ticketID < 0) {
            System.out.println("Erreur: L'ID du ticket doit être un entier positif.");
            return;
        }
        this.ticketID = ticketID;

        // Validation du titre non vide
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Erreur: Le titre du ticket ne peut pas être vide.");
            return;
        }
        this.title = title;

        // Validation de la description non vide
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Erreur: La description du ticket ne peut pas être vide.");
            return;
        }
        this.description = description;

        this.status = "OUVERT";
        // Vérifier si la priorité est valide
        switch (priority) {
            case "BASSE":
            case "MOYENNE":
            case "HAUTE":
            case "CRITIQUE":
            case "URGENTE": // Support legacy
                this.priority = priority;
                break;
        
            default:
                System.out.println("Priorité invalide. La priorité doit être l'une des suivantes : BASSE, MOYENNE, HAUTE, CRITIQUE.");
                this.priority = "MOYENNE"; // Priorité par défaut
                break;
        }
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
        // Validation du titre non vide
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Erreur: Le titre du ticket ne peut pas être vide.");
            return;
        }
        this.title = title;
        this.updateDate = LocalDate.now().toString();
    }

    public void setDescription(String description) {
        // Validation de la description non vide
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Erreur: La description du ticket ne peut pas être vide.");
            return;
        }
        this.description = description;
        this.updateDate = LocalDate.now().toString();
    }

    public void setPriority(String priority) {
        // Vérifier si la priorité est valide
        switch (priority) {
            case "BASSE":
            case "MOYENNE":
            case "HAUTE":
            case "CRITIQUE":
            case "URGENTE": // Support legacy
                this.priority = priority;
                this.updateDate = LocalDate.now().toString();
                break;
        
            default:
                System.out.println("Priorité invalide. La priorité doit être l'une des suivantes : BASSE, MOYENNE, HAUTE, CRITIQUE.");
                break;
        }
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
            System.out.println("Impossible d'assigner un utilisateur, le ticket est terminé.");
            return;
        }
        // Validation de nullité
        if (user == null) {
            System.out.println("Erreur: Impossible d'assigner un ticket à un utilisateur null.");
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
                System.out.println("Impossible de faire cette manipulation, le ticket est terminé.");
                return;
        }
        // Vérifier si le statut est valide
        switch (status) {
            case "OUVERT": //SI le ticket est remis à ouvert, il est désassigné automatiquement
                if (assignedUserId != 0) {
                this.status = status;
                this.updateDate = LocalDate.now().toString();
                System.out.println("Le ticket " + ticketID + " est maintenant ouvert et a été automatiquement désassigné de l'utilisateur " + assignedUserId + ".");
                assignedUserId = 0;
                } else {
                System.out.println("Le ticket " + ticketID + " est déjà ouvert.");
                }
                break;
            case "VALIDATION": //SI le ticket n'est pas assigné, il ne peut pas être mis en validation
                if (this.status.equals("ASSIGNÉ")) {
                    this.status = status;
                    this.updateDate = LocalDate.now().toString();
                    System.out.println("Le statut du ticket " + ticketID + " a été mis à jour à : " + status);
                } else {
                    System.out.println("Un ticket ne peut être mis en validation que s'il est assigné d'abord.");
                }
                break;
            case "TERMINÉ": //SI le ticket n'est pas en validation, il ne peut pas être terminé
                if (this.status.equals("VALIDATION") || this.status.equals("OUVERT") || this.status.equals("ASSIGNÉ")) {
                    this.status = status;
                    this.updateDate = LocalDate.now().toString();
                    System.out.println("Le statut du ticket " + ticketID + " a été mis à jour à : " + status);
                } else {
                    System.out.println("Un ticket ne peut être terminé que depuis VALIDATION (normal) ou depuis OUVERT/ASSIGNÉ (fermeture directe).");
                }
                break;
                
            default: //SI le statut saisi n'est pas valide, il faut afficher un message d'erreur
                System.out.println("Statut invalide. Le statut doit être l'un des suivants : OUVERT, ASSIGNÉ, VALIDATION, TERMINÉ.");
                break;
        }
    }

    public void addComment(String comment) {
        //SI le ticket est terminé, on ne peut pas ajouter de commentaire
        if (this.status.equals("TERMINÉ")) {
            System.out.println("Impossible d'ajouter un commentaire, le ticket est terminé.");
            return;
        }
        //SI le ticket n'est pas assigné, on ne peut pas ajouter de commentaire
        else if (assignedUserId == 0) {
            System.out.println("Impossible d'ajouter un commentaire, le ticket n'est pas assigné.");
            return;
        }
        // Ajouter un commentaire au ticket
        comments.add(comment);
        this.updateDate = LocalDate.now().toString();
        System.out.println("Commentaire ajouté au ticket " + ticketID + " : " + comment);
    }
}