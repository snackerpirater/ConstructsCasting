package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;

public class CCMaterialTextures extends AbstractMaterialSpriteProvider {

	@Override
	public String getName() {
		return "Construct's Casting Material Sprites";
	}

	@Override
	protected void addAllMaterials() {
		ResourceLocation baseTexture = new ResourceLocation(ConstructsCasting.MOD_ID, "item/materials/generator/arcanium");
		ResourceLocation borderTexture = new ResourceLocation(ConstructsCasting.MOD_ID, "item/materials/generator/arcanium_border");
		ResourceLocation highlightTexture = new ResourceLocation(ConstructsCasting.MOD_ID, "item/materials/generator/arcanium_highlight");
		buildMaterial(CCMaterials.arcanium).meleeHarvest()
				.fallbacks("metal")
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture( 63, borderTexture,    0xFFC8C8C8).addTexture(102, borderTexture)
						.addTexture(140, baseTexture,      0xFFE1E1E1).addTexture(178, baseTexture)
						.addTexture(216, highlightTexture, 0xFFE1E1E1).addTexture(255, highlightTexture)
						.build());
	}
}
