package net.kurasava.noisynomore;

import net.fabricmc.api.ModInitializer;
import net.kurasava.noisynomore.config.NoisyConfigManager;

public class NoisyNoMore implements ModInitializer {

	public static NoisyNoMore INSTANCE;

	@Override
	public void onInitialize() {
		INSTANCE = this;
		NoisyConfigManager.load();
	}
}