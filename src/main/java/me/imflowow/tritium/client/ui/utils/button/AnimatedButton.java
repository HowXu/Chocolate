package me.imflowow.tritium.client.ui.utils.button;

import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;

public class AnimatedButton extends SimpleButton{
	Translate anim = new Translate(0, 0);
	int borderColor;
	public AnimatedButton(String text, double x, double y, double width, double height, int textColor,int borderColor, int color,
			Runnable click) {
		super(text, x, y, width, height, textColor, color, click);
		this.borderColor = borderColor;
	}
	
	@Override
	public void draw() {
		this.click.tick();
		new Rect(x, y, width, height, color, Rect.RenderType.Expand).draw();
		StringUtils.drawCenteredStringWithShadow(text, x + width / 2, y + height / 2 - 2, textColor, SizeType.Size16);
		
		if (click.isInArea()) {
			anim.interpolate((float) width + 1, (float) height + 1, 0.3f);
		} else {
			anim.interpolate(0, 0, 0.3f);
		}
		
		new Rect(x, y - 1, anim.getX(), 1, borderColor, Rect.RenderType.Expand).draw();
		new Rect(x + width, y, 1, anim.getY(), borderColor, Rect.RenderType.Expand).draw();
		new Rect(x + width - anim.getX(), y + height, anim.getX(), 1, borderColor, Rect.RenderType.Expand).draw();
		new Rect(x - 1, y + height - anim.getY(), 1, anim.getY(), borderColor, Rect.RenderType.Expand).draw();
	}

}
