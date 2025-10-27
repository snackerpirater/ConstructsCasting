package com.snackpirate.constructscasting.modifiers.hooks;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface SpellOnCastHook {
    boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource);

    default void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource) {}

    record AllMerger(Collection<SpellOnCastHook> modules) implements SpellOnCastHook {
        @Override
        public boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource) {
            for (SpellOnCastHook module: modules) {
                if (!module.beforeSpellCast(tool, modifier, caster, spell, school, castSource)) return false;
            }
            return true;
        }

        @Override
        public void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, AbstractSpell spell, SchoolType school, CastSource castSource) {
            for (SpellOnCastHook module: modules) {
                module.afterSpellCast(tool, modifier, caster, spell, school, castSource);
            }
        }
    }
}
