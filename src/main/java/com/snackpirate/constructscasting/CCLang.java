package com.snackpirate.constructscasting;

import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
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

		addModifier(CCModifiers.CASTING.getId(), "Casting", "Not for fish, unfortunately. ", "Allows the tool to cast spells on right click.");
		addModifier(CCModifiers.ARCANE, "Arcane" ,"Mana-rific!", "Grants +25 max mana.");
		addFluid(CCFluids.arcaneEssence, "Arcane Essence");
		addFluid(CCFluids.fireEssence, "Fire Essence");
		addFluid(CCFluids.iceEssence, "Ice Essence");
		addFluid(CCFluids.lightningEssence, "Lightning Essence");
		addFluid(CCFluids.enderEssence, "Ender Essence");
		addFluid(CCFluids.holyEssence, "Holy Essence");
		addFluid(CCFluids.bloodEssence, "Blood Essence");
		addFluid(CCFluids.evocationEssence, "Evocation Essence");
		addFluid(CCFluids.natureEssence, "Nature Essence");


		addFluid(CCFluids.liquidLightning, "Lightning");
		addFluid(CCFluids.liquidDivinity, "Divinity");
		addFluid(CCFluids.potatoStew, "Potato Stew");
		addFluid(CCFluids.poisonousPotatoStew, "Poisonous Potato Stew");
		addFluid(CCFluids.moltenArcanium, "Molten Arcanium");

		addItem(CCItems.potatoStewBowl, "Potato Stew");
		addItem(CCItems.poisonousPotatoStewBowl, "Poisonous Potato Stew");
	}

	public void addMaterial(MaterialId material, String name, String flavour, String desc) {
		String id = material.getPath();
		add("material.constructs_casting." + id, name);
		if (!flavour.isEmpty())
			add("material.constructs_casting." + id + ".flavor", flavour);
		if (!desc.isEmpty())
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
