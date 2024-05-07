package com.snackpirate.constructscasting.mixin;

import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;

@Mixin(Utils.class)
public class UtilsMixin {
	@Inject(method="canBeUpgraded", at=@At(value = "HEAD"), remap = false, cancellable = true)
	private static void canBeUpgraded(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		Item item = stack.getItem();
		if (item instanceof ModifiableArmorItem) cir.setReturnValue(false);
	}
}
