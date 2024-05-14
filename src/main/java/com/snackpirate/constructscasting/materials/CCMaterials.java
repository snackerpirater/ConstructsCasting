package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class CCMaterials extends AbstractMaterialDataProvider {

	public static final MaterialId arcanium = createMaterial("arcanium"); //trait: arcane
	public static final MaterialId ardite = createMaterial("ardite"); //trait: infernal / fiery?
	public static final MaterialId kryosteel = createMaterial("kryosteel"); //trait: cryonic / freezing?
	public static final MaterialId fulminite = createMaterial("fulminite"); //trait: fulminous / chance to lightning?
	public static final MaterialId cosmichalcum = createMaterial("cosmichalcum"); //trait: cosmic / enderference?
	public static final MaterialId sanctinium = createMaterial("sanctinium"); //trait: divine  / smite?
	public static final MaterialId necromantium = createMaterial("necromantium"); //trait: sanguine / the wither bone one
	public static final MaterialId spectrine = createMaterial("spectrine"); //trait: evocative / killager?
	public static final MaterialId phygentum = createMaterial("phygentum"); //trait: blighted /
	public static final MaterialId abominite = createMaterial("abominite"); //trait: lovecraftian /
	public CCMaterials(DataGenerator gen) {
		super(gen);
	}

	private static MaterialId createMaterial(String name) {
		return new MaterialId(ConstructsCasting.id(name));
	}

	@Override
	protected void addMaterials() {
		addMaterial(arcanium, 3, 0, false);
	}

	@Override
	public String getName() {
		return "Construct's Casting Materials";
	}

	public static class CCMaterialStats extends AbstractMaterialStatsDataProvider {

		public CCMaterialStats(DataGenerator gen, AbstractMaterialDataProvider materials) {
			super(gen, materials);
		}

		@Override
		protected void addMaterialStats() {
			addMaterialStats(arcanium,
					new HeadMaterialStats(380, 7.0f, Tiers.DIAMOND, 2.0f),
					new HandleMaterialStats(1.05f, 1.1f, 0.95f, 0.8f),
					StatlessMaterialStats.BINDING,
					StatlessMaterialStats.MAILLE,
					new PlatingMaterialStats(PlatingMaterialStats.HELMET, 288, 2, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.CHESTPLATE, 428, 6, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.LEGGINGS, 400, 5, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.BOOTS, 344, 2, 2, 0.1f),
					new PlatingMaterialStats(PlatingMaterialStats.SHIELD, 484, 1, 2, 0.1f));
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Stats";
		}
	}
	public static class CCMaterialTraits extends AbstractMaterialTraitDataProvider {

		public CCMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materials) {
			super(gen, materials);
		}

		@Override
		protected void addMaterialTraits() {
			addDefaultTraits(arcanium, CCModifiers.ARCANE);
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Traits";
		}
	}

	public static class CCMaterialRenderInfo extends AbstractMaterialRenderInfoProvider {

		public CCMaterialRenderInfo(DataGenerator gen, @Nullable AbstractMaterialSpriteProvider materialSprites) {
			super(gen, materialSprites);
		}

		@Override
		protected void addMaterialRenderInfo() {
			buildRenderInfo(arcanium).color(0xFF0000);
		}

		@Override
		public String getName() {
			return "Construct's Casting Material Render Info";
		}
	}
}
