package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CCAttributes {

	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ConstructsCasting.MOD_ID);

	public static void register(IEventBus eventBus) {
		ATTRIBUTES.register(eventBus);
	}
	public static final RegistryObject<Attribute> SLIME_MAGIC_RESIST = newResistanceAttribute("slime");
	public static final RegistryObject<Attribute> SLIME_MAGIC_POWER = newPowerAttribute("slime");


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
