package main; // Package declaration
import view.MainWindow; // Importing the MainWindow class from the view package

public class Main { 
    public static void main(String[] args) { // Entry point of the Java application
        javax.swing.SwingUtilities.invokeLater(() -> new MainWindow()); // Ensures GUI updates are performed on the Event Dispatch Thread
    }
}
