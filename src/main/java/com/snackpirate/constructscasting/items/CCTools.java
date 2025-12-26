package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.materials.CCMaterialStats;
import com.snackpirate.constructscasting.materials.CCToolStats;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.definition.module.ToolModule;
import slimeknights.tconstruct.library.tools.definition.module.build.*;
import slimeknights.tconstruct.library.tools.definition.module.display.StatTypesToolNameModule;
import slimeknights.tconstruct.library.tools.definition.module.interaction.DualOptionInteraction;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialTraitsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningSpeedModifierModule;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.stats.*;

import java.util.Set;

public class CCTools {
	public static class CCToolDefinitions extends AbstractToolDefinitionDataProvider {

		public static final ToolDefinition SLIMY_SPELLBOOK = ToolDefinition.create(CCItems.slimySpellbook);
		public static final ToolDefinition TRAVELLERS_SPELLBOOK = ToolDefinition.create(CCItems.travellersSpellbook);
		public static final ToolDefinition PLATED_SPELLBOOK = ToolDefinition.create(CCItems.platedSpellbook);
		public static final ToolDefinition ELDRITCH_STAFF = ToolDefinition.create(CCItems.eldritchStaff);
		public static final ToolDefinition WAND = ToolDefinition.create(CCItems.wand);
		public static final ToolDefinition BATTLESTAFF = ToolDefinition.create(CCItems.battlestaff);
		public static final ToolDefinition FLAMBERGE = ToolDefinition.create(CCItems.flamberge);
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

			RandomMaterial anyMaterial = RandomMaterial.random().allowHidden().build();
            RandomMaterial tier1Material = RandomMaterial.random().tier(1).build();
			DefaultMaterialsModule defaultThreeParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material).build();
            DefaultMaterialsModule defaultFourParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material, tier1Material).build();
			DefaultMaterialsModule ancientThreeParts = DefaultMaterialsModule.builder().material(anyMaterial, anyMaterial, anyMaterial).build();
			define(SLIMY_SPELLBOOK)
					.module(ToolSlotsModule.builder()
							//match slimesuit, but w/o abilities since what's the point?
							.slots(SlotType.UPGRADE, 5)
                            .slots(SlotType.ABILITY, 1)
							.build())
					.module(new SetStatsModule(StatsNBT.builder()
                            .set(CCToolStats.MAX_MANA, 125)
                            .set(CCToolStats.COOLDOWN_REDUCTION, 0.1f)
                            .set(CCToolStats.SPELL_SLOTS, 6)
                            .build()))
//					.module(ToolTraitsModule.builder().trait(CCModifiers.SPELL_SLOTS.getId()).build())
            ;

			define(TRAVELLERS_SPELLBOOK)
					.module(PartStatsModule.parts()
							.part(CCItems.spellbookCover.get(), 0.375f)
							.part(CCItems.spellbookCover.get(), 0.375f)
							.part(CCItems.pages.get())
							.primaryPart(0)
							.build())
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 3)
							.slots(SlotType.ABILITY, 1)
							.build())
                    .module(defaultThreeParts);
			define(PLATED_SPELLBOOK)
                    .module(defaultThreeParts)
					.module(PartStatsModule.parts()
							.part(CCItems.spellbookPlating.get())
							.part(CCItems.spellbookCover.get())
							.part(CCItems.pages.get())
                            .primaryPart(0)
							.build())
//                    .module(new SetStatsModule(StatsNBT.builder().set(CCToolStats.SPELL_SLOTS, 10).build()))
					.module(ToolSlotsModule.builder()
							.slots(SlotType.UPGRADE, 1)
							.build())
					.module(new StatTypesToolNameModule(Set.of(CCMaterialStats.Statless.ADORNMENT.getIdentifier())))
					.build();
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
							.set(ToolStats.USE_ITEM_SPEED, 0.4f).build()))
                    .module(DualOptionInteraction.INSTANCE);
			define(WAND)
					.smallToolStartingSlots()
                    .module(defaultThreeParts)
					.module(PartStatsModule.parts()
							.part(CCItems.facetedGem)
							.part(CCItems.facetedGem)
							.part(CCItems.wandRod)
							.primaryPart(0)
							.build())
					.module(ToolTraitsModule.builder()
							.trait(CCModifiers.CASTING).build())
					.module(new StatTypesToolNameModule(Set.of(CCMaterialStats.Statless.ADORNMENT.getIdentifier())))
					.module(DualOptionInteraction.INSTANCE);
			define(BATTLESTAFF)
					.largeToolStartingSlots()
                    .module(defaultFourParts)
					.module(PartStatsModule.parts()
							.part(TinkerToolParts.broadAxeHead)
							.part(CCItems.facetedGem)
							.part(CCItems.wandRod)
							.part(TinkerToolParts.toughHandle)
							.primaryPart(0)
							.build())
					.module(ToolTraitsModule.builder()
							.trait(CCModifiers.CASTING).build())
                    .module(new SetStatsModule(StatsNBT.builder()
                            .set(ToolStats.ATTACK_DAMAGE, 2f)
                            .set(ToolStats.ATTACK_SPEED, 1.0f).build()))
                    .module(new MultiplyStatsModule(MultiplierNBT.builder()
                            .set(ToolStats.ATTACK_DAMAGE, 1.25f)
                            .set(ToolStats.MINING_SPEED, 0.25f)
                            .set(ToolStats.DURABILITY, 1.5f).build()))
					.module(new StatTypesToolNameModule(Set.of(HeadMaterialStats.ID, CCMaterialStats.Statless.ADORNMENT.getIdentifier())))
					.module(DualOptionInteraction.INSTANCE).build();

			ToolModule[] swordHarvest = {
					IsEffectiveModule.tag(TinkerTags.Blocks.MINABLE_WITH_SWORD),
					MiningSpeedModifierModule.blocks(7.5f, Blocks.COBWEB)
			};
			define(FLAMBERGE)
					.module(MaterialStatsModule.stats()
							.stat(HeadMaterialStats.ID)
							.stat(PlatingMaterialStats.LEGGINGS)
							.stat(HandleMaterialStats.ID)
							.build())
					.module(ancientThreeParts)
					.module(new MaterialTraitsModule(PlatingMaterialStats.LEGGINGS.getId(), 1), ToolHooks.REBALANCED_TRAIT)
					.module(ToolSlotsModule.builder()
							.slots(SlotType.ABILITY, 1)
							.slots(SlotType.UPGRADE, 2)
							.slots(SlotType.DEFENSE, 2)
							.build())
					.module(new SetStatsModule(StatsNBT.builder()
							.set(ToolStats.ATTACK_DAMAGE, 2.5f)
							.set(ToolStats.ATTACK_SPEED, 1.0f)
							.set(ToolStats.BLOCK_AMOUNT, 15).build()))
					.module(new MultiplyStatsModule(MultiplierNBT.builder()
							.set(ToolStats.ATTACK_DAMAGE, 1.25f)
							.set(ToolStats.MINING_SPEED, 0.25f)
							.set(ToolStats.DURABILITY, 2f)
							.set(ToolStats.DRAW_SPEED, 1.5f)
							.build()))
					.module(ToolTraitsModule.builder()
							.trait(TinkerModifiers.springing, 1)
							.build())
					// behavior
					.module(ToolActionsModule.of(ToolActions.SWORD_DIG))
					.module(swordHarvest)
					.build();
		}

		@Override
		public String getName() {
			return "Construct's Casting Tool Definitions";
		}
	}
}
