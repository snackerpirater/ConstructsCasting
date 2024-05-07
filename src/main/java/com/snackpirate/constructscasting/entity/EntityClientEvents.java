package com.snackpirate.constructscasting.entity;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= ConstructsCasting.MOD_ID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EntityClientEvents {
	@SubscribeEvent
	static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(CCEntities.SLIMEBALL_PROJECTILE.get(), SlimeballProjectileRenderer::new);
	}
}
