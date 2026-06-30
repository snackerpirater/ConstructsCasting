package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.CCDamageTypes;
import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.fluids.CCFluidEffects;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCToolStats;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.render.CinderousRarity;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Rarity;
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
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ConditionalStatModule;
import slimeknights.tconstruct.library.modifiers.modules.build.*;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalPowerModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.List;
import java.util.stream.Stream;

import static slimeknights.tconstruct.library.json.math.ModifierFormula.MULTIPLIER;
import static slimeknights.tconstruct.library.json.math.ModifierFormula.VALUE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ConstructsCasting.MOD_ID)
public class CCModifiers extends AbstractModifierProvider {
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(ConstructsCasting.MOD_ID);

	public static final StaticModifier<Modifier> CASTING = MODIFIERS.register("casting", CastingModifier::new);
	public static final StaticModifier<Modifier> SPELLBLADE = MODIFIERS.register("spellblade", SpellbladeModifier::new);
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
	public static final ModifierId COMBUSTIVE = new ModifierId(ConstructsCasting.MOD_ID, "combustive"); //pyrium melee/ranged: hits have a chance to apply immolation stacks
	public static final ModifierId HEATSHIELD = new ModifierId(ConstructsCasting.MOD_ID, "heatshield"); //pyrium armor: fire damage increases protection?

	public static final ModifierId ANTIMAGIC = new ModifierId(ConstructsCasting.MOD_ID, "antimagic");
	public static final ModifierId SORCEROUS = new ModifierId(ConstructsCasting.MOD_ID, "sorcerous"); //mithril melee/ranged: hits have a chance to return mana
	public static final ModifierId MANA_PROTECTION = new ModifierId(ConstructsCasting.MOD_ID, "mana_protection"); //mithril armor: consumes mana on hit for percent protection

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

    public static final ModifierId FIRE_SPECIALIZATION  = new ModifierId(ConstructsCasting.MOD_ID, "fire_specialization");

	public static final ModifierId ABYSSAL_UPGRADE      = new ModifierId(ConstructsCasting.MOD_ID, "abyssal_upgrade");
	public static final ModifierId TECHNOMANCY_UPGRADE  = new ModifierId(ConstructsCasting.MOD_ID, "technomancy_upgrade");
	public static final ModifierId AQUA_UPGRADE         = new ModifierId(ConstructsCasting.MOD_ID, "aqua_upgrade");
	public static final ModifierId SOUND_UPGRADE        = new ModifierId(ConstructsCasting.MOD_ID, "sound_upgrade");

	public static final ModifierId DUMMY_SPELL_POWER_UPGRADE = new ModifierId(ConstructsCasting.MOD_ID, "dummy_spell_power_upgrade");

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
	public static final ModifierId BLOODTHIRSTY = new ModifierId(ConstructsCasting.MOD_ID, "bloodthirsty");

	public static final ModifierId FROSTBITE = new ModifierId(ConstructsCasting.MOD_ID, "frostbite");

	public static final ModifierId REINSCRIBED = new ModifierId(ConstructsCasting.MOD_ID, "reinscribed");

	public static final ModifierId venomagic = new ModifierId(ConstructsCasting.MOD_ID, "venomagic");

	public CCModifiers(PackOutput generator) {
		super(generator);
	}

	private static final EquipmentSlot[] notOffhand = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND};
	@Override
	protected void addModifiers() {
		buildModifier(ARCANE).levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCItems.Tags.MAGIC_TOOL).eachLevel(50f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.or(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL), ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).inverted()).eachLevel(25f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION).tool(ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).eachLevel(50f))
				.build();

		buildModifier(SWIFTCASTING).levelDisplay(ModifierLevelDisplay.DEFAULT)
                .addModule(AttributeModule.builder(AttributeRegistry.CASTING_MOVESPEED.get(), AttributeModifier.Operation.MULTIPLY_BASE)
					.tooltipStyle(AttributeModule.TooltipStyle.ATTRIBUTE).amount(0.2f, 0.2f))
				.build();
		buildModifier(ANTIMAGIC)
				.addModule(ConditionalMeleeDamageModule.builder().target(magicUser).eachLevel(2f))
				.addModule(ConditionalPowerModule.builder().target(magicUser).eachLevel(0.75f))
						.build();
		buildModifier(SPELLBOUND)
                .addModule(AttributeModule.builder(AttributeRegistry.SPELL_POWER.get(), AttributeModifier.Operation.MULTIPLY_BASE).tool(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL).inverted()).uniqueFrom(SPELLBOUND).eachLevel(0.05f))
                .addModule(StatBoostModule.add(CCToolStats.SPELL_POWER).toolTag(CCItems.Tags.MAGIC_TOOL).eachLevel(0.05f)).build();
		buildModifier(ANTIFROST).addModule(ConditionalMeleeDamageModule.builder().target(LivingEntityPredicate.IS_FREEZING).eachLevel(2.0f));
		buildModifier(MANA_UPGRADE)     .levelDisplay(ModifierLevelDisplay.DEFAULT)
				.addModule(StatBoostModule.add(CCToolStats.MAX_MANA).toolTag(CCItems.Tags.MOD_SPELLBOOKS).eachLevel(80f))
				.addModule(AttributeModule.builder(AttributeRegistry.MAX_MANA.get(), AttributeModifier.Operation.ADDITION)
						.slots(notOffhand).tool(ToolStackPredicate.tag(CCItems.Tags.MOD_SPELLBOOKS).inverted()).eachLevel(80f))
				.build();
		buildModifier(COOLDOWN_UPGRADE) .levelDisplay(ModifierLevelDisplay.DEFAULT)
                .addModule(AttributeModule.builder(AttributeRegistry.COOLDOWN_REDUCTION.get(), AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).tool(ToolStackPredicate.tag(CCItems.Tags.MAGIC_TOOL).inverted()).uniqueFrom(COOLDOWN_UPGRADE).eachLevel(0.05f))
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

		buildModifier(DUMMY_SPELL_POWER_UPGRADE).build();

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

        buildModifier(FIRE_SPECIALIZATION)
				.addModule(AttributeModule.builder(AttributeRegistry.FIRE_SPELL_POWER, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).exactLevel(1).flat(0.23f))

				.addModule(AttributeModule.builder(AttributeRegistry.FIRE_SPELL_POWER, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).exactLevel(2).flat(0.50f))

				.addModule(AttributeModule.builder(AttributeRegistry.FIRE_SPELL_POWER, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).exactLevel(3).flat(0.85f))
				.addModule(new RarityModule(CinderousRarity.CINDEROUS_RARITY))
				.addModule(AttributeModule.builder(AttributeRegistry.SPELL_POWER, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).eachLevel(-0.1f)).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL);

		buildModifier(SPELL_PROTECTION).addModule(ProtectionModule.builder().source(DamageSourcePredicate.tag(CCDamageTypes.Tags.SPELL_BASED)).eachLevel(2.5f)).build();
//		buildModifier(SPELLBOOK_STRAP).priority(95)
//				.addModule(InventoryModule.builder().pattern(new Pattern("constructs_casting:spellbook_plus")).slotsPerLevel(1))
//				.addModule(new SpellbookStrapModule(TooltipKey.NORMAL))
//				.addModule(InventoryMenuModule.SHIFT)
//				.addModule(new VolatileFlagModule(ToolInventoryCapability.INCLUDE_OFFHAND));
		buildModifier(IMPROVEABLE).addModule(ModifierSlotModule.slot(AFFINITY_SLOT).eachLevel(2)).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL).build();
		buildModifier(RINGBEARER).addModule(new BonusCurioSlotModule("ring", new LevelingInt(0, 2), "64d62ea-03d8-4919-9ba5-fec06d332c72"));
		buildModifier(REGROWTH).addModule(AttributeModule.builder(AttributeRegistry.MANA_REGEN, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).eachLevel(0.1f)).levelDisplay(ModifierLevelDisplay.DEFAULT).build();
	    buildModifier(EXPEDIENT).addModule(AttributeModule.builder(AttributeRegistry.CAST_TIME_REDUCTION, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).eachLevel(0.1f)).levelDisplay(ModifierLevelDisplay.DEFAULT).build();
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
		ModifierSlotModule UPGRADE = ModifierSlotModule.slot(SlotType.UPGRADE).eachLevel(1);
		buildModifier(REINSCRIBED).tooltipDisplay(BasicModifier.TooltipDisplay.TINKER_STATION).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL).addModule(UPGRADE);
		List<ResourceLocation> summonSpells = Stream.of( //hardcoded because FUCK YOU
				"irons_spellbooks:summon_swords",
				"irons_spellbooks:summon_polar_bear",
				"irons_spellbooks:summon_vex",
				"irons_spellbooks:raise_dead",
				"cataclysm_spellbooks:conjure_coral_golem",
				"cataclysm_spellbooks:conjure_coralssus",
				"cataclysm_spellbooks:conjure_clawdian",
				"cataclysm_spellbooks:conjure_koboldiator",
				"cataclysm_spellbooks:conjure_koboleton",
				"cataclysm_spellbooks:thoths_witness",
				"cataclysm_spellbooks:conjure_thrall",
				"cataclysm_spellbooks:conjure_amethyst_crab",
				"cataclysm_spellbooks:dos_swarm",
				"cataclysm_spellbooks:construct_watchers",
				"cataclysm_spellbooks:construct_prowler"
		).map(ResourceLocation::parse).toList();
		buildModifier(BLOODTHIRSTY).levelDisplay(ModifierLevelDisplay.SINGLE_LEVEL)
				.addModule(new SelfDamageOnCastModule(summonSpells, LevelingValue.eachLevel(2)))
				.addModule(AttributeModule.builder(AttributeRegistry.SUMMON_DAMAGE, AttributeModifier.Operation.MULTIPLY_BASE).eachLevel(0.15f))
				.build();
		buildModifier(COMBUSTIVE)
				.addModule(new CombustiveModule(new LevelingValue(0.3f, 0.2f))) //1 -> 6 hits, 2 -> 4 hits, 3 -> 3 hits, 4 -> 2.4, 5 -> 2
				.addModule(new RarityModule(CinderousRarity.CINDEROUS_RARITY))
				.build(); //manyullyn takes 5 hits to max out, so around 5 hits for an explosion would be nice
		buildModifier(MANA_PROTECTION)
				.addModule(new ManaProtectionModule(LevelingValue.flat(4), LevelingValue.eachLevel(0.04f)))
				.addModule(new RarityModule(Rarity.RARE))
				.build();
		buildModifier(SORCEROUS)
				.addModule(new ManaOnHitModule(LevelingValue.flat(3), LevelingValue.eachLevel(0.25f)))
				.addModule(new RarityModule(Rarity.RARE))
				.build();
		buildModifier(venomagic)
				.addModule(new VenomagicModule(LevelingValue.eachLevel(0.1f)))
				.build();
	}

	private static AttributeModule spellPowerModifier(ModifierId modifier, Attribute attribute) { //no spell power upgrades on offhand >:(
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).uniqueFrom(modifier).eachLevel(0.05f);
	}

	private static AttributeModule spellDispulsionModifier(ModifierId modifier, Attribute attribute) {
		return AttributeModule.builder(attribute, AttributeModifier.Operation.MULTIPLY_BASE).slots(notOffhand).uniqueFrom(modifier).eachLevel(0.15f);
	}
	public static LivingEntityPredicate magicUser = LivingEntityPredicate.simple(
			target -> MagicData.getPlayerMagicData(target).isCasting() ||
					target instanceof AbstractSpellCastingMob ||
					target instanceof IMagicSummon ||
					target.getAttributeValue(AttributeRegistry.MAX_MANA.get()) > 100
	);

	@Override
	public String getName() {
		return "Construct's Casting Modifiers";
	}
	public static class Tags extends AbstractModifierTagProvider {

		public Tags(PackOutput packOutput, String modId, ExistingFileHelper existingFileHelper) {
			super(packOutput, modId, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(TinkerTags.Modifiers.DUAL_INTERACTION).add(CASTING.getId());
			tag(TinkerTags.Modifiers.GENERAL_UPGRADES)
					.add(MANA_UPGRADE, COOLDOWN_UPGRADE, EXPEDIENT, DUMMY_SPELL_POWER_UPGRADE);
			tag(TinkerTags.Modifiers.BONUS_SLOTLESS).add(REINSCRIBED);
			tag(TinkerTags.Modifiers.PROTECTION_DEFENSE).add(SPELL_PROTECTION);
            tag(TinkerTags.Modifiers.GENERAL_ABILITIES).add(IMPROVEABLE, IMBUED.getId());
			tag(TinkerTags.Modifiers.INTERACTION_ABILITIES).add(CASTING.getId());
			tag(TinkerTags.Modifiers.BOOT_UPGRADES).add(SWIFTCASTING);
			tag(TinkerTags.Modifiers.LEGGING_ABILITIES).add(SPELLBOOK_STRAP.getId());
			tag(TinkerTags.Modifiers.MELEE_ABILITIES).add(SPELLBLADE.getId());
            tag(TinkerTags.Modifiers.CHESTPLATE_ABILITIES).add(RINGBEARER);
			tag(TinkerTags.Modifiers.HIDDEN_FROM_RECIPE_VIEWERS).add(DUMMY_SPELL_POWER_UPGRADE);
			tag(TinkerTags.Modifiers.EXTRACT_MODIFIER_BLACKLIST).add(DUMMY_SPELL_POWER_UPGRADE);
			tag(TinkerTags.Modifiers.BLOCK_WHILE_CHARGING).add(CASTING.getId());
            tag(TinkerTags.Modifiers.GENERAL_ARMOR_UPGRADES).add(SLOT_IMPROVEMENT);
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
