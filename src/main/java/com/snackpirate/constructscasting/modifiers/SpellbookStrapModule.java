package com.snackpirate.constructscasting.modifiers;

import com.google.common.collect.ImmutableSet;
import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.registry.GenericLoaderRegistry;
import slimeknights.tconstruct.library.json.TinkerLoadables;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.KeybindInteractModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Set;

public record SpellbookStrapModule(Set<TooltipKey> keys) implements ModifierModule, KeybindInteractModifierHook {
	public static final RecordLoadable<SpellbookStrapModule> LOADER = RecordLoadable.create(TinkerLoadables.TOOLTIP_KEY.set().requiredField("on_key", SpellbookStrapModule::keys), SpellbookStrapModule::new);
	private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.<SpellbookStrapModule>defaultHooks(ModifierHooks.ARMOR_INTERACT, ModifierHooks.ARMOR_INTERACT);

	@Override
	public RecordLoadable<SpellbookStrapModule> getLoader() {
		return LOADER;
	}
	public SpellbookStrapModule(TooltipKey... keys) {
		this(ImmutableSet.copyOf(keys));
	}

	@Override
	public List<ModuleHook<?>> getDefaultHooks() {
		return DEFAULT_HOOKS;
	}
	@Override
	public boolean startInteract(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot equipmentSlot, TooltipKey keyModifier) {
		if (keys.contains(keyModifier)) {
			Level level = player.level();
			if (level.isClientSide) {
				return true;
			}
			// offhand must be able to go in the pants
			CuriosApi.getCuriosInventory(player).ifPresent((handler) -> {
				ItemStack spellbook = handler.getCurios().get("spellbook").getStacks().getStackInSlot(1);
//				ConstructsCasting.LOGGER.info(spellbook.toString());
			});
//			if (offhand.isEmpty() || !ToolInventoryCapability.isBlacklisted(offhand)) {
//				ToolInventoryCapability.InventoryModifierHook inventory = modifier.getHook(ToolInventoryCapability.HOOK);
//				int slots = inventory.getSlots(tool, modifier);
//
//				// new offhand is first slot
//				ItemStack newOffhand = inventory.getStack(tool, modifier, 0);
//				player.setItemInHand(InteractionHand.OFF_HAND, newOffhand);
//				// shift all other slots back by 1;
//				for (int i = 1; i < slots; i++) {
//					inventory.setStack(tool, modifier, i - 1, inventory.getStack(tool, modifier, i));
//				}
//				// put old offhand in last slot
//				inventory.setStack(tool, modifier, slots - 1, offhand);
//
//				// sound effect
//				if (!newOffhand.isEmpty() || !offhand.isEmpty()) {
//					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.PLAYERS, 1.0f, 1.0f);
//				}
//				return true;
//			}
		}
		return true;
	}
}
