package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class CCMaterials extends AbstractMaterialDataProvider {

	public static final MaterialId arcanium = createMaterial("arcanium");
	public CCMaterials(DataGenerator gen) {
		super(gen);
	}

	private static MaterialId createMaterial(String name) {
		return new MaterialId(new ResourceLocation(ConstructsCasting.MOD_ID, name));
	}

	@Override
	protected void addMaterials() {

	}

	@Override
	public String getName() {
		return "Construct's Casting Materials";
	}
}
