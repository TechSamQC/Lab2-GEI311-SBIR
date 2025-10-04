import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class userValidator {
    private String emailPattern;
    private Pattern emailRegex;
    private List<String> validRoles;
    private int minNameLength;
    private int maxNameLength;

    // Constructeur avec valeurs par défaut
    public userValidator() {
        this.emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        this.emailRegex = Pattern.compile(emailPattern);
        this.validRoles = Arrays.asList("USER", "DEVELOPER", "ADMIN");
        this.minNameLength = 2;
        this.maxNameLength = 100;
    }

    // Constructeur avec paramètres personnalisés
    public userValidator(int minNameLength, int maxNameLength) {
        this.emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        this.emailRegex = Pattern.compile(emailPattern);
        this.validRoles = Arrays.asList("USER", "DEVELOPER", "ADMIN");
        this.minNameLength = minNameLength;
        this.maxNameLength = maxNameLength;
    }

    // Validation complète de l'utilisateur
    public boolean validateUser(User user) {
        if (user == null) {
            return false;
        }
        
        return validateName(user.getName()) &&
               validateEmail(user.getEmail()) &&
               validateRole(user.getRole()) &&
               validateUserID(user.getUserID());
    }

    // Validation du nom
    public boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        int length = name.trim().length();
        return length >= minNameLength && length <= maxNameLength;
    }

    // Validation de l'email
    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return isValidEmailFormat(email);
    }

    // Validation du rôle
    public boolean validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        return validRoles.contains(role.toUpperCase());
    }

    // Validation de l'ID utilisateur
    public boolean validateUserID(int userID) {
        return userID > 0;
    }

    // Vérification du format de l'email avec regex
    public boolean isValidEmailFormat(String email) {
        if (email == null) {
            return false;
        }
        return emailRegex.matcher(email).matches();
    }

    // Liste les erreurs de validation
    public List<String> getValidationErrors(User user) {
        List<String> errors = new ArrayList<>();
        
        if (user == null) {
            errors.add("L'utilisateur est null");
            return errors;
        }

        // Validation du nom
        if (!validateName(user.getName())) {
            errors.add("Nom invalide (doit contenir entre " + minNameLength + " et " + maxNameLength + " caractères)");
        }

        // Validation de l'email
        if (!validateEmail(user.getEmail())) {
            errors.add("Email invalide: " + user.getEmail());
        }

        // Validation du rôle
        if (!validateRole(user.getRole())) {
            errors.add("Rôle invalide: " + user.getRole());
        }

        // Validation de l'ID
        if (!validateUserID(user.getUserID())) {
            errors.add("ID utilisateur invalide: " + user.getUserID());
        }

        return errors;
    }

    // Getters
    public List<String> getValidRoles() {
        return new ArrayList<>(validRoles);
    }

    public String getEmailPattern() {
        return emailPattern;
    }

    public int getMinNameLength() {
        return minNameLength;
    }

    public int getMaxNameLength() {
        return maxNameLength;
    }

    // Setters
    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }
}
