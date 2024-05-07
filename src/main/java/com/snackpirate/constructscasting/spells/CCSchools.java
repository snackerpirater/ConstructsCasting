package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CCSchools
{
	private static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(ConstructsCasting.id("schools"), ConstructsCasting.MOD_ID);

	public static final Supplier<IForgeRegistry<SchoolType>> REGISTRY = SCHOOLS.makeRegistry(() -> new RegistryBuilder<SchoolType>().disableSaving().disableOverrides());

	public static void register(IEventBus eventBus) {
		SCHOOLS.register(eventBus);
		eventBus.addListener(SchoolRegistry::clientSetup);
	}


	private static RegistryObject<SchoolType> registerSchool(SchoolType schoolType) {
		return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
	}

	public static SchoolType getSchool(ResourceLocation resourceLocation) {
		return REGISTRY.get().getValue(resourceLocation);
	}
	public static final ResourceLocation SLIME_RESOURCE = ConstructsCasting.id("slime");

	public static final RegistryObject<SchoolType> SLIME = registerSchool(new SchoolType(SLIME_RESOURCE, ItemTags.create(ConstructsCasting.id("slime_focus")), Component.translatable("school.constructs_casting.slime").withStyle(ChatFormatting.GREEN), LazyOptional.of(CCAttributes.SLIME_MAGIC_POWER::get), LazyOptional.of(CCAttributes.SLIME_MAGIC_RESIST::get), LazyOptional.of(() -> SoundEvents.SLIME_HURT_SMALL)));

}
