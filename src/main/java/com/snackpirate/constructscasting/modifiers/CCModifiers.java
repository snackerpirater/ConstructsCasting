package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.CCDamageTypes;
import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluidEffects;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCToolStats;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.data.predicate.damage.DamageSourcePredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;
import slimeknights.tconstruct.library.json.LevelingInt;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackPredicate;
import slimeknights.tconstruct.library.json.variable.entity.EntityVariable;
import slimeknights.tconstruct.library.json.variable.stat.EntityConditionalStatVariable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ConditionalStatModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierSlotModule;
import slimeknights.tconstruct.library.modifiers.modules.build.SetStatModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.shared.TinkerEffects;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.library.json.math.ModifierFormula.MULTIPLIER;
import static slimeknights.tconstruct.library.json.math.ModifierFormula.VALUE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ConstructsCasting.MOD_ID)
public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);
	public static final StaticModifier<Modifier> SPELLBLADE = MODIFIERS.register("spellblade", SpellbladeModifier::new);
	public static final StaticModifier<Modifier> ANTIMAGIC = MODIFIERS.register("antimagic", AntimagicModifier::new);
	public static final StaticModifier<Modifier> IMBUED = MODIFIERS.register("imbued", ImbuedModifier::new);
	public static final StaticModifier<Modifier> ENCYCLOPEDIC = MODIFIERS.register("encyclopedic", EncyclopedicModifier::new);
//	public static final StaticModifier<Modifier> ANTIFROST = MODIFIERS.register("antifrost", AntifrostModifier::new);
	public static final StaticModifier<Modifier> SPELLBOOK_STRAP = MODIFIERS.register("spellbook_strap", SpellbookStrapModifier::new);
	public static final StaticModifier<Modifier> CONSERVING = MODIFIERS.register("conserving", ConservingModifier::new);
	public static final StaticModifier<Modifier> SOLAR_CHARGED = MODIFIERS.register("solar_charged", SolarChargedModifier::new);
//	public static final StaticModifier<Modifier> SPELL_SLOTS = MODIFIERS.register("spell_slots", SpellSlotsModifier::new);
    public static final StaticModifier<Modifier> DRAGONSPELLS = MODIFIERS.register("dragonspells", DragonspellsModifier::new);
	public static final StaticModifier<Modifier> GASHING = MODIFIERS.register("gashing", GashingModifier::new);
    public static final StaticModifier<Modifier> PUNCTURING = MODIFIERS.register("puncturing", PuncturingModifier::new);
    public static final StaticModifier<Modifier> ENDERBENDER = MODIFIERS.register("enderbender", EnderbenderModifier::new);
    public static final StaticModifier<Modifier> APOPTOTIC = MODIFIERS.register("apoptotic", ApoptoticModifier::new);
    public static final StaticModifier<Modifier> CALORIFIC = MODIFIERS.register("calorific", CalorificModifier::new);
//	public static final StaticModifier<RingbearerModifier> RINGBEARER = MODIFIERS.register("ringbearer", RingbearerModifier::new);
    public static final ModifierId ARCANE = new ModifierId(ConstructsCasting.MOD_ID, "arcane");

	public static final ModifierId SWIFTCASTING = new ModifierId(ConstructsCasting.MOD_ID, "swiftcasting");
	public static final ModifierId SPELLBOUND = new ModifierId(ConstructsCasting.MOD_ID, "spellbound");
    public static final ModifierId SPELL_PROTECTION = new ModifierId(ConstructsCasting.MOD_ID, "spell_protection");
	public static final ModifierId ANTIFROST = new ModifierId(ConstructsCasting.MOD_ID, "antifrost");
//  for some reason, the module-based approach does not work due to something weird with the serializer, so we're hardcoding this
//	public static final ModifierId SPELLBOOK_STRAP = new ModifierId(ConstructsCasting.MOD_ID, "spellbook_strap");
	//orb upgrades
	public static final ModifierId MANA_UPGRADE      = new ModifierId(ConstructsCasting.MOD_ID, "mana_upgrade");
	public static final ModifierId FIRE_UPGRADE      = new ModifierId(ConstructsCasting.MOD_ID, "fire_upgrade");
	public static final ModifierId ICE_UPGRADE       = new ModifierId(ConstructsCasting.MOD_ID, "ice_upgrade");
	public static final ModifierId LIGHTNING_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "lightning_upgrade");
	public static final ModifierId ENDER_UPGRADE     = new ModifierId(ConstructsCasting.MOD_ID, "ender_upgrade");
	public static final ModifierId HOLY_UPGRADE      = new ModifierId(ConstructsCasting.MOD_ID, "holy_upgrade");
	public static final ModifierId BLOOD_UPGRADE     = new ModifierId(ConstructsCasting.MOD_ID, "blood_upgrade");
	public static final ModifierId EVOCATION_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "evocation_upgrade");
	public static final ModifierId NATURE_UPGRADE    = new ModifierId(ConstructsCasting.MOD_ID, "nature_upgrade");
	public static final ModifierId COOLDOWN_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "cooldown_upgrade");
	public static final ModifierId ELDRITCH_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "eldritch_upgrade");
	
	public static final ModifierId SPELL_DISPULSION     = new ModifierId(ConstructsCasting.MOD_ID, "spell_dispulsion");
	public static final ModifierId FIRE_DISPULSION      = new ModifierId(ConstructsCasting.MOD_ID, "fire_dispulsion");
	public static final ModifierId ICE_DISPULSION       = new ModifierId(ConstructsCasting.MOD_ID, "ice_dispulsion");
	public static final ModifierId LIGHTNING_DISPULSION = new ModifierId(ConstructsCasting.MOD_ID, "lightning_dispulsion");
	public static final ModifierId ENDER_DISPULSION     = new ModifierId(ConstructsCasting.MOD_ID, "ender_dispulsion");
	public static final ModifierId HOLY_DISPULSION      = new ModifierId(ConstructsCasting.MOD_ID, "holy_dispulsion");
	public static final ModifierId BLOOD_DISPULSION     = new ModifierId(ConstructsCasting.MOD_ID, "blood_dispulsion");
	public static final ModifierId EVOCATION_DISPULSION = new ModifierId(ConstructsCasting.MOD_ID, "evocation_dispulsion");
	public static final ModifierId NATURE_DISPULSION    = new ModifierId(ConstructsCasting.MOD_ID, "nature_dispulsion");
	public static final ModifierId ELDRITCH_DISPULSION  = new ModifierId(ConstructsCasting.MOD_ID, "eldritch_dispulsion");

	public static final ModifierId ABYSSAL_UPGRADE      = new ModifierId(ConstructsCasting.MOD_ID, "abyssal_upgrade");
	public static final ModifierId TECHNOMANCY_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "technomancy_upgrade");
	public static final ModifierId AQUA_UPGRADE         = new ModifierId(ConstructsCasting.MOD_ID, "aqua_upgrade");

    public static final SlotType AFFINITY_SLOT = SlotType.getOrCreate("affinity");
	//paper trait: lets you apply orb upgrades to level 4
    public static final ModifierId IMPROVEABLE = new ModifierId(ConstructsCasting.MOD_ID, "improvable");
	//wood magic trait: 10% mana regen
	public static final ModifierId REGROWTH = new ModifierId(ConstructsCasting.MOD_ID, "regrowth");
    public static final ModifierId THICK_SKINNED = new ModifierId(ConstructsCasting.MOD_ID, "thick_skinned");
    public static final ModifierId EXPEDIENT = new ModifierId(ConstructsCasting.MOD_ID, "expedient");
    public static final ModifierId ICHORSPELLS = new ModifierId(ConstructsCasting.MOD_ID, "ichorspells");
	public static final ModifierId RINGBEARER = new ModifierId(ConstructsCasting.MOD_ID, "ringbearer");
    public static final ModifierId SLOT_IMPROVEMENT = new ModifierId(ConstructsCasting.MOD_ID, "slot_improvement");

	public static final ModifierId FROSTBITE = new ModifierId(ConstructsCasting.MOD_ID, "frostbite");

	public CCModifiers(PackOutput generator) {
		super(generator);
	}

	@Override
	protected void addModifiers() {
		buildModifier(ARCANE).levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCItems.Tags.MAGIC_TOOL).eachLevel(50f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.or(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL), ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).inverted()).eachLevel(25f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).eachLevel(50f))
				.build();

		buildModifier(SWIFTCASTING).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
                .addModule(SetStatModule.set(ToolStats.USE_ITEM_SPEED).value(1.8f))
				.addModule(ModifierRequirementsModule.builder().requireModifier(CASTING.getId(), 1).translationKey("constructs_casting.modifier.swiftcasting.requirement").build())
				.build();

		buildModifier(SPELLBOUND)
                .addModule(AttributeModule.builder(AttributeRegistry.SPELL_POWER.get(), AttributeModifier.Operation.MULTIPLY_BASE).tool(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL).inverted()).uniqueFrom(SPELLBOUND).eachLevel(0.05f))
                .addModule(StatBoostModule.add(CCToolStats.SPELL_POWER).toolTag(CCItems.Tags.MAGIC_TOOL).eachLevel(0.05f)).build();
		buildModifier(ANTIFROST).addModule(ConditionalMeleeDamageModule.builder().target(LivingEntityPredicate.IS_FREEZING).eachLevel(2.0f));
		buildModifier(MANA_UPGRADE)     .levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCItems.Tags.MOD_SPELLBOOKS).eachLevel(80f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.tag(CCItems.Tags.MOD_SPELLBOOKS).inverted()).eachLevel(80f))
				.build();
		buildModifier(COOLDOWN_UPGRADE) .levelDisplay(ModifierLevelDisplay.DEFAULT)
                .addModule(AttributeModule.builder(AttributeRegistry.COOLDOWN_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE).tool(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL).inverted()).uniqueFrom(COOLDOWN_UPGRADE).eachLevel(0.05f))
                .addModule(StatBoostModule.add(CCToolStats.COOLDOWN_REDUCTION).toolTag(CCItems.Tags.MAGIC_TOOL).eachLevel(0.05f)).build();
		buildModifier(FIRE_UPGRADE)     .addModule(spellPowerModifier(FIRE_UPGRADE,      AttributeRegistry.FIRE_SPELL_POWER     .get())).build();
		buildModifier(ICE_UPGRADE)      .addModule(spellPowerModifier(ICE_UPGRADE,       AttributeRegistry.ICE_SPELL_POWER      .get())).build();
		buildModifier(LIGHTNING_UPGRADE).addModule(spellPowerModifier(LIGHTNING_UPGRADE, AttributeRegistry.LIGHTNING_SPELL_POWER.get())).build();
		buildModifier(ENDER_UPGRADE)    .addModule(spellPowerModifier(ENDER_UPGRADE,     AttributeRegistry.ENDER_SPELL_POWER    .get())).build();
		buildModifier(HOLY_UPGRADE)     .addModule(spellPowerModifier(HOLY_UPGRADE,      AttributeRegistry.HOLY_SPELL_POWER     .get())).build();
		buildModifier(BLOOD_UPGRADE)    .addModule(spellPowerModifier(BLOOD_UPGRADE,     AttributeRegistry.BLOOD_SPELL_POWER    .get())).build();
		buildModifier(EVOCATION_UPGRADE).addModule(spellPowerModifier(EVOCATION_UPGRADE, AttributeRegistry.EVOCATION_SPELL_POWER.get())).build();
		buildModifier(NATURE_UPGRADE)   .addModule(spellPowerModifier(NATURE_UPGRADE,    AttributeRegistry.NATURE_SPELL_POWER   .get())).build();
		buildModifier(ELDRITCH_UPGRADE) .addModule(spellPowerModifier(ELDRITCH_UPGRADE,  AttributeRegistry.ELDRITCH_SPELL_POWER .get())).build();

		buildModifier(SPELL_DISPULSION).addModule(AttributeModule.builder(AttributeRegistry.SPELL_RESIST, AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(SPELL_DISPULSION).eachLevel(0.075f));
		buildModifier(FIRE_DISPULSION).addModule(spellDispulsionModifier(FIRE_DISPULSION, AttributeRegistry.FIRE_MAGIC_RESIST.get())).build();
		buildModifier(ICE_DISPULSION).addModule(spellDispulsionModifier(ICE_DISPULSION, AttributeRegistry.ICE_MAGIC_RESIST.get())).build();
		buildModifier(LIGHTNING_DISPULSION).addModule(spellDispulsionModifier(LIGHTNING_DISPULSION, AttributeRegistry.LIGHTNING_MAGIC_RESIST.get())).build();
		buildModifier(ENDER_DISPULSION).addModule(spellDispulsionModifier(ENDER_DISPULSION, AttributeRegistry.ENDER_MAGIC_RESIST.get())).build();
		buildModifier(HOLY_DISPULSION).addModule(spellDispulsionModifier(HOLY_DISPULSION, AttributeRegistry.HOLY_MAGIC_RESIST.get())).build();
		buildModifier(BLOOD_DISPULSION).addModule(spellDispulsionModifier(BLOOD_DISPULSION, AttributeRegistry.BLOOD_MAGIC_RESIST.get())).build();
		buildModifier(EVOCATION_DISPULSION).addModule(spellDispulsionModifier(EVOCATION_DISPULSION, AttributeRegistry.EVOCATION_MAGIC_RESIST.get())).build();
		buildModifier(NATURE_DISPULSION).addModule(spellDispulsionModifier(NATURE_DISPULSION, AttributeRegistry.NATURE_MAGIC_RESIST.get())).build();
		buildModifier(ELDRITCH_DISPULSION).addModule(spellDispulsionModifier(ELDRITCH_DISPULSION, AttributeRegistry.ELDRITCH_MAGIC_RESIST.get())).build();


		buildModifier(SPELL_PROTECTION).addModule(ProtectionModule.builder().source(DamageSourcePredicate.tag(CCDamageTypes.Tags.SPELL_BASED)).eachLevel(2.5f)).build();
//		buildModifier(SPELLBOOK_STRAP).priority(95)
//				.addModule(InventoryModule.builder().pattern(new Pattern("constructs_casting:spellbook_plus")).slotsPerLevel(1))
//				.addModule(new SpellbookStrapModule(TooltipKey.NORMAL))
//				.addModule(InventoryMenuModule.SHIFT)
//				.addModule(new VolatileFlagModule(ToolInventoryCapability.INCLUDE_OFFHAND));
		buildModifier(IMPROVEABLE).addModule(ModifierSlotModule.slot(AFFINITY_SLOT).eachLevel(2)).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL).build();
		buildModifier(RINGBEARER).addModule(new BonusCurioSlotModule("ring", new LevelingInt(0, 2), "64d62ea-03d8-4919-9ba5-fec06d332c72"));
		buildModifier(REGROWTH).addModule(AttributeModule.builder(AttributeRegistry.MANA_REGEN, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.1f)).levelDisplay(ModifierLevelDisplay.DEFAULT).build();
	    buildModifier(EXPEDIENT).addModule(AttributeModule.builder(AttributeRegistry.CAST_TIME_REDUCTION, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.1f)).levelDisplay(ModifierLevelDisplay.DEFAULT).build();
        buildModifier(THICK_SKINNED)
                .levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
                .addModule(ConditionalStatModule.stat(CCToolStats.SPELL_POWER)
                        .formula()
                        .customVariable("temperature", new EntityConditionalStatVariable(EntityVariable.BIOME_TEMPERATURE, 2.0f)) //temp range: -0.5, 2
                        .constant(0.75f).subtract() // range is now -1.25 to 1.25
                        .constant(0.08f).multiply() // move range to be -0.1 to 0.1, bit more reasonable power ranges
                        .variable(MULTIPLIER).multiply()
                        .constant(0).max() //debuffs could be interesting but eh
                        .variable(VALUE).add()
                        .build())
                .addModule(ConditionalStatModule.stat(CCToolStats.COOLDOWN_REDUCTION)
                        .formula()
                        .customVariable("temperature", new EntityConditionalStatVariable(EntityVariable.BIOME_TEMPERATURE, 2.0f))
                        .constant(0.75f).subtract() // range is now -1.25 to 1.25
                        .constant(-0.25f).multiply()// move range to be 0.2 to -0.2,
                        .variable(MULTIPLIER).multiply()
                        .constant(0).max()
                        .variable(VALUE).add()
                        .build());
        buildModifier(ICHORSPELLS)
                .addModule(AttributeModule.builder(AttributeRegistry.MANA_REGEN, AttributeModifier.Operation.MULTIPLY_TOTAL).eachLevel(-0.3f))
                .addModule(StatBoostModule.add(CCToolStats.SPELL_POWER).eachLevel(0.15f))
                .levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
                .build();
        buildModifier(SLOT_IMPROVEMENT)
                .addModule(StatBoostModule.add(CCToolStats.SPELL_SLOTS).eachLevel(1f))
                .levelDisplay(ModifierLevelDisplay.DEFAULT)
                .build();
		buildModifier(FROSTBITE).priority(150).addModule(MobEffectModule.builder(CCFluidEffects.MobEffects.frostbite).time(RandomLevelingValue.random(5 * 20, 5 * 20)).chance(LevelingValue.flat(0.15f)).build());


	}
	private static AttributeModule spellPowerModifier(ModifierId modifier, Attribute attribute) {
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(modifier).eachLevel(0.05f);
	}

	private static AttributeModule spellDispulsionModifier(ModifierId modifier, Attribute attribute) {
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(modifier).eachLevel(0.15f);
	}
	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
	public static class Tags extends AbstractModifierTagProvider {

        public static final TagKey<Modifier> CASTING_MODIFIER = ModifierManager.getTag(ConstructsCasting.id("casting_modifier"));

		public Tags(PackOutput packOutput, String modId, ExistingFileHelper existingFileHelper) {
			super(packOutput, modId, existingFileHelper);
		}

		@Override
		protected void addTags() {
            tag(CASTING_MODIFIER).add(CASTING.getId());
			tag(TinkerTags.Modifiers.DUAL_INTERACTION).add(CASTING.getId());
			tag(TinkerTags.Modifiers.GENERAL_UPGRADES).add(MANA_UPGRADE, COOLDOWN_UPGRADE, FIRE_UPGRADE, ICE_UPGRADE, LIGHTNING_UPGRADE, ENDER_UPGRADE, HOLY_UPGRADE, BLOOD_UPGRADE, NATURE_UPGRADE, ELDRITCH_UPGRADE, TECHNOMANCY_UPGRADE, ABYSSAL_UPGRADE, EXPEDIENT);
			tag(TinkerTags.Modifiers.PROTECTION_DEFENSE).add(SPELL_PROTECTION);
            tag(TinkerTags.Modifiers.GENERAL_ABILITIES).add(IMPROVEABLE);
			tag(TinkerTags.Modifiers.INTERACTION_ABILITIES).add(CASTING.getId()).add(SWIFTCASTING);
			tag(TinkerTags.Modifiers.LEGGING_ABILITIES).add(SPELLBOOK_STRAP.getId());
			tag(TinkerTags.Modifiers.MELEE_ABILITIES).add(SPELLBLADE.getId());
            tag(TinkerTags.Modifiers.CHESTPLATE_ABILITIES).add(RINGBEARER);
		}

		@Override
		public String getName() {
			return "Construct's Casting Modifier Tags";
		}
	}

//	@SubscribeEvent
//	void registerSerializers(RegisterEvent event) {
//		ConstructsCasting.LOGGER.info("register event");
//		if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
//			ConstructsCasting.LOGGER.info("register serializer event");
//			ModifierModule.LOADER.register(ConstructsCasting.id("spellbook_strap"), SpellbookStrapModule.LOADER);
//			ModifierModule.LOADER.register(ConstructsCasting.id("bonus_curio_slots"), BonusCurioSlotModule.LOADER);
//		}
//	}
}
