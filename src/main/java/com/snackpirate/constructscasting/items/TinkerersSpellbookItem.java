package com.snackpirate.constructscasting.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.materials.CCMaterials;
import com.snackpirate.constructscasting.materials.CCToolStats;
import com.snackpirate.constructscasting.modifiers.CCModifiers;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.network.ClientboundSyncMana;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.capability.inventory.ToolInventoryCapability;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;
import slimeknights.tconstruct.library.tools.item.ITinkerStationDisplay;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.MaterialIdNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;
import slimeknights.tconstruct.tools.client.OverslimeModifierModel;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TinkerersSpellbookItem extends SpellBook implements IModifiableDisplay {

    private final ToolDefinition definition;
	private ItemStack toolForRendering;

	public TinkerersSpellbookItem(Properties prop, int slots, ToolDefinition definition) {
		super(slots, SpellRarity.EPIC, prop);
		this.definition = definition;
	}

	@Override
	public ToolDefinition getToolDefinition() {
		return definition;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
		spellbookLines(itemStack, level, lines, flag, SafeClientAccess.getTooltipKey());
		TooltipUtil.addInformation(this, itemStack, level, lines, SafeClientAccess.getTooltipKey(), flag);
	}
	public void spellbookLines(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> lines, @NotNull TooltipFlag flag, TooltipKey key) {
		if (key == TooltipKey.CONTROL || key == TooltipKey.SHIFT) return;
		if (this.isUnique()) {
			lines.add(Component.translatable("tooltip.irons_spellbooks.spellbook_rarity", new Object[]{Component.translatable("tooltip.irons_spellbooks.spellbook_unique").withStyle(TooltipsUtils.UNIQUE_STYLE)}).withStyle(ChatFormatting.GRAY));
		}

		Player player = MinecraftInstanceHelper.getPlayer();
		if (player != null && ISpellContainer.isSpellContainer(itemStack)) {
			ISpellContainer spellList = ISpellContainer.get(itemStack);
			List<SpellData> activeSpellSlots = spellList.getActiveSpells();
			if (!activeSpellSlots.isEmpty()) {
				lines.add(Component.translatable("tooltip.irons_spellbooks.spellbook_tooltip").withStyle(ChatFormatting.GRAY));
				SpellSelectionManager spellSelectionManager = ClientMagicData.getSpellSelectionManager();

				for(int i = 0; i < activeSpellSlots.size(); ++i) {
					MutableComponent spellText = TooltipsUtils.getTitleComponent((SpellData)activeSpellSlots.get(i), (LocalPlayer)player).setStyle(Style.EMPTY);
					if (MinecraftInstanceHelper.getPlayer() != null && Utils.getPlayerSpellbookStack(MinecraftInstanceHelper.getPlayer()) == itemStack && spellSelectionManager.getCurrentSelection().equipmentSlot.equals(Curios.SPELLBOOK_SLOT) && i == spellSelectionManager.getSelectionIndex()) {
						List<MutableComponent> shiftMessage = TooltipsUtils.formatActiveSpellTooltip(itemStack, spellSelectionManager.getSelectedSpellData(), CastSource.SPELLBOOK, (LocalPlayer)player);
						shiftMessage.remove(0);
						TooltipsUtils.addShiftTooltip(lines, Component.literal("> ").append(spellText).withStyle(ChatFormatting.YELLOW), (List)shiftMessage.stream().map((component) -> {
							return Component.literal(" ").append(component);
						}).collect(Collectors.toList()));
					} else {
						lines.add(Component.literal(" ").append(spellText.withStyle(Style.EMPTY.withColor(8947966))));
					}
				}
			}
		}
	}

	@Override
	public ItemStack getRenderTool() {
		if (toolForRendering == null) {
			toolForRendering = new MaterialIdNBT(List.of(MaterialIds.cobalt, MaterialIds.wood, CCMaterials.paper)).updateStack(new ItemStack(CCItems.platedSpellbook.get()));
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
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
        ToolStack tool = ToolStack.from(stack);
        int manaBonus = tool.getStats().getInt(CCToolStats.MAX_MANA);
        attributeBuilder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier("tool.constructs_casting.mana_bonus", manaBonus, AttributeModifier.Operation.ADDITION));
        float spBonus = tool.getStats().get(CCToolStats.SPELL_POWER);
        attributeBuilder.put(AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("tool.constructs_casting.spell_power_bonus", spBonus, AttributeModifier.Operation.MULTIPLY_BASE));
        float cdBonus = tool.getStats().get(CCToolStats.COOLDOWN_REDUCTION);
        attributeBuilder.put(AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier("tool.constructs_casting.cd_reduction", cdBonus, AttributeModifier.Operation.MULTIPLY_BASE));

        for (ModifierEntry entry : tool.getModifierList()) {
			entry.getHook(ModifierHooks.ATTRIBUTES).addAttributes(tool, entry, EquipmentSlot.MAINHAND, attributeBuilder::put);
		}
        return attributeBuilder.build();
	}
    //doesn't automatically clear max mana bonus for some reason, need to force it
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        AttributeInstance maxMana = slotContext.entity().getAttribute(AttributeRegistry.MAX_MANA.get());
        maxMana.getModifiers().stream().filter((modifier) -> modifier.getName().equals("tool.constructs_casting.mana_bonus")).forEach((modifier) -> maxMana.removeModifier(modifier));
        AttributeInstance sp = slotContext.entity().getAttribute(AttributeRegistry.SPELL_POWER.get());
        maxMana.getModifiers().stream().filter((modifier) -> modifier.getName().equals("tool.constructs_casting.spell_power_bonus")).forEach((modifier) -> sp.removeModifier(modifier));
        AttributeInstance cd = slotContext.entity().getAttribute(AttributeRegistry.COOLDOWN_REDUCTION.get());
        maxMana.getModifiers().stream().filter((modifier) -> modifier.getName().equals("tool.constructs_casting.cd_reduction")).forEach((modifier) -> cd.removeModifier(modifier));
        super.onUnequip(slotContext, newStack, stack);
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

	@Override
	public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
		TooltipBuilder builder = new TooltipBuilder(tool, tooltips);
		if (tool.hasTag(CCToolStats.MAGIC_TOOL)) {
			builder.add(CCToolStats.MAX_MANA);
			builder.add(CCToolStats.SPELL_POWER);
			builder.add(CCToolStats.SPELL_SLOTS);
			builder.add(CCToolStats.COOLDOWN_REDUCTION);
		}
		IModifiableDisplay.super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
		return tooltips;
	}

	@Override
	public void initializeSpellContainer(ItemStack itemStack) {
		ToolStack tool = ToolStack.from(itemStack);
		tool.ensureHasData();
		int spells = tool.getStats().getInt(CCToolStats.SPELL_SLOTS);
		ConstructsCasting.LOGGER.info("spells: {}", spells);

		if (!ISpellContainer.isSpellContainer(itemStack) || (spells > 0 && ISpellContainer.isSpellContainer(itemStack) && (ISpellContainer.get(itemStack).getMaxSpellCount() != spells))) {
			ISpellContainer spellContainer = ISpellContainer.create(spells, true, true);
			spellContainer.save(itemStack);
		}
	}

	@Override
	public boolean canSync(SlotContext slotContext, ItemStack stack) {
		return true;
	}
}
