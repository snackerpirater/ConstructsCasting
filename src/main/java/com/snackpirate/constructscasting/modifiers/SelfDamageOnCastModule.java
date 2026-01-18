package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellOnCastHook;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.mantle.data.loadable.primitive.ResourceLocationLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.List;

public record SelfDamageOnCastModule(@Nullable List<ResourceLocation> spellFilter, LevelingValue healthDepleted) implements ModifierModule, SpellOnCastHook {
	public static final List<ModuleHook<?>> HOOKS = HookProvider.<SelfDamageOnCastModule>defaultHooks(CCModifierHooks.SPELL_CAST);
	public static final RecordLoadable<SelfDamageOnCastModule> LOADER = RecordLoadable.create(
			ResourceLocationLoadable.DEFAULT.list(1).nullableField("spell_filter", SelfDamageOnCastModule::spellFilter),
			LevelingValue.LOADABLE.requiredField("health_depleted", SelfDamageOnCastModule::healthDepleted),
			SelfDamageOnCastModule::new
	);

	@Override
	public boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spell, SchoolType school, CastSource castSource) {
		return true;
	}

	@Override
	public void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spellId, SchoolType school, CastSource castSource) {
		ResourceLocation spellLocation = ResourceLocation.parse(spellId);
		if (spellFilter != null) {
			if (spellFilter.contains(spellLocation)) {
				caster.hurt(caster.damageSources().cactus(), healthDepleted.compute(modifier.getLevel()));
			}
		} else {
			caster.hurt(caster.damageSources().cactus(), healthDepleted.compute(modifier.getLevel()));
		}
	}

	@Override
	public RecordLoadable<? extends ModifierModule> getLoader() {
		return LOADER;
	}

	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return HOOKS;
	}
}
