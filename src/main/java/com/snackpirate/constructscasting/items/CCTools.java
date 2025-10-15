package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.materials.CCToolStats;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolSlotsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static slimeknights.tconstruct.tools.TinkerToolParts.*;

public class CCTools {
	public static class CCToolDefinitions extends AbstractToolDefinitionDataProvider {

		public static final ToolDefinition SLIMY_SPELLBOOK = ToolDefinition.create(CCItems.slimySpellbook);
		public static final ToolDefinition TRAVELLERS_SPELLBOOK = ToolDefinition.create(CCItems.travellersSpellbook);
		public static final ToolDefinition PLATED_SPELLBOOK = ToolDefinition.create(CCItems.platedSpellbook);
		public static final ToolDefinition ELDRITCH_STAFF = ToolDefinition.create(CCItems.eldritchStaff);
		public CCToolDefinitions(PackOutput generator, String modId) {
			super(generator, modId);
		}

		@Override
		protected void addToolDefinitions() {
			//Traveller's armor: Mid upgrade, mid defense
			//Plate armor: Low upgrade, high defense
			//Slimy armor: High upgrade, no defense

			//Traveller's book: Mid upgrade, 6 slots
			//Plate book: Low upgrade, high slots, defense
			//Slimy book: High upgrade, 6 slots
			define(SLIMY_SPELLBOOK)
					.module(ToolSlotsModule.builder()
							//match slimesuit, but w/o abilities since what's the point?
							.slots(SlotType.UPGRADE, 5)
                            .slots(CCModifiers.AFFINITY_SLOT, 2)
							.build())
					.module(new SetStatsModule(StatsNBT.builder()
                            .set(CCToolStats.MAX_MANA, 125)
                            .set(CCToolStats.COOLDOWN_REDUCTION, 0.1f)
                            .set(CCToolStats.SPELL_SLOTS, 6)
                            .build()))
					.module(ToolTraitsModule.builder().trait(CCModifiers.SPELL_SLOTS.getId()).build());

			define(TRAVELLERS_SPELLBOOK)
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 3)
							.build())
					.module(ToolTraitsModule.builder()
							.trait(CCModifiers.COOLDOWN_UPGRADE)
							.build());
			define(PLATED_SPELLBOOK)
					.module(PartStatsModule.parts()
							.part(CCItems.spellbookPlating.get())
							.part(CCItems.spellbookCover.get())
							.part(CCItems.pages.get())
                            .primaryPart(0)
							.build())
                    .module(DefaultMaterialsModule.builder().material(MaterialIds.cobalt).material(MaterialIds.wood).material(CCMaterials.paper).build())
//					.module(new SetStatsModule(StatsNBT.builder().set(CCToolStats.SPELL_SLOTS, 10).build()))
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 1)
							.build())
					.module(ToolTraitsModule.builder().trait(CCModifiers.SPELL_SLOTS.getId()).build());
			define(ELDRITCH_STAFF)
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 3)
							.slots(SlotType.ABILITY, 1)
							.build())
					.module(ToolTraitsModule.builder()
							.trait(CCModifiers.ELDRITCH_UPGRADE, 3)
							.trait(CCModifiers.CASTING).build())
					.module(new SetStatsModule(StatsNBT.builder()
							.set(ToolStats.DURABILITY, 1337)
							.set(ToolStats.BLOCK_AMOUNT, 20)
							.set(ToolStats.BLOCK_ANGLE, 50)
							.set(ToolStats.USE_ITEM_SPEED, 0.4f).build()));
		}

		@Override
		public String getName() {
			return "Construct's Casting Tool Definitions";
		}
	}
}
