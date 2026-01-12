package com.snackpirate.constructscasting.materials;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.network.chat.Component;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public class CCMaterialStats {
	public enum Statless implements IMaterialStats{
		ADORNMENT("adornment");
//		SPELLBOOK_PLATING("spellbook_plating");


		private static final List<Component> LOCALIZED = List.of(IMaterialStats.makeTooltip(ConstructsCasting.id("extra.no_stats")));
		private static final List<Component> DESCRIPTION = List.of(Component.empty());
		private final MaterialStatType<Statless> type;

		Statless(String name) {
			this.type = MaterialStatType.singleton(new MaterialStatsId(ConstructsCasting.id(name)), this);
		}

		@Override
		public MaterialStatType<?> getType() {
			return type;
		}

		@Override
		public List<Component> getLocalizedInfo() {
			return LOCALIZED;
		}

		@Override
		public List<Component> getLocalizedDescriptions() {
			return DESCRIPTION;
		}

		@Override
		public void apply(ModifierStatsBuilder builder, float scale) {}


	}
}
