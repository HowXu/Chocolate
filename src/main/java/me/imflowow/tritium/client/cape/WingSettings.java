package me.imflowow.tritium.client.cape;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;

public class WingSettings {
	public boolean chroma = false;
	public int scale = 100;
	public ResourceLocation location;

	public float[] getColors() {
		if (this.chroma) {
			Color color = Color.getHSBColor(System.currentTimeMillis() % 1000L / 1000.0f, 0.8f, 1.0f);
			return new float[] { color.getRed() / 255.0f, color.getGreen() / 255.0f,
					color.getBlue() / 255.0f };
		} else {
			return new float[] { 1.0f, 1.0f, 1.0f };
		}
	}
	
}
