package com.snackpirate.constructscasting.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

//needed because it won't display the magic stats otherwise
public class ModifiableMagicStaff extends ModifiableItem {
	public ModifiableMagicStaff(Properties properties, ToolDefinition toolDefinition) {
		super(properties, toolDefinition);
	}

	@Override
	public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
		TooltipBuilder builder = new TooltipBuilder(tool, super.getStatInformation(tool, player, tooltips, key, tooltipFlag));

		if (tool.hasTag(CCItems.Tags.MODIFIABLE_SPELLCASTING)) {
			builder.add(ToolStats.DRAW_SPEED);
			builder.add(ToolStats.VELOCITY);
			builder.add(ToolStats.PROJECTILE_DAMAGE);
			builder.add(ToolStats.ACCURACY);
		}
		return tooltips;
	}
}
