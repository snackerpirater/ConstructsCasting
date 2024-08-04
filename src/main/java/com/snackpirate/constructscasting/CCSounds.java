package com.snackpirate.constructscasting;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCSounds {
	private static final DeferredRegister<SoundEvent> EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ConstructsCasting.MOD_ID);

	public static void register(IEventBus eventBus) {
		EVENTS.register(eventBus);
	}
	private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
		return EVENTS.register(name, () -> new SoundEvent(ConstructsCasting.id(name)));
	}

	public static RegistryObject<SoundEvent> SLIME_CAST = registerSoundEvent("cast.generic.slime");
}
