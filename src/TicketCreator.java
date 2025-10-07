public class TicketCreator {
    private int nextTicketID;
    private ticketValidator ticketValidator;
    private descriptionManager descriptionManager;

    // Constructeur
    public TicketCreator() {
        this.nextTicketID = 1;
        this.ticketValidator = new ticketValidator();
        this.descriptionManager = new descriptionManager();
    }

    // Constructeur avec ID de départ personnalisé
    public TicketCreator(int startingID) {
        this.nextTicketID = startingID;
        this.ticketValidator = new ticketValidator();
        this.descriptionManager = new descriptionManager();
    }

    // Création d'un ticket avec un manager de description personnalisé
    public TicketCreator(int startingID, descriptionManager customManager) {
        this.nextTicketID = startingID;
        this.ticketValidator = new ticketValidator();
        this.descriptionManager = customManager;
    }

    // Création simple d'un ticket (titre et créateur)
    public Ticket createTicket(String title, User creator) {
        return createTicket(title, "", creator, "MOYENNE");
    }

    // Création d'un ticket avec description
    public Ticket createTicket(String title, String description, User creator) {
        return createTicket(title, description, creator, "MOYENNE");
    }

    // Création complète d'un ticket avec priorité
    public Ticket createTicket(String title, String description, User creator, String priority) {
        // Génération de l'ID
        int ticketID = generateTicketID();
        
        // Validation avant création
        if (!validateBeforeCreation(ticketID, title, description, priority)) {
            System.out.println("Erreur: Impossible de créer le ticket avec les données fournies.");
            return null;
        }

        // Création de la description
        Description desc = descriptionManager.createDescription(description);

        // Création du ticket
        Ticket ticket = new Ticket(ticketID, title, desc, priority);

        // Initialisation du ticket
        initializeTicket(ticket);

        System.out.println("Ticket créé avec succès: " + title + " (ID: " + ticketID + ", Priorité: " + priority + ")");
        return ticket;
    }

    // Génère un nouvel ID unique
    public int generateTicketID() {
        return nextTicketID++;
    }

    // Initialise les valeurs par défaut d'un ticket (déjà fait dans le constructeur de Ticket)
    public void initializeTicket(Ticket ticket) {
        // Le statut est déjà initialisé à "OUVERT" dans le constructeur de Ticket
        // Les dates sont déjà initialisées dans le constructeur de Ticket
        // Cette méthode peut être étendue pour d'autres initialisations futures
    }

    // Validation avant création
    public boolean validateBeforeCreation(int ticketID, String title, String description, String priority) {
        if (!ticketValidator.validateTicket(title, priority, ticketID)) {
            System.out.println("Erreur(s) de création du ticket: ");
            ticketValidator.getValidationErrors(title, priority, ticketID).forEach(System.out::println);
            return false;
        }

        return true;
    }

    // Getters
    public int getNextTicketID() {
        return nextTicketID;
    }

    public ticketValidator getTicketValidator() {
        return ticketValidator;
    }

    public descriptionManager getDescriptionManager() {
        return descriptionManager;
    }

    // Setter pour réinitialiser l'ID
    public void setNextTicketID(int nextTicketID) {
        this.nextTicketID = nextTicketID;
    }
}

