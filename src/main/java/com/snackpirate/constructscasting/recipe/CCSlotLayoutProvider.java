package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.items.CCTools;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;

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
        defineModifiable(CCItems.battlestaff)
                .sortIndex(SORT_LARGE + SORT_RANGED)
                .addInputItem(TinkerToolParts.broadAxeHead, 25, 26)
                .addInputItem(CCItems.facetedGem,   45, 26)
                .addInputItem(CCItems.wandRod,  25, 46)
                .addInputItem(TinkerToolParts.toughHandle,   7, 62)
                .build();
        defineModifiable(CCItems.wand)
                .sortIndex(SORT_RANGED)
                .addInputItem(CCItems.facetedGem, 48, 26)
                .addInputItem(CCItems.facetedGem, 12, 62)
                .addInputItem(CCItems.wandRod, 30, 44)
                .build();
	}

	@Override
	public String getName() {
		return "Construct's Casting Station Slot Layout Provider";
	}
}
