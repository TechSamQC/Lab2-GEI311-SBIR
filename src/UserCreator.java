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
        return createUser(name, email, "USER"); //Le rôle par défaut est "USER"
    }

    // Création d'un utilisateur avec rôle spécifique
    public User createUser(String name, String email, String role) {
        // Génération de l'ID
        int userID = generateUserID();
        // Validation avant création
        if (!validateBeforeCreation(userID, name, email, role)) {
            System.out.println("Erreur: Impossible de créer l'utilisateur avec les données fournies.");
            return null;
        }

        // Création de l'utilisateur
        User user = new User(userID, name, email, role);

        System.out.println("Utilisateur créé avec succès: " + name + " (ID: " + userID + ", Rôle: " + role + ")");
        return user;
    }

    // Génère un nouvel ID unique
    public int generateUserID() {
        return nextUserID++;
    }

    // Validation avant création
    public boolean validateBeforeCreation(int userID, String name, String email, String role) {
        if (!userValidator.validateUser(name, email, role, userID)) {
            System.out.println("Erreur(s) de création de l'utilisateur: ");
            userValidator.getValidationErrors(name, email, role, userID).forEach(System.out::println);
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

