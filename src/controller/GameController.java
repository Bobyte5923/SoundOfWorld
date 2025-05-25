package controller;

import java.net.URL;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import model.Instrument;
import view.InstrumentGuessPanel;
import view.MainWindow;

public class GameController {
    private final MainWindow window;
    private final List<Instrument> allInstruments;
    private Clip currentClip = null; // to track currently playing sound

    public GameController(MainWindow window) {
        this.window = window;
        this.allInstruments = loadAllInstruments();
    }

    private List<Instrument> loadAllInstruments() {
        List<Instrument> list = new ArrayList<>();
        list.add(new Instrument("Piano", "/resources/images/Piano.jpg", "/resources/sounds/Piano.wav"));
        list.add(new Instrument("Guitare", "/resources/images/Guitare.jpg", "/resources/sounds/Guitare.wav"));
        list.add(new Instrument("Batterie", "/resources/images/Batterie.jpg", "/resources/sounds/Batterie.wav"));
        list.add(new Instrument("Violon", "/resources/images/Violon.jpg", "/resources/sounds/Violon.wav"));
        list.add(new Instrument("Saxophone", "/resources/images/Saxophone.jpg", "/resources/sounds/Saxophone.wav")); // FIXED
        list.add(new Instrument("Flûte", "/resources/images/Flute.jpg", "/resources/sounds/Flute.wav"));
        list.add(new Instrument("Djembe", "/resources/images/Djembe.jpg", "/resources/sounds/Djembe.wav"));
        list.add(new Instrument("Accordeon", "/resources/images/Accordeon.jpg", "/resources/sounds/Accordeon.wav"));
        list.add(new Instrument("Grelots", "/resources/images/Grelots.jpg", "/resources/sounds/Grelots.wav"));
        list.add(new Instrument("Banjo", "/resources/images/Banjo.jpg", "/resources/sounds/Banjo.wav"));
        list.add(new Instrument("Harpe", "/resources/images/Harpe.jpg", "/resources/sounds/Harpe.wav"));
        list.add(new Instrument("Harmonica", "/resources/images/Harmonica.jpg", "/resources/sounds/Harmonica.wav"));
        list.add(new Instrument("Maracas", "/resources/images/Maracas.jpg", "/resources/sounds/Maracas.wav"));
        list.add(new Instrument("Orgue", "/resources/images/Orgue.jpg", "/resources/sounds/Orgue.wav"));
        list.add(new Instrument("Sitar", "/resources/images/Sitar.jpg", "/resources/sounds/Sitar.wav"));
        list.add(new Instrument("Ukelele", "/resources/images/Ukelele.jpg", "/resources/sounds/Ukelele.wav"));
        list.add(new Instrument("Mandoline", "/resources/images/Mandoline.jpg", "/resources/sounds/Mandoline.wav"));
        list.add(new Instrument("Clarinette", "/resources/images/Clarinette.jpg", "/resources/sounds/Clarinette.wav"));
        return list;
    }

    public List<Instrument> getRandomInstruments(int n) {
        List<Instrument> copy = new ArrayList<>(allInstruments);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(n, copy.size()));
    }

    public JPanel createGuessPanel(Instrument instrument) {
        return new InstrumentGuessPanel(instrument, this);
    }

    public synchronized void playSound(Instrument instrument) {
        try {
            if (currentClip != null && currentClip.isRunning()) {
                return;
            }
            URL soundUrl = getClass().getResource(instrument.getSoundPath());
            if (soundUrl == null) {
                window.showMessage("Audio file missing: " + instrument.getSoundPath());
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioIn);
            currentClip.start();
        } catch (Exception e) {
            window.showMessage("Audio error: " + e.getMessage());
        }
    }

    public boolean checkAnswer(String input, Instrument instrument) {
        return instrument.getName().equalsIgnoreCase(input.trim());
    }

    // Allow other components to access the MainWindow
    public MainWindow getWindow() {
        return window;
    }
}
