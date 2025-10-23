package com.snackpirate.constructscasting;

import com.snackpirate.constructscasting.fluids.CCFluidEffects;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
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
		addMaterial(CCMaterials.arcanium, "Arcanium", "Yer a wizard, Harry!", "Gives the wielder +25 max mana per part.");
		addMaterial(CCMaterials.exilite, "Exilite", "#1 Wizard Hater", "Deals greater damage to magic-wielding enemies.");
		addMaterial(CCMaterials.arcaneCloth, "Arcane Cloth", "Mage essential!", "Empowers the wielder with greater spell power.");
		addMaterial(CCMaterials.frozenBone, "Frozen Bone", "Ice, ice, baby", "Deals greater damage to frozen enemies");
		addMaterial(CCMaterials.frostRod, "Frosted", "Cold, cold heart", "Empowers ice-based magic");

        addMaterial(CCMaterials.paper, "Paper", "Simple but effective", "No special ability, but has a lot of spell slots");
		addMaterial(CCMaterials.leaf, "Leaf", "Time to make like a tree...", "Like all plants, it becomes more powerful in sunlight.");
        addMaterial(CCMaterials.hogskin, "Hogskin", "Unsanitary, but still useful", "The skin adapts to its environment, providing different buffs depending on the temperature");
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
		add("pattern.constructs_casting.spellbook_plating", "Spellbook Plating");
		add("pattern.constructs_casting.spellbook_cover", "Spellbook Cover");
		add("pattern.constructs_casting.pages", "Pages");
        add("tool_stat.constructs_casting.extra.no_stats", "No stats");
        add("material.constructs_casting.exilite.armor", "Protects against damage inflicted by spells.");
		addMaterial(CCMaterials.rainbowSlime, "Rainbowslime", "How are you seeing this?", "Happy pride month!");
		addModifier(CCModifiers.CASTING.getId(), "Casting", "Not for fish, unfortunately.", "Allows the tool to cast spells on right click.");
		addModifier(CCModifiers.SWIFTCASTING, "Swiftcasting", "Run 'n' Gun!", "Allows the user to retain their full movement speed while casting spells.");
		addModifier(CCModifiers.SPELLBLADE.getId(), "Spellblade", "Strike!", "Hitting an enemy casts the spell imbued on the tool.");
		addMaterial(CCMaterials.cosmichalcum, "Cosmichalcum", "Template flavor text", "Template description");

		add("constructs_casting.modifier.swiftcasting.requirement", "Requires the Casting ability to be applied first.");
		addModifier(CCModifiers.IMBUED.getId(), "Imbued", "One more spell!", "Allows the tool to be imbued with a spell. Disallowed on swords, since they can already be imbued.");
		addModifier(CCModifiers.ENCYCLOPEDIC.getId(), "Encyclopedic", "Well read", "Allows the Slimy Spellbook to function as an encyclopedia.");

		addModifier(CCModifiers.ARCANE, "Arcane" ,"Mana-licious!", "Grants +25 max mana.");
		addModifier(CCModifiers.ANTIMAGIC.getId(), "Antimagic", "Self-explanatory.", "Grants +2 damage against magic users.");
		add("modifier.constructs_casting.antimagic.damage_boost", "Antimagic Damage");
		addModifier(CCModifiers.SPELL_PROTECTION, "Spell Protection", "Diabolical!", "Grants +10% resistance against spells. (Different from Magic Protection)");
		add("modifier.constructs_casting.spell_protection.resistance", "Spell Resistance");
		addModifier(CCModifiers.ANTIFROST.getId(), "Antifrost", "Don't drink it!", "Grants +3 damage per level to frozen targets.");
		add("modifier.constructs_casting.antifrost.damage_boost", "Antifrost Damage");
		addModifier(CCModifiers.SPELLBOUND, "Spellbound", "Jack of all trades!", "Grants +5% power to all types of spells.");
		addModifier(CCModifiers.SPELLBOOK_STRAP.getId(), "Spellbook Strap", "Twice the spells, twice the fun!", "Interacting with the leggings allows swapping your spellbook with another one in the leggings' inventory.");

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

		addModifier(CCModifiers.SPELL_DISPULSION, "Spell Dispulsion", "Not to be confused with Spell Protection", "Grants +7.5% Spell Resistance");
		addModifier(CCModifiers.FIRE_DISPULSION, "Fire Dispulsion", "The power of the sun in the palm of my hand", "Grants +15% Fire Spell Resistance.");
		addModifier(CCModifiers.ICE_DISPULSION, "Ice Dispulsion", "Ice to meet you", "Grants +15% Ice Spell Resistance.");
		addModifier(CCModifiers.LIGHTNING_DISPULSION, "Lightning Dispulsion", "Surge protection", "Grants +15% Lightning Spell Resistance.");
		addModifier(CCModifiers.ENDER_DISPULSION, "Ender Dispulsion", "Take that, globeheads!", "Grants +15% Ender Spell Resistance.");
		addModifier(CCModifiers.HOLY_DISPULSION, "Holy Dispulsion", "Neither the power of god nor anime", "Grants +15% Holy Spell Resistance.");
		addModifier(CCModifiers.BLOOD_DISPULSION, "Blood Dispulsion", "Dirty haemophiles", "Grants +15% Blood Spell Resistance.");
		addModifier(CCModifiers.EVOCATION_DISPULSION, "Evocation Dispulsion", "Hrmmm!", "Grants +15% Evocation Spell Resistance.");
		addModifier(CCModifiers.NATURE_DISPULSION, "Nature Dispulsion", "Insert allegory for AI here", "Grants +15% Nature Spell Resistance.");

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
//		addItem(CCItems.arcaniumApple, "Arcanium Apple");

        addItem(CCItems.spellbookPlating, "Spellbook Plating");
        add(CCItems.spellbookPlatingCast.getSand().getDescriptionId(), "Spellbook Plating Sand Cast");
		add(CCItems.spellbookPlatingCast.get().getDescriptionId(), "Spellbook Plating Gold Cast");
		add(CCItems.spellbookPlatingCast.getRedSand().getDescriptionId(), "Spellbook Plating Red Sand Cast");
        addItem(CCItems.spellbookCover, "Spellbook Cover");
        addItem(CCItems.pages, "Pages");

		add("gui.constructs_casting.fluid.bottle", "%s Bottles");
        add("ui.constructs_casting.spellbook_has_slots", "Cannot modify, remove spells from spellbook first");

		addItem(CCItems.wizardslimeBall, "Wizardslime Ball");
		add("school.constructs_casting.slime", "Slime");
		addSpell(CCSpells.FREEZE_SPELL, "Freeze", "Rapidly cools down the targeted Casting Table or Basin, instantly finishing the casting process. Only works on molten metals.");
		add("spell.constructs_casting.freeze.invalid_target", "Invalid target!");
//		addSpell(CCSpells.SLIMEBALL_SPELL, "Slimeball", "Lobs a random slimeball, capable of bouncing off of blocks and enemies alike. The higher level, the bouncier.");
		add("ui.constructs_casting.slimeball.max_bounces", "%s Bounces");
		add("ui.constructs_casting.enderference_anti_teleport", "Your current affliction prevents you from teleporting...");
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

		addFluid(CCFluids.moltenCrystallizedCoral, "Molten Crystallized Coral", "");
		addFluid(CCFluids.gasifiedRedstone, "Redstone", "");
		addFluid(CCFluids.moltenPearl, "Molten Pearl", "");

		addFluid(CCFluids.abyssalEssence, "Abyssal Essence", "");
		addFluid(CCFluids.technomancyEssence, "Technomancy Essence", "");
		addFluid(CCFluids.aquaEssence, "Aqua Essence", "");

	    add("item.tconstruct.creative_slot.affinity", "Creative Affinity Slot");
        add("stat.tconstruct.slot.prefix.affinity", "Affinity Slots: ");
        add("stat.tconstruct.slot.display.affinity", "affinity");
		add("modifier.tconstruct.rebalanced.affinity", "Affinity");


		addModifier(CCModifiers.IMPROVEABLE, "Improvable", "Upgrades are good. But they can be better.", "Adds two bonus Affinity slots to the tool.");
        addModifier(CCModifiers.REGROWTH, "Regrowth", "The best brown thing!", "Increases Mana Regeneration by 15%.");
    	addModifier(CCModifiers.CONSERVING.getId(), "Conserving", "Great deals all day!", "Reduces the mana cost of casting spells by 10.");
        addModifier(CCModifiers.EXPEDIENT, "Expedient", "In a big-time rush?", "Reduces spell cast time by 10%.");
        addModifier(CCModifiers.SOLAR_CHARGED.getId(), "Solar Charged", "The best green thing!", "Empowers Mana Regeneration while under sunlight.");
		addModifier(CCModifiers.THICK_SKINNED, "Thick-Skinned", "You monster", "Increases spell power and cooldowns in hot biomes, reduces spell power and cooldowns in cold biomes.");
        add("modifier.constructs_casting.thick_skinned.spell_power", "Thick-Skinned Spell Power");
        add("modifier.constructs_casting.thick_skinned.cooldown_reduction", "Thick-Skinned Cooldown Reduction");
        add("modifier.constructs_casting.solar_charged.boost", "Mana Regen In Sun");
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
