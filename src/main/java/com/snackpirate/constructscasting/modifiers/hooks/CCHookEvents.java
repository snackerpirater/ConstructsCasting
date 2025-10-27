package com.snackpirate.constructscasting.modifiers.hooks;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//event subscriber for tool hooks
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ConstructsCasting.MOD_ID)
public class CCHookEvents {
    @SubscribeEvent
    static void spellCast(SpellOnCastEvent event) {

    }
    @SubscribeEvent
    static void preCast(SpellPreCastEvent event) {

    }
    @SubscribeEvent
    static void spellDamage(SpellDamageEvent event) {
        
    }
}
