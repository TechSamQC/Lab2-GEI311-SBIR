public class descriptionManager {
    private DescriptionValidator descriptionValidator;

    // Constructeur
    public descriptionManager() {
        this.descriptionValidator = new DescriptionValidator();
    }

    // Constructeur avec validateur personnalisé
    public descriptionManager(DescriptionValidator validator) {
        this.descriptionValidator = validator;
    }

    // Crée une nouvelle description vide
    public Description createDescription() {
        return new Description();
    }

    // Crée une nouvelle description avec du texte
    public Description createDescription(String textContent) {
        Description description = new Description(textContent);
        
        if (!validateDescription(description)) {
            System.out.println("Attention: La description créée contient des erreurs de validation.");
        }
        
        return description;
    }

    // Met à jour le texte d'une description
    public boolean updateDescription(Description description, String newText) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        if (!descriptionValidator.validateTextContent(newText)) {
            System.out.println("Erreur: Le nouveau texte dépasse la longueur maximale.");
            return false;
        }

        description.setTextContent(newText);
        System.out.println("Description mise à jour avec succès.");
        return true;
    }

    // Ajoute une image à une description
    public boolean addImageToDescription(Description description, String imagePath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        if (!descriptionValidator.validateImagePath(imagePath)) {
            System.out.println("Erreur: Le chemin d'image est invalide ou le format n'est pas accepté.");
            return false;
        }

        int currentCount = description.getImagePaths().size();
        if (!descriptionValidator.validateImageCount(currentCount + 1)) {
            System.out.println("Erreur: Nombre maximum d'images atteint.");
            return false;
        }

        description.addImagePath(imagePath);
        System.out.println("Image ajoutée à la description: " + imagePath);
        return true;
    }

    // Ajoute une vidéo à une description
    public boolean addVideoToDescription(Description description, String videoPath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        if (!descriptionValidator.validateVideoPath(videoPath)) {
            System.out.println("Erreur: Le chemin de vidéo est invalide ou le format n'est pas accepté.");
            return false;
        }

        int currentCount = description.getVideoPaths().size();
        if (!descriptionValidator.validateVideoCount(currentCount + 1)) {
            System.out.println("Erreur: Nombre maximum de vidéos atteint.");
            return false;
        }

        description.addVideoPath(videoPath);
        System.out.println("Vidéo ajoutée à la description: " + videoPath);
        return true;
    }

    // Retire une image d'une description
    public boolean removeImageFromDescription(Description description, String imagePath) {
        if (description == null) {
            System.out.println("Erreur: La description est null.");
            return false;
        }

        boolean removed = description.removeImagePath(imagePath);
        if (removed) {
            System.out.println("Image retirée de la description: " + imagePath);
        } else {
            System.out.println("Erreur: Image non trouvée dans la description.");
        }
        return removed;
    }

    // Retire une vidéo d'une description
    public boolean removeVideoFromDescription(Description description, String videoPath) {
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

    // Valide une description
    public boolean validateDescription(Description description) {
        return descriptionValidator.validateDescription(description);
    }

    // Exporte une description en PDF (simulation)
    public boolean exportDescriptionToPDF(Description description, String filePath) {
        if (description == null || filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Erreur: Description ou chemin de fichier invalide.");
            return false;
        }

        // Simulation de l'export PDF
        System.out.println("\n=== Export PDF de la description ===");
        System.out.println("Fichier: " + filePath);
        System.out.println("Contenu de la description:");
        System.out.println(getDescriptionSummary(description));
        System.out.println("\n[Simulation] La description a été exportée vers: " + filePath);
        
        return true;
    }

    // Obtient un résumé de la description
    public String getDescriptionSummary(Description description) {
        if (description == null) {
            return "Description null";
        }

        StringBuilder summary = new StringBuilder();
        summary.append("\n--- Résumé de la description ---\n");
        summary.append("Texte: ").append(description.hasContent() ? 
            (description.getTextContent().length() > 100 ? 
                description.getTextContent().substring(0, 100) + "..." : 
                description.getTextContent()) : 
            "Aucun texte").append("\n");
        summary.append("Nombre d'images: ").append(description.getImagePaths().size()).append("\n");
        summary.append("Nombre de vidéos: ").append(description.getVideoPaths().size()).append("\n");
        summary.append("Date de création: ").append(description.getCreationDate()).append("\n");
        summary.append("Dernière modification: ").append(description.getLastModified()).append("\n");
        
        return summary.toString();
    }

    // Obtient le validateur
    public DescriptionValidator getDescriptionValidator() {
        return descriptionValidator;
    }

    // Définit un nouveau validateur
    public void setDescriptionValidator(DescriptionValidator validator) {
        this.descriptionValidator = validator;
    }
}
