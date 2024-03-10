package me.imflowow.tritium.core.modules;

import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.utils.HSBColor;

public class EnchantEffect extends Module {

	public ColorValue color = new ColorValue("Color", new HSBColor(255, 255, 255, 255));

	public EnchantEffect() {
		super("EnchantEffect", "You can make your enchant effect colorful.");
		super.addValues(color);
	}

}
