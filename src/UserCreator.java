public class UserCreator {
    private int nextUserID;
    private userValidator userValidator;

    // Constructeur
    public UserCreator() {
        this.nextUserID = 1;
        this.userValidator = new userValidator();
    }

    // Constructeur avec ID de départ personnalisé
    public UserCreator(int startingID) {
        this.nextUserID = startingID;
        this.userValidator = new userValidator();
    }

    // Création d'un utilisateur avec rôle par défaut
    public User createUser(String name, String email) {
        return createUser(name, email, assignDefaultRole());
    }

    // Création d'un utilisateur avec rôle spécifique
    public User createUser(String name, String email, String role) {
        // Validation avant création
        if (!validateBeforeCreation(name, email, role)) {
            System.out.println("Erreur: Impossible de créer l'utilisateur avec les données fournies.");
            return null;
        }

        // Génération de l'ID et création
        int userID = generateUserID();
        User user = new User(userID, name, email, role);

        System.out.println("Utilisateur créé avec succès: " + name + " (ID: " + userID + ", Rôle: " + role + ")");
        return user;
    }

    // Génère un nouvel ID unique
    public int generateUserID() {
        return nextUserID++;
    }

    // Retourne le rôle par défaut
    public String assignDefaultRole() {
        return "USER";
    }

    // Validation avant création
    public boolean validateBeforeCreation(String name, String email, String role) {
        if (!userValidator.validateName(name)) {
            System.out.println("Erreur: Nom invalide.");
            return false;
        }

        if (!userValidator.validateEmail(email)) {
            System.out.println("Erreur: Email invalide.");
            return false;
        }

        if (!userValidator.validateRole(role)) {
            System.out.println("Erreur: Rôle invalide. Rôles acceptés: " + userValidator.getValidRoles());
            return false;
        }

        return true;
    }

    // Getters
    public int getNextUserID() {
        return nextUserID;
    }

    public userValidator getUserValidator() {
        return userValidator;
    }

    // Setter pour réinitialiser l'ID
    public void setNextUserID(int nextUserID) {
        this.nextUserID = nextUserID;
    }
}

