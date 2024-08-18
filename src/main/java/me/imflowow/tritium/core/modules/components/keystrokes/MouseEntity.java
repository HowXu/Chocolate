package me.imflowow.tritium.core.modules.components.keystrokes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.imflowow.tritium.core.Tritium;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.event.events.MouseEvent;
import tritium.api.utils.render.Blur;
import tritium.api.utils.render.Circle;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;

public class MouseEntity extends KeyStrokesUI {

	String text;
	int keycode;
	ArrayList<Long> click;

	public MouseEntity(String text, int keycode) {
		this.text = text;
		this.keycode = keycode;
	}

	Scissor scissor;

	Blur blur;

	HashMap<KeyCircle, Translate> circles = new HashMap<>();

	boolean press;

	@Override
	public void init() {
		this.scissor = new Scissor(0, 0, 34, 22);
		this.blur = new Blur(0, 0, 34, 22, 4);
		this.click = new ArrayList();
	}

	@Override
	public void draw(double x, double y, double scaled) {
		this.scissor.setX(x * scaled);
		this.scissor.setY(y * scaled);
		this.scissor.setScale(scaled);
		this.scissor.doScissor();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		new Rect(x, y, 34, 22, this.getColor(0), Rect.RenderType.Expand).draw();

		if (!Mouse.isButtonDown(keycode))
			press = false;

		if (Mouse.isButtonDown(keycode) && !press) {
			circles.put(new KeyCircle(new Circle(x + 17, y + 11, 0, this.getColor(1)), 0), new Translate(0, 255));
			press = true;
		}

		try {
			circles.forEach((keyCircle, translate) -> {

				if (hasKeyDownCircle() != null && hasKeyDownCircle() != keyCircle)
					keyCircle.target = 0;
				else
					keyCircle.target = Mouse.isButtonDown(keycode) ? 180 : 0;

				translate.interpolate(20, keyCircle.target, getAnimationSpeed());

				keyCircle.circle.setRadius(translate.getX());
				final int f = (getColor(1) >> 16 & 255);
				final int f1 = (getColor(1) >> 8 & 255);
				final int f2 = (getColor(1) & 255);
				final int f3 = (int) Math.round(translate.getY());

				keyCircle.circle.setColor(new Color(f, f1, f2, f3).getRGB());
				keyCircle.circle.setX(x + 17);
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

		boolean cpsdisplay = this.getCPSDisplay();

		int width = Tritium.instance.getFontManager().arial18.getStringWidth(text);
		int height = Tritium.instance.getFontManager().arial18.getStringHeight(text) - 2 + (cpsdisplay ? 6 : 0);
		Tritium.instance.getFontManager().arial18.drawString(text, x + 17 - width / 2.0, y + 11 - height / 2.0,
				this.getColor(2));
		if (cpsdisplay) {
			String text = this.getCPS() + " CPS";
			width = Tritium.instance.getFontManager().arialBold10.getStringWidth(text);
			height = Tritium.instance.getFontManager().arialBold10.getStringHeight(text) - 10;
			Tritium.instance.getFontManager().arialBold10.drawString(text, x + 17 - width / 2.0, y + 11 - height / 2.0,
					this.getColor(2));
		}
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

	public void onClick(MouseEvent event) {
		if (event.isDown()) {
			if (event.getButton() == this.keycode) {
				this.addClick();
			}
		}
	}

	public int getCPS() {
		long time = System.currentTimeMillis();
		this.click.removeIf(e -> time - e > 1000);
		return this.click.size();
	}

	public void addClick() {
		this.click.add(System.currentTimeMillis());
	}
}
