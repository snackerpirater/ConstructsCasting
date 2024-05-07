package com.snackpirate.constructscasting;


import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.util.ConstantFluidContainerWrapper;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CCEvents {
	private static AttachCapabilitiesEvent<ItemStack> event;
	@SubscribeEvent
	static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		CCEvents.event = event;
		ItemStack stack = event.getObject();
		itemPouring(stack, ItemRegistry.LIGHTNING_BOTTLE.get(), CCFluids.liquidLightning, 250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(stack, ItemRegistry.INK_COMMON.get(),       CCFluids.commonInk,       250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(stack, ItemRegistry.INK_UNCOMMON.get(),     CCFluids.uncommonInk,     250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(stack, ItemRegistry.INK_RARE.get(),         CCFluids.rareInk,         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(stack, ItemRegistry.INK_EPIC.get(),         CCFluids.epicInk,         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(stack, ItemRegistry.INK_LEGENDARY.get(),    CCFluids.legendaryInk,    250, Items.GLASS_BOTTLE.getDefaultInstance());
	}
	public static void itemPouring(ItemStack itemStack, Item input, FluidObject<? extends Fluid> fluidObject, int amount, ItemStack output) {
		if (itemStack.getItem().equals(input)) {
			event.addCapability(
					fluidObject.getId(),
					new ConstantFluidContainerWrapper(new FluidStack(fluidObject.get(), amount), itemStack, output)
			);
		}
	}
	@SubscribeEvent
	static void swiftcastingHandleInput(MovementInputUpdateEvent event) {
		if (ClientMagicData.isCasting() &&
				ModifierUtil.getModifierLevel(event.getEntity().getItemInHand(InteractionHand.MAIN_HAND), CCModifiers.SWIFTCASTING) > 0) {
			event.getInput().leftImpulse *= 5;
			event.getInput().forwardImpulse *= 5;
		}
	}
}
