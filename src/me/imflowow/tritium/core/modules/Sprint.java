package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.Tritium;
import tritium.api.Wrapper;
import tritium.api.module.Module;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.TickEvent;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", "Keep sprint!", false, true);
	}

	@Override
	public void onEnable() {
		mc.gameSettings.keyBindSprint.keepPressed = true;
	}

	@Override
	public void onDisable() {
		if (mc.thePlayer != null && mc.theWorld != null) {
			mc.gameSettings.keyBindSprint.keepPressed = false;
		}
	}

	@EventTarget
	public void onTick(TickEvent e) {
		mc.gameSettings.keyBindSprint.keepPressed = true;
	}

}
