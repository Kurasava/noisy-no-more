package net.kurasava.noisynomore.config;

import java.util.HashSet;
import java.util.Set;

public class NoisyConfig {
    private int altCrowdVol = 0;
    public Set<SoundEntry> objectSounds = new HashSet<>() {
    };

    public static NoisyConfig createDefault() {
        NoisyConfig config = new NoisyConfig();
        config.altCrowdVol = 0;

        config.objectSounds.add(new SoundEntry("Pistons", "minecraft:block.piston", 1.0));
        config.objectSounds.add(new SoundEntry("Dispenser", "minecraft:block.dispenser", 1.0));
        config.objectSounds.add(new SoundEntry("Explosions", "minecraft:entity.generic.explode",1.0));
        config.objectSounds.add(new SoundEntry("Enderman", "minecraft:entity.enderman",1.0));
        config.objectSounds.add(new SoundEntry("Minecarts", "minecraft:entity.minecart", 1.0));
        config.objectSounds.add(new SoundEntry("Ender Dragon", "minecraft:entity.ender_dragon", 1.0));
        config.objectSounds.add(new SoundEntry("Wither", "minecraft:entity.wither", 1.0));
        config.objectSounds.add(new SoundEntry("Villagers", "minecraft:entity.villager", 1.0));
        config.objectSounds.add(new SoundEntry("Ghast", "minecraft:entity.ghast", 1.0));
        config.objectSounds.add(new SoundEntry("Slime", "minecraft:entity.slime", 1.0));
        config.objectSounds.add(new SoundEntry("Blaze", "minecraft:entity.blaze", 1.0));
        config.objectSounds.add(new SoundEntry("Zombie", "minecraft:entity.zombie", 1.0));
        config.objectSounds.add(new SoundEntry("Zombified Piglin", "minecraft:entity.zombified_piglin", 1.0));

        return config;
    }

    public void setAltCrowdVol(int altCrowdVol) {
        this.altCrowdVol = altCrowdVol;
    }

    public int getAltCrowdVol() {
        return altCrowdVol;
    }
}
