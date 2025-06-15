package model;

/**
 * Represents a musical instrument with a name, image path, and audio path.
 */
public class Instrument {
    private final String nom;         // Instrument name
    private final String imagePath;   // Path to image (.jpg)
    private final String audioPath;   // Path to sound (.wav)

    public Instrument(String nom, String imagePath, String audioPath) {
        this.nom = nom;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
    }

    public String getName() {
        return nom;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }
}
