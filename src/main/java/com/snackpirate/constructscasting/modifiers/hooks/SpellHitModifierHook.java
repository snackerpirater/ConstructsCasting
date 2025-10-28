package com.snackpirate.constructscasting.modifiers.hooks;

import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface SpellHitModifierHook {
    void onSpellHit(IToolStackView tool, ModifierEntry modifier, LivingEntity target, float amount, SpellDamageSource damageSource);

    record AllMerger(Collection<SpellHitModifierHook> modules) implements SpellHitModifierHook {
        @Override
        public void onSpellHit(IToolStackView tool, ModifierEntry modifier, LivingEntity target, float amount, SpellDamageSource damageSource) {
            for (SpellHitModifierHook module: modules) {
                module.onSpellHit(tool, modifier, target, amount, damageSource);
            }
        }
    }
}
