public class TicketCreator {
    private int nextTicketID;
    private ticketValidator ticketValidator;
    private DescriptionValidator descriptionValidator;

    // Constructeur
    public TicketCreator() {
        this.nextTicketID = 1;
        this.ticketValidator = new ticketValidator();
        this.descriptionValidator = new DescriptionValidator();
    }

    // Constructeur avec ID de départ personnalisé
    public TicketCreator(int startingID) {
        this.nextTicketID = startingID;
        this.ticketValidator = new ticketValidator();
        this.descriptionValidator = new DescriptionValidator();
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
        // Validation avant création
        if (!validateBeforeCreation(title, description, creator)) {
            System.out.println("Erreur: Impossible de créer le ticket avec les données fournies.");
            return null;
        }

        // Validation de la priorité
        if (!ticketValidator.validatePriority(priority)) {
            System.out.println("Erreur: Priorité invalide. Utilisation de la priorité par défaut (MOYENNE).");
            priority = "MOYENNE";
        }

        // Génération de l'ID et création
        int ticketID = generateTicketID();
        Ticket ticket = new Ticket(ticketID, title, description, priority);
        
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
    public boolean validateBeforeCreation(String title, String description, User creator) {
        // Validation du titre
        if (!ticketValidator.validateTitle(title)) {
            System.out.println("Erreur: Titre invalide.");
            return false;
        }

        // Validation du créateur
        if (creator == null) {
            System.out.println("Erreur: Le créateur ne peut pas être null.");
            return false;
        }

        if (!ticketValidator.validateCreator(creator)) {
            System.out.println("Erreur: Créateur invalide.");
            return false;
        }

        // La description peut être vide mais pas null
        if (description == null) {
            System.out.println("Erreur: La description ne peut pas être null.");
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

    public DescriptionValidator getDescriptionValidator() {
        return descriptionValidator;
    }

    // Setter pour réinitialiser l'ID
    public void setNextTicketID(int nextTicketID) {
        this.nextTicketID = nextTicketID;
    }
}

