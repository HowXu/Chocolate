package me.imflowow.tritium.core.modules;

import net.minecraft.client.gui.ScaledResolution;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.module.value.utils.HSBColor;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.CrosshairEvent;
import tritium.api.utils.event.events.MouseEvent;
import tritium.api.utils.event.events.Render2DEvent;
import tritium.api.utils.render.Rect;

public class Crosshair extends Module {
	ColorValue crosshairColor = new ColorValue("CrosshairColor", new HSBColor(34, 255, 10, 255));
	ColorValue dotColor = new ColorValue("DotColor", new HSBColor(0, 0, 0, 255));
	ColorValue outlineColor = new ColorValue("OutlineColor", new HSBColor(0, 0, 0, 255));

	BooleanValue dot = new BooleanValue("Dot", true);
	BooleanValue outline = new BooleanValue("Outline", true);

	NumberValue<Double> size = new NumberValue("Size", 1.0, 0.5, 10.0, 0.1);
	NumberValue<Double> dotsize = new NumberValue("DotSize", 1.0, 0.5, 10.0, 0.1);
	NumberValue<Double> outlinesize = new NumberValue("OutlineSize", 0.5, 0.5, 10.0, 0.1);

	NumberValue<Double> width = new NumberValue("Width", 12.0, 0.0, 40.0, 0.1);
	NumberValue<Double> height = new NumberValue("Height", 12.0, 0.0, 40.0, 0.1);
	NumberValue<Double> gap = new NumberValue("Gap", 8.0, 0.0, 40.0, 0.1);

	BooleanValue animation = new BooleanValue("Animation", true);
	NumberValue<Double> animationGap = new NumberValue("AnimationGap", 8.0, 0.0, 40.0, 0.1);
	NumberValue<Float> animationSpeed = new NumberValue("AnimationSpeed", 0.3f, 0.01f, 1.0f, 0.01f);

	Translate anim = new Translate(0, 0);

	public Crosshair() {
		super("Crosshair", "Make your crosshair great again!");
		super.addValues(crosshairColor, dotColor, outlineColor, dot, dotsize, outline, outlinesize, size, width, height,
				gap, animation, animationGap, animationSpeed);
	}

	@EventTarget
	public void onCrosshair(CrosshairEvent event) {
		event.setCancelled(true);
		this.anim.interpolate(0, 0, this.animationSpeed.getValue());
		if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug()
				&& !this.mc.gameSettings.reducedDebugInfo) {
			return;
		}
		ScaledResolution sr = event.getScaledresolution();

		double left = sr.getScaledWidth_double() / 2;
		double top = sr.getScaledHeight_double() / 2;
		double size = this.size.getValue();
		double dotsize = this.dotsize.getValue();
		double outlinesize = this.outlinesize.getValue();

		if (this.dot.getValue()) {
			new Rect(left - dotsize / 2, top - dotsize / 2, dotsize, dotsize,
					this.dotColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();
		}
		double diff = this.animation.getValue() ? this.anim.getX() : 0;

		new Rect(left - this.gap.getValue() - diff - this.width.getValue(), top - size / 2, this.width.getValue(), size,
				this.crosshairColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();// LEFT
		new Rect(left + this.gap.getValue() + diff, top - size / 2, this.width.getValue(), size,
				this.crosshairColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();// RIGHT
		new Rect(left - size / 2, top - this.gap.getValue() - diff - this.height.getValue(), size,
				this.height.getValue(), this.crosshairColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
						.draw();// TOP
		new Rect(left - size / 2, top + this.gap.getValue() + diff, size, this.height.getValue(),
				this.crosshairColor.getValue().getColor().getRGB(), Rect.RenderType.Expand).draw();// BOTTOM
		if (this.outline.getValue()) {
			if (this.dot.getValue()) {
				this.drawOutsideRect(left - dotsize / 2, top - dotsize / 2, dotsize, dotsize, outlinesize,
						this.outlineColor.getValue().getColor().getRGB());
			}
			this.drawOutsideRect(left - this.gap.getValue() - diff - this.width.getValue(), top - size / 2,
					this.width.getValue(), size, outlinesize, this.outlineColor.getValue().getColor().getRGB());
			this.drawOutsideRect(left + this.gap.getValue() + diff, top - size / 2, this.width.getValue(), size,
					outlinesize, this.outlineColor.getValue().getColor().getRGB());
			this.drawOutsideRect(left - size / 2, top - this.gap.getValue() - diff - this.height.getValue(), size,
					this.height.getValue(), outlinesize, this.outlineColor.getValue().getColor().getRGB());
			this.drawOutsideRect(left - size / 2, top + this.gap.getValue() + diff, size, this.height.getValue(),
					outlinesize, this.outlineColor.getValue().getColor().getRGB());
		}
	}

	@EventTarget
	public void onMouse(MouseEvent event) {
		if(event.isDown()) {
			this.anim.setX(this.animationGap.getValue().floatValue());
		}
	}

	private void drawOutsideRect(double x, double y, double x2, double y2, double width, int color) {
		this.drawOutsideRect2(x, y, x + x2, y + y2, width, color);
	}

	private void drawOutsideRect2(double x, double y, double x2, double y2, double width, int color) {
		if (x > x2) {
			double i = x;
			x = x2;
			x2 = i;
		}

		if (y > y2) {
			double j = y;
			y = y2;
			y2 = j;
		}

		new Rect(x, y - width, x - width, y2, color, Rect.RenderType.Position).draw();
		new Rect(x, y, x2 + width, y - width, color, Rect.RenderType.Position).draw();
		new Rect(x2, y, x2 + width, y2 + width, color, Rect.RenderType.Position).draw();
		new Rect(x - width, y2, x2, y2 + width, color, Rect.RenderType.Position).draw();
	}
}
