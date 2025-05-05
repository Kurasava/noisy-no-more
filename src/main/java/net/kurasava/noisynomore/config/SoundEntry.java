package net.kurasava.noisynomore.config;

public class SoundEntry {
    private final String name;
    private final String objectId;
    private double volume;

    public SoundEntry(String name, String objectId, double volume) {
        this.name = name;
        this.objectId = objectId;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public String getObjectId() {
        return objectId;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
