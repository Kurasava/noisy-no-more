package net.kurasava.noisynomore.mixins;

import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameOptions.class)
public interface GameOptionsInvoker {
    @Invoker("getPercentValueOrOffText")
    static Text getPercentValueOrOffText(Text prefix, double value) {
        throw new AssertionError();
    }
}
