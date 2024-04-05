package com.snackpirate.constructscasting.mixin;

import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

@Mixin(ManaBarOverlay.class)
public class ManaBarOverlayMixin {
	@Inject(method="shouldShowManaBar", at=@At(value="HEAD"), cancellable = true, remap = false)
	private static void shouldShowManaBar(Player player, CallbackInfoReturnable<Boolean> cir) {
		if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ModifiableItem) {
			cir.setReturnValue(ModifierUtil.getModifierLevel(player.getItemInHand(InteractionHand.MAIN_HAND), CCModifiers.CASTING.getId()) > 0);
		}
		else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ModifiableItem) {
			cir.setReturnValue(ModifierUtil.getModifierLevel(player.getItemInHand(InteractionHand.OFF_HAND), CCModifiers.CASTING.getId()) > 0);
		}
	}
}
