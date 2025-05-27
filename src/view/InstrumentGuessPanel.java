// Classes handle the on-screen layout of components, capturing user actions, and displaying results (individual panel for guessing an instrument).
package view;

import controller.GameController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Instrument;

public class InstrumentGuessPanel extends JPanel { // GUI component for a single instrument guessing block
    private final Instrument instrument; // The instrument to guess
    private final GameController controller; // Reference to controller to trigger game logic

    private final JButton playButton = new JButton("Play Sound"); // Button to play the sound
    private final JTextField guessField = new JTextField(); // Input field for user guesses
    private final JPanel imagePanel = new JPanel(); // Area to display image after correct guess

    private boolean alreadyAnswered = false; // Prevents multiple submissions

    public InstrumentGuessPanel(Instrument instrument, GameController controller) {
        this.instrument = instrument;
        this.controller = controller;

        setLayout(new BorderLayout()); // Set layout to border layout
        setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add visual border

        playButton.addActionListener(e -> controller.playSound(instrument)); // Hook up sound playback
        add(playButton, BorderLayout.NORTH);

        imagePanel.setBackground(Color.LIGHT_GRAY); // Placeholder background
        add(imagePanel, BorderLayout.CENTER);

        guessField.addActionListener(new GuessHandler()); // Handle user submissions
        add(guessField, BorderLayout.SOUTH);
    }

    private class GuessHandler implements ActionListener { // Handles when user presses Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            if (alreadyAnswered) return;

            String userInput = guessField.getText().trim();

            if (controller.checkAnswer(userInput, instrument)) {
                showCorrect(); // If correct, display image
            } else {
                showIncorrect(); // If incorrect, show red background
            }
        }
    }

    private void showCorrect() { // Loads and displays the instrument image
        SwingUtilities.invokeLater(() -> {
            try {
                File file = new File("resources" + instrument.getImagePath().replace("/resources", ""));
                if (!file.exists()) {
                    throw new IOException("Image not found: " + file.getAbsolutePath());
                }

                Image image = ImageIO.read(file);
                JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

                imagePanel.removeAll();
                imagePanel.add(label);
                imagePanel.setBackground(Color.WHITE);

                guessField.setEnabled(false);
                playButton.setEnabled(false);
                alreadyAnswered = true;

                revalidate();
                repaint();
            } catch (IOException e) {
                controller.getWindow().showMessage("Image loading failed: " + e.getMessage());
            }
        });
    }

    private void showIncorrect() { // Red background to signal a wrong answer
        imagePanel.removeAll();
        imagePanel.setBackground(Color.RED);
        revalidate();
        repaint();
    }
}
