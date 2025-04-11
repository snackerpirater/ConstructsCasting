package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.field.LoadableField;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeLookup;

import java.util.List;

public class ScrollMeltingRecipe implements IMeltingRecipe {
	protected static final LoadableField<Ingredient, ScrollMeltingRecipe> INPUT;
	protected static final LoadableField<FluidOutput, ScrollMeltingRecipe> OUTPUT;
	protected static final LoadableField<Integer, ScrollMeltingRecipe> TEMPERATURE;
	protected static final LoadableField<Integer, ScrollMeltingRecipe> TIME;
	protected static final LoadableField<List<FluidOutput>, ScrollMeltingRecipe> BYPRODUCTS;
	public static final RecordLoadable<ScrollMeltingRecipe> LOADER;
	private final ResourceLocation id;
	protected final String group;
	protected final Ingredient input;
	protected final FluidOutput output;
	protected final int temperature;
	protected final int time;
	protected final List<FluidOutput> byproducts;

	public ScrollMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidOutput output, int temperature, int time, List<FluidOutput> byproducts) {
		this(id, group, input, output, temperature, time, byproducts, true);
	}

	public ScrollMeltingRecipe(ResourceLocation id, String group, Ingredient input, FluidOutput output, int temperature, int time, List<FluidOutput> byproducts, boolean addLookup) {
		super();
		this.id = id;
		this.group = group;
		this.input = input;
		this.output = output;
		this.temperature = temperature;
		this.time = time;
		this.byproducts = byproducts;
		if (addLookup) {
			MeltingRecipeLookup.addMeltingFluid(input, output, temperature);
		}

	}

	@Override
	public FluidStack getOutput(IMeltingContainer inv) {
		return new FluidStack(CCFluids.getInkFluidForRarity(ISpellContainer.get(inv.getStack()).getSpellAtIndex(0).getRarity()).get(), 100);
	}

	@Override
	public int getTemperature(IMeltingContainer iMeltingContainer) {
		return this.temperature;
	}

	@Override
	public int getTime(IMeltingContainer iMeltingContainer) {
		return 20;
	}

	@Override
	public boolean matches(IMeltingContainer pContainer, Level pLevel) {
		return pContainer.hasAnyMatching((stack) -> stack.is(ItemRegistry.SCROLL.get()));
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CCRecipes.scrollMeltingSerializer.get();
	}

	static {
		INPUT = IngredientLoadable.DISALLOW_EMPTY.requiredField("ingredient", (r) -> r.input);
		OUTPUT = FluidOutput.Loadable.REQUIRED.requiredField("result", (r) -> r.output);
		TEMPERATURE = IntLoadable.FROM_ZERO.requiredField("temperature", (r) -> r.temperature);
		TIME = IntLoadable.FROM_ONE.requiredField("time", (r) -> r.time);
		BYPRODUCTS = FluidOutput.Loadable.REQUIRED.list(0).defaultField("byproducts", List.of(), (r) -> r.byproducts);
		LOADER = RecordLoadable.create(ContextKey.ID.requiredField(), LoadableRecipeSerializer.RECIPE_GROUP, INPUT, OUTPUT, TEMPERATURE, TIME, BYPRODUCTS, ScrollMeltingRecipe::new);
	}
}
