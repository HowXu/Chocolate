package me.imflowow.tritium.utils.events;

import tritium.api.utils.event.api.events.Event;

public class HitBoxEvent implements Event {
	private float size;

	public HitBoxEvent(float size) {
		super();
		this.size = size;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

}
