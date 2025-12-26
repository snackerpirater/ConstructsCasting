package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.materials.CCMaterialStats;
import com.snackpirate.constructscasting.materials.MagicBaseMaterialStats;
import com.snackpirate.constructscasting.materials.MagicClothMaterialStats;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;

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
		buildTool("wand")
				.addPart("crystal", CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.addPart("trim", CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.addPart("handle", MagicBaseMaterialStats.ID);
		buildTool("battlestaff")
				.withLarge()
				.addPart("blade", HeadMaterialStats.ID)
				.addPart("crystal", CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.addPart("handle", HandleMaterialStats.ID)
				.addPart("grip", MagicBaseMaterialStats.ID);
		buildTool("flamberge")
				.withLarge()
				.addHead("blade")
				.addPart("guard", PlatingMaterialStats.LEGGINGS.getId())
				.addHandle("handle");
		addPart("spellbook_plating", CCMaterialStats.Statless.ADORNMENT.getIdentifier());
		addPart("spellbook_cover", MagicBaseMaterialStats.ID);
		addPart("pages", MagicClothMaterialStats.ID);
		addPart("wand_rod", MagicBaseMaterialStats.ID);
		addPart("faceted_gem", CCMaterialStats.Statless.ADORNMENT.getIdentifier());
	}
}
