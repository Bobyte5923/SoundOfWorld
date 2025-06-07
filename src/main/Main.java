package main;

import javax.swing.SwingUtilities;
import view.MainWindow;

/**
 * Entry point of the application. Starts the GUI.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
