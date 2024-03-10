package tritium.api.utils.event.events;

import net.minecraft.client.gui.ScaledResolution;
import tritium.api.utils.event.api.events.Event;

public class GLRenderEvent implements Event {
	private ScaledResolution scaledResolution;

	public GLRenderEvent(ScaledResolution scaledResolution) {
		this.scaledResolution = scaledResolution;
	}

	public ScaledResolution getScaledResolution() {
		return this.scaledResolution;
	}

}
