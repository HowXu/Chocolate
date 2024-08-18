package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.callables.EventCancellable;

public class SendMessageEvent extends EventCancellable {
	private String message;

	public SendMessageEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}