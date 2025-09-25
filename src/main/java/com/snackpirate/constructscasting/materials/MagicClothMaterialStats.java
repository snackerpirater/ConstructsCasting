package com.snackpirate.constructscasting.materials;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.utils.Util;

import java.util.List;

public record MagicClothMaterialStats(float spellSlots, float cooldownReduction) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(ConstructsCasting.id("magic_cloth"));
    public static final MaterialStatType<MagicClothMaterialStats> TYPE = new MaterialStatType<>(ID, new MagicClothMaterialStats(0, 0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("spell_slots", 0f, true, MagicClothMaterialStats::spellSlots),
            FloatLoadable.ANY.defaultField("cooldown_reduction", 0f, true, MagicClothMaterialStats::cooldownReduction),
            MagicClothMaterialStats::new
    ));

    private static final List<Component> DESCRIPTION = ImmutableList.of(CCToolStats.SPELL_SLOTS.getDescription(), CCToolStats.COOLDOWN_REDUCTION.getDescription());

    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override
    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatNumber(IMaterialStats.makeTooltipKey(ConstructsCasting.id("spell_slots")), TextColor.fromRgb(0x54fcfc), this.spellSlots));
        info.add(IToolStat.formatColoredPercentBoost(IMaterialStats.makeTooltipKey(ConstructsCasting.id("cooldown_reduction")), this.cooldownReduction));
        return info;
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public void apply(ModifierStatsBuilder builder, float scale) {
        CCToolStats.SPELL_SLOTS.update(builder, spellSlots * scale);
        CCToolStats.COOLDOWN_REDUCTION.update(builder, cooldownReduction * scale);
    }
}
