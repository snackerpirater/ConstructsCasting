package com.snackpirate.constructscasting.modifiers;

import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ImbuedModifier extends Modifier implements EquipmentChangeModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		super.registerHooks(hookBuilder);
		hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
	}

	@Override
	public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
		ItemStack replacement = context.getReplacement();
		if (!ISpellContainer.isSpellContainer(replacement)) {
			var container = ISpellContainer.create(1, true, replacement.getItem() instanceof ModifiableArmorItem);
			container.save(replacement);
		}
//	code for if i want to make imbued give >1 slot (it's kinda buggy past that)
//		need to account for higher levels of imbued + swords getting imbued
//		else if (ISpellContainer.get(replacement).getMaxSpellCount() < expectedSpellSlots) {
//			var container = ISpellContainer.get(replacement);
//			var newContainer = ISpellContainer.create(expectedSpellSlots, true, replacement.getItem() instanceof ModifiableArmorItem);
//			if (container.getAllSpells() != null) {
//				SpellData[] spells = container.getAllSpells();
//				for (SpellData data : spells)
//					if (data != null) newContainer.addSpell(data.getSpell(), data.getLevel(), false, replacement);
//			}
//			newContainer.save(replacement);
//		}
		EquipmentChangeModifierHook.super.onEquip(tool, modifier, context);


	}
}
