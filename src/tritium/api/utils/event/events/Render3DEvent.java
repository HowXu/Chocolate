package tritium.api.utils.event.events;

import tritium.api.utils.event.api.events.Event;
import tritium.api.utils.event.api.types.EventType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Render3DEvent implements Event {

	private float partialTicks;
	private EventType type;
	//不知道为什么直接从源代码上扣的不能用，看了FPSMaster的尝试添加EventType

	public Render3DEvent(EventType type , final float partialTicks) {
		//Logger.getLogger("Render3DEvent").log(Level.INFO,"Call Render3DEvent");
		this.partialTicks = partialTicks;
		this.type = type;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
	public EventType getEventType(){
		return type;
	}
}
