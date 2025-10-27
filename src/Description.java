import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Description {
    private String textContent;
    private List<ImageIcon> images;
    private List<String> videoPaths;

    // Constructeur par défaut
    public Description() {
        this.textContent = "";
        this.images = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    // Constructeur avec texte
    public Description(String textContent) {
        this.textContent = textContent != null ? textContent : "";
        this.images = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    // Getters
    public String getTextContent() {
        return textContent;
    }

    public List<ImageIcon> getImages() {
        return new ArrayList<>(images);
    }

    public List<String> getVideoPaths() {
        return new ArrayList<>(videoPaths);
    }

    // Setters
    public void setTextContent(String textContent) {
        this.textContent = textContent != null ? textContent : "";
    }

    // Méthodes pour gérer les images
    public void addImage(String path) {
        if (path != null && !path.trim().isEmpty()) {
            ImageIcon icon = new ImageIcon(path);
            images.add(icon);
        }
    }

    public boolean removeImage(ImageIcon image) {
        boolean removed = images.remove(image);
        if (removed) {
        }
        return removed;
    }

    public void clearImages() {
        if (!images.isEmpty()) {
            images.clear();
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
        return !images.isEmpty();
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

