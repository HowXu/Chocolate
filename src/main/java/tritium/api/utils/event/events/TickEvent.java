package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;

public class TickEvent implements Event {
	public enum Phase {
		START, END;
	}

	public final Phase phase;

	public TickEvent(Phase phase) {
		this.phase = phase;
	}
}
