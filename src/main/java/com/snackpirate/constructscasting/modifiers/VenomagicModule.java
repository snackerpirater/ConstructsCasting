package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellDamageModifierHook;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;

public record VenomagicModule(LevelingValue bonusDamage) implements ModifierModule, SpellDamageModifierHook, TooltipModifierHook {
	public static final List<ModuleHook<?>> HOOKS = HookProvider.<VenomagicModule>defaultHooks(ModifierHooks.TOOLTIP, CCModifierHooks.SPELL_DAMAGE);
	public static final RecordLoadable<VenomagicModule> LOADER = RecordLoadable.create(
			LevelingValue.LOADABLE.requiredField("bonus_percent", VenomagicModule::bonusDamage),
			VenomagicModule::new
	);

	@Override
	public float getSpellDamage(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, LivingEntity target, AbstractSpell spell, float previousDamage) {
		return previousDamage + (target.hasEffect(MobEffects.POISON) ? previousDamage * bonusDamage.compute(modifier.getLevel()) : 0);
	}

	/**
	 * @param tool        Tool instance
	 * @param modifier    Tool level
	 * @param player      Player holding this tool
	 * @param tooltip     Tooltip
	 * @param tooltipKey  Shows if the player is holding shift, control, or neither
	 * @param tooltipFlag Flag determining tooltip type
	 */
	@Override
	public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
		float bonus = bonusDamage.compute(modifier.getLevel());
//		tooltip.add(applyStyle(Component.literal(Util.PERCENT_BOOST_FORMAT.format(bonus) + " ").append(Component.translatable("modifier.constructs_casting.venomagic.boost"))));
		TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.constructs_casting.venomagic.boost"), bonus, tooltip);
	}

	/**
	 * @return
	 */
	@Override
	public RecordLoadable<? extends ModifierModule> getLoader() {
		return LOADER;
	}

	/**
	 * @return
	 */
	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return HOOKS;
	}
}
