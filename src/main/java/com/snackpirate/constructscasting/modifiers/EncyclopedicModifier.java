package com.snackpirate.constructscasting.modifiers;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.client.book.TinkerBook;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.shared.item.TinkerBookItem;

public class EncyclopedicModifier extends NoLevelsModifier implements GeneralInteractionModifierHook {
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
		hookBuilder.addHook(this, ModifierHooks.GENERAL_INTERACT);
		super.registerHooks(hookBuilder);
	}

	/**
	 * @param tool     Tool performing interaction
	 * @param modifier Modifier instance
	 * @param player   Interacting player
	 * @param hand     Hand used for interaction
	 * @param source   Source of the interaction
	 */
	@Override
	public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
		ConstructsCasting.LOGGER.info("encyclopedic use");
		if (player.level().isClientSide) TinkerBook.getBook(TinkerBookItem.BookType.ENCYCLOPEDIA).openGui(hand, player.getItemInHand(hand));
		return InteractionResult.SUCCESS;
	}
}
