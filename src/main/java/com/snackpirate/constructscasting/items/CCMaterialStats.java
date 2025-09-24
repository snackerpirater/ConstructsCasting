package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.network.chat.Component;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.List;

public class CCMaterialStats {
	public enum Statless implements IMaterialStats {
		MAGIC_CRYSTAL("crystal");


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
