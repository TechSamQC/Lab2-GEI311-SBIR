import java.time.LocalDate;
import java.util.ArrayList;
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
        System.out.println("Admin " + name + " a assigné le ticket " + ticket.getTicketID() + " à l'utilisateur " + user.getUserID() + ".");
    }

    public void closeTicket(Ticket ticket) {
        // Fermer un ticket
        ticket.updateStatus("FERMÉ");
        System.out.println("Admin " + name + " a fermé le ticket " + ticket.getTicketID() + ".");
    }

    public void viewAllTickets(List<Ticket> tickets) {
        // Afficher tous les tickets
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
}