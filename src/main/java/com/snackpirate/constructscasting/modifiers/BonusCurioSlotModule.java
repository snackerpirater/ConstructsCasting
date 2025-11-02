package com.snackpirate.constructscasting.modifiers;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.primitive.StringLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.registry.GenericLoaderRegistry;
import slimeknights.tconstruct.library.json.LevelingInt;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.UUID;

public record BonusCurioSlotModule(String slotIdentifier, LevelingInt amount, String uuid) implements ModifierModule, EquipmentChangeModifierHook {
	public static final RecordLoadable<BonusCurioSlotModule> LOADER = RecordLoadable.create(
			StringLoadable.DEFAULT.requiredField("slot_identifier", BonusCurioSlotModule::slotIdentifier),
			LevelingInt.LOADABLE.requiredField("amount", BonusCurioSlotModule::amount),
			StringLoadable.DEFAULT.requiredField("uuid", BonusCurioSlotModule::uuid),
			BonusCurioSlotModule::new
	);
	@Override
	public RecordLoadable<? extends GenericLoaderRegistry.IHaveLoader> getLoader() {
		return LOADER;
	}

	@Override
	public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
//        ConstructsCasting.LOGGER.info("ringbearer eq");
		if (!context.getLevel().isClientSide()) {
			CuriosApi.getCuriosInventory(context.getEntity()).ifPresent(handler -> handler.getStacksHandler(slotIdentifier).ifPresent(stacks -> stacks.addTransientModifier(new AttributeModifier(UUID.fromString(uuid), "bonus_curio_slots", amount.compute(modifier.getEffectiveLevel()), AttributeModifier.Operation.ADDITION))));
		}
		EquipmentChangeModifierHook.super.onEquip(tool, modifier, context);
	}

	@Override
	public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
//        ConstructsCasting.LOGGER.info("ringbearer uneq");
		if (!context.getLevel().isClientSide()) {
			CuriosApi.getCuriosInventory(context.getEntity()).ifPresent(handler -> handler.getStacksHandler(slotIdentifier).ifPresent(stacks -> stacks.removeModifier(UUID.fromString(uuid))));
		}
		EquipmentChangeModifierHook.super.onUnequip(tool, modifier, context);
	}
	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return List.of(ModifierHooks.EQUIPMENT_CHANGE);
	}
}
