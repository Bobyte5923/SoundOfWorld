// Classes handle the on-screen layout of components, capturing user actions, and displaying results (the main application window).
package view;

import controller.GameController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Instrument;

public class MainWindow extends JFrame {
    private GameController controller; // Game logic handler
    private GameMenuBar menuBar; // Menu bar with lives and reset
    
    public MainWindow() {
        super("Sound of World - Guess the Instrument"); // Set window title
        setSize(900, 600); // Define initial window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close application on exit
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout()); // Use BorderLayout
        
        menuBar = new GameMenuBar(); // Create menu bar
        setJMenuBar(menuBar); // Set the menu bar for the window

        controller = new GameController(this); // Instantiate controller with this window

        controller.setMenuBar(menuBar); // Set the menu bar in the controller

        controller.resetGame(); // Start the game with initial setup

        setVisible(true); // Make the window visible

    }

    public void displayInstrumentsgrid() { // Display a grid of instrument guess panels
        getContentPane().removeAll();

        List<Instrument> instruments = controller.getRandomInstruments(6);
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        for (Instrument instrument : instruments) {
            gridPanel.add(controller.createGuessPanel(instrument));
        }

        add(gridPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    } 

    public void showMessage(String msg) { // Display a message dialog
        JOptionPane.showMessageDialog(this, msg);
    }

    public void updateLives(int lives) { // Update the lives displayed in the menu bar
        menuBar.setLives(lives);
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

    public void showVictoryDialog() { // Show a dialog when the player has guessed all instruments

        /*
        comme j'ai des promblème de liste d'instruments restant, je n'arrive pas à tester si Clippy fonctionne ou pas
        */

        ImageIcon victoryClippy = new ImageIcon(getClass().gatResource("/resources/images/imagesClippy/victoryclippy.gif"));
        JLabel gifLabel = new Jlabel(victoryClippy);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel messageLabel = new JLabel("<html>Bravo ! Tu as deviné tous les instruments ! 🎉<br>Rejouer ?</html>");
        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(gifLabel, BorderLayout.CENTER);

        int choice = JOptionPane.showOptionDialog(
            this,
            panel,
            "Bravo ! Tu as deviné tous les instruments ! 🎉\nRejouer ?",
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
