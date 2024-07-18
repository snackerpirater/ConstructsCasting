package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.DifferenceIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.CompositeCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialCastingRecipe;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.function.Consumer;

public class CCRecipes extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, ISmelteryRecipeHelper, IRecipeHelper {
	public CCRecipes(DataGenerator pGenerator) {
		super(pGenerator);
	}

	public static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ConstructsCasting.MOD_ID);

	public static final RegistryObject<RecipeSerializer<ScrollMeltingRecipe>> scrollMeltingSerializer = RECIPE_SERIALIZERS.register("scroll_melting", () -> LoadableRecipeSerializer.of(ScrollMeltingRecipe.LOADER));

	private static Consumer<FinishedRecipe> aConsumer;
	private static final String castingFolder = "smeltery/casting/";
	private static final String alloyFolder = "smeltery/alloys/";
	private static final String materialFolder = "tools/materials/";
	private static final String modifierFolder = "tools/modifiers/";
	private static final String meltingFolder = "smeltery/melting/metal/";

	@Override
	public String getModId() {
		return ConstructsCasting.MOD_ID;
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		aConsumer = consumer;
		//arcanium making
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_INGOT.get()), new FluidStack(CCFluids.moltenArcanium.get(), FluidValues.INGOT), 800, 30)
				.save(consumer, ConstructsCasting.id(meltingFolder + "metal/molten_arcanium/arcane_ingot"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.moltenArcanium.get(), FluidValues.INGOT), 800)
				.addInput(new FluidStack(CCFluids.arcaneEssence.get(), 4*FluidValues.BOTTLE))
				.addInput(FluidIngredient.of(CCFluids.Tags.ARCANIUM_BASE, FluidValues.INGOT))
				.save(consumer, ConstructsCasting.id(alloyFolder + "molten_arcanium"));
		materialMeltingCasting(consumer, CCMaterials.arcanium, CCFluids.moltenArcanium, FluidValues.INGOT, materialFolder);
		castingWithCast(consumer, CCFluids.moltenArcanium, false, FluidValues.INGOT, TinkerSmeltery.ingotCast, ItemRegistry.ARCANE_INGOT.get(), castingFolder + "arcane_ingot");
		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcanium).setIngredient(ItemRegistry.ARCANE_INGOT.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "arcanium/ingot"));
		//exilite making
		materialMeltingCasting(consumer, CCMaterials.exilite, CCFluids.moltenExilite, FluidValues.INGOT, materialFolder);
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.exiliteIngot.get()),new FluidStack(CCFluids.moltenExilite.get(), FluidValues.INGOT), 800, 30).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/ingot"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.exiliteNugget.get()),new FluidStack(CCFluids.moltenExilite.get(), FluidValues.NUGGET), 800, 4).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/nugget"));
		ingotCasting(consumer, CCFluids.moltenExilite, CCItems.exiliteIngot.get(), castingFolder + "exilite_ingot");
		nuggetCastingRecipe(consumer, CCFluids.moltenExilite, CCItems.exiliteNugget.get(), castingFolder + "exilite_nugget");
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.moltenExilite.get(), FluidValues.INGOT))
				.addInput(TinkerFluids.moltenCobalt.getForgeTag(), FluidValues.INGOT)
				.addInput(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE)
				.addInput(TinkerFluids.moltenObsidian.getLocalTag(), FluidValues.GLASS_PANE)
				.save(consumer, ConstructsCasting.id(alloyFolder + "molten_exilite"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.exilite).setIngredient(CCItems.exiliteIngot.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "exilite/ingot"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.exilite).setIngredient(CCItems.exiliteNugget.get()).setValue(1).setNeeded(9).save(consumer, ConstructsCasting.id(materialFolder + "exilite/nugget"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MAGEHUNTER.get()), new FluidStack(CCFluids.moltenExilite.get(), 2*FluidValues.INGOT), 700, 30).setDamagable(25).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/magehunter"));
		//arcane cloth making
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MAGIC_CLOTH.get())
				.setCast(Items.COBWEB.asItem(), true)
				.setCoolingTime(52)
				.setFluid(CCFluids.arcaneEssence.get(), 6*FluidValues.BOTTLE)
				.save(consumer, ConstructsCasting.id(castingFolder + "arcane_cloth"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcaneCloth)
				.setIngredient(ItemRegistry.MAGIC_CLOTH.get())
				.setValue(1).setNeeded(1)
				.save(consumer, ConstructsCasting.id(materialFolder + "arcane_cloth"));
		//frozen bone
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.FROZEN_BONE_SHARD.get()).setCast(Items.BONE, true).setFluidAndTime(new FluidStack(CCFluids.iceEssence.get(), 4*FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(castingFolder + "frozen_bone"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.frozenBone).setIngredient(ItemRegistry.FROZEN_BONE_SHARD.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "frozen_bone"));
		MaterialFluidRecipeBuilder.material(CCMaterials.frozenBone).setInputId(MaterialIds.bone).setFluidAndTemp(new FluidStack(CCFluids.iceEssence.get(), 4*FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(materialFolder + "frozen_bone_composite"));
		//frosted rod
		MaterialRecipeBuilder.materialRecipe(CCMaterials.frostRod).setIngredient(ItemRegistry.FROSTED_HELVE.get()).setValue(3).setNeeded(1).setLeftover(ItemOutput.fromItem(ItemRegistry.FROZEN_BONE_SHARD.get())).save(consumer, ConstructsCasting.id(materialFolder + "frost_rod"));
		//casting ability
		ModifierRecipeBuilder.modifier(CCModifiers.CASTING)
				.allowCrystal()
				.exactLevel(1)
				.setSlots(SlotType.ABILITY, 1)
				.setTools(TinkerTags.Items.STAFFS)
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.addInput(ItemRegistry.CINDER_ESSENCE.get())
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.addInput(ItemTags.create(IronsSpellbooks.id("inscribed_rune")))
				.addInput(ItemTags.create(IronsSpellbooks.id("inscribed_rune")))
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/casting"));
		//swiftcasting
		ModifierRecipeBuilder.modifier(CCModifiers.SWIFTCASTING)
				.allowCrystal()
				.exactLevel(1)
				.setSlots(SlotType.ABILITY, 1)
				.setTools(TinkerTags.Items.STAFFS)
				.addInput(ItemRegistry.MAGIC_CLOTH.get())
				.addInput(ItemRegistry.ARCANE_SALVAGE.get())
				.addInput(ItemRegistry.MAGIC_CLOTH.get())
				.addInput(Items.RABBIT_FOOT)
				.addInput(Items.RABBIT_FOOT)
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/swiftcasting"));
		//spell prot
		ItemCastingRecipeBuilder.tableRecipe(CCItems.exiliteReinforcement.get())
				.setCast(TinkerTables.pattern.get(), true)
				.setFluid(CCFluids.moltenExilite.get(), FluidValues.INGOT)
				.setCoolingTime(10)
				.save(consumer, ConstructsCasting.id(castingFolder + "exilite_reinforcement"));
		IncrementalModifierRecipeBuilder.modifier(CCModifiers.SPELL_PROTECTION.getId())
				.setInput(CCItems.exiliteReinforcement.get(), 1, 5)
				.setSlots(SlotType.DEFENSE, 1)
				.setTools(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.ARMOR), Ingredient.of(TinkerTags.Items.HELD)))
				.save(consumer, ConstructsCasting.id(modifierFolder + "defense/spell_protection"));
		//imbued
		ModifierRecipeBuilder.modifier(CCModifiers.IMBUED)
				.allowCrystal()
				.exactLevel(1)
				.setSlots(SlotType.ABILITY, 1)
				.setTools(CompoundIngredient.of(DifferenceIngredient.of(Ingredient.of(TinkerTags.Items.HELD), Ingredient.of(TinkerTags.Items.SWORD)), Ingredient.of(TinkerTags.Items.ARMOR)))
				.setMaxLevel(1)
				.addInput(ItemRegistry.ARCANE_SALVAGE.get())
				.addInput(TinkerTags.Items.SWORD)
				.addInput(ItemRegistry.ARCANE_SALVAGE.get())
				.addInput(ItemRegistry.MANA_UPGRADE_ORB.get())
				.addInput(ItemRegistry.MANA_UPGRADE_ORB.get())
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/imbued"));
		//encyclopedic
		ModifierRecipeBuilder.modifier(CCModifiers.ENCYCLOPEDIC)
				.allowCrystal()
				.exactLevel(1)
				.setTools(Ingredient.of(CCItems.slimySpellbook.get()))
				.setMaxLevel(1)
				.addInput(TinkerCommons.encyclopedia)
				.save(consumer, ConstructsCasting.id(modifierFolder + "slotless/encyclopedic"));
		//gay slime armor!!!
		SwappableModifierRecipeBuilder.modifier(TinkerModifiers.embellishment, "constructs_casting:rainbowslime")
				.setTools(TinkerTags.Items.EMBELLISHMENT_SLIME)
				.addInput(Items.RED_DYE)
				.addInput(Items.ORANGE_DYE)
				.addInput(Items.GREEN_DYE)
				.addInput(Items.BLUE_DYE)
				.addInput(Items.PURPLE_DYE)
				.save(consumer, ConstructsCasting.id(modifierFolder + "slotless/rainbowslime_embellishment"));
		//elemental power upgrades
		incrementalModifierRecipe(CCModifiers.MANA_UPGRADE,      ItemRegistry.MANA_RUNE.get(),      ItemRegistry.MANA_UPGRADE_ORB.get(),      "mana_upgrade");
		incrementalModifierRecipe(CCModifiers.COOLDOWN_UPGRADE,  ItemRegistry.COOLDOWN_RUNE.get(),  ItemRegistry.COOLDOWN_UPGRADE_ORB.get(),  "cooldown_upgrade");
		incrementalModifierRecipe(CCModifiers.FIRE_UPGRADE,      ItemRegistry.FIRE_RUNE.get(),      ItemRegistry.FIRE_UPGRADE_ORB.get(),      "fire_upgrade");
		incrementalModifierRecipe(CCModifiers.ICE_UPGRADE,       ItemRegistry.ICE_RUNE.get(),       ItemRegistry.ICE_UPGRADE_ORB.get(),       "ice_upgrade");
		incrementalModifierRecipe(CCModifiers.LIGHTNING_UPGRADE, ItemRegistry.LIGHTNING_RUNE.get(), ItemRegistry.LIGHTNING_UPGRADE_ORB.get(), "lightning_upgrade");
		incrementalModifierRecipe(CCModifiers.ENDER_UPGRADE,     ItemRegistry.ENDER_RUNE.get(),     ItemRegistry.ENDER_UPGRADE_ORB.get(),     "ender_upgrade");
		incrementalModifierRecipe(CCModifiers.HOLY_UPGRADE,      ItemRegistry.HOLY_RUNE.get(),      ItemRegistry.HOLY_UPGRADE_ORB.get(),      "holy_upgrade");
		incrementalModifierRecipe(CCModifiers.BLOOD_UPGRADE,     ItemRegistry.BLOOD_RUNE.get(),     ItemRegistry.BLOOD_UPGRADE_ORB.get(),     "blood_upgrade");
		incrementalModifierRecipe(CCModifiers.EVOCATION_UPGRADE, ItemRegistry.EVOCATION_RUNE.get(), ItemRegistry.EVOCATION_UPGRADE_ORB.get(), "evocation_upgrade");
		incrementalModifierRecipe(CCModifiers.NATURE_UPGRADE,    ItemRegistry.NATURE_RUNE.get(),    ItemRegistry.NATURE_UPGRADE_ORB.get(),    "nature_upgrade");
		//essence making
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_ESSENCE.get()), new FluidStack(CCFluids.arcaneEssence.get(), 250), 100, 5)
				.save(consumer, ConstructsCasting.id(meltingFolder + "arcane_essence"));
		essenceRecipe(CCFluids.fireEssence,      TinkerFluids.blazingBlood   .getLocalTag(),100, "fire_essence"     );
		essenceRecipe(CCFluids.iceEssence,       TinkerFluids.powderedSnow.getForgeTag(),   250, "ice_essence"      );
		essenceRecipe(CCFluids.lightningEssence, CCFluids.Tags.LIQUID_LIGHTNING,            250, "lightning_essence");
		essenceRecipe(CCFluids.enderEssence,     TinkerFluids.moltenEnder    .getLocalTag(),250, "ender_essence"    );
		essenceRecipe(CCFluids.holyEssence,      CCFluids.Tags.LIQUID_DIVINITY,             250, "holy_essence"     );
		essenceRecipe(CCFluids.bloodEssence,     TinkerFluids.meatSoup       .getLocalTag(),250, "blood_essence"    );
		essenceRecipe(CCFluids.evocationEssence, TinkerFluids.moltenEmerald  .getLocalTag(),100, "evocation_essence");
		essenceRecipe(CCFluids.natureEssence,    CCFluids.Tags.POISONOUS_POTATO_STEW,       50, "nature_essence"   );
		//rune casting
		runeCastingRecipe(CCFluids.fireEssence,      ItemRegistry.FIRE_RUNE.get(),           "fire_rune");
		runeCastingRecipe(CCFluids.iceEssence,       ItemRegistry.ICE_RUNE.get(),             "ice_rune");
		runeCastingRecipe(CCFluids.lightningEssence, ItemRegistry.LIGHTNING_RUNE.get(), "lightning_rune");
		runeCastingRecipe(CCFluids.enderEssence,     ItemRegistry.ENDER_RUNE.get(),         "ender_rune");
		runeCastingRecipe(CCFluids.holyEssence,      ItemRegistry.HOLY_RUNE.get(),           "holy_rune");
		runeCastingRecipe(CCFluids.bloodEssence,     ItemRegistry.BLOOD_RUNE.get(),         "blood_rune");
		runeCastingRecipe(CCFluids.evocationEssence, ItemRegistry.EVOCATION_RUNE.get(), "evocation_rune");
		runeCastingRecipe(CCFluids.natureEssence,    ItemRegistry.NATURE_RUNE.get(),       "nature_rune");
		//tater stuff
		MeltingRecipeBuilder.melting(Ingredient.of(Items.POTATO), new FluidStack(CCFluids.potatoStew.get(), 50), 100, 8)
				.save(consumer, ConstructsCasting.id(meltingFolder + "potato_stew_melting"));
		MeltingRecipeBuilder.melting(Ingredient.of(Items.POISONOUS_POTATO), new FluidStack(CCFluids.poisonousPotatoStew.get(), 50), 100, 8)
				.save(consumer, ConstructsCasting.id(meltingFolder + "poisonous_potato_stew_melting"));
		ItemCastingRecipeBuilder.tableRecipe(CCItems.potatoStewBowl.get())
				.setFluidAndTime(new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL))
				.setCoolingTime(1)
				.setCast(Items.BOWL.asItem(), true)
				.save(consumer, ConstructsCasting.id(meltingFolder + "potato_stew_casting"));
		ItemCastingRecipeBuilder.tableRecipe(CCItems.poisonousPotatoStewBowl.get())
				.setFluidAndTime(new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL))
				.setCoolingTime(1)
				.setCast(Items.BOWL.asItem(), true)
				.save(consumer, ConstructsCasting.id(meltingFolder + "poisonous_potato_stew_casting"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.poisonousPotatoStew.get(), 50), 100)
				.addInput(CCFluids.potatoStew.get(), 40)
				.addInput(TinkerFluids.venom.get(), 10)
				.save(consumer, ConstructsCasting.id(alloyFolder + "poisonous_potato_stew_alloying"));
		//divinity
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.DIVINE_PEARL.get()), new FluidStack(CCFluids.liquidDivinity.get(), 250), 700, 5)
				.save(consumer, ConstructsCasting.id(meltingFolder + "divinity"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.liquidDivinity.get(), 250), 800)
				.addInput(TinkerFluids.moltenGold.getForgeTag(), FluidValues.INGOT)
				.addInput(TinkerFluids.moltenAmethyst.getLocalTag(), FluidValues.GEM)
				.save(consumer, ConstructsCasting.id(alloyFolder + "divinity"));
		//ink
		inkFillingRecipe(ItemRegistry.INK_COMMON.get(), CCFluids.Tags.ink("common"), "common");
		inkFillingRecipe(ItemRegistry.INK_UNCOMMON.get(), CCFluids.Tags.ink("uncommon"), "uncommon");
		inkFillingRecipe(ItemRegistry.INK_RARE.get(), CCFluids.Tags.ink("rare"), "rare");
		inkFillingRecipe(ItemRegistry.INK_EPIC.get(), CCFluids.Tags.ink("epic"), "epic");
		inkFillingRecipe(ItemRegistry.INK_LEGENDARY.get(), CCFluids.Tags.ink("legendary"), "legendary");

		MeltingRecipeBuilder.melting(Ingredient.of(Items.INK_SAC), new FluidStack(CCFluids.squidInk.get(), FluidValues.BOTTLE), 300, 8).save(consumer, ConstructsCasting.id("smeltery/melting/ink"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.commonInk.get(),    250), 300).addInput(CCFluids.squidInk.get(),                   750).addInput(CCFluids.arcaneEssence.get(), 500).save(consumer, ConstructsCasting.id(alloyFolder + "common_ink"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.uncommonInk.get(),  250), 300).addInput(CCFluids.Tags.ink("common"),   750).addInput(TinkerFluids.moltenCopper  .getForgeTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "uncommon_ink"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.rareInk.get(),      250), 300).addInput(CCFluids.Tags.ink("uncommon"), 750).addInput(TinkerFluids.moltenIron    .getForgeTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "rare_ink"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.epicInk.get(),      250), 300).addInput(CCFluids.Tags.ink("rare"), 	   750).addInput(TinkerFluids.moltenGold    .getForgeTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "epic_ink"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.legendaryInk.get(), 250), 300).addInput(CCFluids.Tags.ink("epic"),     750).addInput(TinkerFluids.moltenAmethyst.getLocalTag(), FluidValues.GEM)  .save(consumer, ConstructsCasting.id(alloyFolder + "legendary_ink"));

		//slimy spellbook!
		ItemCastingRecipeBuilder.basinRecipe(CCItems.slimySpellbook.get()).setCast(ItemRegistry.DIAMOND_SPELL_BOOK.get(),  true).setFluidAndTime(new FluidStack(TinkerFluids.enderSlime.get(),                  1000)).save(consumer, ConstructsCasting.id(castingFolder + "tinkerers_spellbook"));
		//plated spellbook
		ItemCastingRecipeBuilder.tableRecipe(CCItems.platedSpellbook.get()).setCast(ItemRegistry.DIAMOND_SPELL_BOOK.get(), true).setFluidAndTime(new FluidStack(TinkerFluids.moltenCobalt.get(), 4*FluidValues.INGOT)).save(consumer, ConstructsCasting.id(castingFolder + "plated_spellbook"));
		//eldritch staff
		ShapedRecipeBuilder.shaped(CCItems.eldritchStaff.get())
				.define('s', Items.ECHO_SHARD)
				.define('l', ItemTags.WARPED_STEMS)
				.define('a', ItemRegistry.ARCANE_INGOT.get())
				.pattern("sls")
				.pattern(" a ")
				.pattern(" l ")
				.unlockedBy("has_item", RecipeProvider.has(Items.ECHO_SHARD))
				.save(consumer, ConstructsCasting.id("crafting/eldritch_staff"));
	}
	public static void runeCastingRecipe(FluidObject<UnplaceableFluid> essence, Item result, String recipeId) {
		 ItemCastingRecipeBuilder.tableRecipe(result).setCast(ItemRegistry.BLANK_RUNE.get(), true).setFluidAndTime(new FluidStack(essence.get(), 1000)).save(aConsumer, ConstructsCasting.id(castingFolder + recipeId));
	}
	public static void essenceRecipe(FluidObject<?> essence, TagKey<Fluid> alloyIngredient, int amount, String recipeId) {
		AlloyRecipeBuilder.alloy(new FluidStack(essence.get(), FluidValues.BOTTLE), 700).addInput(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE).addInput(alloyIngredient, amount).save(aConsumer, ConstructsCasting.id(alloyFolder + recipeId));
	}
	public static void inkFillingRecipe(Item inkBottle, TagKey<Fluid> ink, String rarity) {
		ItemCastingRecipeBuilder.tableRecipe(inkBottle).setFluid(ink,FluidValues.BOTTLE).setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1).save(aConsumer, ConstructsCasting.id(castingFolder + "ink_" + rarity));
	}
	public static void incrementalModifierRecipe(ModifierId modifier, ItemLike runeItem, ItemLike orbItem, String id) {
		IncrementalModifierRecipeBuilder.modifier(modifier)
				.setInput(runeItem, 1, 16)
				.setSlots(SlotType.UPGRADE, 1)
				.allowCrystal()
				.setMaxLevel(3)
				.save(aConsumer, ConstructsCasting.id(modifierFolder + "upgrade/" + id + "_rune"));
		ModifierRecipeBuilder.modifier(modifier)
				.addInput(orbItem)
				.setSlots(SlotType.UPGRADE, 1)
				.allowCrystal()
				.setMaxLevel(3)
				.save(aConsumer, ConstructsCasting.id(modifierFolder + "upgrade/" + id + "_orb"));

	}
}
