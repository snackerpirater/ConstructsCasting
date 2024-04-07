package com.snackpirate.constructscasting;

import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class CCLang extends LanguageProvider {

	public CCLang(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations() {
		addMaterial(CCMaterials.arcanium, "Arcanium", "Yer a wizard, Harry!", "Gives the wielder +50 max mana per part.");
		addModifier(CCModifiers.CASTING.getId(), "Casting", "Not for fish, unfortunately. ", "Allows tools to cast spells on right click.");
		addFluid(CCFluids.arcaneEssence, "Arcane Essence");
		addFluid(CCFluids.liquidLightning, "Lightning");
	}

	public void addMaterial(MaterialId material, String name, String flavour, String desc) {
		String id = material.getPath();
		add("material.constructs_casting." + id, name);
		if (!flavour.equals(""))
			add("material.constructs_casting." + id + ".flavor", flavour);
		if (!desc.equals(""))
			add("material.constructs_casting." + id + ".encyclopedia", desc);
	}

	public void addModifier(ModifierId modifier, String name, String flavour, String desc) {
		String id = modifier.getPath();
		add("modifier.constructs_casting." + id, name);
		add("modifier.constructs_casting." + id + ".flavor", flavour);
		add("modifier.constructs_casting." + id + ".description", desc);
	}

	public void addFluid(FluidObject<?> fluid, String name) {
		add("fluid_type.constructs_casting." + fluid.getId().getPath(), name);
		add("item.constructs_casting." + fluid.getId().getPath() + "_bucket", name + " Bucket");
	}
}
