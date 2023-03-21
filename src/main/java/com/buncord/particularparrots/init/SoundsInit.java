package com.buncord.particularparrots.init;

import com.buncord.particularparrots.ParticularParrot;
import com.buncord.particularparrots.ParticularParrots;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundsInit {
	private static final Logger LOGGER = LogManager.getLogger();
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ParticularParrots.MODID);
	private static final Map<ParticularParrot, List<RegistryObject<SoundEvent>>> PARROT_SOUNDS = new HashMap<>();

	static {
		List<RegistryObject<SoundEvent>> phoenixList = new ArrayList<>();
		phoenixList.add(registerSoundEvent("phoenix_objection"));
		phoenixList.add(registerSoundEvent("phoenix_holdit"));
		PARROT_SOUNDS.put(ParticularParrot.Phoenix, phoenixList);
		List<RegistryObject<SoundEvent>> apolloList = new ArrayList<>();
		apolloList.add(registerSoundEvent("apollo_glassk"));
		apolloList.add(registerSoundEvent("apollo_hello"));
		apolloList.add(registerSoundEvent("apollo_itsabell"));
		apolloList.add(registerSoundEvent("apollo_pistash"));
		apolloList.add(registerSoundEvent("apollo_shrek"));
		apolloList.add(registerSoundEvent("apollo_wario"));
		PARROT_SOUNDS.put(ParticularParrot.Apollo, apolloList);
		List<RegistryObject<SoundEvent>> heimdallList = new ArrayList<>();
		heimdallList.add(registerSoundEvent("heimdall_bawkbawk"));
		heimdallList.add(registerSoundEvent("heimdall_goodgirl"));
		heimdallList.add(registerSoundEvent("heimdall_hello"));
		PARROT_SOUNDS.put(ParticularParrot.Heimdall, heimdallList);
	}

	private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
		return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ParticularParrots.MODID, name)));
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}

	public static SoundEvent getRandomSoundFrom(ParticularParrot parrot, Level level) {
		List<RegistryObject<SoundEvent>> parrotSounds = PARROT_SOUNDS.get(parrot);
		if (parrotSounds == null) {
			LOGGER.warn("Missing sounds for " + parrot);
			return null;
		}
		return parrotSounds.get(level.random.nextInt(parrotSounds.size())).get();
	}
}
