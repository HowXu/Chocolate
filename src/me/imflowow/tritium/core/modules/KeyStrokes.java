package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.keystrokes.KeyStrokesEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.MouseEvent;

public class KeyStrokes extends Module {
	public BooleanValue mouse = new BooleanValue("Mouse", false);
	public BooleanValue cps = new BooleanValue("CPS", false);

	public BooleanValue jump = new BooleanValue("Jump", false);

	public ColorValue rectColor = new ColorValue("RectColor", new HSBColor(33, 47, 60, 80));
	public ColorValue clickDownColor = new ColorValue("ClickDownColor", new HSBColor(248, 249, 249, 180));
	public ColorValue textColor = new ColorValue("TextColor", new HSBColor(255, 255, 255, 255));

	public NumberValue<Float> animationSpeed = new NumberValue<Float>("AnimationSpeed", 0.3f, 0.01f, 1.0f, 0.01f);

	private PositionValue position = new PositionValue("KeyStrokes", new Position(50, 50, Direction.Left, Direction.Top));

	public KeyStrokesEntity entity;

	public KeyStrokes() {
		super("KeyStrokes", "When you press down a key,it can show you what you press down.");
		super.addValues(mouse, cps, jump, rectColor, clickDownColor, textColor, animationSpeed);
		this.entity = new KeyStrokesEntity(position, this);
		super.addGuiEntities(entity);
	}

	@EventTarget
	public void onClick(MouseEvent event) {
		this.entity.onClick(event);
	}

}
