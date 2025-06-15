package controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.Instrument;
import view.GameMenuBar;
import view.MainWindow;

/**
 * GameController manages instrument selection, game progress, and sound playback.
 */
public class GameController {
    private final List<Instrument> allInstruments;
    private List<Instrument> currentGameInstruments;
    private List<Instrument> remainingInstruments;     // Instruments left to guess
    private final MainWindow window;               // Reference to main UI
    private Clip currentClip = null;                   // Only one sound plays at a time

    private int lives = 10;
    private GameMenuBar menuBar;

    public GameController(MainWindow window) {
        this.window = window;
        this.allInstruments = loadInstruments();
        this.currentGameInstruments = new ArrayList<>();
        this.remainingInstruments = new ArrayList<>();

    }

    // Load instrument files from resources/images and resources/sounds
    private List<Instrument> loadInstruments() {
        List<Instrument> list = new ArrayList<>();
        File imageDir = new File("resources/images");
        File soundDir = new File("resources/sounds");

        if (!imageDir.exists() || !soundDir.exists()) return list;

        for (File img : Objects.requireNonNull(imageDir.listFiles())) {
            if (!img.getName().endsWith(".jpg")) continue;
            String name = img.getName().replace(".jpg", "");
            File wav = new File(soundDir, name + ".wav");
            if (wav.exists()) {
                list.add(new Instrument(name, img.getPath(), wav.getPath()));
            }
        }
        return list;
    }

    public void resetGame() { // Reset the game state to start a new game
        lives = 10;
        updateLivesDisplay();

        List<Instrument> shuffled = new ArrayList<>(allInstruments);
        Collections.shuffle(shuffled);
        
        currentGameInstruments = new ArrayList<>(shuffled.subList(0, 6));
        remainingInstruments = new ArrayList<>(currentGameInstruments);

        window.displayInstrumentGrid(); // Reset the game UI with new instruments
    
    }

    public List<Instrument> getCurrentGameInstruments() {
        return new ArrayList<>(currentGameInstruments);
    }

    public List<Instrument> getRemainingInstruments() { // Accessor for remaining instruments to guess
        return new ArrayList<>(remainingInstruments);
    }
    
    // Pick N random instruments
    public List<Instrument> getRandomInstruments(int count) {
        return new ArrayList<>(currentGameInstruments);
    } 


    // Exclusive audio playback
    public void playSound(String audioPath) {
        try {
            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop();
                currentClip.close();
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(audioPath));
            currentClip = AudioSystem.getClip();
            currentClip.open(audioIn);
            currentClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

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

    public boolean checkAnswer(String input, Instrument instrument) {
        boolean correct = instrument.getName().equalsIgnoreCase(input.trim());
        
        System.out.println("DEBUG: checkAnswer appelé avec '" + input + "' pour " + instrument.getName() + " -> " + correct);

        if (correct) {
            
            // Tente de retirer l'instrument
            boolean removed = remainingInstruments.removeIf(i -> i.getName().equalsIgnoreCase(instrument.getName()));
            

            // IMPORTANT: Vérifier la victoire IMMÉDIATEMENT après la suppression
            if (remainingInstruments.isEmpty()) {
               
                SwingUtilities.invokeLater(() -> {
                    window.showVictoryDialog();
                });
            }
        } else {
            lives--;
            updateLivesDisplay();
        
            if (lives <= 0) {
                SwingUtilities.invokeLater(() -> {
                    window.showGameOverDialog();
                });
            }
        }
        return correct;
    } 
    

    public MainWindow getWindow() {
        return window;
    }

    public void setMenuBar(GameMenuBar menuBar) { // Set the menu bar for the game controller
        this.menuBar = menuBar;
        updateLivesDisplay();
    }

    private void updateLivesDisplay() { // Update the lives display in the menu bar
        if (menuBar != null) {
            menuBar.setLives(lives);
        }
    }

    // Get all instrument names (from code 1)
    public List<String> getAllInstrumentNames() {
        List<String> names = new ArrayList<>();
        for (Instrument inst : allInstruments) {
            names.add(inst.getName());
        }
        return names;
    }
}
