package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.CCDamageTypes;
import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.materials.CCToolStats;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.data.predicate.damage.DamageSourcePredicate;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackPredicate;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierSlotModule;
import slimeknights.tconstruct.library.modifiers.modules.build.SetStatModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ConstructsCasting.MOD_ID)
public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);
	public static final StaticModifier<Modifier> ANTIMAGIC = MODIFIERS.register("antimagic", AntimagicModifier::new);
	public static final StaticModifier<Modifier> IMBUED = MODIFIERS.register("imbued", ImbuedModifier::new);
	public static final StaticModifier<Modifier> ENCYCLOPEDIC = MODIFIERS.register("encyclopedic", EncyclopedicModifier::new);
	public static final StaticModifier<Modifier> ANTIFROST = MODIFIERS.register("antifrost", AntifrostModifier::new);
	public static final StaticModifier<Modifier> SPELLBOOK_STRAP = MODIFIERS.register("spellbook_strap", SpellbookStrapModifier::new);
	public static final StaticModifier<Modifier> CONSERVING = MODIFIERS.register("conserving", ConservingModifier::new);
	public static final StaticModifier<Modifier> SOLAR_CHARGED = MODIFIERS.register("solar_charged", SolarChargedModifier::new);


	public static final ModifierId ARCANE = new ModifierId(ConstructsCasting.MOD_ID, "arcane");

	public static final ModifierId SWIFTCASTING = new ModifierId(ConstructsCasting.MOD_ID, "swiftcasting");
	public static final ModifierId SPELLBOUND = new ModifierId(ConstructsCasting.MOD_ID, "spellbound");
    public static final ModifierId SPELL_PROTECTION = new ModifierId(ConstructsCasting.MOD_ID, "spell_protection");
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

	public static final ModifierId ABYSSAL_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "abyssal_upgrade");
	public static final ModifierId TECHNOMANCY_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "technomancy_upgrade");
	public static final ModifierId AQUA_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "aqua_upgrade");

    public static final SlotType AFFINITY_SLOT = SlotType.getOrCreate("affinity");
	//paper trait: lets you apply orb upgrades to level 4
    public static final ModifierId IMPROVEABLE = new ModifierId(ConstructsCasting.MOD_ID, "blank");
	//wood magic trait: 10% mana regen
	public static final ModifierId REGROWTH = new ModifierId(ConstructsCasting.MOD_ID, "regrowth");

    public static final ModifierId EXPEDIENT = new ModifierId(ConstructsCasting.MOD_ID, "expedient");

	public CCModifiers(PackOutput generator) {
		super(generator);
	}

	@Override
	protected void addModifiers() {
		buildModifier(ARCANE).levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCToolStats.MAGIC_TOOL).eachLevel(50f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.or(ToolStackPredicate.tag(CCToolStats.MAGIC_TOOL), ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).inverted()).eachLevel(25f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).eachLevel(50f))
				.build();

		buildModifier(SWIFTCASTING).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
                .addModule(SetStatModule.set(ToolStats.USE_ITEM_SPEED).value(1.8f))
				.addModule(ModifierRequirementsModule.builder().requireModifier(CASTING.getId(), 1).translationKey("constructs_casting.modifier.swiftcasting.requirement").build())
				.build();

		buildModifier(SPELLBOUND).addModule(AttributeModule.builder(AttributeRegistry.SPELL_POWER.get(), AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(SPELLBOUND).eachLevel(0.075f)).levelDisplay(ModifierLevelDisplay.NO_LEVELS).build();

		buildModifier(MANA_UPGRADE)     .levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCToolStats.MAGIC_TOOL).eachLevel(80f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.tag(CCToolStats.MAGIC_TOOL).inverted()).eachLevel(80f))
				.build();
		buildModifier(COOLDOWN_UPGRADE) .levelDisplay(ModifierLevelDisplay.DEFAULT).addModule(AttributeModule.builder(AttributeRegistry.COOLDOWN_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(COOLDOWN_UPGRADE).eachLevel(0.05f)).build();
		buildModifier(FIRE_UPGRADE)     .addModule(spellPowerModifier(FIRE_UPGRADE,      AttributeRegistry.FIRE_SPELL_POWER     .get())).build();
		buildModifier(ICE_UPGRADE)      .addModule(spellPowerModifier(ICE_UPGRADE,       AttributeRegistry.ICE_SPELL_POWER      .get())).build();
		buildModifier(LIGHTNING_UPGRADE).addModule(spellPowerModifier(LIGHTNING_UPGRADE, AttributeRegistry.LIGHTNING_SPELL_POWER.get())).build();
		buildModifier(ENDER_UPGRADE)    .addModule(spellPowerModifier(ENDER_UPGRADE,     AttributeRegistry.ENDER_SPELL_POWER    .get())).build();
		buildModifier(HOLY_UPGRADE)     .addModule(spellPowerModifier(HOLY_UPGRADE,      AttributeRegistry.HOLY_SPELL_POWER     .get())).build();
		buildModifier(BLOOD_UPGRADE)    .addModule(spellPowerModifier(BLOOD_UPGRADE,     AttributeRegistry.BLOOD_SPELL_POWER    .get())).build();
		buildModifier(EVOCATION_UPGRADE).addModule(spellPowerModifier(EVOCATION_UPGRADE, AttributeRegistry.EVOCATION_SPELL_POWER.get())).build();
		buildModifier(NATURE_UPGRADE)   .addModule(spellPowerModifier(NATURE_UPGRADE,    AttributeRegistry.NATURE_SPELL_POWER   .get())).build();
		buildModifier(ELDRITCH_UPGRADE) .addModule(spellPowerModifier(ELDRITCH_UPGRADE,  AttributeRegistry.ELDRITCH_SPELL_POWER .get())).build();

		buildModifier(SPELL_PROTECTION).addModule(ProtectionModule.builder().source(DamageSourcePredicate.tag(CCDamageTypes.Tags.SPELL_BASED)).eachLevel(2.5f)).build();
//		buildModifier(SPELLBOOK_STRAP).priority(95)
//				.addModule(InventoryModule.builder().pattern(new Pattern("constructs_casting:spellbook_plus")).slotsPerLevel(1))
//				.addModule(new SpellbookStrapModule(TooltipKey.NORMAL))
//				.addModule(InventoryMenuModule.SHIFT)
//				.addModule(new VolatileFlagModule(ToolInventoryCapability.INCLUDE_OFFHAND));
		buildModifier(IMPROVEABLE).addModule(ModifierSlotModule.slot(AFFINITY_SLOT).eachLevel(2)).levelDisplay(ModifierLevelDisplay.NO_LEVELS).build();
		buildModifier(REGROWTH).addModule(AttributeModule.builder(AttributeRegistry.MANA_REGEN, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.15f)).build();
	    buildModifier(EXPEDIENT).addModule(AttributeModule.builder(AttributeRegistry.CAST_TIME_REDUCTION, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.1f)).build();
    }
	private static AttributeModule spellPowerModifier(ModifierId modifier, Attribute attribute) {
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).uniqueFrom(modifier).eachLevel(0.05f);
	}

	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
	public static class Tags extends AbstractModifierTagProvider {

		public Tags(PackOutput packOutput, String modId, ExistingFileHelper existingFileHelper) {
			super(packOutput, modId, existingFileHelper);
		}

		/**
		 *
		 */
		@Override
		protected void addTags() {
			tag(TinkerTags.Modifiers.DUAL_INTERACTION).add(CASTING.getId());
			tag(TinkerTags.Modifiers.GENERAL_UPGRADES).add(MANA_UPGRADE, COOLDOWN_UPGRADE, FIRE_UPGRADE, ICE_UPGRADE, LIGHTNING_UPGRADE, ENDER_UPGRADE, HOLY_UPGRADE, BLOOD_UPGRADE, NATURE_UPGRADE, ELDRITCH_UPGRADE, TECHNOMANCY_UPGRADE, ABYSSAL_UPGRADE);
			tag(TinkerTags.Modifiers.PROTECTION_DEFENSE).add(SPELL_PROTECTION);
			tag(TinkerTags.Modifiers.INTERACTION_ABILITIES).add(CASTING.getId()).add(SWIFTCASTING);
			tag(TinkerTags.Modifiers.LEGGING_ABILITIES).add(SPELLBOOK_STRAP.getId());
		}

		@Override
		public String getName() {
			return "Construct's Casting Modifier Tags";
		}
	}

	@SubscribeEvent
	void registerSerializers(RegisterEvent event) {
		if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
//			ConstructsCasting.LOGGER.info("register serializer event");
			ModifierModule.LOADER.register(ConstructsCasting.id("spellbook_strap"), SpellbookStrapModule.LOADER);
		}
	}
}
