package org.openapitools.client;

import org.openapitools.client.gui.DisplayGUI;

import javax.swing.*;

/**
 * Classe principale pour lancer l'application cliente avec interface graphique
 * @author Il'aina Ratefinanahary et Samuel Brassard
 * @assistant Claude 3.5 Sonnet
 * @version 2.0 (Client REST)
 */
public class Main {
    
    // URL par défaut du serveur
    private static final String DEFAULT_SERVER_URL = "http://localhost:8080/api/v1";
    
    public static void main(String[] args) {
        // Configuration du Look and Feel pour une meilleure apparence
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Impossible de définir le Look and Feel: " + e.getMessage());
        }
        
        // Récupérer l'URL du serveur depuis les arguments ou utiliser la valeur par défaut
        String serverUrl = DEFAULT_SERVER_URL;
        if (args.length > 0) {
            serverUrl = args[0];
        }
        
        final String finalServerUrl = serverUrl;
        
        // Lancer l'interface graphique dans le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            System.out.println("=================================================");
            System.out.println("  Système de Gestion de Tickets - Client REST");
            System.out.println("=================================================");
            System.out.println("Connexion au serveur : " + finalServerUrl);
            System.out.println("Auteurs: Il'aina Ratefinanahary et Samuel Brassard");
            System.out.println("UQAC - Automne 2025 - 6GEI311");
            System.out.println("=================================================\n");
            
            try {
                // Créer et afficher la fenêtre principale
                new DisplayGUI(finalServerUrl);
                
                System.out.println("✓ Interface graphique lancée avec succès");
                System.out.println("✓ Connecté au serveur REST");
                System.out.println("\nPour arrêter l'application, fermez la fenêtre.\n");
                
            } catch (Exception e) {
                System.err.println("✗ Erreur lors du lancement de l'application:");
                System.err.println("  " + e.getMessage());
                System.err.println("\nVérifiez que le serveur Spring Boot est démarré sur " + finalServerUrl);
                
                JOptionPane.showMessageDialog(null,
                    "Erreur de connexion au serveur:\n" + e.getMessage() + 
                    "\n\nVérifiez que le serveur est démarré sur:\n" + finalServerUrl,
                    "Erreur de connexion",
                    JOptionPane.ERROR_MESSAGE);
                
                System.exit(1);
            }
        });
    }
}

