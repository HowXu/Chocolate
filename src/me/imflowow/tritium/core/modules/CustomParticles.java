package me.imflowow.tritium.core.modules;

import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.utils.HSBColor;

public class CustomParticles extends Module {

	public ColorValue color = new ColorValue("Color", new HSBColor(255, 255, 255, 255));

	public CustomParticles() {
		super("CustomParticles", "You can make your particles colorful.");
		super.addValues(color);
	}

}
