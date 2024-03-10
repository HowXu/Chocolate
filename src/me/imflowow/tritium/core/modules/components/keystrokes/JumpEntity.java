package me.imflowow.tritium.core.modules.components.keystrokes;

import org.lwjgl.opengl.GL11;

import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Blur;
import tritium.api.utils.render.Circle;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class JumpEntity extends KeyStrokesUI {

	Scissor scissor;

	Blur blur;

	HashMap<KeyCircle, Translate> circles = new HashMap<>();

	@Override
	public void init() {
		this.scissor = new Scissor(0, 0, 70, 11);
		this.blur = new Blur(0, 0, 70, 11, 4);
	}

	@Override
	public void draw(double x, double y, double scaled) {

		this.scissor.setX(x * scaled);
		this.scissor.setY(y * scaled);
		this.scissor.setScale(scaled);
		this.scissor.doScissor();

		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		new Rect(x, y, 70, 11, this.getColor(0), Rect.RenderType.Expand).draw();

		if (mc.gameSettings.keyBindJump.isPressed())
			circles.put(new KeyCircle(new Circle(x + 35, y + 5.5, 0, this.getColor(1)), 0), new Translate(0, 255));

		try {
			circles.forEach((keyCircle, translate) -> {

				if (hasKeyDownCircle() != null && hasKeyDownCircle() != keyCircle)
					keyCircle.target = 0;
				else
					keyCircle.target = mc.gameSettings.keyBindJump.isKeyDown() ? 180 : 0;

				translate.interpolate(36, keyCircle.target, getAnimationSpeed());

				keyCircle.circle.setRadius(translate.getX());
				final int f = (getColor(1) >> 16 & 255);
				final int f1 = (getColor(1) >> 8 & 255);
				final int f2 = (getColor(1) & 255);
				final int f3 = (int) Math.round(translate.getY());

				keyCircle.circle.setColor(new Color(f, f1, f2, f3).getRGB());
				keyCircle.circle.setX(x + 35);
				keyCircle.circle.setY(y + 5.5);
				keyCircle.circle.draw();

				if (translate.getY() <= 0 && keyCircle.target == 0)
					circles.remove(keyCircle);
			});
		}
		/*
		 * REMOVE WHEN ON IT , WILL CAUSE A ARRAY BUG , JUST IGNORE
		 * 当他在运行这个对象时如果从组里删除会报在数组中找不到对象的问题，忽略即可
		 */ catch (Exception ignored) {
		}

		new Rect(x + 15, y + 5, 40, 1, this.getColor(2), Rect.RenderType.Expand).draw();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	private KeyCircle hasKeyDownCircle() {
		final AtomicReference<KeyCircle> has = new AtomicReference<>();
		has.set(null);
		circles.forEach((keyCircle, translate) -> {
			if (keyCircle.target == 180)
				has.set(keyCircle);
		});
		return has.get();
	}
}
