package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCToolStats;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

//if it's sunny outside, +15% mana regen
public class SolarChargedModifier extends Modifier implements EquipmentChangeModifierHook, TooltipModifierHook, InventoryTickModifierHook {
    private static final UUID uuid = UUID.nameUUIDFromBytes("attribute.constructs_casting.solar_charged".getBytes());
    private static final Component BOOST = Component.literal(Util.makeTranslationKey("modifier", ConstructsCasting.id("solar_charged.boost")));
    private static final int minLight = 5;
    private static final float amount = 0.15f;
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }
    private int getLight(Level level, BlockPos pos) {
        return level.getBrightness(LightLayer.SKY, pos);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry entry, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
//        ConstructsCasting.LOGGER.info("solar charged tooltip");
        if (!tool.hasTag(CCToolStats.MAGIC_TOOL)) {
            return;
        }
        int light = 15;
        if (player != null && key == TooltipKey.SHIFT) {
            light = getLight(player.level(), player.blockPosition());
        }
        float boost = amount * (light - minLight) * entry.getEffectiveLevel() / 10;
        if (boost > 0) {
            Modifier modifier = entry.getModifier();
            tooltip.add(applyStyle(Component.literal(Util.PERCENT_BOOST_FORMAT.format(boost) + " ").append(Component.translatable("modifier.constructs_casting.solar_charged.boost"))));
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity living, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        // no point trying if not on the ground
//        ConstructsCasting.LOGGER.info("solar charged tick");
        Level level = living.level();
        if (!living.onGround() || level.isClientSide) {
            return;
        }
        // must have regen
        AttributeInstance attribute = living.getAttribute(AttributeRegistry.MANA_REGEN.get());
        if (attribute == null) {
            return;
        }
        // start by removing the attribute, we are likely going to give it a new number
        if (attribute.getModifier(uuid) != null) {
            attribute.removeModifier(uuid);
        }

        // not above air
        Vec3 vecPos = living.position();
        BlockPos pos = BlockPos.containing(vecPos.x, vecPos.y + 0.5f, vecPos.z);
        int light = getLight(level, pos);
        if (light > minLight) {
            int scaledLight = light - minLight;
            attribute.addTransientModifier(new AttributeModifier(uuid, "attribute.constructs_casting.solar_charged", scaledLight * amount * modifier.getEffectiveLevel() / 10, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        EquipmentChangeModifierHook.super.onEquip(tool, modifier, context);
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity livingEntity = context.getEntity();
        if (context.getChangedSlot() == EquipmentSlot.LEGS) {
            IToolStackView newTool = context.getReplacementTool();
            // damaging the tool will trigger this hook, so ensure the new tool has the same level
            if (newTool == null || newTool.getModifier(modifier.getId()).getEffectiveLevel() != modifier.getEffectiveLevel()) {
                AttributeInstance attribute = livingEntity.getAttribute(AttributeRegistry.MANA_REGEN.get());
                if (attribute != null && attribute.getModifier(uuid) != null) {
                    attribute.removeModifier(uuid);
                }
            }
        }
    }
}
