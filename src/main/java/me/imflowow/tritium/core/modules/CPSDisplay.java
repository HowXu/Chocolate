package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.cpsdisplay.CPSEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.MouseEvent;

public class CPSDisplay extends Module {
	public ColorValue textColor = new ColorValue("TextColor", new HSBColor(255, 255, 255, 255));
	public ColorValue backgroundColor = new ColorValue("BackgroundColor", new HSBColor(0, 0, 0, 180));

	public PositionValue position = new PositionValue("CPSDisplay", new Position(10, 10, Direction.Left, Direction.Top));

	private CPSEntity entity;

	public CPSDisplay() {
		super("CPSDisplay", "Display your CPS on your screen.");
		this.entity = new CPSEntity(this.position);
		super.addValues(textColor, backgroundColor);
		super.addGuiEntities(this.entity);
	}

	@EventTarget
	public void onClick(MouseEvent event) {
		this.entity.onClick(event);
	}

}
