package com.snackpirate.constructscasting;


import com.snackpirate.constructscasting.fluids.CCFluidEffects;
import com.snackpirate.constructscasting.fluids.CCFluids;
import com.snackpirate.constructscasting.items.CCItems;
import com.snackpirate.constructscasting.items.ModifiableSpellbookItem;
import com.snackpirate.constructscasting.items.ModifiableSpellbookRenderer;
import com.snackpirate.constructscasting.items.book.ArtificersGuideItem;
import com.snackpirate.constructscasting.spells.CCEntities;
import com.snackpirate.constructscasting.spells.slime.slimeball.SlimeballProjectileRenderer;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.fluids.util.ConstantFluidContainerWrapper;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.shared.CommonsClientEvents;
import slimeknights.tconstruct.shared.TinkerEffects;
import slimeknights.tconstruct.tools.data.ModifierIds;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;

import static slimeknights.tconstruct.tools.logic.ModifierEvents.SOULBOUND;

@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CCEvents {
	private static final String SOULBOUND_SLOT = "tic_soulbound_slot";
	@SubscribeEvent
	static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		itemPouring(event, stack, ItemRegistry.LIGHTNING_BOTTLE.get(), CCFluids.liquidLightning, 250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_COMMON.get(),       FluidRegistry.COMMON_INK.get(),  250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_UNCOMMON.get(),     FluidRegistry.UNCOMMON_INK.get(),     250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_RARE.get(),         FluidRegistry.RARE_INK.get(),         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_EPIC.get(),         FluidRegistry.EPIC_INK.get(),         250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.INK_LEGENDARY.get(),    FluidRegistry.LEGENDARY_INK.get(),    250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.BLOOD_VIAL.get(), FluidRegistry.BLOOD.get(),    250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.ICE_VENOM_VIAL.get(), FluidRegistry.ICE_VENOM_FLUID.get(),    250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.TIMELESS_SLURRY.get(), FluidRegistry.TIMELESS_SLURRY_FLUID.get(),    250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.GREATER_HEALING_POTION.get(), FluidRegistry.GREATER_HEALING_ELIXIR_FLUID.get(),    250, Items.GLASS_BOTTLE.getDefaultInstance());

		itemPouring(event, stack, ItemRegistry.INVISIBILITY_ELIXIR.get(), FluidRegistry.INVISIBILITY_ELIXIR_FLUID.get(),                250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.GREATER_INVISIBILITY_ELIXIR.get(), FluidRegistry.GREATER_INVISIBILITY_ELIXIR_FLUID.get(),250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.EVASION_ELIXIR.get(), FluidRegistry.EVASION_ELIXIR_FLUID.get(),                          250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.GREATER_EVASION_ELIXIR.get(), FluidRegistry.GREATER_EVASION_ELIXIR_FLUID.get(),          250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.OAKSKIN_ELIXIR.get(), FluidRegistry.GREATER_OAKSKIN_ELIXIR_FLUID.get(),                  250, Items.GLASS_BOTTLE.getDefaultInstance());
		itemPouring(event, stack, ItemRegistry.GREATER_OAKSKIN_ELIXIR.get(), FluidRegistry.GREATER_OAKSKIN_ELIXIR_FLUID.get(),          250, Items.GLASS_BOTTLE.getDefaultInstance());
	}
	public static void itemPouring(AttachCapabilitiesEvent<ItemStack> event, ItemStack itemStack, Item input, FluidObject<? extends Fluid> fluidObject, int amount, ItemStack output) {
		if (itemStack.getItem().equals(input)) {
			event.addCapability(
					fluidObject.getId(),
					new ConstantFluidContainerWrapper(new FluidStack(fluidObject.get(), amount), itemStack, output)
			);
		}
	}
	public static void itemPouring(AttachCapabilitiesEvent<ItemStack> event, ItemStack itemStack, Item input, Fluid fluidObject, int amount, ItemStack output) {
		if (itemStack.getItem().equals(input)) {
			event.addCapability(
					ConstructsCasting.id("pouring_capability_" + ForgeRegistries.ITEMS.getKey(input).getPath()),
					new ConstantFluidContainerWrapper(new FluidStack(fluidObject, amount), itemStack, output)
			);
		}
	}

	//if the target has the enderference effect, cancel teleportations
	@SubscribeEvent
	static void enderferenceAntiSpell(SpellPreCastEvent event) {
		Player entity = event.getEntity();
		if (entity.hasEffect(TinkerEffects.enderference.get())) {
            String spellId = event.getSpellId();
			if (spellId.equals("irons_spellbooks:teleport") || spellId.equals("irons_spellbooks:blood_step") || spellId.equals("irons_spellbooks:frost_step")) {
				entity.level().playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL, 2f, 0.2f + Utils.random.nextFloat() * .2f);
				entity.displayClientMessage(Component.translatable("ui.constructs_casting.enderference_anti_teleport").withStyle(ChatFormatting.RED), true);
				event.setCanceled(true);
			}
		}
	}
    @SubscribeEvent
    static void soulboundSpellbooks(DropRulesEvent event) {
        event.addOverride((stack) -> (stack.is(CCItems.Tags.MOD_SPELLBOOKS) && ModifierUtil.getModifierLevel(stack, ModifierIds.soulbound) > 0), ICurio.DropRule.ALWAYS_KEEP);
    }
//	@SubscribeEvent
//	static void soulboundSpellbookDeath(LivingDeathEvent event) {
//		LivingEntity entity = event.getEntity();
//		if (!entity.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && entity instanceof Player player && !(player instanceof FakePlayer)) {
//			// start with the hotbar, must be soulbound or soul belt
//			CuriosApi.getCuriosInventory(player).ifPresent((handler) -> {
//				ItemStack spellbook = handler.getCurios().get("spellbook").getStacks().getStackInSlot(0);
//				if (!spellbook.isEmpty() && (ModifierUtil.checkVolatileFlag(spellbook, SOULBOUND))) {
//					spellbook.getOrCreateTag().putInt(SOULBOUND_SLOT, 999 /*a great idea*/);
//			}
//		});
//		}
//	}
//	@SubscribeEvent
//	static void soulboundSpellbookDrop(LivingDropsEvent event) {
//		// only care about real players with keep inventory off
//		LivingEntity entity = event.getEntity();
//		if (!entity.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && entity instanceof Player player && !(entity instanceof FakePlayer)) {
//			Collection<ItemEntity> drops = event.getDrops();
//			Iterator<ItemEntity> iter = drops.iterator();
//            while (iter.hasNext()) {
//				ItemEntity itemEntity = iter.next();
//				ItemStack stack = itemEntity.getItem();
//				// find items with our soulbound tag set and move them back into the inventory, will move them over later
//				CompoundTag tag = stack.getTag();
//				if (tag != null && tag.contains(SOULBOUND_SLOT, Tag.TAG_ANY_NUMERIC)) {
//					int slot = tag.getInt(SOULBOUND_SLOT);
//					// return the tool to its requested slot if possible, remove from the drops
//					if (slot == 999) {
//						CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.setEquippedCurio("spellbook", 0, stack));
//						iter.remove();
//						// don't clear the tag yet, we need it one last time for player clone
//					}
//				}
//				// handle items that did not get their requested slot last, to ensure they don't take someone else's slot while being added to a default
////			for (ItemEntity itemEntity : takenSlot) {
////				ItemStack stack = itemEntity.getItem();
////				if (!inventory.add(stack)) {
////					// last resort, somehow we just cannot put the stack anywhere, so drop it on the ground
////					// this should never happen, but better to be safe
////					// ditch the soulbound slot tag, to prevent item stacking issues
////					CompoundTag tag = stack.getTag();
////					if (tag != null) {
////						tag.remove(SOULBOUND_SLOT);
////						if (tag.isEmpty()) {
////							stack.setTag(null);
////						}
////					}
////					drops.add(itemEntity);
////				}
////			}
//			}
//		}
//	}
//	@SubscribeEvent
//	static void soulboundSpellbookClone(PlayerEvent.Clone event) {
//		if (!event.isWasDeath()) {
//			return;
//		}
//		Player original = event.getOriginal();
//		Player clone = event.getEntity();
//		// inventory already copied
//		if (clone.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || original.isSpectator()) {
//			return;
//		}
//		// find items with the soulbound tag set and move them over
//		LazyOptional<ICuriosItemHandler> originalInv = CuriosApi.getCuriosInventory(original);
//		LazyOptional<ICuriosItemHandler> cloneInv = CuriosApi.getCuriosInventory(clone);
//			originalInv.ifPresent((handler) -> handler.findCurio("spellbook", 0).ifPresent(slotResult -> {
//				ItemStack stack = slotResult.stack();
//				if (!stack.isEmpty()) {
//					CompoundTag tag = stack.getTag();
//					if (tag != null && tag.contains(SOULBOUND_SLOT, Tag.TAG_ANY_NUMERIC)) {
//						cloneInv.ifPresent(handler2 -> handler2.setEquippedCurio("spellbook", 0, stack));
//						// remove the slot tag, clear the tag if needed
//						tag.remove(SOULBOUND_SLOT);
//						if (tag.isEmpty()) {
//							stack.setTag(null);
//						}
//					}
//				}
//
//			}));
//	}
	@SubscribeEvent
	static void damageModifiers(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		LivingEntity entity = event.getEntity();
		float originalDamage = event.getAmount();
		if (source.is(DamageTypes.FREEZE)) {
			int level = TinkerEffect.getLevel(entity, CCFluidEffects.MobEffects.frostbite);
			if (level > 0) {
				originalDamage *= (float) Math.pow(2, level);
			}
		}

		// ensure any changes made so far apply, though we may change it again
		event.setAmount(originalDamage);

	}
    @SubscribeEvent
    static void initSpellbooks(PlayerEvent.ItemCraftedEvent event) {
//        ConstructsCasting.LOGGER.info("crafted {}", event.getCrafting().getDisplayName().getString());
        ItemStack crafted = event.getCrafting();
//        if (crafted.is(CCItems.Tags.MOD_SPELLBOOKS)) {
//            ((ModifiableSpellbookItem) crafted.getItem()).initializeSpellContainer(crafted);
//        } else if (crafted.is(Items.AIR)) {
//            int shiftClickSlot = event.getEntity().getInventory().getFreeSlot() - 1;
//            ItemStack shiftClickedItem = event.getEntity().getInventory().getItem(shiftClickSlot);
//            ConstructsCasting.LOGGER.info("crafted: {}", shiftClickedItem.getDisplayName().getString());
//            ((ModifiableSpellbookItem) shiftClickedItem.getItem()).initializeSpellContainer(shiftClickedItem);
//        }
        if (crafted.is(Items.AIR)) { //means we shift clicked
//			for (int i = lastOccupiedSlot(event.getEntity().getInventory()); i < event.getEntity().getInventory().items.size(); i++) {
//				ItemStack toInitialize = event.getEntity().getInventory().items.get(i);
//				ConstructsCasting.LOGGER.info("shift initializing {}, {}", i, toInitialize.getDisplayName().getString());
//				if (toInitialize.is(CCItems.Tags.MOD_SPELLBOOKS)) {
//					((ModifiableSpellbookItem) toInitialize.getItem()).initializeSpellContainer(toInitialize);
//				}
//			}
			//for some godforsaken reason, this is called after the item leaves the slot but before it enters the new one, so it always reads the slot where it SHOULD be as empty
        } else {
			ItemStack toInitialize = event.getCrafting();
			if (toInitialize.is(CCItems.Tags.MOD_SPELLBOOKS)) {
				((ModifiableSpellbookItem) toInitialize.getItem()).initializeSpellContainer(toInitialize);
			}
		}
    }
	private static int lastOccupiedSlot(Inventory inv) {
		for(int i = inv.items.size()-1; i >= 0; --i) {
			if (inv.items.get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}


	@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static class ForgeClientEvents {
//		@SubscribeEvent
//		static void swiftcastingHandleInput(MovementInputUpdateEvent event) {
//			if (ClientMagicData.isCasting() &&
//					ModifierUtil.getModifierLevel(event.getEntity().getItemInHand(InteractionHand.MAIN_HAND), CCModifiers.SWIFTCASTING) > 0) {
//				event.getInput().leftImpulse *= 5;
//				event.getInput().forwardImpulse *= 5;
//			}
//		}

	}
	@Mod.EventBusSubscriber(modid = ConstructsCasting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ModClientEvents {
		@SubscribeEvent
		static void registerCurioRenderers(FMLClientSetupEvent e) {
            CuriosRendererRegistry.register(CCItems.travellersSpellbook.get(), ModifiableSpellbookRenderer::new);
			CuriosRendererRegistry.register(CCItems.slimySpellbook.get(), ModifiableSpellbookRenderer::new);
			CuriosRendererRegistry.register(CCItems.platedSpellbook.get(), ModifiableSpellbookRenderer::new);
		}
		@SubscribeEvent
		static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
			event.registerEntityRenderer(CCEntities.SLIMEBALL_PROJECTILE.get(), SlimeballProjectileRenderer::new);
		}
		@SubscribeEvent
		static void clientSetup(final FMLClientSetupEvent event) {
			ArtificersGuideItem.ARTIFICERS_GUIDE.fontRenderer = CommonsClientEvents.unicodeFontRender();
		}
	}
}
