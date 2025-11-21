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
//				.statType(CCMaterialStats.Statless.ADORNMENT)
				.fallbacks("metal")
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture( 63, arcaniumBorder,    0xFFC8C8C8)
						.addTexture(102, arcaniumBorder,    0xFFFFFFFF)
						.addTexture(140, arcaniumBase,      0xFFD0D0D0)
						.addTexture(178, arcaniumBase,      0xFFE1E1E1)
						.addTexture(216, arcaniumHighlight, 0xFFE1E1E1)
						.addTexture(255, arcaniumHighlight, 0xFFFFFFFF)
						.build());
		buildMaterial(CCMaterials.exilite).meleeHarvest().ranged().armor().maille().repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT)
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
						.addARGB(63,  0xFF444996)
						.addARGB(102, 0xFF615ecc)
						.addARGB(140, 0xFF76a5e1)
						.addARGB(178, 0xFF7bd4e7)
						.addARGB(216, 0xFFcaeafc)
						.addARGB(255, 0xFFfcfcfc)
						.build()));
		buildMaterial(CCMaterials.hogskin).repairKit().statType(MagicClothMaterialStats.ID)
				.fallbacks("cloth", "primitive")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF5e3235)
						.addARGB(102, 0xFF94544b)
						.addARGB(140, 0xFFbb725b)
						.addARGB(178, 0xFFc98265)
						.addARGB(216, 0xFFe8a074)
						.addARGB(255, 0xFFf2ba9a)
						.build()));
        buildMaterial(CCMaterials.dragonskin).repairKit().statType(MagicBaseMaterialStats.ID)
                        .fallbacks("rock", "primitive")
                                .transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
                                        .addARGB(63,  0xFF000000)
                                        .addARGB(102, 0xFF141216)
                                        .addARGB(140, 0xFF1a181c)
                                        .addARGB(178, 0xFF27202b)
                                        .addARGB(216, 0xFF322835)
                                        .addARGB(255, 0xFF413248)
                                        .build()));
		buildMaterial(CCMaterials.frozenBone).meleeHarvest().statType(StatlessMaterialStats.BINDING.getIdentifier(), MagicBaseMaterialStats.ID).repairKit()
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


        buildMaterial(CCMaterials.mithril).meleeHarvest().ranged().armor().maille().repairKit().statType(CCMaterialStats.Statless.ADORNMENT)
                .fallbacks("metal")
                .transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
                        .addARGB(63,  0xFF496c84)
                        .addARGB(102, 0xFF5b8092)
                        .addARGB(140, 0xFF7dacaf)
                        .addARGB(178, 0xFF99cdd9)
                        .addARGB(216, 0xFFbdf3f3)
                        .addARGB(255, 0xFFdcfffe)
                        .build()));
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
						.addARGB(63,  0xFF666666)
						.addARGB(102, 0xFF8C8C8C)
						.addARGB(140, 0xFFB2B2B2)
						.addARGB(178, 0xFFD8D8D8)
						.addARGB(216, 0xFFffffff)
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
		buildMaterial(CCMaterials.amethyst).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF533888)
						.addARGB(102, 0xFF6e4ea9)
						.addARGB(140, 0xFF8b69ca)
						.addARGB(178, 0xFFcd9ef0)
						.addARGB(216, 0xFFfbc9e3)
						.addARGB(255, 0xFFfcfad2)
						.build()));
		buildMaterial(CCMaterials.quartz).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF6e5c59)
						.addARGB(102, 0xFFb4a28c)
						.addARGB(140, 0xFFd1c8b8)
						.addARGB(178, 0xFFdad1c4)
						.addARGB(216, 0xFFe2dcd3)
						.addARGB(255, 0xFFf4f2ef)
						.build()));
		buildMaterial(CCMaterials.emerald).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF002c00)
						.addARGB(102, 0xFF005200)
						.addARGB(140, 0xFF00a82b)
						.addARGB(178, 0xFF17da61)
						.addARGB(216, 0xFF40f082)
						.addARGB(255, 0xFFd8fce8)
						.build()));
		buildMaterial(CCMaterials.earthslimeCrystal).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF33522e)
						.addARGB(102, 0xFF467941)
						.addARGB(140, 0xFF75bc6c)
						.addARGB(178, 0xFF8ad480)
						.addARGB(216, 0xFFc7fbcc)
						.addARGB(255, 0xFFfcfcfc)
						.build()));
		buildMaterial(CCMaterials.skyslimeCrystal).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF2e5250)
						.addARGB(102, 0xFF3a6c6c)
						.addARGB(140, 0xFF62aaa9)
						.addARGB(178, 0xFF80d4d2)
						.addARGB(216, 0xFFc7f1fb)
						.addARGB(255, 0xFFfcfcfc)
						.build()));
		buildMaterial(CCMaterials.enderslimeCrystal).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF23003d)
						.addARGB(102, 0xFF23003d)
						.addARGB(140, 0xFF4f2767)
						.addARGB(178, 0xFF816390)
						.addARGB(216, 0xFFa998af)
						.addARGB(255, 0xFFcbcbcb)
						.build()));
		buildMaterial(CCMaterials.ichorCrystal).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFFae3f00)
						.addARGB(102, 0xFFd15100)
						.addARGB(140, 0xFFfc8124)
						.addARGB(178, 0xFFfcb77b)
						.addARGB(216, 0xFFfcdebd)
						.addARGB(255, 0xFFfcfcfc)
						.build()));
		buildMaterial(CCMaterials.permafrost).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF1171f2)
						.addARGB(102, 0xFF139afc)
						.addARGB(140, 0xFF41b6f1)
						.addARGB(178, 0xFF85cbee)
						.addARGB(216, 0xFFbee6fb)
						.addARGB(255, 0xFFeef7fc)
						.build()));
		buildMaterial(CCMaterials.glowstone).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF59381c)
						.addARGB(102, 0xFF7d4721)
						.addARGB(140, 0xFFb2703f)
						.addARGB(178, 0xFFe8a84d)
						.addARGB(216, 0xFFfcba5d)
						.addARGB(255, 0xFFf6d19a)
						.build()));
		buildMaterial(CCMaterials.echoShard).repairKit()
				.statType(CCMaterialStats.Statless.ADORNMENT.getIdentifier())
				.fallbacks("crystal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF0d1217)
						.addARGB(102, 0xFF052a31)
						.addARGB(140, 0xFF03404f)
						.addARGB(178, 0xFF0a4f5f)
						.addARGB(216, 0xFF009093)
						.addARGB(255, 0xFF29dce8)
						.build()));

	}
}
