package tritium.api.utils.event.events;

import net.minecraft.network.Packet;
import tritium.api.utils.event.api.events.callables.EventCancellable;

public class PacketEvent extends EventCancellable {
	private boolean sending;
	private Packet packet;

	public PacketEvent(Packet packet, boolean sending) {
		this.packet = packet;
		this.sending = sending;
	}

	public Packet getPacket() {
		return packet;
	}

	public boolean isSending() {
		return sending;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
