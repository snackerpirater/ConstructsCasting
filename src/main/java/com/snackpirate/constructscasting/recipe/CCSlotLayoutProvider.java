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

	@Override
	protected void addLayouts() {
		define(ConstructsCasting.id("spellbooks"))
				.sortIndex(SORT_ARMOR)
                .translationKey("pattern.constructs_casting.spellbooks")
				.icon(new Pattern(ConstructsCasting.id("spellbooks")))
				.addInputPattern(new Pattern(ConstructsCasting.id("spellbooks_first_part")), 31-9, 22+9, Ingredient.of(CCItems.spellbookCover.get(), CCItems.spellbookPlating.get()))
				.addInputItem(CCItems.spellbookCover.get(), 51-9, 34+9)
				.addInputItem(CCItems.pages.get(), 31-9, 43+9)
				.build();
	}

	@Override
	public String getName() {
		return "Construct's Casting Station Slot Layout Provider";
	}
}
