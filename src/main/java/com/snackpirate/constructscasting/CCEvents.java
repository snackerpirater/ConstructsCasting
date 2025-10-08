package com.snackpirate.constructscasting;


import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.items.ModifiableSpellbookItem;
import com.snackpirate.constructscasting.items.ModifiableSpellbookRenderer;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import com.snackpirate.constructscasting.spells.CCEntities;
import com.snackpirate.constructscasting.spells.slime.slimeball.SlimeballProjectileRenderer;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.util.ConstantFluidContainerWrapper;
import slimeknights.tconstruct.library.events.ToolEquipmentChangeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.shared.TinkerEffects;
import slimeknights.tconstruct.tools.item.ModifiableSwordItem;
import slimeknights.tconstruct.tools.logic.ToolEvents;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static slimeknights.tconstruct.tools.logic.ModifierEvents.SOULBOUND;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CCEvents {
	private static final String SOULBOUND_SLOT = "tic_soulbound_slot";
	@SubscribeEvent
	static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		itemPouring(event, stack, ItemRegistry.LIGHTNING_BOTTLE.get(), CCFluids.liquidLightning, 250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_COMMON.get(),       CCFluids.commonInk,       250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_UNCOMMON.get(),     CCFluids.uncommonInk,     250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_RARE.get(),         CCFluids.rareInk,         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_EPIC.get(),         CCFluids.epicInk,         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_LEGENDARY.get(),    CCFluids.legendaryInk,    250, Items.GLASS_BOTTLE.getDefaultInstance());
	}
	public static void itemPouring(AttachCapabilitiesEvent<ItemStack> event, ItemStack itemStack, Item input, FluidObject<? extends Fluid> fluidObject, int amount, ItemStack output) {
		if (itemStack.getItem().equals(input)) {
			event.addCapability(
					fluidObject.getId(),
					new ConstantFluidContainerWrapper(new FluidStack(fluidObject.get(), amount), itemStack, output)
			);
		}
	}

	//if the target has the enderference effect, cancel teleportations
	@SubscribeEvent
	static void enderferenceAntiSpell(SpellPreCastEvent event) {
		Player entity = event.getEntity();
		if (entity.hasEffect(TinkerEffects.enderference.get())) {String spellId = event.getSpellId();
			if (spellId.equals("irons_spellbooks:teleport") || spellId.equals("irons_spellbooks:blood_step") || spellId.equals("irons_spellbooks:frost_step")) {
				entity.level().playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 2f, 0.2f + Utils.random.nextFloat() * .2f);
				entity.displayClientMessage(Component.translatable("ui.constructs_casting.enderference_anti_teleport").withStyle(ChatFormatting.RED), true);
				event.setCanceled(true);
			}
		}
	}
	@SubscribeEvent
	static void imbueSlotOnSwords(ToolEquipmentChangeEvent event) {
		ItemStack replacement = event.getContext().getReplacement();
		if (replacement.getItem() instanceof ModifiableSwordItem && !ISpellContainer.isSpellContainer(replacement)) {
			var container = ISpellContainer.create(1, true, false);
			container.save(replacement);
		}
		else if (replacement.getItem() instanceof ModifiableArmorItem armor && armor.getEquipmentSlot() == EquipmentSlot.CHEST && !ISpellContainer.isSpellContainer(replacement)) {
			var container = ISpellContainer.create(1, true, true);
			container.save(replacement);
		}
	}
	@SubscribeEvent
	static void soulboundSpellbookDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (!entity.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && entity instanceof Player player && !(player instanceof FakePlayer)) {
			// start with the hotbar, must be soulbound or soul belt
			CuriosApi.getCuriosInventory(player).ifPresent((handler) -> {
				ItemStack spellbook = handler.getCurios().get("spellbook").getStacks().getStackInSlot(0);
				if (!spellbook.isEmpty() && (ModifierUtil.checkVolatileFlag(spellbook, SOULBOUND))) {
					spellbook.getOrCreateTag().putInt(SOULBOUND_SLOT, 999 /*a great idea*/);
			}
		});
		}
	}
	@SubscribeEvent
	static void soulboundSpellbookDrop(LivingDropsEvent event) {
		// only care about real players with keep inventory off
		LivingEntity entity = event.getEntity();
		if (!entity.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && entity instanceof Player player && !(entity instanceof FakePlayer)) {
			Collection<ItemEntity> drops = event.getDrops();
			Iterator<ItemEntity> iter = drops.iterator();
			Inventory inventory = player.getInventory();
			List<ItemEntity> takenSlot = new ArrayList<>();
			while (iter.hasNext()) {
				ItemEntity itemEntity = iter.next();
				ItemStack stack = itemEntity.getItem();
				// find items with our soulbound tag set and move them back into the inventory, will move them over later
				CompoundTag tag = stack.getTag();
				if (tag != null && tag.contains(SOULBOUND_SLOT, Tag.TAG_ANY_NUMERIC)) {
					int slot = tag.getInt(SOULBOUND_SLOT);
					// return the tool to its requested slot if possible, remove from the drops
					if (slot == 999) {
						CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
							handler.setEquippedCurio("spellbook", 0, stack);
						});
						iter.remove();
						// don't clear the tag yet, we need it one last time for player clone
					}
				}
				// handle items that did not get their requested slot last, to ensure they don't take someone else's slot while being added to a default
//			for (ItemEntity itemEntity : takenSlot) {
//				ItemStack stack = itemEntity.getItem();
//				if (!inventory.add(stack)) {
//					// last resort, somehow we just cannot put the stack anywhere, so drop it on the ground
//					// this should never happen, but better to be safe
//					// ditch the soulbound slot tag, to prevent item stacking issues
//					CompoundTag tag = stack.getTag();
//					if (tag != null) {
//						tag.remove(SOULBOUND_SLOT);
//						if (tag.isEmpty()) {
//							stack.setTag(null);
//						}
//					}
//					drops.add(itemEntity);
//				}
//			}
			}
		}
	}
	@SubscribeEvent
	static void soulboundSpellbookClone(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			return;
		}
		Player original = event.getOriginal();
		Player clone = event.getEntity();
		// inventory already copied
		if (clone.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || original.isSpectator()) {
			return;
		}
		// find items with the soulbound tag set and move them over
		LazyOptional<ICuriosItemHandler> originalInv = CuriosApi.getCuriosInventory(original);
		LazyOptional<ICuriosItemHandler> cloneInv = CuriosApi.getCuriosInventory(clone);
			originalInv.ifPresent((handler) -> handler.findCurio("spellbook", 0).ifPresent(slotResult -> {
				ItemStack stack = slotResult.stack();
				if (!stack.isEmpty()) {
					CompoundTag tag = stack.getTag();
					if (tag != null && tag.contains(SOULBOUND_SLOT, Tag.TAG_ANY_NUMERIC)) {
						cloneInv.ifPresent(handler2 -> handler2.setEquippedCurio("spellbook", 0, stack));
						// remove the slot tag, clear the tag if needed
						tag.remove(SOULBOUND_SLOT);
						if (tag.isEmpty()) {
							stack.setTag(null);
						}
					}
				}

			}));
	}
	//stuff to run modifiers on equipped spellbooks
	@SubscribeEvent(priority = EventPriority.LOWEST)
	static void livingHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		DamageSource source = event.getSource();
		EquipmentContext context = new EquipmentContext(entity);
		int vanillaModifier = 0;
		float modifierValue = 0;
		float originalDamage = event.getAmount();
        ConstructsCasting.LOGGER.info("living hurt: {}", event.getAmount());

//        if (CuriosApi.getCuriosInventory(entity).map(handler -> handler.findCurio("spellbook", 0)).isPresent()) {
//            CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurio("spellbook", 0).ifPresent(slotResult -> {
//
//            }));
//            ItemStack spellbook = CuriosApi.getCuriosInventory(entity).map(handler -> handler.findCurio("spellbook", 0).get().stack()).orElse(ItemStack.EMPTY);
//            ToolStack toolStack = ToolStack.from(spellbook);
//            for (ModifierEntry entry : toolStack.getModifierList()) {
//                originalDamage = entry.getHook(ModifierHooks.MODIFY_DAMAGE).modifyDamageTaken(toolStack, entry, context, EquipmentSlot.LEGS, source, originalDamage, OnAttackedModifierHook.isDirectDamage(source));
//            }
//
//            if (!toolStack.isBroken()) {
//                for (ModifierEntry entry : toolStack.getModifierList()) {
//                    modifierValue = entry.getHook(ModifierHooks.PROTECTION).getProtectionModifier(toolStack, entry, context, EquipmentSlot.LEGS, source, modifierValue);
//                }
//            }
//
//        }

	}

	@SubscribeEvent
	static void livingDamage(LivingDamageEvent event) {
		LivingEntity entity = event.getEntity();
		DamageSource source = event.getSource();

	}


	@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ForgeClientEvents {
		@SubscribeEvent
		static void swiftcastingHandleInput(MovementInputUpdateEvent event) {
			if (ClientMagicData.isCasting() &&
					ModifierUtil.getModifierLevel(event.getEntity().getItemInHand(InteractionHand.MAIN_HAND), CCModifiers.SWIFTCASTING) > 0) {
				event.getInput().leftImpulse *= 5;
				event.getInput().forwardImpulse *= 5;
			}
		}

	}
	@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ModClientEvents {
		@SubscribeEvent
		static void registerCurioRenderers(FMLClientSetupEvent e) {
			CuriosRendererRegistry.register(CCItems.slimySpellbook.get(), ModifiableSpellbookRenderer::new);
			CuriosRendererRegistry.register(CCItems.platedSpellbook.get(), ModifiableSpellbookRenderer::new);
		}
		@SubscribeEvent
		static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
			event.registerEntityRenderer(CCEntities.SLIMEBALL_PROJECTILE.get(), SlimeballProjectileRenderer::new);
		}
	}
}
