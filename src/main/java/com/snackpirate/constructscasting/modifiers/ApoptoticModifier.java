package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.modifiers.hooks.CCModifierHooks;
import com.snackpirate.constructscasting.modifiers.hooks.SpellHitModifierHook;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

//spell lifesteal
//dealing damage over a certain threshold returns some of that over-threshold damage as health, like tax brackets
public class ApoptoticModifier extends Modifier implements SpellHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, CCModifierHooks.SPELL_HIT);
        super.registerHooks(hookBuilder);
    }

    @Override
    public void onSpellHit(IToolStackView tool, ModifierEntry modifier, LivingEntity target, float amount, SpellDamageSource damageSource) {
        float threshold = dmgThresholdForLevel(modifier.getLevel());
        if (amount > threshold) {
            float dmgOverThreshold = amount - threshold;
            if (damageSource.getEntity() != null && damageSource.getEntity() instanceof LivingEntity living) {
                //100 damage = 4, 9
                //40 damage = 2, 3
                //25 damage = 0.25, 1.5
                living.heal(dmgOverThreshold/threshold);
            }
        }
    }
    //level 1: 20
    //level 2: 10
    //can't get over two levels so who cares
    private static float dmgThresholdForLevel(int level) {
        return 20f/level;
    }
}
