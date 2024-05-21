package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.damage.IndirectSpellDamageSource;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import slimeknights.mantle.data.predicate.damage.DamageSourcePredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.library.modifiers.data.ModifierMaxLevel;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.tools.modifiers.defense.AbstractProtectionModifier;

public class SpellProtectionModifier extends AbstractProtectionModifier<ModifierMaxLevel> {
	private static final TinkerDataCapability.ComputableDataKey<ModifierMaxLevel> KEY = TinkerDataCapability.ComputableDataKey.of(ConstructsCasting.id("spell_protection"), ModifierMaxLevel::new);

	public SpellProtectionModifier() {
		super(KEY);
	}

	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		super.registerHooks(hookBuilder);
		hookBuilder.addModule(ProtectionModule.builder().source(DamageSourcePredicate.simple((
				damageSource -> damageSource instanceof SpellDamageSource ||
						damageSource instanceof IndirectSpellDamageSource ||
						damageSource.getDirectEntity() instanceof AoeEntity ||
						damageSource.getDirectEntity() instanceof AntiMagicSusceptible
		))).entity(LivingEntityPredicate.ANY).eachLevel(2.5f));
	}
}