package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolSlotsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitsModule;

public class CCTools {
	public static class CCToolDefinitions extends AbstractToolDefinitionDataProvider {

		public CCToolDefinitions(DataGenerator generator, String modId) {
			super(generator, modId);
		}

		@Override
		protected void addToolDefinitions() {
			define(TinkerersSpellbookItem.DEFINITION)
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 3)
							.build())
					.module(ToolTraitsModule.builder()
							.trait(CCModifiers.SPELLBOUND)
							.build());
		}

		@Override
		public String getName() {
			return "Construct's Casting Tool Definitions";
		}
	}
}
