package net.kurasava.noisynomore.config;

import com.mojang.serialization.Codec;
import net.kurasava.noisynomore.mixins.GameOptionsInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NoisyConfigScreen extends GameOptionsScreen {

    List<String> altCrowdVolTypes = List.of("OFF", "Only Mobs");
    private final NoisyConfig config = NoisyConfigManager.config;

    public NoisyConfigScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.of("Noisy No More Options"));
    }

    @Override
    protected void addOptions() {
        if (this.body != null) {
            SimpleOption<?>[] opt = options.toArray(new SimpleOption[0]);
            this.body.addSingleOptionEntry(altCrowdVol);
            this.body.addAll(opt);
        }
    }

    private final SimpleOption<Integer> altCrowdVol = new SimpleOption<>(
            "Smooth Sound Volume of Mob Crowds",
            tooltip -> Tooltip.of(Text.of(
                    "Alternative sound behavior model that depends on the number of sound sources playing at the same time — the more sources, the quieter the sound becomes.\n\n" +
                            "OFF – Standard sound behavior model\n" +
                            "Only Mobs – Alternative model applies only to hostile and neutral mobs"
            )),
            (optionText, value) -> Text.of(altCrowdVolTypes.get(value)),
            new SimpleOption.LazyCyclingCallbacks<>(
                    () -> List.of(0, 1),
                    value -> value >= 0 && value <= 1 ? Optional.of(value) : Optional.empty(),
                    Codec.INT
            ),
            this.config.getAltCrowdVol(),
            this.config::setAltCrowdVol
    );

    List<SimpleOption<Double>> options = generateSoundOptions(config.objectSounds);

    private List<SimpleOption<Double>> generateSoundOptions(Set<SoundEntry> objectSounds) {
        List<SimpleOption<Double>> options = new ArrayList<>();

        for (SoundEntry entry : objectSounds) {
            String objectId = entry.getObjectId();
            SimpleOption<Double> option = new SimpleOption<>(
                    entry.getName(),
                    SimpleOption.emptyTooltip(),
                    GameOptionsInvoker::getPercentValueOrOffText,
                    SimpleOption.DoubleSliderCallbacks.INSTANCE,
                    entry.getVolume(),
                    value -> NoisyConfigManager.updateSoundVolume(objectId, value)
            );
            options.add(option);
        }
        return options;
    }

    @Override
    public void removed() {
        super.removed();
        NoisyConfigManager.save();
    }
}


