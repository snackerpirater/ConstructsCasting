package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);

	public static final ModifierId ARCANE = new ModifierId(ConstructsCasting.MOD_ID, "arcane");
	public CCModifiers(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addModifiers() {
		buildModifier(ARCANE).levelDisplay(ModifierLevelDisplay.DEFAULT).addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).uniqueFrom(ARCANE).eachLevel(25f)).build();
	}

	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
}
