package com.snackpirate.constructscasting;


import com.snackpirate.constructscasting.fluids.CCFluids;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.fluids.util.ConstantFluidContainerWrapper;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CCEvents {

	@SubscribeEvent
	static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (ForgeRegistries.ITEMS.getKey(event.getObject().getItem()).equals(new ResourceLocation(IronsSpellbooks.MODID, "lightning_bottle"))) {
			event.addCapability(
					new ResourceLocation(ConstructsCasting.MOD_ID, "liquid_lightning"),
					new ConstantFluidContainerWrapper(new FluidStack(CCFluids.liquidLightning.get(), 250), stack, Items.GLASS_BOTTLE.getDefaultInstance()));
		}
	}
}
