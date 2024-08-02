package com.snackpirate.constructscasting.spells;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;

public class MagicSlime {
	public enum Type {
		EARTH("earth", ChatFormatting.GREEN),
		SKY("sky", ChatFormatting.AQUA),
		ICHOR("ichor", ChatFormatting.GOLD),
		ENDER("ender", ChatFormatting.LIGHT_PURPLE),
		WIZARD("wizard", ChatFormatting.WHITE);

		public final ChatFormatting color;
		public final String id;

		Type(String id, ChatFormatting color) {
			this.id = id;
			this.color = color;
		}
	}
}
