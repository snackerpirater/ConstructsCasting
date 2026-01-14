package com.snackpirate.constructscasting.modifiers.hooks;

import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface SpellOnCastHook {
    /**
     * @param tool Spellbook from which the spell was cast
     * @param modifier Modifier instance
     * @param caster Entity (probably player) who cast the spell
     * @param spell ID string of the spell cast
     * @param school SchoolType of the spell cast
     * @param castSource CastSource of the spell
     * @return True if other spell cast modules should run
     */
    boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spell, SchoolType school, CastSource castSource);

    default void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spellId, SchoolType school, CastSource castSource) {}

    record AllMerger(Collection<SpellOnCastHook> modules) implements SpellOnCastHook {
        @Override
        public boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spell, SchoolType school, CastSource castSource) {
            for (SpellOnCastHook module: modules) {
                if (!module.beforeSpellCast(tool, modifier, caster, spell, school, castSource)) return false;
            }
            return true;
        }

        @Override
        public void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spellId, SchoolType school, CastSource castSource) {
            for (SpellOnCastHook module: modules) {
                module.afterSpellCast(tool, modifier, caster, spellId, school, castSource);
            }
        }
    }
}
