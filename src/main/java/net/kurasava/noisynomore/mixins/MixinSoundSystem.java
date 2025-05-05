package net.kurasava.noisynomore.mixins;

import com.google.common.collect.Multimap;
import net.kurasava.noisynomore.config.NoisyConfig;
import net.kurasava.noisynomore.config.NoisyConfigManager;
import net.kurasava.noisynomore.config.SoundEntry;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(SoundSystem.class)
public class MixinSoundSystem {
    NoisyConfig config = NoisyConfigManager.config;

    @Shadow
    private Multimap<SoundCategory, SoundInstance> sounds;

    @Inject(
            method = "getAdjustedVolume(Lnet/minecraft/client/sound/SoundInstance;)F",
            at = @At("RETURN"),
            cancellable = true
    )
    private float getAdjustedVolume(SoundInstance sound, CallbackInfoReturnable<Float> cir) {
        return this.calculateAlternateVolume(sound, cir.getReturnValueF());
    }


    @ModifyVariable(
            method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At(value = "STORE", ordinal = 0),
            index = 5,
            require = 1
    )
    private float modifyVolume(float trueVolume, SoundInstance sound) {
        return this.calculateAlternateVolume(sound, trueVolume);
    }

    private float calculateAlternateVolume(SoundInstance sound, float trueVolume) {
        int mode = config.getAltCrowdVol();
        double customMul = getCustomMul(sound);
        boolean isFiltered = sound.getId().toString().contains("minecraft:entity");

        if (mode == 1 && isFiltered) {
            Collection<SoundInstance> all = sounds.get(sound.getCategory());
            long count = all.stream()
                    .filter(s -> s.getId().equals(sound.getId()))
                    .count();

            double groupMul = Math.max(0.01, 1.5 / count);
            double finalMul = Math.min(groupMul, customMul);

            return (float) (trueVolume * finalMul);
        }

        return (float) (trueVolume * customMul);
    }

    private double getCustomMul(SoundInstance sound) {
        return config.objectSounds.stream().filter(soundEntry -> sound.getId().toString().startsWith(soundEntry.getObjectId()))
                .findFirst()
                .map(SoundEntry::getVolume)
                .orElse(1.0);
    }
}
