package me.imflowow.tritium.client.ui.clickgui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.imflowow.tritium.client.ui.clickgui.components.values.BooleanEntity;
import me.imflowow.tritium.client.ui.clickgui.components.values.ColorEntity;
import me.imflowow.tritium.client.ui.clickgui.components.values.EnumEntity;
import me.imflowow.tritium.client.ui.clickgui.components.values.NumberEntity;
import me.imflowow.tritium.client.ui.clickgui.components.values.ValueEntity;
import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.Module;
import tritium.api.module.value.Value;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.ColorValue;
import tritium.api.module.value.impl.EnumValue;
import tritium.api.module.value.impl.NumberValue;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.clickable.entity.ClickableString;
import tritium.api.utils.render.utils.ColorAnimations;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class ValueList extends UIComponent {
	private Translate rectanim = new Translate(0, 0);
	private Scissor scissor;
	private ClickEntity clickarea;
	private boolean clickable;
	private boolean locked;

	private Rect background;

	private Translate animation = new Translate(0, 0);
	private ClickableString back;
	private ColorAnimations backColor;

//	private Translate animation = new Translate(0, 0);
	private double tarY;
	private double curY;

	private boolean open;

	private List<ValueEntity> values = new ArrayList();

	@Override
	public void init(double positionX, double positionY) {
		this.tarY = 0;
		this.curY = 0;
		this.open = false;
		this.scissor = new Scissor(0, 0, 150, 300);
		this.background = new Rect(0, 0, 150, 300, this.getColor(15), Rect.RenderType.Expand);
		Color col = new Color(this.getColor(17));
		this.backColor = new ColorAnimations(col.getRed(), col.getGreen(), col.getBlue(), this.getMenuAlpha());
		this.clickarea = new ClickEntity(0, 0, 0, 0, CallType.Expand, () -> {
		}, () -> {
		}, () -> {
			clickable = true;
		}, () -> {
		}, () -> {
			clickable = false;
		});
		this.back = new ClickableString(Tritium.instance.getFontManager().arial32, "-", 0, 0, this.getColor(17), () -> {
			this.getClickGui().getModuleList().setLastOnSetting(this.getClickGui().getModuleList().getOnSetting());
			this.getClickGui().getModuleList().setOnSetting(null);
		}, () -> {
		}, () -> {
			this.back.setColor(this.getColor(18));
		}, () -> {
		}, () -> {
			this.back.setColor(this.getColor(17));
		});
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {

		if ((this.getClickGui().getModuleList().getOnSetting() == null) || (this.getClickGui().getModuleList()
				.getOnSetting() != this.getClickGui().getModuleList().getLastOnSetting())) {
			open = false;
		} else {
			open = true;
		}
		if (open) {
			rectanim.interpolate(150, 0, 0.3f);
		} else {
			rectanim.interpolate(0, 0, 0.3f);
		}

		if (this.isClickable()) {
			if (!values.isEmpty()) {
				if (this.tarY + this.getWheel() < 0) {
					if (this.tarY + this.getWheel() > -this.getValuesHeight() + 5
							+ values.get(values.size() - 1).getHeight()) {
						this.tarY += this.getWheel() * 2;
					} else {
						this.tarY = -this.getValuesHeight() + 5 + values.get(values.size() - 1).getHeight();
					}
				} else {
					this.tarY = 0;
				}
			}
		}

		this.animation.interpolate((float) this.tarY, 0, 0.3f);
		this.curY = animation.getX();

		this.scissor.setX(positionX + 250);
		this.scissor.setY(positionY);
		this.scissor.setWidth(rectanim.getX());
		this.scissor.setHeight(300);
		this.scissor.doScissor();

		this.background.setColor(this.getColor(15));
		this.background.setX(positionX + 250);
		this.background.setY(positionY);
		this.background.draw();

		this.back.setX(positionX + 250 + 6);
		this.back.setY(positionY + 2);
		this.back.draw(false);

		if (rectanim.getX() == 0) {
			this.tarY = 0;
			this.values.clear();
			this.getClickGui().getModuleList().setLastOnSetting(this.getClickGui().getModuleList().getOnSetting());
		}
		if (this.getClickGui().getModuleList().getOnSetting() != null && this.values.isEmpty()) {
			this.tarY = 0;
			Module module = this.getClickGui().getModuleList().getOnSetting();
			for (Value value : module.getValues(false)) {
				if (value instanceof BooleanValue) {
					BooleanValue val = (BooleanValue) value;
					values.add(new BooleanEntity(val));
				}

				if (value instanceof NumberValue) {
					NumberValue val = (NumberValue) value;
					values.add(new NumberEntity(val));
				}

				if (value instanceof EnumValue) {
					EnumValue val = (EnumValue) value;
					values.add(new EnumEntity(val));
				}

				if (value instanceof ColorValue) {
					ColorValue val = (ColorValue) value;
					values.add(new ColorEntity(val));
				}

			}
		}

		if (this.getClickGui().getModuleList().getLastOnSetting() != null) {
			Module module = this.getClickGui().getModuleList().getLastOnSetting();

			StringUtils.drawString(module.getLabel(), positionX + 250 + 25, positionY + 8,
					this.getColor(19), SizeType.Size16);
			this.scissor.setX(positionX + 258);
			this.scissor.setY(positionY + 20);
			this.scissor.setWidth(Math.max(0, Math.min(134, rectanim.getX() - 8)));
			this.scissor.setHeight(272);
			this.scissor.doScissor();

			this.clickarea.setX(positionX + 258);
			this.clickarea.setY(positionY + 20);
			this.clickarea.setX1(Math.max(0, Math.min(134, rectanim.getX() - 8)));
			this.clickarea.setY1(272);
			this.clickarea.tick();

			int y = 0;
			for (ValueEntity entity : this.values) {
				this.scissor.setX(positionX + 258);
				this.scissor.setY(positionY + 20);
				this.scissor.setWidth(Math.max(0, Math.min(134, rectanim.getX() - 8)));
				this.scissor.setHeight(272);
				this.scissor.doScissor();
				entity.draw(mouseX, mouseY, positionX + 250 + 8, positionY + 20 + y + curY);
				y += 5 + entity.getHeight();
			}
			this.setLocked(false);

		}

		this.getClickGui().getWholeScreenScissor().doScissor();
	}

	public int getValuesHeight() {
		int height = 0;
		for (ValueEntity ve : this.values) {
			height += 5 + ve.getHeight();
		}
		return height;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isClickable() {
		return this.clickable && !this.locked;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public Translate getRectanim() {
		return rectanim;
	}

	public void setRectanim(Translate rectanim) {
		this.rectanim = rectanim;
	}

	public double getCurY() {
		return curY;
	}

	public void setCurY(double curY) {
		this.curY = curY;
	}

	public ClickEntity getClickarea() {
		return clickarea;
	}

	public void setClickarea(ClickEntity clickarea) {
		this.clickarea = clickarea;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

}
