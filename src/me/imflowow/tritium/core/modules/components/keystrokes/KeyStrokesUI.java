package me.imflowow.tritium.core.modules.components.keystrokes;

import java.awt.Color;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.core.modules.KeyStrokes;
import net.minecraft.client.Minecraft;
import tritium.api.utils.render.Circle;

public class KeyStrokesUI {

	public Minecraft mc = Minecraft.getMinecraft();

	public void init() {

	}

	public void draw(double x, double y, double scaled) {

	}

	protected int getColor(int type) {
		KeyStrokes module = (KeyStrokes) Tritium.instance.getModuleManager().getModule(KeyStrokes.class);
		switch (type) {
		case 0:
			return module.rectColor.getValue().getColor().getRGB();// RECT
		case 1:
			return module.clickDownColor.getValue().getColor().getRGB();// ANIMATION
		case 2:
			return module.textColor.getValue().getColor().getRGB();// TEXT
		}
		return 0;
	}

	protected float getAnimationSpeed() {
		KeyStrokes module = (KeyStrokes) Tritium.instance.getModuleManager().getModule(KeyStrokes.class);
		return module.animationSpeed.getValue();
	}

	protected boolean getCPSDisplay() {
		KeyStrokes module = (KeyStrokes) Tritium.instance.getModuleManager().getModule(KeyStrokes.class);
		return module.cps.getValue();
	}

	protected static class KeyCircle {
		Circle circle;
		int target;

		public KeyCircle(Circle circle, int target) {
			this.circle = circle;
			this.target = target;
		}
	}
}
