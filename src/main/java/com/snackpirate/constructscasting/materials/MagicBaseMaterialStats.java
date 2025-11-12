package com.snackpirate.constructscasting.materials;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record MagicBaseMaterialStats(float maxMana, float spellPower) implements IMaterialStats, IRepairableMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(ConstructsCasting.id("magic_base"));
    public static final MaterialStatType<MagicBaseMaterialStats> TYPE = new MaterialStatType<>(ID, new MagicBaseMaterialStats(0, 0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("max_mana", 0f, true, MagicBaseMaterialStats::maxMana),
            FloatLoadable.ANY.defaultField("spell_power", 0f, true, MagicBaseMaterialStats::spellPower),
            MagicBaseMaterialStats::new
    ));

    private static final List<Component> DESCRIPTION = ImmutableList.of(CCToolStats.MAX_MANA.getDescription(), CCToolStats.SPELL_POWER.getDescription());

    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override
    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatNumber(IMaterialStats.makeTooltipKey(ConstructsCasting.id("max_mana")), TextColor.fromRgb(0x54fcfc), this.maxMana));
        info.add(IToolStat.formatColoredPercentBoost(IMaterialStats.makeTooltipKey(ConstructsCasting.id("spell_power")), this.spellPower));
        return info;
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public void apply(ModifierStatsBuilder builder, float scale) {
        CCToolStats.MAX_MANA.add(builder, maxMana * scale);
        CCToolStats.SPELL_POWER.add(builder, spellPower * scale);
    }
    //purely so the material displays in the name of the spellbook (e.g. Wood-Bamboo Traveller's Spellbook)
    @Override
    public int durability() {
        return 0;
    }
}
