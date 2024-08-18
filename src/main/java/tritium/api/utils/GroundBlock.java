package tritium.api.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class GroundBlock {
	IBlockState block;
	BlockPos pos;
	double distance;

	public GroundBlock(IBlockState block, BlockPos pos, double distance) {
		this.block = block;
		this.pos = pos;
		this.distance = distance;
	}

	public IBlockState getBlock() {
		return this.block;
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public double getDistance() {
		return this.distance;
	}
}
