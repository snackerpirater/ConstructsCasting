package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.KeybindInteractModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.VolatileFlagModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.library.tools.capability.inventory.InventoryMenuModule;
import slimeknights.tconstruct.library.tools.capability.inventory.InventoryModule;
import slimeknights.tconstruct.library.tools.capability.inventory.ToolInventoryCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

public class SpellbookStrapModifier extends Modifier implements KeybindInteractModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		super.registerHooks(hookBuilder);
		hookBuilder.addHook(this, ModifierHooks.ARMOR_INTERACT);
		hookBuilder.addModule(InventoryModule.builder().pattern(new Pattern("constructs_casting:spellbook")).filter(ItemPredicate.tag(TagKey.create(ForgeRegistries.Keys.ITEMS, ResourceLocation.tryBuild("curios", "spellbook")))).slotsPerLevel(1));
		hookBuilder.addModule(InventoryMenuModule.SHIFT);
//		hookBuilder.addModule(new VolatileFlagModule(ToolInventoryCapability.INCLUDE_OFFHAND));
	}

	@Override
	public int getPriority() {
		return 95; //same as shield strap, can run one or the other
	}

	@Override
	public boolean startInteract(IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, TooltipKey keyModifier) {
//		ConstructsCasting.LOGGER.info("start interact");
		if (keyModifier == TooltipKey.NORMAL) {
//			ConstructsCasting.LOGGER.info("start interact 2");
			Level level = player.level();
			if (level.isClientSide) {
				return true;
			}
			ItemStack spellbook = CuriosApi.getCuriosInventory(player).map(handler -> handler.getStacksHandler("spellbook").map(stacks -> stacks.getStacks().getStackInSlot(0))).get().orElse(ItemStack.EMPTY);
//			ConstructsCasting.LOGGER.info(spellbook.toString());
//
			if (spellbook.isEmpty() || !ToolInventoryCapability.isBlacklisted(spellbook)) {
				ToolInventoryCapability.InventoryModifierHook inventory = modifier.getHook(ToolInventoryCapability.HOOK);
				int slots = inventory.getSlots(tool, modifier);

				// new offhand is first slot
				ItemStack newOffhand = inventory.getStack(tool, modifier, 0);
//				player.setItemInHand(InteractionHand.OFF_HAND, newOffhand);
				CuriosApi.getCuriosInventory(player).map(handler -> handler.getStacksHandler("spellbook").map(stacks -> {
					stacks.getStacks().setStackInSlot(0, newOffhand);
					return stacks;}));
				// shift all other slots back by 1;
                //not sure if this is necessary, may not want >1 level
				for (int i = 1; i < slots; i++) {
					inventory.setStack(tool, modifier, i - 1, inventory.getStack(tool, modifier, i));
				}
				// put old offhand in last slot
				inventory.setStack(tool, modifier, slots - 1, spellbook);

				// sound effect
				if (!newOffhand.isEmpty() || !spellbook.isEmpty()) {
					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.PLAYERS, 1.0f, 1.0f);
				}
				return true;
			}
		}
		return false;


	}
}
