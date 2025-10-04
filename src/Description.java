import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Description {
    private String textContent;
    private List<String> imagePaths;
    private List<String> videoPaths;
    private Date creationDate;
    private Date lastModified;

    // Constructeur par défaut
    public Description() {
        this.textContent = "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
        this.creationDate = new Date();
        this.lastModified = new Date();
    }

    // Constructeur avec texte
    public Description(String textContent) {
        this.textContent = textContent != null ? textContent : "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
        this.creationDate = new Date();
        this.lastModified = new Date();
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    // Setters
    public void setTextContent(String textContent) {
        this.textContent = textContent != null ? textContent : "";
        updateLastModified();
    }

    // Méthodes pour gérer les images
    public void addImagePath(String path) {
        if (path != null && !path.trim().isEmpty()) {
            imagePaths.add(path);
            updateLastModified();
        }
    }

    public boolean removeImagePath(String path) {
        boolean removed = imagePaths.remove(path);
        if (removed) {
            updateLastModified();
        }
        return removed;
    }

    public void clearImages() {
        if (!imagePaths.isEmpty()) {
            imagePaths.clear();
            updateLastModified();
        }
    }

    // Méthodes pour gérer les vidéos
    public void addVideoPath(String path) {
        if (path != null && !path.trim().isEmpty()) {
            videoPaths.add(path);
            updateLastModified();
        }
    }

    public boolean removeVideoPath(String path) {
        boolean removed = videoPaths.remove(path);
        if (removed) {
            updateLastModified();
        }
        return removed;
    }

    public void clearVideos() {
        if (!videoPaths.isEmpty()) {
            videoPaths.clear();
            updateLastModified();
        }
    }

    // Met à jour la date de dernière modification
    public void updateLastModified() {
        this.lastModified = new Date();
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
        
        summary.append(" | Images: ").append(imagePaths.size());
        summary.append(" | Vidéos: ").append(videoPaths.size());
        
        return summary.toString();
    }

    @Override
    public String toString() {
        return "Description{" +
                "textContent='" + (hasContent() ? textContent.substring(0, Math.min(50, textContent.length())) + "..." : "vide") + '\'' +
                ", images=" + imagePaths.size() +
                ", videos=" + videoPaths.size() +
                ", creationDate=" + creationDate +
                ", lastModified=" + lastModified +
                '}';
    }
}

