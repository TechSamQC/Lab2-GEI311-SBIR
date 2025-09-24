import java.util.List;

public class Admin {
    private int adminId;
    private String name;
    private String email;

    // Constructeur
    public Admin(int adminId, String name, String email) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Méthodes spécifiques à l'admin
    public void assignTicket(Ticket ticket, User user) {
        // Assigner un ticket à un utilisateur
        ticket.assignTo(user);
        if (ticket.getStatus().equals("ASSIGNÉ")) {
            System.out.println("Le ticket " + ticket.getTicketID() + " a été assigné à l'utilisateur " + user.getUserID() + ".");
        }
        else {
            System.out.println("Le ticket " + ticket.getTicketID() + " n'a pas pu être assigné.");
        }
    }

    public void closeTicket(Ticket ticket) {
        // Terminer un ticket
        ticket.updateStatus("TERMINÉ");
        if (ticket.getStatus().equals("TERMINÉ")) {
            System.out.println("Admin " + name + " a terminé le ticket " + ticket.getTicketID() + ".");
        }
        else {
            System.out.println("Le ticket " + ticket.getTicketID() + " n'a pas pu être terminé.");
        }
    }

    public void viewAllTickets(List<Ticket> tickets) {
        // Afficher tous les tickets
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}