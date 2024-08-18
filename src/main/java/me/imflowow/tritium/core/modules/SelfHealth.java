package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.selfhealth.SelfHealthEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;

public class SelfHealth extends Module {
	public ColorValue healthColor = new ColorValue("HealthColor", new HSBColor(38, 233, 0, 255));
	public ColorValue healthShadowColor = new ColorValue("HealthShadowColor", new HSBColor(27, 164, 0, 255));
	public ColorValue backgroundColor = new ColorValue("BackgroundColor", new HSBColor(52, 73, 94, 255));
	public NumberValue<Float> animationSpeed = new NumberValue<Float>("AnimationSpeed", 0.3f, 0.01f, 1.0f, 0.01f);

	public PositionValue position = new PositionValue("SelfHealth", new Position(10, 10, Direction.Left, Direction.Top));

	public SelfHealth() {
		super("SelfHealth", "Display your health on your screen.");
		super.addValues( healthColor, healthShadowColor, backgroundColor, animationSpeed);
		super.addGuiEntities(new SelfHealthEntity(position));
	}

}
