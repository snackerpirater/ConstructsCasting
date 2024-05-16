package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class CCMaterialTextures extends AbstractMaterialSpriteProvider {

	@Override
	public String getName() {
		return "Construct's Casting Material Sprites";
	}

	@Override
	protected void addAllMaterials() {
		ResourceLocation arcaniumBase = ConstructsCasting.id("item/materials/generator/arcanium");
		ResourceLocation arcaniumBorder = ConstructsCasting.id("item/materials/generator/arcanium_border");
		ResourceLocation arcaniumHighlight = ConstructsCasting.id("item/materials/generator/arcanium_highlight");
		buildMaterial(CCMaterials.arcanium).meleeHarvest().ranged().armor().maille().repairKit()
				.fallbacks("metal")
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture( 63, arcaniumBorder,    0xFFC8C8C8)
						.addTexture(102, arcaniumBorder)
						.addTexture(140, arcaniumBase,      0xFFE1E1E1)
						.addTexture(178, arcaniumBase)
						.addTexture(216, arcaniumHighlight, 0xFFE1E1E1)
						.addTexture(255, arcaniumHighlight)
						.build());
		buildMaterial(CCMaterials.exilite).meleeHarvest().ranged().armor().maille().repairKit()
				.fallbacks("metal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF292a2c)
						.addARGB(102, 0xFF3a3b3c)
						.addARGB(140, 0xFF47494b)
						.addARGB(178, 0xFF5a5b5c)
						.addARGB(216, 0xFF6e7278)
						.addARGB(255, 0xFF989ba1)
						.build()));
		buildMaterial(CCMaterials.arcaneCloth).maille().repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier())
				.fallbacks("cloth")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF0d4578)
						.addARGB(102, 0xFF2b6396)
						.addARGB(140, 0xFF477fb2)
						.addARGB(178, 0xFF73abde)
						.addARGB(216, 0xFFb1d9ff)
						.addARGB(255, 0xFFebf5ff)
						.build()));
		buildMaterial(CCMaterials.arcaneHide).maille().repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier())
				.fallbacks("cloth")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF4c4c4c)
						.addARGB(102, 0xFF7f7f7f)
						.addARGB(140, 0xFF929292)
						.addARGB(178, 0xFFb9b9b9)
						.addARGB(216, 0xFFd7d7d7)
						.addARGB(255, 0xFFffffff)
						.build()));
	}
}
