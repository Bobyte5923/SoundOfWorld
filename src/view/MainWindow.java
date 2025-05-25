// MainWindow.java
package view;

import controller.GameController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Instrument;

public class MainWindow extends JFrame {
    private GameController controller;

    public MainWindow() {
        super("Sound of World - Guess the Instrument");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        controller = new GameController(this);

        // Load 6 random instruments for this session
        List<Instrument> randomInstruments = controller.getRandomInstruments(6);
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        // Create one panel per instrument placeholder
        for (Instrument instrument : randomInstruments) {
            gridPanel.add(controller.createGuessPanel(instrument));
        }

        add(gridPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Used for showing user dialogs from other components
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
} 
