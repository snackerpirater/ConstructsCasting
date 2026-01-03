package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import slimeknights.mantle.fluid.transfer.AbstractFluidContainerTransferProvider;
import slimeknights.mantle.fluid.transfer.FillFluidContainerTransfer;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.recipe.ingredient.FluidIngredient;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.recipe.FluidValues;

public class CCFluidTransfer extends AbstractFluidContainerTransferProvider {
	public CCFluidTransfer(PackOutput packOutput, String modId) {
		super(packOutput, modId);
	}

	@Override
	protected void addTransfers() {
		addFillEmpty("potato_stew_", CCItems.potatoStewBowl, Items.BOWL, CCFluids.potatoStew, FluidValues.BOWL, false);
		addFillEmpty("poisonous_potato_stew_", CCItems.poisonousPotatoStewBowl, Items.BOWL, CCFluids.poisonousPotatoStew, FluidValues.BOWL, false);
		addBottleFill("blood", ItemRegistry.BLOOD_VIAL.get(), FluidRegistry.BLOOD.get());
		addBottleFill("lightning", ItemRegistry.LIGHTNING_BOTTLE.get(), CCFluids.liquidLightning.get());
		addBottleFill("ice_venom", ItemRegistry.ICE_VENOM_VIAL.get(), FluidRegistry.ICE_VENOM_FLUID.get());
		addBottleFill("timeless_slurry", ItemRegistry.TIMELESS_SLURRY.get(), FluidRegistry.TIMELESS_SLURRY_FLUID.get());


		addBottleFill("ink/common", ItemRegistry.INK_COMMON.get(), FluidRegistry.COMMON_INK.get());
		addBottleFill("ink/uncommon", ItemRegistry.INK_UNCOMMON.get(), FluidRegistry.UNCOMMON_INK.get());
		addBottleFill("ink/rare", ItemRegistry.INK_RARE.get(), FluidRegistry.RARE_INK.get());
		addBottleFill("ink/epic", ItemRegistry.INK_EPIC.get(), FluidRegistry.EPIC_INK.get());
		addBottleFill("ink/legendary", ItemRegistry.INK_LEGENDARY.get(), FluidRegistry.LEGENDARY_INK.get());

		addBottleFill("elixir/greater_healing", ItemRegistry.GREATER_HEALING_POTION.get(), FluidRegistry.GREATER_HEALING_ELIXIR_FLUID.get());
		addBottleFill("elixir/greater_invisibility", ItemRegistry.GREATER_INVISIBILITY_ELIXIR.get(), FluidRegistry.GREATER_INVISIBILITY_ELIXIR_FLUID.get());
		addBottleFill("elixir/invisibility", ItemRegistry.INVISIBILITY_ELIXIR.get(), FluidRegistry.INVISIBILITY_ELIXIR_FLUID.get());
		addBottleFill("elixir/greater_evasion", ItemRegistry.GREATER_EVASION_ELIXIR.get(), FluidRegistry.GREATER_EVASION_ELIXIR_FLUID.get());
		addBottleFill("elixir/evasion", ItemRegistry.EVASION_ELIXIR.get(), FluidRegistry.EVASION_ELIXIR_FLUID.get());
		addBottleFill("elixir/greater_oakskin", ItemRegistry.GREATER_OAKSKIN_ELIXIR.get(), FluidRegistry.GREATER_OAKSKIN_ELIXIR_FLUID.get());
		addBottleFill("elixir/oaksin", ItemRegistry.OAKSKIN_ELIXIR.get(), FluidRegistry.OAKSKIN_ELIXIR_FLUID.get());
	}
	protected void addBottleFill(String name, ItemLike output, Fluid fluid) {
		addTransfer(name + "_fill", new FillFluidContainerTransfer(Ingredient.of(Items.GLASS_BOTTLE), ItemOutput.fromItem(output), FluidIngredient.of(fluid, FluidValues.BOTTLE)));
	}

	@Override
	public String getName() {
		return "Construct's Casting Fluid Transfer Provider";
	}
}
