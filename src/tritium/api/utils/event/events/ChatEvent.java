package tritium.api.utils.event.events;

import net.minecraft.util.IChatComponent;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public class ChatEvent extends EventCancellable {
	private String message;
	private IChatComponent ChatComponent;

	public ChatEvent(String message, IChatComponent ChatComponent) {
		this.message = message;
		this.ChatComponent = ChatComponent;
	}

	public IChatComponent getChatComponent() {
		return this.ChatComponent;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public void setChatComponent(IChatComponent ChatComponent) {
		this.ChatComponent = ChatComponent;
	}
}