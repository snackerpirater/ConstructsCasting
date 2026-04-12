package com.snackpirate.constructscasting.plugin;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.recipe.ScrollMeltingRecipe;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.InkItem;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipe;
import slimeknights.tconstruct.plugin.jei.TConstructJEIConstants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@mezz.jei.api.JeiPlugin
public class CCJeiPlugin implements IModPlugin {
	@Override
	public @NotNull ResourceLocation getPluginUid() {
		return ConstructsCasting.id("jei_plugin");
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration register) {
		register.addRecipes(TConstructJEIConstants.MELTING, getScrollRecipes(register.getVanillaRecipeFactory()).filter(recipe -> !recipe.getInput().isEmpty()).toList());
		register.addRecipes(TConstructJEIConstants.FOUNDRY, getScrollRecipes(register.getVanillaRecipeFactory()).filter(recipe -> !recipe.getInput().isEmpty()).toList());
	}

	private static Stream<MeltingRecipe> getScrollRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
		return Arrays.stream(SpellRarity.values()).flatMap(
				rarity -> SchoolRegistry.REGISTRY.get().getValues().stream().map(schoolType -> recipeForRarityAndSchool(rarity, schoolType))); //whatthefuck
//        return SpellRegistry.getEnabledSpells().stream().flatMap(spell -> IntStream.rangeClosed(spell.getMinLevel(), spell.getMaxLevel()).mapToObj(level -> recipeForSpellAndLevel(spell, level)));
	}
	private static ItemStack getScrollStack(ItemStack stack, AbstractSpell spell, int spellLevel) {
		var scrollStack = stack.copy();
		ISpellContainer.createScrollContainer(spell, spellLevel, scrollStack);
		return scrollStack;
	}
	private static MeltingRecipe recipeForRarityAndSchool(SpellRarity spellRarity, SchoolType school) {
		var scrollStack = new ItemStack(ItemRegistry.SCROLL.get());
		Stream<ItemStack> scrolls = SpellRegistry.getEnabledSpells().stream().flatMap( //is iterated through for every rarity*school combo, consider making better? not sure if iterating through each spell * level would be better
				spell -> IntStream.rangeClosed(spell.getMinLevel(), spell.getMaxLevel())
						.filter(spellLevel -> spell.getRarity(spellLevel) == spellRarity && spell.getSchoolType().equals(school))
						.mapToObj(i -> getScrollStack(scrollStack, spell, i)));
		FluidStack ink = new FluidStack(InkItem.getInkForRarity(spellRarity).fluid().get(), 125);
		return new MeltingRecipe(ConstructsCasting.id("test"), "scroll_melting", Ingredient.of(scrolls), FluidOutput.fromStack(ink), 700, 20, List.of(FluidOutput.fromFluid(ScrollMeltingRecipe.schoolToEssence(school).get(), 100)), false);
	}
    //makes a recipe page for every single spell and level, no bueno
//    private static MeltingRecipe recipeForSpellAndLevel(AbstractSpell spell, int level) {
//        ItemStack scroll = new ItemStack(ItemRegistry.SCROLL.get());
//        scroll = getScrollStack(scroll, spell, level);
//        SchoolType school = spell.getSchoolType();
//        FluidStack ink = new FluidStack(InkItem.getInkForRarity(spell.getRarity(level)).fluid().get(), 125);
//        return new MeltingRecipe(ConstructsCasting.id("test"), "scroll_melting", Ingredient.of(scroll), FluidOutput.fromStack(ink), 700, 20, List.of(FluidOutput.fromFluid(ScrollMeltingRecipe.schoolToEssence(school).get(), 100)));
//
//    }
}
