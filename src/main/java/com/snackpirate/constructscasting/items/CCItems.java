package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluids;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.fluids.item.ContainerFoodItem;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.shared.TinkerFood;

public class CCItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ConstructsCasting.MOD_ID);
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> potatoStewBowl = ITEMS.register("potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.MEAT_SOUP).stacksTo(1).tab(CreativeModeTab.TAB_FOOD).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.potatoStew.get(), FluidValues.BOWL)));
	public static final RegistryObject<ContainerFoodItem.FluidContainerFoodItem> poisonousPotatoStewBowl = ITEMS.register("poisonous_potato_stew", () -> new ContainerFoodItem.FluidContainerFoodItem(new Item.Properties().food(TinkerFood.VENOM_BOTTLE).stacksTo(1).tab(CreativeModeTab.TAB_FOOD).craftRemainder(Items.BOWL), () -> new FluidStack(CCFluids.poisonousPotatoStew.get(), FluidValues.BOWL)));

//	public static final RegistryObject<Item> arcaniumApple = ITEMS.register("arcanium_apple", () -> new EdibleItem(new FoodProperties.Builder().nutrition(4).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffectRegistry.HASTENED.get(), 700, 1), 1.0F).alwaysEat().build(), CreativeModeTab.TAB_FOOD));
}
