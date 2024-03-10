package tritium.api.utils.event.events;

import net.minecraft.util.AxisAlignedBB;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public class BlockOverlayEvent extends EventCancellable {
	AxisAlignedBB axisalignedbb;

	public BlockOverlayEvent(AxisAlignedBB axisalignedbb) {
		this.axisalignedbb = axisalignedbb;
	}

	public AxisAlignedBB getBB() {
		return this.axisalignedbb;
	}

	public void setBB(AxisAlignedBB axisalignedbb) {
		this.axisalignedbb = axisalignedbb;
	}

}
