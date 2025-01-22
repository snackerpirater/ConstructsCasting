package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.IronsSpellbooks;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.item.ContainerFoodItem;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.shared.TinkerFood;

import java.util.concurrent.CompletableFuture;

import static slimeknights.tconstruct.common.TinkerTags.Items.*;

public class CCItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConstructsCasting.MOD_ID);
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> potatoStewBowl = ITEMS.register("potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.MEAT_SOUP).stacksTo(1).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL)));
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> poisonousPotatoStewBowl = ITEMS.register("poisonous_potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.VENOM_BOTTLE).stacksTo(1).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL)));

	public static final RegistryObject<Item> exiliteIngot = ITEMS.register("exilite_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> exiliteNugget = ITEMS.register("exilite_nugget", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> wizardslimeBall = ITEMS.register("wizardslime_ball", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> slimeRune = ITEMS.register("slime_rune", () -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> exiliteReinforcement = ITEMS.register("exilite_reinforcement", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> slimySpellbook = ITEMS.register("tinkerers_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 6, CCTools.CCToolDefinitions.SLIMY_SPELLBOOK));
	//will fully implement later vvv
	public static final RegistryObject<Item> travellersSpellbook = ITEMS.register("travellers_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1), 10, CCTools.CCToolDefinitions.TRAVELLERS_SPELLBOOK));
	public static final RegistryObject<Item> platedSpellbook = ITEMS.register("plated_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 12, CCTools.CCToolDefinitions.PLATED_SPELLBOOK));
	public static final RegistryObject<Item> eldritchStaff = ITEMS.register("eldritch_staff", () -> new ModifiableItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE), CCTools.CCToolDefinitions.ELDRITCH_STAFF));


	public static CreativeModeTab.DisplayItemsGenerator DISPLAY_ITEMS = (parameters, output) -> {
		ITEMS.getEntries().forEach((regObj) -> output.accept(regObj.get()));
	};

	public static class Tags extends ItemTagsProvider {
		public static final TagKey<Item> SLIME_FOCUS = ItemTags.create(ConstructsCasting.id("slime_focus"));

		public Tags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
		}

		@SafeVarargs
		private void addToolTags(ItemLike tool, TagKey<Item>... tags) {
			Item item = tool.asItem();
			for (TagKey<Item> tag : tags) {
				ConstructsCasting.LOGGER.info("adding tool tag {}", tag.toString());
				this.tag(tag).add(item);
			}
		}

		@Override
		protected void addTags(HolderLookup.Provider pProvider) {
			ConstructsCasting.LOGGER.info("addubg tags");
//			tag(SLIME_FOCUS).add(wizardslimeBall.get());
//			tag(ItemTags.create(IronsSpellbooks.id("school_focus"))).add(wizardslimeBall.get());
//			tag(ItemTags.create(IronsSpellbooks.id("inscribed_rune"))).add(slimeRune.get());
			tag(ItemTags.create(new ResourceLocation("forge:ingots/exilite"))).add(exiliteIngot.get());
			tag(ItemTags.create(new ResourceLocation("forge:nuggets/exilite"))).add(exiliteNugget.get());
			tag(TinkerTags.Items.BONUS_SLOTS).add(slimySpellbook.get()).add(platedSpellbook.get()).add(eldritchStaff.get());
			tag(ItemTags.create(new ResourceLocation("curios:spellbook"))).add(slimySpellbook.get()).add(platedSpellbook.get());
			addToolTags(eldritchStaff.get(),    DURABILITY, STAFFS, SPECIAL_TOOLS, HELD_ARMOR, INTERACTABLE_DUAL, AOE, DYEABLE, EMBELLISHMENT_WOOD, BONUS_SLOTS);

			ConstructsCasting.LOGGER.info("addubg tags finish");
		}
	}

}

