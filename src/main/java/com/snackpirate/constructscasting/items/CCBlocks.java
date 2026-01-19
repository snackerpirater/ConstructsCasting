package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CCBlocks {
	protected static final Item.Properties ITEM_PROPS = new Item.Properties();
	protected static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().stacksTo(1);
	protected static final Function<Block,? extends BlockItem> BLOCK_ITEM = (b) -> new BlockItem(b, ITEM_PROPS);
	protected static final Function<Block,? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, ITEM_PROPS);
	public static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(ConstructsCasting.MOD_ID);

	public static final ItemObject<Block> exiliteBlock = BLOCKS.register("exilite_block", metalBuilder(MapColor.TERRACOTTA_GRAY), BLOCK_ITEM);
	public static final ItemObject<Block> arcaneBlock = BLOCKS.register("arcane_block", metalBuilder(MapColor.COLOR_MAGENTA), BLOCK_ITEM);
	public static final ItemObject<Block> mithrilBlock = BLOCKS.register("mithril_block", metalBuilder(MapColor.COLOR_LIGHT_BLUE), BLOCK_ITEM);
	public static final ItemObject<Block> pyriumBlock = BLOCKS.register("pyrium_block", metalBuilder(MapColor.TERRACOTTA_YELLOW), BLOCK_ITEM);


	protected static BlockBehaviour.Properties metalBuilder(MapColor color) {
		return BlockBehaviour.Properties.of().mapColor(color).sound(SoundType.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0f);
	}
	public static class Tags extends BlockTagsProvider {

		public Tags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, modId, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.Provider pProvider) {
			tag(BlockTags.MINEABLE_WITH_PICKAXE).add(arcaneBlock.get(), exiliteBlock.get(), mithrilBlock.get(), pyriumBlock.get());
			tag(BlockTags.NEEDS_IRON_TOOL).add(arcaneBlock.get(), exiliteBlock.get());
			tag(BlockTags.NEEDS_DIAMOND_TOOL).add(mithrilBlock.get(), pyriumBlock.get());
			tag(net.minecraftforge.common.Tags.Blocks.STORAGE_BLOCKS).add(arcaneBlock.get(), exiliteBlock.get(), pyriumBlock.get(), mithrilBlock.get());
		}
	}
}
