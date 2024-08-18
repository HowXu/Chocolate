package me.imflowow.tritium.client.ui.clickgui.components.values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.value.Value;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class EnumEntity extends ValueEntity {

	Translate animation;

	private Scissor scissors;
	private ClickableRect rect;
	private ClickEntity clickarea;
	private boolean clickable;

	private boolean isOpen;

	private List<EnumButton> values;

	public EnumEntity(EnumValue value) {
		super(value);
	}

	@Override
	public void init(double positionX, double positionY) {
		this.animation = new Translate(StringUtils.getWidth(this.getValue().getFixedValue(), SizeType.Size14)
				 + 2, 10);
		this.isOpen = false;
		this.scissors = new Scissor(0, 0, 0, 0);
		this.clickarea = new ClickEntity(0, 0, 0, 0, CallType.Expand, () -> {
		}, () -> {
		}, () -> {
			this.clickable = true;
		}, () -> {
		}, () -> {
			this.clickable = false;
		});
		this.rect = new ClickableRect(0, 0, 0, 0, this.getColor(23), Rect.RenderType.Expand, () -> {
			if (!this.isClickable())
				return;
				this.isOpen = !this.isOpen;
		}, () -> {
		}, () -> {
			this.rect.setColor(this.getColor(24));
		}, () -> {
		}, () -> {
			this.rect.setColor(this.getColor(23));
		});
		this.values = new ArrayList();
		for (Enum e : this.getValue().getConstants()) {
			values.add(new EnumButton(this, e.toString()));
		}
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
		StringUtils.drawString(value.getLabel(), positionX, positionY + 4,
				this.getColor(20), SizeType.Size14);
		int width = StringUtils.getWidth(this.getValue().getFixedValue(), SizeType.Size14) + 2;
		
		this.scissors.setX(positionX + 131 - this.animation.getX());
		this.scissors.setY(Math.max(positionY, this.getClickGui().getValueList().getClickarea().getY()));

		double diff = positionY - this.getClickGui().getValueList().getClickarea().getY();

		this.scissors.setWidth(Math.max(Math.min(this.animation.getX(),
				this.getClickGui().getValueList().getRectanim().getX() - (150 - this.animation.getX())), 0));
		this.scissors.setHeight(Math.max(diff < 0 ? this.animation.getY() + diff : this.animation.getY(), 0));
		this.scissors.doScissor();

		this.clickarea.setX(positionX + 131 - this.animation.getX());
		this.clickarea.setY(Math.max(positionY, this.getClickGui().getValueList().getClickarea().getY()));
		this.clickarea.setX1(Math.max(Math.min(this.animation.getX(),
				this.getClickGui().getValueList().getRectanim().getX() - (150 - this.animation.getX())), 0));
		this.clickarea.setY1(Math.max(Math.min(this.animation.getY(), this.animation.getY()), 0));
		this.clickarea.tick();

		this.rect.setX(positionX + 131 - this.getWidth());
		this.rect.setY(positionY);
		this.rect.setWidth(this.getWidth());
		this.rect.setHeight(10);
		this.rect.draw();

		StringUtils.drawString(this.getValue().getFixedValue(),
				positionX + 132 - (getWidth() / 2 + width / 2), positionY + 3.5, this.getColor(20), SizeType.Size14);
		int y = 10;
		for (EnumButton button : values) {
			if (button.getValue().equals(this.getValue().getFixedValue()))
				continue;
			button.draw(mouseX, mouseY, positionX + 131 - this.getWidth(), positionY + y);
			y += 10;
		}
		if (this.isOpen) {
			this.animation.interpolate(this.getWidth(), values.size() * 10, 0.3f);
		} else {
			this.animation.interpolate(this.getWidth(), 10, 0.3f);
		}

		this.getClickGui().getWholeScreenScissor().doScissor();

	}

	public int getWidth() {
		int width = StringUtils.getWidth(this.getValue().getFixedValue(), SizeType.Size14) + 2;
		return this.isOpen ? this.getLongestWidth() + 2 : width;
	}

	public int getLongestWidth() {
		List<Integer> nums = new ArrayList<Integer>();
		for (Enum e : this.getValue().getConstants()) {
			nums.add(StringUtils.getWidth(e.toString(), SizeType.Size14));
		}
		return Collections.max(nums);
	}

	@Override
	public double getHeight() {
		return this.animation.getY() - 2;
	}

	@Override
	public EnumValue getValue() {
		return (EnumValue) super.getValue();
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public ClickEntity getClickarea() {
		return clickarea;
	}

	public void setClickarea(ClickEntity clickarea) {
		this.clickarea = clickarea;
	}

	@Override
	protected boolean isClickable() {
		return super.isClickable() && this.clickable;
	}
}
