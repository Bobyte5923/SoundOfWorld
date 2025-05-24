package controller;

import model.Instrument;
import view.MainWindow;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private MainWindow window;

    public GameController(MainWindow window) {
        this.window = window;
    }

    public List<Instrument> loadInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        instruments.add(new Instrument("Piano", "resources/images/piano.png", "resources/sounds/piano.wav"));
        instruments.add(new Instrument("Guitare", "resources/images/guitare.png", "resources/sounds/guitare.wav"));
        instruments.add(new Instrument("Batterie", "resources/images/batterie.png", "resources/sounds/batterie.wav"));
        instruments.add(new Instrument("Violon", "resources/images/violon.png", "resources/sounds/violon.wav"));
        instruments.add(new Instrument("Trompette", "resources/images/trompette.png", "resources/sounds/trompette.wav"));
        instruments.add(new Instrument("Flûte", "resources/images/flute.png", "resources/sounds/flute.wav"));
        return instruments;
    }

    public void playSound(Instrument instrument) {
        File soundFile = new File(instrument.getSoundPath());
        if (!soundFile.exists()) {
            JOptionPane.showMessageDialog(window, "Fichier audio introuvable : " + soundFile.getPath());
            return;
        }

        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.setFramePosition(0); // Rejoue toujours depuis le début
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(window, "Erreur lors de la lecture du son : " + e.getMessage());
        }
    }
}
