package net.kurasava.noisynomore.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Set;

public class NoisyConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "noisy-no-more.json");
    public static NoisyConfig config = NoisyConfig.createDefault();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, NoisyConfig.class);
            } catch (Exception e) {
                System.err.println("[NoisyNoMore] Failed to load config: " + e.getMessage());
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            System.err.println("[NoisyNoMore] Failed to save config: " + e.getMessage());
        }
    }

    public static void updateSoundVolume(String objectId, double volume) {
        Set<SoundEntry> objectSounds = config.objectSounds;
        objectSounds.stream()
                .filter(soundEntry -> soundEntry.getObjectId().equals(objectId))
                .findFirst()
                .ifPresent(soundEntry -> soundEntry.setVolume(volume));
        save();
    }
}
