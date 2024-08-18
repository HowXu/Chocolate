package tritium.api.utils.event.events;

import net.minecraft.client.gui.ScaledResolution;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public class CrosshairEvent extends EventCancellable {
	private ScaledResolution scaledresolution;
	
	public CrosshairEvent(ScaledResolution scaledresolution)
	{
		this.scaledresolution = scaledresolution;
	}

	public ScaledResolution getScaledresolution() {
		return scaledresolution;
	}

	public void setScaledresolution(ScaledResolution scaledresolution) {
		this.scaledresolution = scaledresolution;
	}
}
