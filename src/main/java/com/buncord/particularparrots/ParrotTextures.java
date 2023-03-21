package com.buncord.particularparrots;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ParrotTextures {
	private static final Map<ParticularParrot, ResourceLocation> PARROT_LOCATIONS = new HashMap<>();

	static {
		PARROT_LOCATIONS.put(ParticularParrot.Phoenix, new ResourceLocation(ParticularParrots.MODID, "textures/entity/phoenix.png"));
		PARROT_LOCATIONS.put(ParticularParrot.Apollo, new ResourceLocation(ParticularParrots.MODID, "textures/entity/apollo.png"));
		PARROT_LOCATIONS.put(ParticularParrot.Heimdall, new ResourceLocation(ParticularParrots.MODID, "textures/entity/heimdall.png"));
	}

	public static ResourceLocation getParrotTextureFor(ParticularParrot parrot) {
		return PARROT_LOCATIONS.get(parrot);
	}
}
