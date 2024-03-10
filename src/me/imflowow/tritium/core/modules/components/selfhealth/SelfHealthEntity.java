package me.imflowow.tritium.core.modules.components.selfhealth;

import me.imflowow.tritium.core.Tritium;
import me.imflowow.tritium.core.modules.SelfHealth;
import tritium.api.module.gui.GuiEntity;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.utils.animate.Translate;
import tritium.api.utils.render.Rect;

public class SelfHealthEntity extends GuiEntity {

	Translate animation = new Translate(0, 0);
	Translate animation2 = new Translate(0, 0);

	public SelfHealthEntity(PositionValue position) {
		super(position);
	}

	@Override
	public void init() {
	}

	@Override
	public void draw(double x, double y) {

		float health = mc.thePlayer.getHealth();
		float maxhealth = mc.thePlayer.getMaxHealth();
		animation.interpolate((health / maxhealth) * 100.0f, 0, this.getModule().animationSpeed.getValue());
		animation2.interpolate((health / maxhealth) * 100.0f, 0, this.getModule().animationSpeed.getValue() / 2);

		new Rect(x, y, 102, 4, this.getModule().backgroundColor.getValue().getColor().getRGB(), Rect.RenderType.Expand)
				.draw();
		new Rect(x + 1, y + 1, animation2.getX(), 2, this.getModule().healthShadowColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
		new Rect(x + 1, y + 1, animation.getX(), 2, this.getModule().healthColor.getValue().getColor().getRGB(),
				Rect.RenderType.Expand).draw();
	}

	@Override
	public int getHeight() {
		return 4;
	}

	@Override
	public int getWidth() {
		return 104;
	}

	public SelfHealth getModule() {
		return (SelfHealth) Tritium.instance.getModuleManager().getModule(SelfHealth.class);
	}

}
