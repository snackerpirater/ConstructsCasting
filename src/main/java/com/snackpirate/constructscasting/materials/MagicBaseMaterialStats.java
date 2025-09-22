package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record MagicBaseMaterialStats(float maxMana, float spellPower) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(ConstructsCasting.id("magic_base"));
    public static final MaterialStatType<MagicBaseMaterialStats> TYPE = new MaterialStatType<>(ID, new MagicBaseMaterialStats(0, 0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("max_mana", 0f, true, MagicBaseMaterialStats::maxMana),
            FloatLoadable.ANY.defaultField("spell_power", 0f, true, MagicBaseMaterialStats::spellPower),
            MagicBaseMaterialStats::new
    ));
    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override
    public List<Component> getLocalizedInfo() {
        return List.of();
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return List.of();
    }

    @Override
    public void apply(ModifierStatsBuilder builder, float scale) {
        CCToolStats.MAX_MANA.add(builder, maxMana * scale);
        CCToolStats.SPELL_POWER.percent(builder, spellPower * scale);
    }
}
