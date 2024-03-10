package me.imflowow.tritium.client.ui.clickgui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import tritium.api.module.GlobalModule;
import tritium.api.module.Module;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.Scissor;
import tritium.api.utils.render.clickable.ClickEntity;
import tritium.api.utils.render.utils.MouseBounds.CallType;

public class ModuleList extends UIComponent {

	public Scissor scissor;
	public List<ModuleRect> lists;

	public Module lastOnHover;
	public Module onHover;
	public Module lastOnSetting;
	public Module onSetting;

	public ClickEntity clickarea;
	public boolean clickable;

	public Rect onHover_rect;
	public Translate anim_onhover = new Translate(0, 0);
	public double onHover_width;

	public double tarY;
	public double curY;
	public Translate animation = new Translate(0, 0);

	@Override
	public void init(double positionX, double positionY) {
		this.curY = 0;
		this.tarY = 0;
		this.onHover_width = 0;
		this.lists = new ArrayList();
		this.refreshList(0);
		this.clickarea = new ClickEntity(positionX + 110, positionY + 10, 130, 280, CallType.Expand, () -> {
		}, () -> {
		}, () -> {
			clickable = true;
		}, () -> {
		}, () -> {
			clickable = false;
		});
		this.scissor = new Scissor(positionX + 110, positionY + 10, 130, 280);
		this.onHover_rect = new Rect(2, this.getScaledHeight() - 10, 0, 10, this.getColor(13), Rect.RenderType.Expand);
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {
//		this.init(positionX, positionY);
		this.scissor.setX(positionX + 110);
		this.scissor.setY(positionY + 10);
		this.scissor.setWidth(130);
		this.scissor.setHeight(280);
		this.scissor.doScissor();

		this.clickarea.setX(positionX + 110);
		this.clickarea.setY(positionY + 10);
		this.clickarea.setX1(130);
		this.clickarea.setY1(280);
		this.clickarea.tick();

		this.setOnHover(null);
		int index = 0;
		for (ModuleRect mr : lists) {
			mr.draw(mouseX, mouseY, positionX + 110, positionY + this.curY + 10 + 25 * index);
			index++;
		}

		if (this.isClickable()) {
			if (this.tarY + this.getWheel() < 0) {
				if (this.tarY + this.getWheel() > (lists.size() - 1) * -25) {
					this.tarY += this.getWheel() * 2;
				} else {
					this.tarY = (lists.size() - 1) * -25;
				}
			} else {
				this.tarY = 0;
			}
		}

		this.animation.interpolate((float) this.tarY, 0, 0.3f);
		this.curY = animation.getX();

		if (onHover != null) {
			anim_onhover.interpolate((float) this.onHover_width + 2, 0, 0.3f);
		} else {
			anim_onhover.interpolate(0, 0, 0.3f);
		}

		Tritium.instance.getClientListener().getClickGui().getWholeScreenScissor().doScissor();
		if (this.onHover != null || anim_onhover.getX() != 0) {
			if (this.onHover != null)
				this.lastOnHover = this.onHover;
			String text = this.lastOnHover.getIntroduction();
			float width = StringUtils.getWidth(text, SizeType.Size16);
			this.onHover_width = width;
			this.scissor.setX(2);
			this.scissor.setY(this.getScaledHeight() - 2 - 10);
			this.scissor.setWidth(anim_onhover.getX());
			this.scissor.setHeight(10);
			this.scissor.doScissor();
			this.onHover_rect.setColor(this.getColor(13));
			this.onHover_rect.setWidth(anim_onhover.getX());
			this.onHover_rect.setY(this.getScaledHeight() - 2 - 10);
			this.onHover_rect.draw();

			StringUtils.drawString(text, 3, this.getScaledHeight() - 9, this.getColor(14), SizeType.Size16);
		}

		Tritium.instance.getClientListener().getClickGui().getWholeScreenScissor().doScissor();

	}

	public void refreshList(int type) {
		switch (type) {
		case 0:
			lists.clear();
			List<Module> list1 = Tritium.instance.getModuleManager().getModules(false);
			for (Module module : list1) {
				lists.add(new ModuleRect(module));
			}
			break;
		case 1:
			lists.clear();
			List<Module> list2 = Tritium.instance.getModuleManager().getModules(true);
			for (Module module : list2) {
				if (module instanceof GlobalModule) {
					if (!((GlobalModule) module).isHidden())
						lists.add(new ModuleRect(module));
				}
			}
			break;
		}
	}

	public Module getOnHover() {
		return onHover;
	}

	public void setOnHover(Module onHover) {
		this.onHover = onHover;
	}

	public Module getOnSetting() {
		return onSetting;
	}

	public void setOnSetting(Module onSetting) {
		this.onSetting = onSetting;
	}

	public Module getLastOnSetting() {
		return lastOnSetting;
	}

	public void setLastOnSetting(Module lastOnSetting) {
		this.lastOnSetting = lastOnSetting;
	}

	public double getTarY() {
		return tarY;
	}

	public void setTarY(double tarY) {
		this.tarY = tarY;
	}

	public ClickEntity getClickarea() {
		return clickarea;
	}

	public void setClickarea(ClickEntity clickarea) {
		this.clickarea = clickarea;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

}
