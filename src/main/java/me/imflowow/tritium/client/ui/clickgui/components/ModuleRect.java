package me.imflowow.tritium.client.ui.clickgui.components;

import java.awt.Color;

import me.imflowow.tritium.client.ui.clickgui.utils.UIComponent;
import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.utils.language.LangUtils.SizeType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tritium.api.manager.MessageManager.MessageType;
import tritium.api.module.Module;
import tritium.api.utils.StringUtils;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Image;
import tritium.api.utils.render.Rect;
import tritium.api.utils.render.clickable.entity.ClickableRect;
import tritium.api.utils.render.utils.ColorAnimations;

public class ModuleRect extends UIComponent {
	private Module module;
	private boolean hasValues;
	private ClickableRect rect;
	private Translate anim = new Translate(0, 0);

	private Rect setting;

	private Rect enable_rect;
	private ColorAnimations coloranim;

	private ClickableRect setting_rect;

	public ModuleRect(Module module) {
		this.module = module;
		this.hasValues = !this.module.getValues(false).isEmpty();
		this.init(0, 0);
	}

	@Override
	public void init(double positionX, double positionY) {
		Color color = this.module.isEnabled() ? new Color(1, 223, 1, this.getMenuAlpha())
				: new Color(223, 1, 1, this.getMenuAlpha());
		this.setting = new Rect(0, 0, 1, 1, this.getColor(16), Rect.RenderType.Expand);
		this.coloranim = new ColorAnimations(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		this.setting_rect = new ClickableRect(0, 0, 10, 20, this.getColor(8), Rect.RenderType.Expand, () -> {
			if (!this.getClickGui().getModuleList().isClickable())
				return;
			if (Tritium.instance.getClientListener().getClickGui().getModuleList().getOnSetting() == this.module)
				return;
			if (Tritium.instance.getClientListener().getClickGui().getModuleList().getLastOnSetting() == null) {
				Tritium.instance.getClientListener().getClickGui().getModuleList().setLastOnSetting(this.module);
			} else if (Tritium.instance.getClientListener().getClickGui().getModuleList()
					.getLastOnSetting() == Tritium.instance.getClientListener().getClickGui().getModuleList()
							.getOnSetting()) {
				Tritium.instance.getClientListener().getClickGui().getModuleList().setLastOnSetting(
						Tritium.instance.getClientListener().getClickGui().getModuleList().getOnSetting());
			}

			Tritium.instance.getClientListener().getClickGui().getModuleList().setOnSetting(this.module);
		}, () -> {

		}, () -> {
			if (!this.getClickGui().getModuleList().isClickable())
				return;
			this.setting_rect.setColor(this.getColor(12));
		}, () -> {
		}, () -> {
			this.setting_rect.setColor(this.getColor(11));
		});
		this.rect = new ClickableRect(0, 0, this.hasValues ? 120 : 130, 20, this.getColor(8), Rect.RenderType.Expand,
				() -> {
					if (!this.getClickGui().getModuleList().isClickable())
						return;
					this.module.toggle();
				}, () -> {
				}, () -> {
					if (!this.getClickGui().getModuleList().isClickable())
						return;
					this.rect.setColor(this.getColor(10));
					Tritium.instance.getClientListener().getClickGui().getModuleList().setOnHover(this.module);
					anim.interpolate(5, 0, 0.3f);
				}, () -> {
				}, () -> {
					this.rect.setColor(this.getColor(8));
					anim.interpolate(0, 0, 0.3f);
				});
		this.enable_rect = new Rect(0, 0, 2, 20,
				this.module.isEnabled() ? new Color(1, 223, 1, this.getMenuAlpha()).getRGB()
						: new Color(223, 1, 1, this.getMenuAlpha()).getRGB(),
				Rect.RenderType.Expand);
	}

	@Override
	public void draw(int mouseX, int mouseY, double positionX, double positionY) {

//		this.init(positionX, positionY);

		this.rect.setX(positionX);
		this.rect.setY(positionY);
		this.rect.draw();

		if (this.rect.isInArea()) {
			if (this.rect.isMiddlePressed()) {
				this.getClickGui().setOnBindingModule(this.module);
				Tritium.instance.getMessagemanager().addMessage(
						Tritium.instance.getLanguagemanager().getTextRender().getText(this.getModule().getLabel())
								+ Tritium.instance.getLanguagemanager().getTextRender()
										.getText(": press any key you want to bind to"),
						MessageType.Warnning, 3000);
			}
		}

		if (this.module.isEnabled()) {
			this.enable_rect.setColor(coloranim.getColor(1, 223, 1, this.getMenuAlpha()));
		} else {
			this.enable_rect.setColor(coloranim.getColor(223, 1, 1, this.getMenuAlpha()));
		}
		this.enable_rect.setX(positionX);
		this.enable_rect.setY(positionY);
		this.enable_rect.draw();

		if (this.hasValues) {
			this.setting_rect.setX(positionX + 120);
			this.setting_rect.setY(positionY);
			this.setting_rect.draw();

			this.setting.setColor(this.getColor(16));
			this.setting.setX(positionX + 124.5);
			this.setting.setY(positionY + 7);
			this.setting.draw();

			this.setting.setX(positionX + 124.5);
			this.setting.setY(positionY + 10);
			this.setting.draw();

			this.setting.setX(positionX + 124.5);
			this.setting.setY(positionY + 13);
			this.setting.draw();
		}

		StringUtils.drawString(this.module.getLabel(), positionX + 8 + anim.getX(), positionY + 8, this.getColor(9),
				SizeType.Size18);
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
