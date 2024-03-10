package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.informations.FPSEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;

public class FPSDisplay extends Module {
	public ColorValue textColor = new ColorValue("TextColor", new HSBColor(255, 255, 255, 255));
	public ColorValue backgroundColor = new ColorValue("BackgroundColor", new HSBColor(0, 0, 0, 180));

	public PositionValue position = new PositionValue("FPSDisplay", new Position(10, 10, Direction.Left, Direction.Top));

	public FPSDisplay() {
		super("FPSDisplay", "Display your FPS on your screen.");
		super.addValues(textColor, backgroundColor);
		super.addGuiEntities(new FPSEntity(this.position));
	}

}
