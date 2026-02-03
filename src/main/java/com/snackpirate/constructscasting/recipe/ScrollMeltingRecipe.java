package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.field.LoadableField;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.recipe.IMultiRecipe;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeLookup;

import java.util.List;

public class ScrollMeltingRecipe implements IMeltingRecipe {
	protected static final LoadableField<Integer, ScrollMeltingRecipe> OUTPUT;
	protected static final LoadableField<Integer, ScrollMeltingRecipe> BYPRODUCT_AMOUNT;
	protected static final LoadableField<Integer, ScrollMeltingRecipe> TEMPERATURE;
	protected static final LoadableField<Integer, ScrollMeltingRecipe> TIME;
	public static final RecordLoadable<ScrollMeltingRecipe> LOADER;
	private final ResourceLocation id;
	protected final String group;

	protected final int output;
	protected final int byproductAmount;
	protected final int temperature;
	protected final int time;

	public ScrollMeltingRecipe(ResourceLocation id, String group, int output, int byproducts, int temperature, int time) {
		this(id, group, output, byproducts, temperature, time, true);
	}

	public ScrollMeltingRecipe(ResourceLocation id, String group, int output, int byproducts, int temperature, int time, boolean addLookup) {
		super();
		this.id = id;
		this.group = group;
		this.output = output;
		this.byproductAmount = byproducts;
		this.temperature = temperature;
		this.time = time;
	}

	@Override
	public FluidStack getOutput(IMeltingContainer inv) {
		return new FluidStack(CCFluids.getInkFluidForRarity(ISpellContainer.get(inv.getStack()).getSpellAtIndex(0).getRarity()), this.output);
	}

	@Override
	public void handleByproducts(IMeltingContainer inv, IFluidHandler handler) {
		SchoolType school = ISpellContainer.get(inv.getStack()).getSpellAtIndex(0).getSpell().getSchoolType();
		handler.fill(new FluidStack(schoolToEssence(school).get(), byproductAmount), IFluidHandler.FluidAction.EXECUTE);
	}
	public static FluidObject<UnplaceableFluid> schoolToEssence(SchoolType school) {
		String id = school.getId().getPath();
		return switch (id) {
			case ("fire") -> CCFluids.fireEssence;
			case ("ice") -> CCFluids.iceEssence;
			case ("ender") -> CCFluids.enderEssence;
			case ("lightning") -> CCFluids.lightningEssence;
			case ("holy") -> CCFluids.holyEssence;
			case ("blood") -> CCFluids.bloodEssence;
			case ("nature") -> CCFluids.natureEssence;
			case ("evocation") -> CCFluids.evocationEssence;
			case ("technomancy") -> CCFluids.technomancyEssence;
			case ("abyssal") -> CCFluids.abyssalEssence;
			case ("aqua") -> CCFluids.aquaEssence;
			case ("sound") -> CCFluids.soundEssence;
			default -> CCFluids.arcaneEssence;
		};
	}

	@Override
	public int getTemperature(IMeltingContainer iMeltingContainer) {
		return this.temperature;
	}

	@Override
	public int getTime(IMeltingContainer iMeltingContainer) {
		return time;
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
		OUTPUT = IntLoadable.FROM_ONE.requiredField("output_amount", (r) -> r.output);
		BYPRODUCT_AMOUNT = IntLoadable.FROM_ONE.requiredField("byproduct_amount", (r) -> r.byproductAmount);
		TEMPERATURE = IntLoadable.FROM_ZERO.requiredField("temperature", (r) -> r.temperature);
		TIME = IntLoadable.FROM_ONE.requiredField("time", (r) -> r.time);
		LOADER = RecordLoadable.create(ContextKey.ID.requiredField(), LoadableRecipeSerializer.RECIPE_GROUP, OUTPUT, BYPRODUCT_AMOUNT, TEMPERATURE, TIME, ScrollMeltingRecipe::new);
	}
}
