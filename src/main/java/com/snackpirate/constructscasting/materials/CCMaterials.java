package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import java.util.List;

public class CCMaterials extends AbstractMaterialDataProvider {

    public static final List<MaterialVariantId> tinkerClothMaterials = List.of(MaterialIds.leather, MaterialIds.ancientHide, MaterialIds.slimeskin, MaterialIds.ichorskin, MaterialIds.skySlimeskin, MaterialIds.enderSlimeskin, MaterialIds.roseGold);
    public static final List<MaterialVariantId> tinkerMagicMaterials = List.of(MaterialIds.wood, MaterialIds.nahuatl, MaterialIds.bone, MaterialIds.blazewood, MaterialIds.bamboo, MaterialIds.bone);
    public static final List<MaterialId> platingMaterials = List.of(MaterialIds.searedStone, MaterialIds.cobalt, MaterialIds.gold, MaterialIds.iron, MaterialIds.steel, MaterialIds.pigIron, MaterialIds.copper, MaterialIds.obsidian); //basically the selection of materials for plated armor, without the overslime ones since overslime does nothing

    public static final MaterialId arcanium = createMaterial("arcanium"); //trait: arcane
	public static final MaterialId exilite = createMaterial("exilite"); //trait: damage to magic users? pyromancers etc. also people who are casting spells
	//armor trait: spell protection (also makes the reinforcement)
	//needs nugget/ingot/blocks, this is the stuff that makes the magehunter
	public static final MaterialId arcaneCloth = createMaterial("arcane_cloth"); //trait: mana regen, maille/binding only
	public static final MaterialId frozenBone = createMaterial("frozen_bone");
	public static final MaterialId frostRod = createMaterial("frosted_rod");
	public static final MaterialId hogskin = createMaterial("hogskin");
	public static final MaterialId dragonskin = createMaterial("dragonskin");
	public static final MaterialId rainbowSlime = createMaterial("rainbowslime");

	public static final MaterialId cosmichalcum = createMaterial("cosmichalcum");

    public static final MaterialId paper = createMaterial("paper");
	public static final MaterialId leaf = createMaterial("leaf");

	public CCMaterials(PackOutput gen) {
		super(gen);
	}

	private static MaterialId createMaterial(String name) {
		return new MaterialId(ConstructsCasting.id(name));
	}

	@Override
	protected void addMaterials() {
        addMaterial(paper, 1, 0, true);
		addMaterial(frozenBone, 2, 0, true);
		addMaterial(arcaneCloth, 2, 0, true);
		addMaterial(leaf,2,0,true);
		addMaterial(arcanium, 3, 0, false);
		addMaterial(exilite, 3, 0, false);
		addMaterial(frostRod, 3, 0, true);
		addMaterial(rainbowSlime, 3, 0, false);

//		addMaterial(cosmichalcum, 4, 10, false);
//		addMaterial(hogskin, 3, 0, true);
//		addMaterial(dragonskin, 4, 0, true);
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
                    CCMaterialStats.Statless.SPELLBOOK_PLATING);
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
                    CCMaterialStats.Statless.SPELLBOOK_PLATING);
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
					StatlessMaterialStats.MAILLE,
                    CCMaterialStats.Statless.SPELLBOOK_PLATING);

			addMaterialStats(frozenBone,
					new HeadMaterialStats(175, 4, Tiers.IRON, 2.5f),
					new HandleMaterialStats(0.1f, -0.05f, -0.1f, 0.1f),
					StatlessMaterialStats.BINDING);
			addMaterialStats(frostRod,
					new HandleMaterialStats(0.1f, -0.1f, -0.15f, 0.15f));
			addMaterialStats(arcaneCloth, StatlessMaterialStats.BINDING, StatlessMaterialStats.MAILLE, StatlessMaterialStats.BOWSTRING,
					new MagicClothMaterialStats(10, 0.05f));
			addMaterialStats(hogskin, StatlessMaterialStats.BINDING, StatlessMaterialStats.MAILLE, StatlessMaterialStats.BOWSTRING);
			addMaterialStats(dragonskin, StatlessMaterialStats.BINDING, StatlessMaterialStats.MAILLE, StatlessMaterialStats.BOWSTRING);
			addMaterialStats(rainbowSlime);

            addMaterialStats(paper, new MagicClothMaterialStats(8, -0.15f));
			addMaterialStats(leaf, new MagicClothMaterialStats(6, 0.15f));

            //existing materials, new stats
            addMaterialStats(MaterialIds.wood, new MagicBaseMaterialStats(100, 0));
            addMaterialStats(MaterialIds.bamboo, new MagicBaseMaterialStats(100, -0.05f));
			addMaterialStats(MaterialIds.leather, new MagicClothMaterialStats(6, 0.1f));
            addMaterialStats(MaterialIds.ancientHide, new MagicClothMaterialStats(8, -0.1f));
            addMaterialStats(MaterialIds.roseGold, new MagicClothMaterialStats(8, -0.1f));
            //spellbook platings
            platingMaterials.forEach((materialId) -> addMaterialStats(materialId, CCMaterialStats.Statless.SPELLBOOK_PLATING));
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
			addDefaultTraits(dragonskin, CCModifiers.ENDER_UPGRADE);
			addTraits(frostRod, HandleMaterialStats.ID, new ModifierEntry(CCModifiers.ICE_UPGRADE, 1));
			addTraits(exilite, MaterialRegistry.MELEE_HARVEST, CCModifiers.ANTIMAGIC);
			addTraits(exilite, MaterialRegistry.ARMOR, CCModifiers.SPELL_PROTECTION);
			noTraits(rainbowSlime);
            addDefaultTraits(paper, CCModifiers.IMPROVEABLE);
			addDefaultTraits(leaf, CCModifiers.SOLAR_CHARGED);
			addTraits(MaterialIds.leather, MagicClothMaterialStats.ID, CCModifiers.CONSERVING);
            addTraits(MaterialIds.ancientHide, MagicClothMaterialStats.ID, ModifierIds.fortified);
            addTraits(MaterialIds.wood, MagicBaseMaterialStats.ID, CCModifiers.REGROWTH);
            addTraits(MaterialIds.bamboo, MagicBaseMaterialStats.ID, CCModifiers.EXPEDIENT);
		}

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
			buildRenderInfo(hogskin).color(0xFF0000).fallbacks("cloth", "primitive");
			buildRenderInfo(exilite).color(0x47494b);
			buildRenderInfo(frozenBone).color(0xd0e5e4).fallbacks("bone", "rock");
			buildRenderInfo(rainbowSlime).color(0xFFFF00);
			buildRenderInfo(frostRod).color(0xc8ecec).fallbacks("metal", "primitive");
			buildRenderInfo(leaf).color(0x48B518).fallbacks("vine");

			buildRenderInfo(cosmichalcum).color(0x111081).fallbacks("metal");

            buildRenderInfo(paper).color(0xEEEEEE).fallbacks("cloth");
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Render Info";
		}
	}
}
