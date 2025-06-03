package view;

import java.awt.event.ActionListener;
import javax.swing.*;

public class GameMenuBar extends JMenuBar {

    private JButton resetButton;
    private JLabel livesLabel;

    public GameMenuBar() {

        resetButton = new JButton("Réinitialiser");
        livesLabel = new JLabel("Vies: 3");

        this.add(resetButton);
        this.add(Box.createHorizontalGlue()); // Adds flexible space between reset button and lives label
        this.add(livesLabel);
    }

    public void setLives(int lives) {
        livesLabel.setText("Vies: " + lives);
    }

    public void addResetListener(ActionListener listener) {
        resetButton.addActionListener(listener);

    }
}