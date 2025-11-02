package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellHitModifierHook;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerDamageTypes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;
import java.util.Objects;

public class PuncturingModifier extends Modifier implements TooltipModifierHook, SpellHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, CCModifierHooks.SPELL_HIT);
        super.registerHooks(hookBuilder);
    }

    @Override
    public void onSpellHit(IToolStackView tool, ModifierEntry modifier, LivingEntity target, float amount, SpellDamageSource damageSource) {
        DamageSource source = TinkerDamageTypes.source(
                target.level().registryAccess(),
                TinkerDamageTypes.PIERCING,
                Objects.requireNonNullElse(damageSource.getEntity(), damageSource.getDirectEntity()));
        // 1 damage per level, spells are fast
        //TODO: change this with bone's reworked trait in the next TiC update
        float secondaryDamage = (modifier.getEffectiveLevel());
        ToolAttackUtil.attackEntitySecondary(source, secondaryDamage, target, target, true);

    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        TooltipModifierHook.addDamageBoost(tool, this, modifier.getEffectiveLevel(), tooltip);
    }
}
