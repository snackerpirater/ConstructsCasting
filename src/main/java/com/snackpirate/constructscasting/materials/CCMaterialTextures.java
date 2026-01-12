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
		buildMaterial(CCMaterials.divinePearl).arrowHead()
						.fallbacks("crystal")
								.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
										.addARGB(63,  0xFF54398a)
										.addARGB(102, 0xFF6f4fab)
										.addARGB(140, 0xFF8d6acc)
										.addARGB(178, 0xFFe9b115)
										.addARGB(216, 0xFFfad64a)
										.addARGB(255, 0xFFfbf7b7)
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
//				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
//						.addARGB(63,  0xFF335160)
//						.addARGB(102, 0xFF496c84)
//						.addARGB(140, 0xFF7db8b7)
//						.addARGB(178, 0xFF99cdd9)
//						.addARGB(216, 0xFFbdf3f3)
//						.addARGB(255, 0xFFdcfffe)
//						.build()))
				.transformer(GreyToSpriteTransformer.builderFromBlack()
						.addTexture(63, ConstructsCasting.id("item/materials/generator/mithril/mithril_63"))
						.addTexture(102, ConstructsCasting.id("item/materials/generator/mithril/mithril_102"))
						.addTexture(140, ConstructsCasting.id("item/materials/generator/mithril/mithril_140"))
						.addTexture(178, ConstructsCasting.id("item/materials/generator/mithril/mithril_178"))
						.addTexture(216, ConstructsCasting.id("item/materials/generator/mithril/mithril_216"))
						.addTexture(255, ConstructsCasting.id("item/materials/generator/mithril/mithril_255"))
						.animated(ConstructsCasting.id("item/materials/generator/mithril/frames"), 8))
		;
		buildMaterial(CCMaterials.pyrium).meleeHarvest().ranged().armor().maille().repairKit().statType(CCMaterialStats.Statless.ADORNMENT)
				.fallbacks("metal")
				.transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
						.addARGB(63,  0xFF491b10)
						.addARGB(102, 0xFF733c25)
						.addARGB(140, 0xFF9c5e2c)
						.addARGB(178, 0xFFb9892e)
						.addARGB(216, 0xFFd6af39)
						.addARGB(255, 0xFFe8e895)
						.build()));

//        buildMaterial(CCMaterials.mithril).meleeHarvest().ranged().armor().maille().repairKit().statType(CCMaterialStats.Statless.ADORNMENT)
//                .fallbacks("metal")
//                .transformer(new RecolorSpriteTransformer(GreyToColorMapping.builderFromBlack()
//                        .addARGB(63,  0xFF496c84)
//                        .addARGB(102, 0xFF5b8092)
//                        .addARGB(140, 0xFF7dacaf)
//                        .addARGB(178, 0xFF99cdd9)
//                        .addARGB(216, 0xFFbdf3f3)
//                        .addARGB(255, 0xFFdcfffe)
//                        .build()));
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
