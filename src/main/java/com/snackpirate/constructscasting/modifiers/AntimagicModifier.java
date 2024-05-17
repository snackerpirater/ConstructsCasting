package com.snackpirate.constructscasting.modifiers;

import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;
import java.util.Objects;


public class AntimagicModifier extends Modifier implements MeleeDamageModifierHook, TooltipModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOLTIP);
		super.registerHooks(hookBuilder);
	}

	@Override
	public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
		int level = modifierEntry.getLevel();
		float damage = isMagicUser(Objects.requireNonNull(toolAttackContext.getLivingTarget())) ? iToolStackView.getStats().get(ToolStats.ATTACK_DAMAGE) : 0;
		return damage + (2.0f * level);
	}

	@Override
	public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
		int damageBoost = 2 * modifierEntry.getLevel();
		list.add(applyStyle(Component.literal(Util.BONUS_FORMAT.format(damageBoost) + " ").append(Component.translatable("modifier.constructs_casting.antimagic.damage_boost"))));
	}
	private static boolean isMagicUser(LivingEntity target) {
		return MagicData.getPlayerMagicData(target).isCasting() || target instanceof AbstractSpellCastingMob;
	}
}
