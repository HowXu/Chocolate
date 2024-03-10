package me.imflowow.tritium.client.ui.clickgui.components.values;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.entity.ClickableRect;

public class EnumButton extends UIComponent {
	ClickableRect rect;
	EnumEntity values;
	String value;

	public EnumButton(EnumEntity values, String value) {
		super();
		this.values = values;
		this.value = value;
		this.rect = new ClickableRect(0, 0, 0, 0, this.getColor(23), Rect.RenderType.Expand, () -> {
			if(!this.values.isClickable())
				return;
			this.values.setLocked(true);
			this.values.getValue().setValue(this.value);
			this.values.setOpen(false);
		}, () -> {
		}, () -> {
			this.rect.setColor(this.getColor(24));
		}, () -> {
		}, () -> {
			this.rect.setColor(this.getColor(23));
		});

	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {

		this.rect.setX(positionX);
		this.rect.setY(positionY);
		this.rect.setWidth(this.values.getWidth());
		this.rect.setHeight(10);
		this.rect.draw();
		
		int width = StringUtils.getWidth(value, SizeType.Size14) + 2;
		StringUtils.drawString(value,
				positionX + 1  + (this.values.getWidth() / 2 - width / 2), positionY + 3.5, this.getColor(20), SizeType.Size14);

	}

	public ClickableRect getRect() {
		return rect;
	}

	public void setRect(ClickableRect rect) {
		this.rect = rect;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
