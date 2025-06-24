package com.snackpirate.constructscasting.fluids;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.network.ClientboundSyncMana;
import io.redspace.ironsspellbooks.setup.Messages;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.data.tinkering.AbstractFluidEffectProvider;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;

public class CCFluidEffects extends AbstractFluidEffectProvider {
	public CCFluidEffects(PackOutput packOutput, String modId) {
		super(packOutput, modId);
	}

	@Override
	protected void addFluids() {
		addFluid(CCFluids.arcaneEssence, 50).addEntityEffect(ADD_MANA);
		addFluid(CCFluids.cinderEssence, 50).addEntityEffect(DEPLETE_MANA);
	}

	@Override
	public String getName() {
		return "Construct's Casting Fluid Effect Provider";
	}

	public static FluidEffect<FluidEffectContext.Entity> DEPLETE_MANA = FluidEffect.simple((fluid, level, context, action) -> {
		if (action.execute()) {
			LivingEntity living = context.getLivingTarget();
			MagicData playerMagicData = MagicData.getPlayerMagicData(living);
			playerMagicData.addMana(-50 * level.value());
			if (living instanceof ServerPlayer serverPlayer) {
				Messages.sendToPlayer(new ClientboundSyncMana(playerMagicData), serverPlayer);
			}
		}
		return level.value();
	});

	public static FluidEffect<FluidEffectContext.Entity> ADD_MANA = FluidEffect.simple((fluid, level, context, action) -> {
		if (action.execute()) {
			LivingEntity living = context.getLivingTarget();
			MagicData playerMagicData = MagicData.getPlayerMagicData(living);
			playerMagicData.addMana(50 * level.value());
			if (living instanceof ServerPlayer serverPlayer) {
				Messages.sendToPlayer(new ClientboundSyncMana(playerMagicData), serverPlayer);
			}
		}
		return level.value();
	});

}
