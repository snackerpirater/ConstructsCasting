package com.snackpirate.constructscasting.modifiers.hooks;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.CuriosApi;

//event subscriber for tool hooks
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ConstructsCasting.MOD_ID)
public class CCHookEvents {
    @SubscribeEvent
    static void spellCast(SpellOnCastEvent event) {
        EquipmentContext context = new EquipmentContext(event.getEntity());
        for (EquipmentSlot slotType: EquipmentSlot.values()) {
            IToolStackView toolStack = context.getToolInSlot(slotType);
            if (toolStack != null && !toolStack.isBroken()) {
                for (ModifierEntry entry: toolStack.getModifierList()) {
                    entry.getHook(CCModifierHooks.SPELL_CAST).afterSpellCast(toolStack, entry, event.getEntity(), event.getSpellId(), event.getSchoolType(), event.getCastSource());
                }
            }
        }
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent((handler) -> handler.findCurios(stack -> stack.is(CCItems.Tags.MODIFIABLE_CURIOS)).forEach(slotResult -> {
            IToolStackView toolStack = ToolStack.from(slotResult.stack());
            for (ModifierEntry entry: toolStack.getModifierList()) {
                entry.getHook(CCModifierHooks.SPELL_CAST).afterSpellCast(toolStack, entry, event.getEntity(), event.getSpellId(), event.getSchoolType(), event.getCastSource());
            }
        }));
    }
    @SubscribeEvent
    static void preCast(SpellPreCastEvent event) {
        EquipmentContext context = new EquipmentContext(event.getEntity());
        for (EquipmentSlot slotType: EquipmentSlot.values()) {
            IToolStackView toolStack = context.getToolInSlot(slotType);
            if (toolStack != null && !toolStack.isBroken()) {
                for (ModifierEntry entry: toolStack.getModifierList()) {
                    if (!entry.getHook(CCModifierHooks.SPELL_CAST).beforeSpellCast(toolStack, entry, event.getEntity(), event.getSpellId(), event.getSchoolType(), event.getCastSource())) event.setCanceled(true);
                }
            }
        }
        CuriosApi.getCuriosInventory(event.getEntity()).ifPresent((handler) -> handler.findCurios(stack -> stack.is(CCItems.Tags.MODIFIABLE_CURIOS)).forEach(slotResult -> {
            IToolStackView toolStack = ToolStack.from(slotResult.stack());
            for (ModifierEntry entry: toolStack.getModifierList()) {
                if (!entry.getHook(CCModifierHooks.SPELL_CAST).beforeSpellCast(toolStack, entry, event.getEntity(), event.getSpellId(), event.getSchoolType(), event.getCastSource())) event.setCanceled(true);
            }
        }));
    }
    @SubscribeEvent
    static void spellDamage(SpellDamageEvent event) {
//        ConstructsCasting.LOGGER.info("spell damage {}", event.getAmount());
        final float[] damage = {event.getAmount()};
        Entity attacker = event.getSpellDamageSource().getEntity();
        if (attacker instanceof LivingEntity livingAttacker) {
            EquipmentContext context = new EquipmentContext(livingAttacker);
            LivingEntity target = event.getEntity();
            for (EquipmentSlot slotType : EquipmentSlot.values()) {
                IToolStackView toolStack = context.getToolInSlot(slotType);
                if (toolStack != null && !toolStack.isBroken() && (livingAttacker.getItemBySlot(slotType).is(TinkerTags.Items.HELD) || livingAttacker.getItemBySlot(slotType).is(TinkerTags.Items.ARMOR))) {
                    for (ModifierEntry entry : toolStack.getModifierList()) {
//                        ConstructsCasting.LOGGER.info("testing hook: {}", entry.getId());
//                        ConstructsCasting.LOGGER.info("damage set: {}", damage[0]);
                        damage[0] = (entry.getHook(CCModifierHooks.SPELL_DAMAGE).getSpellDamage(toolStack, entry, livingAttacker, target, event.getSpellDamageSource().spell(), damage[0]));
//                        ConstructsCasting.LOGGER.info("to: {}", damage[0]);
                    }
                }
            }
            if (attacker instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent((handler) -> handler.findCurios(stack -> stack.is(CCItems.Tags.MODIFIABLE_CURIOS)).forEach(slotResult -> {
//					ConstructsCasting.LOGGER.info("change mana event 2");
                    IToolStackView toolStack = ToolStack.from(slotResult.stack());
                    for (ModifierEntry entry: toolStack.getModifierList()) {
//                            ConstructsCasting.LOGGER.info("testing hook 2: {}", entry.getId());
//                            ConstructsCasting.LOGGER.info("damage set 2: {}", damage[0]);
                        damage[0] = (entry.getHook(CCModifierHooks.SPELL_DAMAGE).getSpellDamage(toolStack, entry, livingAttacker, target, event.getSpellDamageSource().spell(), damage[0]));
//                            ConstructsCasting.LOGGER.info("to 2: {}", damage[0]);
                    }
                }));
            }
            for (EquipmentSlot slotType : EquipmentSlot.values()) {
                IToolStackView toolStack = context.getToolInSlot(slotType);
                if (toolStack != null && !toolStack.isBroken() && (livingAttacker.getItemBySlot(slotType).is(TinkerTags.Items.HELD) || livingAttacker.getItemBySlot(slotType).is(TinkerTags.Items.ARMOR))) {
                    for (ModifierEntry entry : toolStack.getModifierList()) {
                        entry.getHook(CCModifierHooks.SPELL_HIT).onSpellHit(target, damage[0], event.getSpellDamageSource()); }
                }
            }
            if (attacker instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent((handler) -> handler.findCurios(stack -> stack.is(CCItems.Tags.MODIFIABLE_CURIOS)).forEach(slotResult -> {
//					ConstructsCasting.LOGGER.info("change mana event 2");
                    IToolStackView toolStack = ToolStack.from(slotResult.stack());
                    for (ModifierEntry entry: toolStack.getModifierList()) {
                        entry.getHook(CCModifierHooks.SPELL_HIT).onSpellHit(target, damage[0], event.getSpellDamageSource());
                    }
                }));
            }
        }
//        ConstructsCasting.LOGGER.info("setting damage to {}", damage[0]);
        event.setAmount(damage[0]);
    }
}
