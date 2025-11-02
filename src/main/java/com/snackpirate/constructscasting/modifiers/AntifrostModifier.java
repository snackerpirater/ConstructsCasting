package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellDamageModifierHook;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;

public class AntifrostModifier extends Modifier implements MeleeDamageModifierHook, TooltipModifierHook, SpellDamageModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOLTIP, CCModifierHooks.SPELL_DAMAGE);
		super.registerHooks(hookBuilder);
	}
	private static final float DAMAGE_PER_LEVEL = 3f;
	/**
	 * @param tool       Tool used to attack
	 * @param modifier   Modifier level
	 * @param context    Attack context
	 * @param baseDamage Base damage dealt before modifiers
	 * @param damage     Computed damage from all prior modifiers
	 */
	@Override
	public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
//		ConstructsCasting.LOGGER.info("antifrost getMeleeDamage {}", damage + (context.getLivingTarget() != null ? calculateBonus(modifier, context.getLivingTarget()) : 0));
		return damage + (context.getLivingTarget() != null && tool.getItem().builtInRegistryHolder().is(TinkerTags.Items.MELEE) ? calculateBonus(modifier, context.getLivingTarget()) : 0);
	}

	private static float calculateBonus(ModifierEntry modifier, LivingEntity target) {
		int level = modifier.getLevel();
		int isFrozen = target.getTicksFrozen() > target.getTicksRequiredToFreeze() ? 1 : 0;
//		ConstructsCasting.LOGGER.info("calc bonus {}", DAMAGE_PER_LEVEL * level * isFrozen);
		return DAMAGE_PER_LEVEL * level * isFrozen;
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
	public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
		float dmgBonus = DAMAGE_PER_LEVEL * modifier.getLevel();
		tooltip.add(applyStyle(Component.literal(Util.BONUS_FORMAT.format(dmgBonus) + " ").append(Component.translatable("modifier.constructs_casting.antifrost.damage_boost"))));
	}

	@Override
	public float getSpellDamage(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, LivingEntity target, AbstractSpell spell, float previousDamage) {
//		ConstructsCasting.LOGGER.info("get spell damage for {}: {}", tool.getItem().getDescriptionId(), previousDamage + (target != null && tool.getItem().builtInRegistryHolder().is(CCItems.Tags.MODIFIABLE_CURIOS) ? calculateBonus(modifier, target) : 0));
//		ConstructsCasting.LOGGER.info("bool test: {}, {}", target != null, tool.getItem().builtInRegistryHolder().is(CCItems.Tags.MODIFIABLE_CURIOS));
		return previousDamage + (target != null && tool.getItem().builtInRegistryHolder().is(CCItems.Tags.MODIFIABLE_CURIOS) ? calculateBonus(modifier, target) : 0);
	}
}
