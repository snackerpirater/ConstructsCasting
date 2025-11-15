package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCItems;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.*;
import slimeknights.tconstruct.library.utils.Util;

public class CCToolStats {
    public static final MaterialStatsId MAGIC = new MaterialStatsId(ConstructsCasting.id("magic"));

    public static final FloatToolStat MAX_MANA = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "max_mana"), 0xFF55FFFF, 0, -50, 5000, CCItems.Tags.MOD_SPELLBOOKS));

    public static final FloatToolStat SPELL_SLOTS = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "spell_slots"), 0xFFd6be96, 0, 0, 15f, CCItems.Tags.MOD_SPELLBOOKS));

    public static final BonusFloatToolStat SPELL_POWER = ToolStats.register(new BonusFloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "spell_power"), 0xFF5555FF, 0, -2048f, 2048f, CCItems.Tags.MAGIC_TOOL));

    public static final BonusFloatToolStat COOLDOWN_REDUCTION = ToolStats.register(new BonusFloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "cooldown_reduction"), 0xffe8bfcf, 0, -2048f, 2048f, CCItems.Tags.MAGIC_TOOL));

    //displays +/-XX%
    public static class BonusFloatToolStat extends FloatToolStat {

        public BonusFloatToolStat(ToolStatId name, int color, float defaultValue, float minValue, float maxValue, @Nullable TagKey<Item> tag) {
            super(name, color, defaultValue, minValue, maxValue, tag);
        }

        @Override
        public Component formatValue(float value) {
            return Component.translatable(getTranslationKey())
                    .append(Component.literal(Util.PERCENT_BOOST_FORMAT.format(value)).withStyle(style -> style.withColor(getColor())));
        }
    }
}
