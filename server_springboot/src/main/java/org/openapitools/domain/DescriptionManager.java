package org.openapitools.domain;

public class DescriptionManager {

    public DescriptionManager() {}

    public TicketDescription createDescription() {
        return new TicketDescription();
    }

    public TicketDescription createDescription(String textContent) {
        return new TicketDescription(textContent);
    }

    public boolean updateDescription(TicketDescription description, String newText) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        description.setTextContent(newText);
        System.out.println("Description mise à jour avec succès.");
        return true;
    }

    public boolean addImageToDescription(TicketDescription description, String imagePath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        if (imagePath == null || imagePath.trim().isEmpty()) {
            System.out.println("Erreur: Le chemin d'image est invalide.");
            return false;
        }

        description.addImagePaths(imagePath);
        System.out.println("Image ajoutée à la description: " + imagePath);
        return true;
    }

    public boolean addVideoToDescription(TicketDescription description, String videoPath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        if (videoPath == null || videoPath.trim().isEmpty()) {
            System.out.println("Erreur: Le chemin de vidéo est invalide.");
            return false;
        }

        description.addVideoPath(videoPath);
        System.out.println("Vidéo ajoutée à la description: " + videoPath);
        return true;
    }

    public boolean removeImageFromDescription(TicketDescription description, String imagePath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        boolean removed = description.removeImagePaths(imagePath);
        if (removed) {
            System.out.println("Image retirée de la description: " + imagePath);
        } else {
            System.out.println("Erreur: Image non trouvée dans la description.");
        }
        return removed;
    }

    public boolean removeVideoFromDescription(TicketDescription description, String videoPath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        boolean removed = description.removeVideoPath(videoPath);
        if (removed) {
            System.out.println("Vidéo retirée de la description: " + videoPath);
        } else {
            System.out.println("Erreur: Vidéo non trouvée dans la description.");
        }
        return removed;
    }

    public String getDescriptionSummary(TicketDescription description) {
        if (description == null) {
            return "Description null";
        }

        StringBuilder summary = new StringBuilder();
        summary.append("--- Résumé de la description ---\n");
        summary.append("Texte: ").append(description.getTextContent()).append("\n");
        summary.append("Images: ").append(description.getImagePaths()).append("\n");
        summary.append("Vidéos: ").append(description.getVideoPaths());

        return summary.toString();
    }
}

