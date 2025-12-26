package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EquipmentSlot;
import slimeknights.tconstruct.library.data.tinkering.AbstractMobEquipmentProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;

public class CCMobEquipment extends AbstractMobEquipmentProvider {
	public CCMobEquipment(PackOutput output, String modId) {
		super(output, modId);
	}

	@Override
	protected void addEquipment() {
		RandomMaterial random = RandomMaterial.ancient();
		equip(EntityRegistry.KEEPER.get())
				.slot(EquipmentSlot.MAINHAND)
				.tool(CCItems.flamberge)
				.material(random, random, random);
	}
	@Override
	public String getName() {
		return "Construct's Casting Mob Equipment Provider";
	}
}
