package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);
	public CCModifiers(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addModifiers() {

	}

	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
}
