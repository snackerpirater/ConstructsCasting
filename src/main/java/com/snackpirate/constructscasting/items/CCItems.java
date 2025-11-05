package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.materials.CCMaterialStats;
import com.snackpirate.constructscasting.materials.CCToolStats;
import com.snackpirate.constructscasting.materials.MagicBaseMaterialStats;
import com.snackpirate.constructscasting.materials.MagicClothMaterialStats;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.fluids.item.ContainerFoodItem;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.shared.TinkerFood;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static slimeknights.tconstruct.common.TinkerTags.Items.*;

public class CCItems {
	public static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(ConstructsCasting.MOD_ID);
	public static final ItemObject<ContainerFoodItem.FluidContainerFoodItem> potatoStewBowl = ITEMS.register("potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.MEAT_SOUP).stacksTo(1).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL)));
	public static final ItemObject<ContainerFoodItem.FluidContainerFoodItem> poisonousPotatoStewBowl = ITEMS.register("poisonous_potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.VENOM_BOTTLE).stacksTo(1).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL)));

	public static final ItemObject<Item> exiliteIngot = ITEMS.register("exilite_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final ItemObject<Item> exiliteNugget = ITEMS.register("exilite_nugget", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final ItemObject<Item> wizardslimeBall = ITEMS.register("wizardslime_ball", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final ItemObject<Item> slimeRune = ITEMS.register("slime_rune", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final ItemObject<Item> exiliteReinforcement = ITEMS.register("exilite_reinforcement", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final ItemObject<Item> slimySpellbook = ITEMS.register("tinkerers_spellbook", () -> new ModifiableSpellbookItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 6, CCTools.CCToolDefinitions.SLIMY_SPELLBOOK));
	//will fully implement later vvv
	public static final ItemObject<ModifiableSpellbookItem> travellersSpellbook = ITEMS.register("travellers_spellbook", () -> new ModifiableSpellbookItem(new Item.Properties().stacksTo(1), 12, CCTools.CCToolDefinitions.TRAVELLERS_SPELLBOOK));
	public static final ItemObject<ModifiableSpellbookItem> platedSpellbook = ITEMS.register("plated_spellbook", () -> new ModifiableSpellbookItem(new Item.Properties().stacksTo(1), 12, CCTools.CCToolDefinitions.PLATED_SPELLBOOK));
	public static final ItemObject<Item> eldritchStaff = ITEMS.register("eldritch_staff", () -> new ModifiableItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), CCTools.CCToolDefinitions.ELDRITCH_STAFF));

    public static final ItemObject<ToolPartItem> spellbookPlating = ITEMS.register("spellbook_plating", () -> new ToolPartItem(new Item.Properties(), CCMaterialStats.Statless.SPELLBOOK_PLATING.getIdentifier()));

    public static final ItemObject<ToolPartItem> pages = ITEMS.register("pages", () -> new ToolPartItem(new Item.Properties(), MagicClothMaterialStats.ID));

	public static final ItemObject<ToolPartItem> spellbookCover = ITEMS.register("spellbook_cover", () -> new ToolPartItem(new Item.Properties(), MagicBaseMaterialStats.ID));

    public static final CastItemObject spellbookPlatingCast = ITEMS.registerCast("spellbook_plating", new Item.Properties());
//	public static CreativeModeTab.DisplayItemsGenerator DISPLAY_ITEMS = (parameters, output) -> ITEMS.getEntries().forEach((regObj) -> {
//		if (!regObj.get().getDefaultInstance().is(Tags.HIDE_CREATIVE)) output.accept(regObj.get());
//
//	});

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(potatoStewBowl);
        output.accept(poisonousPotatoStewBowl);
        output.accept(exiliteIngot);
        output.accept(exiliteNugget);
        output.accept(slimySpellbook);
        output.accept(eldritchStaff);
        output.accept(spellbookPlatingCast);
		output.accept(spellbookPlatingCast.getSand());
		output.accept(spellbookPlatingCast.getRedSand());

        ToolBuildHandler.addVariants(output::accept, CCItems.platedSpellbook.get(), "");
		ToolBuildHandler.addVariants(output::accept, CCItems.travellersSpellbook.get(), "");
        spellbookPlating.get().addVariants(output::accept, "");
        spellbookCover.get().addVariants(output::accept, "");
        pages.get().addVariants(output::accept, "");
    }

	public static class Tags extends ItemTagsProvider {
		public static final TagKey<Item> SLIME_FOCUS = ItemTags.create(ConstructsCasting.id("slime_focus"));
		public static final TagKey<Item> HIDE_CREATIVE = ItemTags.create(ConstructsCasting.id("hide_creative"));
        public static final TagKey<Item> MODIFIABLE_SPELLCASTING = ItemTags.create(ConstructsCasting.id("modifiable_staffs"));
		public static final TagKey<Item> DRAGONSCALES = ItemTags.create(ConstructsCasting.id("dragon_scales"));
		public static final TagKey<Item> MODIFIABLE_CURIOS = ItemTags.create(ConstructsCasting.id("modifiable_curios"));

        public Tags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
		}

		@SafeVarargs
		private void addToolTags(ItemLike tool, TagKey<Item>... tags) {
			Item item = tool.asItem();
			for (TagKey<Item> tag : tags) {
//				ConstructsCasting.LOGGER.info("adding tool tag {}", tag.toString());
				this.tag(tag).add(item);
			}
		}

		@Override
		protected void addTags(HolderLookup.Provider pProvider) {
//			ConstructsCasting.LOGGER.info("addubg tags");
//			tag(SLIME_FOCUS).add(wizardslimeBall.get());
//			tag(ItemTags.create(IronsSpellbooks.id("school_focus"))).add(wizardslimeBall.get());
//			tag(ItemTags.create(IronsSpellbooks.id("inscribed_rune"))).add(slimeRune.get());
			tag(ItemTags.create(ResourceLocation.parse("forge:ingots/exilite"))).add(exiliteIngot.get());
			tag(ItemTags.create(ResourceLocation.parse("forge:nuggets/exilite"))).add(exiliteNugget.get());
			tag(TinkerTags.Items.BONUS_SLOTS).add(slimySpellbook.get()).add(platedSpellbook.get(), eldritchStaff.get(), travellersSpellbook.get());
			tag(ItemTags.create(ResourceLocation.parse("curios:spellbook"))).add(slimySpellbook.get(), platedSpellbook.get(), travellersSpellbook.get());
            tag(MODIFIABLE).addTag(MODIFIABLE_CURIOS);
			tag(MODIFIABLE_CURIOS).add(platedSpellbook.get(), slimySpellbook.get(), travellersSpellbook.get());
			tag(CCToolStats.MAGIC_TOOL).add(platedSpellbook.get(), slimySpellbook.get(), travellersSpellbook.get());
			addToolTags(eldritchStaff.get(),    DURABILITY, STAFFS, SPECIAL_TOOLS, HELD_ARMOR, INTERACTABLE_DUAL, AOE, DYEABLE, EMBELLISHMENT_WOOD, BONUS_SLOTS, MODIFIABLE_SPELLCASTING);
			tag(HIDE_CREATIVE).add(slimeRune.get(), wizardslimeBall.get(), travellersSpellbook.get(), pages.get(), spellbookCover.get(), spellbookPlating.get());
//			ConstructsCasting.LOGGER.info("addubg tags finish");
            tag(DRAGONSCALES).add(ItemRegistry.DRAGONSKIN.get(), TinkerModifiers.dragonScale.asItem());
            tag(MULTIPART_TOOL).add(platedSpellbook.get(), travellersSpellbook.get());
            IntrinsicTagAppender<Item> goldCasts = this.tag(TinkerTags.Items.GOLD_CASTS);
            IntrinsicTagAppender<Item> sandCasts = this.tag(TinkerTags.Items.SAND_CASTS);
            IntrinsicTagAppender<Item> redSandCasts = this.tag(TinkerTags.Items.RED_SAND_CASTS);
            IntrinsicTagAppender<Item> singleUseCasts = this.tag(TinkerTags.Items.SINGLE_USE_CASTS);
            IntrinsicTagAppender<Item> multiUseCasts = this.tag(TinkerTags.Items.MULTI_USE_CASTS);
            Consumer<CastItemObject> addCast = cast -> {
                // tag based on material
                goldCasts.add(cast.get());
                sandCasts.add(cast.getSand());
                redSandCasts.add(cast.getRedSand());
                // tag based on usage
                singleUseCasts.addTag(cast.getSingleUseTag());
                this.tag(cast.getSingleUseTag()).add(cast.getSand(), cast.getRedSand());
                multiUseCasts.addTag(cast.getMultiUseTag());
                this.tag(cast.getMultiUseTag()).add(cast.get());
            };
            addCast.accept(CCItems.spellbookPlatingCast);
		}
	}

}

