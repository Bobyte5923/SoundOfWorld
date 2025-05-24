package model;

import java.util.Objects;

public class Instrument {
    private final String name;
    private final String imagePath;
    private final String soundPath;

    public Instrument(String name, String imagePath, String soundPath) {
        this.name = name;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", soundPath='" + soundPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;
        Instrument that = (Instrument) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
