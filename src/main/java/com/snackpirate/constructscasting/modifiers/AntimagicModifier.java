package com.snackpirate.constructscasting.modifiers;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;


public class AntimagicModifier extends Modifier implements MeleeDamageModifierHook, TooltipModifierHook, ProjectileHitModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOLTIP, ModifierHooks.PROJECTILE_HIT);
		super.registerHooks(hookBuilder);
	}

	@Override
	public float getMeleeDamage(IToolStackView iToolStackView, ModifierEntry modifierEntry, ToolAttackContext toolAttackContext, float v, float v1) {
		int level = modifierEntry.getLevel();
		float damageBonus = isMagicUser(toolAttackContext.getLivingTarget()) ? 2.0f * level : 0;
		return v1 + damageBonus;
	}

	@Override
	public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
		int damageBoost = 2 * modifierEntry.getLevel();
		list.add(applyStyle(Component.literal(Util.BONUS_FORMAT.format(damageBoost) + " ").append(Component.translatable("modifier.constructs_casting.antimagic.damage_boost"))));
	}
	private static boolean isMagicUser(LivingEntity target) {
		return MagicData.getPlayerMagicData(target).isCasting() || target instanceof AbstractSpellCastingMob || target instanceof IMagicSummon || MagicData.getPlayerMagicData(target).getMana() > 101;
	}

	@Override
	public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
		if (target != null && isMagicUser(target) && projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + modifier.getLevel() / 2f);
		}
		return false;
	}
}
