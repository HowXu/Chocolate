package tritium.api.utils.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import tritium.api.utils.event.api.events.Event;

public class RenderBlockModelEvent implements Event {
    private final Block block;

    private final BlockPos pos;

    public RenderBlockModelEvent(Block block, BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }
}