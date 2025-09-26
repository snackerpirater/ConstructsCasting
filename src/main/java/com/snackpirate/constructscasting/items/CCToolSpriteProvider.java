package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.materials.CCMaterialStats;
import com.snackpirate.constructscasting.materials.MagicBaseMaterialStats;
import com.snackpirate.constructscasting.materials.MagicClothMaterialStats;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;

public class CCToolSpriteProvider extends AbstractPartSpriteProvider {
	public CCToolSpriteProvider(String modID) {
		super(modID);
	}

	@Override
	public String getName() {
		return "Construct's Casting Tool Sprite";
	}

	/**
	 *
	 */
	@Override
	protected void addAllSpites() {
		buildTool("plated_spellbook")
				.addPart("plating", PlatingMaterialStats.SHIELD.getId())
				.addPart("cover", MagicBaseMaterialStats.ID)
				.addPart("pages", MagicClothMaterialStats.ID);
		addPart("spellbook_plating", PlatingMaterialStats.SHIELD.getId());
		addPart("spellbook_cover", MagicBaseMaterialStats.ID);
		addPart("pages", MagicClothMaterialStats.ID);
	}
}
