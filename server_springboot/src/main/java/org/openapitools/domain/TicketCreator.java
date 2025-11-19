package org.openapitools.domain;

public class TicketCreator {
    private int nextTicketID;
    private DescriptionManager descriptionManager;
    private TicketManager ticketManager;

    public TicketCreator() {
        this.nextTicketID = 1;
        this.descriptionManager = new DescriptionManager();
    }

    public TicketCreator(int startingID) {
        this.nextTicketID = startingID;
        this.descriptionManager = new DescriptionManager();
    }

    public TicketCreator(int startingID, TicketManager ticketManager) {
        this.nextTicketID = startingID;
        this.ticketManager = ticketManager;
        this.descriptionManager = ticketManager.getDescriptionManager();
    }

    public Ticket createTicket(String title, User creator) {
        return createTicket(title, "", creator, "MOYENNE");
    }

    public Ticket createTicket(String title, String description, User creator) {
        return createTicket(title, description, creator, "MOYENNE");
    }

    public Ticket createTicket(String title, String description, User creator, String priority) {
        int ticketID = generateTicketID();

        TicketDescription desc = descriptionManager.createDescription(description);

        Ticket ticket = new Ticket(ticketID, title, desc, priority);

        if (ticketManager != null) {
            ticketManager.addTicket(ticket);
        }

        System.out.println("Ticket créé avec succès: " + title + " (ID: " + ticketID + ", Priorité: " + priority + ")");
        return ticket;
    }

    public int generateTicketID() {
        return nextTicketID++;
    }

    public int getNextTicketID() {
        return nextTicketID;
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }

    public void setNextTicketID(int nextTicketID) {
        this.nextTicketID = nextTicketID;
    }
}

