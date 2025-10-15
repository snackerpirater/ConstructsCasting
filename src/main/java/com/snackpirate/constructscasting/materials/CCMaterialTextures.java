package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
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
		buildMaterial(CCMaterials.arcaneCloth).maille().repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier(), StatlessMaterialStats.BOWSTRING.getIdentifier(), MagicClothMaterialStats.ID)
				.fallbacks("cloth")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF0d4578)
						.addARGB(102, 0xFF2b6396)
						.addARGB(140, 0xFF477fb2)
						.addARGB(178, 0xFF73abde)
						.addARGB(216, 0xFFb1d9ff)
						.addARGB(255, 0xFFebf5ff)
						.build()));
		buildMaterial(CCMaterials.hogskin).maille().repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier(), StatlessMaterialStats.BOWSTRING.getIdentifier(), MagicClothMaterialStats.ID)
				.fallbacks("cloth", "primitive")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF5e3235)
						.addARGB(102, 0xFF94544b)
						.addARGB(140, 0xFFbb725b)
						.addARGB(178, 0xFFc98265)
						.addARGB(216, 0xFFe8a074)
						.addARGB(255, 0xFFf2ba9a)
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


		ResourceLocation cosmiBase = ConstructsCasting.id("item/materials/generator/cosmichalcum");
		ResourceLocation cosmiBorder = ConstructsCasting.id("item/materials/generator/cosmichalcum_border");
		ResourceLocation cosmiHighlight = ConstructsCasting.id("item/materials/generator/cosmichalcum_highlight");
		buildMaterial(CCMaterials.cosmichalcum).meleeHarvest().ranged().armor().maille().repairKit()
				.fallbacks("metal")
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture( 63, cosmiBorder,    0xFFC8C8C8)
						.addTexture(102, cosmiBorder)
						.addTexture(140, cosmiBase,      0xFFE1E1E1)
						.addTexture(178, cosmiBase)
						.addTexture(216, cosmiHighlight, 0xFFE1E1E1)
						.addTexture(255, cosmiHighlight)
						.build());
		buildMaterial(CCMaterials.paper).statType(MagicClothMaterialStats.ID)
				.fallbacks("cloth")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF3f3f3f)
						.addARGB(102, 0xFF666666)
						.addARGB(140, 0xFF8C8C8C)
						.addARGB(178, 0xFFB2B2B2)
						.addARGB(216, 0xFFD8D8D8)
						.addARGB(255, 0xFFffffff)
						.build()));
		buildMaterial(CCMaterials.leaf)
				.statType(MagicClothMaterialStats.ID)
				.fallbacks("primitive", "cloth")
				.colorMapper(GreyToColorMapping.builderFromBlack()
						.addARGB(63, 0xFF143306)
						.addARGB(102, 0xFF183D08)
						.addARGB(140, 0xFF1F4E0A)
						.addARGB(178, 0xFF265F0D)
						.addARGB(216, 0xFF2E730F)
						.addARGB(255, 0xFF3A9313)
						.build());

	}
}
