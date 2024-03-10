package tritium.api.utils.render.clickable.entity;

import tritium.api.utils.font.MCFontRenderer;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class ClickableString extends ClickEntity {
	MCFontRenderer font;
	String text;
	int color;

	public ClickableString(MCFontRenderer font, String text, double x, double y, int color, Runnable click,
			Runnable hold, Runnable focus, Runnable release,Runnable onBlur) {
		super(x, y, 0, 0, CallType.Expand, click, hold, focus, release,onBlur);
		this.font = font;
		this.text = text;
		this.color = color;
	}

	public void draw(boolean shadow) {
		int height = font.getHeight();
		int lenth = font.getStringHeight(text);
		if (shadow) {
			font.drawStringWithShadow(text, this.getX(), this.getY(), color);
		} else {
			font.drawString(text, this.getX(), this.getY(), color);
		}

		super.setX1(lenth);
		super.setY1(height);

		super.tick();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public double getX() {
		return super.getX();
	}

	@Override
	public void setX(double x) {
		super.setX(x);
	}

	@Override
	public double getY() {
		return super.getY();
	}

	@Override
	public void setY(double y) {
		super.setY(y);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
