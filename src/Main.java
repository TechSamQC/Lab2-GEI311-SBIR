public class Main {
    public static void main(String[] args) {
        Ticket ticket = new Ticket(1, "Bug in login", "User unable to login", "HIGH", "2023-10-01", "2023-10-02");
        User user = new User(1, "Alice", "alice@example.com", "USER");
        ticket.assignTo(user);
    }
}