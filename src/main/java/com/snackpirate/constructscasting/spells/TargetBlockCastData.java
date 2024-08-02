package com.snackpirate.constructscasting.spells;

import io.redspace.ironsspellbooks.api.spells.ICastData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

//For block-based utility spells.
public class TargetBlockCastData implements ICastData {
	private final BlockPos targetPos;
	private final Direction face;

	public TargetBlockCastData(BlockPos targetPos, Direction face) {
		this.targetPos = targetPos;
		this.face = face;
	}

	public Block getBlock(ServerLevel level) {
		return level.getBlockState(targetPos).getBlock();
	}
	public Direction getFace() {
		return face;
	}
	@Nullable
	public BlockEntity getBE(ServerLevel level) {
		return level.getBlockEntity(targetPos);
	}
	public BlockPos getTargetPos() {
		return targetPos;
	}

	/**
	 *
	 */
	@Override
	public void reset() {

	}
}
