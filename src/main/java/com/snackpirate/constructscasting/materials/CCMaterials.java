package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.linux.Stat;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import java.util.List;

public class CCMaterials extends AbstractMaterialDataProvider {

    public static final List<MaterialVariantId> tinkerClothMaterials = List.of(MaterialIds.leather, MaterialIds.slimeskin, MaterialIds.ichorskin, MaterialIds.skySlimeskin, MaterialIds.enderSlimeskin, MaterialIds.roseGold, MaterialIds.paper, MaterialIds.leaves);
    public static final List<MaterialVariantId> tinkerMagicMaterials = List.of(MaterialIds.wood, MaterialIds.nahuatl, MaterialIds.bone, MaterialIds.blazewood, MaterialIds.bamboo, MaterialIds.bone, MaterialIds.blazingBone, MaterialIds.necroticBone, MaterialIds.dragonScale);
	public static final List<MaterialVariantId> tinkerAdornMaterials = List.of(MaterialIds.glowstone, MaterialIds.amethyst, MaterialIds.quartz, MaterialIds.earthslime, MaterialIds.skyslime, MaterialIds.enderslime, MaterialIds.ichor);
    public static final MaterialId arcanium = createMaterial("arcanium"); //trait: arcane
	public static final MaterialId exilite = createMaterial("exilite"); //trait: damage to magic users? pyromancers etc. also people who are casting spells
	//armor trait: spell protection (also makes the reinforcement)
	//needs nugget/ingot/blocks, this is the stuff that makes the magehunter
	public static final MaterialId arcaneCloth = createMaterial("arcane_cloth"); //trait: mana regen, maille/binding only
	public static final MaterialId divinePearl = createMaterial("divine_pearl");
	public static final MaterialId frozenBone = createMaterial("frozen_bone");
	public static final MaterialId frostRod = createMaterial("frosted_rod");
	public static final MaterialId hogskin = createMaterial("hogskin");
//	public static final MaterialId dragonskin = createMaterial("dragonskin");
	public static final MaterialId rainbowSlime = createMaterial("rainbowslime");

	public static final MaterialId cosmichalcum = createMaterial("cosmichalcum");

//    public static final MaterialId paper = createMaterial("paper");
//	public static final MaterialId leaf = createMaterial("leaf");

//    public static final MaterialId amethyst = createMaterial("amethyst"); //CDR

//    public static final MaterialId quartz = createMaterial("quartz"); //fire
    public static final MaterialId emerald = createMaterial("emerald"); //evo
//    public static final MaterialId earthslimeCrystal = createMaterial("earthslime_crystal"); //nature
//    public static final MaterialId skyslimeCrystal = createMaterial("skyslime_crystal"); //lightning
//    public static final MaterialId enderslimeCrystal = createMaterial("enderslime_crystal"); //ender
//    public static final MaterialId ichorCrystal = createMaterial("ichor_crystal"); //blood
    public static final MaterialId permafrost = createMaterial("permafrost"); //ice
//    public static final MaterialId glowstone = createMaterial("glowstone"); //holy
    public static final MaterialId echoShard = createMaterial("echo_shard"); //eldritch

	public static final List<MaterialId> crystalMaterials = List.of(MaterialIds.amethyst, MaterialIds.quartz, emerald, MaterialIds.earthslime, MaterialIds.skyslime, MaterialIds.enderslime, MaterialIds.ichor, permafrost, MaterialIds.glowstone, echoShard);
	public CCMaterials(PackOutput gen) {
		super(gen);
	}

	private static MaterialId createMaterial(String name) {
		return new MaterialId(ConstructsCasting.id(name));
	}

	@Override
	protected void addMaterials() {
		addMaterial(frozenBone, 2, 12, true);
        addMaterial(hogskin, 2, 0, true);
		addMaterial(divinePearl, 2, 0, true);
		addMaterial(arcaneCloth, 2, 13, true);
		addMaterial(arcanium, 3, 15, false);
		addMaterial(exilite, 3, 16, false);
		addMaterial(frostRod, 3, 14, true);
		addMaterial(rainbowSlime, 3, 0, false);

//		addMaterial(cosmichalcum, 4, 10, false);
//		addMaterial(hogskin, 3, 0, true);

		addMaterial(permafrost, 2, ORDER_REPAIR + 1, true);
		addMaterial(emerald, 3, 0, true);
		addMaterial(echoShard, 4, 0, true);
	}

	@Override
	public String getName() {
		return "Construct's Casting Materials";
	}

	public static class MaterialStats extends AbstractMaterialStatsDataProvider {

		public MaterialStats(PackOutput gen, AbstractMaterialDataProvider materials) {
			super(gen, materials);
		}

		@Override
		protected void addMaterialStats() {
			addMaterialStats(arcanium,
					new HeadMaterialStats(380, 7.0f, Tiers.DIAMOND, 2.0f),
					new HandleMaterialStats(0.05f, 0.1f, -0.05f, -0.1f),
					StatlessMaterialStats.BINDING,
					//about worse than amethyst bronze
					new PlatingMaterialStats(PlatingMaterialStats.HELMET, 288, 2, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.CHESTPLATE, 428, 6, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.LEGGINGS, 400, 5, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.BOOTS, 344, 2, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.SHIELD, 484, 1, 2, 0.1f),
					StatlessMaterialStats.MAILLE,
					new LimbMaterialStats(380, 0.1f, -0.1f, 0.1f),
					new GripMaterialStats(0.05f, 0.1f, 2f)
					, CCMaterialStats.Statless.ADORNMENT
			);
			addMaterialStats(exilite,
					new HeadMaterialStats(480, 7.5f, Tiers.DIAMOND, 2.5f),
					new HandleMaterialStats(-0.05f, -0.1f, 0.1f, 0.1f),
					StatlessMaterialStats.BINDING,
					new PlatingMaterialStats(PlatingMaterialStats.HELMET, 318, 2, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.CHESTPLATE, 458, 6, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.LEGGINGS, 430, 6, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.BOOTS, 374, 2, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.SHIELD, 514, 1, 2, 0.15f),
					StatlessMaterialStats.MAILLE,
					new LimbMaterialStats(480, -0.1f, 0.1f, -0.1f),
					new GripMaterialStats(-0.05f, -0.1f, 2.5f)
//					, CCMaterialStats.Statless.ADORNMENT
			);
			//TODO: temp stats, should be t4
			addMaterialStats(cosmichalcum,
					new HeadMaterialStats(480, 7.5f, Tiers.DIAMOND, 2.5f),
					new HandleMaterialStats(-0.05f, -0.15f, 0.15f, 0.1f),
					StatlessMaterialStats.BINDING,
					new PlatingMaterialStats(PlatingMaterialStats.HELMET, 318, 2, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.CHESTPLATE, 458, 6, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.LEGGINGS, 430, 6, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.BOOTS, 374, 2, 2, 0.15f),
					new PlatingMaterialStats(PlatingMaterialStats.SHIELD, 514, 1, 2, 0.15f),
					StatlessMaterialStats.MAILLE
                    , CCMaterialStats.Statless.ADORNMENT
            );
			addMaterialStats(divinePearl,
					StatlessMaterialStats.ARROW_HEAD);
			addMaterialStats(frozenBone,
					new HeadMaterialStats(175, 4, Tiers.IRON, 2.5f),
					new HandleMaterialStats(0.1f, -0.05f, -0.1f, 0.1f),
					new MagicBaseMaterialStats(100, 0.05f),
					StatlessMaterialStats.ARROW_SHAFT,
					StatlessMaterialStats.BINDING);
			addMaterialStats(frostRod,
					new HandleMaterialStats(0.1f, -0.1f, -0.15f, 0.15f));
			addMaterialStats(arcaneCloth, StatlessMaterialStats.BINDING, StatlessMaterialStats.MAILLE, StatlessMaterialStats.BOWSTRING,
					new MagicClothMaterialStats(10, 0.05f));
            addMaterialStats(hogskin, new MagicClothMaterialStats(8, 0));
			addMaterialStats(MaterialIds.dragonScale, new MagicBaseMaterialStats(200, -0.1f));
			addMaterialStats(rainbowSlime);

            addMaterialStats(MaterialIds.paper, new MagicClothMaterialStats(8, -0.15f));
			addMaterialStats(MaterialIds.leaves, new MagicClothMaterialStats(6, 0.15f));

            //existing materials, new stats
            addMaterialStats(MaterialIds.wood, new MagicBaseMaterialStats(100, 0));
            addMaterialStats(MaterialIds.bamboo, new MagicBaseMaterialStats(100, -0.05f));
            addMaterialStats(MaterialIds.bone, new MagicBaseMaterialStats(80, 0.1f));
			addMaterialStats(MaterialIds.nahuatl, new MagicBaseMaterialStats(160, -0.1f));
            addMaterialStats(MaterialIds.chorus, new MagicBaseMaterialStats(120, 0.1f));
            addMaterialStats(MaterialIds.necroticBone, new MagicBaseMaterialStats(140, -0.05f));
            addMaterialStats(MaterialIds.blazingBone, new MagicBaseMaterialStats(200, 0f));
            addMaterialStats(MaterialIds.leather, new MagicClothMaterialStats(6, 0.1f));
//            addMaterialStats(MaterialIds.ancientHide, new MagicClothMaterialStats(8, -0.05f));
            addMaterialStats(MaterialIds.roseGold, new MagicClothMaterialStats(8, -0.1f));
            addMaterialStats(MaterialIds.ichorskin, new MagicClothMaterialStats(8, 0.05f));
            //spellbook platings
//            platingMaterials.forEach((materialId) -> addMaterialStats(materialId, CCMaterialStats.Statless.SPELLBOOK_PLATING));
			crystalMaterials.forEach((materialId) -> addMaterialStats(materialId, CCMaterialStats.Statless.ADORNMENT));
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Stats";
		}
	}
	public static class CCMaterialTraits extends AbstractMaterialTraitDataProvider {

		public CCMaterialTraits(PackOutput gen, AbstractMaterialDataProvider materials) {
			super(gen, materials);
		}

		@Override
		protected void addMaterialTraits() {
			addDefaultTraits(arcanium, CCModifiers.ARCANE);

			addTraits(cosmichalcum, MaterialRegistry.MELEE_HARVEST, CCModifiers.ENDER_UPGRADE);
			addTraits(cosmichalcum, MaterialRegistry.ARMOR, CCModifiers.ENDER_UPGRADE);

			addDefaultTraits(arcaneCloth, CCModifiers.SPELLBOUND);
			addDefaultTraits(hogskin, CCModifiers.ARCANE);
			addDefaultTraits(frozenBone, CCModifiers.ANTIFROST);
			addDefaultTraits(divinePearl, ModifierIds.holy);
			addTraits(frozenBone, StatlessMaterialStats.ARROW_SHAFT.getIdentifier(), CCModifiers.FROSTBITE);
			addTraits(MaterialIds.dragonScale, MagicBaseMaterialStats.ID, CCModifiers.DRAGONSPELLS);
			addTraits(frostRod, HandleMaterialStats.ID, new ModifierEntry(CCModifiers.ICE_UPGRADE, 1));
			addTraits(exilite, MaterialRegistry.MELEE_HARVEST, CCModifiers.ANTIMAGIC);
			addTraits(exilite, MaterialRegistry.ARMOR, CCModifiers.SPELL_PROTECTION);
            addTraits(CCMaterials.exilite, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.SPELL_DISPULSION);
//
			noTraits(rainbowSlime);
            addTraits(MaterialIds.paper, MagicClothMaterialStats.ID, CCModifiers.IMPROVEABLE);
			addTraits(MaterialIds.leaves, MagicClothMaterialStats.ID, CCModifiers.SOLAR_CHARGED);
            addDefaultTraits(hogskin, CCModifiers.THICK_SKINNED);
			addTraits(MaterialIds.leather, MagicClothMaterialStats.ID, CCModifiers.CONSERVING);
//            addTraits(MaterialIds.ancientHide, MagicClothMaterialStats.ID, ModifierIds.fortified);
            addTraits(MaterialIds.ichorskin, MagicClothMaterialStats.ID, CCModifiers.ICHORSPELLS);
            addTraits(MaterialIds.wood, MagicBaseMaterialStats.ID, CCModifiers.REGROWTH);
            addTraits(MaterialIds.bamboo, MagicBaseMaterialStats.ID, CCModifiers.EXPEDIENT);
            addTraits(MaterialIds.bone, MagicBaseMaterialStats.ID, CCModifiers.PUNCTURING);
			addTraits(MaterialIds.nahuatl, MagicBaseMaterialStats.ID, CCModifiers.GASHING);
            addTraits(MaterialIds.chorus, MagicBaseMaterialStats.ID, CCModifiers.ENDERBENDER);
            addTraits(MaterialIds.necroticBone, MagicBaseMaterialStats.ID, CCModifiers.APOPTOTIC);
            addTraits(MaterialIds.blazingBone, MagicBaseMaterialStats.ID, CCModifiers.CALORIFIC);

			addTraits(MaterialIds.amethyst, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.COOLDOWN_UPGRADE);
			addTraits(MaterialIds.quartz, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.FIRE_UPGRADE, CCModifiers.ICE_DISPULSION);
			addTraits(emerald, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.EVOCATION_UPGRADE, CCModifiers.NATURE_DISPULSION);
			addTraits(MaterialIds.earthslime, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.NATURE_UPGRADE, CCModifiers.EVOCATION_DISPULSION);
			addTraits(MaterialIds.skyslime, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.LIGHTNING_UPGRADE, CCModifiers.ENDER_DISPULSION);
			addTraits(MaterialIds.enderslime, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.ENDER_UPGRADE, CCModifiers.LIGHTNING_DISPULSION);
			addTraits(MaterialIds.ichor, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.BLOOD_UPGRADE, CCModifiers.HOLY_DISPULSION);
			addTraits(permafrost, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.ICE_UPGRADE, CCModifiers.FIRE_DISPULSION);
			addTraits(MaterialIds.glowstone, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.HOLY_UPGRADE, CCModifiers.BLOOD_DISPULSION);
			addTraits(echoShard, CCMaterialStats.Statless.ADORNMENT.getIdentifier(), CCModifiers.ELDRITCH_UPGRADE, CCModifiers.ELDRITCH_DISPULSION);
		}
//		public static final List<MaterialId> crystalMaterials = List.of( amethyst, quartz, emerald, earthslimeCrystal, skyslimeCrystal, enderslimeCrystal, ichorCrystal, blueIce, glowstone, echoShard);

		@Override
		public String getName() {
			return "Construct's Casting Material Traits";
		}
	}

	public static class CCMaterialRenderInfo extends AbstractMaterialRenderInfoProvider {

		public CCMaterialRenderInfo(PackOutput gen, @Nullable AbstractMaterialSpriteProvider materialSprites, ExistingFileHelper fileHelper) {
			super(gen, materialSprites, fileHelper);
		}

		@Override
		protected void addMaterialRenderInfo() {
			buildRenderInfo(arcanium).color(0x73abde);
			buildRenderInfo(arcaneCloth).color(0x73abde).fallbacks("cloth");
			buildRenderInfo(divinePearl).color(0xfecbe6).fallbacks("crystal");
			buildRenderInfo(hogskin).color(0xe8a074).fallbacks("cloth", "primitive");
			buildRenderInfo(exilite).color(0x47494b);
			buildRenderInfo(frozenBone).color(0xd0e5e4).fallbacks("bone", "rock");
			buildRenderInfo(rainbowSlime).color(0xFFFF00);
			buildRenderInfo(frostRod).color(0xc8ecec).fallbacks("metal", "primitive");

			buildRenderInfo(cosmichalcum).color(0x111081).fallbacks("metal");

			buildRenderInfo(permafrost).color(0xEEEEEE).fallbacks("crystal");
			buildRenderInfo(emerald).color(0xEEEEEE).fallbacks("crystal");
			buildRenderInfo(echoShard).color(0xEEEEEE).fallbacks("crystal");
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Render Info";
		}
	}
}
