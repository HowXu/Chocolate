package me.imflowow.tritium.core.modules.components.keystrokes;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.KeyStrokes;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.event.events.MouseEvent;

public class KeyStrokesEntity extends GuiEntity {

	private KeyEntity w;
	private KeyEntity a;
	private KeyEntity s;
	private KeyEntity d;

	private JumpEntity jump;

	private MouseEntity left;
	private MouseEntity right;

	private KeyStrokes module;

	public KeyStrokesEntity(PositionValue position, KeyStrokes module) {
		super(position);
		this.module = module;
	}

	@Override
	public void init() {

		this.w = new KeyEntity(Keyboard.getKeyName(mc.gameSettings.keyBindForward.getKeyCode()),
				mc.gameSettings.keyBindForward);
		this.a = new KeyEntity(Keyboard.getKeyName(mc.gameSettings.keyBindLeft.getKeyCode()),
				mc.gameSettings.keyBindLeft);
		this.s = new KeyEntity(Keyboard.getKeyName(mc.gameSettings.keyBindBack.getKeyCode()),
				mc.gameSettings.keyBindBack);
		this.d = new KeyEntity(Keyboard.getKeyName(mc.gameSettings.keyBindRight.getKeyCode()),
				mc.gameSettings.keyBindRight);

		this.jump = new JumpEntity();

		this.left = new MouseEntity("L", 0);
		this.right = new MouseEntity("R", 1);

		this.w.init();
		this.a.init();
		this.s.init();
		this.d.init();
		this.jump.init();
		this.left.init();
		this.right.init();
	}

	@Override
	public void draw(double x, double y) {
		double scaled = this.getPosition().getValue().getScaled();
		int diffX = 24;
		int diffY = 0;
		boolean jump = this.module.jump.getValue();
		boolean mouse = this.module.mouse.getValue();
		this.w.draw(x + diffX, y + diffY, scaled);
		diffX = 0;
		diffY = 24;
		this.a.draw(x + diffX, y + diffY, scaled);
		diffX = 24;
		this.s.draw(x + diffX, y + diffY, scaled);
		diffX = 48;
		this.d.draw(x + diffX, y + diffY, scaled);
		if (jump) {
			diffX = 0;
			diffY += 24;
			this.jump.draw(x + diffX, y + diffY, scaled);
		}
		if (mouse) {
			diffX = 0;
			diffY += jump ? 13 : 24;
			this.left.draw(x + diffX, y + diffY, scaled);
			diffX = 36;
			this.right.draw(x + diffX, y + diffY, scaled);
		}
	}

	@Override
	public int getHeight() {
		boolean jump = false;
		boolean mouse = false;
		if (this.module != null) {
			jump = this.module.jump.getValue();
			mouse = this.module.mouse.getValue();
		}
		int diffY = 48;
		if (jump) {
			diffY += 24;
		}
		if (mouse) {
			diffY += jump ? 13 : 24;
		}
		return diffY;
	}

	@Override
	public int getWidth() {
		return 70;
	}

	public void onClick(MouseEvent event) {
		this.left.onClick(event);
		this.right.onClick(event);
	}

}
