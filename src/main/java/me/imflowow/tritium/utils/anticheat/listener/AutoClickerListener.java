package me.imflowow.tritium.utils.anticheat.listener;


import net.minecraft.client.Minecraft;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.MouseEvent;
public class AutoClickerListener {
	Minecraft mc = Minecraft.getMinecraft();
	long lastclickdown = 0;
	long lastpress = 0;
	
	long lastclickdownR = 0;
	long lastpressR = 0;

	public AutoClickerListener() {
		EventManager.register(this);
	}

	@EventTarget
	public void onMouse(MouseEvent event) {
		if (event.getButton() == 0) {
			if (event.isDown()) {
				lastclickdown = System.currentTimeMillis();
				if (lastpress == 0) {
					event.setCancelled(true);
				}
			} else {
				lastpress = System.currentTimeMillis() - lastclickdown;
			}
		}
	}

}
