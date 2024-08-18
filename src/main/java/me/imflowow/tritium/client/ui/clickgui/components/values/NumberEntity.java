package me.imflowow.tritium.client.ui.clickgui.components.values;

import java.math.BigDecimal;

import org.lwjgl.input.Mouse;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.value.Value;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class NumberEntity extends ValueEntity {

	private Translate anim;
	private float curX;
	private float tarX;

	private ClickEntity clickentity;

	private int mouseX;
	private double positionX;
	private boolean locked;

	public NumberEntity(NumberValue value) {
		super(value);
	}

	@Override
	public void init(double positionX, double positionY) {
		this.clickentity = new ClickEntity(0, 0, 0, 0, CallType.Expand, () -> {
			this.locked = true;
		}, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		});

		double inc = this.getValue().getInc().doubleValue();
		double max = this.getValue().getMaximum().doubleValue();
		double min = this.getValue().getMinimum().doubleValue();
		double value = this.getValue().getSliderValue().doubleValue();
		this.tarX = this.curX = (float) ((125 / (max - min)) * (value - min));
		this.anim = new Translate(this.tarX, 0);
		this.locked = false;
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
		if (!Mouse.isButtonDown(0))
			this.locked = false;

		this.mouseX = mouseX;
		this.positionX = positionX;
		this.clickentity.setX(positionX - 2);
		this.clickentity.setX1(134);
		this.clickentity.setY(positionY + 12);
		this.clickentity.setY1(6);
		this.clickentity.tick();

		double inc = this.getValue().getInc().doubleValue();
		double max = this.getValue().getMaximum().doubleValue();
		double min = this.getValue().getMinimum().doubleValue();
		double value = this.getValue().getSliderValue().doubleValue();

		this.tarX = (float) ((125 / (max - min)) * (value - min));
		this.anim.interpolate(this.tarX, 0, 0.3f);
		this.curX = (float) this.anim.getX();
		StringUtils.drawString(this.value.getLabel(), positionX, positionY + 4, this.getColor(20), SizeType.Size14);

		String text;
		int width;
		text = this.getValue().getValue().toString();
		width = StringUtils.getWidth(text, SizeType.Size14);
		StringUtils.drawString(text, positionX + 130 - width, positionY + 4, this.getColor(20), SizeType.Size14);

		new Rect(positionX, positionY + 14, 130, 2, this.getColor(25), Rect.RenderType.Expand).draw();
		new Rect(positionX, positionY + 14, this.curX, 2, this.getColor(27), Rect.RenderType.Expand).draw();
		Tritium.instance.getFontManager().logo14.drawString("t", positionX + this.curX, positionY + 14,
				this.getColor(26));

		if (this.isClickable() && this.locked) {
			double pos = this.mouseX - (this.positionX);
			if (pos < 0) {
				pos = 0;
			}
			if (pos > 130) {
				pos = 130;
			}
			double result = new BigDecimal((((int) (pos / (130 / (max - min)) / inc)) * inc) + min)
					.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			value = result;
			if (this.getValue().getValue() instanceof Double) {
				this.getValue().setValue(value);
			} else if (this.getValue().getValue() instanceof Integer) {
				this.getValue().setValue((int) value);
			} else if (this.getValue().getValue() instanceof Float) {
				this.getValue().setValue((float) value);
			} else if (this.getValue().getValue() instanceof Long) {
				this.getValue().setValue((long) value);
			}
		}
	}

	@Override
	public double getHeight() {
		return 16;
	}

	@Override
	public NumberValue getValue() {
		return (NumberValue) super.getValue();
	}

}
