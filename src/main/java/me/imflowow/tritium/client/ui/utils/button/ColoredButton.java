package me.imflowow.tritium.client.ui.utils.button;

import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;

public class ColoredButton extends SimpleButton {
	int textInAreaColor;

	public ColoredButton(String text, double x, double y, double width, double height, int textColor,
			int textInAreaColor, int color, Runnable click) {
		super(text, x, y, width, height, textColor, color, click);
		this.textInAreaColor = textInAreaColor;
	}

	@Override
	public void draw() {
		this.click.tick();
		new Rect(x, y, width, height, color, Rect.RenderType.Expand).draw();
		int col;
		if (click.isInArea()) {
			col = textInAreaColor;
		} else {
			col = textColor;
		}
		StringUtils.drawCenteredStringWithShadow(text, x + width / 2, y + height / 2 - 2, col, SizeType.Size16);
	}

}
