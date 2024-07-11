package com.snackpirate.constructscasting.modifiers;

import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CastingModifier extends NoLevelsModifier implements GeneralInteractionModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder builder) {
		builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
	}
	//copy staff code? copy staff code.
	@Override
	public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
		if (interactionSource == InteractionSource.LEFT_CLICK) return InteractionResult.FAIL;
		ItemStack itemStack = player.getItemInHand(interactionHand);
		SpellSelectionManager spellSelectionManager = new SpellSelectionManager(player);
		SpellSelectionManager.SelectionOption selectionOption = spellSelectionManager.getSelection();
		if (selectionOption == null || selectionOption.spellData.equals(SpellData.EMPTY)) {
			return InteractionResult.PASS;
		}
		SpellData spellData = selectionOption.spellData;

		if (player.level.isClientSide()) {
			if (ClientMagicData.isCasting()) {
				return InteractionResult.CONSUME;
			} else if (ClientMagicData.getPlayerMana() < spellData.getSpell().getManaCost(spellData.getLevel())
					|| ClientMagicData.getCooldowns().isOnCooldown(spellData.getSpell())
					|| !ClientMagicData.getSyncedSpellData(player).isSpellLearned(spellData.getSpell())) {
				return InteractionResult.PASS;
			} else {
				return InteractionResult.CONSUME;
			}
		}

		String castingSlot = interactionHand.ordinal() == 0 ? SpellSelectionManager.MAINHAND : SpellSelectionManager.OFFHAND;

		//TODO: Fix the bug that causes every OTHER continuous cast to cease
		if (spellData.getSpell().attemptInitiateCast(itemStack, spellData.getLevel(), player.level, player, selectionOption.getCastSource(), true, castingSlot)) {
			if (spellData.getSpell().getCastType().holdToCast()) {
				GeneralInteractionModifierHook.startUsing(iToolStackView, modifierEntry.getId(), player, interactionHand);
			}
			return InteractionResult.CONSUME;
		} else {
			return InteractionResult.FAIL;
		}
//		return null;
	}

	@Override
	public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
		return 7200;
	}

	@Override
	public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
		return UseAnim.BOW;
	}

	@Override
	public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
		GeneralInteractionModifierHook.finishUsing(tool);
		Utils.releaseUsingHelper(entity, tool.getItem().getDefaultInstance(), timeLeft);
	}

	@Override
	public int getPriority() {
		return 150;//anything above blocking (100) works, but just to be sure
	}
}
