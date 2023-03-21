package com.buncord.particularparrots;

import com.buncord.particularparrots.init.SoundsInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ParticularParrots.MODID)
public class ParticularParrots {
	public static final String MODID = "particularparrots";

	public ParticularParrots() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		SoundsInit.register(bus);
	}

}
