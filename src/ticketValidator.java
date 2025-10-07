import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ticketValidator {
    private int minTitleLength;
    private int maxTitleLength;
    private List<String> validStatuses;
    private List<String> validPriorities;

    // Constructeur avec valeurs par défaut
    public ticketValidator() {
        this.minTitleLength = 5;
        this.maxTitleLength = 200;
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    // Constructeur avec paramètres personnalisés
    public ticketValidator(int minTitleLength, int maxTitleLength) {
        this.minTitleLength = minTitleLength;
        this.maxTitleLength = maxTitleLength;
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    // Validation complète du ticket
    public boolean validateTicket(String title, String priority, int ticketID) {
        return validateTicketID(ticketID) &&
            validateTitle(title) &&
            validatePriority(priority);
    }

    // Validation de l'ID
    public boolean validateTicketID(int ticketID) {
        return ticketID >= 0;
    }

    // Validation du titre
    private boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        int length = title.trim().length();
        return length >= minTitleLength && length <= maxTitleLength;
    }

    // Validation de la priorité
    private boolean validatePriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return false;
        }
        return validPriorities.contains(priority.toUpperCase());
    }

    // Liste les erreurs de validation
    public List<String> getValidationErrors(String title, String priority, int ticketID) {
        List<String> errors = new ArrayList<>();

        // Validation de l'ID
        if (!validateTicketID(ticketID)) {
            errors.add("ID du ticket invalide (doit être un entier positif)");
        }

        // Validation du titre
        if (!validateTitle(title)) {
            errors.add("Titre invalide (doit contenir entre " + minTitleLength + " et " + maxTitleLength + " caractères)");
        }

        // Validation de la priorité
        if (!validatePriority(priority)) {
            errors.add("Priorité invalide: " + priority);
        }

        return errors;
    }

    // Getters
    public int getMinTitleLength() {
        return minTitleLength;
    }

    public int getMaxTitleLength() {
        return maxTitleLength;
    }

    public List<String> getValidStatuses() {
        return new ArrayList<>(validStatuses);
    }

    public List<String> getValidPriorities() {
        return new ArrayList<>(validPriorities);
    }

    // Setters
    public void setMinTitleLength(int minTitleLength) {
        this.minTitleLength = minTitleLength;
    }

    public void setMaxTitleLength(int maxTitleLength) {
        this.maxTitleLength = maxTitleLength;
    }
}
