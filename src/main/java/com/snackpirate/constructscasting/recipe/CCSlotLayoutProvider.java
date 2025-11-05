package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;

public class CCSlotLayoutProvider extends AbstractStationSlotLayoutProvider {
	public CCSlotLayoutProvider(PackOutput packOutput) {
		super(packOutput);
	}

	/**
	 *
	 */
	@Override
	protected void addLayouts() {
		define(ConstructsCasting.id("spellbooks"))
				.sortIndex(SORT_ARMOR)
				.icon(new Pattern(ConstructsCasting.id("spellbooks")))
				.addInputPattern(new Pattern(ConstructsCasting.id("spellbooks_first_part")), 31, 22, Ingredient.of(CCItems.spellbookCover.get(), CCItems.spellbookPlating.get()))
				.addInputItem(CCItems.spellbookCover.get(), 51, 34)
				.addInputItem(CCItems.pages.get(), 22, 53)
				.build();
	}

	/**
	 * @return
	 */
	@Override
	public String getName() {
		return "Construct's Casting Station Slot Layout Provider";
	}
}
