package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModuleCondition;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.modifiers.modules.build.SetStatModule;
import slimeknights.tconstruct.library.modifiers.modules.build.VolatileFlagModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);

	public static final ModifierId ARCANE = new ModifierId(ConstructsCasting.MOD_ID, "arcane");

	public static final ModifierId SWIFTCASTING = new ModifierId(ConstructsCasting.MOD_ID, "swiftcasting");

	//orb upgrades
	public static final ModifierId MANA_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "mana_upgrade");
	public static final ModifierId FIRE_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "fire_upgrade");
	public static final ModifierId ICE_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "ice_upgrade");
	public static final ModifierId LIGHTNING_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "lightning_upgrade");
	public static final ModifierId ENDER_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "ender_upgrade");
	public static final ModifierId HOLY_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "holy_upgrade");
	public static final ModifierId BLOOD_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "blood_upgrade");
	public static final ModifierId EVOCATION_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "evocation_upgrade");
	public static final ModifierId NATURE_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "nature_upgrade");
	public static final ModifierId COOLDOWN_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "cooldown_upgrade");
	public CCModifiers(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addModifiers() {
		buildModifier(ARCANE).levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION)
						.uniqueFrom(ARCANE)
						.eachLevel(25f))
				.build();

		buildModifier(SWIFTCASTING).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
				.addModule(new SetStatModule<>(ToolStats.USE_ITEM_SPEED, 1.8f, ModifierModuleCondition.ANY))
				.addModule(ModifierRequirementsModule.builder().requireModifier(CASTING.getId(), 1).translationKey("constructs_casting.modifier.swiftcasting.requirement").build())
				.build();

		buildModifier(MANA_UPGRADE)     .levelDisplay(ModifierLevelDisplay.DEFAULT).addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).uniqueFrom(MANA_UPGRADE).eachLevel(80f)).build();
		buildModifier(COOLDOWN_UPGRADE) .levelDisplay(ModifierLevelDisplay.DEFAULT).addModule(AttributeModule.builder(AttributeRegistry.COOLDOWN_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(COOLDOWN_UPGRADE).eachLevel(0.08f)).build();

		buildModifier(FIRE_UPGRADE)     .addModule(spellPowerModifier(FIRE_UPGRADE,      AttributeRegistry.FIRE_SPELL_POWER     .get())).build();
		buildModifier(ICE_UPGRADE)      .addModule(spellPowerModifier(ICE_UPGRADE,       AttributeRegistry.ICE_SPELL_POWER      .get())).build();
		buildModifier(LIGHTNING_UPGRADE).addModule(spellPowerModifier(LIGHTNING_UPGRADE, AttributeRegistry.LIGHTNING_SPELL_POWER.get())).build();
		buildModifier(ENDER_UPGRADE)    .addModule(spellPowerModifier(ENDER_UPGRADE,     AttributeRegistry.ENDER_SPELL_POWER    .get())).build();
		buildModifier(HOLY_UPGRADE)     .addModule(spellPowerModifier(HOLY_UPGRADE,      AttributeRegistry.HOLY_SPELL_POWER     .get())).build();
		buildModifier(BLOOD_UPGRADE)    .addModule(spellPowerModifier(BLOOD_UPGRADE,     AttributeRegistry.BLOOD_SPELL_POWER    .get())).build();
		buildModifier(EVOCATION_UPGRADE).addModule(spellPowerModifier(EVOCATION_UPGRADE, AttributeRegistry.EVOCATION_SPELL_POWER.get())).build();
		buildModifier(NATURE_UPGRADE)   .addModule(spellPowerModifier(NATURE_UPGRADE,    AttributeRegistry.NATURE_SPELL_POWER   .get())).build();

	}
	private static AttributeModule spellPowerModifier(ModifierId modifier, Attribute attribute) {
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(modifier).eachLevel(0.05f);
	}

	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
}
