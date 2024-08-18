package tritium.api.utils.event.events;

import net.minecraft.client.gui.ScaledResolution;
import tritium.api.utils.event.api.events.Event;

public class Render2DEvent implements Event {
	private ScaledResolution scaledResolution;
	private float partialTicks;

	public Render2DEvent(ScaledResolution scaledResolution, float partialTicks) {
		this.scaledResolution = scaledResolution;
		this.partialTicks = partialTicks;
	}

	public ScaledResolution getScaledResolution() {
		return this.scaledResolution;
	}

	public float getPartialTicks() {
		return this.partialTicks;
	}
}
