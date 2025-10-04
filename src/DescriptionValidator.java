import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DescriptionValidator {
    private int maxTextLength;
    private int maxImagesCount;
    private int maxVideosCount;
    private List<String> allowedImageFormats;
    private List<String> allowedVideoFormats;

    // Constructeur avec valeurs par défaut
    public DescriptionValidator() {
        this.maxTextLength = 5000;
        this.maxImagesCount = 10;
        this.maxVideosCount = 5;
        this.allowedImageFormats = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
        this.allowedVideoFormats = Arrays.asList(".mp4", ".avi", ".mov");
    }

    // Constructeur avec paramètres personnalisés
    public DescriptionValidator(int maxTextLength, int maxImagesCount, int maxVideosCount) {
        this.maxTextLength = maxTextLength;
        this.maxImagesCount = maxImagesCount;
        this.maxVideosCount = maxVideosCount;
        this.allowedImageFormats = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
        this.allowedVideoFormats = Arrays.asList(".mp4", ".avi", ".mov");
    }

    // Validation complète de la description
    public boolean validateDescription(Description description) {
        if (description == null) {
            return false;
        }
        
        return validateTextContent(description.getTextContent()) &&
               validateImageCount(description.getImagePaths().size()) &&
               validateVideoCount(description.getVideoPaths().size()) &&
               validateAllImagePaths(description.getImagePaths()) &&
               validateAllVideoPaths(description.getVideoPaths());
    }

    // Validation du contenu texte
    public boolean validateTextContent(String text) {
        if (text == null) {
            return true; // Le texte peut être null/vide
        }
        return text.length() <= maxTextLength;
    }

    // Validation d'un chemin d'image
    public boolean validateImagePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        return validateImageFormat(path);
    }

    // Validation d'un chemin de vidéo
    public boolean validateVideoPath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return false;
        }
        return validateVideoFormat(path);
    }

    // Validation du format d'image
    public boolean validateImageFormat(String path) {
        if (path == null) {
            return false;
        }
        String lowerPath = path.toLowerCase();
        for (String format : allowedImageFormats) {
            if (lowerPath.endsWith(format)) {
                return true;
            }
        }
        return false;
    }

    // Validation du format de vidéo
    public boolean validateVideoFormat(String path) {
        if (path == null) {
            return false;
        }
        String lowerPath = path.toLowerCase();
        for (String format : allowedVideoFormats) {
            if (lowerPath.endsWith(format)) {
                return true;
            }
        }
        return false;
    }

    // Validation du nombre d'images
    public boolean validateImageCount(int count) {
        return count >= 0 && count <= maxImagesCount;
    }

    // Validation du nombre de vidéos
    public boolean validateVideoCount(int count) {
        return count >= 0 && count <= maxVideosCount;
    }

    // Validation de tous les chemins d'images
    private boolean validateAllImagePaths(List<String> paths) {
        for (String path : paths) {
            if (!validateImagePath(path)) {
                return false;
            }
        }
        return true;
    }

    // Validation de tous les chemins de vidéos
    private boolean validateAllVideoPaths(List<String> paths) {
        for (String path : paths) {
            if (!validateVideoPath(path)) {
                return false;
            }
        }
        return true;
    }

    // Liste les erreurs de validation
    public List<String> getValidationErrors(Description description) {
        List<String> errors = new ArrayList<>();
        
        if (description == null) {
            errors.add("La description est null");
            return errors;
        }

        // Vérification du texte
        if (!validateTextContent(description.getTextContent())) {
            errors.add("Le texte dépasse la longueur maximale de " + maxTextLength + " caractères");
        }

        // Vérification du nombre d'images
        int imageCount = description.getImagePaths().size();
        if (!validateImageCount(imageCount)) {
            errors.add("Nombre d'images invalide: " + imageCount + " (maximum: " + maxImagesCount + ")");
        }

        // Vérification du nombre de vidéos
        int videoCount = description.getVideoPaths().size();
        if (!validateVideoCount(videoCount)) {
            errors.add("Nombre de vidéos invalide: " + videoCount + " (maximum: " + maxVideosCount + ")");
        }

        // Vérification des chemins d'images
        for (String path : description.getImagePaths()) {
            if (!validateImagePath(path)) {
                errors.add("Chemin d'image invalide: " + path);
            }
        }

        // Vérification des chemins de vidéos
        for (String path : description.getVideoPaths()) {
            if (!validateVideoPath(path)) {
                errors.add("Chemin de vidéo invalide: " + path);
            }
        }

        return errors;
    }

    // Getters pour les formats autorisés
    public List<String> getAllowedImageFormats() {
        return new ArrayList<>(allowedImageFormats);
    }

    public List<String> getAllowedVideoFormats() {
        return new ArrayList<>(allowedVideoFormats);
    }

    public int getMaxTextLength() {
        return maxTextLength;
    }

    public int getMaxImagesCount() {
        return maxImagesCount;
    }

    public int getMaxVideosCount() {
        return maxVideosCount;
    }

    // Setters
    public void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }

    public void setMaxImagesCount(int maxImagesCount) {
        this.maxImagesCount = maxImagesCount;
    }

    public void setMaxVideosCount(int maxVideosCount) {
        this.maxVideosCount = maxVideosCount;
    }
}

