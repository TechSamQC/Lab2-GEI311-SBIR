import javax.swing.SwingUtilities;

/**
 * Point d'entrée principal du Système de Gestion de Tickets
 * Lance l'interface graphique (GUI)
 */
public class Main {
    public static void main(String[] args) {
        // Lancement de l'interface graphique dans le thread EDT (Event Dispatch Thread) pour garantir que l'interface est
        // exécutée en toute sécurité dans le contexte de l'interface utilisateur.
        SwingUtilities.invokeLater(() -> {
            new Display();
        });
    }
}