package view;

import controller.GameController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Instrument;

public class InstrumentGuessPanel extends JPanel {
    private final Instrument instrument;
    private final GameController controller;

    private final JButton playButton = new JButton("Play Sound");
    private final JTextField guessField = new JTextField();
    private final JPanel imagePanel = new JPanel();

    private boolean alreadyAnswered = false;

    public InstrumentGuessPanel(Instrument instrument, GameController controller) {
        this.instrument = instrument;
        this.controller = controller;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Button at the top to play the sound of the instrument
        playButton.addActionListener(e -> controller.playSound(instrument));
        add(playButton, BorderLayout.NORTH);

        // Center area where the image will be shown if answer is correct
        imagePanel.setBackground(Color.LIGHT_GRAY);
        add(imagePanel, BorderLayout.CENTER);

        // Input field for user to type the guessed instrument name
        guessField.addActionListener(new GuessHandler());
        add(guessField, BorderLayout.SOUTH);
    }

    // Handle guess when user presses Enter
    private class GuessHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (alreadyAnswered) return;

            String userInput = guessField.getText().trim();

            // Check if guess is correct
            if (controller.checkAnswer(userInput, instrument)) {
                showCorrect();
            } else {
                showIncorrect();
            }
        }
    }

    // Display image if the user guessed correctly
    private void showCorrect() {
        // Run image update safely in Swing thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Load image using absolute file path from project structure
                File file = new File("resources" + instrument.getImagePath().replace("/resources", ""));
                if (!file.exists()) {
                    throw new IOException("Image not found: " + file.getAbsolutePath());
                }

                // Load and scale the image
                Image image = ImageIO.read(file);
                JLabel label = new JLabel(new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

                // Clear and display image
                imagePanel.removeAll();
                imagePanel.add(label);
                imagePanel.setBackground(Color.WHITE);

                // Lock input and sound after success
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

    // Mark the panel in red for wrong answer
    private void showIncorrect() {
        imagePanel.removeAll();
        imagePanel.setBackground(Color.RED);
        revalidate();
        repaint();
    }
}
