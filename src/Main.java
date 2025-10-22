import javax.swing.SwingUtilities;

/**
 * Point d'entrée principal du Système de Gestion de Tickets
 * Lance l'interface graphique (GUI)
 */
public class Main {
    public static void main(String[] args) {
        // Lancement de l'interface graphique dans le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new Display();
        });
    }
}
