package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
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
		buildMaterial(CCMaterials.hogskin).maille().repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier())
				.fallbacks("cloth", "primitive")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF4c4c4c)
						.addARGB(102, 0xFF7f7f7f)
						.addARGB(140, 0xFF929292)
						.addARGB(178, 0xFFb9b9b9)
						.addARGB(216, 0xFFd7d7d7)
						.addARGB(255, 0xFFffffff)
						.build()));
		buildMaterial(CCMaterials.frozenBone).meleeHarvest().statType(StatlessMaterialStats.BINDING.getIdentifier()).repairKit()
				.fallbacks("bone", "rock")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF6a787d)
						.addARGB(102, 0xFF7e9396)
						.addARGB(140, 0xFFa3c9c8)
						.addARGB(178, 0xFFd0e5e4)
						.addARGB(216, 0xFFeaf8f9)
						.addARGB(255, 0xFFfafcfc)
						.build()));
		buildMaterial(CCMaterials.frostRod).statType(HandleMaterialStats.ID)
				.fallbacks("metal", "primitive")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF4b7589)
						.addARGB(102, 0xFFa3b3b6)
						.addARGB(140, 0xFFbed5d7)
						.addARGB(178, 0xFFc8ecec)
						.addARGB(216, 0xFFfcfcfc)
						.addARGB(255, 0xFFffffff)
						.build()));
		ResourceLocation rainbowSlimeBase = ConstructsCasting.id("item/materials/generator/rainbowslime");
		ResourceLocation rainbowSlimeBorder = ConstructsCasting.id("item/materials/generator/rainbowslime_border");
		ResourceLocation rainbowSlimeHighlight = ConstructsCasting.id("item/materials/generator/rainbowslime_highlight");
		buildMaterial(CCMaterials.rainbowSlime).statType(TinkerPartSpriteProvider.SLIMESUIT)
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture( 63, rainbowSlimeBorder,    0xFFC8C8C8)
						.addTexture(102, rainbowSlimeBorder)
						.addTexture(140, rainbowSlimeBase,      0xFFE1E1E1)
						.addTexture(178, rainbowSlimeBase)
						.addTexture(216, rainbowSlimeHighlight, 0xFFE1E1E1)
						.addTexture(255, rainbowSlimeHighlight)
						.build());
	}
}
