import java.util.ArrayList;
import java.util.List;

public class Description {
    private String textContent;
    private List<String> imagePaths;
    private List<String> videoPaths;

    // Constructeur par défaut
    public Description() {
        this.textContent = "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    // Constructeur avec texte
    public Description(String textContent) {
        this.textContent = textContent != null ? textContent : "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    // Getters
    public String getTextContent() {
        return textContent;
    }

    public List<String> getImagePaths() {
        return new ArrayList<>(imagePaths);
    }

    public List<String> getVideoPaths() {
        return new ArrayList<>(videoPaths);
    }

    // Setters
    public void setTextContent(String textContent) {
        this.textContent = textContent != null ? textContent : "";
    }

    // Méthodes pour gérer les images
    public void addImagePaths(String path) {
        if (path != null && !path.trim().isEmpty()) {
            imagePaths.add(path);
        }
    }

    public boolean removeImagePaths(String path) {
        boolean removed = imagePaths.remove(path);
        if (removed) {
        }
        return removed;
    }

    public void clearImagePaths() {
        if (!imagePaths.isEmpty()) {
            imagePaths.clear();
        }
    }

    // Méthodes pour gérer les vidéos
    public void addVideoPath(String path) {
        if (path != null && !path.trim().isEmpty()) {
            videoPaths.add(path);
        }
    }

    public boolean removeVideoPath(String path) {
        boolean removed = videoPaths.remove(path);
        if (removed) {
        }
        return removed;
    }

    public void clearVideos() {
        if (!videoPaths.isEmpty()) {
            videoPaths.clear();
        }
    }

    // Vérifications
    public boolean hasContent() {
        return textContent != null && !textContent.trim().isEmpty();
    }

    public boolean hasImages() {
        return !imagePaths.isEmpty();
    }

    public boolean hasVideos() {
        return !videoPaths.isEmpty();
    }

    // Résumé du contenu
    public String getContentSummary() {
        StringBuilder summary = new StringBuilder();
        
        if (hasContent()) {
            String preview = textContent.length() > 100 
                ? textContent.substring(0, 100) + "..." 
                : textContent;
            summary.append("Texte: ").append(preview);
        } else {
            summary.append("Aucun texte");
        }
        
        return summary.toString();
    }

    @Override
    public String toString() {
        return ": " + textContent + ".";
    }
}

