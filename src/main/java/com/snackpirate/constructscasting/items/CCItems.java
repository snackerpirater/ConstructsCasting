package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
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
import static slimeknights.tconstruct.common.TinkerTags.Items.*;

public class CCItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConstructsCasting.MOD_ID);
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> potatoStewBowl = ITEMS.register("potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.MEAT_SOUP).stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL)));
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> poisonousPotatoStewBowl = ITEMS.register("poisonous_potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.VENOM_BOTTLE).stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL)));

	public static final RegistryObject<Item> exiliteIngot = ITEMS.register("exilite_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static final RegistryObject<Item> exiliteNugget = ITEMS.register("exilite_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));

	public static final RegistryObject<Item> wizardslimeBall = ITEMS.register("wizardslime_ball", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));

	public static final RegistryObject<Item> exiliteReinforcement = ITEMS.register("exilite_reinforcement", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static final RegistryObject<Item> slimySpellbook = ITEMS.register("tinkerers_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).rarity(Rarity.EPIC), 6, CCTools.CCToolDefinitions.SLIMY_SPELLBOOK));
	//will fully implement later vvv
	public static final RegistryObject<Item> travellersSpellbook = ITEMS.register("travellers_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 10, CCTools.CCToolDefinitions.TRAVELLERS_SPELLBOOK));
	public static final RegistryObject<Item> platedSpellbook = ITEMS.register("plated_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).rarity(Rarity.RARE), 12, CCTools.CCToolDefinitions.PLATED_SPELLBOOK));
	public static final RegistryObject<Item> eldritchStaff = ITEMS.register("eldritch_staff", () -> new ModifiableItem(new Item.Properties().stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).rarity(Rarity.RARE), CCTools.CCToolDefinitions.ELDRITCH_STAFF));

	public static class Tags extends ItemTagsProvider {
		public static final TagKey<Item> SLIME_FOCUS = ItemTags.create(ConstructsCasting.id("slime_focus"));

		public Tags(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, modId, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(SLIME_FOCUS).add(wizardslimeBall.get());
			tag(ItemTags.create(IronsSpellbooks.id("school_focus"))).add(wizardslimeBall.get());
			tag(ItemTags.create(new ResourceLocation("forge:ingots/exilite"))).add(exiliteIngot.get());
			tag(ItemTags.create(new ResourceLocation("forge:nuggets/exilite"))).add(exiliteNugget.get());
			tag(TinkerTags.Items.BONUS_SLOTS).add(slimySpellbook.get()).add(platedSpellbook.get()).add(eldritchStaff.get());
			tag(ItemTags.create(new ResourceLocation("curios:spellbook"))).add(slimySpellbook.get()).add(platedSpellbook.get());
			addToolTags(eldritchStaff.get(),    DURABILITY, STAFFS, SPECIAL_TOOLS, HELD_ARMOR, INTERACTABLE_DUAL, AOE, DYEABLE, EMBELLISHMENT_WOOD, BONUS_SLOTS);


		}

		@SafeVarargs
		private void addToolTags(ItemLike tool, TagKey<Item>... tags) {
			Item item = tool.asItem();
			for (TagKey<Item> tag : tags) {
				this.tag(tag).add(item);
			}
		}
	}

}

