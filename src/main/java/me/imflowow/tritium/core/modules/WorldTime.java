package me.imflowow.tritium.core.modules;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import tritium.api.module.Module;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.TickEvent;

public class WorldTime extends Module {
	private NumberValue<Long> Time = new NumberValue("Time", 18000l, 0l, 24000l, 1l);

	public WorldTime() {
		super("WorldTime", "Change the world time.");
		super.addValues(Time);
	}

	@EventTarget
	public void EventPacketSend(PacketEvent e) {
		if (e.getPacket() instanceof S03PacketTimeUpdate) {
			e.setCancelled(true);
		}
	}

	@EventTarget
	public void onTick(TickEvent event) {
		if (mc.thePlayer != null && mc.theWorld != null) {
			mc.theWorld.setWorldTime(Time.getValue().longValue());
		}
	}

}
