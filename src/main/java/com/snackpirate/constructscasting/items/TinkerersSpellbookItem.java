package com.snackpirate.constructscasting.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.capability.inventory.ToolInventoryCapability;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class TinkerersSpellbookItem extends SpellBook implements IModifiableDisplay {
	public static final ToolDefinition DEFINITION = ToolDefinition.create(CCItems.tinkerersSpellbook);
	private ItemStack toolForRendering;

	public TinkerersSpellbookItem(Properties prop) {
		super(10, SpellRarity.EPIC, prop);
	}
	@Override
	public ToolDefinition getToolDefinition() {
		return DEFINITION;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
		super.appendHoverText(itemStack, level, lines, flag);
		TooltipUtil.addInformation(this, itemStack, level, lines, SafeClientAccess.getTooltipKey(), flag);
	}

	@Override
	public ItemStack getRenderTool() {
		if (toolForRendering == null) {
			toolForRendering = ToolBuildHandler.buildToolForRendering(this, this.getToolDefinition());
		}
		return toolForRendering;
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new ToolCapabilityProvider(stack);
	}

	@Override
	public void verifyTagAfterLoad(CompoundTag pCompoundTag) {
		ToolStack.verifyTag(this, pCompoundTag, getToolDefinition());
	}

	@Override
	public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
		ToolStack.ensureInitialized(pStack, getToolDefinition());
	}

	@Override
	public int getMaxSpellSlots() {
		return 10;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
		ToolStack tool = ToolStack.from(stack);
		for (ModifierEntry entry : tool.getModifierList()) {
			entry.getHook(ModifierHooks.ATTRIBUTES).addAttributes(tool, entry, EquipmentSlot.MAINHAND, attributeBuilder::put);
		}
		return attributeBuilder.build();
	}

	@Override
	public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
		return ToolStack.from(stack).getModifierLevel(CCModifiers.ENCYCLOPEDIC.getId()) < 1;
	}
	protected static boolean shouldInteract(@Nullable LivingEntity player, ToolStack toolStack, InteractionHand hand) {
		IModDataView volatileData = toolStack.getVolatileData();
		if (volatileData.getBoolean(NO_INTERACTION)) {
			return false;
		}
		// off hand always can interact
		if (hand == InteractionHand.OFF_HAND) {
			return true;
		}
		// main hand may wish to defer to the offhand if it has a tool
		return player == null || !volatileData.getBoolean(DEFER_OFFHAND) || player.getOffhandItem().isEmpty();
	}
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
		ItemStack stack = playerIn.getItemInHand(hand);
		if (stack.getCount() > 1) {
			return InteractionResultHolder.pass(stack);
		}
		ToolStack tool = ToolStack.from(stack);
		if (shouldInteract(playerIn, tool, hand)) {
			for (ModifierEntry entry : tool.getModifierList()) {
				InteractionResult result = entry.getHook(ModifierHooks.GENERAL_INTERACT).onToolUse(tool, entry, playerIn, hand, InteractionSource.RIGHT_CLICK);
				if (result.consumesAction()) {
					return new InteractionResultHolder<>(result, stack);
				}
			}
		}
		return new InteractionResultHolder<>(ToolInventoryCapability.tryOpenContainer(stack, tool, playerIn, Util.getSlotType(hand)), stack);
	}

	@Override
	public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		super.onEquip(slotContext, prevStack, stack);
		ToolStack.ensureInitialized(stack, getToolDefinition());
	}
}
