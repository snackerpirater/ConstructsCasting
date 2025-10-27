package com.snackpirate.constructscasting.modifiers.hooks;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

//used for
public interface SpellDamageModifierHook {

    float getSpellDamage(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource, float previousDamage);

    record AllMerger(Collection<SpellDamageModifierHook> modules) implements SpellDamageModifierHook {
        @Override
        public float getSpellDamage(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource, float previousDamage) {
            for (SpellDamageModifierHook module: modules) {
                previousDamage = module.getSpellDamage(tool, modifier, caster, spell, school, castSource, previousDamage);
            }
            return previousDamage;
        }
    }
}
