package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.materials.CCMaterialStats;
import com.snackpirate.constructscasting.materials.MagicBaseMaterialStats;
import com.snackpirate.constructscasting.materials.MagicClothMaterialStats;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

public class CCToolSpriteProvider extends AbstractPartSpriteProvider {
	public CCToolSpriteProvider(String modID) {
		super(modID);
	}

	@Override
	public String getName() {
		return "Construct's Casting Tool Sprites";
	}

	@Override
	protected void addAllSpites() {
		buildTool("plated_spellbook")
				.addPart("plating", CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.addPart("cover", MagicBaseMaterialStats.ID)
				.addPart("pages", MagicClothMaterialStats.ID);
		buildTool("travellers_spellbook")
				.addPart("binding", MagicBaseMaterialStats.ID)
				.addPart("cover", MagicBaseMaterialStats.ID)
				.addPart("pages", MagicClothMaterialStats.ID);
		addPart("spellbook_plating", CCMaterialStats.Statless.ADORNMENT.getIdentifier());
		addPart("spellbook_cover", MagicBaseMaterialStats.ID);
		addPart("pages", MagicClothMaterialStats.ID);
	}
}
