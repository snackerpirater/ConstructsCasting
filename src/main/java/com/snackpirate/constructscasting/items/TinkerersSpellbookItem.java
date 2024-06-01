package com.snackpirate.constructscasting.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
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
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class TinkerersSpellbookItem extends SpellBook implements IModifiableDisplay {
	public static final ToolDefinition DEFINITION = ToolDefinition.create(CCItems.tinkerersSpellbook);
	private ItemStack toolForRendering;

	@Override
	public ToolDefinition getToolDefinition() {
		return DEFINITION;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
		TooltipUtil.addInformation(this, itemStack, level, lines, SafeClientAccess.getTooltipKey(), flag);
		super.appendHoverText(itemStack, level, lines, flag);
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

}
