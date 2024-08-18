package me.imflowow.tritium.client.ui.utils.button;

import java.awt.Color;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.globals.ClientConfig;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.entity.ClickableRect;

public class FlatMainButton {
	String title;
	ClickableRect rect;
	Translate animations;
	Rect animationsRect;
	boolean isHover;
	Runnable lastFocus;
	int hoverTick;

	public FlatMainButton(String title, ClickableRect rect) {
		super();
		this.animations = new Translate(0, 0);
		this.title = title;
		this.rect = rect;
		this.isHover = false;
		this.animationsRect = new Rect(this.getRect().getX() + this.getRect().getX1() / 2,
				this.getRect().getY() + this.getRect().getY1(), this.getRect().getX() + this.getRect().getX1() / 2,
				this.getRect().getY() + this.getRect().getY1() - 1, new Color(2, 246, 132).getRGB(),
				Rect.RenderType.Position);
		this.lastFocus = this.rect.getFocus();
		this.rect.setFocus(() -> {
			if (hoverTick < 1)
				hoverTick = 1;
			this.lastFocus.run();
			this.isHover = true;
			animations.interpolate((float) this.getRect().getX1() / 2, 0, 0.2f);
			double base = this.getRect().getX() + this.getRect().getX1() / 2;
			this.animationsRect.setX(base - animations.getX());
			this.animationsRect.setWidth(base + animations.getX());
		});

	}

	public void draw() {
		hoverTick--;
		if (!this.isFocus()) {
			animations.interpolate(0, 0, 0.3f);
			double base = this.getRect().getX() + this.getRect().getX1() / 2;
			this.animationsRect.setX(base - animations.getX());
			this.animationsRect.setWidth(base + animations.getX());
		}
		rect.draw();
		int lenth = StringUtils.getWidth(this.getTitle(), SizeType.Size18);
		StringUtils.drawString(this.getTitle(), this.getRect().getX() + (this.getRect().getWidth() - lenth) / 2,
				this.getRect().getY() + 13, this.getColor(isFocus() ? 0 : 1), SizeType.Size18);
		this.animationsRect.draw();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ClickableRect getRect() {
		return rect;
	}

	public void setRect(ClickableRect rect) {
		this.rect = rect;
	}

	public boolean isFocus() {
		return !(hoverTick < 0);
	}

	public int getColor(int type) {
		ClientConfig config = (ClientConfig) Tritium.instance.getModuleManager().getModule(ClientConfig.class);
		switch (config.theme.getValue()) {
		case Dark:
			switch (type) {
			case 0:
				return new Color(255, 255, 255).getRGB();
			case 1:
				return new Color(127, 127, 127).getRGB();
			}
		case Light:
			switch (type) {
			case 0:
				return new Color(0, 0, 0).getRGB();
			case 1:
				return new Color(127, 127, 127).getRGB();
			}
		}
		return 0;
	}

}
