package me.imflowow.tritium.client.ui.utils.input;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;
import tritium.api.utils.timer.MsTimer;

public class GuiFlatInput extends Gui {
	String text = "";
	String emptyText;
	boolean input;
	double x;
	double y;
	double width;
	double height;
	int color;
	int borderColor;
	int textColor;
	boolean password;
	ClickEntity click;

	Translate anim = new Translate(0, 0);

	boolean hidden;

	MsTimer timer = new MsTimer();
	boolean underline;

	public GuiFlatInput(String text, double x, double y, double width, double height, int color, int textColor,
			int borderColor, boolean password) {
		this.emptyText = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.textColor = textColor;
		this.borderColor = borderColor;
		this.password = password;
		this.click = new ClickEntity(x, y, width, height, CallType.Expand, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		});
	}

	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.click.tick();
		if (click.isInArea()) {

			if (click.isLeftPressed()) {
				input = true;
			}
		} else {
			if (click.isLeftPressed()) {
				input = false;
			}
		}

		if (click.isInArea() || input) {
			anim.interpolate((float) width + 1, (float) height + 1, 0.3f);
		} else {
			anim.interpolate(0, 0, 0.3f);
		}

		if (timer.sleep(500))
			underline = !underline;

		new Rect(x, y, width, height, color, Rect.RenderType.Expand).draw();

		new Rect(x, y - 1, anim.getX(), 1, borderColor, Rect.RenderType.Expand).draw();
		new Rect(x + width, y, 1, anim.getY(), borderColor, Rect.RenderType.Expand).draw();
		new Rect(x + width - anim.getX(), y + height, anim.getX(), 1, borderColor, Rect.RenderType.Expand).draw();
		new Rect(x - 1, y + height - anim.getY(), 1, anim.getY(), borderColor, Rect.RenderType.Expand).draw();

		String renderString = text;
		if (password)
			renderString = this.text.replaceAll("(?s).", "*");
		if (text.isEmpty() && !input) {
			StringUtils.drawString(emptyText, x + 2, y + height / 2 - 2, textColor, SizeType.Size16);
		} else {
			Tritium.instance.getFontManager().arial16.drawString(renderString + ((underline && input) ? "_" : ""),
					x + 2, y + height / 2 - 2, textColor);
		}
	}

	public void keyTyped(char typedChar, int keyCode) throws IOException {
		if (input) {
			if (keyCode == Keyboard.KEY_BACK && !text.isEmpty()) {
				text = text.substring(0, text.length() - 1);
				return;
			}

			text += ChatAllowedCharacters.filterAllowedCharacters(String.valueOf(typedChar));
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
