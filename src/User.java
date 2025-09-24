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
        System.out.println(name + " a créé le ticket: " + ticket+ "\n");
    }

    public void viewTicket(Ticket ticket) {
        // Logic to view a ticket
        System.out.println("Visualisation du ticket: " + ticket+ "\n");
    }

    public void updateTicket(Ticket ticket) {
        // Logic to update a ticket
        ticket.updateStatus("EN COURS");
        System.out.println("Ticket mis à jour par " + name + ": " + ticket+ "\n");
    }   
    
}