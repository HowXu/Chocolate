package tritium.api.utils.event.events;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import tritium.api.utils.event.api.events.Event;

public class BlockBeRenderedEvent implements Event {

	private final IBlockState state;
	private final IBlockAccess world;
	private final Block block;
	public final BlockPos pos;
	private final EnumFacing side;
	private boolean toRender;
	public double maxX;
	public double maxY;
	public double maxZ;
	public double minX;
	public double minY;
	public double minZ;

	public BlockBeRenderedEvent(IBlockAccess world, BlockPos pos, Block block, EnumFacing side, double maxX, double minX,
			double maxY, double minY, double maxZ, double minZ) {
		this.block = block;
		this.state = world.getBlockState(pos);
		this.world = world;
		this.pos = pos;
		this.side = side;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
	}

	public IBlockState getState() {
		return state;
	}

	public IBlockAccess getWorld() {
		return world;
	}

	public BlockPos getPos() {
		return pos;
	}

	public Block getBlock()
	{
		return block;
	}
	
	public EnumFacing getSide() {
		return side;
	}

	public boolean isToRender() {
		return toRender;
	}

	public void setToRender(boolean toRender) {
		this.toRender = toRender;
	}
}
