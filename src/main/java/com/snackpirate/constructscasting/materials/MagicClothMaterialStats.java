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

public record MagicClothMaterialStats(float spellSlots, float cooldownReduction) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(ConstructsCasting.id("magic_cloth"));
    public static final MaterialStatType<MagicClothMaterialStats> TYPE = new MaterialStatType<>(ID, new MagicClothMaterialStats(0, 0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("spell_slots", 0f, true, MagicClothMaterialStats::spellSlots),
            FloatLoadable.ANY.defaultField("cooldown_reduction", 0f, true, MagicClothMaterialStats::cooldownReduction),
            MagicClothMaterialStats::new
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
        CCToolStats.SPELL_SLOTS.add(builder, spellSlots * scale);
        CCToolStats.COOLDOWN_REDUCTION.percent(builder, cooldownReduction * scale);
    }
}
