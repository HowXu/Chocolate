package me.imflowow.tritium.core;

import me.imflowow.tritium.client.ui.settings.GuiPositionEditor;
import net.minecraft.client.Minecraft;
import tritium.api.module.Module;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.event.api.EventManager;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.Render2DEvent;
import tritium.api.utils.render.clickable.ClickEntity;
public class GuiListener {

	Minecraft mc = Minecraft.getMinecraft();

	public GuiListener() {
		EventManager.register(this);
	}

	@EventTarget
	public void render(Render2DEvent event) {
		if (!mc.gameSettings.showDebugInfo) {
			if (!(mc.currentScreen instanceof GuiPositionEditor)) {
				for (Module module : Tritium.instance.getModuleManager().getModules(true)) {
					if (module.isEnabled()) {
						for (GuiEntity entity : module.getGuiEntities()) {
							entity.preDraw();
						}
					}
				}
			}
		}
	}
}
