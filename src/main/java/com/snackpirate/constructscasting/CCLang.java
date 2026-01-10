package com.snackpirate.constructscasting;

import com.snackpirate.constructscasting.fluids.CCFluidEffects;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import com.snackpirate.constructscasting.modifiers.CombustiveModule;
import com.snackpirate.constructscasting.spells.CCSpells;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.function.Supplier;

public class CCLang extends LanguageProvider {



	public CCLang(PackOutput output, String modid, String locale) {
		super(output, modid, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.constructs_casting.constructs_casting", "Construct's Casting");
		addMaterial(CCMaterials.arcanium, "Arcanium", "Yer a wizard, Harry!", "Grants +25 Max Mana per level.");
		addMaterial(CCMaterials.exilite, "Exilite", "#1 Wizard Hater", "Deals +2 damage per level to summons and entities with magic capabilities beyond average.");
		addMaterialTraits(CCMaterials.exilite, "Grants +0.5 projectile power per level against summons and entities with magic capabilities beyond average.", "Grants +10% protection per level against spell (NOT magic) damage.");
		addMaterial(CCMaterials.cosmichalcum, "Cosmichalcum", "Template flavor text", "Template description");
		addMaterial(CCMaterials.mithril, "Mithril", "The pride of Moria", "Dealing damage has a 15% chance per level to restore mana (amount scales with damage dealt).");
		addMaterialTraits(CCMaterials.mithril, "Dealing damage has a 30% chance per level to restore mana (amount scales with damage dealt).", "Consumes up to 4 mana on hit to reduce incoming damage by 1% per mana consumed (up to 4%)."); //8 levels = 32% total, 12 = 48%
		addMaterial(CCMaterials.pyrium, "Pyrium", "The might of Tyros himself", "Dealing damage has a 50% chance (+25% chance per additional level) to inflict one stack of Immolation on the target; reaching three stacks of Immolation on a target causes a fiery explosion.");
		addMaterialTraits(CCMaterials.pyrium, "Dealing damage has a 50% chance (+25% chance per additional level) to inflict one stack of Immolation on the target; reaching three stacks of Immolation on a target causes a fiery explosion.", "");
		addMaterial(CCMaterials.arcaneCloth, "Arcane Cloth", "Mage essential!", "Grants +7.5% Spell Power.");
		addMaterial(CCMaterials.frozenBone, "Frozen Bone", "Ice, ice, baby", "Deals +2 damage per level to frozen enemies");
		add("material.constructs_casting.frozen_bone.ammo", "Causes the target to take 200% freezing damage for the next 5 to 10 seconds.");
		addMaterial(CCMaterials.frostRod, "Frosted", "Cold, cold heart", "Grants +5% Ice Spell Power");
		addMaterial(CCMaterials.hogskin, "Hogskin", "Unsanitary, but still useful", "Grants up to 10% Spell Power in hot biomes and 20% Cooldown Reduction in cold biomes.");
		addMaterial(CCMaterials.divinePearl, "Divine Pearl", "The power of god and anime","Gives +0.75 projectile power against undead per level");
		addMaterial(CCMaterials.permafrost, "Permafrost", "Not to be confused with permafrost", "Empowers ice magic");
		addMaterial(CCMaterials.emerald, "Emerald", "Villagers hate this simple trick", "Empowers evocation magic");
		addMaterial(CCMaterials.echoShard, "Echo", "Echo", "Empowers eldritch magic");


		add("stat.constructs_casting.magic_cloth", "Pages");
        add("stat.constructs_casting.magic_base", "Base");
        add("tool_stat.constructs_casting.spell_slots", "Spell Slots: ");
		add("tool_stat.constructs_casting.spell_slots.description", "How many spells this spellbook can have.");
        add("tool_stat.constructs_casting.cooldown_reduction", "Cooldown Reduction: ");
		add("tool_stat.constructs_casting.cooldown_reduction.description", "Increases how often you can cast spells when equipped.");
        add("tool_stat.constructs_casting.max_mana", "Max Mana: ");
		add("tool_stat.constructs_casting.max_mana.description", "Tool grants this much maximum mana when equipped.");
        add("tool_stat.constructs_casting.spell_power", "Spell Power: ");
		add("tool_stat.constructs_casting.spell_power.description", "Increases the power of your spells when equipped.");
        add("stat.constructs_casting.spellbook_plating", "Plating");
		add("stat.constructs_casting.adornment", "Adornment");
		add("pattern.constructs_casting.spellbook_plating", "Spellbook Plating");
        add("pattern.constructs_casting.faceted_gem", "Faceted Gem");
		add("pattern.constructs_casting.spellbook_cover", "Spellbook Cover");
        add("pattern.constructs_casting.wand_rod", "Wand Rod");
		add("pattern.constructs_casting.pages", "Pages");
        add("pattern.constructs_casting.spellbooks", "Spellbooks");
        add("pattern.constructs_casting.spellbooks_first_part", "Spellbook Plating or Cover");
        add("pattern.constructs_casting.spellbooks.description", "Used to hold and cast spells. Use a Spellbook Plating for the Plated Spellbook, or a Spellbook Cover for the Traveller's Spellbook.");
        add("tool_stat.constructs_casting.extra.no_stats", "No stats");
		addMaterial(CCMaterials.rainbowSlime, "Rainbowslime", "How are you seeing this?", "Happy pride month!");
		addModifier(CCModifiers.CASTING.getId(), "Casting", "Not for fish, unfortunately.", "Allows the tool to cast spells on right click.");
		addModifier(CCModifiers.SWIFTCASTING, "Swiftcasting", "Run 'n' Gun!", "Lets you move faster when casting spells");
		addModifier(CCModifiers.SPELLBLADE.getId(), "Spellblade", "Strike!", "Hitting an enemy casts the spell imbued on the tool.");
		add("constructs_casting.modifier.spellblade.requirement", "Requires Imbued to apply.");

		add("constructs_casting.modifier.swiftcasting.requirement", "Requires the Casting ability to be applied first.");
		addModifier(CCModifiers.IMBUED.getId(), "Imbued", "One more spell!", "Allows the tool to be imbued with a spell.");
		addModifier(CCModifiers.ENCYCLOPEDIC.getId(), "Encyclopedic", "Well read", "Allows the Spellbook to function as an encyclopedia on right click.");

		addModifier(CCModifiers.ARCANE, "Arcane" ,"Mana-licious!", "Grants +25 max mana.");
		addModifier(CCModifiers.ANTIMAGIC.getId(), "Antimagic", "Self-explanatory.", "Grants +2 damage against magic users.");
		add("modifier.constructs_casting.antimagic.damage_boost", "Antimagic Damage");
		addModifier(CCModifiers.SPELL_PROTECTION, "Spell Protection", "Diabolical!", "Grants +10% resistance against spells. (Different from Magic Protection)");
		add("modifier.constructs_casting.spell_protection.resistance", "Spell Resistance");
		addModifier(CCModifiers.ANTIFROST, "Antifrost", "Don't drink it!", "Grants +3 damage per level to frozen targets.");
		add("modifier.constructs_casting.antifrost.attack_damage", "Antifrost Damage");
		addModifier(CCModifiers.SPELLBOUND, "Spellbound", "Jack of all trades!", "Grants +5% power to all types of spells.");
		addModifier(CCModifiers.SPELLBOOK_STRAP.getId(), "Spellbook Strap", "Twice the spells, twice the fun!", "Interacting with the leggings allows swapping your spellbook with another one in the leggings' inventory.");
		addModifier(CCModifiers.REINSCRIBED, "Reinscribed", "Oops, All Ink!", "Inks a new upgrade slot onto the tool!");

		addModifier(CCModifiers.MANA_UPGRADE, "Mana Upgrade", "Mana-rific!", "Grants +80 max mana.");
		addModifier(CCModifiers.COOLDOWN_UPGRADE, "Cooldown Upgrade", "I am speed!", "Grants +5% Cooldown Reduction.");
		addModifier(CCModifiers.FIRE_UPGRADE, "Fire Upgrade", "Hot hot hot!", "Grants +5% Fire Spell Power.");
		addModifier(CCModifiers.ICE_UPGRADE, "Ice Upgrade", "Ice ice baby!", "Grants +5% Ice Spell Power.");
		addModifier(CCModifiers.LIGHTNING_UPGRADE, "Lightning Upgrade", "Electrifying!", "Grants +5% Lightning Spell Power.");
		addModifier(CCModifiers.ENDER_UPGRADE, "Ender Upgrade", "Space-y!", "Grants +5% Ender Spell Power.");
		addModifier(CCModifiers.HOLY_UPGRADE, "Holy Upgrade", "Great heavens!", "Grants +5% Holy Spell Power.");
		addModifier(CCModifiers.BLOOD_UPGRADE, "Blood Upgrade", "Bloody hell!", "Grants +5% Blood Spell Power.");
		addModifier(CCModifiers.EVOCATION_UPGRADE, "Evocation Upgrade", "Hrmmm?", "Grants +5% Evocation Spell Power.");
		addModifier(CCModifiers.NATURE_UPGRADE, "Nature Upgrade", "All natural!", "Grants +5% Nature Spell Power.");
		addModifier(CCModifiers.ELDRITCH_UPGRADE, "Eldritch Upgrade", "Man-made modifiers beyond your comprehension", "Grants +5% Eldritch Spell Power");

		addModifier(CCModifiers.ABYSSAL_UPGRADE, "Abyssal Upgrade", "Not to be confused with Aqua", "Grants +5% Abyssal Spell Power.");
		addModifier(CCModifiers.TECHNOMANCY_UPGRADE, "Technomancy Upgrade", "Hackerman", "Grants +5% Technomancy Spell Power.");
		addModifier(CCModifiers.AQUA_UPGRADE, "Aqua Upgrade", "Not to be confused with Abyssal", "Grants +5% Aqua Spell Power.");
		addModifier(CCModifiers.SOUND_UPGRADE, "Sound Upgrade", "Sounds alright", "Grants +5% Sound Spell Power");

		addModifier(CCModifiers.SPELL_DISPULSION, "Spell Dispulsion", "Not to be confused with Spell Protection", "Grants +7.5% Spell Resistance");
		addModifier(CCModifiers.FIRE_DISPULSION, "Fire Dispulsion", "The power of the sun in the palm of my hand", "Grants +15% Fire Spell Resistance.");
		addModifier(CCModifiers.ICE_DISPULSION, "Ice Dispulsion", "Ice to meet you", "Grants +15% Ice Spell Resistance.");
		addModifier(CCModifiers.LIGHTNING_DISPULSION, "Lightning Dispulsion", "Surge protection", "Grants +15% Lightning Spell Resistance.");
		addModifier(CCModifiers.ENDER_DISPULSION, "Ender Dispulsion", "Take that, globeheads!", "Grants +15% Ender Spell Resistance.");
		addModifier(CCModifiers.HOLY_DISPULSION, "Holy Dispulsion", "Neither the power of god nor anime", "Grants +15% Holy Spell Resistance.");
		addModifier(CCModifiers.BLOOD_DISPULSION, "Blood Dispulsion", "Dirty haemophiles", "Grants +15% Blood Spell Resistance.");
		addModifier(CCModifiers.EVOCATION_DISPULSION, "Evocation Dispulsion", "Hrmmm!", "Grants +15% Evocation Spell Resistance.");
		addModifier(CCModifiers.NATURE_DISPULSION, "Nature Dispulsion", "Insert allegory for AI here", "Grants +15% Nature Spell Resistance.");
		addModifier(CCModifiers.ELDRITCH_DISPULSION, "Eldritch Dispulsion", "Comprehending horrors", "Grants +15% Eldritch Spell Resistance.");

		addModifier(CCModifiers.COMBUSTIVE, "Combustive", "Boom, boom, boom", "Dealing damage can cause fiery explosions");
		addModifier(CCModifiers.MANA_PROTECTION, "Mana Protection", "Blocks event the most orcish of spears", "Exchanges mana for some protection");
		add("modifier.constructs_casting.mana_protection.resistance", "Mana Resistance");
		addFluid(CCFluids.arcaneEssence, "Arcane Essence", "Probably tastes like blue raspberry; good if you're short on Mana though.");
		addFluid(CCFluids.fireEssence, "Fire Essence" ,"Subtle notes of sulfur with an ashy aftertaste; Good if you need a little more FIREpower");
		addFluid(CCFluids.iceEssence, "Ice Essence", "The active ingredient in Gatorade Frost; useful if you want to chill out");
		addFluid(CCFluids.lightningEssence, "Lightning Essence", "The closest thing to battery acid you can still drink; Electromancers love it");
		addFluid(CCFluids.enderEssence, "Ender Essence", "You'll have to stomach the grape flavoring, but Shadow-Walkers love it!");
		addFluid(CCFluids.holyEssence, "Holy Essence", "Helpful for those who wish to boost their healing magic");
		addFluid(CCFluids.bloodEssence, "Blood Essence", "Questionably ethical, but Cultists drink it every day");
		addFluid(CCFluids.evocationEssence, "Evocation Essence", "Empowers evocation magic");
		addFluid(CCFluids.natureEssence, "Nature Essence", "Still smells moldy; but it empowers nature magic");
		addFluid(CCFluids.cinderEssence, "Cinder Essence", "From inferno to fork; leaches mana from your enemies");

		addFluid(CCFluids.liquidLightning, "Lightning", "");
		addFluid(CCFluids.potatoStew, "Potato Stew", "A vegan yet still hearty alternative to Meat Soup");
		addFluid(CCFluids.poisonousPotatoStew, "Poisonous Potato Stew", "A vegan yet still devastating alternative to spider venom");
		addFluid(CCFluids.moltenArcanium, "Molten Arcanium", "Definitely tastes like blue raspberry; good for any sort of magic");
		addFluid(CCFluids.moltenExilite, "Molten Exilite", "Most effective against mages and other magic users.");
		addFluid(CCFluids.moltenArcaneSalvage, "Molten Arcane Salvage", "Allows you to cast spells faster");
		addFluid(CCFluids.moltenMithril, "Molten Mithril", "Allows you to cast spells faster");
		addFluid(CCFluids.moltenPyrium, "Molten Pyrium", "Weakens the target's fire magic");

		addFluid(CCFluids.squidInk, "Squid Ink", "Keep it out of your eyes!");
		addFluid(CCFluids.commonInk, "Common Ink", "Weakens the target's magic");
		addFluid(CCFluids.uncommonInk, "Uncommon Ink", "Weakens the target's magic");
		addFluid(CCFluids.rareInk, "Rare Ink", "Weakens the target's magic");
		addFluid(CCFluids.epicInk, "Epic Ink", "Weakens the target's magic");
		addFluid(CCFluids.legendaryInk, "Legendary Ink", "Weakens the target's magic");

		addItem(CCItems.potatoStewBowl, "Potato Stew");
		addItem(CCItems.poisonousPotatoStewBowl, "Poisonous Potato Stew");

		addItem(CCItems.exiliteIngot, "Exilite Ingot");
		addItem(CCItems.exiliteNugget, "Exilite Nugget");
		addItem(CCItems.exiliteReinforcement, "Exilite Reinforcement");

		addItem(CCItems.travellersSpellbook, "Traveller's Spellbook");
		addItem(CCItems.platedSpellbook, "Plated Spellbook");
		add("item.constructs_casting.plated_spellbook.description", "A heavily fortified spellbook geared towards defense rather than spell power.");
		addItem(CCItems.slimySpellbook, "Slimy Spellbook");
		addItem(CCItems.eldritchStaff, "Sculk Staff");

		addItem(CCItems.wand, "Wand");
        add("item.constructs_casting.wand.description", "The wand is a simple casting implement. It buffs spell casting, and allows you to cast spells on right click.");
		addItem(CCItems.battlestaff, "Battlestaff");
        add("item.constructs_casting.battlestaff.description", "The Battlestaff is a hybrid melee/magic weapon. Both melee and magic attacks will hit hard with one of these.");
		addItem(CCItems.flamberge, "Flamberge");
		add("item.constructs_casting.flamberge.description", "The Flamberge is a weapon primarily focused on defense and mobility. Grants armor points when held and allows for a quick dash.");
//		addItem(CCItems.arcaniumApple, "Arcanium Apple");

        addItem(CCItems.spellbookPlating, "Spellbook Plating");
        add(CCItems.spellbookPlatingCast.getSand().getDescriptionId(), "Spellbook Plating Sand Cast");
		add(CCItems.spellbookPlatingCast.get().getDescriptionId(), "Spellbook Plating Gold Cast");
		add(CCItems.spellbookPlatingCast.getRedSand().getDescriptionId(), "Spellbook Plating Red Sand Cast");
        addItem(CCItems.spellbookCover, "Spellbook Cover");
        addItem(CCItems.pages, "Pages");
		addItem(CCItems.facetedGem, "Faceted Gem");
		addItem(CCItems.wandRod, "Wand Rod");
		add(CCItems.facetedGemCast.get().getDescriptionId(), "Faceted Gem Gold Cast");
		add(CCItems.facetedGemCast.getSand().getDescriptionId(), "Faceted Gem Sand Cast");
		add(CCItems.facetedGemCast.getRedSand().getDescriptionId(), "Faceted Gem Red Sand Cast");

		add("gui.constructs_casting.fluid.bottle", "%s Bottles");
        add("ui.constructs_casting.spellbook_has_slots", "Cannot modify, remove spells from spellbook first");

		addItem(CCItems.wizardslimeBall, "Wizardslime Ball");
		add("school.constructs_casting.slime", "Slime");
		addSpell(CCSpells.FREEZE_SPELL, "Freeze", "Rapidly cools down the targeted Casting Table or Basin, instantly finishing the casting process. Only works on molten metals.");
		add("spell.constructs_casting.freeze.invalid_target", "Invalid target!");
//		addSpell(CCSpells.SLIMEBALL_SPELL, "Slimeball", "Lobs a random slimeball, capable of bouncing off of blocks and enemies alike. The higher level, the bouncier.");
		add("ui.constructs_casting.slimeball.max_bounces", "%s Bounces");
		add("ui.constructs_casting.enderference_anti_teleport", "Your current affliction prevents you from teleporting...");
		addSpell(CCSpells.INVERT, "Invert", "Inverts the player's gravity for a short time. Can recast to invert back to normal.");
//		addSpell(CCSpells.SLING_SPELL, "Sling", "Launches player in the opposite direction that they are looking.");

        addEffect(CCFluidEffects.MobEffects.inkyImpairment, "Inky Impairment");
        addEffect(CCFluidEffects.MobEffects.magicEmpowerment, "Magic Empowerment");
        addEffect(CCFluidEffects.MobEffects.magicVulnerability, "Magic Vulnerability");
		addEffect(CCFluidEffects.MobEffects.bloodEmpowerment, "Blood Empowerment");
		addEffect(CCFluidEffects.MobEffects.enderEmpowerment, "Ender Empowerment");
		addEffect(CCFluidEffects.MobEffects.evocationEmpowerment, "Evocation Empowerment");
		addEffect(CCFluidEffects.MobEffects.fireEmpowerment, "Fire Empowerment");
		addEffect(CCFluidEffects.MobEffects.holyEmpowerment, "Holy Empowerment");
		addEffect(CCFluidEffects.MobEffects.iceEmpowerment, "Ice Empowerment");
		addEffect(CCFluidEffects.MobEffects.lightningEmpowerment, "Lightning Empowerment");
		addEffect(CCFluidEffects.MobEffects.natureEmpowerment, "Nature Empowerment");
		addEffect(CCFluidEffects.MobEffects.recoveryEmpowerment, "Recovery Empowerment");
        add("fluid_effect.constructs_casting.add_mana", "Adds 50 mana to the target");
        add("fluid_effect.constructs_casting.subtract", "Subtracts 50 mana from the target");
		addEffect(CCFluidEffects.MobEffects.frostbite, "Frostbite");

		addFluid(CCFluids.moltenCrystallizedCoral, "Molten Crystallized Coral", "");
		addFluid(CCFluids.gasifiedRedstone, "Redstone", "");
		addFluid(CCFluids.moltenPearl, "Molten Pearl", "");

		addFluid(CCFluids.abyssalEssence, "Abyssal Essence", "");
		addFluid(CCFluids.technomancyEssence, "Technomancy Essence", "");
		addFluid(CCFluids.aquaEssence, "Aqua Essence", "");
		addFluid(CCFluids.soundEssence, "Sound Essence", "");

	    add("item.tconstruct.creative_slot.affinity", "Creative Affinity Slot");
        add("stat.tconstruct.slot.prefix.affinity", "Affinity Slots: ");
        add("stat.tconstruct.slot.display.affinity", "affinity");
		add("modifier.tconstruct.rebalanced.affinity", "Affinity");


		addModifier(CCModifiers.IMPROVEABLE, "Improvable", "Upgrades are good. But they can be better.", "Adds two bonus Affinity slots to the tool.");
        addModifier(CCModifiers.REGROWTH, "Regrowth", "The best brown thing!", "Increases Mana Regeneration by 10%.");
    	addModifier(CCModifiers.CONSERVING.getId(), "Conserving", "Great deals all day!", "Reduces the mana cost of casting spells by 10.");
        addModifier(CCModifiers.EXPEDIENT, "Expedient", "In a big-time rush?", "Reduces spell cast time by 10%.");
        addModifier(CCModifiers.SOLAR_CHARGED.getId(), "Solar Charged", "The best green thing!", "Empowers Mana Regeneration while under the sun, up to +15% in full light.");
        add("modifier.constructs_casting.solar_charged.boost", "Solar Charged Mana Regen");
        addModifier(CCModifiers.THICK_SKINNED, "Thick-Skinned", "You monster", "Increases spell power and cooldowns in hot biomes, reduces spell power and cooldowns in cold biomes.");
        add("modifier.constructs_casting.thick_skinned.spell_power", "Thick-Skinned Spell Power");
        add("modifier.constructs_casting.thick_skinned.cooldown_reduction", "Thick-Skinned Cooldown Reduction");
        addModifier(CCModifiers.ICHORSPELLS, "Ichorspells", "You are filled with ichor... and determination I guess", "Increases Spell Power by 15% in exchange for -30% mana regeneration");
        addModifier(CCModifiers.DRAGONSPELLS.getId(), "Dragonspells", "In their tongue he is Dovahtiinker, dragonsmith", "Increases Spell Power by 10% while mid-air.");
		addModifier(CCModifiers.GASHING.getId(), "Gashing", "You haemophile", "Dealing damage with spells has a 50% chance to apply the Bleeding effect");
        addModifier(CCModifiers.PUNCTURING.getId(), "Puncturing", "Why is magic blocked by armor anyways?", "Spell damage cancels out some of the target's armor!");
        addModifier(CCModifiers.ENDERBENDER.getId(), "Enderbender", "Hey, I've seen this one before!", "Teleporting via spell grants the Evasion effect");
        addModifier(CCModifiers.APOPTOTIC.getId(), "Apoptotic", "This may be overpowered but who knows", "Dealing spell damage over a certain threshold heals you");
        addModifier(CCModifiers.CALORIFIC.getId(), "Calorific", "Hot ones, and by ones I mean spells", "Spells deal multiplied damage when you are on fire");
        add("modifier.constructs_casting.calorific.boost", "On-Fire Boost Max");
        addModifier(CCModifiers.RINGBEARER, "Ringbearer", "Does it give you two more fingers?", "Allows you to wear two more rings");
        addModifier(CCModifiers.SLOT_IMPROVEMENT, "Slot Improvement", "65%% more spell per spell!", "Grants +1 Spell Slot. (Cannot exceed 15 total Spell Slots)");
    	addModifier(CCModifiers.FROSTBITE, "Frostbite", "Gives me the chills", "Causes targets to temporarily take 200% freezing damage. (Not ice magic damage)");
	}

	public void addMaterial(MaterialId material, String name, String flavour, String desc) {
		String id = material.getPath();
		add("material.constructs_casting." + id, name);
		if (!flavour.isEmpty())
			add("material.constructs_casting." + id + ".flavor", flavour);
		if (!desc.isEmpty())
			add("material.constructs_casting." + id + ".encyclopedia", desc);
	}
	public void addMaterialTraits(MaterialId material, String ranged, String armor) {
		String id = material.getPath();
		if (!ranged.isEmpty())
			add("material.constructs_casting." + id + ".ranged", ranged);
		if (!armor.isEmpty())
			add("material.constructs_casting." + id + ".armor", armor);
	}

	public void addModifier(ModifierId modifier, String name, String flavour, String desc) {
		String id = modifier.getPath();
		add("modifier.constructs_casting." + id, name);
		add("modifier.constructs_casting." + id + ".flavor", flavour);
		add("modifier.constructs_casting." + id + ".description", desc);
	}
	public void addModifierDetailed(ModifierId modifier, String name, String flavor, String desc, String encyclopedia) {
		String id = modifier.getPath();
		addModifier(modifier, name, flavor, desc);
		add("modifier.constructs_casting." + id, encyclopedia);
	}

	public void addFluid(FluidObject<?> fluid, String name, String effect) {
        add("fluid.constructs_casting." + fluid.getId().getPath(), name);
        add("fluid.constructs_casting." + fluid.getId().getPath() + ".fluid_effect", effect);
		add("fluid_type.constructs_casting." + fluid.getId().getPath(), name);
		add("item.constructs_casting." + fluid.getId().getPath() + "_bucket", name + " Bucket");
	}

	public void addSpell(Supplier<AbstractSpell> spell, String name, String desc) {
		add("spell.constructs_casting." + spell.get().getSpellName(), name);
		add("spell.constructs_casting." + spell.get().getSpellName() + ".guide", desc);
	}
}
