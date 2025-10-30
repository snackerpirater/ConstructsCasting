package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellDamageModifierHook;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;
import slimeknights.tconstruct.tools.modifiers.traits.melee.ConductingModifier;

import java.util.List;

//conducting for spell power
public class CalorificModifier extends Modifier implements TooltipModifierHook, SpellDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, CCModifierHooks.SPELL_DAMAGE);
        super.registerHooks(hookBuilder);
    }

    @Override
    public float getSpellDamage(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, LivingEntity target, AbstractSpell spell, float damage) {
        float bonus = ConductingModifier.bonusScale(caster) * modifier.getEffectiveLevel() * 0.15F;
        if (bonus > 0.0F) {
            damage *= 1.0F + bonus;
        }
        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        float bonus = 0.15F * (float)modifier.getLevel();
        if (player != null && key == TooltipKey.SHIFT && player.getRemainingFireTicks() == 0) {
            bonus = 0.0F;
        }

        tooltip.add(this.applyStyle(Component.literal(Util.PERCENT_BOOST_FORMAT.format((double)bonus) + " ").append(Component.translatable("modifier.constructs_casting.calorific.boost"))));

    }
}
