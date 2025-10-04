import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        this.validStatuses = Arrays.asList("OUVERT", "ASSIGNÉ", "VALIDATION", "TERMINÉ");
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    // Constructeur avec paramètres personnalisés
    public ticketValidator(int minTitleLength, int maxTitleLength) {
        this.minTitleLength = minTitleLength;
        this.maxTitleLength = maxTitleLength;
        this.validStatuses = Arrays.asList("OUVERT", "ASSIGNÉ", "VALIDATION", "TERMINÉ");
        this.validPriorities = Arrays.asList("BASSE", "MOYENNE", "HAUTE", "CRITIQUE");
    }

    // Validation complète du ticket
    public boolean validateTicket(Ticket ticket) {
        if (ticket == null) {
            return false;
        }
        
        return validateTitle(ticket.getTitle()) &&
               validateStatus(ticket.getStatus()) &&
               validatePriority(ticket.getPriority());
    }

    // Validation du titre
    public boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        int length = title.trim().length();
        return length >= minTitleLength && length <= maxTitleLength;
    }

    // Validation du statut
    public boolean validateStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        return validStatuses.contains(status.toUpperCase());
    }

    // Validation de la priorité
    public boolean validatePriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return false;
        }
        return validPriorities.contains(priority.toUpperCase());
    }

    // Validation du créateur
    public boolean validateCreator(User creator) {
        return creator != null && creator.getUserID() > 0;
    }

    // Validation des dates
    public boolean validateDates(Date creationDate, Date updateDate) {
        if (creationDate == null || updateDate == null) {
            return false;
        }
        // La date de mise à jour ne peut pas être antérieure à la date de création
        return !updateDate.before(creationDate);
    }

    // Liste les erreurs de validation
    public List<String> getValidationErrors(Ticket ticket) {
        List<String> errors = new ArrayList<>();
        
        if (ticket == null) {
            errors.add("Le ticket est null");
            return errors;
        }

        // Validation du titre
        if (!validateTitle(ticket.getTitle())) {
            errors.add("Titre invalide (doit contenir entre " + minTitleLength + " et " + maxTitleLength + " caractères)");
        }

        // Validation du statut
        if (!validateStatus(ticket.getStatus())) {
            errors.add("Statut invalide: " + ticket.getStatus());
        }

        // Validation de la priorité
        if (!validatePriority(ticket.getPriority())) {
            errors.add("Priorité invalide: " + ticket.getPriority());
        }

        return errors;
    }

    // Validation pour la création d'un ticket
    public boolean isValidForCreation(Ticket ticket) {
        if (ticket == null) {
            return false;
        }
        
        return validateTitle(ticket.getTitle()) &&
               validatePriority(ticket.getPriority()) &&
               ticket.getStatus().equals("OUVERT");
    }

    // Validation pour la mise à jour d'un ticket
    public boolean isValidForUpdate(Ticket ticket) {
        if (ticket == null) {
            return false;
        }
        
        return validateTitle(ticket.getTitle()) &&
               validateStatus(ticket.getStatus()) &&
               validatePriority(ticket.getPriority());
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
