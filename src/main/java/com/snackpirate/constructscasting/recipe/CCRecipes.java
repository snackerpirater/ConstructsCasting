package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCBlocks;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.DifferenceIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.recipe.ingredient.EntityIngredient;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import slimeknights.mantle.recipe.ingredient.SizedIngredient;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.json.predicate.modifier.ModifierPredicate;
import slimeknights.tconstruct.library.json.predicate.modifier.SlotTypeModifierPredicate;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.entitymelting.EntityMeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.partbuilder.recycle.PartBuilderToolRecycleBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerMaterials;
import slimeknights.tconstruct.shared.block.SlimeType;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.recipe.ModifierRemovalRecipeBuilder;
import slimeknights.tconstruct.world.TinkerWorld;

import java.util.function.Consumer;

public class CCRecipes extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, ISmelteryRecipeHelper, IRecipeHelper, IToolRecipeHelper, ICommonRecipeHelper {

    public CCRecipes(PackOutput output) {
		super(output);
	}

	public static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ConstructsCasting.MOD_ID);

	public static final RegistryObject<RecipeSerializer<ScrollMeltingRecipe>> scrollMeltingSerializer = RECIPE_SERIALIZERS.register("scroll_melting", () -> LoadableRecipeSerializer.of(ScrollMeltingRecipe.LOADER));

	private static final String castingFolder = "smeltery/casting/";
	private static final String alloyFolder = "smeltery/alloys/";
	private static final String materialFolder = "tools/materials/";
	private static final String modifierFolder = "tools/modifiers/";
	private static final String salvageFolder = "tools/modifiers/salvage/";
	private static final String meltingFolder = "smeltery/melting/metal/";
	private static final String recyclingFolder = "tools/recycling/";
    private static final String partsFolder = "tools/parts/";

	@Override
	public String getModId() {
		return ConstructsCasting.MOD_ID;
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		//arcanium making
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_INGOT.get()), new FluidStack(CCFluids.moltenArcanium.get(), FluidValues.INGOT), 800, 30)
				.save(consumer, ConstructsCasting.id(meltingFolder + "arcane/ingot"));
		AlloyRecipeBuilder.alloy((FluidOutput.fromFluid(CCFluids.moltenArcanium.get(), FluidValues.INGOT)), 800)
				.addInput(new FluidStack(CCFluids.arcaneEssence.get(), 4*FluidValues.BOTTLE))
				.addInput(FluidIngredient.of(CCFluids.Tags.ARCANIUM_BASE, FluidValues.INGOT))
				.save(consumer, ConstructsCasting.id(alloyFolder + "molten_arcanium"));
		materialMeltingCasting(consumer, CCMaterials.arcanium, CCFluids.moltenArcanium, FluidValues.INGOT, materialFolder);
		castingWithCast(consumer, CCFluids.moltenArcanium, FluidValues.INGOT, TinkerSmeltery.ingotCast, ItemRegistry.ARCANE_INGOT.get(), castingFolder + "arcane_ingot");
		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcanium).setIngredient(ItemRegistry.ARCANE_INGOT.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "arcanium/ingot"));

		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcanium).setIngredient(CCItems.arcaneNugget.get()).setValue(1).setNeeded(9).save(consumer, location(materialFolder + "arcanium/nugget"));
		nuggetCasting(consumer, CCFluids.moltenArcanium, CCItems.arcaneNugget.get(), castingFolder + "arcane_nugget");
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.arcaneNugget), new FluidStack(CCFluids.moltenArcanium.get(), FluidValues.NUGGET), 1175, 6).save(consumer, location(meltingFolder + "arcane/nugget"));
		packingRecipe(consumer, RecipeCategory.MISC, "ingot", ItemRegistry.ARCANE_INGOT.get(), "nugget", CCItems.arcaneNugget, ItemTags.create(ResourceLocation.parse("forge:nuggets/arcane")), materialFolder);

		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcanium).setIngredient(CCBlocks.arcaneBlock).setValue(9).setNeeded(1).save(consumer, location(materialFolder + "arcane/block"));
		ItemCastingRecipeBuilder.basinRecipe(CCBlocks.arcaneBlock).setFluidAndTime(CCFluids.moltenArcanium, FluidValues.METAL_BLOCK).save(consumer, location(castingFolder + "arcane/block"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCBlocks.arcaneBlock), CCFluids.moltenArcanium, FluidValues.METAL_BLOCK).save(consumer, location(meltingFolder + "arcane/block"));
		packingRecipe(consumer, RecipeCategory.MISC, "block", CCBlocks.arcaneBlock.get(), "ingot", ItemRegistry.ARCANE_INGOT.get(), ItemTags.create(ResourceLocation.parse("forge:ingots/arcane")), materialFolder);

		//exilite making
		materialMeltingCasting(consumer, CCMaterials.exilite, CCFluids.moltenExilite, FluidValues.INGOT, materialFolder);
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.exiliteIngot.get()), new FluidStack(CCFluids.moltenExilite.get(), FluidValues.INGOT), 800, 30).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/ingot"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.exiliteNugget.get()), new FluidStack(CCFluids.moltenExilite.get(), FluidValues.NUGGET), 800, 4).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/nugget"));
		ingotCasting(consumer, CCFluids.moltenExilite, CCItems.exiliteIngot.get(), castingFolder + "exilite_ingot");
		nuggetCasting(consumer, CCFluids.moltenExilite, CCItems.exiliteNugget.get(), castingFolder + "exilite_nugget");
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.moltenExilite.get(), FluidValues.INGOT))
				.addInput(TinkerFluids.moltenCobalt.getCommonTag(), FluidValues.INGOT)
				.addInput(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE)
				.addInput(TinkerFluids.moltenObsidian.getLocalTag(), FluidValues.GLASS_PANE)
				.save(consumer, ConstructsCasting.id(alloyFolder + "molten_exilite"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.exilite).setIngredient(CCItems.exiliteIngot.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "exilite/ingot"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.exilite).setIngredient(CCItems.exiliteNugget.get()).setValue(1).setNeeded(9).save(consumer, ConstructsCasting.id(materialFolder + "exilite/nugget"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MAGEHUNTER.get()), new FluidStack(CCFluids.moltenExilite.get(), 2*FluidValues.INGOT), 700, 30).setDamagable(25).save(consumer, ConstructsCasting.id(meltingFolder + "exilite/magehunter"));

		MaterialRecipeBuilder.materialRecipe(CCMaterials.exilite).setIngredient(CCBlocks.exiliteBlock).setValue(9).setNeeded(1).save(consumer, location(materialFolder + "exiilte/block"));
		ItemCastingRecipeBuilder.basinRecipe(CCBlocks.exiliteBlock).setFluidAndTime(CCFluids.moltenExilite, FluidValues.METAL_BLOCK).save(consumer, location(castingFolder + "exilite/block"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCBlocks.exiliteBlock), CCFluids.moltenExilite, FluidValues.METAL_BLOCK).save(consumer, location(meltingFolder + "exilite/block"));
		packingRecipe(consumer, RecipeCategory.MISC, "block", CCBlocks.exiliteBlock.get(), "ingot", CCItems.exiliteIngot, ItemTags.create(ResourceLocation.parse("forge:ingots/exilite")), materialFolder);


		//arcane salvage making
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_DEBRIS_BLOCK_ITEM.get()), new FluidStack(CCFluids.moltenMithril.get(), 6*FluidValues.NUGGET), 1175,40).save(consumer, ConstructsCasting.id(meltingFolder + "arcane_salvage/ore"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_SALVAGE.get()), new FluidStack(CCFluids.moltenMithril.get(), 3*FluidValues.NUGGET), 1175,20).save(consumer, ConstructsCasting.id(meltingFolder + "arcane_salvage/ingot"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MITHRIL_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenArcaneSalvage.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getMultiUseTag(), false).save(consumer, ConstructsCasting.id(castingFolder + "arcane_salvage_multi"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MITHRIL_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenArcaneSalvage.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getSingleUseTag(), true).save(consumer, ConstructsCasting.id(castingFolder + "arcane_salvage_single"));
		ItemCastingRecipeBuilder.tableRecipe(CCItems.mithrilNugget).setFluidAndTime(new FluidStack(CCFluids.moltenArcaneSalvage.get(), FluidValues.NUGGET)).setCast(TinkerSmeltery.nuggetCast.getMultiUseTag(), false).save(consumer, ConstructsCasting.id(castingFolder + "arcane_salvage_nugget_multi"));
		ItemCastingRecipeBuilder.tableRecipe(CCItems.mithrilNugget).setFluidAndTime(new FluidStack(CCFluids.moltenArcaneSalvage.get(), FluidValues.NUGGET)).setCast(TinkerSmeltery.nuggetCast.getSingleUseTag(), true).save(consumer, ConstructsCasting.id(castingFolder + "arcane_salvage_nugget_single"));
		AlloyRecipeBuilder.alloy(new FluidStack(CCFluids.moltenMithril.get(), FluidValues.NUGGET)).addInput(new FluidStack(CCFluids.moltenArcaneSalvage.get(), FluidValues.NUGGET)).addCatalyst(FluidIngredient.of(CCFluids.moltenMithril.get(), FluidValues.NUGGET)); //legacy arcane salvage conversion to mithril
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MITHRIL_ORE_BLOCK_ITEM.get()), new FluidStack(CCFluids.moltenMithril.get(), 6*FluidValues.NUGGET), 1175, 80).save(consumer, location(meltingFolder + "mithril/ore"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MITHRIL_ORE_DEEPSLATE_BLOCK_ITEM.get()), new FluidStack(CCFluids.moltenMithril.get(), 6*FluidValues.NUGGET), 1175, 80).save(consumer, location(meltingFolder + "mithril/deepslate_ore"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.RAW_MITHRIL.get()), new FluidStack(CCFluids.moltenMithril.get(), 3*FluidValues.NUGGET), 1175, 40).save(consumer, location(meltingFolder + "mithril/raw"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MITHRIL_SCRAP.get()), new FluidStack(CCFluids.moltenMithril.get(), 2*FluidValues.NUGGET), 1175, 40).save(consumer, location(meltingFolder + "mithril/scrap"));
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.MITHRIL_INGOT.get()), new FluidStack(CCFluids.moltenMithril.get(), FluidValues.INGOT), 1175, 60).save(consumer, location(meltingFolder + "mithril/ingot"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MITHRIL_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenMithril.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getMultiUseTag(), false).save(consumer, ConstructsCasting.id(castingFolder + "mithril/ingot_multi_use"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MITHRIL_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenMithril.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getSingleUseTag(), true).save(consumer, ConstructsCasting.id(castingFolder + "mithril/ingot_single_use"));
		materialMeltingCasting(consumer, CCMaterials.mithril, CCFluids.moltenMithril, FluidValues.INGOT, materialFolder);
		MaterialRecipeBuilder.materialRecipe(CCMaterials.mithril).setIngredient(ItemRegistry.MITHRIL_INGOT.get()).setValue(1).setNeeded(1).save(consumer, location(materialFolder + "mithril/ingot"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.mithril).setIngredient(ItemRegistry.MITHRIL_SCRAP.get()).setValue(1).setNeeded(4).save(consumer, location(materialFolder + "mithril/scrap"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.mithril).setIngredient(CCItems.mithrilNugget.get()).setValue(1).setNeeded(9).save(consumer, location(materialFolder + "mithril/nugget"));
		nuggetCasting(consumer, CCFluids.moltenMithril, CCItems.mithrilNugget, castingFolder);
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.mithrilNugget), CCFluids.moltenMithril, FluidValues.NUGGET).save(consumer, location(meltingFolder + "mithril/nugget"));
		packingRecipe(consumer, RecipeCategory.MISC, "ingot", ItemRegistry.MITHRIL_INGOT.get(), "nugget", CCItems.mithrilNugget, ItemTags.create(ResourceLocation.parse("forge:nuggets/mithril")), materialFolder);
		MaterialRecipeBuilder.materialRecipe(CCMaterials.mithril).setIngredient(CCBlocks.mithrilBlock).setValue(9).setNeeded(1).save(consumer, location(materialFolder + "mithril/block"));
		ItemCastingRecipeBuilder.basinRecipe(CCBlocks.mithrilBlock).setFluidAndTime(CCFluids.moltenMithril, FluidValues.METAL_BLOCK).save(consumer, location(castingFolder + "mithril/block"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCBlocks.mithrilBlock), CCFluids.moltenMithril, FluidValues.METAL_BLOCK).save(consumer, location(meltingFolder + "mithril/block"));
		packingRecipe(consumer, RecipeCategory.MISC, "block", CCBlocks.mithrilBlock.get(), "ingot", ItemRegistry.MITHRIL_INGOT.get(), ItemTags.create(ResourceLocation.parse("forge:ingots/mithril")), materialFolder);


		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.PYRIUM_INGOT.get()), new FluidStack(CCFluids.moltenPyrium.get(), FluidValues.INGOT), 1175, 60).save(consumer, location(meltingFolder + "pyrium/ingot"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.PYRIUM_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenPyrium.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getMultiUseTag(), false).save(consumer, ConstructsCasting.id(castingFolder + "pyrium/ingot_multi_use"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.PYRIUM_INGOT.get()).setFluidAndTime(new FluidStack(CCFluids.moltenPyrium.get(), FluidValues.INGOT)).setCast(TinkerSmeltery.ingotCast.getSingleUseTag(), true).save(consumer, ConstructsCasting.id(castingFolder + "pyrium/ingot_single_use"));
		materialMeltingCasting(consumer, CCMaterials.pyrium, CCFluids.moltenPyrium, FluidValues.INGOT, materialFolder);
		MaterialRecipeBuilder.materialRecipe(CCMaterials.pyrium).setIngredient(ItemRegistry.PYRIUM_INGOT.get()).setValue(1).setNeeded(1).save(consumer, location(materialFolder + "pyrium/ingot"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.pyrium).setIngredient(CCItems.pyriumNugget.get()).setValue(1).setNeeded(9).save(consumer, location(materialFolder + "pyrium/nugget"));
		nuggetCasting(consumer, CCFluids.moltenPyrium, CCItems.pyriumNugget.get(), castingFolder + "pyrium_nugget");
		MeltingRecipeBuilder.melting(Ingredient.of(CCItems.pyriumNugget), new FluidStack(CCFluids.moltenPyrium.get(), FluidValues.NUGGET), 1175, 6).save(consumer, location(meltingFolder + "pyrium/nugget"));
		packingRecipe(consumer, RecipeCategory.MISC, "ingot", ItemRegistry.PYRIUM_INGOT.get(), "nugget", CCItems.pyriumNugget, ItemTags.create(ResourceLocation.parse("forge:nuggets/pyrium")), materialFolder);

		MaterialRecipeBuilder.materialRecipe(CCMaterials.pyrium).setIngredient(CCBlocks.pyriumBlock).setValue(9).setNeeded(1).save(consumer, location(materialFolder + "pyrium/block"));
		ItemCastingRecipeBuilder.basinRecipe(CCBlocks.pyriumBlock).setFluidAndTime(CCFluids.moltenPyrium, FluidValues.METAL_BLOCK).save(consumer, location(castingFolder + "pyrium/block"));
		MeltingRecipeBuilder.melting(Ingredient.of(CCBlocks.pyriumBlock), CCFluids.moltenPyrium, FluidValues.METAL_BLOCK).save(consumer, location(meltingFolder + "pyrium/block"));
		packingRecipe(consumer, RecipeCategory.MISC, "block", CCBlocks.pyriumBlock.get(), "ingot", ItemRegistry.PYRIUM_INGOT.get(), ItemTags.create(ResourceLocation.parse("forge:ingots/pyrium")), materialFolder);


		materialMeltingCasting(consumer, MaterialIds.quartz, TinkerFluids.moltenQuartz, FluidValues.GEM, materialFolder); //to permit casting of quartz faceted gem/spellbook


		// arcane cloth making
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.MAGIC_CLOTH.get())
				.setCast(Items.COBWEB.asItem(), true)
				.setCoolingTime(52)
				.setFluid(CCFluids.arcaneEssence.get(), 6*FluidValues.BOTTLE)
				.save(consumer, ConstructsCasting.id(castingFolder + "arcane_cloth"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.arcaneCloth)
				.setIngredient(ItemRegistry.MAGIC_CLOTH.get())
				.setValue(1).setNeeded(1)
				.save(consumer, ConstructsCasting.id(materialFolder + "arcane_cloth"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.hogskin)
				.setIngredient(ItemRegistry.HOGSKIN.get())
				.setValue(1).setNeeded(1)
				.save(consumer, ConstructsCasting.id(materialFolder + "hogskin"));
       //frozen bone
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.FROZEN_BONE_SHARD.get()).setCast(Items.BONE, true).setFluidAndTime(new FluidStack(CCFluids.iceEssence.get(), 4*FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(castingFolder + "frozen_bone"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.frozenBone).setIngredient(ItemRegistry.FROZEN_BONE_SHARD.get()).setValue(1).setNeeded(1).save(consumer, ConstructsCasting.id(materialFolder + "frozen_bone"));
		MaterialFluidRecipeBuilder.material(CCMaterials.frozenBone).setInputId(MaterialIds.bone).setFluidAndTemp(new FluidStack(CCFluids.iceEssence.get(), 4*FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(materialFolder + "frozen_bone_composite"));
		//bloody vellum
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.BLOODY_VELLUM.get()).setCast(ItemRegistry.HOGSKIN.get(), true).setCoolingTime(10).setFluid(FluidIngredient.of(CCFluids.Tags.BLOOD_ESSENCE_INGREDIENTS, 2*FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(castingFolder + "bloody_vellum"));
		MaterialFluidRecipeBuilder.material(CCMaterials.bloodyVellum).setInputId(CCMaterials.hogskin).setTemperature(300).setFluid(CCFluids.Tags.BLOOD_ESSENCE_INGREDIENTS, 2*FluidValues.BOTTLE).save(consumer, ConstructsCasting.id(materialFolder + "bloody_vellum_composite"));
		MaterialRecipeBuilder.materialRecipe(CCMaterials.bloodyVellum)
				.setIngredient(ItemRegistry.BLOODY_VELLUM.get())
				.setValue(1).setNeeded(1)
				.save(consumer, ConstructsCasting.id(materialFolder + "bloody_vellum"));
		//frosted rod
		MaterialRecipeBuilder.materialRecipe(CCMaterials.frostRod).setIngredient(ItemRegistry.FROSTED_HELVE.get()).setValue(3).setNeeded(1).setLeftover(ItemOutput.fromItem(ItemRegistry.FROZEN_BONE_SHARD.get())).save(consumer, ConstructsCasting.id(materialFolder + "frost_rod"));
        MaterialRecipeBuilder.materialRecipe(MaterialIds.dragonScale)
                        .setIngredient(ItemRegistry.DRAGONSKIN.get())
                                .setValue(1).setNeeded(1)
                        .save(consumer, ConstructsCasting.id(materialFolder + "dragon_scale"));
		materialRecipe(consumer, CCMaterials.permafrost, Ingredient.of(ItemRegistry.ICE_CRYSTAL.get()), 1, 1, materialFolder + "permafrost");
		materialRecipe(consumer, CCMaterials.emerald, Ingredient.of(Items.EMERALD), 1, 1, materialFolder + "emerald");
		materialRecipe(consumer, CCMaterials.echoShard, Ingredient.of(Items.ECHO_SHARD), 1, 1, materialFolder + "echo_shard");
		materialRecipe(consumer, CCMaterials.divinePearl, Ingredient.of(ItemRegistry.DIVINE_PEARL.get()), 1, 1, materialFolder + "divine_pearl");
		materialMeltingCasting(consumer, MaterialIds.amethyst, TinkerFluids.moltenAmethyst, FluidValues.GEM, materialFolder);
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
				.saveSalvage(consumer, location(salvageFolder + "casting"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/casting"));
		//swiftcasting
		ModifierRecipeBuilder.modifier(CCModifiers.SWIFTCASTING)
				.allowCrystal()
				.exactLevel(1)
				.setSlots(SlotType.UPGRADE, 1)
				.setTools(TinkerTags.Items.BOOTS)
				.addInput(ItemRegistry.MITHRIL_WEAVE.get())
				.addInput(ItemRegistry.DIVINE_SOULSHARD.get())
				.addInput(Items.RABBIT_FOOT)
				.saveSalvage(consumer, location(salvageFolder + "swiftcasting"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/swiftcasting_1"));
		ModifierRecipeBuilder.modifier(CCModifiers.SWIFTCASTING)
				.disallowCrystal()
				.setMinLevel(2)
				.setMaxLevel(3)
				.setSlots(SlotType.UPGRADE, 1)
				.setTools(TinkerTags.Items.BOOTS)
				.addInput(Items.FEATHER)
				.addInput(Items.RABBIT_FOOT)
				.addInput(Items.FEATHER)
				.saveSalvage(consumer, location(salvageFolder + "swiftcasting_extra"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/swiftcasting"));
		//spell prot
		ItemCastingRecipeBuilder.tableRecipe(CCItems.exiliteReinforcement.get())
				.setCast(TinkerTables.pattern.get(), true)
				.setFluid(CCFluids.moltenExilite.get(), FluidValues.INGOT)
				.setCoolingTime(10)
				.save(consumer, ConstructsCasting.id(castingFolder + "exilite_reinforcement"));
		IncrementalModifierRecipeBuilder.modifier(CCModifiers.SPELL_PROTECTION)
				.setInput(CCItems.exiliteReinforcement.get(), 1, 5)
				.setSlots(SlotType.DEFENSE, 1)
				.setTools(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.ARMOR), Ingredient.of(TinkerTags.Items.HELD)))
				.saveSalvage(consumer, location(salvageFolder + "spell_protection"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "defense/spell_protection"));
		//imbued
		ModifierRecipeBuilder.modifier(CCModifiers.IMBUED)
				.allowCrystal()
				.exactLevel(1)
				.setSlots(SlotType.ABILITY, 1)
				.setTools(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.HELD), Ingredient.of(TinkerTags.Items.ARMOR)))
				.setMaxLevel(1)
				.addInput(ItemRegistry.MITHRIL_INGOT.get())
				.addInput(TinkerTags.Items.SWORD)
				.addInput(ItemRegistry.MITHRIL_INGOT.get())
				.addInput(ItemRegistry.MANA_RUNE.get())
				.addInput(ItemRegistry.MANA_RUNE.get())
				.saveSalvage(consumer, location(salvageFolder + "imbued"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/imbued"));
        //spellblade
        ModifierRecipeBuilder.modifier(CCModifiers.SPELLBLADE)
                .allowCrystal()
                .exactLevel(1)
                .setSlots(SlotType.UPGRADE, 1) //i can't imagine spellblade being that useful, plus imbued is already an ability
                .setTools(Ingredient.of(TinkerTags.Items.HELD))
                .setMaxLevel(1)
                .addInput(TinkerMaterials.steel.getIngotTag())
                .addInput(ItemRegistry.MITHRIL_INGOT.get())
                .addInput(TinkerMaterials.steel.getIngotTag())
				.saveSalvage(consumer, location(salvageFolder + "spellblade"))
                .save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/spellblade"));
		//encyclopedic
		ModifierRecipeBuilder.modifier(CCModifiers.ENCYCLOPEDIC)
				.allowCrystal()
				.exactLevel(1)
				.setTools(CCItems.Tags.MOD_SPELLBOOKS)
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
		//spellbook strap
        //having two spellbooks at once is powerful, should be an ability
		ModifierRecipeBuilder.modifier(CCModifiers.SPELLBOOK_STRAP)
				.addInput(TinkerWorld.enderSlimeVine)
				.addInput(ItemRegistry.MITHRIL_INGOT.get())
				.addInput(TinkerWorld.enderSlimeVine)
				.setSlots(SlotType.ABILITY, 1)
				.setMaxLevel(1)
				.setTools(TinkerTags.Items.LEGGINGS)
				.saveSalvage(consumer, location(salvageFolder + "spellbook_strap"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/spellbook_strap"));
        //gilded but for affinity slots
		ModifierRecipeBuilder.modifier(CCModifiers.IMPROVEABLE)
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.addInput(Items.APPLE)
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.addInput(ItemRegistry.ARCANE_INGOT.get())
				.setTools(TinkerTags.Items.BONUS_SLOTS)
				.setSlots(SlotType.ABILITY, 1)
				.saveSalvage(consumer, location(salvageFolder + "improvable"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "ability/improveable"));
		IJsonPredicate<ModifierId> extractBlacklist = ModifierPredicate.tag(TinkerTags.Modifiers.EXTRACT_MODIFIER_BLACKLIST).inverted();
		for (boolean dagger : new boolean[]{false, true}) {
			String suffix = dagger ? "_dagger" : "";
			SizedIngredient tools = dagger ? SizedIngredient.fromItems(2, TinkerTools.dagger) : SizedIngredient.of(DifferenceIngredient.of(Ingredient.of(TinkerTags.Items.MODIFIABLE), Ingredient.of(TinkerTags.Items.UNSALVAGABLE)));
			ModifierRemovalRecipeBuilder.removal()
					.setTools(tools)
					.slotName(CCModifiers.AFFINITY_SLOT)
					.addInput(ItemRegistry.SHRIVING_STONE.get())
					.addInput(Items.WET_SPONGE)
					.addLeftover(Items.SPONGE)
					.modifierPredicate(ModifierPredicate.and(extractBlacklist, new SlotTypeModifierPredicate(CCModifiers.AFFINITY_SLOT)))
					.save(consumer, location(modifierFolder + "extract/affinity" + suffix));
		}
        ModifierRecipeBuilder.modifier(CCModifiers.RINGBEARER)
                .addInput(Ingredient.of(getItemTag("curios", "ring")))
                .addInput(getItemTag("forge", "ingots/steel"))
                .addInput(Ingredient.of(getItemTag("curios", "ring")))
                .setSlots(SlotType.ABILITY, 1)
                .setTools(TinkerTags.Items.CHESTPLATES)
                .setMaxLevel(1)
				.saveSalvage(consumer, location(salvageFolder + "ringbearer"))
                .save(consumer, ConstructsCasting.id(modifierFolder + "ability/ringbearer"));
		ModifierRecipeBuilder.modifier(CCModifiers.REINSCRIBED)
				.addInput(ItemRegistry.INK_COMMON.get())
				.addInput(ItemRegistry.INK_UNCOMMON.get())
				.addInput(ItemRegistry.INK_RARE.get())
				.addInput(ItemRegistry.INK_EPIC.get())
				.addInput(ItemRegistry.INK_LEGENDARY.get())
				.setTools(TinkerTags.Items.BONUS_SLOTS)
				.setMaxLevel(1)
				.save(consumer, ConstructsCasting.id(modifierFolder + "slotless/reinscribed"));
		//elemental power upgrades
		incrementalModifierRecipe(consumer, CCModifiers.MANA_UPGRADE,      Ingredient.of(ItemRegistry.MANA_RUNE.get()),      Ingredient.of(ItemRegistry.MANA_UPGRADE_ORB.get()),      "mana_upgrade", false);
		incrementalModifierRecipe(consumer, CCModifiers.COOLDOWN_UPGRADE,  Ingredient.of(ItemRegistry.COOLDOWN_RUNE.get()),  Ingredient.of(ItemRegistry.COOLDOWN_UPGRADE_ORB.get()),  "cooldown_upgrade", false);
		incrementalModifierRecipe(consumer, CCModifiers.FIRE_UPGRADE,      Ingredient.of(ItemRegistry.FIRE_RUNE.get()),      Ingredient.of(ItemRegistry.FIRE_UPGRADE_ORB.get()),      "fire_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.ICE_UPGRADE,       Ingredient.of(ItemRegistry.ICE_RUNE.get()),       Ingredient.of(ItemRegistry.ICE_UPGRADE_ORB.get()),       "ice_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.LIGHTNING_UPGRADE, Ingredient.of(ItemRegistry.LIGHTNING_RUNE.get()), Ingredient.of(ItemRegistry.LIGHTNING_UPGRADE_ORB.get()), "lightning_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.ENDER_UPGRADE,     Ingredient.of(ItemRegistry.ENDER_RUNE.get()),     Ingredient.of(ItemRegistry.ENDER_UPGRADE_ORB.get()),     "ender_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.HOLY_UPGRADE,      Ingredient.of(ItemRegistry.HOLY_RUNE.get()),      Ingredient.of(ItemRegistry.HOLY_UPGRADE_ORB.get()),      "holy_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.BLOOD_UPGRADE,     Ingredient.of(ItemRegistry.BLOOD_RUNE.get()),     Ingredient.of(ItemRegistry.BLOOD_UPGRADE_ORB.get()),     "blood_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.EVOCATION_UPGRADE, Ingredient.of(ItemRegistry.EVOCATION_RUNE.get()), Ingredient.of(ItemRegistry.EVOCATION_UPGRADE_ORB.get()), "evocation_upgrade", true);
		incrementalModifierRecipe(consumer, CCModifiers.NATURE_UPGRADE,    Ingredient.of(ItemRegistry.NATURE_RUNE.get()),    Ingredient.of(ItemRegistry.NATURE_UPGRADE_ORB.get()),    "nature_upgrade", true);

		incrementalModifierRecipe(withCondition(consumer, new ModLoadedCondition("cataclysm_spellbook")), CCModifiers.ABYSSAL_UPGRADE, ItemNameIngredient.from(ResourceLocation.parse("cataclysm_spellbooks:abyssal_rune")), ItemNameIngredient.from(ResourceLocation.parse("cataclysm_spellbooks:abyssal_upgrade_orb")), "abyssal_upgrade", true);
		incrementalModifierRecipe(withCondition(consumer, new ModLoadedCondition("cataclysm_spellbook")), CCModifiers.TECHNOMANCY_UPGRADE, ItemNameIngredient.from(ResourceLocation.parse("cataclysm_spellbooks:technomancy_rune")), ItemNameIngredient.from(ResourceLocation.parse("cataclysm_spellbooks:technomancy_upgrade_orb")), "technomancy_upgrade", true);
		incrementalModifierRecipe(withCondition(consumer, new ModLoadedCondition("traveloptics")), CCModifiers.AQUA_UPGRADE, ItemNameIngredient.from(ResourceLocation.parse("traveloptics:aqua_rune")), ItemNameIngredient.from(ResourceLocation.parse("traveloptics:aqua_upgrade_orb")), "aqua_upgrade", true);
		incrementalModifierRecipe(withCondition(consumer, new ModLoadedCondition("alshanex_familiars")), CCModifiers.SOUND_UPGRADE, ItemNameIngredient.from(ResourceLocation.parse("alshanex_familiars:sound_rune")), ItemNameIngredient.from(ResourceLocation.parse("alshanex_familiars:sound_upgrade_orb")), "sound_upgrade", true);
		//essence making
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.ARCANE_ESSENCE.get()), new FluidStack(CCFluids.arcaneEssence.get(), 250), 100, 5)
				.save(consumer, ConstructsCasting.id(meltingFolder + "arcane_essence"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.ARCANE_ESSENCE.get()).setFluidAndTime(new FluidStack(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(castingFolder + "arcane_essence_casting"));

		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.CINDER_ESSENCE.get()), new FluidStack(CCFluids.cinderEssence.get(), 250), 1175, 5)
				.save(consumer, ConstructsCasting.id(meltingFolder + "cinder_essence"));
		ItemCastingRecipeBuilder.tableRecipe(ItemRegistry.CINDER_ESSENCE.get()).setFluidAndTime(new FluidStack(CCFluids.cinderEssence.get(), FluidValues.BOTTLE)).save(consumer, ConstructsCasting.id(castingFolder + "cinder_essence_casting"));
		essenceRecipe(consumer, CCFluids.fireEssence,      TinkerFluids.blazingBlood   .getLocalTag(),100, "fire_essence"     );
		essenceRecipe(consumer, CCFluids.iceEssence,       TinkerFluids.powderedSnow.getCommonTag(),   250, "ice_essence"     );
		essenceRecipe(consumer, CCFluids.lightningEssence, CCFluids.Tags.LIQUID_LIGHTNING,            250, "lightning_essence");
		essenceRecipe(consumer, CCFluids.enderEssence,     TinkerFluids.moltenEnder    .getLocalTag(),250, "ender_essence"    );

		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(CCFluids.holyEssence.get(), FluidValues.BOTTLE), 700)
				.addInput(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE)
				.addInput(TinkerFluids.moltenGold.getLocalTag(), FluidValues.INGOT)
				.addInput(TinkerFluids.moltenAmethyst.getLocalTag(), FluidValues.GEM)
				.save(consumer, ConstructsCasting.id(alloyFolder + "holy_essence"));

		essenceRecipe(consumer, CCFluids.bloodEssence,     CCFluids.Tags.BLOOD_ESSENCE_INGREDIENTS,250, "blood_essence"    );
		essenceRecipe(consumer, CCFluids.evocationEssence, TinkerFluids.moltenEmerald  .getLocalTag(),100, "evocation_essence");
		essenceRecipe(consumer, CCFluids.natureEssence,    CCFluids.Tags.POISONOUS_POTATO_STEW,       50, "nature_essence"   );
		//rune casting
		runeCastingRecipe(consumer, CCFluids.fireEssence,      ItemRegistry.FIRE_RUNE.get(),           "fire_rune");
		runeCastingRecipe(consumer, CCFluids.iceEssence,       ItemRegistry.ICE_RUNE.get(),             "ice_rune");
		runeCastingRecipe(consumer, CCFluids.lightningEssence, ItemRegistry.LIGHTNING_RUNE.get(), "lightning_rune");
		runeCastingRecipe(consumer, CCFluids.enderEssence,     ItemRegistry.ENDER_RUNE.get(),         "ender_rune");
		runeCastingRecipe(consumer, CCFluids.holyEssence,      ItemRegistry.HOLY_RUNE.get(),           "holy_rune");
		runeCastingRecipe(consumer, CCFluids.bloodEssence,     ItemRegistry.BLOOD_RUNE.get(),         "blood_rune");
		runeCastingRecipe(consumer, CCFluids.evocationEssence, ItemRegistry.EVOCATION_RUNE.get(), "evocation_rune");
		runeCastingRecipe(consumer, CCFluids.natureEssence,    ItemRegistry.NATURE_RUNE.get(),       "nature_rune");

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
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(CCFluids.poisonousPotatoStew.get(), 50), 100)
				.addInput(CCFluids.potatoStew.get(), 50)
				.addCatalyst(FluidIngredient.of(CCFluids.poisonousPotatoStew.get(), 10))
				.save(consumer, ConstructsCasting.id(alloyFolder + "poisonous_potato_stew_alloying"));
		//divinity
		MeltingRecipeBuilder.melting(Ingredient.of(ItemRegistry.DIVINE_PEARL.get()), FluidOutput.fromTag(TinkerFluids.moltenGold.getLocalTag(), FluidValues.INGOT), 700, 12).addByproduct(FluidOutput.fromTag(TinkerFluids.moltenAmethyst.getLocalTag(), FluidValues.GEM)).save(consumer);
		//ink
		inkFillingRecipe(consumer, ItemRegistry.INK_COMMON.get(), CCFluids.Tags.ink("common"), "common");
		inkFillingRecipe(consumer, ItemRegistry.INK_UNCOMMON.get(), CCFluids.Tags.ink("uncommon"), "uncommon");
		inkFillingRecipe(consumer, ItemRegistry.INK_RARE.get(), CCFluids.Tags.ink("rare"), "rare");
		inkFillingRecipe(consumer, ItemRegistry.INK_EPIC.get(), CCFluids.Tags.ink("epic"), "epic");
		inkFillingRecipe(consumer, ItemRegistry.INK_LEGENDARY.get(), CCFluids.Tags.ink("legendary"), "legendary");

		MeltingRecipeBuilder.melting(Ingredient.of(Items.INK_SAC), new FluidStack(CCFluids.squidInk.get(), FluidValues.BOTTLE), 300, 8).save(consumer, ConstructsCasting.id("smeltery/melting/ink"));
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(FluidRegistry.COMMON_INK.get(),    250), 300).addInput(CCFluids.squidInk.get(),               250).addInput(CCFluids.arcaneEssence.get(), 500).save(consumer, ConstructsCasting.id(alloyFolder + "common_ink"));
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(FluidRegistry.UNCOMMON_INK.get(),  250), 300).addInput(CCFluids.Tags.ink("common"),   750).addInput(TinkerFluids.moltenCopper  .getCommonTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "uncommon_ink"));
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(FluidRegistry.RARE_INK.get(),      250), 300).addInput(CCFluids.Tags.ink("uncommon"), 750).addInput(TinkerFluids.moltenIron    .getCommonTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "rare_ink"));
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(FluidRegistry.EPIC_INK.get(),      250), 300).addInput(CCFluids.Tags.ink("rare"), 	 750).addInput(TinkerFluids.moltenGold    .getCommonTag(), FluidValues.INGOT).save(consumer, ConstructsCasting.id(alloyFolder + "epic_ink"));
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(FluidRegistry.LEGENDARY_INK.get(), 250), 300).addInput(CCFluids.Tags.ink("epic"),     750).addInput(TinkerFluids.moltenAmethyst.getLocalTag(), FluidValues.GEM)  .save(consumer, ConstructsCasting.id(alloyFolder + "legendary_ink"));

		//slimy spellbook!
		ItemCastingRecipeBuilder.basinRecipe(CCItems.slimySpellbook.get()).setCast(ItemRegistry.DIAMOND_SPELL_BOOK.get(),  true).setFluidAndTime(new FluidStack(TinkerFluids.enderSlime.get(),                  1000)).save(consumer, ConstructsCasting.id(castingFolder + "tinkerers_spellbook"));
		//plated spellbook
		toolBuilding(consumer, CCItems.platedSpellbook.get(), "tools/building/", ConstructsCasting.id("spellbooks"));
		toolBuilding(consumer, CCItems.travellersSpellbook.get(), "tools/building/", ConstructsCasting.id("spellbooks"));
        toolBuilding(consumer, CCItems.wand.get(), "tools/building/", location("wand"));
        toolBuilding(consumer, CCItems.battlestaff.get(), "tools/building/", location("battlestaff"));
		//eldritch staff
//		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CCItems.eldritchStaff.get())
//				.define('s', Items.ECHO_SHARD)
//				.define('l', ItemTags.WARPED_STEMS)
//				.define('a', ItemRegistry.ARCANE_INGOT.get())
//				.pattern("sls")
//				.pattern(" a ")
//				.pattern(" l ")
//				.unlockedBy("has_item", RecipeProvider.has(Items.ECHO_SHARD))
//				.save(consumer, ConstructsCasting.id("crafting/eldritch_staff"));
		//slime stuff
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CCItems.wizardslimeBall.get())
				.define('e', Items.SLIME_BALL)
				.define('n', TinkerCommons.slimeball.get(SlimeType.ENDER))
				.define('a', ItemRegistry.ARCANE_ESSENCE.get())
				.define('s', TinkerCommons.slimeball.get(SlimeType.SKY))
				.define('i', TinkerCommons.slimeball.get(SlimeType.ICHOR))
				.pattern(" e ")
				.pattern("nas")
				.pattern(" i ")
				.unlockedBy("has_item", RecipeProvider.has(TinkerCommons.slimeball.get(SlimeType.ENDER)))
				.save(consumer, ConstructsCasting.id("crafting/wizardslime_ball"));

		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.APOTHECARIST.get()), new FluidStack(CCFluids.natureEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/apothecarist"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.PYROMANCER.get()), new FluidStack(CCFluids.fireEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/pyromancer"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.CRYOMANCER.get()), new FluidStack(CCFluids.iceEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/cryomancer"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.PRIEST.get()), new FluidStack(CCFluids.holyEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/priest"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.ARCHEVOKER.get()), new FluidStack(CCFluids.evocationEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/archevoker"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.NECROMANCER.get()), new FluidStack(CCFluids.arcaneEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/necromancer"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.KEEPER.get()), new FluidStack(CCFluids.cinderEssence.get(), 25)).save(consumer, location("smeltery/melting/entity/keeper"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.CULTIST.get()), new FluidStack(CCFluids.bloodEssence.get(), 50)).save(consumer, location("smeltery/melting/entity/cultist"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.DEAD_KING.get()), new FluidStack(FluidRegistry.RARE_INK.get(), 50)).save(consumer, location("smeltery/melting/entity/dead_king"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityType.SQUID), new FluidStack(CCFluids.squidInk.get(), 50)).save(consumer, location("smeltery/melting/entity/squid"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.FIRE_BOSS.get()), new FluidStack(CCFluids.moltenPyrium.get(), 10)).save(consumer, location("smeltery/melting/entity/tyros"));
		EntityMeltingRecipeBuilder.melting(EntityIngredient.of(EntityRegistry.ICE_SPIDER.get()), new FluidStack(FluidRegistry.ICE_VENOM_FLUID.get(), 50)).save(consumer, location("smeltery/melting/entity/ice_spider"));

		Ingredient rebalancedCommon = Ingredient.of(ItemRegistry.ARCANE_ESSENCE.get());

		SwappableModifierRecipeBuilder.modifier(ModifierIds.rebalanced, CCModifiers.AFFINITY_SLOT.getName())
				.setTools(TinkerTags.Items.BONUS_SLOTS)
				.addInput(rebalancedCommon)
				.addInput(Items.END_CRYSTAL)
				.addInput(rebalancedCommon)
				.addInput(Items.AMETHYST_BLOCK)
				.addInput(Items.AMETHYST_BLOCK)
				.disallowCrystal()
				.save(consumer, ConstructsCasting.id(modifierFolder + "slotless/rebalanced_affinity"));

        ModifierRecipeBuilder.modifier(CCModifiers.EXPEDIENT)
                .addInput(Items.AMETHYST_SHARD)
                .addInput(Items.COPPER_INGOT)
                .addInput(Items.COPPER_INGOT)
                .addInput(Items.COPPER_INGOT)
                .addInput(Items.COPPER_INGOT)
				.setTools(TinkerTags.Items.HELD)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(3)
				.saveSalvage(consumer, location(salvageFolder + "expedient"))
                .save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/expedient"));

        partRecipes(consumer, CCItems.spellbookPlating, CCItems.spellbookPlatingCast, 2, partsFolder, castingFolder);
        partRecipes(consumer, CCItems.facetedGem, CCItems.facetedGemCast, 1, partsFolder, castingFolder);
//		partCasting(consumer, CCItems.facetedGem.get(), CCItems.facetedGemCast, 1, castingFolder);
//		PartRecipeBuilder.partRecipe(CCItems.facetedGem.get())
//				.setPattern(id(CCItems.facetedGem.get()))
//				.setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(CCItems.facetedGemCast.get())))
//				.setCost(2)
//				.setAllowUncraftable(false) //means you can make arcanium and mithril gems in the part builder, i don't really care
//				.save(consumer, location(partsFolder + "builder/" + id(CCItems.facetedGem.get()).getPath()));
//		partCasting(consumer, CCItems.spellbookPlating.get(), CCItems.spellbookPlatingCast, 2, castingFolder);
//		PartRecipeBuilder.partRecipe(CCItems.spellbookPlating.get())
//				.setPattern(id(CCItems.spellbookPlating.get()))
//				.setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(CCItems.spellbookPlatingCast.get())))
//				.setCost(2)
//				.setAllowUncraftable(false)
//				.save(consumer, location(partsFolder + "builder/" + id(CCItems.spellbookPlating.get()).getPath()));

        uncastablePart(consumer, CCItems.spellbookCover.get(), 2, null, partsFolder);
        uncastablePart(consumer, CCItems.wandRod.get(), 2, null, partsFolder);
        uncastablePart(consumer, CCItems.pages.get(), 3, null, partsFolder);
        //To allow the sculk staff (and future staffs) to switch its casting to apply on melee,
//        ModifierSetWorktableRecipeBuilder.setAdding(InteractionSource.LEFT_CLICK.getKey())
//                .modifierPredicate(whitelist)
//                .setTools(TinkerTags.Items.INTERACTABLE_DUAL)
//                .addInput(ItemRegistry.ARCANE_ESSENCE.get())
//                .allowTraits()
//                .save(consumer, location("tools/modifiers/worktable/" + "cast_on_melee"));
//        ModifierSetWorktableRecipeBuilder.setRemoving(InteractionSource.LEFT_CLICK.getKey())
//                .modifierPredicate(whitelist)
//                .setTools(TinkerTags.Items.INTERACTABLE_DUAL)
//                .addInput(ItemRegistry.ARCANE_ESSENCE.get())
//                .addInput(ItemRegistry.ARCANE_ESSENCE.get())
//                .allowTraits()
//                .save(consumer, location("tools/modifiers/worktable/" + "cast_on_interact"));
        ModifierRecipeBuilder.modifier(CCModifiers.SLOT_IMPROVEMENT)
                .setTools(CCItems.Tags.MOD_SPELLBOOKS)
                .addInput(ItemRegistry.LESSER_SPELL_SLOT_UPGRADE.get())
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(6)
                .saveSalvage(consumer, location(modifierFolder + "salvage/slot_improvement"))
                .save(consumer, location(modifierFolder + "slot_improvement"));
		PartBuilderToolRecycleBuilder.tool(CCItems.flamberge)
				.part(TinkerToolParts.broadBlade)
				.part(TinkerToolParts.repairKit)
				.part(TinkerToolParts.toughHandle)
				.save(consumer, location(recyclingFolder + "flamberge"));
        }
	public static void runeCastingRecipe(Consumer<FinishedRecipe> consumer, FluidObject<UnplaceableFluid> essence, Item result, String recipeId) {
		 ItemCastingRecipeBuilder.tableRecipe(result).setCast(ItemRegistry.BLANK_RUNE.get(), true).setFluidAndTime(new FluidStack(essence.get(), 1000)).save(consumer, ConstructsCasting.id(castingFolder + recipeId));
	}
	public static void essenceRecipe(Consumer<FinishedRecipe> consumer, FluidObject<?> essence, TagKey<Fluid> alloyIngredient, int amount, String recipeId) {
		AlloyRecipeBuilder.alloy(FluidOutput.fromFluid(essence.get(), FluidValues.BOTTLE), 700).addInput(CCFluids.arcaneEssence.get(), FluidValues.BOTTLE).addInput(alloyIngredient, amount).save(consumer, ConstructsCasting.id(alloyFolder + recipeId));
	}
	public static void inkFillingRecipe(Consumer<FinishedRecipe> consumer, Item inkBottle, TagKey<Fluid> ink, String rarity) {
		ItemCastingRecipeBuilder.tableRecipe(inkBottle).setFluid(ink,FluidValues.BOTTLE).setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1).save(consumer, ConstructsCasting.id(castingFolder + "ink_" + rarity));
	}
	public static void incrementalModifierRecipe(Consumer<FinishedRecipe> consumer, ModifierId modifier, Ingredient runeItem, Ingredient orbItem, String id, boolean isSpellPower) {
		Ingredient multiuse = DifferenceIngredient.of(Ingredient.of(TinkerTags.Items.MODIFIABLE), Ingredient.of(TinkerTags.Items.SINGLE_USE));
		Ingredient none = DifferenceIngredient.of(Ingredient.of(TinkerTags.Items.MODIFIABLE), Ingredient.of(TinkerTags.Items.MODIFIABLE));
		IncrementalModifierRecipeBuilder.modifier(modifier)
				.setInput(runeItem, 1, 16)
				.setSlots(SlotType.UPGRADE, 1)
				.allowCrystal()
				.setTools(multiuse)
				.setMaxLevel(3)
				.checkTraitLevel()
				.useSalvageMax()
				.saveSalvage(consumer, ConstructsCasting.id(modifierFolder + "salvage/" + id + "_rune"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/" + id + "_rune"));
		ModifierRecipeBuilder.modifier(modifier)
				.addInput(orbItem)
				.setSlots(SlotType.UPGRADE, 1)
				.setTools(multiuse)
				.allowCrystal()
				.setMaxLevel(3)
				.checkTraitLevel()
				.useSalvageMax()
				.saveSalvage(consumer, ConstructsCasting.id(modifierFolder + "salvage/" + id + "_orb"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "upgrade/" + id + "_orb"));
		ModifierRecipeBuilder.modifier(modifier)
				.addInput(runeItem)
				.addInput(orbItem)
				.addInput(runeItem)
				.setTools(multiuse)
				.checkTraitLevel()
				.setSlots(CCModifiers.AFFINITY_SLOT, 1)
				.setLevelRange(4, 5)
				.useSalvageMax()
				.saveSalvage(consumer, ConstructsCasting.id(modifierFolder + "salvage/" + id + "_affinity"))
				.save(consumer, ConstructsCasting.id(modifierFolder + "affinity/" + id + "_orb"));

        if (isSpellPower) {
            IncrementalModifierRecipeBuilder.modifier(CCModifiers.DUMMY_SPELL_POWER_UPGRADE)
                    .setInput(runeItem, 1, 16)
                    .setSlots(SlotType.UPGRADE, 1)
                    .disallowCrystal()
                    .setTools(none)
                    .checkTraitLevel()
                    .save(consumer, ConstructsCasting.id(modifierFolder + "dummy_recipe/" + id + "_rune_dummy"));
            ModifierRecipeBuilder.modifier(CCModifiers.DUMMY_SPELL_POWER_UPGRADE)
                    .addInput(orbItem)
                    .setSlots(SlotType.UPGRADE, 1)
                    .setTools(none)
                    .disallowCrystal()
                    .checkTraitLevel()
                    .save(consumer, ConstructsCasting.id(modifierFolder + "dummy_recipe/" + id + "_orb_dummy"));
            ModifierRecipeBuilder.modifier(CCModifiers.DUMMY_SPELL_POWER_UPGRADE)
                    .addInput(runeItem)
                    .addInput(orbItem)
                    .addInput(runeItem)
                    .setTools(none)
                    .checkTraitLevel()
                    .setSlots(CCModifiers.AFFINITY_SLOT, 1)
                    .disallowCrystal()
                    .save(consumer, ConstructsCasting.id(modifierFolder + "dummy_recipe/" + id + "_affinity_orb_dummy"));
        }
	}
}
