package com.snackpirate.constructscasting.modifiers.hooks;

import com.snackpirate.constructscasting.ConstructsCasting;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;

public class CCModifierHooks {
    CCModifierHooks() {}
    public static void init() {}

    public static final ModuleHook<SpellDamageModifierHook> SPELL_DAMAGE = ModifierHooks.register(ConstructsCasting.id("spell_damage"), SpellDamageModifierHook.class, SpellDamageModifierHook.AllMerger::new, (a, b, c, d, e, f) -> f);

    public static final ModuleHook<SpellHitModifierHook> SPELL_HIT = ModifierHooks.register(ConstructsCasting.id("spell_hit"), SpellHitModifierHook.class, SpellHitModifierHook.AllMerger::new, (a, b, c) -> {});

    public static final ModuleHook<SpellOnCastHook> SPELL_CAST = ModifierHooks.register(ConstructsCasting.id("spell_cast"), SpellOnCastHook.class, SpellOnCastHook.AllMerger::new, (a, b, c, d, e, f) -> true);
}
