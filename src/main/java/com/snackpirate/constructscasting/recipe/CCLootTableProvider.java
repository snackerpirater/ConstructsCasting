package com.snackpirate.constructscasting.recipe;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.items.CCBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CCLootTableProvider extends LootTableProvider {

	private static final Set<ResourceLocation> REQUIRED_TABLES = Set.of();

	public CCLootTableProvider(PackOutput output) {
		super(output, REQUIRED_TABLES, List.of(new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK)));

	}

	public static class Blocks extends BlockLootSubProvider {
		protected Blocks(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures) {
			super(pExplosionResistant, pEnabledFeatures);
		}
		public Blocks() {
			this(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}
		@SuppressWarnings("deprecation")
		@Override
		protected Iterable<Block> getKnownBlocks() {
			return BuiltInRegistries.BLOCK.stream()
					.filter(block -> ConstructsCasting.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()))
					.collect(Collectors.toList());
		}

		@Override
		protected void generate() {
			this.dropSelf(CCBlocks.arcaneBlock.get());
			this.dropSelf(CCBlocks.exiliteBlock.get());
			this.dropSelf(CCBlocks.mithrilBlock.get());
			this.dropSelf(CCBlocks.pyriumBlock.get());
		}
	}

}
