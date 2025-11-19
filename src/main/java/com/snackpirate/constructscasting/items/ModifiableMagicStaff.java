package com.snackpirate.constructscasting.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.snackpirate.constructscasting.materials.CCToolStats;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;
import java.util.UUID;

//easiest solution to get magic stats to display
public class ModifiableMagicStaff extends ModifiableItem {
	public ModifiableMagicStaff(Properties properties, ToolDefinition toolDefinition) {
		super(properties, toolDefinition);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, EquipmentSlot slot) {

		ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
		attributeBuilder.putAll(super.getAttributeModifiers(tool, slot));
		UUID uuid = AttributesModifierHook.HELD_ARMOR_UUID[slot.getIndex()];
//		int manaBonus = tool.getStats().getInt(CCToolStats.MAX_MANA);
//		attributeBuilder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier("tool.constructs_casting.mana_bonus", manaBonus, AttributeModifier.Operation.ADDITION));
		float spBonus =
//				ConditionalStatModifierHook.getModifiedStat(tool, slotContext.entity(), CCToolStats.SPELL_POWER);
                tool.getStats().get(CCToolStats.SPELL_POWER); //TODO: get conditional modifiers working, this method lacks entity context for some godforsaken reason
		attributeBuilder.put(AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(uuid, "tool.constructs_casting.spell_power_bonus", spBonus, AttributeModifier.Operation.MULTIPLY_BASE));
		float cdBonus =
//				ConditionalStatModifierHook.getModifiedStat(tool, slotContext.entity(), CCToolStats.COOLDOWN_REDUCTION);
                tool.getStats().get(CCToolStats.COOLDOWN_REDUCTION);
		attributeBuilder.put(AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(uuid, "tool.constructs_casting.cd_reduction", cdBonus, AttributeModifier.Operation.MULTIPLY_BASE));

		return attributeBuilder.build();
	}

	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return (!player.isCreative() || !this.builtInRegistryHolder().is(TinkerTags.Items.MELEE));
	}


	@Override
	public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag flag) {
		TooltipBuilder builder = new TooltipBuilder(tool, tooltip);
		if (tool.hasTag(TinkerTags.Items.DURABILITY)) {
			builder.addDurability();
		}
		if (tool.hasTag(TinkerTags.Items.RANGED)) {
			builder.add(ToolStats.DRAW_SPEED);
			builder.add(ToolStats.VELOCITY);
			builder.add(ToolStats.PROJECTILE_DAMAGE);
			builder.add(ToolStats.ACCURACY);
		}
		if (tool.hasTag(TinkerTags.Items.MELEE_WEAPON)) {
			builder.addWithAttribute(ToolStats.ATTACK_DAMAGE, Attributes.ATTACK_DAMAGE);
			builder.add(ToolStats.ATTACK_SPEED);
		}
		if (tool.hasTag(CCItems.Tags.MOD_STAFFS)) {
			builder.add(CCToolStats.SPELL_POWER);
			builder.add(CCToolStats.COOLDOWN_REDUCTION);
		}
		if (tool.hasTag(TinkerTags.Items.HARVEST)) {
			if (tool.hasTag(TinkerTags.Items.HARVEST_PRIMARY)) {
				builder.addTier();
			}
			builder.add(ToolStats.MINING_SPEED);
		}
		// slimestaffs and shields are holdable armor, so show armor stats
		if (tool.hasTag(TinkerTags.Items.ARMOR)) {
			builder.addOptional(ToolStats.ARMOR);
			builder.addOptional(ToolStats.ARMOR_TOUGHNESS);
			builder.addOptional(ToolStats.KNOCKBACK_RESISTANCE, 10f);
		}
		if (tool.getModifierLevel(TinkerModifiers.blocking.getId()) > 0 || tool.getModifierLevel(TinkerModifiers.parrying.getId()) > 0) {
			builder.add(ToolStats.BLOCK_AMOUNT);
			builder.add(ToolStats.BLOCK_ANGLE);
		}

		builder.addAllFreeSlots();
		for (ModifierEntry entry : tool.getModifierList()) {
			entry.getHook(ModifierHooks.TOOLTIP).addTooltip(tool, entry, player, tooltip, key, flag);
		}
		TooltipUtil.addAttributes(this, tool, player, tooltip, TooltipUtil.SHOW_MELEE_ATTRIBUTES, EquipmentSlot.MAINHAND);

		return builder.getTooltips();
	}
}
