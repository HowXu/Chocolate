package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.reachdisplay.ReachEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.AttackEvent;

public class ReachDisplay extends Module {

	ReachEntity entity;

	public ColorValue textColor = new ColorValue("TextColor", new HSBColor(255, 255, 255, 255));
	public ColorValue backgroundColor = new ColorValue("BackgroundColor", new HSBColor(0, 0, 0, 180));

	public PositionValue position = new PositionValue("ReachDisplay", new Position(10, 10, Direction.Left, Direction.Top));

	public ReachDisplay() {
		super("ReachDisplay", "Display your attack distance on your screen");
		entity = new ReachEntity(this.position);
		super.addValues(textColor, backgroundColor);
		super.addGuiEntities(entity);
	}

	@EventTarget
	public void onAttack(AttackEvent e) {
		this.entity.onAttack(e);
	}

}
