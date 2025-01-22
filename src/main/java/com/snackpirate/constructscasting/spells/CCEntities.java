package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.spells.slime.slimeball.SlimeballProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCEntities {
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ConstructsCasting.MOD_ID);

	public static void register(IEventBus eventBus) {
		ENTITIES.register(eventBus);
	}

	public static final RegistryObject<EntityType<SlimeballProjectile>> SLIMEBALL_PROJECTILE =
			ENTITIES.register("slimeball_projectile", () -> EntityType.Builder.<SlimeballProjectile>of(SlimeballProjectile::new, MobCategory.MISC)
					.sized(.5f, .5f)
					.clientTrackingRange(64)
					.build(ConstructsCasting.id("slimeball_projectile").toString()));
}
