
package me.imflowow.tritium.core.modules;

import tritium.api.module.Module;
import tritium.api.module.value.impl.NumberValue;

public class LowFire extends Module {
	public NumberValue<Double> y = new NumberValue<Double>("PositionY", -0.1, -2.0, 2.0, 0.05);

	public LowFire() {
		super("LowFire", "Make the fire down!");
		super.addValues(y);
	}

}