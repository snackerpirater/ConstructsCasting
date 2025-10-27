package com.snackpirate.constructscasting.modifiers.hooks;

import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public interface SpellHitModifierHook {
    void onSpellHit(LivingEntity target, float amount, SpellDamageSource damageSource);

    record AllMerger(Collection<SpellHitModifierHook> modules) implements SpellHitModifierHook {
        @Override
        public void onSpellHit(LivingEntity target, float amount, SpellDamageSource damageSource) {
            for (SpellHitModifierHook module: modules) {
                module.onSpellHit(target, amount, damageSource);
            }
        }
    }
}
