public class User {
    private int userID;
    private String name;
    private String email;
    private String role;

    

    public User(int userID,String name,String email, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.role = role;
        
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void createTicket(Ticket ticket) {
        // Validation de nullité du ticket
        if (ticket == null) {
            System.out.println("Erreur: Impossible de créer un ticket null.");
            return;
        }
        
        // Validation des champs obligatoires du ticket
        if (ticket.getTitle() == null || ticket.getTitle().trim().isEmpty()) {
            System.out.println("Erreur: Le titre du ticket ne peut pas être vide.");
            return;
        }
        
        if (ticket.getDescription() == null || ticket.getDescription().trim().isEmpty()) {
            System.out.println("Erreur: La description du ticket ne peut pas être vide.");
            return;
        }
        
        // Logique de création de ticket
        System.out.println(name + " a créé le ticket: " + ticket + "\n");
    }

    public void viewTicket(Ticket ticket) {
        // Validation de nullité du ticket
        if (ticket == null) {
            System.out.println("Erreur: Impossible de visualiser un ticket null.");
            return;
        }
        
        // Logique de visualisation de ticket
        System.out.println("Visualisation du ticket: " + ticket+ "\n");
    }

    public void updateTicket(Ticket ticket) {
        // Validation de nullité du ticket
        if (ticket == null) {
            System.out.println("Erreur: Impossible de mettre à jour un ticket null.");
            return;
        }
        
        // Validation que l'utilisateur est assigné au ticket
        if (ticket.getAssignedUserId() != this.userID) {
            System.out.println("Erreur: Vous ne pouvez modifier que les tickets qui vous sont assignés.");
            return;
        }
        
        // Validation que le ticket n'est pas terminé
        if (ticket.getStatus().equals("TERMINÉ")) {
            System.out.println("Erreur: Impossible de modifier un ticket terminé.");
            return;
        }
        
        // Logique de mise à jour de ticket
        String currentStatus = ticket.getStatus();
        
        switch (currentStatus) {
            case "OUVERT":
                System.out.println("Erreur: Un utilisateur ne peut pas assigner un ticket ouvert. Contactez un administrateur.");
                break;
            case "ASSIGNÉ":
                ticket.updateStatus("VALIDATION");
                break;
            case "VALIDATION":
                System.out.println("Erreur: Seul un administrateur peut terminer un ticket en validation.");
                break;
            case "TERMINÉ":
                System.out.println("Le ticket est déjà terminé.");
                break;
            default:
                System.out.println("Statut inconnu pour le ticket " + ticket.getTicketID());
                break;
        }
    }   
}