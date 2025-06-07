package controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;
import model.Instrument;
import view.MainWindow;

/**
 * GameController manages instrument selection, game progress, and sound playback.
 */
public class GameController {
    private List<Instrument> instruments;              // All instruments
    private List<Instrument> remainingInstruments;     // Instruments left to guess
    private final MainWindow mainWindow;               // Reference to main UI
    private Clip currentClip = null;                   // Only one sound plays at a time

    public GameController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.instruments = loadInstruments();
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

    // Pick N random instruments
    public List<Instrument> getRandomInstruments(int count) {
        if (instruments.size() < count) {
            throw new IllegalStateException("Not enough instruments available.");
        }
        Collections.shuffle(instruments);
        remainingInstruments = new ArrayList<>(instruments.subList(0, count));
        return new ArrayList<>(remainingInstruments);
    }

    // Mark guessed instrument and trigger end if all guessed
    public void markInstrumentAsGuessed(Instrument instrument) {
        remainingInstruments.remove(instrument);
        if (remainingInstruments.isEmpty()) {
            mainWindow.showVictoryScreen();
        }
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
}
