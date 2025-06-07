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

    public MainWindow() {
        super("Sound of World - Guess the Instrument");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        controller = new GameController(this);

        List<Instrument> instruments = controller.getRandomInstruments(6);
        JPanel grid = new JPanel(new GridLayout(2, 3, 10, 10));

        for (Instrument inst : instruments) {
            grid.add(new InstrumentGuessPanel(inst, controller));
        }

        add(grid, BorderLayout.CENTER);
        setVisible(true);
    }

    // Display the victory screen when the game is completed
    public void showVictoryScreen() {
        getContentPane().removeAll();
        JLabel victory = new JLabel(new ImageIcon("resources/images/victory.jpg"));
        victory.setHorizontalAlignment(SwingConstants.CENTER);
        add(victory, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
