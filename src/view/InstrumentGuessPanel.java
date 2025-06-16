package view;

import controller.GameController;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Instrument;

/**
 * One guess panel: plays sound, checks answer, and shows image if correct.
 */
public class InstrumentGuessPanel extends JPanel {
    private final Instrument instrument;
    private final GameController controller;
    private final JButton playButton;
    private final JTextField guessField;
    private final JLabel imageLabel;
    private boolean alreadyAnswered = false;

    public InstrumentGuessPanel(Instrument instrument, GameController controller) {
        this.instrument = instrument;
        this.controller = controller;

        setPreferredSize(new Dimension(250, 250));
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        playButton = new JButton("▶ Play");
        guessField = new JTextField();
        imageLabel = new JLabel("", SwingConstants.CENTER);

        guessField.setHorizontalAlignment(JTextField.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(playButton, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(guessField, BorderLayout.SOUTH);

        playButton.addActionListener(e -> controller.playSound(instrument.getAudioPath()));
        guessField.addActionListener(e -> checkAnswer());
    }

    // Check the input and update state
    private void checkAnswer() {
        if (alreadyAnswered) return;

        String guess = guessField.getText().trim();
        
        boolean correct = controller.checkAnswer(guess, instrument);
        
        if (correct) {
            showImage();
            setBackground(UIManager.getColor("Panel.background")); // Reset red if previously wrong
            guessField.setEditable(false);
            playButton.setEnabled(false);
            alreadyAnswered = true;
        } else {
            setBackground(Color.RED);
        }
    }

    // Display instrument image after correct guess
    private void showImage() {
        try {
            Image img = ImageIO.read(new File(instrument.getImagePath()));
            Image scaled = img.getScaledInstance(getWidth(), getHeight() - 60, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
