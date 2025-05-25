package view;

import controller.GameController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Instrument;

public class MainWindow extends JFrame {
    private GameController controller; // Game logic handler

    public MainWindow() {
        super("Sound of World - Guess the Instrument"); // Set window title
        setSize(900, 600); // Define initial window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close application on exit
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout()); // Use BorderLayout

        controller = new GameController(this); // Instantiate controller with this window

        List<Instrument> randomInstruments = controller.getRandomInstruments(6); // Choose 6 instruments randomly
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // Create a 2x3 grid

        for (Instrument instrument : randomInstruments) {
            gridPanel.add(controller.createGuessPanel(instrument)); // Add a panel per instrument
        }

        add(gridPanel, BorderLayout.CENTER); // Add to window center
        setVisible(true); // Show the window
    }

    public void showMessage(String msg) { // Display a message dialog
        JOptionPane.showMessageDialog(this, msg);
    }
}
