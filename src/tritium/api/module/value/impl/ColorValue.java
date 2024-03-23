package tritium.api.module.value.impl;

import java.awt.Color;
import java.util.Optional;
import java.util.function.Predicate;

import me.imflowow.tritium.core.Tritium;
import tritium.api.module.Module;
import tritium.api.module.value.Value;
import tritium.api.module.value.parse.BooleanParser;
import tritium.api.module.value.utils.HSBColor;

public class ColorValue extends Value<HSBColor> {

	public BooleanValue rainbow;
	public NumberValue rainbowspeed;

	public ColorValue(String label, HSBColor value) {
		super(label, value);
	}

	@Override
	public void setValue(String input) {
		String[] split = input.split(":");
		if (split.length < 4)
			return;
		this.value = new HSBColor(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]),
				Integer.parseInt(split[3]));
	}

	@Override
	public void init(Module module) {
		Tritium.instance.getLanguagemanager().getTextRender().getText(this.getLabel());
		this.rainbow = new BooleanValue(Tritium.instance.getLanguagemanager().getTextRender().getText(this.getLabel())
				+ Tritium.instance.getLanguagemanager().getTextRender().getText("Rainbow"), false);
		this.rainbowspeed = new NumberValue<Integer>(
				Tritium.instance.getLanguagemanager().getTextRender().getText(this.getLabel())
						+ Tritium.instance.getLanguagemanager().getTextRender().getText("RainbowSpeed"),
				3, 1, 10, 1);
		module.addValues(this.rainbow, this.rainbowspeed);
	}

	@Override
	public HSBColor getValue() {
		if (this.rainbow.getValue()) {
			float speed = this.rainbowspeed.getValue().floatValue();
			float hue = System.currentTimeMillis() % (int) ((1 - speed / 15.0) * 2000);
			hue /= (int) ((1 - speed / 15.0) * 2000);
			super.getValue().setHue(hue);
		}
		return super.getValue();
	}

}