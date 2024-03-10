package me.imflowow.tritium.client.ui.utils.button;

import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.gui.Gui;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class SimpleButton extends Gui {
	boolean hidden;
	String text;
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	protected int textColor;
	int color;
	ClickEntity click;

	public SimpleButton(String text, double x, double y, double width, double height, int textColor, int color,
			Runnable click) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textColor = textColor;
		this.color = color;
		this.click = new ClickEntity(x, y, width, height, CallType.Expand, click, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		});
	}

	public void draw() {
		this.click.tick();
		new Rect(x, y, width, height, color, Rect.RenderType.Expand).draw();
		StringUtils.drawCenteredStringWithShadow(text, x + width / 2, y + height / 2 - 2, textColor, SizeType.Size16);
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
