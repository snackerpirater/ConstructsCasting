package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.item.ContainerFoodItem;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.shared.TinkerFood;

public class CCItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConstructsCasting.MOD_ID);
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> potatoStewBowl = ITEMS.register("potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.MEAT_SOUP).stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL)));
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> poisonousPotatoStewBowl = ITEMS.register("poisonous_potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.VENOM_BOTTLE).stacksTo(1).tab(ConstructsCasting.CREATIVE_TAB).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL)));

	public static final RegistryObject<Item> exiliteIngot = ITEMS.register("exilite_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static final RegistryObject<Item> exiliteNugget = ITEMS.register("exilite_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static final RegistryObject<Item> exiliteReinforcement = ITEMS.register("exilite_reinforcement", () -> new Item(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static final RegistryObject<Item> tinkerersSpellbook = ITEMS.register("tinkerers_spellbook", () -> new TinkerersSpellbookItem(new Item.Properties().stacksTo(64).tab(ConstructsCasting.CREATIVE_TAB)));
	public static class CCItemTagsProvider extends ItemTagsProvider {

		public CCItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(dataGenerator, blockTagsProvider, modId, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(ItemTags.create(new ResourceLocation("forge:ingots/exilite"))).add(exiliteIngot.get());
			tag(ItemTags.create(new ResourceLocation("forge:nuggets/exilite"))).add(exiliteNugget.get());
			tag(TinkerTags.Items.BONUS_SLOTS).add(tinkerersSpellbook.get());
			tag(ItemTags.create(new ResourceLocation("curios:spellbook"))).add(tinkerersSpellbook.get());
		}
	}

}

