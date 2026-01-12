package com.snackpirate.constructscasting.fluids;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.data.tinkering.AbstractFluidEffectProvider;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;
import slimeknights.tconstruct.library.modifiers.fluid.FluidMobEffect;
import slimeknights.tconstruct.library.modifiers.fluid.TimeAction;
import slimeknights.tconstruct.library.modifiers.fluid.entity.RestoreHungerFluidEffect;
import slimeknights.tconstruct.library.recipe.FluidValues;

public class CCFluidEffects extends AbstractFluidEffectProvider {
	public CCFluidEffects(PackOutput packOutput, String modId) {
		super(packOutput, modId);
	}

	@Override
	protected void addFluids() {
		addFluid(CCFluids.Tags.essenceOf("arcane"), FluidValues.SIP).addEntityEffect(ADD_MANA);
		addFluid(CCFluids.Tags.essenceOf("cinder"), FluidValues.SIP).addEntityEffect(DEPLETE_MANA);
		addFluid(CCFluids.Tags.MOLTEN_ARCANIUM, FluidValues.NUGGET).fireDamage(2f).addEffect(FluidMobEffect.builder().effect(MobEffects.magicEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.MOLTEN_EXILITE, FluidValues.NUGGET).fireDamage(2f).addEffect(FluidMobEffect.builder().effect(MobEffects.magicVulnerability.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("blood"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.bloodEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("ender"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.enderEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("evocation"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.evocationEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("fire"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.fireEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("holy"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.holyEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("ice"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.iceEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("lightning"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.lightningEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.essenceOf("nature"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.natureEmpowerment.get(), 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.MOLTEN_ARCANE_SALVAGE, FluidValues.NUGGET).addEffect(FluidMobEffect.builder().effect(MobEffects.recoveryEmpowerment.get(), 100, 2), TimeAction.SET);
		addFluid(CCFluids.potatoStew, FluidValues.SIP).addEntityEffect(new RestoreHungerFluidEffect(2, 0.48f, false, ItemOutput.fromItem(CCItems.potatoStewBowl.get())));
		addFluid(CCFluids.poisonousPotatoStew, FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffectRegistry.BLIGHT.get(), 100, 1), TimeAction.SET);

		addFluid(CCFluids.squidInk, FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(net.minecraft.world.effect.MobEffects.BLINDNESS, 100, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.ink("common"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.inkyImpairment.get(), 160, 1), TimeAction.SET);
		addFluid(CCFluids.Tags.ink("uncommon"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.inkyImpairment.get(), 170, 2), TimeAction.SET);
		addFluid(CCFluids.Tags.ink("rare"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.inkyImpairment.get(), 180, 3), TimeAction.SET);
		addFluid(CCFluids.Tags.ink("epic"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.inkyImpairment.get(), 190, 4), TimeAction.SET);
		addFluid(CCFluids.Tags.ink("legendary"), FluidValues.SIP).addEffect(FluidMobEffect.builder().effect(MobEffects.inkyImpairment.get(), 200, 5), TimeAction.SET);
	}

	@Override
	public String getName() {
		return "Construct's Casting Fluid Effect Provider";
	}

	public static FluidEffect<FluidEffectContext.Entity> DEPLETE_MANA = FluidEffect.simple((fluid, level, context, action) -> {
		if (action.execute()) {
			LivingEntity living = context.getLivingTarget();
			MagicData playerMagicData = MagicData.getPlayerMagicData(living);
			playerMagicData.addMana(-50 * level.value());
			if (living instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(playerMagicData));
			}
		}
		return level.value();
	});

	public static FluidEffect<FluidEffectContext.Entity> ADD_MANA = FluidEffect.simple((fluid, level, context, action) -> {
		if (action.execute()) {
			LivingEntity living = context.getLivingTarget();
			MagicData playerMagicData = MagicData.getPlayerMagicData(living);
			playerMagicData.addMana(10 * level.value());
			if (living instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(playerMagicData));
			}
		}
		return level.value();
	});

	public static class MobEffects {
		public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ConstructsCasting.MOD_ID);

		public static void register(IEventBus eventBus) {
			EFFECTS.register(eventBus);
		}

		public static RegistryObject<MobEffect> magicEmpowerment = EFFECTS.register("magic_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xc5e1ff, true).addAttributeModifier(AttributeRegistry.SPELL_POWER.get(), "e39931d9-140b-493d-8c4d-2a1e89393024", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> magicVulnerability = EFFECTS.register("magic_vulnerability", () -> new TinkerEffect(MobEffectCategory.HARMFUL, 0x55617f, true).addAttributeModifier(AttributeRegistry.SPELL_RESIST.get(), "dfccaea6-24db-4bc6-ac68-2f54d1c00232", -0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> inkyImpairment = EFFECTS.register("inky_impairment", () -> new TinkerEffect(MobEffectCategory.HARMFUL, 0x15111f, true).addAttributeModifier(AttributeRegistry.SPELL_POWER.get(), "bfccaea6-24db-4bc6-ac68-2f54d1c00232", -0.15, AttributeModifier.Operation.MULTIPLY_BASE));

		public static RegistryObject<MobEffect> fireEmpowerment = EFFECTS.register("fire_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xffa765, true).addAttributeModifier(AttributeRegistry.FIRE_SPELL_POWER.get(), "713959a9-bed7-427d-975f-809ed994cd17", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> iceEmpowerment = EFFECTS.register("ice_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0x6dfff5, true).addAttributeModifier(AttributeRegistry.ICE_SPELL_POWER.get(), "32cae3f7-78fd-4ea4-b263-0884524384ae", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> lightningEmpowerment = EFFECTS.register("lightning_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xe0defc,true).addAttributeModifier(AttributeRegistry.LIGHTNING_SPELL_POWER.get(), "2b98d41e-509a-4313-a7ab-a9f2131f5149", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> holyEmpowerment = EFFECTS.register("holy_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xfffab7, true).addAttributeModifier(AttributeRegistry.HOLY_SPELL_POWER.get(), "db523eaa-ccbe-4a9e-8ecc-a4e22dc53a95", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> enderEmpowerment = EFFECTS.register("ender_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xdb74ff, true).addAttributeModifier(AttributeRegistry.ENDER_SPELL_POWER.get(), "db64740a-572f-4a52-92ba-1c32b9bdc7a5", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> bloodEmpowerment = EFFECTS.register("blood_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xff7070, true).addAttributeModifier(AttributeRegistry.BLOOD_SPELL_POWER.get(), "6a0239c2-c775-4214-8b52-31ea7bd5fbfa", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> evocationEmpowerment = EFFECTS.register("evocation_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0x99ff9c, true).addAttributeModifier(AttributeRegistry.EVOCATION_SPELL_POWER.get(), "ecd54855-033c-4309-8866-3d567d6e15f0", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> natureEmpowerment = EFFECTS.register("nature_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xb0f869, true).addAttributeModifier(AttributeRegistry.NATURE_SPELL_POWER.get(), "6fdc0348-9e68-4165-9d93-43dc2fdf796e", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
		public static RegistryObject<MobEffect> recoveryEmpowerment = EFFECTS.register("recovery_empowerment", () -> new TinkerEffect(MobEffectCategory.BENEFICIAL, 0xede4e6, true).addAttributeModifier(AttributeRegistry.COOLDOWN_REDUCTION.get(), "6fdc0fff-9e68-4165-9d93-43dc2fdf796e", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));

		public static RegistryObject<TinkerEffect> frostbite = EFFECTS.register("frostbite", () -> new TinkerEffect(MobEffectCategory.HARMFUL, 0x6dfff5, true));
	}
}
