package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellHitModifierHook;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.shared.TinkerEffects;

public class GashingModifier extends Modifier implements SpellHitModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		hookBuilder.addHook(this, CCModifierHooks.SPELL_HIT);
		super.registerHooks(hookBuilder);
	}
	private static void applyEffect(LivingEntity target, int level) {
		// potions are 0 indexed instead of 1 indexed
		// 81 ticks will do about 5 damage at level 1
		TinkerEffects.bleeding.get().apply(target, 1 + 20 * (2 + (RANDOM.nextInt(level + 3))), level - 1, true);
	}
	@Override
	public void onSpellHit(IToolStackView tool, ModifierEntry modifier, LivingEntity target, float amount, SpellDamageSource damageSource) {
//		ConstructsCasting.LOGGER.info("gashing spell hit");
		if (target != null && damageSource.getEntity() != null && target.isAlive() && RANDOM.nextFloat() < 0.50f) {
			// set entity so the potion is attributed as a player kill
			target.setLastHurtMob(damageSource.getEntity());
			applyEffect(target, modifier.getLevel());
		}
	}
}
