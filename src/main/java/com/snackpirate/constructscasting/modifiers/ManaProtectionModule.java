package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.technical.SlotInChargeModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.shared.TinkerAttributes;

import javax.annotation.Nullable;
import java.util.List;

public record ManaProtectionModule(LevelingValue manaPerDamage, LevelingValue maxDamageReduction) implements ModifierModule, ModifyDamageModifierHook, TooltipModifierHook {
	private static final Component PROTECTION = Component.translatable("modifier.constructs_casting.mana_protection.resistance");
	public static final List<ModuleHook<?>> HOOKS = HookProvider.<ManaProtectionModule>defaultHooks(ModifierHooks.MODIFY_HURT, ModifierHooks.TOOLTIP);
	public static final RecordLoadable<ManaProtectionModule> LOADER = RecordLoadable.create(
			LevelingValue.LOADABLE.requiredField("mana_per_damage", ManaProtectionModule::manaPerDamage),
			LevelingValue.LOADABLE.requiredField("max_damage_reduction", ManaProtectionModule::maxDamageReduction),
			ManaProtectionModule::new
	);
	private static final TinkerDataCapability.TinkerDataKey<SlotInChargeModule.SlotInCharge> SLOT_KEY = TinkerDataCapability.TinkerDataKey.of(ConstructsCasting.id("mana_protection"));
	//here's how it works
	//incoming damage is reduced by a percentage (max percentage determined by modifier level)
	//mana is consumed from the wearer on hit, scaling with the damage blocked
	//which means bigger hits will consume more mana than smaller hits
	//4% protection per level, 48% prot absolute maximum (32% maximum of convenience)
	//Take 100 damage
	//32% = 32 damage blocked, 68 dealt
	//blocking 20 damage (a full healthbar) should cost 400 mana?
	@Override
	public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
		if (SlotInChargeModule.isInCharge(context.getTinkerData(), SLOT_KEY, slotType)) { //only run once across all pieces of armor
			int level = SlotInChargeModule.getLevel(context.getTinkerData(), SLOT_KEY, slotType);
			float reductionMult = Math.min(1 - maxDamageReduction.compute(level), (float) context.getEntity().getAttributeValue(TinkerAttributes.PROTECTION_CAP.get())); //90, 95
			//reduction mult is 0.68
			float damageToBlock = Math.min(amount, amount * (1-reductionMult));
			float manaConsumed = damageToBlock * manaPerDamage.compute(level);
			ConstructsCasting.LOGGER.info("incoming damage: {}\nreduction multiplier: {}\nreduction amount: {}\n mana consumed: {}", amount, reductionMult, damageToBlock, manaConsumed);
			if (manaConsumed <= MagicData.getPlayerMagicData(context.getEntity()).getMana()) { //would be nice if we didn't have enough mana then reduction would be scaled down, but i don't care
				MagicData.getPlayerMagicData(context.getEntity()).addMana(-manaConsumed);
				return amount * reductionMult;
			}
		}
		return amount;
	}

	@Override
	public void addModules(ModuleHookMap.Builder builder) {
		builder.addModule(new SlotInChargeModule(SLOT_KEY));
	}

	@Override
	public RecordLoadable<? extends ModifierModule> getLoader() {
		return LOADER;
	}

	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return HOOKS;
	}

	@Override
	public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
		TooltipModifierHook.addPercentBoost(modifier.getModifier(), PROTECTION, this.maxDamageReduction.compute(modifier.getLevel()), tooltip);
	}
}
