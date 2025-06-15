package view;

import controller.GameController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Instrument;

/**
 * The main application window that initializes and displays all instrument guess panels.
 */
public class MainWindow extends JFrame {
    private final GameController controller;
    private GameMenuBar menuBar; 
    private InstrumentListPanel instrumentListPanel; 
    private JPanel gamePanel;
    //Am ajout: GameMenuBar et InstrumentListPanel et gamePanel

    public MainWindow() {
        super("Sound of World - Guess the Instrument");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        System.out.println("=== DEBUG: Initialisation MainWindow ==="); //Am ajout

        controller = new GameController(this); // Initialize the game controller
        System.out.println("DEBUG: GameController créé"); //Am ajout


        menuBar = new GameMenuBar(); // Create menu bar
        menuBar.addResetListener(e -> controller.resetGame());
        setJMenuBar(menuBar); // Set the menu bar for the window
        controller.setMenuBar(menuBar); // Link the menu bar to the controller
        System.out.println("DEBUG: MenuBar créé et configuré"); //Am debug
        //Am ajout: 4 lignes pour initialiser le menu bar

        instrumentListPanel = new InstrumentListPanel(controller.getAllInstrumentNames());
        add(instrumentListPanel, BorderLayout.WEST);
        System.out.println("DEBUG: InstrumentListPanel ajouté"); //Am Debug

        gamePanel = new JPanel();
        add(gamePanel, BorderLayout.CENTER);
        System.out.println("DEBUG: GamePanel ajouté"); //Am debug
        //Am ajout: 2 lignes pour initialiser le gamePanel

        controller.resetGame();
        System.out.println("DEBUG: resetGame() appelé"); //Am ajout et debug



        displayInstrumentGrid(); // Display the initial grid of instruments
        System.out.println("DEBUG: displayInstrumentGrid() appelé");//am¨
        setVisible(true); // Make the window visible
        System.out.println("DEBUG: Fenêtre rendue visible"); //am
    }

    public void displayInstrumentGrid() {

        System.out.println("=== DEBUG: displayInstrumentGrid() ===");
        
        gamePanel.removeAll();
        System.out.println("DEBUG: gamePanel nettoyé");

        List<Instrument> instruments = controller.getCurrentGameInstruments();
        System.out.println("DEBUG: Nombre d'instruments récupérés: " + instruments.size());
        
        if (instruments.isEmpty()) {
            System.out.println("ERREUR: Aucun instrument à afficher!");
            JLabel errorLabel = new JLabel("Aucun instrument trouvé!");
            gamePanel.add(errorLabel);
            gamePanel.revalidate();
            gamePanel.repaint();
            return;
        }

        gamePanel.setLayout(new GridLayout(2, 3, 10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        System.out.println("DEBUG: Layout configuré");

        for (int i = 0; i < instruments.size(); i++) {
            Instrument inst = instruments.get(i);
            System.out.println("DEBUG: Ajout instrument " + (i+1) + ": " + inst.getName());
            InstrumentGuessPanel panel = new InstrumentGuessPanel(inst, controller);
            gamePanel.add(panel);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
        System.out.println("DEBUG: Interface rafraîchie");
    }

    public void updateLives(int lives) { // Update the lives displayed in the menu bar
        menuBar.setLives(lives);
    }

    // Display the victory screen when the game is completed
    public void showVictoryDialog() {
        System.out.println("DEBUG: showVictoryDialog() appelé");
        
        try {
            ImageIcon victoryClippy = new ImageIcon(getClass().getResource("/resources/images/imagesClippy/victoryclippy.gif"));
            JLabel gifLabel = new JLabel(victoryClippy);

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            JLabel messageLabel = new JLabel("<html>Bravo ! Tu as deviné tous les instruments ! 🎉<br>Rejouer ?</html>");
            panel.add(messageLabel, BorderLayout.NORTH);
            panel.add(gifLabel, BorderLayout.CENTER);

            int choice = JOptionPane.showOptionDialog(
                this,
                panel,
                "Victoire !",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Rejouer", "Quitter"},
                "Rejouer"
            );

            if (choice == JOptionPane.YES_OPTION) {
                System.out.println("DEBUG: Joueur veut rejouer");
                controller.resetGame();
            } else {
                System.out.println("DEBUG: Joueur veut quitter");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("ERREUR dans showVictoryDialog: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback si l'image ne se charge pas
            int choice = JOptionPane.showOptionDialog(
                this,
                "Bravo ! Tu as deviné tous les instruments ! 🎉\nRejouer ?",
                "Victoire !",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Rejouer", "Quitter"},
                "Rejouer"
            );

            if (choice == JOptionPane.YES_OPTION) {
                controller.resetGame();
            } else {
                System.exit(0);
            }
        }
}

    public void showGameOverDialog() { // Show a dialog when the game is over
        int option = JOptionPane.showOptionDialog(
            this,
            "Game Over! Voulez-vous réinitialiser la partie ?",
            "Fin de la partie",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Réinitialiser", "Quitter"},
            "Réinitialiser"
        );

        if (option == JOptionPane.YES_OPTION) {
            controller.resetGame();
        } else {
            System.exit(0);
        }
    }

    
}
