package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;

public class KeyPressEvent implements Event {
	private int key;

	public KeyPressEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}
}