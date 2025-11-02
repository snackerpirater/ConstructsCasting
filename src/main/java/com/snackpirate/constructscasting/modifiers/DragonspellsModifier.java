package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.materials.CCToolStats;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;

public class DragonspellsModifier extends Modifier implements ConditionalStatModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.CONDITIONAL_STAT);
        super.registerHooks(hookBuilder);
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        //null check is necessary do not delete
        if (living != null && stat == CCToolStats.SPELL_POWER && !living.onGround()) {
            baseValue+=0.25f*multiplier*modifier.getLevel();
        }
        return baseValue;
    }
}
