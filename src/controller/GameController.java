// Manages the game logic (loading instruments, random selection, playing sounds, checking answers) and links the data to the graphical interface.
package controller;

import java.net.URL; // Used for resource path resolution
import java.util.*; // Provides List, ArrayList, Collections
import javax.sound.sampled.*; // For playing audio clips
import javax.swing.*; // For Swing GUI components
import model.Instrument; // Data model representing an instrument
import view.InstrumentGuessPanel; // GUI component for guessing
import view.MainWindow; // Main application window

public class GameController { 
    private final MainWindow window; // Reference to the main window for displaying messages
    private final List<Instrument> allInstruments; // Complete list of available instruments
    private Clip currentClip = null; // Currently playing audio clip

    public GameController(MainWindow window) { // Constructor initializing game controller with window
        this.window = window;
        this.allInstruments = loadAllInstruments(); // Loads the complete set of instruments
    }

    private List<Instrument> loadAllInstruments() { // Load and return all instrument data with associated resources
        List<Instrument> list = new ArrayList<>();
        list.add(new Instrument("Piano", "/resources/images/Piano.jpg", "/resources/sounds/Piano.wav"));
        list.add(new Instrument("Guitare", "/resources/images/Guitare.jpg", "/resources/sounds/Guitare.wav"));
        list.add(new Instrument("Batterie", "/resources/images/Batterie.jpg", "/resources/sounds/Batterie.wav"));
        list.add(new Instrument("Violon", "/resources/images/Violon.jpg", "/resources/sounds/Violon.wav"));
        list.add(new Instrument("Saxophone", "/resources/images/Saxophone.jpg", "/resources/sounds/Saxophone.wav"));
        list.add(new Instrument("Flute", "/resources/images/Flute.jpg", "/resources/sounds/Flute.wav"));
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

    public List<Instrument> getRandomInstruments(int n) { // Randomly select n instruments
        List<Instrument> copy = new ArrayList<>(allInstruments);
        Collections.shuffle(copy); // Shuffle to ensure randomness
        return copy.subList(0, Math.min(n, copy.size()));
    }

    public JPanel createGuessPanel(Instrument instrument) { // Create a new guessing panel for an instrument
        return new InstrumentGuessPanel(instrument, this);
    }

    public synchronized void playSound(Instrument instrument) { // Play the audio clip for the given instrument
        try {
            if (currentClip != null && currentClip.isRunning()) {
                return; // Do not interrupt a currently playing sound
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

    public boolean checkAnswer(String input, Instrument instrument) { // Compare user input to the correct instrument name
        return instrument.getName().equalsIgnoreCase(input.trim());
    }

    public MainWindow getWindow() { // Accessor for main window (for messaging)
        return window;
    }
}
