package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;

public class BlockAnimationsEvent implements Event {
	boolean pre;
	float swingProgress;

	public BlockAnimationsEvent(float value, boolean pre) {
		this.swingProgress = value;
		this.pre = pre;
	}
	public boolean isPre() {
		return pre;
	}
	
	public float getSwingProgress() {
		return swingProgress;
	}

	public void setSwingProgress(float value) {
		this.swingProgress = value;
	}
}
