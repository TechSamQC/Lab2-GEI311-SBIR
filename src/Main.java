public class Main {
    public static void main(String[] args) {
        int TicketID = 1;
        int UserID = 1;
        Ticket ticket1 = new Ticket(TicketID, "Problème de connexion", "Impossible de se connecter au système", "HAUTE");
        User user1 = new User(UserID, "Alice", "alice@example.com", "USER");
        ticket1.assignTo(user1);
        ticket1.updateStatus("EN COURS");
        ticket1.addComment("L'utilisateur a été contacté pour plus de détails.");
        ticket1.addComment("Le problème semble être lié au serveur.");
        ticket1.updateStatus("RÉSOLU");
    }
}