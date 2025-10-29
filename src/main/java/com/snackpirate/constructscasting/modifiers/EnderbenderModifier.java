package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellOnCastHook;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

//teleport/movement spells grant evasion
public class EnderbenderModifier extends Modifier implements SpellOnCastHook {
    private static final List<String> validIds = List.of("irons_spellbooks:teleport", "irons_spellbooks:blood_step", "irons_spellbooks:frost_step"); //TODO: add movement spells to this list
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, CCModifierHooks.SPELL_CAST);
        super.registerHooks(hookBuilder);
    }

    @Override
    public boolean beforeSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spell, SchoolType school, CastSource castSource) {
        return true;
    }
    @Override
    public void afterSpellCast(IToolStackView tool, ModifierEntry modifier, LivingEntity caster, String spellId, SchoolType school, CastSource castSource) {
//        AbstractSpell spell = SpellRegistry.getSpell(spellId);
//        ConstructsCasting.LOGGER.info("cast {}", spell.getSpellId());
        if (validIds.contains(spellId) && !caster.hasEffect(MobEffectRegistry.EVASION.get())) {
            caster.addEffect(new MobEffectInstance(MobEffectRegistry.EVASION.get(), 60, modifier.getLevel()-1, false, false, true)); //(modifier level) hits to be evaded over 3 seconds
        }
    }
}
