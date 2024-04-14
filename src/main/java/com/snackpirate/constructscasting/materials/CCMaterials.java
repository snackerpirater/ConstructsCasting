package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class CCMaterials extends AbstractMaterialDataProvider {

	public static final MaterialId arcanium = createMaterial("arcanium"); //more materials later i guess
	public static final MaterialId fireMetal = createMaterial("fire_metal"); //more materials later i guess
	public static final MaterialId iceMetal = createMaterial("ice_metal"); //more materials later i guess
	public static final MaterialId fulminite = createMaterial("fulminite"); //more materials later i guess
	public static final MaterialId cosmichalcum = createMaterial("cosmichalcum"); //more materials later i guess
	public static final MaterialId absolite = createMaterial("absolite"); //more materials later i guess
	public static final MaterialId necromantite = createMaterial("necromantite"); //more materials later i guess
	public static final MaterialId spectrine = createMaterial("spectrine"); //more materials later i guess
	public static final MaterialId natureMetal = createMaterial("nature_metal"); //more materials later i guess
	public static final MaterialId lovecraftium = createMaterial("lovecraftium"); //more materials later i guess
	public CCMaterials(DataGenerator gen) {
		super(gen);
	}

	private static MaterialId createMaterial(String name) {
		return new MaterialId(new ResourceLocation(ConstructsCasting.MOD_ID, name));
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
			addMaterialStats(arcanium, new HeadMaterialStats(380, 7.0f, Tiers.DIAMOND, 2.0f), new HandleMaterialStats(1.05f, 1.1f, 0.95f, 0.8f), ExtraMaterialStats.DEFAULT);
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
