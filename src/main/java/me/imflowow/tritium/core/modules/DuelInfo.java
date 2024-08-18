package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.duelinfo.DuelInfoEntity;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.PacketEvent;
import tritium.api.utils.event.events.TickEvent;

public class DuelInfo extends Module {
	public ColorValue textColor = new ColorValue("TextColor", new HSBColor(255, 255, 255, 255));
	public ColorValue backgroundColor = new ColorValue("BackgroundColor", new HSBColor(0, 0, 0, 180));
	public ColorValue backgroundRectColor = new ColorValue("BackgroundRectColor", new HSBColor(0, 0, 0, 120));
	public BooleanValue animaiton = new BooleanValue("Animation", false);
	public ColorValue animationColor = new ColorValue("AnimationColor", new HSBColor(255, 111, 0, 180));
	public ColorValue rectColor = new ColorValue("RectColor", new HSBColor(19, 26, 37, 255));
	public ColorValue targetColor = new ColorValue("TargetColor", new HSBColor(224, 48, 37, 255));
	public ColorValue selfColor = new ColorValue("SelfColor", new HSBColor(94, 183, 153, 255));
	public PositionValue position = new PositionValue("DuelInfo", new Position(10, 10, Direction.Left, Direction.Top));
	DuelInfoEntity entity;

	public DuelInfo() {
		super("DuelInfo", "While pvp in solo,you can use it to display your pvp information.");
		this.entity = new DuelInfoEntity(this.position);
		super.addValues(textColor, backgroundColor, backgroundRectColor, animaiton, animationColor, rectColor,
				targetColor, selfColor);
		super.addGuiEntities(this.entity);
	}

	@EventTarget
	public void onPacket(PacketEvent event) {
		this.entity.onPacket(event);
	}

	@EventTarget
	public void onTick(TickEvent event) {
		if (mc.thePlayer != null && mc.theWorld != null) {
			this.entity.onTick(event);
		}
	}

}
