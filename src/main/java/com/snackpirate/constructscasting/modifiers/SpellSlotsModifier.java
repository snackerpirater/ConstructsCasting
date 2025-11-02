package com.snackpirate.constructscasting.modifiers;


import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

//internal modifier used on multipart spellbooks to check spell slots upon part swap
//cannot swap unless spellbook is empty
public class SpellSlotsModifier extends Modifier implements ValidateModifierHook, InventoryTickModifierHook {
    public static final String TAG_HAS_SPELLS = "spell_slots_active";
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public @Nullable Component validate(IToolStackView tool, ModifierEntry modifier) {
        IModDataView persistentData = tool.getPersistentData();
        if (persistentData.contains(ConstructsCasting.id(TAG_HAS_SPELLS))) {
            if (persistentData.getBoolean(ConstructsCasting.id(TAG_HAS_SPELLS))) return Component.translatable("ui.constructs_casting.spellbook_has_slots");
        }
        return null;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (world.getGameTime() % 10 != 0) return; //runs every half second, probably good enough
        ISpellContainer container = ISpellContainer.get(stack);
//        ConstructsCasting.LOGGER.info("spell slots inv tick: {}", !container.isEmpty());
        ModDataNBT persistentData = tool.getPersistentData();
        persistentData.putBoolean(ConstructsCasting.id(TAG_HAS_SPELLS), !container.isEmpty());
    }
}
