package me.imflowow.tritium.client.ui.clickgui.components.values;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.value.Value;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.clickable.entity.ClickableString;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class BooleanEntity extends ValueEntity {
	private ClickEntity rect;
	private boolean isHover;

	public BooleanEntity(BooleanValue value) {
		super(value);
	}

	@Override
	public void init(double positionX, double positionY) {
		this.rect = new ClickEntity(positionX + 120, positionY + 2, 10, 10, CallType.Expand, () -> {
			if(!this.isClickable())
				return;
			this.getValue().setEnabled(!this.getValue().isEnabled());
		}, () -> {
		}, () -> {
			isHover = true;
		}, () -> {
		}, () -> {
			isHover = false;
		});
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
		this.rect.setX(positionX + 120);
		this.rect.setY(positionY + 2);
		this.rect.tick();
		StringUtils.drawString(value.getLabel(), positionX, positionY + 4,
				this.getColor(20), SizeType.Size14);
		Tritium.instance.getFontManager().logo24.drawString("g", positionX + 120, positionY + 2,
				isHover ? this.getColor(22) : this.getColor(21));
		if (this.getValue().isEnabled()) {
			Tritium.instance.getFontManager().logo16.drawString("r", positionX + 122, positionY + 4, this.getColor(20));
		}
	}

	@Override
	public double getHeight() {
		return 8;
	}

	@Override
	public BooleanValue getValue() {
		return (BooleanValue) super.getValue();
	}

}
