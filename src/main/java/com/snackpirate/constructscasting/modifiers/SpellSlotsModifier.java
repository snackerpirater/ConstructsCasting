package com.snackpirate.constructscasting.modifiers;


import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

//internal modifier used on multipart spellbooks to check spell slots upon part swap
//cannot swap unless spellbook is empty
public class SpellSlotsModifier extends Modifier implements ValidateModifierHook {
    public static final String TAG_HAS_SPELLS = "spell_slots_active";
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.VALIDATE);
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public @Nullable Component validate(IToolStackView tool, ModifierEntry modifier) {
        IModDataView persistentData = tool.getPersistentData();
        if (persistentData.contains(ConstructsCasting.id(TAG_HAS_SPELLS))) {
            if (persistentData.getBoolean(ConstructsCasting.id(TAG_HAS_SPELLS))) return Component.translatable("item.constructs_casting.spellbook_has_slots");
        }
        return null;
    }

}
