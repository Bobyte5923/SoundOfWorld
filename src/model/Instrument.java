// Represents a musical instrument with its name and the paths to its resources (image and sound file)
package model;

import java.util.Objects;

public class Instrument { 
    private final String name; // Instrument name (used for matching answers)
    private final String imagePath; // Path to the instrument's image
    private final String soundPath; // Path to the instrument's audio file

    public Instrument(String name, String imagePath, String soundPath) { // Constructor
        this.name = name;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
    }

    public String getName() { return name; } // Getter for name
    public String getImagePath() { return imagePath; } // Getter for image path
    public String getSoundPath() { return soundPath; } // Getter for sound path

    @Override
    public String toString() { // Human-readable object representation
        return "Instrument{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", soundPath='" + soundPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) { // Equality based on name only
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;
        Instrument that = (Instrument) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() { // Hash code based on name
        return Objects.hash(name);
    }
}
