package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;

import java.util.List;

public class ScrollMeltingRecipe extends MeltingRecipe {

	public static final RecordLoadable<ScrollMeltingRecipe> LOADER = RecordLoadable.create(
			ContextKey.ID.requiredField(), LoadableRecipeSerializer.RECIPE_GROUP, INPUT, OUTPUT, TEMPERATURE, TIME, BYPRODUCTS, ScrollMeltingRecipe::new);

	public ScrollMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidStack output, int temperature, int time, List<FluidStack> byproducts) {
		super(id, group, input, output, temperature, time, byproducts);
	}

	@Override
	public FluidStack getOutput(IMeltingContainer inv) {
		return new FluidStack(CCFluids.getInkFluidForRarity(ISpellContainer.get(inv.getStack()).getSpellAtIndex(0).getRarity()).get(), 100);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CCRecipes.scrollMeltingSerializer.get();
	}
}
