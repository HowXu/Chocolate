package me.imflowow.tritium.client.ui.settings;

import java.awt.Color;
import java.io.IOException;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.gui.GuiScreen;
import tritium.api.module.Module;
import tritium.api.module.gui.GuiEntity;
import tritium.api.utils.render.Rect;

public class GuiPositionEditor extends GuiScreen {
	GuiScreen last;

	public GuiPositionEditor(GuiScreen last) {
		this.last = last;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCrosshair();

		boolean lock = false;
		for (Module module : Tritium.instance.getModuleManager().getModules(true)) {
			if (module.isEnabled()) {
				for (GuiEntity entity : module.getGuiEntities()) {
					lock = entity.onEdit(mouseX, mouseY, lock);
				}
			}
		}
	}

	public void drawCrosshair() {
		new Rect(0, height / 2 - 1, width, 1, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
		new Rect(0, height / 4 - 1, width, 1, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
		new Rect(0, height / 4 * 3 - 1, width, 1, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
		new Rect(width / 2 - 1, 0, 1, height, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
		new Rect(width / 4 - 1, 0, 1, height, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
		new Rect(width / 4 * 3 - 1, 0, 1, height, new Color(127, 127, 127).getRGB(), Rect.RenderType.Expand).draw();
	}
}
