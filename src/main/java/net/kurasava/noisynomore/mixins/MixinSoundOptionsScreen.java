package net.kurasava.noisynomore.mixins;

import net.kurasava.noisynomore.config.NoisyConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import java.util.function.Supplier;


@Mixin(SoundOptionsScreen.class)
public class MixinSoundOptionsScreen extends GameOptionsScreen {

    @Shadow
    private SimpleOption<?>[] getVolumeOptions() {
        throw new AssertionError();
    }

    @Shadow
    private static SimpleOption<?>[] getOptions(GameOptions gameOptions) {
        throw new AssertionError();
    }

    public MixinSoundOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, Text.translatable("options.sounds.title"));
    }

    private ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier) {
        return ButtonWidget.builder(message, (button) -> this.client.setScreen(screenSupplier.get())).width(310).build();
    }

    @Overwrite
    public void addOptions() {
        ButtonWidget additionalSoundSettings = createButton(Text.of("Additional Sound Settings"), () -> new NoisyConfigScreen(this));

        OptionListWidget list = this.body;
        list.addSingleOptionEntry(this.gameOptions.getSoundVolumeOption(SoundCategory.MASTER));
        list.addAll(getVolumeOptions());
        list.addWidgetEntry(additionalSoundSettings, null);
        list.addSingleOptionEntry(this.gameOptions.getSoundDevice());
        list.addAll(getOptions(this.gameOptions));
    }
}

