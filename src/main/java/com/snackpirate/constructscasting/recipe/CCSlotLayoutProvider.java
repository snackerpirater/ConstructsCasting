package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.items.CCItems;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;

public class CCSlotLayoutProvider extends AbstractStationSlotLayoutProvider {
	public CCSlotLayoutProvider(PackOutput packOutput) {
		super(packOutput);
	}

	/**
	 *
	 */
	@Override
	protected void addLayouts() {
		defineModifiable(CCItems.platedSpellbook)
				.sortIndex(SORT_ARMOR)
				.addInputItem(CCItems.spellbookPlating.get(), 31, 22)
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
