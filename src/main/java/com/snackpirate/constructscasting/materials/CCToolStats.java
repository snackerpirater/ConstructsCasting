package com.snackpirate.constructscasting.materials;

import com.google.gson.JsonElement;
import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.*;

public class CCToolStats {
    public static final TagKey<Item> MAGIC_TOOL = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), ConstructsCasting.id("magic_tool"));
    public static final MaterialStatsId MAGIC = new MaterialStatsId(ConstructsCasting.id("magic"));

    public static final FloatToolStat MAX_MANA = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "max_mana"), 0xFF55FFFF, 0, 0, 5000, MAGIC_TOOL));

    public static final FloatToolStat SPELL_SLOTS = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "spell_slots"), 0xFFFFFFFF, 0, 0, 30, MAGIC_TOOL));

    public static final FloatToolStat SPELL_POWER = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "spell_power"), 0xFF5555FF, 0, 0, 2048f, MAGIC_TOOL));

    public static final FloatToolStat COOLDOWN_REDUCTION = ToolStats.register(new FloatToolStat(new ToolStatId(ConstructsCasting.MOD_ID, "cooldown_reduction"), 0xffd9cdd4, 0, 0, 2048f, MAGIC_TOOL));
}
