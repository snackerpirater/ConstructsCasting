package com.snackpirate.constructscasting.fluids;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.datagen.MantleTags;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.tooltip.AbstractFluidTooltipProvider;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.fluids.block.BurningLiquidBlock;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.concurrent.CompletableFuture;

public class CCFluids {
	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(ConstructsCasting.MOD_ID);

	public static final FluidObject<UnplaceableFluid> arcaneEssence = essence("arcane_essence");
	public static final FluidObject<UnplaceableFluid> fireEssence = essence("fire_essence");
	public static final FluidObject<UnplaceableFluid> iceEssence = essence("ice_essence");
	public static final FluidObject<UnplaceableFluid> lightningEssence = essence("lightning_essence");
	public static final FluidObject<UnplaceableFluid> enderEssence = essence("ender_essence");
	public static final FluidObject<UnplaceableFluid> holyEssence = essence("holy_essence");
	public static final FluidObject<UnplaceableFluid> bloodEssence = essence("blood_essence");
	public static final FluidObject<UnplaceableFluid> evocationEssence = essence("evocation_essence");
	public static final FluidObject<UnplaceableFluid> natureEssence = essence("nature_essence");


	public static final FluidObject<UnplaceableFluid> liquidLightning = FLUIDS.register("liquid_lightning").bucket().type(FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).unplacable();

	public static final FluidObject<UnplaceableFluid> cinderEssence = essence("cinder_essence");

	public static FlowingFluidObject<ForgeFlowingFluid> potatoStew = FLUIDS.register("potato_stew").type(cool().temperature(400)).bucket().block(MapColor.WATER, 0).flowing();
	public static FlowingFluidObject<ForgeFlowingFluid> poisonousPotatoStew = FLUIDS.register("poisonous_potato_stew").type(cool().temperature(400)).bucket().block(MapColor.WATER, 0).flowing();

	public static FlowingFluidObject<ForgeFlowingFluid> moltenArcanium = FLUIDS.register("molten_arcanium").type(hot()).bucket().block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 12, 10, 2f)).flowing();
	public static FlowingFluidObject<ForgeFlowingFluid> moltenExilite = FLUIDS.register("molten_exilite").type(hot()).bucket().block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 12, 10, 3f)).flowing();
	public static FlowingFluidObject<ForgeFlowingFluid> moltenMithril = FLUIDS.register("molten_mithril").type(hot().temperature(1475)).bucket().block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 15, 10, 3f)).flowing();
	public static FlowingFluidObject<ForgeFlowingFluid> moltenPyrium = FLUIDS.register("molten_pyrium").type(hot().temperature(1475)).bucket().block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 12, 10, 5f)).flowing();


	public static final FluidObject<UnplaceableFluid> squidInk =     FLUIDS.register("squid_ink")    .type(cool().temperature(100)).commonTag("ink")   .bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> commonInk =    FLUIDS.register("common_ink")   .type(cool().temperature(100)).commonTag("ink/common")   .bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> uncommonInk =  FLUIDS.register("uncommon_ink") .type(cool().temperature(100)).commonTag("ink/uncommon") .bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> rareInk =      FLUIDS.register("rare_ink")     .type(cool().temperature(100)).commonTag("ink/rare")     .bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> epicInk =      FLUIDS.register("epic_ink")     .type(cool().temperature(100)).commonTag("ink/epic")     .bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> legendaryInk = FLUIDS.register("legendary_ink").type(cool().temperature(100)).commonTag("ink/legendary").bucket().unplacable();

	public static final FlowingFluidObject<ForgeFlowingFluid> moltenArcaneSalvage = FLUIDS.register("molten_arcane_salvage").type(hot()).bucket().block(MapColor.TERRACOTTA_WHITE, 12).flowing();
	//------compat------
	//arcane essence + crystallized coral?
	//ugh why does it have to be multiple colors
	public static final FluidObject<UnplaceableFluid> moltenCrystallizedCoral = essence("molten_crystallized_coral");
	public static final FluidObject<UnplaceableFluid> abyssalEssence = essence("abyssal_essence");
	//arcane essence + redstone?
	//redstone is NOT a fluid i won't allow it
	public static final FluidObject<UnplaceableFluid> gasifiedRedstone = FLUIDS.register("gasified_redstone").type(hot().density(-1600)).bucket().unplacable();
	public static final FluidObject<UnplaceableFluid> technomancyEssence = essence("technomancy_essence");
	//arcane essence + pearls?
	//molten pearl i guess
	public static final FluidObject<UnplaceableFluid> moltenPearl = essence("molten_pearl");
	public static final FluidObject<UnplaceableFluid> aquaEssence = essence("aqua_essence");


	public static FluidObject<UnplaceableFluid> getInkFluidForRarity(SpellRarity rarity) {
		return switch (rarity) {
			case UNCOMMON -> uncommonInk;
			case RARE -> rareInk;
			case EPIC -> epicInk;
			case LEGENDARY -> legendaryInk;
			default -> commonInk;
		};
	}
	public static FluidObject<UnplaceableFluid> essence(String name) {
		return FLUIDS.register(name)
				.bucket()
				.type(FluidType.Properties.create()
						.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
						.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY))
				.unplacable();
	}

	private static FluidType.Properties cool() {
		return FluidType.Properties.create()
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
	}

	private static FluidType.Properties hot() {
		return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
	}


	@SubscribeEvent
	void registerSerializers(RegisterEvent event) {
		if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
//			ConstructsCasting.LOGGER.info("recipe serializer event");
			FluidEffect.ENTITY_EFFECTS.register(ConstructsCasting.id("deplete_mana"), CCFluidEffects.DEPLETE_MANA.getLoader());
			FluidEffect.ENTITY_EFFECTS.register(ConstructsCasting.id("add_mana"), CCFluidEffects.ADD_MANA.getLoader());
		}
	}

//datagen all below here
	public static class CCFluidTextures extends AbstractFluidTextureProvider {

		public CCFluidTextures(PackOutput generator, @Nullable String modId) {
			super(generator, modId);
		}

		@Override
		public void addTextures() {
			ResourceLocation potion = ResourceLocation.parse("tconstruct:fluid/potion/");
			texture(arcaneEssence)   .textures(potion, false, false).color(0xff79c0f3);
			texture(fireEssence)     .textures(potion, false, false).color(0xfffc9269);
			texture(iceEssence)      .textures(potion, false, false).color(0xff75f1ec);
			texture(lightningEssence).textures(potion, false, false).color(0xff8273e0);
			texture(enderEssence)    .textures(potion, false, false).color(0xffe073fc);
			texture(holyEssence)     .textures(potion, false, false).color(0xfffce969);
			texture(bloodEssence)    .textures(potion, false, false).color(0xffd67369);
			texture(evocationEssence).textures(potion, false, false).color(0xff75fc79);
			texture(natureEssence)   .textures(potion, false, false).color(0xffb0f869);
			texture(cinderEssence)   .textures(potion, false, false).color(0xff6a2b00);

			texture(liquidLightning).textures(potion, false, false).color(0xffd7eef5);
			texture(potatoStew).textures(ResourceLocation.parse("tconstruct:fluid/food/stew/"), false, false).color(0xffe9ba61);
			texture(poisonousPotatoStew).textures(ResourceLocation.parse("tconstruct:fluid/food/stew/"), false, false).color(0xffedea61);
			ResourceLocation molten = ResourceLocation.parse("tconstruct:fluid/molten/");

			texture(moltenArcanium).textures(ConstructsCasting.id("fluid/arcanium/"),      false, false).color(0xffffffff);
			texture(moltenExilite) .textures(molten,      false, false).color(0xff5a5b5c);
			texture(moltenMithril).root(molten).still().flowing().color(0xff99cdd9).overlay().camera();
			texture(moltenPyrium).root(molten).still().flowing().color(0xffd6af39).overlay().camera();
			texture(moltenArcaneSalvage).textures(molten, false, false).color(0xffffffff);
			ResourceLocation inky = ResourceLocation.parse("tconstruct:fluid/slime/venom/");
			texture(squidInk)	   .textures(inky, false, false).color(0xff180030);
			texture(commonInk)     .textures(inky, false, false).color(0xff2d2d2d);
			texture(uncommonInk)   .textures(inky, false, false).color(0xff124300);
			texture(rareInk)       .textures(inky, false, false).color(0xff0f3844);
			texture(epicInk)       .textures(inky, false, false).color(0xff442d5d);
			texture(legendaryInk)  .textures(inky, false, false).color(0xffd6a200);

			texture(moltenCrystallizedCoral).textures(molten, false, false).color(0xcfd48996);
			texture(gasifiedRedstone).textures(ResourceLocation.parse("tconstruct:fluid/slime/ichor/"), false, false).color(0xffff0f01);
			texture(moltenPearl).textures(molten, false, false).color(0xfff28ba6);

			texture(abyssalEssence).textures(potion, false, false).color(0xff6400fc);
			texture(technomancyEssence).textures(potion, false, false).color(0xffb1bcc3);
			texture(aquaEssence).textures(potion, false, false).color(0xff56aada);
		}

		@Override
		public String getName() {
			return "Construct's Casting's Fluid Textures";
		}
	}

	public static class CCBucketModels extends FluidBucketModelProvider {
		public CCBucketModels(PackOutput packOutput, String modId) {
			super(packOutput, modId);
		}
	}
	public static class Tags extends FluidTagsProvider {
		public Tags(PackOutput p_255941_, CompletableFuture<HolderLookup.Provider> p_256600_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(p_255941_, p_256600_, modId, existingFileHelper);
		}



		public static class CCFluidTooltipProvider extends AbstractFluidTooltipProvider {

			public CCFluidTooltipProvider(PackOutput generator, String modId) {
				super(generator, modId);
			}

			@Override
			protected void addFluids() {

				add("bottle", BOTTLE_TOOLTIP)
						.addUnit("bottle", FluidValues.BOTTLE);
			}

			@Override
			public String getName() {
				return "Construct's Casting Fluid Tooltips";
			}
		}

		public static final TagKey<Fluid> MOLTEN_ARCANIUM = FluidTags.create(ConstructsCasting.id( "molten_arcanium"));
		public static final TagKey<Fluid> MOLTEN_EXILITE = FluidTags.create(ConstructsCasting.id( "molten_exilite"));
		public static final TagKey<Fluid> MOLTEN_ARCANE_SALVAGE = FluidTags.create(ConstructsCasting.id("molten_arcane_salvage"));
        public static final TagKey<Fluid> POTATO_STEW = FluidTags.create(ConstructsCasting.id("potato_stew"));
		public static final TagKey<Fluid> POISONOUS_POTATO_STEW = FluidTags.create(ConstructsCasting.id("poisonous_potato_stew"));
		public static final TagKey<Fluid> LIQUID_LIGHTNING = FluidTags.create(ConstructsCasting.id("liquid_lightning"));
		public static final TagKey<Fluid> BLOOD_ESSENCE_INGREDIENTS = FluidTags.create(ConstructsCasting.id("blood_essence_ingredients"));
		public static final TagKey<Fluid> ARCANIUM_BASE = FluidTags.create(ConstructsCasting.id("arcanium_base"));
		public static final TagKey<Fluid> BOTTLE_TOOLTIP = FluidTags.create(ConstructsCasting.id("bottle_tooltip"));


		public static TagKey<Fluid> essenceOf(String type) {
			return FluidTags.create(ResourceLocation.fromNamespaceAndPath(ConstructsCasting.MOD_ID, type + "_essence"));
		}
		public static TagKey<Fluid> ink(String rarity) {
			return FluidTags.create(ResourceLocation.fromNamespaceAndPath(ConstructsCasting.MOD_ID, rarity + "_ink"));
		}

		@Override
		protected void addTags(HolderLookup.Provider provider) {
			tag(MOLTEN_ARCANIUM).add(moltenArcanium.get());
			tag(MOLTEN_EXILITE).add(moltenExilite.get());
            tag(MOLTEN_ARCANE_SALVAGE).add(moltenArcaneSalvage.get());
			tag(POTATO_STEW).add(potatoStew.get());
			tag(POISONOUS_POTATO_STEW).add(poisonousPotatoStew.get());
			tag(LIQUID_LIGHTNING).add(liquidLightning.get());

			tag(ARCANIUM_BASE).add(TinkerFluids.moltenCopper.get()).add(TinkerFluids.moltenIron.get()).add(TinkerFluids.moltenGold.get());
			tag(essenceOf("arcane")).add(arcaneEssence.get());
            tag(essenceOf("cinder")).add(cinderEssence.get());
			tag(essenceOf("fire")).add(fireEssence.get());
			tag(essenceOf("ice")).add(iceEssence.get());
			tag(essenceOf("lightning")).add(lightningEssence.get());
			tag(essenceOf("ender")).add(enderEssence.get());
			tag(essenceOf("holy")).add(holyEssence.get());
			tag(essenceOf("blood")).add(bloodEssence.get());
			tag(essenceOf("evocation")).add(evocationEssence.get());
			tag(essenceOf("nature")).add(natureEssence.get());
			tag(ink("squid")).add(squidInk.get());
			tag(TagKey.create(ResourceKey.createRegistryKey(ResourceLocation.parse("forge:fluid_type")),ResourceLocation.parse("forge:ink"))).add(squidInk.get());
			tag(ink("common")).add(commonInk.get(), FluidRegistry.COMMON_INK.get()).addOptional(ResourceLocation.parse("create_wizardry:common_ink"));
			tag(ink("uncommon")).add(uncommonInk.get(), FluidRegistry.UNCOMMON_INK.get()).addOptional(ResourceLocation.parse("create_wizardry:uncommon_ink"));
			tag(ink("rare")).add(rareInk.get(), FluidRegistry.RARE_INK.get()).addOptional(ResourceLocation.parse("create_wizardry:rare_ink"));
			tag(ink("epic")).add(epicInk.get(), FluidRegistry.EPIC_INK.get()).addOptional(ResourceLocation.parse("create_wizardry:epic_ink"));
			tag(ink("legendary")).add(legendaryInk.get(), FluidRegistry.LEGENDARY_INK.get()).addOptional(ResourceLocation.parse("create_wizardry:legendary_ink"));
			tag(BLOOD_ESSENCE_INGREDIENTS).add(TinkerFluids.meatSoup.get(), FluidRegistry.BLOOD.get()).addOptional(ResourceLocation.parse("create_wizardry:blood"));
			//tooltips
			tag(TinkerTags.Fluids.METAL_TOOLTIPS).add(moltenArcanium.get(), moltenExilite.get(), moltenArcaneSalvage.get(), moltenMithril.get(), moltenPyrium.get());
			tag(BOTTLE_TOOLTIP)
					.add(arcaneEssence.get())
					.add(cinderEssence.get())
					.add(fireEssence.get())
					.add(iceEssence.get())
					.add(lightningEssence.get())
					.add(enderEssence.get())
					.add(holyEssence.get())
					.add(bloodEssence.get())
					.add(evocationEssence.get())
					.add(natureEssence.get())
					.add(liquidLightning.get())
					.add(squidInk.get())
					.add(commonInk.get())
					.add(uncommonInk.get())
					.add(rareInk.get())
					.add(epicInk.get())
					.add(legendaryInk.get())
					.add(abyssalEssence.get())
					.add(aquaEssence.get())
					.add(technomancyEssence.get())
					.add(FluidRegistry.COMMON_INK.get(), FluidRegistry.UNCOMMON_INK.get(), FluidRegistry.RARE_INK.get(), FluidRegistry.EPIC_INK.get(), FluidRegistry.LEGENDARY_INK.get())
					.add(FluidRegistry.ICE_VENOM_FLUID.get(), FluidRegistry.BLOOD.get(), FluidRegistry.TIMELESS_SLURRY_FLUID.get())
					.add(FluidRegistry.POTION_FLUID.get(),
							FluidRegistry.INVISIBILITY_ELIXIR_FLUID.get(), FluidRegistry.GREATER_INVISIBILITY_ELIXIR_FLUID.get(),
							FluidRegistry.GREATER_HEALING_ELIXIR_FLUID.get(), FluidRegistry.OAKSKIN_ELIXIR_FLUID.get(),
							FluidRegistry.GREATER_OAKSKIN_ELIXIR_FLUID.get(), FluidRegistry.EVASION_ELIXIR_FLUID.get(),
							FluidRegistry.GREATER_EVASION_ELIXIR_FLUID.get());
			tag(MantleTags.Fluids.SOUP).add(potatoStew.get()).add(poisonousPotatoStew.get());
			tag(TinkerTags.Fluids.SLIME_TOOLTIPS).add(moltenPearl.get());
			tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS).add(moltenCrystallizedCoral.get());
		}
	}
}
