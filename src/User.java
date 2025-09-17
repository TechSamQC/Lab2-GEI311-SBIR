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
        // Logic to create a ticket
        System.out.println("Ticket created by " + name + ": " + ticket);
    }
    public void viewTicket(Ticket ticket) {
        // Logic to view a ticket
        System.out.println("Viewing ticket: " + ticket);
    }
    public void updateTicket(Ticket ticket, String status) {
        // Logic to update a ticket
        ticket.setStatus(status);
        System.out.println("Ticket updated by " + name + ": " + ticket);
    }   
    
}