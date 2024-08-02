package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY;

public class CCSpells {

	public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, ConstructsCasting.MOD_ID);


	public static void register(IEventBus eventBus) {
		SPELLS.register(eventBus);
		Attributes.ATTRIBUTES.register(eventBus);
		Schools.SCHOOLS.register(eventBus);
	}

	public static final RegistryObject<AbstractSpell> FREEZE_SPELL = registerSpell(new FreezeSpell());

	public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
		return SPELLS.register(spell.getSpellName(), () -> spell);
	}



	public static class Attributes {
		public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ConstructsCasting.MOD_ID);

		public static final RegistryObject<Attribute> SLIME_POWER = newPowerAttribute("slime");

		public static final RegistryObject<Attribute> SLIME_RESIST = newResistanceAttribute("slime");

		private static RegistryObject<Attribute> newResistanceAttribute(String id) {
			return ATTRIBUTES.register(id + "_magic_resist", () -> (new MagicRangedAttribute("attribute.constructs_casting." + id + "_magic_resist", 1.0D, -100, 100).setSyncable(true)));
		}

		private static RegistryObject<Attribute> newPowerAttribute(String id) {
			return ATTRIBUTES.register(id + "_spell_power", () -> (new MagicRangedAttribute("attribute.constructs_casting." + id + "_spell_power", 1.0D, -100, 100).setSyncable(true)));
		}

	}



	public static class Schools {
		public static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(SchoolRegistry.SCHOOL_REGISTRY_KEY, ConstructsCasting.MOD_ID);

		public static final ResourceLocation SLIME_LOC = ConstructsCasting.id("slime");
		public static final RegistryObject<SchoolType> SLIME = registerSchool(new SchoolType(SLIME_LOC,
				CCItems.Tags.SLIME_FOCUS,
				Component.translatable("school.constructs_casting.slime").withStyle(Style.EMPTY.withColor(0x119c3b)),
				LazyOptional.of(Attributes.SLIME_POWER::get),
				LazyOptional.of(Attributes.SLIME_RESIST::get),
				LazyOptional.of(() -> SoundEvents.SLIME_BLOCK_BREAK)
		));

		private static RegistryObject<SchoolType> registerSchool(SchoolType schoolType) {
			return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
		}

	}
}
