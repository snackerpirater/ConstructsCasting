package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.CCDamageTypes;
import com.snackpirate.constructscasting.CCSounds;
import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.spells.slime.slimeball.SlimeballSpell;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
//	public static final RegistryObject<AbstractSpell> SLIMEBALL_SPELL = registerSpell(new SlimeballSpell());

	public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
		return SPELLS.register(spell.getSpellName(), () -> spell);
	}



	@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
		@SubscribeEvent
		public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
			e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute.get())));
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
				LazyOptional.of(() -> CCSounds.SLIME_CAST.get()),
				CCDamageTypes.SLIME_MAGIC
		));

		private static RegistryObject<SchoolType> registerSchool(SchoolType schoolType) {
			return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
		}

	}
}
