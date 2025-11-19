package org.openapitools.domain;

import java.util.ArrayList;
import java.util.List;

public class TicketDescription {
    private String textContent;
    private List<String> imagePaths;
    private List<String> videoPaths;

    public TicketDescription() {
        this.textContent = "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    public TicketDescription(String textContent) {
        this.textContent = textContent != null ? textContent : "";
        this.imagePaths = new ArrayList<>();
        this.videoPaths = new ArrayList<>();
    }

    public String getTextContent() {
        return textContent;
    }

    public List<String> getImagePaths() {
        return new ArrayList<>(imagePaths);
    }

    public List<String> getVideoPaths() {
        return new ArrayList<>(videoPaths);
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent != null ? textContent : "";
    }

    public void addImagePaths(String path) {
        if (path != null && !path.trim().isEmpty()) {
            imagePaths.add(path);
        }
    }

    public boolean removeImagePaths(String path) {
        return imagePaths.remove(path);
    }

    public void clearImagePaths() {
        if (!imagePaths.isEmpty()) {
            imagePaths.clear();
        }
    }

    public void addVideoPath(String path) {
        if (path != null && !path.trim().isEmpty()) {
            videoPaths.add(path);
        }
    }

    public boolean removeVideoPath(String path) {
        return videoPaths.remove(path);
    }

    public void clearVideos() {
        if (!videoPaths.isEmpty()) {
            videoPaths.clear();
        }
    }

    public boolean hasContent() {
        return textContent != null && !textContent.trim().isEmpty();
    }

    public boolean hasImages() {
        return !imagePaths.isEmpty();
    }

    public boolean hasVideos() {
        return !videoPaths.isEmpty();
    }

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

