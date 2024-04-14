package com.snackpirate.constructscasting.mixin;

import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.gui.overlays.ActiveSpellOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

@Mixin(ActiveSpellOverlay.class)
public class ActiveSpellOverlayMixin {
	@Inject(method = "hasRightClickCasting", at=@At(value = "HEAD"), cancellable = true, remap = false)
	private static void hasRightClickCasting(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item instanceof ModifiableItem && Minecraft.getInstance().player != null) {
				cir.setReturnValue(ModifierUtil.getModifierLevel(Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND), CCModifiers.CASTING.getId()) + ModifierUtil.getModifierLevel(Minecraft.getInstance().player.getItemInHand(InteractionHand.OFF_HAND), CCModifiers.CASTING.getId()) > 0);
		}
	}
}
