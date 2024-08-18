package me.imflowow.tritium.core.modules.components.keystrokes;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.Tritium;
import net.minecraft.client.settings.KeyBinding;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Blur;
import tritium.api.utils.render.Circle;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class KeyEntity extends KeyStrokesUI {

	String text;
	KeyBinding keycode;

	public KeyEntity(String text, KeyBinding keycode) {
		this.text = text;
		this.keycode = keycode;
	}

	Blur blur;

	Scissor scissor;

	HashMap<KeyCircle, Translate> circles = new HashMap<>();

	boolean press;

	@Override
	public void init() {
		this.scissor = new Scissor(0, 0, 22, 22);
	}

	@Override
	public void draw(double x, double y, double scaled) {
		this.scissor.setX(x * scaled);
		this.scissor.setY(y * scaled);
		this.scissor.setScale(scaled);
		this.scissor.doScissor();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		new Rect(x, y, 22, 22, this.getColor(0), Rect.RenderType.Expand).draw();

		if (!keycode.isKeyDown())
			press = false;

		if (keycode.isKeyDown() && !press) {
			circles.put(new KeyCircle(new Circle(x + 11, y + 11, 0, this.getColor(1)), 0), new Translate(0, 255));
			press = true;
		}

		try {
			circles.forEach((keyCircle, translate) -> {

				if (hasKeyDownCircle() != null && hasKeyDownCircle() != keyCircle)
					keyCircle.target = 0;
				else
					keyCircle.target = keycode.isKeyDown() ? 180 : 0;

				translate.interpolate(16, keyCircle.target, getAnimationSpeed());

				keyCircle.circle.setRadius(translate.getX());
				final int f = (getColor(1) >> 16 & 255);
				final int f1 = (getColor(1) >> 8 & 255);
				final int f2 = (getColor(1) & 255);
				final int f3 = (int) Math.round(translate.getY());

				keyCircle.circle.setColor(new Color(f, f1, f2, f3).getRGB());
				keyCircle.circle.setX(x + 11);
				keyCircle.circle.setY(y + 11);
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

		int width = Tritium.instance.getFontManager().arial18.getStringWidth(text);
		int height = Tritium.instance.getFontManager().arial18.getStringHeight(text) - 2;
		Tritium.instance.getFontManager().arial18.drawString(text, x + 11 - width / 2.0, y + 11 - height / 2.0,
				this.getColor(2));
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
