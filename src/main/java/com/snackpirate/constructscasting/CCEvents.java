package com.snackpirate.constructscasting;


import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
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
import slimeknights.tconstruct.library.events.ToolEquipmentChangeEvent;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;

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
	//if the target has the enderference effect, cancel teleportations
	@SubscribeEvent
	static void enderferenceAntiSpell(SpellPreCastEvent event) {
		if (event.getEntity().hasEffect(TinkerModifiers.enderferenceEffect.get())) {
			event.getEntity().level.playSound(null, event.getEntity().blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 2f, 0.2f + Utils.random.nextFloat() * .2f);
			event.setCanceled(event.getSpellId().equals("irons_spellbooks:teleport")
					|| event.getSpellId().equals("irons_spellbooks:blood_step")
					|| event.getSpellId().equals("irons_spellbooks:frost_step"));
		}
	}
	@SubscribeEvent
	static void imbueSlotOnSwords(ToolEquipmentChangeEvent event) {
		ItemStack replacement = event.getContext().getReplacement();
		if (replacement.getItem() instanceof ModifiableSwordItem && !ISpellContainer.isSpellContainer(replacement)) {
			var container = ISpellContainer.create(1, true, false);
			container.save(replacement);
		}
		else if (replacement.getItem() instanceof ModifiableArmorItem armor && armor.getSlot() == EquipmentSlot.CHEST && !ISpellContainer.isSpellContainer(replacement)) {
			var container = ISpellContainer.create(1, true, true);
			container.save(replacement);
		}
	}
}
